/* DeleteFolderAction.java

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
package com.cubusmail.gwtui.client.actions.folder;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.widgets.MessageBox;

import com.cubusmail.gwtui.client.actions.GWTFolderAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.exceptions.folder.GWTMailFolderException;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Delete all messages in mail folder.
 * 
 * @author schlierf
 */
public class EmptyFolderAction extends GWTFolderAction implements AsyncCallback<Void> {

	/**
	 * 
	 */
	public EmptyFolderAction() {

		super();
		setText( TextProvider.get().actions_emptyfolder_text() );
		setImageName( ImageProvider.MAIL_FOLDER_EMPTY );
		setTooltipText( TextProvider.get().actions_emptyfolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		MessageBox.confirm( TextProvider.get().actions_emptyfolder_text(),
				TextProvider.get().actions_emptyfolder_warning(), new MessageBox.ConfirmCallback() {

					public void execute( String btnID ) {

						if ( "yes".equals( btnID ) ) {
							GWTMailFolder folder = (GWTMailFolder) getSelectedTreeNode().getUserObject();
							if ( folder != null ) {
								PanelRegistry.LEFT_PANEL.mask();
								emptyFolder( folder.getId() );
							}
						}
					}
				} );
	}

	private void emptyFolder( String folderId ) {

		PanelRegistry.LEFT_PANEL.mask();
		ServiceProvider.getMailboxService().emptyFolder( folderId, this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
		GWTMailFolderException e = (GWTMailFolderException) caught;
		MessageBox.alert( TextProvider.get().common_error(), TextProvider.get().exception_folder_empty(
				e.getFolderName() ) );
		GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) getSelectedTreeNode().getUserObject() );
		EventBroker.get().fireFolderSelected( (GWTMailFolder) getSelectedTreeNode().getUserObject() );
		EventBroker.get().fireMessagesChanged();
		PanelRegistry.LEFT_PANEL.unmask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

		PanelRegistry.LEFT_PANEL.unmask();
		GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) getSelectedTreeNode().getUserObject() );
		EventBroker.get().fireFolderSelected( (GWTMailFolder) getSelectedTreeNode().getUserObject() );
		EventBroker.get().fireMessagesChanged();
	}
}
