/* MoveFolderAction.java

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
import com.cubusmail.common.model.IGWTFolder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * Action for moving mail folders
 * 
 * @author Juergen Schlierf
 */
public class MoveFolderAction extends GWTFolderAction implements AsyncCallback<GWTMailFolder> {

	private TreeNode sourceNode;
	private TreeNode targetNode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		IGWTFolder sourceFolder = GWTUtil.getGwtFolder( this.sourceNode );
		IGWTFolder targetFolder = GWTUtil.getGwtFolder( this.targetNode );
		ServiceProvider.getMailboxService().moveFolder( sourceFolder.getId(), targetFolder.getId(), this );
	}

	/**
	 * @param sourceNode
	 */
	public void setSourceNode( TreeNode sourceNode ) {

		this.sourceNode = sourceNode;
	}

	/**
	 * @param targetNode
	 */
	public void setTargetNode( TreeNode targetNode ) {

		this.targetNode = targetNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMailFolder result ) {

		// GWTUtil.setGwtFolder( this.sourceNode, result );
		// moveSorted( this.tree.getData(), this.targetNode, this.sourceNode );
		EventBroker.get().fireFoldersReload();
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
			text = TextProvider.get().exception_folder_move( e.getFolderName() );
		}
		SC.warn( text, new BooleanCallback() {

			public void execute( Boolean value ) {

				EventBroker.get().fireFoldersReload();
			}
		} );
	}
}
