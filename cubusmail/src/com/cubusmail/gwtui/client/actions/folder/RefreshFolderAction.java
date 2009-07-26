/* RefreshFolderAction.java

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
package com.cubusmail.gwtui.client.actions.folder;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Refresh folder tree.
 * 
 * @author Jürgen Schlierf
 */
public class RefreshFolderAction extends GWTAction {
	
	/**
	 * 
	 */
	public RefreshFolderAction() {

		super();
		setText( TextProvider.get().actions_renamefolder_text() );
		setImageName( ImageProvider.MAIL_FOLDER_REFRESH );
		setTooltipText( TextProvider.get().actions_renamefolder_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		EventBroker.get().fireFoldersReload();
	}
}
