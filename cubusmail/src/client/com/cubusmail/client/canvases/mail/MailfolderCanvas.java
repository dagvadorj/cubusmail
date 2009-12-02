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
package com.cubusmail.client.canvases.mail;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.actions.folder.NewFolderAction;
import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.FoldersReloadListener;
import com.cubusmail.client.events.MessagesChangedListener;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.IGWTFolder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

/**
 * Panel for mail folder.
 * 
 * @author Juergen Schlierf
 */
public class MailfolderCanvas extends SectionStack implements FoldersReloadListener, MessagesChangedListener {

	private TreeNode currentTreeNode;
	private TreeGrid tree;

	// Toolbar items
	private Button refreshFolderButton;
	private Button newFolderButton;
	private Button moveFolderButton;
	private Button renameFolderButton;
	private Button deleteFolderButton;
	private Button emptyFolderButton;

	public MailfolderCanvas() {

		super();
		setShowResizeBar( true );

		SectionStackSection section = new SectionStackSection();
		section.setCanCollapse( false );
		section.setExpanded( true );
		section.setResizeable( true );

		createTree();
		section.setItems( this.tree );

		createToolbar( section );

		setSections( section );

		EventBroker.get().addFoldersReloadListener( this );
		EventBroker.get().addMessagesChangedListener( this );

		addDrawHandler( new DrawHandler() {

			public void onDraw( DrawEvent event ) {

				EventBroker.get().fireFoldersReload();
			}
		} );
	}

	/**
	 * 
	 */
	private void createTree() {

		this.tree = new TreeGrid();
		this.tree.setShowRoot( false );
		this.tree.setSelectionType( SelectionStyle.SINGLE );
		this.tree.setWidth100();
		this.tree.setHeight100();
		this.tree.setAnimateFolders( false );
		this.tree.setShowSortArrow( SortArrow.CORNER );
		this.tree.setShowAllRecords( true );
		this.tree.setLoadDataOnDemand( false );
		this.tree.setCanSort( false );
		this.tree.setShowHeader( false );
		this.tree.setAutoFetchData( false );
		this.tree.setDataSource( DataSourceRegistry.MAIL_FOLDER.get() );
		TreeGridField field = new TreeGridField( "name" );
		this.tree.setFields( field );

		// add action handler to the
		this.tree.addNodeClickHandler( ActionRegistry.NEW_FOLDER.get( NewFolderAction.class ).getNodeClickHandler() );
		this.tree.addNodeContextClickHandler( ActionRegistry.NEW_FOLDER.get( NewFolderAction.class )
				.getNodeContextClickHandler() );
		this.tree.addNodeClickHandler( new MailfolderClickHandler() );

		this.tree.addDataArrivedHandler( new DataArrivedHandler() {

			public void onDataArrived( DataArrivedEvent event ) {

				currentTreeNode = getInboxTreeNode();
				GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) GWTUtil.getUserData( currentTreeNode ) );
				EventBroker.get().fireFolderSelected( (GWTMailFolder) GWTUtil.getUserData( currentTreeNode ) );
				EventBroker.get().fireMessagesChanged();
				tree.getData().openAll();
			}
		} );
	}

	/**
	 * @param section
	 */
	private void createToolbar( SectionStackSection section ) {

		ToolStrip strip = new ToolStrip();
		strip.setAutoWidth();
		strip.setOverflow( Overflow.VISIBLE );
		strip.setBorder( "0px" );

		this.refreshFolderButton = UIFactory.createToolbarButton( ActionRegistry.REFRESH_FOLDER.get(), true );
		strip.addMember( this.refreshFolderButton );
		this.newFolderButton = UIFactory.createToolbarButton( ActionRegistry.NEW_FOLDER.get(), true );
		strip.addMember( this.newFolderButton );
		this.renameFolderButton = UIFactory.createToolbarButton( ActionRegistry.RENAME_FOLDER.get(), true );
		strip.addMember( this.renameFolderButton );
		this.deleteFolderButton = UIFactory.createToolbarButton( ActionRegistry.DELETE_FOLDER.get(), true );
		strip.addMember( this.deleteFolderButton );
		this.emptyFolderButton = UIFactory.createToolbarButton( ActionRegistry.EMPTY_FOLDER.get(), true );
		strip.addMember( this.emptyFolderButton );

		section.setControls( this.refreshFolderButton, this.newFolderButton, this.renameFolderButton,
				this.deleteFolderButton, this.emptyFolderButton );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.events.FoldersReloadListener#onFoldersReload()
	 */
	public void onFoldersReload() {

		this.currentTreeNode = null;
		// changeToolbarButtonStatus( null );
		this.tree.fetchData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.MessagesChangedListener#onMessagesChanged()
	 */
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

	/**
	 * @return
	 */
	private TreeNode getInboxTreeNode() {

		Tree treeData = this.tree.getData();
		TreeNode[] nodes = treeData.getChildren( treeData.getRoot() );
		if ( nodes != null && nodes.length > 0 ) {
			if ( GWTUtil.getUserData( nodes[0] ) instanceof GWTMailbox ) {
				nodes = treeData.getChildren( nodes[0] );
			}

			for (TreeNode node : nodes) {
				GWTMailFolder folder = (GWTMailFolder) GWTUtil.getUserData( node );
				if ( folder.isInbox() ) {
					return (TreeNode) node;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @author Juergen Schlierf
	 */
	private class MailfolderClickHandler implements NodeClickHandler {

		public void onNodeClick( NodeClickEvent event ) {

			TreeNode selectedNode = event.getNode();
			IGWTFolder mailFolder = (IGWTFolder) GWTUtil.getUserData( selectedNode );
			if ( !selectedNode.equals( currentTreeNode ) ) {
				currentTreeNode = selectedNode;
				// changeToolbarButtonStatus( mailFolder );
				if ( mailFolder instanceof GWTMailFolder ) {
					GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) mailFolder );
					EventBroker.get().fireFolderSelected( (GWTMailFolder) mailFolder );
				}
			}
		}
	}
}
