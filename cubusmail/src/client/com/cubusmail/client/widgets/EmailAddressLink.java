/* EmailAddressLink.java

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
package com.cubusmail.client.widgets;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.actions.contact.AddContactFromEmailAddressAction;
import com.cubusmail.client.actions.contact.ComposeMessageForEmailAddressAction;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.common.model.GWTEmailAddress;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.menu.Menu;

/**
 * Hyperlink for email addresses.
 * 
 * @author Juergen Schlierf
 */
public class EmailAddressLink extends Anchor {

	private Menu contextMenu;
	private GWTEmailAddress emailAddress;

	public EmailAddressLink( GWTEmailAddress address ) {

		super();
		setStyleName( "emailAddressLink" );
		setText( address.getInternetAddress() );
		setHref( "#" );

		this.emailAddress = address;
		GWTUtil.disableContextMenu( getElement() );

		addMouseDownHandler( new MouseDownHandler() {

			public void onMouseDown( MouseDownEvent event ) {

				ActionRegistry.ADD_CONTACT_FROM_EMAILADDRESS.get( AddContactFromEmailAddressAction.class ).setAddress(
						emailAddress );
				ActionRegistry.COMPOSE_MESSAGE_FOR_EMAIL.get( ComposeMessageForEmailAddressAction.class ).setAddress(
						emailAddress );
				if ( contextMenu != null ) {
					contextMenu.setLeft( event.getClientX() - 10 );
					contextMenu.setTop( event.getClientY() - 10 );
					contextMenu.setVisibility( Visibility.VISIBLE );
					contextMenu.draw();
				}
			}
		} );
	}

	public void setContextMenu( Menu contextMenu ) {

		this.contextMenu = contextMenu;
	}
}
