/* AddressEditEmailSubCanvas.java

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

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditEmailSubCanvas extends AddressEditAbstractSubCanvas {

	public AddressEditEmailSubCanvas() {

		super( AddressEditEmailForm.class, AddressEditFormTypeEnum.EMAIL_GROUP );
	}

	public void fillAddress( Address address ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				String email = form.getValue();
				switch (form.getType()) {
				case EMAIL1:
					address.setEmail( email );
					break;
				case EMAIL2:
					address.setEmail2( email );
					break;
				case EMAIL3:
					address.setEmail3( email );
					break;
				case EMAIL4:
					address.setEmail4( email );
					break;
				case EMAIL5:
					address.setEmail5( email );
					break;
				}
			}
		}

	}

	/**
	 * @param address
	 */
	public void setAddress( Address address ) {

		// remove other phone forms
		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				form.setVisible( false );
			}
		}

		if ( GWTUtil.hasText( address.getEmail() ) ) {
			addForm( AddressEditFormTypeEnum.EMAIL1, address.getEmail() );
		}
		if ( GWTUtil.hasText( address.getEmail2() ) ) {
			addForm( AddressEditFormTypeEnum.EMAIL2, address.getEmail2() );
		}
		if ( GWTUtil.hasText( address.getEmail3() ) ) {
			addForm( AddressEditFormTypeEnum.EMAIL3, address.getEmail3() );
		}
		if ( GWTUtil.hasText( address.getEmail4() ) ) {
			addForm( AddressEditFormTypeEnum.EMAIL4, address.getEmail4() );
		}
		if ( GWTUtil.hasText( address.getEmail5() ) ) {
			addForm( AddressEditFormTypeEnum.EMAIL5, address.getEmail5() );
		}
	}
}
