/* AddContactAction.java

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
package com.cubusmail.gwtui.client.actions.contact;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Add contact.
 * 
 * @author Juergen Schlierf
 */
public class AddContactAction extends GWTAction {

	/**
	 * 
	 */
	public AddContactAction() {

		setText( TextProvider.get().actions_add_contact_text() );
		setImageName( ImageProvider.CONTACT_ADD );
		setTooltipText( TextProvider.get().actions_add_contact_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		WindowRegistry.CONTACT_WINDOW.open( TextProvider.get().actions_add_contact_text() );
	}
}
