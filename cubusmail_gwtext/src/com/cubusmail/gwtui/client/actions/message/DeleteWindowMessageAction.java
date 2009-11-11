/* DeleteMessagesAction.java

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
package com.cubusmail.gwtui.client.actions.message;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.actions.message.MarkMessageAction.MarkActionType;
import com.cubusmail.gwtui.client.panels.MessageReadingPanePanel;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Delete message in message window.
 * 
 * @author Juergen Schlierf
 */
public class DeleteWindowMessageAction extends DeleteMessagesAction {

	@Override
	public void execute() {

		long[] messageIds = new long[1];
		messageIds[0] = PanelRegistry.MESSAGE_READING_PANE_FOR_WINDOW.get( MessageReadingPanePanel.class ).getCurrentMessage()
				.getId();

		if ( GWTSessionManager.get().getPreferences().isMarkAsDeletedWithoutTrash()
				&& GWTSessionManager.get().getMailbox().getTrashFolder() == null ) {
			ActionRegistry.MARK_AS_DELETED.get().execute();
			ServiceProvider.getMailboxService().markMessage( messageIds, MarkActionType.DELETED.getFlagField(), true,
					this );
		}
		else {
			ServiceProvider.getMailboxService().deleteMessages( messageIds, this );
		}

		WindowRegistry.SHOW_MESSAGE_WINDOW.close();
	}
}
