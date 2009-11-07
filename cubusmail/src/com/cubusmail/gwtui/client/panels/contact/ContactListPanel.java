/* ContactListPanel.java

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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.SortState;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarItem;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridRowListener;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtextux.client.data.GWTProxy;
import com.gwtextux.client.widgets.grid.plugins.GridSearchPlugin;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.events.ContactFolderSelectedListener;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.events.ReloadContactsListener;
import com.cubusmail.gwtui.client.panels.ContactReadingPanelPanel;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.panels.ToolbarManager;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.util.UIFactory;
import com.cubusmail.gwtui.client.widgets.MessageQuickSearchPlugin;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactFolder;
import com.cubusmail.gwtui.domain.ContactListFields;

/**
 * Panel for the contact list.
 * 
 * @author Juergen Schlierf
 */
public class ContactListPanel extends Panel implements ContactFolderSelectedListener, ReloadContactsListener {

	public static final int TYPE_FOR_PANEL = 0;
	public static final int TYPE_FOR_WINDOW = 1;

	private static final int CONTACT_LOAD_DELAY = 400;

	private GridPanel gridPanel;
	private Store store;
	private ContactListProxy contactListProxy;

	private ContactLoadTimer contactLoadTimer;
	private ToolbarTextItem folderNameItem;

	// context menu
	private Menu contextMenu;

	private int type;

	private ComboBox contactListCombo;
	private Store contactListComboStore;
	private List<ContactFolder> contactFolderList;

	private static final RecordDef RECORD_DEF = new RecordDef( new FieldDef[] {
			new StringFieldDef( ContactListFields.ID.name() ),
			new StringFieldDef( ContactListFields.DISPLAY_NAME.name() ),
			new StringFieldDef( ContactListFields.EMAIL.name() ),
			new StringFieldDef( ContactListFields.COMPANY.name() ),
			new StringFieldDef( ContactListFields.INTERNET_ADDRESS.name() ) } );

	private static ColumnModel COLUMN_MODEL = new ColumnModel( new ColumnConfig[] {
			new ColumnConfig( TextProvider.get().contact_list_panel_col_name(), ContactListFields.DISPLAY_NAME.name(),
					150, true ),
			new ColumnConfig( TextProvider.get().contact_list_panel_col_email(), ContactListFields.EMAIL.name(), 150,
					true ),
			new ColumnConfig( TextProvider.get().contact_list_panel_col_company(), ContactListFields.COMPANY.name(),
					150, true ) } );

