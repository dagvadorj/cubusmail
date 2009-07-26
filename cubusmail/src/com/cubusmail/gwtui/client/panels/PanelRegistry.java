/* PanelRegistry.java

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
package com.cubusmail.gwtui.client.panels;

import java.util.HashMap;
import java.util.Map;

import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.AccordionLayout;

import com.cubusmail.gwtui.client.panels.contact.ContactFolderPanel;
import com.cubusmail.gwtui.client.panels.contact.ContactListPanel;

/**
 * Registry for all important panels in Cubusmail.
 * 
 * @author Jürgen Schlierf
 */
public enum PanelRegistry {
	MAIL_FOLDER_PANEL, LEFT_PANEL, MESSAGE_LIST_PANEL,

	CONTACT_FOLDER_PANEL, CONTACT_LIST_PANEL, CONTACT_LIST_PANEL_FOR_WINDOW, CONTACT_READING_PANE, 
	
	MESSAGE_READING_PANE_PREVIEW, MESSAGE_READING_PANE_FOR_WINDOW, LIST_DETAILS_PANEL, WORKBENCH_PANEL;

	private static Map<PanelRegistry, Panel> PANEL_MAP = new HashMap<PanelRegistry, Panel>();

	public Panel get() {

		Panel result = PANEL_MAP.get( this );
		if ( result == null ) {
			result = create();
			result.setId( this.name() );
			PANEL_MAP.put( this, result );
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends Panel> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}
	
	/**
	 * @return
	 */
	private Panel create() {

		switch ( this )
		{
			case MESSAGE_LIST_PANEL:
				return new MessageListPanel();
			case LEFT_PANEL:
				Panel leftPanel = new Panel();
				leftPanel.setBorder( true );
				leftPanel.setWidth( 200 );
				leftPanel.setLayout( new AccordionLayout( false ) );				
				return leftPanel;
			case MAIL_FOLDER_PANEL:
				return new MailfolderPanel();
			case MESSAGE_READING_PANE_PREVIEW:
			case MESSAGE_READING_PANE_FOR_WINDOW:
				return new MessageReadingPanePanel();
			case CONTACT_FOLDER_PANEL:
				return new ContactFolderPanel();
			case CONTACT_LIST_PANEL:
				return new ContactListPanel( ContactListPanel.TYPE_FOR_PANEL );
			case CONTACT_LIST_PANEL_FOR_WINDOW:
				return new ContactListPanel( ContactListPanel.TYPE_FOR_WINDOW );
			case LIST_DETAILS_PANEL:
				return new ListDetailsPanel();
			case WORKBENCH_PANEL:
				return new WorkbenchPanel();
			case CONTACT_READING_PANE:
				return new ContactReadingPanelPanel();
		}

		throw new IllegalArgumentException( "Panel missing: " + name() );
	}

	/**
	 * 
	 */
	public void mask() {

		ExtElement element = get().getEl();
		if ( element != null ) {
			element.mask();
		}
	}

	/**
	 * 
	 */
	public void unmask() {

		ExtElement element = get().getEl();
		if ( element != null ) {
			element.unmask();
		}
	}
}
