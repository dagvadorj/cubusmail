/* AddressDetailsForms.java

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

import java.util.HashMap;
import java.util.Map;

import com.cubusmail.common.model.Address;
import com.smartgwt.client.widgets.form.fields.LinkItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public enum AddressDetailsForms {
	DETAIL_HEADER(""), PRIVATE_PHONE("Private Phone"), WORK_PHONE("Work Phone"), PRIVATE_MOBILE("Private Mobile"), WORK_MOBILE(
			"Work Mobile"), PRIVATE_FAX("Private Fax"), WORK_FAX("Work Fax"),

	EMAIL1("Email 1"), EMAIL2("Email 2"), EMAIL3("Email 3"), EMAIL4("Email 4"), EMAIL5("Email 5"), URL("URL"), IM("IM"),

	COMPANY("Company"), POSITION("Position"), DEPARTMENT("Department"), TITLE("Title");

	private final static Map<AddressDetailsForms, AddressDetailSubForm> FORM_MAP = new HashMap<AddressDetailsForms, AddressDetailSubForm>();

	private String title;

	private AddressDetailsForms( String title ) {

		this.title = title;
	}

	public AddressDetailSubForm get() {

		AddressDetailSubForm result = FORM_MAP.get( this );
		if ( result == null ) {
			result = create();
			result.setID( this.name() );
			FORM_MAP.put( this, result );
		}

		return result;
	}

	private AddressDetailSubForm create() {

		switch (this) {
		case DETAIL_HEADER:
			return createHeaderLine();
		case PRIVATE_PHONE:
		case WORK_PHONE:
		case PRIVATE_MOBILE:
		case WORK_MOBILE:
		case PRIVATE_FAX:
		case WORK_FAX:
			return createStandardTextLine( this );
		case EMAIL1:
		case EMAIL2:
		case EMAIL3:
		case EMAIL4:
		case EMAIL5:
			return createLinkLine( this );
		}

		throw new IllegalArgumentException( "AddressDetailsForms type missing: " + name() );
	}

	/**
	 * @param address
	 */
	public static void setAddress( Address address ) {

		for (AddressDetailsForms form : values()) {
			form.get().setAddress( address );
		}
	}

	/************************************************************************/
	/* creating Subforms *************************************************** */
	/************************************************************************/
	private AddressDetailSubForm createHeaderLine() {

		return new AddressDetailSubForm( this.title ) {

			@Override
			public void init( String title ) {

				super.init( title );
				setBackgroundColor( "#EEEEEE" );
				setColWidths( "100%" );

				this.formItem.setShowTitle( false );
				this.formItem.setTextBoxStyle( "addressDetailsHeader" );
			}

			@Override
			protected String getValue( Address address ) {

				return address.getAddressDetailName();
			}
		};
	}

	private AddressDetailSubForm createStandardTextLine( final AddressDetailsForms form ) {

		return new AddressDetailSubForm( this.title ) {

			@Override
			protected String getValue( Address address ) {

				switch (form) {
				case PRIVATE_PHONE:
					return address.getPrivatePhone();
				case WORK_PHONE:
					return address.getWorkPhone();
				case PRIVATE_MOBILE:
					return address.getPrivateMobile();
				case WORK_MOBILE:
					return address.getWorkMobile();
				case PRIVATE_FAX:
					return address.getPrivateFax();
				case WORK_FAX:
					return address.getWorkFax();
				}

				throw new IllegalArgumentException( "AddressDetailsForms type missing: " + name() );
			}
		};
	}

	private AddressDetailSubForm createLinkLine( final AddressDetailsForms form ) {

		return new AddressDetailSubForm( this.title ) {

			@Override
			protected void init( String title ) {

				this.formItem = new LinkItem( "linkItem" );
				this.formItem.setTitle( title );
				setItems( this.formItem );
			}

			@Override
			protected String getValue( Address address ) {

				switch (form) {
				case EMAIL1:
					return address.getEmail();
				case EMAIL2:
					return address.getEmail2();
				case EMAIL3:
					return address.getEmail3();
				case EMAIL4:
					return address.getEmail4();
				case EMAIL5:
					return address.getEmail5();
				}

				throw new IllegalArgumentException( "AddressDetailsForms type missing: " + name() );
			}
		};
	}
}
