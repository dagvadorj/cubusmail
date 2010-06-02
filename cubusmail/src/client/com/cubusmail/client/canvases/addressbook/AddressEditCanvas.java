/* AddressEditCanvas.java

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

import com.cubusmail.common.model.Address;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditCanvas extends VLayout {

	private AddressEditNameForm nameForm;
	private AddressEditPhoneSubCanvas phoneSubCanvas;
	private AddressEditEmailSubCanvas emailSubCanvas;
	private AddressEditAddressSubCanvas addressSubCanvas;
	private AddressEditMoreInfoSubCanvas moreInfoSubCanvas;

	private Address currentAddress;

	private Button saveButton;

	public AddressEditCanvas() {

		super();
		setOverflow( Overflow.SCROLL );
		setWidth100();
		setMembersMargin( 15 );

		this.nameForm = new AddressEditNameForm();
		this.phoneSubCanvas = new AddressEditPhoneSubCanvas();
		this.emailSubCanvas = new AddressEditEmailSubCanvas();
		this.addressSubCanvas = new AddressEditAddressSubCanvas();
		this.moreInfoSubCanvas = new AddressEditMoreInfoSubCanvas();

		this.saveButton = new Button( "Save" );
		this.saveButton.setHeight( 30 );
		this.saveButton.setPadding( 20 );
		this.saveButton.setLeft( 20 );

		setMembers( this.nameForm, this.phoneSubCanvas, this.emailSubCanvas, this.addressSubCanvas,
				this.moreInfoSubCanvas, this.saveButton );

		init();
	}

	private void init() {

		this.phoneSubCanvas.init();
		this.emailSubCanvas.init();
		this.addressSubCanvas.init();
		this.moreInfoSubCanvas.init();
	}

	public void setAddress( Address address ) {

		this.currentAddress = address;
		this.nameForm.setAddress( address );
		this.phoneSubCanvas.setAddress( address );
		this.emailSubCanvas.setAddress( address );
		this.addressSubCanvas.setAddress( address );
		this.moreInfoSubCanvas.setAddress( address );
	}

	public Address getAddress() {

		Address result = new Address();

		this.nameForm.fillAddress( result );
		this.phoneSubCanvas.fillAddress( result );
		this.emailSubCanvas.fillAddress( result );
		this.addressSubCanvas.fillAddress( result );
		this.moreInfoSubCanvas.fillAddress( result );

		if ( this.currentAddress != null ) {
			result.setAddressFolder( this.currentAddress.getAddressFolder() );
			result.setId( this.currentAddress.getId() );
		}

		return result;
	}

	/**
	 * @param handler
	 */
	public void addSaveButtonHandler( ClickHandler handler ) {

		this.saveButton.addClickHandler( handler );
	}
}
