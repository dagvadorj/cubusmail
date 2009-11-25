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

import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.GWTMessage;
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

	public MessageReadingPaneHeader() {

		super();
		setStyleName( "messageReadingPaneHeader" );
		setWidth100();
		setAutoHeight();
		setPadding( 4 );

		this.subject = new Label( "" );
		this.subject.setWidth100();
		this.subject.setAutoHeight();
		this.subject.setStyleName( "message-subject" );

		this.from = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_from() );
		this.to = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_to() );

		setMembers( this.subject, this.from, this.to );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		this.subject.setContents( message.getSubject() );
		this.from.setAddresses( message.getFromArray() );
		this.to.setAddresses( message.getToArray() );
		this.redraw();
	}
}
