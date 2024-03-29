/* AddContactAction.java

   Copyright (c) 2009 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.client.actions.contact;

import java.util.List;

import com.cubusmail.client.actions.GWTAction;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.AsyncCallbackAdapter;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.GWTEmailAddress;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Add a contact from email address.
 * 
 * @author Juergen Schlierf
 */
public class AddContactFromEmailAddressAction extends GWTAction implements AsyncCallback<Void> {

	private GWTEmailAddress address;

	public AddContactFromEmailAddressAction() {

		super();
		setText( TextProvider.get().actions_add_to_contactlist() );
		setIcon( ImageProvider.CONTACT_ADD );
	}

	public void setAddress( GWTEmailAddress address ) {

		this.address = address;
	}

	@Override
	public void execute() {

		if ( GWTSessionManager.get().getStandardContactFolder() == null ) {
			ServiceProvider.getUserAccountService().retrieveAddressFolders(
					new AsyncCallbackAdapter<List<AddressFolder>>() {

						@Override
						public void onSuccess( List<AddressFolder> result ) {

							GWTSessionManager.get().setAddressFolderList( result );
							saveAddress();
						}
					} );
		}
		else {
			saveAddress();
		}
	}

	/**
	 * 
	 */
	private void saveAddress() {

		Address address = GWT.create( Address.class );
		address.setAddressFolder( GWTSessionManager.get().getStandardContactFolder() );
		address.setEmail( this.address.getEmail() );
		address.setLastName( this.address.getName() );

		ServiceProvider.getUserAccountService().saveAddress( address, this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
	}
}
