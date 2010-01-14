/* MessageListCanvas.java

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
import com.cubusmail.client.actions.message.LoadMessageAction;
import com.cubusmail.client.canvases.CanvasRegistry;
import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.FolderSelectedListener;
import com.cubusmail.client.events.MessageLoadedListener;
import com.cubusmail.client.events.MessagesReloadListener;
import com.cubusmail.client.toolbars.MailToolbar;
import com.cubusmail.client.toolbars.ToolbarRegistry;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.ImageProvider;
import com.cubusmail.common.model.MessageListFields;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Canvas for the message list.
 * 
 * @author Juergen Schlierf
 */
public class MessageListCanvas extends VLayout implements MessagesReloadListener, FolderSelectedListener,
		MessageLoadedListener {

	private SectionStack sectionStack;
	private SectionStackSection section;
	private ListGrid grid;
	private TextItem searchItem;

	public MessageListCanvas() {

		super();

		this.sectionStack = new SectionStack();
		// this.sectionStack.setSectionHeaderClass( "sectionStackSection" );

		this.section = new SectionStackSection();
		this.section.setCanCollapse( false );
		this.section.setExpanded( true );
		this.section.setResizeable( true );
		this.section.setShowHeader( true );

		this.searchItem = new TextItem();
		this.searchItem.setTitle( "Search" );
		DynamicForm searchCanvas = new DynamicForm();
		searchCanvas.setItems( this.searchItem );

		Button searchButton = new Button( "" );
		searchButton.setBorder( "0px" );
		searchButton.setIcon( ImageProvider.FIND );
		searchButton.setAutoFit( true );
		section.setControls( searchCanvas, searchButton );

		this.grid = new ListGrid();
		this.grid.setSelectionType( SelectionStyle.MULTIPLE );
		this.grid.setAutoFetchData( false );
		this.grid.setAlternateRecordStyles( true );
		this.grid.setWidth100();
		this.grid.setFields( generateFields() );
		this.grid.setDataSource( DataSourceRegistry.MESSAGE_LIST.get() );
		this.grid.setDataPageSize( GWTMailConstants.MESSAGE_LIST_PAGE_SIZE );
		this.grid.setFastCellUpdates( false );
		this.grid.setSaveLocally( false );
		addGridHandlers();

		this.section.setItems( this.grid );

		this.sectionStack.setSections( this.section );

		if ( GWTSessionManager.get().getPreferences().isShowReadingPane() ) {
			this.sectionStack.setHeight( "50%" );
			this.sectionStack.setShowResizeBar( true );
			setMembers( this.sectionStack, CanvasRegistry.MESSAGE_READING_PANE.get() );
		}
		else {
			this.sectionStack.setHeight100();
			setMembers( this.sectionStack );
		}

		EventBroker.get().addMessagesReloadListener( this );
		EventBroker.get().addFolderSelectedListener( this );
		EventBroker.get().addMessageLoadedListener( this );
	}

	/**
	 * Create the columns fields for the message grid.
	 * 
	 * @return
	 */
	private ListGridField[] generateFields() {

		ListGridField[] fields = new ListGridField[7];

		// read flag
		fields[0] = new ListGridField( MessageListFields.FLAG_IMAGE.name(), TextProvider.get().grid_messages_status(),
				25 );
		fields[0].setAlign( Alignment.CENTER );
		fields[0].setType( ListGridFieldType.IMAGE );
		fields[0].setCanSort( false );
		fields[0].setCanGroupBy( false );
		fields[0].setShowGridSummary( false );
		Button headerButton = new Button();
		headerButton.setIcon( ImageProvider.MSG_STATUS_READ );
		fields[0].setHeaderButtonProperties( headerButton );
		fields[0].setShowDefaultContextMenu( false );
		fields[0].setFrozen( true );
		fields[0].setCanFreeze( false );

		// attachment flag
		fields[1] = new ListGridField( MessageListFields.ATTACHMENT_IMAGE.name(), TextProvider.get()
				.grid_messages_attachments(), 25 );
		fields[1].setAlign( Alignment.CENTER );
		fields[1].setType( ListGridFieldType.IMAGE );
		fields[1].setCanSort( false );
		fields[1].setCanGroupBy( false );
		fields[1].setShowGridSummary( false );
		headerButton = new Button();
		headerButton.setIcon( ImageProvider.MSG_ATTACHMENT );
		fields[1].setHeaderButtonProperties( headerButton );
		fields[1].setShowDefaultContextMenu( false );
		fields[1].setFrozen( true );
		fields[1].setCanFreeze( false );

		// priority flag
		fields[2] = new ListGridField( MessageListFields.PRIORITY_IMAGE.name(), TextProvider.get()
				.grid_messages_priority(), 25 );
		fields[2].setAlign( Alignment.CENTER );
		fields[2].setType( ListGridFieldType.IMAGE );
		fields[2].setCanSort( false );
		fields[2].setCanGroupBy( false );
		fields[2].setShowGridSummary( false );
		fields[2].setShowGridSummary( false );
		headerButton = new Button();
		headerButton.setIcon( ImageProvider.PRIORITY_HIGH );
		fields[2].setHeaderButtonProperties( headerButton );
		fields[2].setShowDefaultContextMenu( false );
		fields[2].setFrozen( true );
		fields[2].setCanFreeze( false );

		// subject
		fields[3] = new ListGridField( MessageListFields.SUBJECT.name(), TextProvider.get().grid_messages_subject(),
				500 );
		fields[3].setAlign( Alignment.LEFT );
		fields[3].setCanGroupBy( false );
		fields[3].setFrozen( true );
		fields[3].setCanFreeze( false );

		// from
		fields[4] = new ListGridField( MessageListFields.FROM.name(), TextProvider.get().grid_messages_from(), 200 );
		fields[4].setAlign( Alignment.LEFT );
		fields[4].setCanGroupBy( false );
		fields[4].setShowGridSummary( false );
		fields[4].setFrozen( true );
		fields[4].setCanFreeze( false );

		// send date
		fields[5] = new ListGridField( MessageListFields.SEND_DATE.name(), TextProvider.get().grid_messages_date(), 120 );
		fields[5].setAlign( Alignment.LEFT );
		fields[5].setCanGroupBy( false );
		fields[5].setShowGridSummary( false );
		fields[5].setFrozen( true );
		fields[5].setCanFreeze( false );

		// size
		fields[6] = new ListGridField( MessageListFields.SIZE.name(), TextProvider.get().grid_messages_size(), 120 );
		fields[6].setAlign( Alignment.RIGHT );
		fields[6].setCanGroupBy( false );
		fields[6].setShowGridSummary( false );
		fields[6].setFrozen( true );
		fields[6].setCanFreeze( false );

		return fields;
	}

	/**
	 * Add all necessary handlers.
	 */
	private void addGridHandlers() {

		this.grid.addRecordClickHandler( ActionRegistry.LOAD_MESSAGE.get( LoadMessageAction.class ) );
		this.grid.addSelectionChangedHandler( ToolbarRegistry.MAIL.get( MailToolbar.class ) );
		this.grid.addDataArrivedHandler( ToolbarRegistry.MAIL.get( MailToolbar.class ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.MessagesReloadListener#onMessagesReload()
	 */
	public void onMessagesReload() {

		Criteria criteria = new Criteria( GWTMailConstants.PARAM_FOLDER_ID, GWTSessionManager.get()
				.getCurrentMailFolder().getId() );
		this.grid.invalidateCache();
		this.grid.fetchData( criteria );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.FolderSelectedListener#onFolderSelected(com
	 * .cubusmail.common.model.GWTMailFolder)
	 */
	public void onFolderSelected( GWTMailFolder mailFolder ) {

		// TODO: set the right section style
		this.sectionStack.setSectionTitle( 0, "&nbsp;&nbsp;" + mailFolder.getName() );
		Criteria criteria = new Criteria( GWTMailConstants.PARAM_FOLDER_ID, mailFolder.getId() );
		this.grid.invalidateCache();
		this.grid.fetchData( criteria );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.MessageLoadedListener#onMessageLoaded(com
	 * .cubusmail.common.model.GWTMessage)
	 */
	public void onMessageLoaded( GWTMessage message ) {

		RecordList list = this.grid.getRecordList();
		if ( list != null && list.getLength() > 0 ) {
			ListGridRecord record = (ListGridRecord) list.find( MessageListFields.ID.name(), String.valueOf( message
					.getId() ) );
			if ( record != null ) {
				String source[] = message.getMessageRecord();
				int index = this.grid.getRecordIndex( record );
				if ( index > -1 ) {
					for (MessageListFields fieldDef : MessageListFields.values()) {
						record.setAttribute( fieldDef.name(), source[fieldDef.ordinal()] );
					}
					this.grid.refreshRow( index );
					// this.grid.redraw();
				}
			}
		}
	}
}
