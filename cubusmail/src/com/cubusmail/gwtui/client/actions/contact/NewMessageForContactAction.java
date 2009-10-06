/* NewMessageForContactAction.java

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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.Record;

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;
import com.cubusmail.gwtui.domain.ContactListFields;

/**
 * Compose a message for contact.
 * 
 * @author Jürgen Schlierf
 */
public class NewMessageForContactAction extends BaseGridAction implements AsyncCallback<Void> {

	/**
	 * 
	 */
	public NewMessageForContactAction() {

		super();
		setText( TextProvider.get().actions_newmessage_tocontact_text() );
		setImageName( ImageProvider.MSG_NEW );
		setTooltipText( TextProvider.get().actions_newmessage_tocontact_tooltip() );
	}

	@Override
	public void execute() {

		ServiceProvider.getMailboxService().prepareNewMessage( this );
	}

	public void onSuccess( Void result ) {

		StringBuffer to = new StringBuffer();
		Record[] records = getSelectionModel().getSelections();
		if ( records != null && records.length > 0 ) {
			for (Record record : records) {
				String email = record.getAsString( ContactListFields.INTERNET_ADDRESS.name() );
				if ( GWTUtil.hasText( email ) ) {
					if ( to.length() > 0 ) {
						to.append( ", " );
					}
					to.append( email );
				}
			}
		}
		WindowRegistry.COMPOSE_MESSAGE_WINDOW.open( to.toString() );
	}

	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
	}
}
