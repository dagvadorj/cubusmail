/* RetrieveMessageAction.java

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
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.MessageListFields;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

/**
 * Action for loading messages from server.
 * 
 * @author Juergen Schlierf
 */
public class LoadMessageAction extends GWTAction implements AsyncCallback<GWTMessage>, RecordClickHandler {

	private long messageId;
	private boolean loadImages;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		ServiceProvider.getMailboxService().retrieveMessage( null, this.messageId, this.loadImages, this );
	}

	public void onSuccess( GWTMessage result ) {

		GWTSessionManager.get().setCurrentMessage( result );
		EventBroker.get().fireMessageLoaded( result );
	};

	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
	}

	public void setMessageId( long messageId ) {

		this.messageId = messageId;
	}

	public void setLoadImages( boolean loadImages ) {

		this.loadImages = loadImages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smartgwt.client.widgets.grid.events.RecordClickHandler#onRecordClick
	 * (com.smartgwt.client.widgets.grid.events.RecordClickEvent)
	 */
	public void onRecordClick( RecordClickEvent event ) {

		int selected = event.getViewer().getSelection() != null ? event.getViewer().getSelection().length : 0;
		if ( selected == 1 ) {
			String messageID = event.getRecord().getAttribute( MessageListFields.ID.name() );
			setLoadImages( true );
			setMessageId( Long.parseLong( messageID ) );
			execute();
		}
	}
}
