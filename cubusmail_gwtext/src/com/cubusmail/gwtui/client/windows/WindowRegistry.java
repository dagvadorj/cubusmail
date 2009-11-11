/* WindowRegistry.java

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
package com.cubusmail.gwtui.client.windows;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Window;

import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.panels.contact.ContactWindow;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.Contact;

/**
 * Registry of all windows for Cubusmail.
 * 
 * @author Juergen Schlierf
 */
public enum WindowRegistry {
	COMPOSE_MESSAGE_WINDOW, SHOW_MESSAGE_WINDOW, PREFERENCES_WINDOW, COPY_MESSAGES_WINDOW, MOVE_MESSAGES_WINDOW, MOVE_FOLDER_WINDOW,

	CONTACT_LIST_WINDOW, CONTACT_WINDOW;

	private static Map<WindowRegistry, IGWTWindow> WINDOW_MAP = new HashMap<WindowRegistry, IGWTWindow>();

	/**
	 * @return
	 */
	public IGWTWindow get() {

		IGWTWindow result = WINDOW_MAP.get( this );
		if ( result == null ) {
			result = create();
			result.setId( this.name() );
			WINDOW_MAP.put( this, result );
		}

		return result;
	}

	/**
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IGWTWindow> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}

	/**
	 * @return
	 */
	private IGWTWindow create() {

		Window window = null;

		switch (this) {
		case COMPOSE_MESSAGE_WINDOW:
			return new ComposeMessageWindow();
		case SHOW_MESSAGE_WINDOW:
			return new ShowMessageWindow();
		case PREFERENCES_WINDOW:
			return new PreferencesWindow();
		case COPY_MESSAGES_WINDOW:
			window = new MailFolderWindow();
			window.setTitle( TextProvider.get().actions_copymessages_text() );
			return (IGWTWindow) window;
		case MOVE_MESSAGES_WINDOW:
			window = new MailFolderWindow();
			window.setTitle( TextProvider.get().actions_movemessage_title() );
			return (IGWTWindow) window;
		case MOVE_FOLDER_WINDOW:
			return new MailFolderWindow();
		case CONTACT_LIST_WINDOW:
			return new ContactListWindow();
		case CONTACT_WINDOW:
			return new ContactWindow();
		}

		throw new IllegalArgumentException( "Window missing: " + name() );
	}

	/**
	 * @param data
	 */
	private void setData( Object... data ) {

		switch (this) {
		case COMPOSE_MESSAGE_WINDOW:
			if ( data[0] instanceof GWTMessage ) {
				get( ComposeMessageWindow.class ).setMessage( (GWTMessage) data[0] );
			}
			else if ( data[0] instanceof String ) {
				get( ComposeMessageWindow.class ).setTo( (String) data[0] );
			}
			break;
		case SHOW_MESSAGE_WINDOW:
			get( ShowMessageWindow.class ).setMessage( (GWTMessage) data[0] );
			break;
		case MOVE_FOLDER_WINDOW:
			get( MailFolderWindow.class ).setTitle( TextProvider.get().actions_movefolder_title( (String) data[0] ) );
			break;
		case CONTACT_WINDOW:
			get( ContactWindow.class ).setTitle( (String) data[0] );
			if ( data.length > 1 ) {
				get( ContactWindow.class ).setContactToEdit( (Contact) data[1] );
			}
			else {
				Contact newContact = GWT.create( Contact.class );
				get( ContactWindow.class ).setContactToEdit( newContact );
			}
			break;
		}
	}

	/**
	 * 
	 */
	public void open() {

		get().init();
		if ( !get().isVisible() ) {
			get().show();
		}
	}

	/**
	 * 
	 */
	public void open( Object... data ) {

		get().init();
		if ( data != null ) {
			setData( data );
		}
		if ( !get().isVisible() ) {
			get().show();
		}
	}

	/**
	 * 
	 */
	public void close() {

		if ( get().isVisible() ) {
			get().hide();
		}
	}

	/**
	 * 
	 */
	public void mask() {

		ExtElement element = ((Window) get()).getEl();
		if ( element != null ) {
			element.mask();
		}
	}

	/**
	 * 
	 */
	public void unmask() {

		ExtElement element = ((Window) get()).getEl();
		if ( element != null ) {
			element.unmask();
		}
	}

	/**
	 * @return
	 */
	public boolean validate() {

		return get().validate();
	}
}
