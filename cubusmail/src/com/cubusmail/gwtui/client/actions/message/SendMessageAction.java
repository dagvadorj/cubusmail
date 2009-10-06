/* SendMessageAction.java

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
package com.cubusmail.gwtui.client.actions.message;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.widgets.MessageBox;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.exceptions.GWTInvalidAddressException;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.widgets.ToastMessage;
import com.cubusmail.gwtui.client.windows.ComposeMessageWindow;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Send the message.
 * 
 * @author Jürgen Schlierf
 */
public class SendMessageAction extends GWTAction implements AsyncCallback<Void> {

	/**
	 * 
	 */
	public SendMessageAction() {

		super();
		setText( TextProvider.get().actions_compose_send_text() );
		setImageName( ImageProvider.MSG_SEND );
		setTooltipText( TextProvider.get().actions_compose_send_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		if ( WindowRegistry.COMPOSE_MESSAGE_WINDOW.validate() ) {
			WindowRegistry.COMPOSE_MESSAGE_WINDOW.mask();
			GWTMessage message = WindowRegistry.COMPOSE_MESSAGE_WINDOW.get( ComposeMessageWindow.class ).getMessage();
			ServiceProvider.getMailboxService().sendMessage( message, this );
		}
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
		if ( caught instanceof GWTInvalidAddressException ) {
			GWTInvalidAddressException e = (GWTInvalidAddressException) caught;
			MessageBox.alert( TextProvider.get().common_error(), TextProvider.get().exception_compose_invalid_address(
					e.getAddress() ) );
		}
		else {
			MessageBox.alert( TextProvider.get().common_error(), TextProvider.get().exception_compose_send() );
		}
		WindowRegistry.COMPOSE_MESSAGE_WINDOW.unmask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

		WindowRegistry.COMPOSE_MESSAGE_WINDOW.close();
		ToastMessage.showMessage( TextProvider.get().actions_compose_send_hint_header(), TextProvider.get()
				.actions_compose_send_hint_text() );
		WindowRegistry.COMPOSE_MESSAGE_WINDOW.unmask();
	}
}
