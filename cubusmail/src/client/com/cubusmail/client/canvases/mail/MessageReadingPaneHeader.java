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
import com.cubusmail.common.model.GWTMessage;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
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

	public MessageReadingPaneHeader() {

		super();
		setStyleName( "messageReadingPaneHeader" );
		setWidth100();
		setAutoHeight();
		setPadding( 4 );
		setOverflow( Overflow.VISIBLE );

		this.subject = new Label( "" );
		this.subject.setWidth100();
		this.subject.setAutoHeight();
		this.subject.setStyleName( "message-subject" );

		this.from = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_from() );
		this.to = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_to() );
		this.cc = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_cc() );
		this.replyTo = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_replyto() );

		setMembers( this.subject, this.from, this.to, this.cc, this.replyTo );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		this.subject.setContents( "" );
		if ( GWTUtil.hasText( message.getSubject() ) ) {
			this.subject.setContents( message.getSubject() );
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
		this.redraw();
	}
}
