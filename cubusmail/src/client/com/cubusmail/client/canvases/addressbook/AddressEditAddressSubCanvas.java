/* AddressEditAddressSubCanvas.java

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
public class AddressEditAddressSubCanvas extends AddressEditAbstractSubCanvas {

	public AddressEditAddressSubCanvas() {

		super( AddressEditAddressForm.class, AddressEditFormTypeEnum.ADDRESS_GROUP );
	}

	public void setAddress( Address address ) {

		if ( GWTUtil.hasText( address.getPrivateStreet() ) || GWTUtil.hasText( address.getPrivateZipcode() )
				|| GWTUtil.hasText( address.getPrivateState() ) || GWTUtil.hasText( address.getPrivateCity() )
				|| GWTUtil.hasText( address.getPrivateCountry() ) ) {
			addForm( AddressEditFormTypeEnum.PRVATE_ADDRESS, address );
		}
		if ( GWTUtil.hasText( address.getWorkStreet() ) || GWTUtil.hasText( address.getWorkZipcode() )
				|| GWTUtil.hasText( address.getWorkState() ) || GWTUtil.hasText( address.getWorkCity() )
				|| GWTUtil.hasText( address.getWorkCountry() ) ) {
			addForm( AddressEditFormTypeEnum.WORK_ADDRESS, address );
		}
	}
}
