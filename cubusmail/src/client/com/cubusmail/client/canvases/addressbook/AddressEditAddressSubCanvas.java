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

import java.util.ArrayList;
import java.util.List;

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.common.model.Address;
import com.google.gwt.core.client.GWT;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditAddressSubCanvas extends AddressEditAbstractSubCanvas {

	public AddressEditAddressSubCanvas() {

		super( getFormList( AddressEditFormTypeEnum.ADDRESS_GROUP ), AddressEditFormTypeEnum.ADDRESS_GROUP );
	}

	public void fillAddress( Address address ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				Address source = form.getValue();
				if ( form.getType() == AddressEditFormTypeEnum.PRVATE_ADDRESS ) {
					address.setPrivateStreet( source.getPrivateStreet() );
					address.setPrivateState( source.getPrivateState() );
					address.setPrivateZipcode( source.getPrivateZipcode() );
					address.setPrivateCity( source.getPrivateCity() );
					address.setPrivateCountry( source.getPrivateCountry() );
				}
				else if ( form.getType() == AddressEditFormTypeEnum.WORK_ADDRESS ) {
					address.setWorkStreet( source.getWorkStreet() );
					address.setWorkState( source.getWorkState() );
					address.setWorkZipcode( source.getWorkZipcode() );
					address.setWorkCity( source.getWorkCity() );
					address.setWorkCountry( source.getWorkCountry() );
				}
			}
		}
	}

	public void setAddress( Address address ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				form.setVisible( false );
			}
		}

		if ( address != null ) {
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

	private static List<AddressEditAbstractForm> getFormList( AddressEditFormTypeEnum[] group ) {

		List<AddressEditAbstractForm> formList = new ArrayList<AddressEditAbstractForm>();
		for (int i = 0; i < group.length; i++) {
			AddressEditAbstractForm form = GWT.create( AddressEditAddressForm.class );
			formList.add( form );
		}

		return formList;
	}
}
