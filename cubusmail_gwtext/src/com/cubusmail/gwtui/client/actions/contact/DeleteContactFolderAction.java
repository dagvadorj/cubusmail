/* DeleteContactFolderAction.java

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
package com.cubusmail.gwtui.client.actions.contact;

import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.tree.TreeNode;

import com.cubusmail.gwtui.client.actions.GWTFolderAction;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.ContactFolder;

/**
 * Delete contact folder.
 * 
 * @author Juergen Schlierf
 */
public class DeleteContactFolderAction extends GWTFolderAction {

	public DeleteContactFolderAction() {

		super();
		setText( TextProvider.get().actions_delete_contactfolder_text() );
		setImageName( ImageProvider.CONTACT_FOLDER_DELETE );
		setTooltipText( TextProvider.get().actions_delete_contactfolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		MessageBox.confirm( TextProvider.get().actions_deletefolder_text(),
				TextProvider.get().actions_deletefolder_warning(), new MessageBox.ConfirmCallback() {

					public void execute( String btnID ) {

						if ( "yes".equals( btnID ) ) {
							TreeNode folder = getSelectedTreeNode();
							if ( folder != null ) {
								PanelRegistry.LEFT_PANEL.mask();
								deleteFolder( folder );
							}
						}
					}
				} );
	}

	/**
	 * @param node
	 */
	private void deleteFolder( final TreeNode node ) {

		ContactFolder folder = (ContactFolder) node.getUserObject();
		ServiceProvider.getUserAccountService().deleteContactFolder( folder, new AsyncCallbackAdapter<Void>() {

			@Override
			public void onSuccess( Void result ) {

				node.remove();
			}
		} );

	}
}
