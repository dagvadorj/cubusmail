/* AddressFolderCanvas.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.client.canvases.addressbook;

import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.GWTConstants;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.DisplayNodeType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressFolderCanvas extends SectionStack {

	private TreeNode currentTreeNode;
	private TreeGrid tree;

	public AddressFolderCanvas() {

		setShowResizeBar( true );
		SectionStackSection section = new SectionStackSection();
		section.setCanCollapse( false );
		section.setExpanded( true );
		section.setResizeable( true );

		createTree();
		section.setItems( this.tree );
		setSections( section );

		addDrawHandler( new DrawHandler() {

			@Override
			public void onDraw( DrawEvent event ) {

				tree.fetchData();
			}
		} );

	}

	private void createTree() {

		this.tree = new TreeGrid();
		this.tree.setOverflow( Overflow.AUTO );
		this.tree.setLoadingDataMessage( TextProvider.get().common_mask_text() );
		this.tree.setDisplayNodeType( DisplayNodeType.NULL );
		this.tree.setShowRoot( false );
		this.tree.setSelectionType( SelectionStyle.SINGLE );
		this.tree.setWidth100();
		this.tree.setHeight100();
		this.tree.setAnimateFolders( false );
		this.tree.setShowSortArrow( SortArrow.NONE );
		this.tree.setShowAllRecords( true );
		this.tree.setCanSort( false );
		this.tree.setShowHeader( false );
		this.tree.setAutoFetchData( false );
		this.tree.setLoadDataOnDemand( false );
		this.tree.setDataSource( DataSourceRegistry.ADDRESS_FOLDER.get() );
		this.tree.setCanEdit( true );
		this.tree.setConfirmCancelEditing( false );
		this.tree.setCanAcceptDroppedRecords( true );
		this.tree.setLeaveScrollbarGap( false );
		this.tree.setCanReorderRecords( true );
		this.tree.setCanDrag( false );

		this.tree.addSelectionChangedHandler( new AddressFolderSelectionChangedHandler() );
		this.tree.addDataArrivedHandler( new DataArrivedHandler() {

			public void onDataArrived( DataArrivedEvent event ) {

				tree.getData().setModelType( TreeModelType.CHILDREN );
				tree.getData().openAll();
				tree.selectRecord( 0 );
			}
		} );

		this.tree.addDropHandler( new DropHandler() {

			@Override
			public void onDrop( DropEvent event ) {

				GWT.log( event.getSource().toString() );
			}
		} );
	}

	private class AddressFolderSelectionChangedHandler implements SelectionChangedHandler {

		public void onSelectionChanged( SelectionEvent event ) {

			TreeNode selectedNode = (TreeNode) event.getRecord();
			AddressFolder folder = (AddressFolder) selectedNode.getAttributeAsObject( GWTConstants.ADDRESS_FOLDER );
			if ( !selectedNode.equals( currentTreeNode ) ) {
				currentTreeNode = selectedNode;
				GWTSessionManager.get().setCurrentAddressFolder( folder );
				EventBroker.get().fireAddressFolderSelected( folder );
				EventBroker.get().fireReloadAddressList( folder );
			}
		}
	}
}
