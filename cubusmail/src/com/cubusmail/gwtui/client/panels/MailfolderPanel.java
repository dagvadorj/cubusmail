/* MailfolderPanel.java

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
package com.cubusmail.gwtui.client.panels;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.actions.GWTFolderAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.events.FoldersReloadListener;
import com.cubusmail.gwtui.client.events.MessagesChangedListener;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.IGWTFolder;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.util.UIFactory;

/**
 * Panel for mail folder.
 * 
 * @author Juergen Schlierf
 */
public class MailfolderPanel extends Panel implements AsyncCallback<GWTMailFolder[]>, FoldersReloadListener,
		MessagesChangedListener {

	private MailFolderTreePanel treePanel;
	private TreeNode currentTreeNode;

	// Toolbar items
	private ToolbarButton refreshFolderButton;
	private ToolbarButton newFolderButton;
	private ToolbarButton moveFolderButton;
	private ToolbarButton renameFolderButton;
	private ToolbarButton deleteFolderButton;
	private ToolbarButton emptyFolderButton;

	// Context menu items
	private MenuItem newFolderItem;
	private MenuItem moveFolderItem;
	private MenuItem renameFolderItem;
	private MenuItem deleteFolderItem;
	private MenuItem emptyFolderItem;

	// context menu
	private Menu contextMenu;

	/**
	 * 
	 */
	public MailfolderPanel() {

		super();

		setBorder( false );
		setTitle( TextProvider.get().common_mailbox() );
		setLayout( new FitLayout() );
		setCollapsible( true );
		setIconCls( "mailfolder-icon" );

		this.treePanel = new MailFolderTreePanel();
		this.treePanel.addListener( new MailTreePanelListener() );
		add( this.treePanel );

		createActions();
		createToolbar();
		createContextMenu();

		addListener( new PanelListenerAdapter() {

			@Override
			public void onExpand( Panel panel ) {

				ListDetailsPanel listDetailsPanel = (ListDetailsPanel) PanelRegistry.LIST_DETAILS_PANEL.get();
				listDetailsPanel.setMessagesPanelActive();
			}
		} );

		EventBroker.get().addFoldersReloadListener( this );
		EventBroker.get().addMessagesChangedListener( this );
	}

	@Override
	protected void afterRender() {

		super.afterRender();

		EventBroker.get().fireFoldersReload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.FolderChangedListener#onFolderChanged()
	 */
	public void onFoldersReload() {

		PanelRegistry.LEFT_PANEL.mask();
		this.currentTreeNode = null;
		changeToolbarButtonStatus( null );
		ServiceProvider.getMailboxService().retrieveFolderTree( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.MessagesChangedListener#onMessagesChanged
	 * ()
	 */
	public void onMessagesChanged() {

		if ( this.currentTreeNode != null ) {
			final TreeNode selectedNode = this.currentTreeNode;
			ServiceProvider.getMailboxService().getFormattedMessageCount(
					((GWTMailFolder) selectedNode.getUserObject()).getId(), new AsyncCallback<String>() {

						public void onFailure( Throwable caught ) {

							GWTExceptionHandler.handleException( caught );
						}

						public void onSuccess( String result ) {

							selectedNode.setText( result );
						}

					} );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		PanelRegistry.LEFT_PANEL.unmask();
		GWTExceptionHandler.handleException( caught );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMailFolder[] result ) {

		PanelRegistry.LEFT_PANEL.unmask();
		GWTSessionManager.get().getMailbox().setMailFolders( result );
		if ( result != null ) {
			this.treePanel.buildTree( GWTSessionManager.get().getMailbox() );
			this.currentTreeNode = this.treePanel.getInboxTreeNode();
			GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) this.currentTreeNode.getUserObject() );
			EventBroker.get().fireFolderSelected( (GWTMailFolder) this.currentTreeNode.getUserObject() );
			EventBroker.get().fireMessagesChanged();
		}
	}

	/**
	 * Add a new folder
	 * 
	 * @param newMailFolder
	 */
	public void appenChild( GWTMailFolder newMailFolder ) {

		if ( this.currentTreeNode != null ) {
			this.treePanel.appenChild( this.currentTreeNode, newMailFolder );
		}
	}

	private void createActions() {

		this.treePanel.addListener( ((GWTFolderAction) ActionRegistry.NEW_FOLDER.get()).getTreePanelListener() );
		this.treePanel.addListener( ((GWTFolderAction) ActionRegistry.MOVE_FOLDER.get()).getTreePanelListener() );
		this.treePanel.addListener( ((GWTFolderAction) ActionRegistry.RENAME_FOLDER.get()).getTreePanelListener() );
		this.treePanel.addListener( ((GWTFolderAction) ActionRegistry.DELETE_FOLDER.get()).getTreePanelListener() );
		this.treePanel.addListener( ((GWTFolderAction) ActionRegistry.EMPTY_FOLDER.get()).getTreePanelListener() );
		this.treePanel.addListener( new MailDragAndDropListener() );
	}

	/**
	 * create all toolbar buttons.
	 */
	private void createToolbar() {

		Toolbar toolbar = new Toolbar();
		toolbar.addFill();
		this.refreshFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.REFRESH_FOLDER.get() );
		toolbar.addButton( this.refreshFolderButton );

		this.newFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.NEW_FOLDER.get() );
		toolbar.addButton( this.newFolderButton );

		this.moveFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.MOVE_FOLDER.get() );
		toolbar.addButton( this.moveFolderButton );

		this.renameFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.RENAME_FOLDER.get() );
		toolbar.addButton( this.renameFolderButton );

		this.deleteFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.DELETE_FOLDER.get() );
		toolbar.addButton( this.deleteFolderButton );

		this.emptyFolderButton = UIFactory.createToolbarImageButton( ActionRegistry.EMPTY_FOLDER.get() );
		toolbar.addButton( this.emptyFolderButton );

		setTopToolbar( toolbar );
	}

	/**
	 * create all context menu items
	 */
	private void createContextMenu() {

		this.contextMenu = new Menu();

		this.newFolderItem = UIFactory.createMenuItem( ActionRegistry.NEW_FOLDER.get() );
		this.contextMenu.addItem( this.newFolderItem );

		this.moveFolderItem = UIFactory.createMenuItem( ActionRegistry.MOVE_FOLDER.get() );
		this.contextMenu.addItem( this.moveFolderItem );

		this.renameFolderItem = UIFactory.createMenuItem( ActionRegistry.RENAME_FOLDER.get() );
		this.contextMenu.addItem( this.renameFolderItem );

		this.deleteFolderItem = UIFactory.createMenuItem( ActionRegistry.DELETE_FOLDER.get() );
		this.contextMenu.addItem( deleteFolderItem );

		this.emptyFolderItem = UIFactory.createMenuItem( ActionRegistry.EMPTY_FOLDER.get() );
		this.contextMenu.addItem( this.emptyFolderItem );
	}

	/**
	 * Disable or enable toolbar buttons. If mailfolder is null all buttons get
	 * disabled.
	 */
	private void changeToolbarButtonStatus( IGWTFolder mailFolder ) {

		this.newFolderButton.setDisabled( mailFolder != null ? !mailFolder.isCreateSubfolderSupported() : true );
		this.moveFolderButton.setDisabled( mailFolder != null ? !mailFolder.isMoveSupported() : true );
		this.renameFolderButton.setDisabled( mailFolder != null ? !mailFolder.isRenameSupported() : true );
		this.deleteFolderButton.setDisabled( mailFolder != null ? !mailFolder.isDeleteSupported() : true );
		this.emptyFolderButton.setDisabled( mailFolder != null ? !mailFolder.isEmptySupported() : true );
	}

	/**
	 * Disable or enable context menu items. If mailfolder is null all buttons
	 * get disabled.
	 */
	private void changeContextMenuStatus( IGWTFolder mailFolder ) {

		this.newFolderItem.setDisabled( mailFolder != null ? !mailFolder.isCreateSubfolderSupported() : true );
		this.moveFolderItem.setDisabled( mailFolder != null ? !mailFolder.isMoveSupported() : true );
		this.renameFolderItem.setDisabled( mailFolder != null ? !mailFolder.isRenameSupported() : true );
		this.deleteFolderItem.setDisabled( mailFolder != null ? !mailFolder.isDeleteSupported() : true );
		this.emptyFolderItem.setDisabled( mailFolder != null ? !mailFolder.isEmptySupported() : true );
	}

	/**
	 * @return
	 */
	public TreeNode getCurrentTreeNode() {

		return this.currentTreeNode;
	}

	/**
	 * 
	 * @author Juergen Schlierf
	 */
	private class MailTreePanelListener extends TreePanelListenerAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter#onClick
		 * (com.gwtext.client.widgets.tree.TreeNode,
		 * com.gwtext.client.core.EventObject)
		 */
		public void onClick( TreeNode node, EventObject e ) {

			IGWTFolder mailFolder = (IGWTFolder) node.getUserObject();
			if ( !node.equals( currentTreeNode ) ) {
				currentTreeNode = node;
				changeToolbarButtonStatus( mailFolder );
				if ( mailFolder instanceof GWTMailFolder ) {
					GWTSessionManager.get().setCurrentMailFolder( (GWTMailFolder) mailFolder );
					EventBroker.get().fireFolderSelected( (GWTMailFolder) mailFolder );
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seecom.gwtext.client.widgets.tree.event.TreePanelListenerAdapter#
		 * onContextMenu(com.gwtext.client.widgets.tree.TreeNode,
		 * com.gwtext.client.core.EventObject)
		 */
		public void onContextMenu( TreeNode node, EventObject e ) {

			IGWTFolder mailFolder = (IGWTFolder) node.getUserObject();
			changeContextMenuStatus( mailFolder );

			contextMenu.showAt( e.getXY() );
		}
	}
}
