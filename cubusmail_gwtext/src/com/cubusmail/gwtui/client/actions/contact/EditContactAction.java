/* EditContactAction.java

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
package com.cubusmail.gwtui.client.actions.contact;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.Record;

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.panels.contact.ContactWindow;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactListFields;

/**
 * Edit contact.
 * 
 * @author Juergen Schlierf
 */
public class EditContactAction extends BaseGridAction implements AsyncCallback<Contact> {

	/**
	 * 
	 */
	public EditContactAction() {

		super();
		setText( TextProvider.get().actions_edit_contact_text() );
		setImageName( ImageProvider.CONTACT_EDIT );
		setTooltipText( TextProvider.get().actions_edit_contact_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		Record[] records = getSelectionModel().getSelections();
		if ( records != null && records.length > 0 ) {
			String id = records[0].getAsString( ContactListFields.ID.name() );
			WindowRegistry.CONTACT_WINDOW.open( TextProvider.get().actions_edit_contact_text() );
			WindowRegistry.CONTACT_WINDOW.mask();
			ServiceProvider.getUserAccountService().retrieveContact( Long.parseLong( id ), this );
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Contact result ) {

		WindowRegistry.CONTACT_WINDOW.get( ContactWindow.class ).setContactToEdit( result );
		WindowRegistry.CONTACT_WINDOW.unmask();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	public void onFailure( Throwable caught ) {

		WindowRegistry.CONTACT_WINDOW.unmask();
		GWTExceptionHandler.handleException( caught );
	}
}
