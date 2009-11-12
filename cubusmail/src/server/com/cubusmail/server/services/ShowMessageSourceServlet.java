/* ShowMessageSourceServlet.java

   Copyright (c) 2009 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.server.services;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.util.CubusConstants;

/**
 * Servlet for showing the message source.
 *
 * @author Juergen Schlierf
 */
public class ShowMessageSourceServlet extends HttpServlet {

	private Log logger = LogFactory.getLog( this.getClass() );

	/**
	 * 
	 */
	private static final long serialVersionUID = 1739028172595259352L;

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
			if ( messageId != null ) {
				IMailbox mailbox = SessionManager.get().getMailbox();
				Message msg = mailbox.getCurrentFolder().getMessageById( Long.parseLong( messageId ) );

				ContentType contentType = new ContentType( "text/plain" );
				response.setContentType( contentType.getBaseType() );
				response.setHeader( "expires", "0" );
				String charset = null;
				if ( msg.getContentType() != null ) {
					try {
						charset = new ContentType( msg.getContentType() ).getParameter( "charset" );
					}
					catch (Throwable e) {
						// should never happen
					}
				}
				if ( null == charset || charset.equalsIgnoreCase( CubusConstants.US_ASCII ) ) {
					charset = CubusConstants.DEFAULT_CHARSET;
				}

				OutputStream outputStream = response.getOutputStream();

				// writing the header
				String header = generateHeader( msg );
				outputStream.write( header.getBytes(), 0, header.length() );

				BufferedInputStream bufInputStream = new BufferedInputStream( msg.getInputStream() );

				InputStreamReader reader = null;

				try {
					reader = new InputStreamReader( bufInputStream, charset );
				}
				catch (UnsupportedEncodingException e) {
					logger.error( e.getMessage(), e );
					reader = new InputStreamReader( bufInputStream );
				}

				OutputStreamWriter writer = new OutputStreamWriter( outputStream );
				char[] inBuf = new char[1024];
				int len = 0;
				try {
					while ((len = reader.read( inBuf )) > 0) {
						writer.write( inBuf, 0, len );
					}
				}
				catch (Throwable e) {
					logger.warn( "Download canceled!" );
				}

				writer.flush();
				writer.close();
				outputStream.flush();
				outputStream.close();
			}

		}
		catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
	}

	/**
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String generateHeader( Message msg ) {

		StringBuffer headerString = new StringBuffer();

		try {
			Enumeration<Header> e = msg.getAllHeaders();
			if ( e != null ) {
				for (; e.hasMoreElements();) {
					Header header = e.nextElement();
					headerString.append( header.getName() );
					headerString.append( ": " );
					headerString.append( header.getValue() );
					headerString.append( '\n' );
				}
			}
		}
		catch (MessagingException e) {
			logger.error( e.getMessage(), e );
		}

		return headerString.toString();
	}
}
