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
import com.cubusmail.client.actions.folder.GWTFolderAction;
import com.cubusmail.client.actions.folder.MoveFolderAction;
import com.cubusmail.client.actions.folder.RenameFolderAction;
import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.FoldersReloadListener;
import com.cubusmail.client.events.MessagesChangedListener;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.IGWTFolder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.DisplayNodeType;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.EditCompletionEvent;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderDropEvent;
import com.smartgwt.client.widgets.tree.events.FolderDropHandler;

/**
 * Panel for mail folder.
 * 
 * @author Juergen Schlierf
 */
public class MailfolderCanvas extends SectionStack implements FoldersReloadListener, MessagesChangedListener {

	private TreeNode currentTreeNode;
	private TreeGrid tree;
	private MailfolderEditorExitHandler editorExitHandler;

	// Toolbar items
	private ImgButton refreshFolderButton;
	private ImgButton newFolderButton;
	private ImgButton deleteFolderButton;
	private ImgButton emptyFolderButton;

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
		createContextMenu();

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
		this.tree.setLoadingDataMessage( TextProvider.get().common_mask_text() );
		this.tree.setDisplayNodeType( DisplayNodeType.NULL );
		this.tree.setShowRoot( false );
		this.tree.setSelectionType( SelectionStyle.SINGLE );
		this.tree.setWidth100();
		this.tree.setHeight100();
		this.tree.setAnimateFolders( false );
		this.tree.setShowSortArrow( SortArrow.NONE );
		this.tree.setShowAllRecords( true );
		this.tree.setLoadDataOnDemand( false );
		this.tree.setCanSort( false );
		this.tree.setShowHeader( false );
		this.tree.setAutoFetchData( false );
		this.tree.setDataSource( DataSourceRegistry.MAIL_FOLDER.get() );
		this.tree.setCanEdit( true );
		this.tree.setConfirmCancelEditing( false );
		TreeGridField field = new TreeGridField( "name" );
		this.tree.setFields( field );
		this.tree.setCanReorderRecords( true );
		this.tree.setCanAcceptDroppedRecords( true );
		this.tree.setCanDropOnLeaves( true );
		this.tree.setCanReparentNodes( true );
		this.tree.setDragDataAction( DragDataAction.MOVE );

		// add action handler to the
		this.tree.addSelectionChangedHandler( new MailfolderSelectionChangedHandler() );
		this.tree.addDataArrivedHandler( new DataArrivedHandler() {

			public void onDataArrived( DataArrivedEvent event ) {

				tree.getData().setModelType( TreeModelType.CHILDREN );
				tree.getData().openAll();
				tree.selectRecord( getInboxTreeNode() );
			}
		} );
		this.tree.addEditorEnterHandler( new EditorEnterHandler() {

			public void onEditorEnter( EditorEnterEvent event ) {

				// block unauthorized editing
				TreeNode node = (TreeNode) event.getRecord();
				if ( !GWTUtil.getGwtFolder( node ).isRenameSupported() ) {
					tree.cancelEditing();
				}
				editorExitHandler.setAlreadyDiscarded( false );
			}
		} );
		this.editorExitHandler = new MailfolderEditorExitHandler();
		this.tree.addEditorExitHandler( this.editorExitHandler );

