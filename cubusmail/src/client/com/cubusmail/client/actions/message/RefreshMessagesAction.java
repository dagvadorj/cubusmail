/* RefreshMessagesAction.java

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
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.ImageProvider;

/**
 * Refresh message list.
 *
 * @author Juergen Schlierf
 */
public class RefreshMessagesAction extends GWTAction {

	public RefreshMessagesAction() {

		super();
		setText( TextProvider.get().actions_refresh_text() );
		setImageName( ImageProvider.MSG_REFRESH );
		setTooltipText( TextProvider.get().actions_refresh_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		EventBroker.get().fireMessagesReload();
	}
}
