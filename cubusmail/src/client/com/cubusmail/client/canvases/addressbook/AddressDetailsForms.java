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

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.common.model.Address;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public enum AddressDetailsForms {
	DETAIL_HEADER(""), PRIVATE_PHONE("Private Phone"), WORK_PHONE("Work Phone"), PRIVATE_MOBILE("Private Mobile"), WORK_MOBILE(
			"Work Mobile"), PRIVATE_FAX("Private Fax"), WORK_FAX("Work Fax"),

	EMAIL1("Email 1"), EMAIL2("Email 2"), EMAIL3("Email 3"), EMAIL4("Email 4"), EMAIL5("Email 5"), URL("URL"), IM("IM"),

	PRIVATE_ADDRESS("Private Address"), WORK_ADDRESS("Work Address"),

	TITLE("Title"), COMPANY("Company"), POSITION("Position"), DEPARTMENT("Department");

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
			return createHeaderSubForm();
		case PRIVATE_PHONE:
		case WORK_PHONE:
		case PRIVATE_MOBILE:
		case WORK_MOBILE:
		case PRIVATE_FAX:
		case WORK_FAX:
		case IM:
		case TITLE:
		case COMPANY:
		case POSITION:
		case DEPARTMENT:
			return createStandardSubForm( this );
		case EMAIL1:
		case EMAIL2:
		case EMAIL3:
		case EMAIL4:
		case EMAIL5:
		case URL:
			return createLinkSubForm( this );
		case PRIVATE_ADDRESS:
		case WORK_ADDRESS:
			return createAddressSubForm( this );
		}

		throw new IllegalArgumentException( "AddressDetailsForms type missing: " + name() );
	}

	/**
	 * @param address
	 */
	public static void setAddress( Address address ) {

		for (AddressDetailsForms form : values()) {
			if ( address != null ) {
				form.get().setAddress( address );
			}
			else {
				form.get().setVisible( false );
			}
		}
	}

	public String getTitle() {

		return title;
	};

	/************************************************************************/
	/* creating Subforms *************************************************** */
	/************************************************************************/
	private AddressDetailSubForm createHeaderSubForm() {

		return new AddressDetailSubForm( this ) {

			@Override
			public void init() {

				super.init();
				setBackgroundColor( "#EEEEEE" );
				setTitleSuffix( "" );

				this.formItem.setTitle( "" );
				this.formItem.setTextBoxStyle( "addressDetailsHeader" );
			}

			@Override
			protected String getValue( Address address ) {

				return address.getAddressDetailName();
			}
		};
	}

	private AddressDetailSubForm createStandardSubForm( final AddressDetailsForms form ) {

		return new AddressDetailSubForm( this ) {

			@Override
			protected String getValue( Address address ) {

				switch (this.detailsForm) {
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
				case IM:
					return address.getIm();
				case TITLE:
					return address.getTitle();
				case COMPANY:
					return address.getCompany();
				case POSITION:
					return address.getPosition();
				case DEPARTMENT:
					return address.getDepartment();
				}

				throw new IllegalArgumentException( "AddressDetailsForms type missing: " + name() );
			}
		};
	}

	private AddressDetailSubForm createLinkSubForm( final AddressDetailsForms form ) {

		return new AddressDetailSubForm( this ) {

			@Override
			protected void init() {

				this.formItem = new LinkItem( "linkItem" );
				this.formItem.setTitleStyle( "addressDetailsTitle" );
				this.formItem.setTitle( this.detailsForm.getTitle() );
				setItems( this.formItem );
			}

			@Override
			protected String getValue( Address address ) {

				switch (this.detailsForm) {
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
				case URL:
					return address.getUrl();
				}

				throw new IllegalArgumentException( "AddressDetailsForms type missing: " + name() );
			}
		};
	}

	private AddressDetailSubForm createAddressSubForm( final AddressDetailsForms form ) {

		return new AddressDetailSubForm( this ) {

			private StaticTextItem streetItem;
			private StaticTextItem zipCityItem;
			private StaticTextItem countryItem;

			@Override
			protected void init() {

				setTitleSuffix( "" );
				this.streetItem = new StaticTextItem( "streetItem", "" );
				this.streetItem.setTitleStyle( "addressDetailsTitle" );
				this.zipCityItem = new StaticTextItem( "zipCityItem", "" );
				this.zipCityItem.setTitleStyle( "addressDetailsTitle" );
				this.countryItem = new StaticTextItem( "countryItem", "" );
				this.countryItem.setTitleStyle( "addressDetailsTitle" );
				setItems( this.streetItem, this.zipCityItem, this.countryItem );
			}

			@Override
			public void setAddress( Address address ) {

				String street = this.detailsForm == PRIVATE_ADDRESS ? address.getPrivateStreet() : address
						.getWorkStreet();
				String zipCode = this.detailsForm == PRIVATE_ADDRESS ? address.getPrivateZipcode() : address
						.getWorkZipcode();
				String city = this.detailsForm == PRIVATE_ADDRESS ? address.getPrivateCity() : address.getWorkCity();
				String country = this.detailsForm == PRIVATE_ADDRESS ? address.getPrivateCountry() : address
						.getWorkCountry();

				if ( GWTUtil.hasText( street ) || GWTUtil.hasText( zipCode ) || GWTUtil.hasText( city )
						|| GWTUtil.hasText( country ) ) {
					boolean isTitleSet = false;
					if ( GWTUtil.hasText( street ) ) {
						this.streetItem.setTitle( this.detailsForm.getTitle() + " :" );
						isTitleSet = true;
						this.streetItem.setValue( street );
						this.streetItem.setVisible( true );
					}
					else {
						this.streetItem.setVisible( false );
					}

					if ( GWTUtil.hasText( zipCode ) || GWTUtil.hasText( city ) ) {
						if ( !isTitleSet ) {
							this.zipCityItem.setTitle( this.detailsForm.getTitle() + " :" );
							isTitleSet = true;
						}
						String value = "";
						if ( GWTUtil.hasText( zipCode ) ) {
							value = zipCode + " ";
						}
						if ( GWTUtil.hasText( city ) ) {
							value += city;
						}
						this.zipCityItem.setValue( value );
						this.zipCityItem.setVisible( true );
					}
					else {
						this.zipCityItem.setVisible( false );
					}

					if ( GWTUtil.hasText( country ) ) {
						if ( !isTitleSet ) {
							this.countryItem.setTitle( this.detailsForm.getTitle() + " :" );
						}
						this.countryItem.setValue( country );
						this.countryItem.setVisible( true );
					}
					else {
						this.countryItem.setVisible( false );
					}
					setVisible( true );
				}
			}

			@Override
			protected String getValue( Address address ) {

				return null;
			}
		};
	}
}
