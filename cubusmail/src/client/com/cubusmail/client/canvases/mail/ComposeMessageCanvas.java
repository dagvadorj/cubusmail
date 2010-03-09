/* ComposeMessageCanvas.java

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

import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Compose canvas for email messages.
 * 
 * @author Juergen Schlierf
 */
public class ComposeMessageCanvas extends VLayout {

	private EmailAddressInputLine toInput;
	private EmailAddressInputLine ccInput;
	private EmailAddressInputLine bccInput;

	private RichTextItem messageText;

	public ComposeMessageCanvas() {

		super();
		setShowEdges( false );
		setWidth100();
		setHeight100();
		setOverflow( Overflow.HIDDEN );

		this.toInput = new EmailAddressInputLine( "to", TextProvider.get().window_compose_message_label_to() );
		this.ccInput = new EmailAddressInputLine( "cc", TextProvider.get().window_compose_message_label_cc() );
		this.bccInput = new EmailAddressInputLine( "bcc", TextProvider.get().window_compose_message_label_bcc() );

		this.messageText = new RichTextItem( "messageText" );
		this.messageText.setWidth( 800 );
		
		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		form.setOverflow( Overflow.VISIBLE );
		form.setItems( this.messageText );
		form.setShowEdges( true );

		setMembers( this.toInput, this.ccInput, this.bccInput, form );
	}
}
