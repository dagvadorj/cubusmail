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
import com.cubusmail.client.widgets.AttachmentWidget;
import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMessage;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

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
	private FlowPanel attachmentLine;

	public MessageReadingPaneHeader() {

		super();
		setStyleName( "messageReadingPaneHeader" );
		setWidth100();
		setPadding( 4 );
		setOverflow( Overflow.CLIP_H );
		setShowEdges( true );

		this.subject = new Label( "" );
		this.subject.setWidth100();
		this.subject.setAutoHeight();
		this.subject.setStyleName( "message-subject" );
		this.subject.setOverflow( Overflow.CLIP_H );

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

		this.attachmentLine = new FlowPanel();
		this.attachmentLine.setWidth( "100%" );
		this.attachmentLine.setHeight( "100%" );

		WidgetCanvas widgetCanvas = new WidgetCanvas( this.attachmentLine );
		widgetCanvas.setWidth100();
		widgetCanvas.setAutoHeight();
		widgetCanvas.setOverflow( Overflow.CLIP_H );

		setMembers( this.subject, this.from, this.to, this.cc, this.replyTo, this.dateLine );
		addMember( widgetCanvas );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		this.attachmentLine.clear();
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
			this.date.setContents( message.getDate().toGMTString() );
			this.dateLine.setVisible( true );
		}
		else {
			this.dateLine.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getAttachments() ) ) {
			this.attachmentLine.clear();
			for (int i = 0; i < message.getAttachments().length; i++) {
				this.attachmentLine.add( new AttachmentWidget( message.getAttachments()[i] ) );
				if ( i < (message.getAttachments().length - 1) ) {
					this.attachmentLine.add( new AttachmentSeparator() );
				}
			}
			this.attachmentLine.setVisible( true );
		}
		else {
			this.attachmentLine.setVisible( false );
		}

		this.reflow();
	}

	private class AttachmentSeparator extends HTML {

		public AttachmentSeparator() {

			// include whitespace for wrapping
			setHTML( " " );
			DOM.setStyleAttribute( getElement(), "rightPadding", "15px" );
		}
	}
}
