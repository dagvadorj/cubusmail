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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * Rename mail folder.
 * 
 * @author Juergen Schlierf
 */
public class RenameFolderAction extends GWTFolderAction implements AsyncCallback<GWTMailFolder> {

	private TreeNode renamedNode;
	private String newName;

	/**
	 * 
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		if ( this.renamedNode != null ) {
			if ( !GWTUtil.hasText( this.newName ) || this.newName.equals( this.renamedNode.getName() ) ) {
				this.tree.discardAllEdits();
			}
			else {
				this.tree.saveAllEdits();
				ServiceProvider.getMailboxService().renameFolder( getSelectedTreeNode().getAttribute( "id" ),
						this.newName, this );
			}
		}
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
		String text = null;
		if ( caught instanceof GWTMailFolderExistException ) {
			text = TextProvider.get().exception_folder_already_exist( e.getFolderName() );
		}
		else {
			text = TextProvider.get().exception_folder_rename( e.getFolderName() );
		}
		SC.warn( text, new BooleanCallback() {

			public void execute( Boolean value ) {

				EventBroker.get().fireFoldersReload();
			}
		} );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMailFolder result ) {

		EventBroker.get().fireFoldersReload();
	}

	public void setRenamedNode( TreeNode renamedNode ) {

		this.renamedNode = renamedNode;
	}

	public void setNewName( String newName ) {

		this.newName = newName;
	}
}
