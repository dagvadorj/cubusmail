/* NewFolderAction.java

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
package com.cubusmail.client.actions.folder;

import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.exceptions.folder.GWTMailFolderException;
import com.cubusmail.common.exceptions.folder.GWTMailFolderExistException;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;

/**
 * Create a new mail folder.
 * 
 * @author schlierf
 */
public class NewFolderAction extends GWTFolderAction implements AsyncCallback<GWTMailFolder> {

	/**
	 * 
	 */
	public NewFolderAction() {

		super();
		setText( TextProvider.get().actions_newfolder_text() );
		setImageName( ImageProvider.MAIL_FOLDER_NEW );
		setTooltipText( TextProvider.get().actions_newfolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		SC.askforValue( TextProvider.get().actions_newfolder_text(), TextProvider.get().actions_newfolder_question(),
				new ValueCallback() {

					public void execute( String text ) {

						if ( GWTUtil.hasText( text ) ) {
							if ( getSelectedTreeNode() != null ) {
								if ( GWTUtil.getUserData( getSelectedTreeNode() ) instanceof GWTMailFolder ) {
									GWTMailFolder folder = (GWTMailFolder) GWTUtil.getUserData( getSelectedTreeNode() );
									createFolder( folder.getId(), text );
								}
								else {
									createFolder( null, text );
								}
							}
						}
					}
				} );

	}

	/**
	 * @param parentFolderId
	 * @param folderName
	 */
	private void createFolder( String parentFolderId, String folderName ) {

		// PanelRegistry.LEFT_PANEL.mask();
		ServiceProvider.getMailboxService().createFolder( parentFolderId, folderName, this );
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
		if ( caught instanceof GWTMailFolderExistException ) {
			SC.showPrompt( TextProvider.get().common_error(), TextProvider.get().exception_folder_already_exist(
					e.getFolderName() ) );
		}
		else {
			SC.showPrompt( TextProvider.get().common_error(), TextProvider.get().exception_folder_create(
					e.getFolderName() ) );
		}
		EventBroker.get().fireFoldersReload();
		// PanelRegistry.LEFT_PANEL.unmask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMailFolder result ) {

		EventBroker.get().fireFoldersReload();
//		if ( getSelectedTreeNode() != null ) {
//			getSelectedTreeNode().appendChild( UIFactory.createTreeNode( result ) );
//		}
		// PanelRegistry.LEFT_PANEL.unmask();
	}
}
