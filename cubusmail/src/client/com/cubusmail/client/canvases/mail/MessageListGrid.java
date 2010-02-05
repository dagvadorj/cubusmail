/* MessageListGrid.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
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
import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.toolbars.MailToolbar;
import com.cubusmail.client.toolbars.ToolbarRegistry;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMessageFlags;
import com.cubusmail.common.model.ImageProvider;
import com.cubusmail.common.model.MessageListFields;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Special ListGrid implementation for messages.
 * 
 * @author Juergen Schlierf
 */
public class MessageListGrid extends ListGrid {

	private final static String HTML_IMG_UNREAD = Canvas.imgHTML( ImageProvider.MSG_STATUS_UNREAD );
	private final static String HTML_IMG_ANSWERED = Canvas.imgHTML( ImageProvider.MSG_STATUS_ANSWERED );
	private final static String HTML_IMG_DELETED = Canvas.imgHTML( ImageProvider.MSG_STATUS_DELETED );
	private final static String HTML_IMG_DRAFT = Canvas.imgHTML( ImageProvider.MSG_STATUS_DRAFT );

	public MessageListGrid() {

		super();
		setSelectionType( SelectionStyle.MULTIPLE );
		setAutoFetchData( false );
		setAlternateRecordStyles( true );
		setWidth100();
		setDataSource( DataSourceRegistry.MESSAGE_LIST.get() );
		setDataPageSize( GWTMailConstants.MESSAGE_LIST_PAGE_SIZE );
		setFastCellUpdates( false );
		setSaveLocally( false );
		setFields( generateFields() );
		addGridHandlers();
	}

	/**
	 * Create the columns fields for the message grid.
	 * 
	 * @return
	 */
	private ListGridField[] generateFields() {

		ListGridField[] fields = new ListGridField[7];

		// read flag
		fields[0] = new ListGridField( MessageListFields.FLAGS.name(), TextProvider.get().grid_messages_status(), 25 );
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
		fields[0].setCellFormatter( new FlagCellFormatter() );

		// attachment flag
		fields[1] = new ListGridField( MessageListFields.ATTACHMENT_IMAGE.name(), TextProvider.get()
				.grid_messages_attachments(), 25 );
		fields[1].setAlign( Alignment.CENTER );
		fields[1].setType( ListGridFieldType.IMAGE );
		fields[1].setCanSort( false );
		fields[1].setCanGroupBy( false );
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
		fields[2].setType( ListGridFieldType.IMAGEFILE );
		fields[2].setCanSort( false );
		fields[2].setCanGroupBy( false );
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
		fields[1].setType( ListGridFieldType.DATE );
		fields[5].setAlign( Alignment.LEFT );
		fields[5].setCanGroupBy( false );
		fields[5].setShowGridSummary( false );
		fields[5].setFrozen( true );
		fields[5].setCanFreeze( false );
		fields[5].setCellFormatter( new SendDateCellFormatter() );

		// size
		fields[6] = new ListGridField( MessageListFields.SIZE.name(), TextProvider.get().grid_messages_size(), 120 );
		fields[1].setType( ListGridFieldType.INTEGER );
		fields[6].setAlign( Alignment.RIGHT );
		fields[6].setCanGroupBy( false );
		fields[6].setShowGridSummary( false );
		fields[6].setFrozen( true );
		fields[6].setCanFreeze( false );
		fields[6].setCellFormatter( new SizeCellFormatter() );

		return fields;
	}

	/**
	 * Add all necessary handlers.
	 */
	private void addGridHandlers() {

		addRecordClickHandler( ActionRegistry.LOAD_MESSAGE.get( LoadMessageAction.class ) );
		addSelectionChangedHandler( ToolbarRegistry.MAIL.get( MailToolbar.class ) );
		addDataArrivedHandler( ToolbarRegistry.MAIL.get( MailToolbar.class ) );
	}

	/**
	 * CellFormater for mail flags.
	 * 
	 * @author Juergen Schlierf
	 */
	private class FlagCellFormatter implements CellFormatter {

		public String format( Object value, ListGridRecord record, int rowNum, int colNum ) {

			if ( value != null ) {
				GWTMessageFlags flags = (GWTMessageFlags) value;
				if ( flags.isDeleted() ) {
					return HTML_IMG_DELETED;
				}
				else if ( flags.isAnswered() ) {
					return HTML_IMG_ANSWERED;
				}
				else if ( flags.isDraft() ) {
					return HTML_IMG_DRAFT;
				}
				else if ( flags.isUnread() ) {
					return HTML_IMG_UNREAD;
				}
			}

			return null;
		}
	}

	/**
	 * CellFormatter for send date.
	 * 
	 * @author Juergen Schlierf
	 */
	private class SendDateCellFormatter implements CellFormatter {

		public String format( Object value, ListGridRecord record, int rowNum, int colNum ) {

			return record.getAttributeAsString( MessageListFields.SEND_DATE_STRING.name() );
		}
	}

	/**
	 * CellFormatter for message size.
	 * 
	 * @author Juergen Schlierf
	 */
	private class SizeCellFormatter implements CellFormatter {

		public String format( Object value, ListGridRecord record, int rowNum, int colNum ) {

			return record.getAttributeAsString( MessageListFields.SIZE_STRING.name() );
		}
	}
}
