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

import com.cubusmail.client.canvases.CanvasRegistry;
import com.cubusmail.client.datasource.MessageGridRecord;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.FolderSelectedListener;
import com.cubusmail.client.events.MessageLoadedListener;
import com.cubusmail.client.events.MessagesReloadListener;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.GWTMessageRecord;
import com.cubusmail.common.model.MessageListFields;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
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
	private MessageListGrid grid;
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
		this.searchItem.setWidth( 200 );
		DynamicForm searchForm = new DynamicForm();
		searchForm.setItems( this.searchItem );

		final PickerIcon searchIcon = new PickerIcon( PickerIcon.SEARCH );
		final PickerIcon clearIcon = new PickerIcon( PickerIcon.CLEAR );
		this.searchItem.setIcons( searchIcon, clearIcon );
		this.searchItem.addIconClickHandler( new IconClickHandler() {

			public void onIconClick( IconClickEvent event ) {

				FormItemIcon icon = event.getIcon();
				if ( icon.getSrc().equals( clearIcon.getSrc() ) ) {
					resetFilter();
				}
				loadMessages();
			}
		} );
		this.searchItem.addKeyPressHandler( new KeyPressHandler() {

			public void onKeyPress( KeyPressEvent event ) {

				if ( "enter".equalsIgnoreCase( event.getKeyName() ) ) {
					loadMessages();
				}
			}
		} );

		section.setControls( searchForm );

		this.grid = new MessageListGrid();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.MessagesReloadListener#onMessagesReload()
	 */
	public void onMessagesReload() {

		resetFilter();
		loadMessages();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.FolderSelectedListener#onFolderSelected(com
	 * .cubusmail.common.model.GWTMailFolder)
	 */
	public void onFolderSelected( GWTMailFolder mailFolder ) {

		resetFilter();
		this.sectionStack.setSectionTitle( 0, "&nbsp;&nbsp;" + mailFolder.getName() );
		loadMessages();
	}

	/**
	 * @param criteria
	 */
	private void loadMessages() {

		Criteria criteria = new Criteria( GWTMailConstants.PARAM_FOLDER_ID, GWTSessionManager.get()
				.getCurrentMailFolder().getId() );
		this.grid.invalidateCache();
		this.grid.fetchData( criteria );
	}

	/**
	 * 
	 */
	private void resetFilter() {

		this.searchItem.clearValue();
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
			MessageGridRecord record = (MessageGridRecord) list.find( MessageListFields.ID.name(), String
					.valueOf( message.getId() ) );
			if ( record != null ) {
				GWTMessageRecord source = message.getMessageRecord();
				int index = this.grid.getRecordIndex( record );
				if ( index > -1 ) {
					record.setGWTMessageRecord( source );
					this.grid.refreshRow( index );
				}
			}
		}
	}
}
