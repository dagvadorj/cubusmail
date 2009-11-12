/* AttachmentUploadServlet.java

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
package com.cubusmail.gwtui.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

import com.cubusmail.mail.SessionManager;

/**
 * Retrieve Attachments.
 * 
 * @author Juergen Schlierf
 */
public class AttachmentUploadServlet extends HttpServlet {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7973672729803989276L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent( request );

		// Create a new file upload handler
		if ( isMultipart ) {
			ServletFileUpload upload = new ServletFileUpload();

			try {
				// Parse the request
				FileItemIterator iter = upload.getItemIterator( request );
				while ( iter.hasNext() ) {
					FileItemStream item = iter.next();
					String name = item.getFieldName();
					InputStream stream = item.openStream();
					if ( item.isFormField() ) {
						System.out.println( "Form field " + name + " with value " + Streams.asString( stream )
								+ " detected." );
					} else {
						System.out.println( "File field " + name + " with file name " + item.getName() + " detected." );
						DataSource source = createDataSource( item );
						SessionManager.get().getCurrentComposeMessage().addComposeAttachment( source );
					}

					JSONObject jsonResponse = null;
					try {
						jsonResponse = new JSONObject();
						jsonResponse.put( "success", true );
						jsonResponse.put( "error", "Upload successful" );
					}
					catch ( Exception e ) {

					}

					Writer w = new OutputStreamWriter( response.getOutputStream() );
					w.write( jsonResponse.toString() );
					w.close();

					stream.close();
				}
			}
			catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}

		response.setStatus( HttpServletResponse.SC_OK );
	}

	/**
	 * Create a MimeBodyPart.
	 * 
	 * @param item
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private DataSource createDataSource( FileItemStream item ) throws MessagingException, IOException {

		final String fileName = FilenameUtils.getName( item.getName() );
		final String contentType = item.getContentType();
		final InputStream stream = item.openStream();

		ByteArrayDataSource source = new ByteArrayDataSource( stream, contentType );
		source.setName( fileName );

		return source;
	}
}
