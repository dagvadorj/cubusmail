/* AddressEditMoreInfoSubCanvas.java

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
public class AddressEditMoreInfoSubCanvas extends AddressEditAbstractSubCanvas {

	public AddressEditMoreInfoSubCanvas() {

		super( AddressEditMoreInfoForm.class, AddressEditFormTypeEnum.MORE_INFO_GROUP );
	}

	public void fillAddress( Address address ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				String value = form.getValue();
				switch (form.getType()) {
				case TITLE:
					address.setTitle( value );
					break;
				case POSITION:
					address.setPosition( value );
					break;
				case DEPARTMENT:
					address.setDepartment( value );
					break;
				case URL:
					address.setUrl( value );
					break;
				case IM:
					address.setIm( value );
					break;
				case PAGER:
					address.setPager( value );
					break;
				}
			}
		}
	}

	public void setAddress( Address address ) {

		// remove other phone forms
		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				form.setVisible( false );
			}
		}

		if ( GWTUtil.hasText( address.getTitle() ) ) {
			addForm( AddressEditFormTypeEnum.TITLE, address.getTitle() );
		}
		if ( GWTUtil.hasText( address.getPosition() ) ) {
			addForm( AddressEditFormTypeEnum.POSITION, address.getPosition() );
		}
		if ( GWTUtil.hasText( address.getDepartment() ) ) {
			addForm( AddressEditFormTypeEnum.DEPARTMENT, address.getDepartment() );
		}
		if ( GWTUtil.hasText( address.getUrl() ) ) {
			addForm( AddressEditFormTypeEnum.URL, address.getUrl() );
		}
		if ( GWTUtil.hasText( address.getIm() ) ) {
			addForm( AddressEditFormTypeEnum.IM, address.getIm() );
		}
		if ( GWTUtil.hasText( address.getPager() ) ) {
			addForm( AddressEditFormTypeEnum.PAGER, address.getPager() );
		}
	}
}
