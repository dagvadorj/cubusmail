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
public class AddressEditPhoneSubCanvas extends AddressEditAbstractSubCanvas {

	public AddressEditPhoneSubCanvas() {

		super( getFormList( AddressEditFormTypeEnum.PHONE_GROUP ), AddressEditFormTypeEnum.PHONE_GROUP );
	}

	public void fillAddress( Address address ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				String phone = form.getValue();
				switch (form.getType()) {
				case PRIVATE_PHONE:
					address.setPrivatePhone( phone );
					break;
				case WORK_PHONE:
					address.setWorkPhone( phone );
					break;
				case PRIVATE_MOBILE:
					address.setPrivateMobile( phone );
					break;
				case WORK_MOBILE:
					address.setWorkMobile( phone );
					break;
				case PRIVATE_FAX:
					address.setPrivateFax( phone );
					break;
				case WORK_FAX:
					address.setWorkFax( phone );
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

		if ( address != null ) {
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

	private static List<AddressEditAbstractForm> getFormList( AddressEditFormTypeEnum[] group ) {

		List<AddressEditAbstractForm> formList = new ArrayList<AddressEditAbstractForm>();
		for (int i = 0; i < group.length; i++) {
			AddressEditAbstractForm form = GWT.create( AddressEditPhoneForm.class );
			formList.add( form );
		}

		return formList;
	}
}
