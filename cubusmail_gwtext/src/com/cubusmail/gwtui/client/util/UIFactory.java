/* UIFactory.java

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
package com.cubusmail.gwtui.client.util;

import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.tree.TreeNode;

import com.cubusmail.gwtui.client.actions.IGWTAction;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.GWTMailbox;

/**
 * Factory for UI elements.
 *
 * @author Juergen Schlierf
 */
public class UIFactory {

	/**
	 * @param action
	 * @return
	 */
	public static ToolbarButton createToolbarButton( IGWTAction action ) {

		ToolbarButton button = new ToolbarButton( action.getText(), action, action.getImageName() );
		if ( action.getTooltipText() != null ) {
			button.setTooltip( action.getTooltipText() );
		}
		action.registerComponent( button );

		return button;
	}

	/**
	 * @param action
	 * @return
	 */
	public static ToolbarButton createToolbarImageButton( IGWTAction action ) {

		ToolbarButton button = new ToolbarButton( null, action, action.getImageName() );
		if ( action.getTooltipText() != null ) {
			button.setTooltip( action.getTooltipText() );
		}
		action.registerComponent( button );

		return button;
	}

	/**
	 * @param action
	 * @return
	 */
	public static ToolbarMenuButton createToolbarMenuButton( IGWTAction action ) {

		ToolbarMenuButton button = new ToolbarMenuButton( action.getText() );
		button.addListener( action );
		button.setIcon( action.getImageName() );
		if ( action.getTooltipText() != null ) {
			button.setTooltip( action.getTooltipText() );
		}
		action.registerComponent( button );

		return button;
	}

	/**
	 * @param action
	 * @return
	 */
	public static MenuItem createMenuItem( IGWTAction action ) {

		MenuItem item = new MenuItem();
		item.setText( action.getText() );
		item.setIcon( action.getImageName() );
		item.addListener( action );
		action.registerComponent( item );

		return item;
	}

	/**
	 * @param mailFolder
	 * @return
	 */
	public static TreeNode createTreeNode( GWTMailFolder mailFolder ) {

		TreeNode node = new TreeNode( mailFolder.getName() );
		node.setIcon( getFolderIcon( mailFolder ) );
		node.setUserObject( mailFolder );

		for ( int i = 0; i < mailFolder.getSubfolders().length; i++ ) {
			node.appendChild( createTreeNode( mailFolder.getSubfolders()[i] ) );
		}

		return node;
	}

	public static TreeNode createTreeNode( GWTMailbox mailbox ) {

		TreeNode node = new TreeNode( mailbox.getEmailAddress() );
		node.setIcon( getFolderIcon( mailbox ) );
		node.setUserObject( mailbox );

		for ( int i = 0; i < mailbox.getMailFolders().length; i++ ) {
			node.appendChild( createTreeNode( mailbox.getMailFolders()[i] ) );
		}

		return node;
	}

	private static String getFolderIcon( GWTMailbox mailbox ) {

		return ImageProvider.MAIL_FOLDER_MAILBOX;
	}

	private static String getFolderIcon( GWTMailFolder mailFolder ) {

		if ( mailFolder.isInbox() ) {
			return ImageProvider.MAIL_FOLDER_INBOX;
		} else if ( mailFolder.isDraft() ) {
			return ImageProvider.MAIL_FOLDER_DRAFT;
		} else if ( mailFolder.isSent() ) {
			return ImageProvider.MAIL_FOLDER_SENT;
		} else if ( mailFolder.isTrash() ) {
			return ImageProvider.MAIL_FOLDER_TRASH_FULL;
		} else {
			return ImageProvider.MAIL_FOLDER;
		}
	}
}
