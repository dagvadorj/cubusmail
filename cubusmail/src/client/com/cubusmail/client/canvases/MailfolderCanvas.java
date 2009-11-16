/* MailfolderCanvas.java

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
package com.cubusmail.client.canvases;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.FoldersReloadListener;
import com.cubusmail.client.events.MessagesChangedListener;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.common.model.GWTMailFolder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * Panel for mail folder.
 * 
 * @author Juergen Schlierf
 */
public class MailfolderCanvas extends SectionStack implements AsyncCallback<GWTMailFolder[]>, FoldersReloadListener,
		MessagesChangedListener {

	private TreeNode currentTreeNode;

	// Toolbar items
	private Button refreshFolderButton;
	private Button newFolderButton;
	private Button moveFolderButton;
	private Button renameFolderButton;
	private Button deleteFolderButton;
	private Button emptyFolderButton;
	
	public MailfolderCanvas() {
		super();
		
		SectionStackSection section = new SectionStackSection();
		section.setCanCollapse( false );
		section.setExpanded( true );
		section.setResizeable( true );

		TreeGrid tree = new TreeGrid();
		tree.setWidth100();
		tree.setHeight100();
		tree.setAnimateFolderTime( 100 );
		tree.setAnimateFolders( true );
		tree.setAnimateFolderSpeed( 1000 );
		tree.setShowSortArrow( SortArrow.CORNER );
		tree.setShowAllRecords( true );
		tree.setLoadDataOnDemand( false );
		tree.setCanSort( false );
		tree.setCellHeight( 17 );
		tree.setShowHeader( false );

		TreeGridField field = new TreeGridField();
		field.setCanFilter( true );
		field.setName( "name" );
		field.setTitle( "<b>SmartGWT Showcase</b>" );
		tree.setFields( field );

		Tree treeData = new Tree();
		treeData.setModelType(TreeModelType.PARENT);
		treeData.setNameProperty("name");
		treeData.setOpenProperty("isOpen");
		treeData.setIdField("nodeID");
		treeData.setParentIdField("parentNodeID");
		treeData.setRootValue("root");
		
		EventBroker.get().addFoldersReloadListener( this );
		EventBroker.get().addMessagesChangedListener( this );

		addDrawHandler( new DrawHandler() {

			public void onDraw( DrawEvent event ) {

				EventBroker.get().fireFoldersReload();
			}
		} );
	}

	private void createToolbar() {
		ToolStrip strip = new ToolStrip();
		strip.setAutoWidth();
		strip.setOverflow(Overflow.VISIBLE);
		strip.setBorder("0px");

		// this.refreshFolderButton = UIFactory.createButton( ActionRegistry. )
		// strip.addMember( UIFactory. )
	}
	
	public void onFailure( Throwable caught ) {

	}

	public void onSuccess( GWTMailFolder[] result ) {

	}

	public void onFoldersReload() {

		this.currentTreeNode = null;
		// changeToolbarButtonStatus( null );
		ServiceProvider.getMailboxService().retrieveFolderTree( this );
	}

	public void onMessagesChanged() {

		if ( this.currentTreeNode != null ) {
			final TreeNode selectedNode = this.currentTreeNode;
			ServiceProvider.getMailboxService().getFormattedMessageCount(
					((GWTMailFolder) GWTUtil.getUserData( selectedNode )).getId(), new AsyncCallback<String>() {

						public void onFailure( Throwable caught ) {

							GWTExceptionHandler.handleException( caught );
						}

						public void onSuccess( String result ) {

							selectedNode.setTitle( result );
						}

					} );
		}
	}
}
