/* MessageListPanel.java

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

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.events.FolderSelectedListener;
import com.cubusmail.gwtui.client.events.MessageLoadedListener;
import com.cubusmail.gwtui.client.events.MessagesReloadListener;
import com.cubusmail.gwtui.client.events.PreferencesChangedListener;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.stores.StoreProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.widgets.MessageQuickSearchPlugin;
import com.cubusmail.gwtui.client.windows.WindowRegistry;
import com.cubusmail.gwtui.domain.GWTMailConstants;
import com.cubusmail.gwtui.domain.MessageListFields;
import com.cubusmail.gwtui.domain.Preferences;

/**
 * Contains the table of the messages.
 * 
 * @author Juergen Schlierf
 */
public class MessageListPanel extends Panel implements FolderSelectedListener, MessagesReloadListener,
		MessageLoadedListener, PreferencesChangedListener, AsyncCallback<GWTMessage[]> {

	private static final int MESSAGE_LOAD_DELAY = 400;

	private static final String IMAGE_TAG_ATTACHMENT = "<img src=\"" + ImageProvider.MSG_ATTACHMENT + "\"/>";
	private static final String IMAGE_TAG_UNREAD = "<img src=\"" + ImageProvider.MSG_STATUS_UNREAD + "\"/>";
	private static final String IMAGE_TAG_DELETED = "<img src=\"" + ImageProvider.MSG_STATUS_DELETED + "\"/>";
	private static final String IMAGE_TAG_ANSWERED = "<img src=\"" + ImageProvider.MSG_STATUS_ANSWERED + "\"/>";
	private static final String IMAGE_TAG_DRAFT = "<img src=\"" + ImageProvider.MSG_STATUS_DRAFT + "\"/>";
	private static final String IMAGE_TAG_BLANK = "<img src=\"" + ImageProvider.FILE_BLANK + "\"/>";
	private static final String IMAGE_TAG_PRIORITY_HIGH = "<img src=\"" + ImageProvider.PRIORITY_HIGH + "\"/>";
	private static final String IMAGE_TAG_PRIORITY_LOW = "<img src=\"" + ImageProvider.PRIORITY_LOW + "\"/>";

	private GridPanel gridPanel;
	private ColumnModel columnModel;
	private ToolbarTextItem folderNameItem;
	private Panel gridPanelWrapper;
	private ExtendedSearchPanel extendedSearchPanel;
	private PagingToolbar pagingToolbar;

	private MessageQuickSearchPlugin quickSearch = null;
	private Toolbar searchToolbar;
	private boolean extendedSearchMode;
	private boolean extendedSearchPerformed;

	private MessagePreviewTimer messageLoadTimer;

	private static Renderer flagRenderer = new Renderer() {

		public String render( Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
				Store store ) {

			if ( record.getAsBoolean( MessageListFields.DELETED_FLAG.name() ) ) {
				return IMAGE_TAG_DELETED;
			}
			else if ( record.getAsBoolean( MessageListFields.ANSWERED_FLAG.name() ) ) {
				return IMAGE_TAG_ANSWERED;
			}
			else if ( record.getAsBoolean( MessageListFields.DRAFT_FLAG.name() ) ) {
				return IMAGE_TAG_DRAFT;
			}
			else {
				if ( !record.getAsBoolean( MessageListFields.READ_FLAG.name() ) ) {
					return IMAGE_TAG_UNREAD;
				}
			}

			return null;
		}
	};

	private static Renderer priorityRenderer = new Renderer() {

		public String render( Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
				Store store ) {

			int priority = record.getAsInteger( MessageListFields.PRIORITY.name() );
			if ( priority == GWTMailConstants.PRIORITY_NONE ) {
				return null;
			}
			else if ( priority == GWTMailConstants.PRIORITY_VERY_LOW || priority == GWTMailConstants.PRIORITY_LOW ) {
				return IMAGE_TAG_PRIORITY_LOW;
			}
			else if ( priority == GWTMailConstants.PRIORITY_VERY_HIGH
					|| priority == GWTMailConstants.PRIORITY_VERY_HIGH ) {
				return IMAGE_TAG_PRIORITY_HIGH;
			}

			return null;
		}
	};

	private static Renderer attachmentRenderer = new Renderer() {

		public String render( Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
				Store store ) {

			boolean hasAttachment = record.getAsBoolean( MessageListFields.ATTACHMENT_FLAG.name() );

			if ( hasAttachment ) {
				return IMAGE_TAG_ATTACHMENT;
			}

			return null;
		}
	};

	private static Renderer commonRenderer = new Renderer() {

		public String render( Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
				Store store ) {

			if ( value != null ) {
				setStyleForRecord( cellMetadata, record );

				return value.toString();
			}

			return null;
		}
	};

	/**
	 * @param cellMetadata
	 * @param record
	 */
	private static void setStyleForRecord( CellMetadata cellMetadata, Record record ) {

		boolean deleted = record.getAsBoolean( MessageListFields.DELETED_FLAG.name() );
		boolean read = record.getAsBoolean( MessageListFields.READ_FLAG.name() );
		if ( deleted ) {
			if ( !read ) {
				cellMetadata.setHtmlAttribute( GWTUtil.STYLE_BOLD_LINE_THROUGH );
			}
			else {
				cellMetadata.setHtmlAttribute( GWTUtil.STYLE_LINE_THROUGH );
			}
		}
		else if ( !read ) {
			cellMetadata.setHtmlAttribute( GWTUtil.STYLE_BOLD );
		}
	}

	private static ColumnConfig[] COLUMN_CONFIG = new ColumnConfig[] {
			new ColumnConfig( IMAGE_TAG_BLANK, MessageListFields.READ_FLAG.name(), 25, false, flagRenderer,
					MessageListFields.READ_FLAG.name() ),
			new ColumnConfig( IMAGE_TAG_PRIORITY_HIGH, MessageListFields.PRIORITY.name(), 25, false, priorityRenderer,
					MessageListFields.PRIORITY.name() ),
			new ColumnConfig( IMAGE_TAG_ATTACHMENT, MessageListFields.ATTACHMENT_FLAG.name(), 25, true,
					attachmentRenderer, MessageListFields.ATTACHMENT_FLAG.name() ),
			new ColumnConfig( TextProvider.get().grid_messages_subject(), MessageListFields.SUBJECT.name(), 500, true,
					commonRenderer, MessageListFields.SUBJECT.name() ),
			new ColumnConfig( TextProvider.get().grid_messages_from(), MessageListFields.FROM.name(), 200, true,
					commonRenderer, MessageListFields.FROM.name() ),
			new ColumnConfig( TextProvider.get().grid_messages_to(), MessageListFields.TO.name(), 200, true,
					commonRenderer, MessageListFields.TO.name() ),
			new ColumnConfig( TextProvider.get().grid_messages_date(), MessageListFields.DATE.name(), 120, true,
					commonRenderer, MessageListFields.DATE.name() ),
			new ColumnConfig( TextProvider.get().grid_messages_size(), MessageListFields.SIZE.name(), 80, true,
					commonRenderer, MessageListFields.SIZE.name() ) };

	/**
	 * 
	 */
	public MessageListPanel() {

		setBorder( false );
		setLayout( new FitLayout() );

		this.columnModel = createColumnModel();

		this.gridPanelWrapper = new Panel();
		this.gridPanelWrapper.setBorder( false );
		this.gridPanelWrapper.setFrame( false );
		this.gridPanelWrapper.setLayout( new BorderLayout() );

		this.gridPanel = new GridPanel();
		this.gridPanel.setBorder( false );
		this.gridPanel.setFrame( false );
		this.gridPanel.setColumnModel( this.columnModel );
		this.gridPanel.setStripeRows( true );
		this.gridPanel.getSelectionModel().addListener( new MessageGridRowSelectionListener() );
		this.gridPanel.addGridRowListener( new OpenMessageGridRowSelectionListener() );
		this.gridPanel.setLoadMask( true );
		this.gridPanel.setStore( getMessageStore() );
		this.gridPanel.setEnableDragDrop( true );
		this.gridPanel.setDdGroup( "messagesDDGroup" );

		this.pagingToolbar = new PagingToolbar( getMessageStore() );
		this.pagingToolbar.setPageSize( GWTSessionManager.get().getPreferences().getPageCount() );
		this.pagingToolbar.setDisplayInfo( true );
		this.pagingToolbar.setDisplayMsg( TextProvider.get().grid_messages_count().replace( '[', '{' ).replace( ']',
				'}' ) );
		this.pagingToolbar.setEmptyMsg( TextProvider.get().grid_messages_no_messages() );
		this.pagingToolbar.setBeforePageText( TextProvider.get().grid_messages_page() );
		this.pagingToolbar.setAfterPageText( TextProvider.get().grid_messages_after_page().replace( '[', '{' ).replace(
				']', '}' ) );
		this.pagingToolbar.bind( getMessageStore() );
		this.gridPanel.setBottomToolbar( this.pagingToolbar );

		this.folderNameItem = new ToolbarTextItem( "" );

		this.searchToolbar = new Toolbar();
		this.searchToolbar.addSpacer();
		this.searchToolbar.addItem( this.folderNameItem );
		this.searchToolbar.addFill();

		ToolbarButton extendedSearch = new ToolbarButton( TextProvider.get().grid_messages_extended_search() );
		extendedSearch.setEnableToggle( true );
		extendedSearch.addListener( new ButtonListenerAdapter() {

			public void onToggle( Button button, boolean pressed ) {

				setExtendedSearchMode( pressed );
			}
		} );

		searchToolbar.addButton( extendedSearch );
		searchToolbar.addSeparator();

		this.quickSearch = new MessageQuickSearchPlugin( MessageQuickSearchPlugin.TOP );
		this.quickSearch.setMode( MessageQuickSearchPlugin.REMOTE );
		this.quickSearch.setSearchText( TextProvider.get().grid_messages_search() );
		this.gridPanel.addPlugin( quickSearch );
		this.gridPanel.setTopToolbar( searchToolbar );
		String[] disabled = new String[4];
		disabled[0] = MessageListFields.READ_FLAG.name();
		disabled[1] = MessageListFields.PRIORITY.name();
		disabled[2] = MessageListFields.SIZE.name();
		disabled[3] = MessageListFields.ATTACHMENT_FLAG.name();

		JavaScriptObjectHelper.setAttribute( quickSearch.getJsObj(), "disableIndexes", disabled );
		JavaScriptObjectHelper.setAttribute( quickSearch.getJsObj(), "searchTipText", TextProvider.get()
				.grid_messages_search_tooltip() );

		this.gridPanel.getView().setAutoFill( true );
		this.gridPanel.getView().setForceFit( true );
		this.gridPanel.getView().setEnableRowBody( true );
		this.gridPanel.getView().setEmptyText( TextProvider.get().grid_messages_no_messages() );

		this.extendedSearchPanel = new ExtendedSearchPanel( this.extendedSearchMode );
		this.extendedSearchPanel.addSearchButtonListener( new ButtonListenerAdapter() {

			public void onClick( Button button, EventObject e ) {

				startExtendedSearch( extendedSearchPanel.getParams() );
			}
		} );
		this.extendedSearchPanel.addKeyListener( new KeyListener() {

			public void onKey( int key, EventObject e ) {

				startExtendedSearch( extendedSearchPanel.getParams() );
			}
		} );

		this.gridPanelWrapper.add( this.extendedSearchPanel, new BorderLayoutData( RegionPosition.NORTH ) );
		this.gridPanelWrapper.add( this.gridPanel, new BorderLayoutData( RegionPosition.CENTER ) );

		add( this.gridPanelWrapper );

		this.messageLoadTimer = new MessagePreviewTimer();

		getMessageStore().addStoreListener( new StoreListenerAdapter() {

			@Override
			public void onLoad( Store store, Record[] records ) {

				GWTMailFolder currentFolder = GWTSessionManager.get().getCurrentMailFolder();
				setColumnHidden( MessageListFields.TO.name(), !currentFolder.isSent() );
				setColumnHidden( MessageListFields.FROM.name(), currentFolder.isSent() );
			}
		} );

		EventBroker.get().addFolderSelectedListener( this );
		EventBroker.get().addMessagesReloadListener( this );
		EventBroker.get().addPreferencesChangedListener( this );
		EventBroker.get().addMessageLoadedListener( this );
	}

	/**
	 * @return Returns the extendedSearchMode.
	 */
	public boolean isExtendedSearchMode() {

		return this.extendedSearchMode;
	}

	/**
	 * @return Returns the gridPanel.
	 */
	public GridPanel getGridPanel() {

		return this.gridPanel;
	}

	/**
	 * @param extendedSearchMode
	 *            The extendedSearchMode to set.
	 */
	public void setExtendedSearchMode( boolean extendedSearchMode ) {

		this.extendedSearchMode = extendedSearchMode;
		this.quickSearch.disableSearchField( this.extendedSearchMode );
		if ( this.extendedSearchMode ) {
			this.quickSearch.clearSearchField();
		}
		else {
			this.extendedSearchPanel.clearFields();
			if ( this.extendedSearchPerformed ) {
				startExtendedSearch( null );
			}
			this.extendedSearchPerformed = false;
		}
		this.extendedSearchPanel.setVisible( this.extendedSearchMode );

		doLayout( true );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.FolderSelectedListener#onFolderSelected
	 * (com.cubusmail.gwtui.client.model.GWTMailFolder)
	 */
	public void onFolderSelected( GWTMailFolder mailFolder ) {

		this.folderNameItem.setText( "<b class=\"messagelist-mailfolder-name\">" + mailFolder.getName() + "</b>" );
		this.gridPanel.removeAll();
		this.quickSearch.clearSearchField();
		if ( this.extendedSearchMode ) {
			getMessageStore().setBaseParams( this.extendedSearchPanel.getParams() );
		}
		else {
			getMessageStore().setBaseParams( null );
		}
		StoreProvider.get().getMessageListProxy().setCurrentFolderId( mailFolder.getId() );
		getMessageStore().load();
	}

	/**
	 * @param params
	 */
	private void startExtendedSearch( UrlParam[] params ) {

		this.gridPanel.removeAll();
		this.quickSearch.clearSearchField();
		getMessageStore().setBaseParams( params );
		getMessageStore().load();
		this.extendedSearchPerformed = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.MessageLoadedListener#onMessageLoaded
	 * (com.cubusmail.gwtui.client.model.GWTMessage)
	 */
	public void onMessageLoaded( GWTMessage message ) {

		Record msgRecord = getMessageStore().getById( String.valueOf( message.getId() ) );
		if ( msgRecord != null ) {
			msgRecord.set( MessageListFields.READ_FLAG.name(), message.isRead() );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.MessagesReloadListener#onMessagesReload
	 * ()
	 */
	public void onMessagesReload() {

		this.gridPanel.removeAll();
		getMessageStore().reload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		PanelRegistry.MESSAGE_LIST_PANEL.unmask();
		GWTExceptionHandler.handleException( caught );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMessage[] messages ) {

		if ( messages != null ) {
			getMessageStore().removeAll();
			for (int i = 0; i < messages.length; i++) {
				Record record = StoreProvider.MESSAGE_LIST_RECORD_DEF.createRecord( messages[i].getRowData() );
				getMessageStore().add( record );
			}
			this.gridPanel.reconfigure( getMessageStore(), new ColumnModel( COLUMN_CONFIG ) );
		}
		PanelRegistry.MESSAGE_LIST_PANEL.unmask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.cubusmail.gwtui.client.events.PreferencesChangedListener#
	 * onPreferencesChanged(com.cubusmail.gwtui.domain.Preferences)
	 */
	public void onPreferencesChanged( Preferences preferences ) {

		this.pagingToolbar.setPageSize( preferences.getPageCount() );
	}

	/**
	 * @param action
	 */
	public void registerMessageAction( BaseGridAction action ) {

		this.gridPanel.getSelectionModel().addListener( action.getRowSelectionListener() );
	}

	/**
	 * @return Returns the messageStore.
	 */
	public Store getMessageStore() {

		return StoreProvider.get().getMessageListStore();
	}

	/**
	 * @return
	 */
	private ColumnModel createColumnModel() {

		getColumnConfigById( MessageListFields.READ_FLAG.name() ).setFixed( true );
		getColumnConfigById( MessageListFields.PRIORITY.name() ).setFixed( true );
		getColumnConfigById( MessageListFields.ATTACHMENT_FLAG.name() ).setFixed( true );
		getColumnConfigById( MessageListFields.TO.name() ).setHidden( true );
		getColumnConfigById( MessageListFields.SIZE.name() ).setAlign( TextAlign.RIGHT );

		return new ColumnModel( COLUMN_CONFIG );
	}

	/**
	 * @param id
	 * @return
	 */
	private ColumnConfig getColumnConfigById( String id ) {

		for (ColumnConfig config : COLUMN_CONFIG) {
			if ( id.equals( config.getId() ) ) {
				return config;
			}
		}

		return null;
	}

	/**
	 * @param id
	 * @param hidden
	 */
	private void setColumnHidden( String id, boolean hidden ) {

		if ( (hidden && !this.columnModel.isHidden( id )) || (!hidden && this.columnModel.isHidden( id )) ) {
			this.columnModel.setHidden( id, hidden );
		}
	}

	/**
	 * 
	 * @author Juergen Schlierf
	 */
	private class MessagePreviewTimer extends Timer {

		RowSelectionModel rowSelectionModel;

		/**
		 * @param rowSelectionModel
		 *            The rowSelectionModel to set.
		 */
		public void setRowSelectionModel( RowSelectionModel rowSelectionModel ) {

			this.rowSelectionModel = rowSelectionModel;
		}

		@Override
		public void run() {

			if ( this.rowSelectionModel != null && this.rowSelectionModel.getCount() == 1 ) {
				String messageIdString = this.rowSelectionModel.getSelected().getAsString( MessageListFields.ID.name() );
				int messageId = Integer.valueOf( messageIdString );
				EventBroker.get().fireMessageSelected( messageId );
			}
		}
	}

	/**
	 * Selection Listener for the list of messages. It loads the selected
	 * message.
	 * 
	 * @author Juergen Schlierf
	 */
	private class MessageGridRowSelectionListener extends RowSelectionListenerAdapter {

		@Override
		public void onSelectionChange( RowSelectionModel sm ) {

			if ( sm.getCount() == 1 ) {
				messageLoadTimer.cancel();
				messageLoadTimer.setRowSelectionModel( sm );
				messageLoadTimer.schedule( MESSAGE_LOAD_DELAY );
			}
		}
	}

	/**
	 * Selection listener.
	 * 
	 * @author Juergen Schlierf
	 */
	private class OpenMessageGridRowSelectionListener extends GridRowListenerAdapter implements
			AsyncCallback<GWTMessage> {

		@Override
		public void onRowDblClick( GridPanel grid, int rowIndex, EventObject e ) {

			Record messageRecord = grid.getStore().getAt( rowIndex );
			boolean isDraft = messageRecord.getAsBoolean( MessageListFields.DRAFT_FLAG.name() );
			String messageId = messageRecord.getId();
			if ( messageId != null ) {
				if ( isDraft ) {
					ServiceProvider.getMailboxService().openDraftMessage( Long.parseLong( messageId ), this );
				}
				else {
					ServiceProvider.getMailboxService().retrieveMessage(
							GWTSessionManager.get().getCurrentMailFolder().getId(), Long.parseLong( messageId ), false,
							this );
				}
			}
		}

		public void onFailure( Throwable caught ) {

			GWTExceptionHandler.handleException( caught );
		}

		public void onSuccess( GWTMessage result ) {

			if ( result.isDraft() ) {
				WindowRegistry.COMPOSE_MESSAGE_WINDOW.open( result );
			}
			else {
				WindowRegistry.SHOW_MESSAGE_WINDOW.open( result );
			}
		}
	}
}
