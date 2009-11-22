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
package com.cubusmail.client.canvases;

import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.events.MessagesReloadListener;
import com.cubusmail.client.util.ImageProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.MessageListFields;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

/**
 * Canvas for the message list.
 * 
 * @author Juergen Schlierf
 */
public class MessageListCanvas extends SectionStack implements MessagesReloadListener {

	private SectionStackSection section;
	private ListGrid grid;

	public MessageListCanvas() {

		super();
		setShowResizeBar( true );

		this.section = new SectionStackSection( "Inbox" );
		this.section.setCanCollapse( false );
		this.section.setExpanded( true );
		this.section.setResizeable( true );

		TextItem textItem = new TextItem();
		textItem.setTitle( "Search" );
		DynamicForm searchCanvas = new DynamicForm();
		searchCanvas.setItems( textItem );

		Button searchButton = new Button( "" );
		searchButton.setBorder( "0px" );
		searchButton.setIcon( ImageProvider.FIND );
		searchButton.setAutoFit( true );
		section.setControls( searchCanvas, searchButton );

		this.grid = new ListGrid();
		this.grid.setAutoFetchData( false );
		this.grid.setAlternateRecordStyles( true );
		this.grid.setWidth100();
		this.grid.setCellHeight( 17 );
		this.grid.setBaseStyle( "myOtherGridCell" );
		this.grid.setFields( generateFields() );
		this.grid.setAutoFetchData( true );
		this.grid.setDataSource( DataSourceRegistry.MESSAGE_LIST.get() );
		this.section.setItems( this.grid );

		setSections( this.section );

	}

	/**
	 * Create the columns fields for the message grid.
	 * 
	 * @return
	 */
	private ListGridField[] generateFields() {

		ListGridField[] fields = new ListGridField[7];

		// read flag
		fields[0] = new ListGridField( MessageListFields.READ_FLAG.name(), "", 25 );
		fields[0].setAlign( Alignment.CENTER );
		fields[0].setType( ListGridFieldType.IMAGE );
		Button headerButton = new Button();
		headerButton.setIcon( ImageProvider.MSG_STATUS_READ );
		fields[0].setHeaderButtonProperties( headerButton );

		// attachment flag
		fields[1] = new ListGridField( MessageListFields.ATTACHMENT_FLAG.name(), "", 25 );
		fields[1].setAlign( Alignment.CENTER );
		fields[1].setType( ListGridFieldType.IMAGE );
		headerButton = new Button();
		headerButton.setIcon( ImageProvider.MSG_ATTACHMENT );
		fields[1].setHeaderButtonProperties( headerButton );

		// priority flag
		fields[2] = new ListGridField( MessageListFields.PRIORITY.name(), TextProvider.get().grid_messages_subject(),
				25 );
		fields[2].setAlign( Alignment.CENTER );
		fields[2].setType( ListGridFieldType.IMAGE );
		headerButton = new Button();
		headerButton.setIcon( ImageProvider.PRIORITY_HIGH );
		fields[2].setHeaderButtonProperties( headerButton );

		// subject
		fields[3] = new ListGridField( MessageListFields.SUBJECT.name(), TextProvider.get().grid_messages_subject(),
				500 );

		// from
		fields[4] = new ListGridField( MessageListFields.FROM.name(), TextProvider.get().grid_messages_from(), 200 );

		// send date
		fields[5] = new ListGridField( MessageListFields.SEND_DATE.name(), TextProvider.get().grid_messages_date(), 120 );

		// size
		fields[6] = new ListGridField( MessageListFields.SIZE.name(), TextProvider.get().grid_messages_size(), 120 );

		return fields;
	}

	@Override
	public void onMessagesReload() {

		this.grid.fetchData();
	}
}
