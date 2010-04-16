/* AddressEditFormsManagerEnum.java

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

import com.cubusmail.common.model.Address;
import com.smartgwt.client.widgets.form.DynamicForm;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public enum AddressEditFormsManagerEnum {
	DETAIL_NAME(""), PRIVATE_PHONE("Private Phone"), WORK_PHONE("Work Phone"), PRIVATE_MOBILE("Private Mobile"), WORK_MOBILE(
			"Work Mobile"), PRIVATE_FAX("Private Fax"), WORK_FAX("Work Fax");

	public final static AddressEditFormsManagerEnum[] PHONE_GROUP = { PRIVATE_PHONE, WORK_PHONE, PRIVATE_MOBILE,
			WORK_MOBILE, PRIVATE_FAX, WORK_FAX };

	private String title;
	private IAddressEditForm form;

	private AddressEditFormsManagerEnum( String title ) {

		this.title = title;
	}

	public IAddressEditForm getForm() {

		if ( this.form == null ) {
			this.form = create();
		}

		return this.form;
	}

	public DynamicForm getDynmaicForm() {

		return (DynamicForm) getForm();
	}

	private IAddressEditForm create() {

		switch (this) {
		case DETAIL_NAME:
			return new AddressEditNameForm();
		case PRIVATE_PHONE:
		case WORK_PHONE:
		case PRIVATE_MOBILE:
		case WORK_MOBILE:
		case PRIVATE_FAX:
		case WORK_FAX:
			return new AddressEditPhoneForm( this );
		}

		throw new IllegalArgumentException( "AddressEditFormsManagerEnum type missing: " + name() );
	}

	/**
	 * @return
	 */
	public String getTitle() {

		return title;
	}

	public static void setAddress( Address address ) {

		if ( address == null ) {
			for (AddressEditFormsManagerEnum formsManager : values()) {
				formsManager.setVisible( false );
			}
			DETAIL_NAME.setVisible( true );
			addPhoneForm();
		}
	}

	public static DynamicForm[] getAllDynamicForms() {

		return null;
	}

	private String[] getUnusedPhoneTypes() {

		List<String> types = new ArrayList<String>();
		types.add( this.title );
		for (AddressEditFormsManagerEnum formsManager : PHONE_GROUP) {
			if ( formsManager != this && !formsManager.isVisible() ) {
				types.add( formsManager.title );
			}
		}
		return types.toArray( new String[0] );
	}

	public static void addPhoneForm() {

		for (AddressEditFormsManagerEnum formsManager : PHONE_GROUP) {
			AddressEditAbstractForm form = (AddressEditAbstractForm) formsManager.getForm();
			if ( !form.isVisible() ) {
				String[] phoneTypes = formsManager.getUnusedPhoneTypes();
				form.setSelectionTypes( phoneTypes );
				form.setSelectionType( phoneTypes[0] );
				form.setVisible( true );
				break;
			}
			else {
				form.setSelectionTypes( formsManager.getUnusedPhoneTypes() );
			}
		}
	}

	public void setVisible( boolean visible ) {

		getDynmaicForm().setVisible( visible );
	}

	public boolean isVisible() {

		return getDynmaicForm().isVisible();
	}
}
