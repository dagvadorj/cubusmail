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
import com.cubusmail.client.actions.folder.NewFolderAction;
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
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
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
public class MailfolderCanvas extends SectionStack implements AsyncCallback<GWTMailFolder[]>, FoldersReloadListener,
		MessagesChangedListener {

	private TreeNode currentTreeNode;
	private Tree treeData;
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

		TreeGridField field = new TreeGridField();
		field.setCanFilter( true );
		field.setName( "name" );
		field.setTitle( "<b>SmartGWT Showcase</b>" );
		this.tree.setFields( field );

		this.treeData = new Tree();
		treeData.setModelType( TreeModelType.PARENT );
		treeData.setNameProperty( "name" );
		treeData.setIdField( "ID" );
		treeData.setParentIdField( "parentID" );
		this.tree.setData( this.treeData );
		this.tree.addNodeClickHandler( new MailfolderClickHandler() );

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
		this.tree.setSelectionType( SelectionStyle.SINGLE );
		this.tree.setWidth100();
		this.tree.setHeight100();
		this.tree.setAnimateFolders( false );
		this.tree.setShowSortArrow( SortArrow.CORNER );
		this.tree.setShowAllRecords( true );
		this.tree.setLoadDataOnDemand( false );
		this.tree.setCanSort( false );
		this.tree.setShowHeader( false );
		this.tree.setShowRoot( true );

		// add action handler to the
		this.tree.addNodeClickHandler( ActionRegistry.NEW_FOLDER.get( NewFolderAction.class ).getNodeClickHandler() );
		this.tree.addNodeContextClickHandler( ActionRegistry.NEW_FOLDER.get( NewFolderAction.class )
				.getNodeContextClickHandler() );
	}

	/**
	 * @param section
	 */
	private void createToolbar( SectionStackSection section ) {

		ToolStrip strip = new ToolStrip();
		strip.setAutoWidth();
		strip.setOverflow( Overflow.VISIBLE );
		strip.setBorder( "0px" );

		this.refreshFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.REFRESH_FOLDER.get() );
		strip.addMember( this.refreshFolderButton );
		this.newFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.NEW_FOLDER.get() );
		strip.addMember( this.newFolderButton );
		this.renameFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.RENAME_FOLDER.get() );
		strip.addMember( this.renameFolderButton );
		this.deleteFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.DELETE_FOLDER.get() );
		strip.addMember( this.deleteFolderButton );
		this.emptyFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.EMPTY_FOLDER.get() );
		strip.addMember( this.emptyFolderButton );

		section.setControls( this.refreshFolderButton, this.newFolderButton, this.renameFolderButton,
				this.deleteFolderButton, this.emptyFolderButton );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMailFolder[] result ) {

		GWTSessionManager.get().getMailbox().setMailFolders( result );
		if ( result != null ) {
			buildTree( GWTSessionManager.get().getMailbox() );
			this.currentTreeNode = getInboxTreeNode();
			GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) GWTUtil.getUserData( this.currentTreeNode ) );
			EventBroker.get().fireFolderSelected( (GWTMailFolder) GWTUtil.getUserData( this.currentTreeNode ) );
			EventBroker.get().fireMessagesChanged();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.events.FoldersReloadListener#onFoldersReload()
	 */
	public void onFoldersReload() {

		this.currentTreeNode = null;
		// changeToolbarButtonStatus( null );
		ServiceProvider.getMailboxService().retrieveFolderTree( this );
	}

	/**
	 * @param mailFolder
	 */
	public void buildTree( GWTMailbox mailbox ) {

		// TreeNode rootNode = this.treeData.getRoot();
		TreeNode newNode = UIFactory.createTreeNode( mailbox );
		this.treeData.setRoot( newNode );
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

	/**
	 * @return
	 */
	private TreeNode getInboxTreeNode() {

		TreeNode[] nodes = this.treeData.getChildren( this.treeData.getRoot() );
		if ( nodes != null ) {
			for (TreeNode node : nodes) {
				GWTMailFolder folder = (GWTMailFolder) GWTUtil.getUserData( node );
				if ( folder.isInbox() ) {
					return (TreeNode) node;
				}
			}
		}
		return null;
	}

	private class MailfolderClickHandler implements NodeClickHandler {

		@Override
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
