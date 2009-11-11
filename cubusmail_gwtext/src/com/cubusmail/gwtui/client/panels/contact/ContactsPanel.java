/* ContactListPanel.java

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
package com.cubusmail.gwtui.client.panels.contact;

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.util.UIFactory;

/**
 * Inner panel for contact list.
 * 
 * @author Juergen Schlierf
 */
public class ContactsPanel extends Panel {

	private BorderLayoutData readingPaneLayoutData;

	private MenuItem addContactItem;
	private MenuItem editContactItem;
	private MenuItem deleteContactItem;
	private MenuItem newMessageFromContactItem;

	private Menu contextMenu;

	public ContactsPanel( RegionPosition readingPanePos ) {

		super();
		setLayout( new BorderLayout() );
		setPaddings( 10 );
		setBorder( false );

		BorderLayoutData data = new BorderLayoutData( RegionPosition.CENTER );
		add( PanelRegistry.CONTACT_LIST_PANEL.get(), data );

		this.readingPaneLayoutData = new BorderLayoutData( readingPanePos );
		this.readingPaneLayoutData.setSplit( true );

		if ( readingPanePos.equals( RegionPosition.SOUTH ) ) {
			PanelRegistry.CONTACT_READING_PANE.get().setHeight( (Window.getClientHeight() - 80) / 2 );
		}
		else {
			PanelRegistry.CONTACT_READING_PANE.get().setWidth( 400 );
		}
		add( PanelRegistry.CONTACT_READING_PANE.get(), this.readingPaneLayoutData );

		initActions();
		createContextMenu();
	}

	/**
	 * 
	 */
	private void initActions() {

		BaseGridAction action = (BaseGridAction) ActionRegistry.EDIT_CONTACT.get();
		action.setStore( PanelRegistry.CONTACT_LIST_PANEL.get( ContactListPanel.class ).getStore() );
		PanelRegistry.CONTACT_LIST_PANEL.get( ContactListPanel.class ).registerAction( action );

		action = (BaseGridAction) ActionRegistry.DELETE_CONTACT.get();
		action.setStore( PanelRegistry.CONTACT_LIST_PANEL.get( ContactListPanel.class ).getStore() );
		PanelRegistry.CONTACT_LIST_PANEL.get( ContactListPanel.class ).registerAction( action );

		action = (BaseGridAction) ActionRegistry.NEW_MESSAGE_FOR_CONTACT.get();
		action.setStore( PanelRegistry.CONTACT_LIST_PANEL.get( ContactListPanel.class ).getStore() );
		PanelRegistry.CONTACT_LIST_PANEL.get( ContactListPanel.class ).registerAction( action );
	}

	/**
	 * create all context menu items
	 */
	private void createContextMenu() {

		this.contextMenu = new Menu();

		this.addContactItem = UIFactory.createMenuItem( ActionRegistry.ADD_CONTACT.get() );
		this.contextMenu.addItem( this.addContactItem );

		this.editContactItem = UIFactory.createMenuItem( ActionRegistry.EDIT_CONTACT.get() );
		this.contextMenu.addItem( this.editContactItem );

		this.deleteContactItem = UIFactory.createMenuItem( ActionRegistry.DELETE_CONTACT.get() );
		this.contextMenu.addItem( this.deleteContactItem );

		this.newMessageFromContactItem = UIFactory.createMenuItem( ActionRegistry.NEW_MESSAGE_FOR_CONTACT.get() );
		this.contextMenu.addItem( newMessageFromContactItem );
	}
}
