/* ForwardAction.java

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

import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.ImageProvider;

/**
 * Forward a message.
 *
 * @author Juergen Schlierf
 */
public class ForwardAction extends ReplyAction {

	/**
	 * 
	 */
	public ForwardAction() {

		setText( TextProvider.get().actions_forward_text() );
		setImageName( ImageProvider.MSG_FORWARD );
		setTooltipText( TextProvider.get().actions_forward_tooltip() );
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
			ServiceProvider.getMailboxService().prepareForwardMessage( messageIds[0], this );
		}
	}
}
