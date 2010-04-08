/* EventBroker.java

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
package com.cubusmail.client.events;

import java.util.ArrayList;
import java.util.List;

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.Preferences;

/**
 * Global event broker
 * 
 * @author Juergen Schlierf
 */
public class EventBroker {

	private static EventBroker instance;

	private List<LogoutListener> logoutListenerList = null;

	private List<FoldersReloadListener> foldersReloadListenerList = null;

	private List<FolderSelectedListener> folderSelectedListenerList = null;

	private List<MessageLoadedListener> messageLoadedListenerList = null;

	private List<MessagesReloadListener> messagesReloadListenerList = null;

	private List<MessagesChangedListener> messagesChangedListenerList = null;

	private List<CleanReadingPaneListener> cleanReadingPaneListenerList = null;

	private List<AddressFolderSelectedListener> addressFolderSelectedListenerList = null;

	private List<AddressLoadedListener> addressLoadedListenerList = null;

	private List<ReloadAddressListListener> reloadAdressListListenerList = null;

	private List<PreferencesChangedListener> preferencesChangedListenerList = null;

	private List<AddressSelectedListener> addressSelectedListenerList = null;

	private EventBroker() {

		this.logoutListenerList = new ArrayList<LogoutListener>();
		this.foldersReloadListenerList = new ArrayList<FoldersReloadListener>();
		this.folderSelectedListenerList = new ArrayList<FolderSelectedListener>();
		this.messageLoadedListenerList = new ArrayList<MessageLoadedListener>();
		this.addressSelectedListenerList = new ArrayList<AddressSelectedListener>();
		this.messagesReloadListenerList = new ArrayList<MessagesReloadListener>();
		this.messagesChangedListenerList = new ArrayList<MessagesChangedListener>();
		this.cleanReadingPaneListenerList = new ArrayList<CleanReadingPaneListener>();
		this.addressFolderSelectedListenerList = new ArrayList<AddressFolderSelectedListener>();
		this.addressLoadedListenerList = new ArrayList<AddressLoadedListener>();
		this.reloadAdressListListenerList = new ArrayList<ReloadAddressListListener>();
		this.preferencesChangedListenerList = new ArrayList<PreferencesChangedListener>();
	}

	public static EventBroker get() {

		if ( instance == null ) {
			instance = new EventBroker();
		}

		return instance;
	}

	public static void reset() {

		instance = null;
	}

	public void addLgoutListener( LogoutListener listener ) {

		this.logoutListenerList.add( listener );
	}

	public void removeLogoutListener( LogoutListener listener ) {

		this.logoutListenerList.remove( listener );
	}

	public void fireLogut() {

		for (int i = 0; i < this.logoutListenerList.size(); i++) {
			LogoutListener listener = this.logoutListenerList.get( i );
			listener.onLogout();
		}
	}

	public void addFoldersReloadListener( FoldersReloadListener listener ) {

		this.foldersReloadListenerList.add( listener );
	}

	public void removeFoldersReloadListener( FoldersReloadListener listener ) {

		this.foldersReloadListenerList.remove( listener );
	}

	public void fireFoldersReload() {

		for (int i = 0; i < this.foldersReloadListenerList.size(); i++) {
			FoldersReloadListener listener = this.foldersReloadListenerList.get( i );
			listener.onFoldersReload();
		}
	}

	public void addFolderSelectedListener( FolderSelectedListener listener ) {

		this.folderSelectedListenerList.add( listener );
	}

	public void removeFolderSelectedListener( FolderSelectedListener listener ) {

		this.folderSelectedListenerList.remove( listener );
	}

	public void fireFolderSelected( GWTMailFolder mailFolder ) {

		for (int i = 0; i < this.folderSelectedListenerList.size(); i++) {
			FolderSelectedListener listener = this.folderSelectedListenerList.get( i );
			listener.onFolderSelected( mailFolder );
		}
	}

	public void addMessageLoadedListener( MessageLoadedListener listener ) {

		this.messageLoadedListenerList.add( listener );
	}

	public void removeMessageLoadedListener( MessageLoadedListener listener ) {

		this.messageLoadedListenerList.remove( listener );
	}

	public void fireMessageLoaded( GWTMessage message ) {

		for (int i = 0; i < this.messageLoadedListenerList.size(); i++) {
			MessageLoadedListener listener = this.messageLoadedListenerList.get( i );
			listener.onMessageLoaded( message );
		}
	}

