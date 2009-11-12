/* ServiceProvider.java

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
package com.cubusmail.client.util;

import com.cubusmail.common.services.ICubusService;
import com.cubusmail.common.services.ICubusServiceAsync;
import com.cubusmail.common.services.IMailboxService;
import com.cubusmail.common.services.IMailboxServiceAsync;
import com.cubusmail.common.services.IUserAccountService;
import com.cubusmail.common.services.IUserAccountServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Service locator for all services.
 * 
 * @author Juergen Schlierf
 */
public class ServiceProvider {

	private static final String SERVLET_NAME_CUBUSSERVICE = "cubusService.rpc";
	private static final String SERVLET_NAME_MAILBOXSERVICE = "mailboxService.rpc";
	private static final String SERVLET_NAME_USERACCOUNT_SERVICE = "userAccountService.rpc";

	public static final String SERVLET_NAME_SHOW_MESSAGE_SOURCE = "showMessageSource.rpc";
	public static final String SERVLET_NAME_ATTACHMENT_UPLOAD = "attachmentUpload.rpc";
	public static final String SERVLET_NAME_RETRIEVE_ATTACHMENT = "retrieveAttachment.rpc";
	public static final String SERVLET_NAME_RETRIEVE_IMAGE = "retrieveImage.rpc";

	private static ServiceProvider instance;

	private ICubusServiceAsync cubusService;
	private IMailboxServiceAsync mailboxService;
	private IUserAccountServiceAsync userAccountService;

	private ServiceProvider() {

		this.cubusService = (ICubusServiceAsync) GWT.create( ICubusService.class );
		((ServiceDefTarget) this.cubusService)
				.setServiceEntryPoint( GWT.getModuleBaseURL() + SERVLET_NAME_CUBUSSERVICE );

		this.mailboxService = (IMailboxServiceAsync) GWT.create( IMailboxService.class );
		((ServiceDefTarget) this.mailboxService).setServiceEntryPoint( GWT.getModuleBaseURL()
				+ SERVLET_NAME_MAILBOXSERVICE );

		this.userAccountService = (IUserAccountServiceAsync) GWT.create( IUserAccountService.class );
		((ServiceDefTarget) this.userAccountService).setServiceEntryPoint( GWT.getModuleBaseURL()
				+ SERVLET_NAME_USERACCOUNT_SERVICE );
	}

	private static ServiceProvider get() {

		if ( instance == null ) {
			instance = new ServiceProvider();
		}

		return instance;
	}

	/**
	 * Return the global service.
	 * 
	 * @return
	 */
	public static ICubusServiceAsync getCubusService() {

		return get().cubusService;
	}

	/**
	 * Return service for mail boxes.
	 * 
	 * @return
	 */
	public static IMailboxServiceAsync getMailboxService() {

		return get().mailboxService;
	}

	/**
	 * Return service for UserAccount
	 * 
	 * @return
	 */
	public static IUserAccountServiceAsync getUserAccountService() {

		return get().userAccountService;
	}

	public static String getMessageSourceServletUrl( String messageId ) {

		return GWT.getModuleBaseURL() + SERVLET_NAME_SHOW_MESSAGE_SOURCE + "?messageId=" + messageId;
	}

	public static String getAttachmentUploadServletUrl() {

		return GWT.getModuleBaseURL() + SERVLET_NAME_ATTACHMENT_UPLOAD;
	}

	public static String getRetrieveAttachmentServletUrl( long messageId, int attachmentIndex ) {

		return GWT.getModuleBaseURL() + SERVLET_NAME_RETRIEVE_ATTACHMENT + "?messageId=" + messageId
				+ "&attachmentIndex=" + attachmentIndex;
	}

	public static String getRetrieveImageServletUrl( long messageId, int attachmentIndex, boolean thumbnail ) {

		return GWT.getModuleBaseURL() + SERVLET_NAME_RETRIEVE_IMAGE + "?messageId=" + messageId + "&attachmentIndex="
				+ attachmentIndex + "&thumbnail=" + thumbnail;
	}
}