	/**
	 * 
	 */
	public ContactListPanel( int type ) {

		this.type = type;

		setLayout( new FitLayout() );
		setBorder( false );

		this.contactListProxy = new ContactListProxy();
		this.store = new Store( this.contactListProxy, new ArrayReader( ContactListFields.ID.ordinal(), RECORD_DEF ),
				true );
		this.store.setSortInfo( new SortState( ContactListFields.DISPLAY_NAME.name(), SortDir.ASC ) );

		this.gridPanel = new GridPanel( this.store, COLUMN_MODEL );

		GridSearchPlugin plugin = new GridSearchPlugin( GridSearchPlugin.TOP );
		plugin.setMode( MessageQuickSearchPlugin.LOCAL );
		this.gridPanel.setTopToolbar( createToolbar() );
		this.gridPanel.addPlugin( plugin );

		this.gridPanel.setStripeRows( true );
		this.gridPanel.setLoadMask( true );
		this.gridPanel.getSelectionModel().addListener( new ContactGridRowSelectionListener() );

		this.gridPanel.getView().setForceFit( true );
		this.gridPanel.getView().setAutoFill( true );
		this.gridPanel.getView().setEnableRowBody( true );
		this.gridPanel.getView().setEmptyText( TextProvider.get().contact_list_panel_no_contacts() );
		if ( type == TYPE_FOR_PANEL ) {
			this.gridPanel.addGridRowListener( new ContextMenuListener() );
		}
		this.gridPanel.addGridListener( new KeyGridListener() );
		this.gridPanel.setEnableDragDrop( true );
		this.gridPanel.setDdGroup( "contactsDDGroup" );

		add( this.gridPanel );

		addListener( new ContactListPanelListener() );

		this.contactLoadTimer = new ContactLoadTimer();

		createContextMenu();

		EventBroker.get().addContactFolderSelectedListener( this );
		EventBroker.get().addReloadContactsListener( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.widgets.Component#afterRender()
	 */
	@Override
	protected void afterRender() {

		if ( this.type == TYPE_FOR_WINDOW ) {
			ServiceProvider.getUserAccountService().retrieveContactFolders(
					new AsyncCallbackAdapter<List<ContactFolder>>() {

						@Override
						public void onSuccess( List<ContactFolder> result ) {

							contactFolderList = result;
							fillContactListCombobox( result );
							contactListCombo.setValue( Long.toString( result.get( 0 ).getId() ) );
							onFolderSelected( result.get( 0 ) );
						}
					} );
		}
	}

	/**
	 * @return
	 */
	private Toolbar createToolbar() {

		Toolbar toolbar = new Toolbar();

		if ( this.type == TYPE_FOR_WINDOW ) {
			this.contactListCombo = new ComboBox();
			this.contactListCombo.setForceSelection( true );
			this.contactListCombo.setDisplayField( "text" );
			this.contactListCombo.setValueField( "value" );
			this.contactListCombo.setEditable( false );
			this.contactListCombo.setForceSelection( true );
			this.contactListCombo.addListener( new ComboBoxListenerAdapter() {

				@Override
				public void onSelect( ComboBox comboBox, Record record, int index ) {

					String id = record.getAsString( "value" );
					onFolderSelected( getContactFolderById( id ) );
				}
			} );
			toolbar.addItem( new ToolbarItem( this.contactListCombo.getElement() ) );
		}
		else {
			this.folderNameItem = new ToolbarTextItem( "" );
			toolbar.addItem( this.folderNameItem );
		}

		toolbar.addFill();

		return toolbar;
	}

	/**
	 * 
	 */
	private void createContextMenu() {

		this.contextMenu = new Menu();

		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.EDIT_CONTACT.get() ) );
		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.DELETE_CONTACT.get() ) );
	}

	/**
	 * 
	 */
	private void fillContactListCombobox( List<ContactFolder> folderList ) {

		if ( folderList != null && folderList.size() > 0 ) {
			String[][] array = new String[folderList.size()][2];
			for (int i = 0; i < folderList.size(); i++) {
				array[i][0] = Long.toString( folderList.get( i ).getId() );
				array[i][1] = folderList.get( i ).getFolderName();
			}
			this.contactListComboStore = new SimpleStore( new String[] { "value", "text" }, array );
			this.contactListComboStore.setAutoLoad( true );
			this.contactListCombo.setStore( contactListComboStore );
			this.contactListComboStore.load();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.cubusmail.gwtui.client.events.ContactFolderSelectedListener#
	 * onFolderSelected(com.cubusmail.gwtui.domain.ContactFolder)
	 */
	public void onFolderSelected( ContactFolder contactFolder ) {

		if ( this.folderNameItem != null ) {
			this.folderNameItem.setText( "<b class=\"contactlist-contactfolder-name\">"
					+ GWTUtil.getTranslatedFolderName( contactFolder ) + "</b>" );
		}
		this.gridPanel.removeAll();
		this.contactListProxy.setCurrentFolder( contactFolder );
		this.store.load();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.ReloadContactsListener#onReloadContacts
	 * ()
	 */
	public void onReloadContacts() {

		this.gridPanel.removeAll();
		this.store.load();
	}

	private class ContactGridRowSelectionListener extends RowSelectionListenerAdapter {

		@Override
		public void onSelectionChange( RowSelectionModel sm ) {

			if ( sm.getCount() > 0 ) {
				ToolbarManager.get().disableContactToolbar( false );
			}
			else {
				ToolbarManager.get().disableContactToolbar( true );
				PanelRegistry.CONTACT_READING_PANE.get( ContactReadingPanelPanel.class ).clearContent();
			}

			if ( sm.getCount() == 1 ) {
				Long id = Long.valueOf( sm.getSelected().getAsInteger( ContactListFields.ID.name() ) );

				contactLoadTimer.cancel();
				contactLoadTimer.setContactId( id );
				contactLoadTimer.schedule( CONTACT_LOAD_DELAY );
			}
		}
	}

	/**
	 * Delay timer for contact loading.
	 * 
	 * @author Juergen Schlierf
	 */
	private class ContactLoadTimer extends Timer {

		private Long contactId;

		/**
		 * @param contactId
		 *            The contactId to set.
		 */
		public void setContactId( Long contactId ) {

			this.contactId = contactId;
		}

		@Override
		public void run() {

			PanelRegistry.CONTACT_READING_PANE.mask();
			ServiceProvider.getUserAccountService().retrieveContact( this.contactId,
					new AsyncCallbackAdapter<Contact>() {

						@Override
						public void onSuccess( Contact result ) {

							GWTSessionManager.get().setCurrentContact( result );
							EventBroker.get().fireContactSelected( result );
							PanelRegistry.CONTACT_READING_PANE.unmask();
						}

						@Override
						public void onFailure( Throwable caught ) {

							super.onFailure( caught );
							PanelRegistry.CONTACT_READING_PANE.unmask();
						}
					} );
		}
	}

	private class ContactListPanelListener extends PanelListenerAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.gwtext.client.widgets.event.PanelListenerAdapter#onExpand(com
		 * .gwtext.client.widgets.Panel)
		 */
		@Override
		public void onExpand( Panel panel ) {

			PanelRegistry.CONTACT_LIST_PANEL.mask();
			store.load();
		}
	}

	/**
	 * Contact list proxy.
	 * 
	 * @author Juergen Schlierf
	 */
	private class ContactListProxy extends GWTProxy {

		private ContactFolder currentFolder;

		public void load( int start, int limit, String sort, String dir, final JavaScriptObject o, UrlParam[] baseParams ) {

			ServiceProvider.getUserAccountService().retrieveContactArray( this.currentFolder, sort, dir,
					new AsyncCallback<String[][]>() {

						public void onFailure( Throwable caught ) {

							loadResponse( o, false, 0, (JavaScriptObject) null );
							PanelRegistry.CONTACT_LIST_PANEL.unmask();
						}

						public void onSuccess( String[][] result ) {

							loadResponse( o, true, result.length, result );
							PanelRegistry.CONTACT_LIST_PANEL.unmask();
						}
					} );
		}

		/**
		 * @param currentFolder
		 *            The currentFolder to set.
		 */
		public void setCurrentFolder( ContactFolder currentFolder ) {

			this.currentFolder = currentFolder;
		}
	}

	/**
	 * Listener for key events (e.g. DEL key).
	 * 
	 * @author Juergen Schlierf
	 */
	private class KeyGridListener extends GridListenerAdapter {

		@Override
		public void onKeyDown( EventObject e ) {

			if ( e.getKey() == EventObject.DELETE ) {
				ActionRegistry.DELETE_CONTACT.get().execute();
			}
		}
	}

	/**
	 * Listener for context menu.
	 * 
	 * @author Juergen Schlierf
	 */
	private class ContextMenuListener extends GridRowListenerAdapter {

		@Override
		public void onRowContextMenu( GridPanel grid, int rowIndex, EventObject e ) {

			contextMenu.showAt( e.getXY() );
			e.stopEvent();
		}

		@Override
		public void onRowDblClick( GridPanel grid, int rowIndex, EventObject e ) {

			ActionRegistry.EDIT_CONTACT.execute();
		}
	}

	public GridPanel getGridPanel() {

		return this.gridPanel;
	}

	/**
	 * @return Returns the store.
	 */
	public Store getStore() {

		return this.store;
	}

	/**
	 * @param action
	 */
	public void registerAction( BaseGridAction action ) {

		this.gridPanel.getSelectionModel().addListener( action.getRowSelectionListener() );
	}

	/**
	 * @param listener
	 * @see com.gwtext.client.widgets.grid.GridPanel#addGridRowListener(com.gwtext.client.widgets.grid.event.GridRowListener)
	 */
	public void addGridRowListener( GridRowListener listener ) {

		this.gridPanel.addGridRowListener( listener );
	}

	/**
	 * @param id
	 * @return
	 */
	private ContactFolder getContactFolderById( String id ) {

		for (ContactFolder folder : this.contactFolderList) {
			if ( id.equals( Long.toString( folder.getId() ) ) ) {
				return folder;
			}
		}
		return null;
	}
}
