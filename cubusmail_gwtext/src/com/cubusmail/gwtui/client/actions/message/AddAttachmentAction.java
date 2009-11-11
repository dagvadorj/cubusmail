/* AddAttachmentAction.java

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
package com.cubusmail.gwtui.client.actions.message;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtextux.client.widgets.upload.UploadDialog;
import com.gwtextux.client.widgets.upload.UploadDialogListenerAdapter;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.ComposeMessageWindow;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Action for adding attachments.
 * 
 * @author Juergen Schlierf
 */
public class AddAttachmentAction extends GWTAction {

	private UploadDialog uploadDialog;

	/**
	 * 
	 */
	public AddAttachmentAction() {

		setText( TextProvider.get().actions_compose_addattachment_text() );
		setImageName( ImageProvider.MSG_ADD_ATTACHMENT );
		setTooltipText( TextProvider.get().actions_compose_addattachment_tooltip() );

		this.uploadDialog = new UploadDialog( TextProvider.get().actions_compose_addattachment_text() );
		this.uploadDialog.setModal( true );
		this.uploadDialog.setUrl( ServiceProvider.getAttachmentUploadServletUrl() );

		this.uploadDialog.addListener( new AttachmentUploadDialogListener() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		this.uploadDialog.show();
	}

	private class AttachmentUploadDialogListener extends UploadDialogListenerAdapter implements
			AsyncCallback<GWTAttachment[]> {

		public void onFailure( Throwable caught ) {

			GWTExceptionHandler.handleException( caught );
		}

		public void onSuccess( GWTAttachment[] attachents ) {

			if ( attachents != null ) {
				WindowRegistry.COMPOSE_MESSAGE_WINDOW.get( ComposeMessageWindow.class ).setAttachments( attachents );
			}
		}

		@Override
		public void onUploadComplete( UploadDialog source ) {

			ServiceProvider.getMailboxService().retrieveCurrentComposeMessageAttachments( this );
		}

	}
}
