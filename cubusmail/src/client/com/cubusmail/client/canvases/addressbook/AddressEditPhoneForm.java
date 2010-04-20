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
import com.cubusmail.common.model.Address;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditPhoneForm extends AddressEditAbstractForm {

	private TextItem phoneItem;

	public AddressEditPhoneForm( final AddressEditFormsManagerEnum defaultType ) {

		super( defaultType );

		this.phoneItem = new TextItem( "phoneItem" );
		this.phoneItem.setHint( "Phone Number" );
		this.phoneItem.setShowHintInField( true );
		this.phoneItem.setShowTitle( false );

		if ( this.type == AddressEditFormsManagerEnum.PRIVATE_PHONE ) {
			this.removeItem.setVisible( false );
		}

		setItems( this.typeSelectionItem, this.phoneItem, this.removeItem, this.addItem );
	}

	@Override
	public void setAddress( Address address ) {

		String value = null;

		switch (this.type) {
		case PRIVATE_PHONE:
			value = address.getPrivatePhone();
			break;
		case WORK_PHONE:
			value = address.getWorkPhone();
			break;
		case PRIVATE_MOBILE:
			value = address.getPrivateMobile();
			break;
		case WORK_MOBILE:
			value = address.getWorkMobile();
			break;
		case PRIVATE_FAX:
			value = address.getPrivateFax();
			break;
		case WORK_FAX:
			value = address.getPrivateFax();
			break;

		default:
			throw new IllegalArgumentException( "AddressDetailsFormsManagerEnum type missing: "
					+ this.type.name() );
		}

		if ( GWTUtil.hasText( value ) ) {
			this.phoneItem.setValue( value );
			setVisible( true );
		}
		else {
			this.phoneItem.clearValue();
			setVisible( false );
		}
	}
}
