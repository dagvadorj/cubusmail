/* RenameContactFolderAction.java

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
 * Rename contact folder.
 * 
 * @author Juergen Schlierf
 */
public class RenameContactFolderAction extends GWTFolderAction {

	public RenameContactFolderAction() {

		super();
		setText( TextProvider.get().actions_rename_contactfolder_text() );
		setImageName( ImageProvider.CONTACT_FOLDER_RENAME );
		setTooltipText( TextProvider.get().actions_rename_contactfolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		MessageBox.prompt( TextProvider.get().actions_newfolder_text(), TextProvider.get()
				.actions_rename_contactfolder_question(), new MessageBox.PromptCallback() {

			public void execute( String btnID, String text ) {

				if ( "ok".equals( btnID ) ) {
					if ( getSelectedTreeNode() != null ) {
						renameFolder( getSelectedTreeNode(), text );
					}
				}
			}
		} );
	}

	/**
	 * @param folder
	 * @param name
	 */
	private void renameFolder( final TreeNode node, final String name ) {

		PanelRegistry.LEFT_PANEL.mask();
		ContactFolder folder = (ContactFolder) node.getUserObject();
		folder.setFolderName( name );
		ServiceProvider.getUserAccountService().saveContactFolder( folder, new AsyncCallbackAdapter<Void>() {

			@Override
			public void onSuccess( Void result ) {

				node.setText( name );
				PanelRegistry.LEFT_PANEL.unmask();
			}

			@Override
			public void onFailure( Throwable caught ) {

				super.onFailure( caught );
				PanelRegistry.LEFT_PANEL.unmask();
			}
		} );
	}

}
