/* CancelSendAction.java

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

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Discard the composed message
 * 
 * @author Jürgen Schlierf
 */
public class CancelSendAction extends GWTAction {

	private boolean toClose = false;

	/**
	 * 
	 */
	public CancelSendAction() {

		super();
		setText( TextProvider.get().common_button_cancel() );
		setImageName( ImageProvider.CANCEL );
		setTooltipText( TextProvider.get().actions_compose_cancel_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		WindowRegistry.COMPOSE_MESSAGE_WINDOW.close();
		ServiceProvider.getMailboxService().cancelComposeMessage( new AsyncCallbackAdapter<Object>() {
		} );
		
		// MessageBox.confirm( "Abbruch",
		// "Die eingegebene Nachricht geht verloren. Wollen Sie wirklich abbrechen?",
		// new MessageBox.ConfirmCallback() {
		//
		// public void execute( String btnID ) {
		//
		// if ( "yes".equals( btnID ) ) {
		// toClose = true;
		// WindowManager.get().closeComposeMessageWindow();
		// }
		// }
		// } );
	}

	/**
	 * @return Returns the toClose.
	 */
	public boolean isToClose() {

		return this.toClose;
	}
}
