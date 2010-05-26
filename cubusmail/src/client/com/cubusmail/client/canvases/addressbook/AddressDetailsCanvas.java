/* AddressDetailsCanvas.java

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
package com.cubusmail.client.canvases.addressbook;

import com.cubusmail.common.model.Address;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressDetailsCanvas extends VLayout {

	private Button editButton;

	public AddressDetailsCanvas() {

		super();
		setOverflow( Overflow.SCROLL );
		setMembersMargin( 5 );

		DynamicForm[] forms = new DynamicForm[AddressDetailsFormsManagerEnum.values().length];
		for (int i = 0; i < AddressDetailsFormsManagerEnum.values().length; i++) {
			forms[i] = AddressDetailsFormsManagerEnum.values()[i].get();
		}
		setMembers( forms );

		this.editButton = new Button( "Edit" );
		this.editButton.setPadding( 30 );
		this.editButton.setVisible( false );

		addMember( this.editButton );
	}

	/**
	 * @param address
	 */
	public void setAddress( Address address ) {

		for (AddressDetailsFormsManagerEnum form : AddressDetailsFormsManagerEnum.values()) {
			if ( address != null ) {
				form.get().setAddress( address );
			}
			else {
				form.get().setVisible( false );
			}
		}

		if ( address == null ) {
			this.editButton.setVisible( false );
		}
		else {
			this.editButton.setVisible( true );
		}
	}

	/**
	 * @param handler
	 */
	public void addEditButtonHandler( ClickHandler handler ) {

		this.editButton.addClickHandler( handler );
	}
}
