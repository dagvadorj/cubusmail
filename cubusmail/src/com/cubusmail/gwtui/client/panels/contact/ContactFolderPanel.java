/* ContactFolderPanel.java

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
package com.cubusmail.gwtui.client.panels.contact;

import java.util.List;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

import com.cubusmail.gwtui.client.actions.contact.DeleteContactFolderAction;
import com.cubusmail.gwtui.client.actions.contact.NewContactFolderAction;
import com.cubusmail.gwtui.client.actions.contact.RenameContactFolderAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.panels.ListDetailsPanel;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.util.UIFactory;
import com.cubusmail.gwtui.domain.ContactFolder;

/**
 * Panel for the contact folder tree.
 * 
 * @author Juergen Schlierf
 */
public class ContactFolderPanel extends Panel {

	private TreeNode rootNode;
	private TreePanel treePanel;
	private TreeNode currentTreeNode;

	// actions
	private NewContactFolderAction newContactFolderAction;
	private RenameContactFolderAction renameContactFolderAction;
	private DeleteContactFolderAction deleteContactFolderAction;

	// toolbar
	private ToolbarButton newContactFolderButton;
	private ToolbarButton renameContactFolderButton;
	private ToolbarButton deleteContactFolderButton;

	// Context menu items
	private MenuItem newContactFolderItem;
	private MenuItem renameContactFolderItem;
	private MenuItem deleteContactFolderItem;

	// context menu
	private Menu contextMenu;

	public ContactFolderPanel() {

		super();
		setBorder( false );
		setTitle( TextProvider.get().contact_folder_panel_title() );
		setLayout( new FitLayout() );
		setCollapsible( true );
		setIconCls( "contactfolder-icon" );

		this.rootNode = new TreeNode();

		this.treePanel = new TreePanel();
		this.treePanel.addListener( new ContactTreePanelListener() );
		this.treePanel.setRootVisible( false );
		this.treePanel.setRootNode( this.rootNode );
		this.treePanel.setAnimate( false );
		this.treePanel.setAutoScroll( true );
		this.treePanel.setBorder( false );
		this.treePanel.setEnableDrop( true );
		this.treePanel.setDdGroup( "contactsDDGroup" );

		createActions();
		createToolbar();
		createContextMenu();

		this.addListener( new PanelListenerAdapter() {

			@Override
			public void onExpand( Panel panel ) {

				PanelRegistry.LEFT_PANEL.mask();
				ServiceProvider.getUserAccountService().retrieveContactFolders(
						new AsyncCallbackAdapter<List<ContactFolder>>() {

							@Override
							public void onSuccess( List<ContactFolder> result ) {

								GWTSessionManager.get().setContactFolderList( result );
								buildContactFolderList();
								ListDetailsPanel panel = (ListDetailsPanel) PanelRegistry.LIST_DETAILS_PANEL.get();
								panel.setContactsPanelActive();
								PanelRegistry.LEFT_PANEL.unmask();
								if ( GWTSessionManager.get().getCurrentContactFolder() == null ) {
									// set first folder as selected
									GWTSessionManager.get().setCurrentContactFolder( result.get( 0 ) );
									EventBroker.get().fireContactFolderSelected( result.get( 0 ) );
								}
								else {
									EventBroker.get().fireReloadContacts();
								}
							}
						} );
			}
		} );

		add( this.treePanel );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.widgets.Component#afterRender()
	 */
	@Override
	protected void afterRender() {

		super.afterRender();
	}

	/**
	 * create all actions
	 */
	private void createActions() {

		this.newContactFolderAction = new NewContactFolderAction();
		this.renameContactFolderAction = new RenameContactFolderAction();
		this.deleteContactFolderAction = new DeleteContactFolderAction();

		this.treePanel.addListener( this.newContactFolderAction.getTreePanelListener() );
		this.treePanel.addListener( this.renameContactFolderAction.getTreePanelListener() );
		this.treePanel.addListener( this.deleteContactFolderAction.getTreePanelListener() );
		this.treePanel.addListener( new ContactsDragAndDropListener() );
	}

	/**
	 * create all toolbar buttons.
	 */
	private void createToolbar() {

		Toolbar toolbar = new Toolbar();
		toolbar.addFill();
		this.newContactFolderButton = UIFactory.createToolbarImageButton( this.newContactFolderAction );
		toolbar.addButton( this.newContactFolderButton );

		this.renameContactFolderButton = UIFactory.createToolbarImageButton( this.renameContactFolderAction );
		toolbar.addButton( this.renameContactFolderButton );

		this.deleteContactFolderButton = UIFactory.createToolbarImageButton( this.deleteContactFolderAction );
		toolbar.addButton( this.deleteContactFolderButton );

		setTopToolbar( toolbar );
	}

	/**
	 * create all context menu items
	 */
	private void createContextMenu() {

		this.contextMenu = new Menu();

		this.newContactFolderItem = UIFactory.createMenuItem( this.newContactFolderAction );
		this.contextMenu.addItem( this.newContactFolderItem );

		this.renameContactFolderItem = UIFactory.createMenuItem( this.renameContactFolderAction );
		this.contextMenu.addItem( this.renameContactFolderItem );

		this.deleteContactFolderItem = UIFactory.createMenuItem( this.deleteContactFolderAction );
		this.contextMenu.addItem( this.deleteContactFolderItem );
	}

	/**
	 * 
	 */
	private void buildContactFolderList() {

		List<ContactFolder> folderList = GWTSessionManager.get().getContactFolderList();
		if ( folderList != null ) {
			TreeNode rootNode = this.treePanel.getRootNode();
			Node[] children = rootNode.getChildNodes();
			if ( children != null ) {
				for (Node child : children) {
					rootNode.removeChild( child );
				}
			}
			for (ContactFolder contactFolder : folderList) {
				addContactFolder( contactFolder );
			}
		}
	}

	/**
	 * @param folder
	 */
	public void addContactFolder( ContactFolder folder ) {

		TreeNode node = new TreeNode( GWTUtil.getTranslatedFolderName( folder ) );
		node.setUserObject( folder );
		node.setIcon( ImageProvider.CONTACT_FOLDER );
		this.treePanel.getRootNode().appendChild( node );
	}

	private class ContactTreePanelListener extends TreePanelListenerAdapter {

		@Override
		public void onClick( TreeNode node, EventObject e ) {

			ContactFolder contactFolder = (ContactFolder) node.getUserObject();
			if ( !node.equals( currentTreeNode ) ) {
				currentTreeNode = node;
				GWTSessionManager.get().setCurrentContactFolder( contactFolder );
				EventBroker.get().fireContactFolderSelected( contactFolder );
			}
		}

		@Override
		public void onContextMenu( TreeNode node, EventObject e ) {

			contextMenu.showAt( e.getXY() );
		}
	}
}
