/* MailDragAndDropListener.java

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

import com.google.gwt.core.client.GWT;
import com.gwtext.client.data.Record;
import com.gwtext.client.dd.DragData;
import com.gwtext.client.dd.DragDrop;
import com.gwtext.client.widgets.grid.GridDragData;
import com.gwtext.client.widgets.tree.DropNodeCallback;
import com.gwtext.client.widgets.tree.TreeDragData;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.actions.folder.MoveFolderAction;
import com.cubusmail.gwtui.client.actions.message.CopyMoveMessagesAction;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.IGWTFolder;

/**
 * Manage the drag and drop Operations for moving mail foders and messages.
 * 
 * @author Jürgen Schlierf
 */
public class MailDragAndDropListener extends TreePanelListenerAdapter {

	private final static String POINT_APPEND = "append";

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gwtext.client.widgets.tree.event.TreePanelListenerAdapter#
	 * doBeforeNodeDrop(com.gwtext.client.widgets.tree.TreePanel,
	 * com.gwtext.client.widgets.tree.TreeNode, com.gwtext.client.dd.DragData,
	 * java.lang.String, com.gwtext.client.dd.DragDrop,
	 * com.gwtext.client.widgets.tree.TreeNode,
	 * com.gwtext.client.widgets.tree.DropNodeCallback)
	 */
	public boolean doBeforeNodeDrop( TreePanel treePanel, TreeNode target, DragData dragData, String point,
			DragDrop source, TreeNode dropNode, DropNodeCallback dropNodeCallback ) {

		try {
			if ( dragData instanceof TreeDragData ) {
				IGWTFolder sourceFolder = (IGWTFolder) dropNode.getUserObject();
				IGWTFolder targedFolder = (IGWTFolder) target.getUserObject();

				ActionRegistry.MOVE_FOLDER.get( MoveFolderAction.class ).moveFolder( sourceFolder.getId(),
						targedFolder.getId() );
				return true;
			}
			else if ( dragData instanceof GridDragData ) {

				if ( POINT_APPEND.equals( point ) ) {
					GWTMailFolder targetFolder = (GWTMailFolder) target.getUserObject();
					long[] messageIds = getSelectedMessageIds( ((GridDragData) dragData).getSelections() );
					ActionRegistry.MOVE_MESSAGES.get( CopyMoveMessagesAction.class ).copyMoveMessagesTo( targetFolder,
							messageIds );
					return true;
				}
				return false;
			}
		}
		catch (Throwable e) {
			GWT.log( e.getMessage(), e );
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gwtext.client.widgets.tree.event.TreePanelListenerAdapter#
	 * onNodeDragOver(com.gwtext.client.widgets.tree.TreePanel,
	 * com.gwtext.client.widgets.tree.TreeNode, com.gwtext.client.dd.DragData,
	 * java.lang.String, com.gwtext.client.dd.DragDrop,
	 * com.gwtext.client.widgets.tree.TreeNode)
	 */
	public boolean onNodeDragOver( TreePanel treePanel, TreeNode target, DragData dragData, String point,
			DragDrop source, TreeNode dropNode ) {

		// is drop node allowed to move
		try {
			if ( dragData instanceof TreeDragData ) {
				IGWTFolder sourceFolder = (IGWTFolder) dropNode.getUserObject();
				IGWTFolder targedFolder = (IGWTFolder) target.getUserObject();

				return POINT_APPEND.equals( point ) && allowMoving( sourceFolder, targedFolder );
			}
			else if ( dragData instanceof GridDragData ) {
				return POINT_APPEND.equals( point );
			}
		}
		catch (Throwable e) {
			GWT.log( e.getMessage(), e );
		}

		return false;
	}

	private long[] getSelectedMessageIds( Record[] selectedMsgs ) {

		if ( selectedMsgs != null && selectedMsgs.length > 0 ) {
			long[] messsageIds = new long[selectedMsgs.length];
			for (int i = 0; i < selectedMsgs.length; i++) {
				messsageIds[i] = Long.parseLong( selectedMsgs[i].getId() );
			}
			return messsageIds;
		}

		return null;
	}

	/**
	 * @param source
	 * @param target
	 * @return
	 */
	private boolean allowMoving( IGWTFolder source, IGWTFolder target ) {

		if ( source == null || target == null ) {
			return false;
		}

		if ( !source.isMoveSupported() ) {
			return false;
		}

		if ( source.getParent().getId().equals( target.getId() ) ) {
			return false;
		}

		if ( source.getId().equals( target.getId() ) ) {
			return false;
		}

		return true;
	}
}
