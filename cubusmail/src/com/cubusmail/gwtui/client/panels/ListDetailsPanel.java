/* ListDetailsPanel.java

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
package com.cubusmail.gwtui.client.panels;

import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.CardLayout;

import com.cubusmail.gwtui.client.panels.contact.ContactsPanel;

/**
 * Panel for list and reading pane.
 * 
 * @author Juergen Schlierf
 */
public class ListDetailsPanel extends Panel {

	private MessagesPanel messagesPanel;
	private ContactsPanel contactsPanel;

	/**
	 * 
	 */
	public ListDetailsPanel() {

		super();
		setLayout( new CardLayout() );
		setBodyBorder( false );
		setAutoScroll( false );
		setAutoDestroy( false );
		setMessagesPanelActive();
	}

	public MessagesPanel getMessagesPanel() {

		if ( this.messagesPanel == null ) {
			this.messagesPanel = new MessagesPanel();
			add( this.messagesPanel );
		}

		return this.messagesPanel;
	}

	public ContactsPanel getContactsPanel() {

		if ( this.contactsPanel == null ) {
			this.contactsPanel = new ContactsPanel( RegionPosition.SOUTH );
			add( this.contactsPanel );
		}

		return this.contactsPanel;
	}

	/**
	 * 
	 */
	public void setMessagesPanelActive() {

		setActiveItemID( getMessagesPanel().getId() );
		ToolbarManager.get().showMessageToolbarItems();
		ToolbarManager.get().hideContactToolbarItems();
	}

	/**
	 * 
	 */
	public void setContactsPanelActive() {

		setActiveItemID( getContactsPanel().getId() );
		ToolbarManager.get().showContactToolbarItems();
		ToolbarManager.get().hideMessageToolbarItems();
	}
}
