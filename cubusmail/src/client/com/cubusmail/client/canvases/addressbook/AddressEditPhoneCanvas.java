/* AddressEditPhoneCanvas.java

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
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditPhoneCanvas extends VLayout {

	private List<AddressEditPhoneForm> phoneForms = new ArrayList<AddressEditPhoneForm>(
			AddressEditFormTypeEnum.PHONE_GROUP.length );

	public AddressEditPhoneCanvas() {

		setOverflow( Overflow.VISIBLE );
		setShowEdges( true );

		for (int i = 0; i < AddressEditFormTypeEnum.PHONE_GROUP.length; i++) {
			final AddressEditPhoneForm form = new AddressEditPhoneForm();
			form.getAddItem().addIconClickHandler( new IconClickHandler() {

				@Override
				public void onIconClick( IconClickEvent event ) {

					addPhoneForm( null, null );
				}
			} );

			form.getRemoveItem().addIconClickHandler( new IconClickHandler() {

				@Override
				public void onIconClick( IconClickEvent event ) {

					removePhoneForm( form );
				}
			} );

			this.phoneForms.add( form );
			addMember( form );
		}

	}

	/**
	 * 
	 */
	public void init() {

		// remove other phone forms
		for (AddressEditPhoneForm form : this.phoneForms) {
			if ( form.isVisible() ) {
				form.setVisible( false );
			}
		}

		addPhoneForm( AddressEditFormTypeEnum.PRIVATE_PHONE, null );
	}

	/**
	 * @param address
	 */
	public void setAddress( Address address ) {

		// remove other phone forms
		for (AddressEditPhoneForm form : this.phoneForms) {
			if ( form.isVisible() ) {
				form.setVisible( false );
			}
		}

		if ( GWTUtil.hasText( address.getPrivatePhone() ) ) {
			addPhoneForm( AddressEditFormTypeEnum.PRIVATE_PHONE, address.getPrivatePhone() );
		}
		if ( GWTUtil.hasText( address.getWorkPhone() ) ) {
			addPhoneForm( AddressEditFormTypeEnum.WORK_PHONE, address.getWorkPhone() );
		}
		if ( GWTUtil.hasText( address.getPrivateMobile() ) ) {
			addPhoneForm( AddressEditFormTypeEnum.PRIVATE_MOBILE, address.getPrivateMobile() );
		}
		if ( GWTUtil.hasText( address.getWorkMobile() ) ) {
			addPhoneForm( AddressEditFormTypeEnum.WORK_MOBILE, address.getWorkMobile() );
		}
		if ( GWTUtil.hasText( address.getPrivateFax() ) ) {
			addPhoneForm( AddressEditFormTypeEnum.PRIVATE_FAX, address.getPrivateFax() );
		}
		if ( GWTUtil.hasText( address.getWorkFax() ) ) {
			addPhoneForm( AddressEditFormTypeEnum.WORK_FAX, address.getWorkFax() );
		}
	}

	/**
	 * 
	 */
	private void addPhoneForm( AddressEditFormTypeEnum type, String value ) {

		for (AddressEditPhoneForm form : this.phoneForms) {
			if ( !form.isVisible() ) {
				AddressEditFormTypeEnum[] types = getAvailableFormTypes();
				form.setFormTypes( types );
				if ( type == null ) {
					form.setType( types[0] );
				}
				else {
					form.setType( type );
				}
				form.setValue( value );
				form.setVisible( true );
				break;
			}
		}
		setAddButtonVisibility();
	}

	/**
	 * @param form
	 */
	private void removePhoneForm( AddressEditPhoneForm form ) {

		int index = this.phoneForms.indexOf( form );
		for (int i = index; i < this.phoneForms.size() - 1; i++) {
			AddressEditPhoneForm target = this.phoneForms.get( i );
			AddressEditPhoneForm source = this.phoneForms.get( i + 1 );
			target.setValue( source.getValue() );
			target.setType( source.getType() );
			target.setSelectionTypes( source.getSelectionTypes() );
		}
		for (int i = this.phoneForms.size() - 1; i >= 0; i--) {
			if ( this.phoneForms.get( i ).isVisible() ) {
				this.phoneForms.get( i ).setVisible( false );
				break;
			}
		}

		setAddButtonVisibility();
	}

	/**
	 * 
	 */
	private void setAddButtonVisibility() {

		// all input fields visible
		boolean visible = true;
		if ( getAvailableFormTypesCount() == 0 ) {
			visible = false;
		}

		for (int i = this.phoneForms.size() - 2; i >= 0; i--) {
			if ( this.phoneForms.get( i ).isVisible() ) {
				this.phoneForms.get( i ).getAddItem().setVisible( visible );
				if ( this.phoneForms.get( i ).isDrawn() ) {
					this.phoneForms.get( i ).redraw();
				}
				visible = false;
			}
		}
	}

	/**
	 * @return
	 */
	private AddressEditFormTypeEnum[] getAvailableFormTypes() {

		List<AddressEditFormTypeEnum> typeList = new ArrayList<AddressEditFormTypeEnum>();
		for (int i = 0; i < this.phoneForms.size(); i++) {
			if ( !this.phoneForms.get( i ).isVisible() ) {
				typeList.add( AddressEditFormTypeEnum.PHONE_GROUP[i] );
			}
		}

		return typeList.toArray( new AddressEditFormTypeEnum[0] );
	}

	/**
	 * @return
	 */
	private int getAvailableFormTypesCount() {

		int count = 0;
		for (AddressEditPhoneForm form : this.phoneForms) {
			if ( !form.isVisible() ) {
				count++;
			}
		}

		return count;
	}
}
