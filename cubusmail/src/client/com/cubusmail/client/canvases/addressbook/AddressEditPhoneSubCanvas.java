/* AddressEditPhoneSubCanvas.java

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
public class AddressEditPhoneSubCanvas extends AddressEditSubCanvas {

	public AddressEditPhoneSubCanvas() {

		super( AddressEditPhoneForm.class, AddressEditFormTypeEnum.PHONE_GROUP );
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

		if ( GWTUtil.hasText( address.getPrivatePhone() ) ) {
			addForm( AddressEditFormTypeEnum.PRIVATE_PHONE, address.getPrivatePhone() );
		}
		if ( GWTUtil.hasText( address.getWorkPhone() ) ) {
			addForm( AddressEditFormTypeEnum.WORK_PHONE, address.getWorkPhone() );
		}
		if ( GWTUtil.hasText( address.getPrivateMobile() ) ) {
			addForm( AddressEditFormTypeEnum.PRIVATE_MOBILE, address.getPrivateMobile() );
		}
		if ( GWTUtil.hasText( address.getWorkMobile() ) ) {
			addForm( AddressEditFormTypeEnum.WORK_MOBILE, address.getWorkMobile() );
		}
		if ( GWTUtil.hasText( address.getPrivateFax() ) ) {
			addForm( AddressEditFormTypeEnum.PRIVATE_FAX, address.getPrivateFax() );
		}
		if ( GWTUtil.hasText( address.getWorkFax() ) ) {
			addForm( AddressEditFormTypeEnum.WORK_FAX, address.getWorkFax() );
		}
	}
}
