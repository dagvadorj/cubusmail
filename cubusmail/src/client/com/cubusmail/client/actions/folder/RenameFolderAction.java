/* RenameFolderAction.java

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
import com.cubusmail.common.util.ImageProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;

/**
 * Rename mail folder.
 * 
 * @author Juergen Schlierf
 */
public class RenameFolderAction extends GWTFolderAction implements AsyncCallback<String> {

	/**
	 * 
	 */
	public RenameFolderAction() {

		super();
		setText( TextProvider.get().actions_renamefolder_text() );
		setImageName( ImageProvider.MAIL_FOLDER_RENAME );
		setTooltipText( TextProvider.get().actions_renamefolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		SC.askforValue( TextProvider.get().actions_rename_contactfolder_text(), TextProvider.get()
				.actions_renamefolder_question(), new ValueCallback() {

			public void execute( String text ) {

				if ( GWTUtil.hasText( text ) ) {
					if ( getSelectedTreeNode() != null ) {
						if ( GWTUtil.getUserData( getSelectedTreeNode() ) instanceof GWTMailFolder ) {
							GWTMailFolder folder = (GWTMailFolder) GWTUtil.getUserData( getSelectedTreeNode() );
							renameFolder( folder.getId(), text );
						}
					}
				}
			}
		} );

	}

	private void renameFolder( String folderId, String newName ) {

		// PanelRegistry.LEFT_PANEL.mask();
		ServiceProvider.getMailboxService().renameFolder( folderId, newName, this );
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
			SC.showPrompt( TextProvider.get().common_error(), TextProvider.get().exception_folder_rename(
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
	public void onSuccess( String result ) {

		if ( getSelectedTreeNode() != null ) {
			getSelectedTreeNode().setTitle( result );
		}
		// PanelRegistry.LEFT_PANEL.unmask();
	}
}
