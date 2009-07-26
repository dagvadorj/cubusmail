/* AddContactAction.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.gwtui.client.actions.contact;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTAddress;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactFolder;

/**
 * Add a contact from email address.
 * 
 * @author Jürgen Schlierf
 */
public class AddContactFromEmailAddressAction extends GWTAction implements AsyncCallback<Object> {

	private GWTAddress address;

	public AddContactFromEmailAddressAction() {

		super();
		setText( TextProvider.get().actions_add_to_contactlist() );
		setImageName( ImageProvider.CONTACT_ADD );
	}

	public void setAddress( GWTAddress address ) {

		this.address = address;
	}

	@Override
	public void execute() {

		if ( GWTSessionManager.get().getStandardContactFolder() == null ) {
			ServiceProvider.getUserAccountService().retrieveContactFolders(
					new AsyncCallbackAdapter<List<ContactFolder>>() {

						@Override
						public void onSuccess( List<ContactFolder> result ) {

							GWTSessionManager.get().setContactFolderList( result );
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

		Contact contact = GWT.create( Contact.class );
		contact.setContactFolder( GWTSessionManager.get().getStandardContactFolder() );
		contact.setEmail( this.address.getEmail() );
		contact.setLastName( this.address.getName() );

		ServiceProvider.getUserAccountService().saveContact( contact, this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Object result ) {

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
