/* ContactListPanel.java

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
package com.cubusmail.gwtui.client.panels.contact;

import com.gwtext.client.data.Record;
import com.gwtext.client.dd.DragData;
import com.gwtext.client.dd.DragDrop;
import com.gwtext.client.widgets.grid.GridDragData;
import com.gwtext.client.widgets.tree.DropNodeCallback;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.domain.ContactFolder;

/**
 * Manage drag & drop for contacts.
 * 
 * @author Jürgen Schlierf
 */
public class ContactsDragAndDropListener extends TreePanelListenerAdapter {

	private final static String POINT_APPEND = "append";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter#onNodeDragOver
	 * (com.gwtext.client.widgets.tree.TreePanel,
	 * com.gwtext.client.widgets.tree.TreeNode, com.gwtext.client.dd.DragData,
	 * java.lang.String, com.gwtext.client.dd.DragDrop,
	 * com.gwtext.client.widgets.tree.TreeNode)
	 */
	@Override
	public boolean onNodeDragOver( TreePanel treePanel, TreeNode target, DragData dragData, String point,
			DragDrop source, TreeNode dropNode ) {

		if ( dragData instanceof GridDragData ) {
			if ( POINT_APPEND.equals( point ) ) {
				ContactFolder currentFoler = GWTSessionManager.get().getCurrentContactFolder();
				ContactFolder targetFolder = (ContactFolder) target.getUserObject();
				if ( currentFoler != null && targetFolder != null
						&& !currentFoler.getId().equals( targetFolder.getId() ) ) {
					return true;
				}
			}
		}
		return false;
	}

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
	@Override
	public boolean doBeforeNodeDrop( TreePanel treePanel, TreeNode target, DragData dragData, String point,
			DragDrop source, TreeNode dropNode, DropNodeCallback dropNodeCallback ) {

		try {
			if ( dragData instanceof GridDragData ) {

				if ( POINT_APPEND.equals( point ) ) {
					ContactFolder targetFolder = (ContactFolder) target.getUserObject();
					Record[] selections = ((GridDragData) dragData).getSelections();
					Long[] ids = getSelectionIds( selections );
					if ( ids != null && ids.length > 0 ) {
						PanelRegistry.CONTACT_LIST_PANEL.mask();
						ServiceProvider.getUserAccountService().moveContacts( ids, targetFolder,
								new AsyncCallbackAdapter<Object>() {

									@Override
									public void onSuccess( Object result ) {

										PanelRegistry.CONTACT_LIST_PANEL.unmask();
										EventBroker.get().fireReloadContacts();
									}

									@Override
									public void onFailure( Throwable caught ) {

										PanelRegistry.CONTACT_LIST_PANEL.unmask();
										super.onFailure( caught );
									}
								} );
						return true;
					}
				}
			}
		}
		catch (Exception e) {
			GWTExceptionHandler.handleException( e );
		}

		return false;
	}

	/**
	 * @param selections
	 * @return
	 */
	private Long[] getSelectionIds( Record[] selections ) {

		Long[] result = null;
		if ( selections != null ) {
			result = new Long[selections.length];
			for (int i = 0; i < selections.length; i++) {
				result[i] = Long.parseLong( selections[i].getId() );
			}
		}

		return result;
	}
}
