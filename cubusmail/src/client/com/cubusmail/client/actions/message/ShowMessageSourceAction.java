/* ShowMessageSourceAction.java

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

import com.cubusmail.client.actions.BaseGridAction;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.user.client.Window;

/**
 * Show the source of a message.
 * 
 * @author Juergen Schlierf
 */
public class ShowMessageSourceAction extends BaseGridAction {

	/**
	 * 
	 */
	public ShowMessageSourceAction() {

		super();
		setText( TextProvider.get().actions_showmessagesource_text() );
		setTooltip( TextProvider.get().actions_showmessagesource_tooltip() );
		setIcon( ImageProvider.MSG_SOURCE );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		long[] messageIds = getSelectedIds();
		if ( messageIds != null && messageIds.length > 0 ) {
			String messageId = Long.toString( messageIds[0] );
			String url = ServiceProvider.getMessageSourceServletUrl( messageId );
			Window.open( url, "MessageSource", "" );
		}
	}
}