	public void addMessagesReloadListener( MessagesReloadListener listener ) {

		this.messagesReloadListenerList.add( listener );
	}

	public void removeReloadMessagesListener( MessagesReloadListener listener ) {

		this.messagesReloadListenerList.remove( listener );
	}

	public void fireMessagesReload() {

		for (int i = 0; i < this.messagesReloadListenerList.size(); i++) {
			MessagesReloadListener listener = this.messagesReloadListenerList.get( i );
			listener.onMessagesReload();
		}
	}

	public void addMessagesChangedListener( MessagesChangedListener listener ) {

		this.messagesChangedListenerList.add( listener );
	}

	public void removeMessagesChangedListener( MessagesChangedListener listener ) {

		this.messagesChangedListenerList.remove( listener );
	}

	public void fireMessagesChanged() {

		for (int i = 0; i < this.messagesChangedListenerList.size(); i++) {
			MessagesChangedListener listener = this.messagesChangedListenerList.get( i );
			listener.onMessagesChanged();
		}
	}

	public void addCleanReadingPaneListener( CleanReadingPaneListener listener ) {

		this.cleanReadingPaneListenerList.add( listener );
	}

	public void removeCleanReadingPaneListener( CleanReadingPaneListener listener ) {

		this.cleanReadingPaneListenerList.remove( listener );
	}

	public void fireCleanReadingPane() {

		for (int i = 0; i < this.cleanReadingPaneListenerList.size(); i++) {
			CleanReadingPaneListener listener = this.cleanReadingPaneListenerList.get( i );
			listener.onReadingPaneCleared();
		}
	}

	public void addAddressFolderSelectedListener( AddressFolderSelectedListener listener ) {

		this.addressFolderSelectedListenerList.add( listener );
	}

	public void removeAddressFolderSelectedListener( AddressFolderSelectedListener listener ) {

		this.addressFolderSelectedListenerList.remove( listener );
	}

	public void fireAddressFolderSelected( AddressFolder addressFolder ) {

		for (int i = 0; i < this.addressFolderSelectedListenerList.size(); i++) {
			AddressFolderSelectedListener listener = this.addressFolderSelectedListenerList.get( i );
			listener.onFolderSelected( addressFolder );
		}
	}

	public void addAddressLoadedListener( AddressLoadedListener listener ) {

		this.addressLoadedListenerList.add( listener );
	}

	public void removeAddressLoadedListener( AddressLoadedListener listener ) {

		this.addressLoadedListenerList.remove( listener );
	}

	public void fireAddressLoaded( Address address ) {

		for (int i = 0; i < this.addressLoadedListenerList.size(); i++) {
			AddressLoadedListener listener = this.addressLoadedListenerList.get( i );
			listener.onAddressLoaded( address );
		}
	}

	public void addReloadAddressListListener( ReloadAddressListListener listener ) {

		this.reloadAdressListListenerList.add( listener );
	}

	public void removeReloadAddressListListener( ReloadAddressListListener listener ) {

		this.reloadAdressListListenerList.remove( listener );
	}

	public void fireReloadAddressList() {

		for (int i = 0; i < this.reloadAdressListListenerList.size(); i++) {
			ReloadAddressListListener listener = this.reloadAdressListListenerList.get( i );
			listener.onReloadAddressList();
		}
	}

	public void addPreferencesChangedListener( PreferencesChangedListener listener ) {

		this.preferencesChangedListenerList.add( listener );
	}

	public void removePreferencesChangedListener( PreferencesChangedListener listener ) {

		this.preferencesChangedListenerList.remove( listener );
	}

	public void firePreferencesChanged( Preferences preferences ) {

		for (int i = 0; i < this.preferencesChangedListenerList.size(); i++) {
			PreferencesChangedListener listener = this.preferencesChangedListenerList.get( i );
			listener.onPreferencesChanged( preferences );
		}
	}

	public void addAddressSelectedListener( AddressSelectedListener listener ) {

		this.addressSelectedListenerList.add( listener );
	}

	public void removeAddressSelectedListener( AddressSelectedListener listener ) {

		this.addressSelectedListenerList.remove( listener );
	}

	public void fireAddressSelected( Address address ) {

		for (int i = 0; i < this.addressSelectedListenerList.size(); i++) {
			AddressSelectedListener listener = this.addressSelectedListenerList.get( i );
			listener.onAddressSelected( address );
		}
	}
}
