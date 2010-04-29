/* AddressEditNameForm.java

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

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.GWTConstants;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditNameForm extends DynamicForm {

	private TextItem firstNameItem;
	private TextItem lastNameItem;

	public AddressEditNameForm() {

		super();
		setWidth100();
		setTitleWidth( GWTConstants.ADDRESS_TITLE_WIDTH );
		setBackgroundColor( "#EEEEEE" );
		setTitleSuffix( "" );
		setNumCols( 3 );
		setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, "*" );

		this.firstNameItem = new TextItem( "firstName", "" );
		this.firstNameItem.setTextBoxStyle( "addressDetailsHeader" );
		this.firstNameItem.setHeight( 30 );
		this.firstNameItem.setHint( "First Name" );
		this.firstNameItem.setShowHintInField( true );
		this.lastNameItem = new TextItem( "lastName" );
		this.lastNameItem.setShowTitle( false );
		this.lastNameItem.setTextBoxStyle( "addressDetailsHeader" );
		this.lastNameItem.setHeight( 30 );
		this.lastNameItem.setHint( "Last Name" );
		this.lastNameItem.setShowHintInField( true );

		setItems( this.firstNameItem, this.lastNameItem );
	}

	public void setAddress( Address address ) {

		if ( address != null ) {
			this.firstNameItem.setValue( GWTUtil.hasText( address.getFirstName() ) ? address.getFirstName() : "" );
			this.lastNameItem.setValue( GWTUtil.hasText( address.getLastName() ) ? address.getLastName() : "" );
		}
		else {
			this.firstNameItem.setValue( "" );
			this.lastNameItem.setValue( "" );
		}
	}
}