		this.tree.addFolderDropHandler( new MailfolderDropHandler() );
	}

	/**
	 * @param section
	 */
	private void createToolbar( SectionStackSection section ) {

		this.refreshFolderButton = UIFactory.createImgButton( ActionRegistry.REFRESH_FOLDER );
		this.newFolderButton = UIFactory.createImgButton( ActionRegistry.NEW_FOLDER );
		this.deleteFolderButton = UIFactory.createImgButton( ActionRegistry.DELETE_FOLDER );
		this.emptyFolderButton = UIFactory.createImgButton( ActionRegistry.EMPTY_FOLDER );

		section.setControls( this.refreshFolderButton, this.newFolderButton, this.deleteFolderButton,
				this.emptyFolderButton );
	}

	/**
	 * 
	 */
	private void createContextMenu() {

		Menu contextMenu = new Menu();

		MenuItem createItem = UIFactory.createMenuItem( ActionRegistry.NEW_FOLDER );
		createItem.setEnableIfCondition( new MenuItemIfFunction() {

			public boolean execute( Canvas target, Menu menu, MenuItem item ) {

				IGWTFolder folder = GWTUtil.getGwtFolder( (TreeNode) tree.getSelectedRecord() );
				return folder.isCreateSubfolderSupported();
			}
		} );

		MenuItem deleteItem = UIFactory.createMenuItem( ActionRegistry.DELETE_FOLDER );
		deleteItem.setEnableIfCondition( new MenuItemIfFunction() {

			public boolean execute( Canvas target, Menu menu, MenuItem item ) {

				IGWTFolder folder = GWTUtil.getGwtFolder( (TreeNode) tree.getSelectedRecord() );
				return folder.isDeleteSupported();
			}
		} );

		MenuItem emptyItem = UIFactory.createMenuItem( ActionRegistry.EMPTY_FOLDER );
		emptyItem.setEnableIfCondition( new MenuItemIfFunction() {

			public boolean execute( Canvas target, Menu menu, MenuItem item ) {

				IGWTFolder folder = GWTUtil.getGwtFolder( (TreeNode) tree.getSelectedRecord() );
				return folder.isEmptySupported();
			}
		} );

		contextMenu.setItems( createItem, deleteItem, emptyItem );

		this.tree.setContextMenu( contextMenu );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.events.FoldersReloadListener#onFoldersReload()
	 */
	public void onFoldersReload() {

		this.currentTreeNode = null;
		changeToolbarButtonStatus( null );
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
					((GWTMailFolder) GWTUtil.getGwtFolder( selectedNode )).getId(), new AsyncCallback<String>() {

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
			if ( GWTUtil.getGwtFolder( nodes[0] ) instanceof GWTMailbox ) {
				nodes = treeData.getChildren( nodes[0] );
			}

			for (TreeNode node : nodes) {
				GWTMailFolder folder = (GWTMailFolder) GWTUtil.getGwtFolder( node );
				if ( folder.isInbox() ) {
					return (TreeNode) node;
				}
			}
		}
		return null;
	}

	/**
	 * Disable or enable toolbar buttons. If mailfolder is null all buttons get
	 * disabled.
	 */
	private void changeToolbarButtonStatus( IGWTFolder mailFolder ) {

		this.newFolderButton.setDisabled( mailFolder != null ? !mailFolder.isCreateSubfolderSupported() : true );
		this.deleteFolderButton.setDisabled( mailFolder != null ? !mailFolder.isDeleteSupported() : true );
		this.emptyFolderButton.setDisabled( mailFolder != null ? !mailFolder.isEmptySupported() : true );
	}

	/**
	 * 
	 * @author Juergen Schlierf
	 */
	private class MailfolderSelectionChangedHandler implements SelectionChangedHandler {

		public void onSelectionChanged( SelectionEvent event ) {

			TreeNode selectedNode = (TreeNode) event.getRecord();
			IGWTFolder mailFolder = GWTUtil.getGwtFolder( selectedNode );
			if ( !selectedNode.equals( currentTreeNode ) ) {
				currentTreeNode = selectedNode;
				prepareActions( selectedNode );
				changeToolbarButtonStatus( mailFolder );
				if ( mailFolder instanceof GWTMailFolder ) {
					GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) mailFolder );
					EventBroker.get().fireFolderSelected( (GWTMailFolder) mailFolder );
				}
			}
		}

		private void prepareActions( TreeNode selectedTreeNode ) {

			ActionRegistry.NEW_FOLDER.get( GWTFolderAction.class ).setSelectedTreeNode( selectedTreeNode );
			ActionRegistry.NEW_FOLDER.get( GWTFolderAction.class ).setTree( tree );
			ActionRegistry.RENAME_FOLDER.get( GWTFolderAction.class ).setSelectedTreeNode( selectedTreeNode );
			ActionRegistry.RENAME_FOLDER.get( GWTFolderAction.class ).setTree( tree );
			ActionRegistry.DELETE_FOLDER.get( GWTFolderAction.class ).setSelectedTreeNode( selectedTreeNode );
			ActionRegistry.DELETE_FOLDER.get( GWTFolderAction.class ).setTree( tree );
			ActionRegistry.EMPTY_FOLDER.get( GWTFolderAction.class ).setSelectedTreeNode( selectedTreeNode );
			ActionRegistry.EMPTY_FOLDER.get( GWTFolderAction.class ).setTree( tree );
			ActionRegistry.MOVE_FOLDER.get( GWTFolderAction.class ).setSelectedTreeNode( selectedTreeNode );
			ActionRegistry.MOVE_FOLDER.get( GWTFolderAction.class ).setTree( tree );
		}
	}

	/**
	 * Exit handler for renaming mail folders.
	 * 
	 * @author Juergen Schlierf
	 */
	private class MailfolderEditorExitHandler implements EditorExitHandler {

		private boolean alreadyDiscarded = false;

		public void onEditorExit( EditorExitEvent event ) {

			event.isCancelled();
			event.getEditCompletionEvent();
			if ( !alreadyDiscarded ) {
				if ( checkInput( event ) ) {
					String newName = event.getNewValue() != null ? event.getNewValue().toString() : null;
					TreeNode renamedNode = (TreeNode) event.getRecord();
					ActionRegistry.RENAME_FOLDER.get( RenameFolderAction.class ).setRenamedNode( renamedNode );
					ActionRegistry.RENAME_FOLDER.get( RenameFolderAction.class ).setNewName( newName );
					ActionRegistry.RENAME_FOLDER.execute();
				}
			}
		}

		/**
		 * Is new name empty or the same as before?
		 * 
		 * @param event
		 * @return
		 */
		private boolean checkInput( EditorExitEvent event ) {

			// only completion event ENTER is accepted
			if ( !EditCompletionEvent.ENTER.equals( event.getEditCompletionEvent() ) ) {
				return false;
			}
			String newName = event.getNewValue() != null ? event.getNewValue().toString() : null;
			String oldName = ((TreeNode) event.getRecord()).getName();
			if ( !GWTUtil.hasText( newName ) || newName.equalsIgnoreCase( oldName ) ) {
				this.alreadyDiscarded = true;
				tree.discardAllEdits();
				return false;
			}

			return true;
		}

		public void setAlreadyDiscarded( boolean alreadyDiscarded ) {

			this.alreadyDiscarded = alreadyDiscarded;
		}
	}

	/**
	 * 
	 * 
	 * @author Juergen Schlierf
	 */
	private class MailfolderDropHandler implements FolderDropHandler {

		public void onFolderDrop( FolderDropEvent event ) {

			if ( event.getNodes() != null && event.getNodes().length > 0 ) {
				TreeNode sourceNode = event.getNodes()[0];
				IGWTFolder sourceFolder = GWTUtil.getGwtFolder( sourceNode );
				if ( sourceFolder.isMoveSupported() ) {
					TreeNode targetNode = event.getFolder();
					IGWTFolder targetFolder = GWTUtil.getGwtFolder( targetNode );
					if ( !isSame( sourceFolder.getParent(), targetFolder ) ) {
						ActionRegistry.MOVE_FOLDER.get( MoveFolderAction.class ).setSourceNode( sourceNode );
						ActionRegistry.MOVE_FOLDER.get( MoveFolderAction.class ).setTargetNode( targetNode );
						ActionRegistry.MOVE_FOLDER.execute();
					}
				}
			}
		}

		private boolean isSame( IGWTFolder folder1, IGWTFolder folder2 ) {

			return folder1.getId().equals( folder2.getId() );
		}
	}
}
