/* AddressEditCanvas.java

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

import java.util.ArrayList;
import java.util.List;

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.common.model.Address;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditCanvas extends VLayout {

	private AddressEditNameForm nameForm;
	private List<AddressEditPhoneForm> phoneForms = new ArrayList<AddressEditPhoneForm>(
			AddressEditFormTypeEnum.PHONE_GROUP.length );

	public AddressEditCanvas() {

		super();
		setOverflow( Overflow.SCROLL );

		this.nameForm = new AddressEditNameForm();
		for (AddressEditFormTypeEnum typeEnum : AddressEditFormTypeEnum.PHONE_GROUP) {
			this.phoneForms.add( new AddressEditPhoneForm( typeEnum ) );
		}

		init();
		// AddressEditFormTypeEnum.setAddress( null );
	}

	public void init() {

		// name form
		if ( !hasMember( this.nameForm ) ) {
			addMember( this.nameForm );
		}

		// first phone form
		if ( !hasMember( this.phoneForms.get( 0 ) ) ) {
			addMember( this.phoneForms.get( 0 ) );
		}

		// remove other phone forms
		for (int i = 1; i < AddressEditFormTypeEnum.PHONE_GROUP.length; i++) {
			if ( hasMember( this.phoneForms.get( i ) ) ) {
				removeMember( this.phoneForms.get( i ) );
			}
		}
	}

	public void setAddress( Address address ) {

		this.nameForm.setAddress( address );
		if ( GWTUtil.hasText( address.getPrivatePhone() ) ) {
			this.phoneForms.get( 0 ).setAddress( address );
		}
	}

	public void addPhoneForm() {

	}

	/**
	 * @return
	 */
	public AddressEditFormTypeEnum[] getAvailableFormTypes() {

		List<AddressEditFormTypeEnum> typeList = new ArrayList<AddressEditFormTypeEnum>();

		for (int i = 0; i < this.phoneForms.size(); i++) {
			AddressEditPhoneForm form = this.phoneForms.get( i );
			if ( !hasMember( form ) ) {
				typeList.add( form.getDefaultType() );
			}
		}

		return typeList.toArray( new AddressEditFormTypeEnum[0] );
	}
}
