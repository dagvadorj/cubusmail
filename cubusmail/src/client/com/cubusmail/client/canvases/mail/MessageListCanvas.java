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
import com.cubusmail.client.canvases.mail.MessageSearchForm.SearchHandler;
import com.cubusmail.client.datasource.MessageGridRecord;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.FolderSelectedListener;
import com.cubusmail.client.events.MessageLoadedListener;
import com.cubusmail.client.events.MessagesReloadListener;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.common.model.GWTConstants;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.GWTMessageRecord;
import com.cubusmail.common.model.MessageListFields;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.RecordList;
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
	private MessageSearchForm searchForm;

	public MessageListCanvas() {

		super();

		this.sectionStack = new SectionStack();

		this.section = new SectionStackSection();
		this.section.setCanCollapse( false );
		this.section.setExpanded( true );
		this.section.setResizeable( true );
		this.section.setShowHeader( true );

		this.searchForm = new MessageSearchForm();
		this.searchForm.addSearchHandler( new SearchHandler() {

			public void onSearch( MessageListFields[] fields, String[] values ) {

				loadMessages();
			}
		} );
		section.setControls( this.searchForm );

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

		this.searchForm.resetFilter();
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

		this.searchForm.resetFilter();
		this.sectionStack.setSectionTitle( 0, "&nbsp;&nbsp;" + mailFolder.getName() );
		loadMessages();
	}

	/**
	 * @param criteria
	 */
	private void loadMessages() {

		// I use DSRequest because the Criteria class doesn't support Enums
		DSRequest request = null;
		if ( this.searchForm.getSearchValues() != null ) {
			request = new DSRequest();
			request.setAttribute( GWTConstants.PARAM_SEARCH_FIELDS, this.searchForm.getSearchFields() );
			request.setAttribute( GWTConstants.PARAM_SEARCH_VALUES, this.searchForm.getSearchValues() );
		}

		this.grid.invalidateCache();

		if ( request != null ) {
			this.grid.fetchData( null, null, request );
		}
		else {
			this.grid.fetchData();
		}
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
