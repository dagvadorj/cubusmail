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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.common.model.Address;
import com.google.gwt.core.client.GWT;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditMoreInfoSubCanvas extends AddressEditAbstractSubCanvas {

	public AddressEditMoreInfoSubCanvas() {

		super( getFormList( AddressEditFormTypeEnum.MORE_INFO_GROUP ), AddressEditFormTypeEnum.MORE_INFO_GROUP );
	}

	public void fillAddress( Address address ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				Object value = form.getValue();
				switch (form.getType()) {
				case BIRTHDATE:
					address.setBirthDate( (Date) value );
					break;
				case TITLE:
					address.setTitle( (String) value );
					break;
				case COMPANY:
					address.setCompany( (String) value );
					break;
				case POSITION:
					address.setPosition( (String) value );
					break;
				case DEPARTMENT:
					address.setDepartment( (String) value );
					break;
				case URL:
					address.setUrl( (String) value );
					break;
				case IM:
					address.setIm( (String) value );
					break;
				case PAGER:
					address.setPager( (String) value );
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

		if ( address != null ) {
			if ( GWTUtil.hasText( address.getTitle() ) ) {
				addForm( AddressEditFormTypeEnum.TITLE, address.getTitle() );
			}
			if ( GWTUtil.hasText( address.getCompany() ) ) {
				addForm( AddressEditFormTypeEnum.COMPANY, address.getCompany() );
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
			if ( address.getBirthDate() != null ) {
				addForm( AddressEditFormTypeEnum.BIRTHDATE, address.getBirthDate() );
			}
		}
	}

	private static List<AddressEditAbstractForm> getFormList( AddressEditFormTypeEnum[] group ) {

		List<AddressEditAbstractForm> formList = new ArrayList<AddressEditAbstractForm>();
		for (int i = 0; i < group.length; i++) {
			AddressEditAbstractForm form = GWT.create( AddressEditMoreInfoForm.class );
			formList.add( form );
		}

		return formList;
	}
}
