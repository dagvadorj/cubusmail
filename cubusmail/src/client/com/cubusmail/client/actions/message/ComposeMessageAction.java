/* NewMessageAction.java

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
package com.cubusmail.client.actions.message;

import com.cubusmail.client.actions.GWTAction;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Create a new message.
 *
 * @author Juergen Schlierf
 */
public class ComposeMessageAction extends GWTAction implements AsyncCallback<Void> {

	public ComposeMessageAction() {

		super();
		setText( TextProvider.get().actions_newmessage_text() );
		setIcon( ImageProvider.MSG_NEW );
		setTooltip( TextProvider.get().actions_newmessage_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		ServiceProvider.getMailboxService().prepareNewMessage( this );
	}

	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
	}

	public void onSuccess( Void result ) {

		// WindowRegistry.COMPOSE_MESSAGE_WINDOW.open();
	}
}
