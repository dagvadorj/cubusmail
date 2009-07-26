/* RetrieveImageServlet.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
   This file is part of Cubusmail (http://code.google.com/p/cubusmail/).
	
   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.
	
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.
	
   You should have received a copy of the GNU Lesser General Public
   License along with Cubusmail. If not, see <http://www.gnu.org/licenses/>.
   
 */
package com.cubusmail.gwtui.server.services;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimePart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import com.cubusmail.mail.IMailbox;
import com.cubusmail.mail.SessionManager;
import com.cubusmail.mail.util.MessageUtils;

/**
 * Retrieve a thumbnail of an image.
 * 
 * @author Jürgen Schlierf
 */
public class RetrieveImageServlet extends HttpServlet {

	private static final long serialVersionUID = -8629074720127118704L;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private Logger logger = Logger.getLogger( this.getClass() );

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			IOException {

		try {
			String messageId = request.getParameter( "messageId" );
			String attachmentIndex = request.getParameter( "attachmentIndex" );
			boolean isThumbnail = Boolean.valueOf( request.getParameter( "thumbnail" ) ).booleanValue();

			if ( messageId != null ) {
				IMailbox mailbox = SessionManager.get().getMailbox();
				Message msg = mailbox.getCurrentFolder().getMessageById( Long.parseLong( messageId ) );

				if ( isThumbnail ) {
					List<MimePart> attachmentList = MessageUtils.attachmentsFromPart( msg );
					int index = Integer.valueOf( attachmentIndex );

					MimePart retrievePart = attachmentList.get( index );

					ContentType contentType = new ContentType( retrievePart.getContentType() );
					response.setContentType( contentType.getBaseType() );

					BufferedInputStream bufInputStream = new BufferedInputStream( retrievePart.getInputStream() );
					OutputStream outputStream = response.getOutputStream();

					writeScaledImage( bufInputStream, outputStream );

					bufInputStream.close();
					outputStream.flush();
					outputStream.close();
				}
				else {
					Part imagePart = findImagePart( msg );
					if ( imagePart != null ) {
						ContentType contentType = new ContentType( imagePart.getContentType() );
						response.setContentType( contentType.getBaseType() );

						BufferedInputStream bufInputStream = new BufferedInputStream( imagePart.getInputStream() );
						OutputStream outputStream = response.getOutputStream();

						byte[] inBuf = new byte[1024];
						int len = 0;
						int total = 0;
						while ((len = bufInputStream.read( inBuf )) > 0) {
							outputStream.write( inBuf, 0, len );
							total += len;
						}

						bufInputStream.close();
						outputStream.flush();
						outputStream.close();
					}
				}
			}
		}
		catch (Exception ex) {
			logger.error( ex.getMessage(), ex );
		}
	}

	/**
	 * @param bufInputStream
	 * @param outputStream
	 */
	private void writeScaledImage( BufferedInputStream bufInputStream, OutputStream outputStream ) {

		long millis = System.currentTimeMillis();
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = bufInputStream.read( buffer, 0, 8192 )) != -1) {
				bos.write( buffer, 0, bytesRead );
			}
			bos.close();

			byte[] imageBytes = bos.toByteArray();

			Image image = Toolkit.getDefaultToolkit().createImage( imageBytes );
			MediaTracker mediaTracker = new MediaTracker( new Container() );
			mediaTracker.addImage( image, 0 );
			mediaTracker.waitForID( 0 );
			// determine thumbnail size from WIDTH and HEIGHT
			int thumbWidth = 300;
			int thumbHeight = 200;
			double thumbRatio = (double) thumbWidth / (double) thumbHeight;
			int imageWidth = image.getWidth( null );
			int imageHeight = image.getHeight( null );
			double imageRatio = (double) imageWidth / (double) imageHeight;
			if ( thumbRatio < imageRatio ) {
				thumbHeight = (int) (thumbWidth / imageRatio);
			}
			else {
				thumbWidth = (int) (thumbHeight * imageRatio);
			}
			// draw original image to thumbnail image object and
			// scale it to the new size on-the-fly
			BufferedImage thumbImage = new BufferedImage( thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB );
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
			graphics2D.drawImage( image, 0, 0, thumbWidth, thumbHeight, null );

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( outputStream );
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam( thumbImage );
			int quality = 70;
			quality = Math.max( 0, Math.min( quality, 100 ) );
			param.setQuality( (float) quality / 100.0f, false );
			encoder.setJPEGEncodeParam( param );
			encoder.encode( thumbImage );
		}
		catch (IOException ex) {
			logger.error( ex.getMessage(), ex );
		}
		catch (InterruptedException ex) {
			logger.error( ex.getMessage(), ex );
		}
		finally {
			logger.debug( "Time for thumbnail: " + (System.currentTimeMillis() - millis) + "ms" );
		}
	}

	/**
	 * @param parent
	 * @return
	 */
	private Part findImagePart( Part parent ) {

		try {
			if ( MessageUtils.isImagepart( parent ) ) {
				return parent;
			}
			else if ( parent.isMimeType( "multipart/*" ) ) {
				Multipart mp;
				mp = (Multipart) parent.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++) {
					Part subPart = findImagePart( mp.getBodyPart( i ) );
					if ( subPart != null ) {
						return subPart;
					}
				}
			}
		}
		catch (MessagingException e) {
			logger.error( e.getMessage(), e );
		}
		catch (IOException e) {
			logger.error( e.getMessage(), e );
		}

		return null;
	}
}
