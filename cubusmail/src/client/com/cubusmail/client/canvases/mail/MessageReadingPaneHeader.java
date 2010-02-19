/* MessageReadingPaneHeader.java

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

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMessage;
import com.smartgwt.client.types.Orientation;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TileLayoutPolicy;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.FlowLayout;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileLayout;

/**
 * Header for message reading pane.
 * 
 * @author Juergen Schlierf
 */
public class MessageReadingPaneHeader extends VLayout {

	private Label subject;
	private EmailAddressLine from;
	private EmailAddressLine to;
	private EmailAddressLine cc;
	private EmailAddressLine replyTo;
	private HLayout dateLine;
	private Label date;
	private FlowLayout attachmentLine;

	public MessageReadingPaneHeader() {

		super();
		setStyleName( "messageReadingPaneHeader" );
		// setWidth100();
		setAutoHeight();
		setPadding( 4 );
		setOverflow( Overflow.VISIBLE );

		this.subject = new Label( "" );
		this.subject.setWrap( true );
		this.subject.setAutoHeight();
		this.subject.setStyleName( "message-subject" );

		this.from = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_from() );
		this.to = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_to() );
		this.cc = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_cc() );
		this.replyTo = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_replyto() );

		this.dateLine = new HLayout();
		this.dateLine.setWidth100();
		this.dateLine.setAutoHeight();
		this.dateLine.setVisible( false );
		this.date = new Label( "" );
		this.date.setWidth100();
		this.date.setAutoHeight();
		Label dateLabel = new Label( TextProvider.get().message_reading_pane_panel_date() );
		dateLabel.setWidth( GWTMailConstants.MESSAGE_READING_PANE_LABEL_WIDTH );
		dateLabel.setAutoHeight();
		this.dateLine.setMembers( dateLabel, this.date );

		this.attachmentLine = new FlowLayout();
		// this.attachmentLine.setTileHMargin( 5 );
		this.attachmentLine.setOrientation( Orientation.HORIZONTAL );
		this.attachmentLine.setWidth100();
		this.attachmentLine.setAutoHeight();
		this.attachmentLine.setVisible( true );
		this.attachmentLine.setOverflow( Overflow.VISIBLE );
		this.attachmentLine.setExpandMargins( false );
		this.attachmentLine.setAnimateTileChange( false );
		this.attachmentLine.setTileHeight( 20 );
		this.attachmentLine.setAutoWrapLines( true );
		this.attachmentLine.setShowEdges( true );
		// DOM.setStyleAttribute( this.attachmentLine.getElement(), "border",
		// "1px solid #00f" );

		setMembers( this.subject, this.from, this.to, this.cc, this.replyTo, this.dateLine, this.attachmentLine );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		// this.attachmentLine.clear();
		this.subject.setContents( "" );

		if ( GWTUtil.hasText( message.getSubject() ) ) {
			this.subject.setContents( GWTUtil.htmlEncode( message.getSubject() ) );
		}
		if ( !GWTUtil.isEmpty( message.getFromArray() ) ) {
			this.from.setVisible( true );
			this.from.setAddresses( message.getFromArray() );
		}
		else {
			this.from.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getToArray() ) ) {
			this.to.setVisible( true );
			this.to.setAddresses( message.getToArray() );
		}
		else {
			this.to.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getCcArray() ) ) {
			this.cc.setVisible( true );
			this.cc.setAddresses( message.getCcArray() );
		}
		else {
			this.cc.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getReplyToArray() ) ) {
			this.replyTo.setVisible( true );
			this.replyTo.setAddresses( message.getReplyToArray() );
		}
		else {
			this.replyTo.setVisible( false );
		}
		if ( message.getDate() != null ) {
			// this.date.setContents( GWTUtil.formatDate( message.getDate(),
			// GWTSessionManager.get().getPreferences()
			// .getTimezoneOffset() ) );
			this.date.setContents( message.getDate().toGMTString() );
			this.dateLine.setVisible( true );
		}
		else {
			this.dateLine.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getAttachments() ) ) {
			IButton[] buttons = new IButton[message.getAttachments().length];
			for (int i = 0; i < message.getAttachments().length; i++) {
				GWTAttachment attachment = message.getAttachments()[i];
				buttons[i] = new IButton( attachment.getFileName() );
				buttons[i].setOverflow( Overflow.VISIBLE );
				this.attachmentLine.addTile( buttons[i] );
				this.attachmentLine.layoutTiles();
			}
			this.attachmentLine.setVisible( true );
		}
		else {
			this.attachmentLine.setVisible( false );
		}

	}

}
