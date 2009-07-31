/* ReplyAction.java

   Copyright (c) 2009 J�rgen Schlierf, All Rights Reserved
   
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

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Reply messages.
 *
 * @author J�rgen Schlierf
 */
public class ReplyAction extends BaseGridAction implements AsyncCallback<GWTMessage> {

	/**
	 * 
	 */
	public ReplyAction() {

		super( null );
		setText( TextProvider.get().actions_reply_text() );
		setImageName( ImageProvider.MSG_REPLY );
		setTooltipText( TextProvider.get().actions_reply_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		long[] messageIds = getSelectedIds();
		if ( messageIds != null && messageIds.length > 0 ) {
			ServiceProvider.getMailboxService().prepareReplyMessage( messageIds[0], false, this );
		}
	}

	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
	}

	public void onSuccess( GWTMessage result ) {

		WindowRegistry.COMPOSE_MESSAGE_WINDOW.open( result );
		WindowRegistry.SHOW_MESSAGE_WINDOW.close();
	}

}
