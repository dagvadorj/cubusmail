/* MailFolderTreePanel.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.gwtui.client.panels;

import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;

import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.GWTMailbox;
import com.cubusmail.gwtui.client.util.UIFactory;

/**
 * Tree panel for the mail folder tree.
 * 
 * @author Jürgen Schlierf
 */
public class MailFolderTreePanel extends TreePanel {

	private TreeNode rootNode;

	/**
	 * 
	 */
	public MailFolderTreePanel() {

		this.rootNode = new TreeNode();

		setRootVisible( false );
		setRootNode( this.rootNode );
		setAnimate( false );
		setEnableDD( true );
		setAutoScroll( true );
		setBorder( false );
		setDdGroup( "messagesDDGroup" );
	}

	/**
	 * @param mailFolder
	 */
	public void buildTree( GWTMailbox mailbox ) {

		TreeNode rootNode = getRootNode();
		TreeNode newNode = UIFactory.createTreeNode( mailbox );
		if ( rootNode.getChildNodes().length > 0 ) {
			rootNode.replaceChild( newNode, rootNode.getChildNodes()[0] );
		} else {
			rootNode.appendChild( newNode );
		}
		expandAll();
	}

	public void appenChild( TreeNode parentTreeNode, GWTMailFolder newMailFolder ) {

		parentTreeNode.appendChild( UIFactory.createTreeNode( newMailFolder ) );
	}

	public TreeNode getInboxTreeNode() {

		if ( this.rootNode.getChildNodes()[0].getChildNodes() != null ) {
			for ( Node node : this.rootNode.getChildNodes()[0].getChildNodes() ) {
				GWTMailFolder folder = (GWTMailFolder) node.getUserObject();
				if ( folder.isInbox() ) {
					return (TreeNode) node;
				}
			}
		}
		return null;
	}
}
