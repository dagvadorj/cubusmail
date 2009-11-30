/* ReplyAllAction.java

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
 * Reply messages to all.
 *
 * @author Juergen Schlierf
 */
public class ReplyAllAction extends ReplyAction {

	/**
	 * 
	 */
	public ReplyAllAction() {

		setText( TextProvider.get().actions_replyall_text() );
		setImageName( ImageProvider.MSG_REPLY_ALL );
		setTooltipText( TextProvider.get().actions_replyall_tooltip() );
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
			ServiceProvider.getMailboxService().prepareReplyMessage( messageIds[0], true, this );
		}
	}
}
