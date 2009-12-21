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

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.ImageProvider;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * Create a new mail folder.
 * 
 * @author schlierf
 */
public class NewFolderAction extends GWTFolderAction {

	/**
	 * 
	 */
	public NewFolderAction() {

		super();
		setText( TextProvider.get().actions_newfolder_text() );
		setIcon( ImageProvider.MAIL_FOLDER_NEW );
		setTooltip( TextProvider.get().actions_newfolder_tooltip() );
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
								if ( GWTUtil.getGwtFolder( getSelectedTreeNode() ) instanceof GWTMailFolder ) {
									createFolder( getSelectedTreeNode(), text );
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
	private void createFolder( final TreeNode parentFolderNode, String folderName ) {

		// PanelRegistry.LEFT_PANEL.mask();
		TreeNode newFolder = new TreeNode( folderName );
		newFolder.setParentID( getSelectedTreeNode().getAttributeAsString( "id" ) );
		DSRequest request = new DSRequest();
		request.setAttribute( GWTMailConstants.PARAM_PARENT_FOLDER, getSelectedTreeNode() );
		this.tree.addData( newFolder, new DSCallback() {

			public void execute( DSResponse response, Object rawData, DSRequest request ) {

				if ( response.getData() != null && response.getData().length > 0 ) {
					TreeNode newNode = (TreeNode) response.getData()[0];
					insertSorted( tree.getTree(), parentFolderNode, newNode );
				}
			}
		}, request );
	}
}
