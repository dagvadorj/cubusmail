/* NewMessageToEmailAddressAction.java

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

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTAddress;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Compose message for email address.
 * 
 * @author Juergen Schlierf
 */
public class NewMessageToEmailAddressAction extends GWTAction implements AsyncCallback<Void> {

	private GWTAddress address;

	public NewMessageToEmailAddressAction() {

		super();
		setText( TextProvider.get().actions_newmessage_text() );
		setImageName( ImageProvider.MSG_NEW );
	}

	public void setAddress( GWTAddress address ) {

		this.address = address;
	}

	@Override
	public void execute() {

		ServiceProvider.getMailboxService().prepareNewMessage( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

		WindowRegistry.COMPOSE_MESSAGE_WINDOW.open( this.address.getInternetAddress() );
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
