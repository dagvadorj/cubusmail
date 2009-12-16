/* GWTFolderAction.java

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

import com.cubusmail.client.actions.GWTAction;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * base action for all mail folder actions
 * 
 * @author Juergen Schlierf
 */
public abstract class GWTFolderAction extends GWTAction {

	private TreeNode selectedTreeNode;
	protected TreeGrid tree;

	/**
	 * @return Returns the selectedTreeNode.
	 */
	public TreeNode getSelectedTreeNode() {

		return this.selectedTreeNode;
	}

	/**
	 * @param selectedTreeNode
	 *            The selectedTreeNode to set.
	 */
	public void setSelectedTreeNode( TreeNode selectedTreeNode ) {

		this.selectedTreeNode = selectedTreeNode;
	}

	public void setTree( TreeGrid tree ) {

		this.tree = tree;
	}
}
