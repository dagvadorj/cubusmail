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

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.GWTConstants;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditNameForm extends DynamicForm implements IAddressEditForm {

	public AddressEditNameForm() {

		super();
		setWidth100();
		setTitleWidth( GWTConstants.ADDRESS_TITLE_WIDTH );
		setBackgroundColor( "#EEEEEE" );
		setTitleSuffix( "" );
		setNumCols( 3 );
		setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, "*" );

		TextItem firstNameItem = new TextItem( "firstName", "" );
		firstNameItem.setTextBoxStyle( "addressDetailsHeader" );
		firstNameItem.setHeight( 30 );
		firstNameItem.setHint( "First Name" );
		firstNameItem.setShowHintInField( true );
		TextItem lastNameItem = new TextItem( "lastName" );
		lastNameItem.setShowTitle( false );
		lastNameItem.setTextBoxStyle( "addressDetailsHeader" );
		lastNameItem.setHeight( 30 );
		lastNameItem.setHint( "Last Name" );
		lastNameItem.setShowHintInField( true );

		setItems( firstNameItem, lastNameItem );
	}

	public void setAddress( Address address ) {

	}
}
