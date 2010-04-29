/* AddressEditPhoneForm.java

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
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditPhoneForm extends AddressEditAbstractForm {

	private TextItem phoneItem;

	public AddressEditPhoneForm() {

		this.phoneItem = new TextItem( "phoneItem" );
		this.phoneItem.setHint( "Phone Number" );
		this.phoneItem.setShowHintInField( true );
		this.phoneItem.setShowTitle( false );

		setItems( this.typeSelectionItem, this.phoneItem, this.removeItem, this.addItem );
	}

	@Override
	public String getValue() {

		return (String) this.phoneItem.getValue();
	}

	@Override
	public void setValue( String value ) {

		if ( GWTUtil.hasText( value ) ) {
			this.phoneItem.setValue( value );
		}
		else {
			if ( this.phoneItem.getValue() != null ) {
				this.phoneItem.clearValue();
			}
		}
	}
}
