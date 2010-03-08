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
import com.cubusmail.client.widgets.EmailAddressComboBox;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Compose canvas für email messages.
 * 
 * @author Juergen Schlierf
 */
public class ComposeMessageCanvas extends VLayout {

	private EmailAddressComboBox toInput;
	private EmailAddressComboBox ccInput;
	private EmailAddressComboBox bccInput;

	public ComposeMessageCanvas() {

		super();
		setShowEdges( true );
		setWidth100();
		setHeight100();
		setOverflow( Overflow.HIDDEN );

		DynamicForm form = createForm();
		setMembers( form );
	}

	private DynamicForm createForm() {

		DynamicForm form = new DynamicForm();
		form.setTitleWidth( 0 );
		form.setWidth100();
		form.setHeight100();
		form.setShowEdges( true );
		form.setOverflow( Overflow.HIDDEN );

		// this.toInput = new EmailAddressComboBox( "to",
		// TextProvider.get().window_compose_message_label_to() );
		// this.ccInput = new EmailAddressComboBox( "cc",
		// TextProvider.get().window_compose_message_label_cc() );
		// this.bccInput = new EmailAddressComboBox( "bcc",
		// TextProvider.get().window_compose_message_label_bcc() );

		ButtonItem toButton = new ButtonItem( "to", TextProvider.get().window_compose_message_label_to() );
		ButtonItem ccButton = new ButtonItem( "cc", TextProvider.get().window_compose_message_label_cc() );
		ButtonItem bccButton = new ButtonItem( "bcc", TextProvider.get().window_compose_message_label_bcc() );

		form.setItems( toButton, ccButton, bccButton );

		return form;
	}
}
