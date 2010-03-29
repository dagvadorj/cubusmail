/* RetrieveAttachmentServlet.java

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
import java.io.OutputStream;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimePart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.util.MessageUtils;

/**
 * Servlet for attachment download.
 *
 * @author Juergen Schlierf
 */
public class RetrieveAttachmentServlet extends HttpServlet {

	private final Log logger = LogFactory.getLog( getClass() );

	/**
	 * 
	 */
	private static final long serialVersionUID = -648851359889982062L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			IOException {

		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext( request
				.getSession().getServletContext() );

		try {
			String messageId = request.getParameter( "messageId" );
			String attachmentIndex = request.getParameter( "attachmentIndex" );
			boolean view = "1".equals( request.getParameter( "view" ) );

			if ( messageId != null ) {
				IMailbox mailbox = SessionManager.get().getMailbox();
				Message msg = mailbox.getCurrentFolder().getMessageById( Long.parseLong( messageId ) );

				List<MimePart> attachmentList = MessageUtils.attachmentsFromPart( msg );
				int index = Integer.valueOf( attachmentIndex );

				MimePart retrievePart = attachmentList.get( index );

				ContentType contentType = new ContentType( retrievePart.getContentType() );

				String fileName = retrievePart.getFileName();
				if ( StringUtils.isEmpty( fileName ) ) {
					fileName = context
							.getMessage( "message.unknown.attachment", null, SessionManager.get().getLocale() );
				}
				StringBuffer contentDisposition = new StringBuffer();
				if ( !view ) {
					contentDisposition.append( "attachment; filename=\"" );
					contentDisposition.append( fileName ).append( "\"" );
				}

				response.setHeader( "cache-control", "no-store" );
				response.setHeader( "pragma", "no-cache" );
				response.setIntHeader( "max-age", 0 );
				response.setIntHeader( "expires", 0 );

				if ( !StringUtils.isEmpty( contentDisposition.toString() ) ) {
					response.setHeader( "Content-disposition", contentDisposition.toString() );
				}
				response.setContentType( contentType.getBaseType() );
				// response.setContentLength(
				// MessageUtils.calculateSizeFromPart( retrievePart ) );

				BufferedInputStream bufInputStream = new BufferedInputStream( retrievePart.getInputStream() );
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
		catch (Exception ex) {
			logger.error( ex.getMessage(), ex );
		}
	}
}
