/* ToolbarManager.java

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

import java.util.ArrayList;
import java.util.List;

import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarItem;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.ToolbarSeparator;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.Separator;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.util.UIFactory;
import com.cubusmail.gwtui.domain.Preferences;

/**
 * Manager for all toolbars.
 * 
 * @author Jürgen Schlierf
 */
public class ToolbarManager {

	private static ToolbarManager instance;

	private Toolbar workbenchToolbar;
	private Toolbar openMessageToolbar;

	private List<Button> messageToolbarButtons;
	private List<ToolbarButton> contactToolbarButtons;
	private List<ToolbarItem> messageToolbarItems;

	private ToolbarButton markToolbarButton;

	public static ToolbarManager get() {

		if ( instance == null ) {
			instance = new ToolbarManager();
		}

		return instance;
	}

	public ToolbarManager() {

	}

	/**
	 * @return Returns the workbenchToolbar.
	 */
	public Toolbar getWorkbenchToolbar() {

		if ( this.workbenchToolbar == null ) {
			createWorkbenchToolbar();
		}
		return this.workbenchToolbar;
	}

	/**
	 * @return Returns the openMessageToolbar.
	 */
	public Toolbar getOpenMessageToolbar() {

		if ( this.openMessageToolbar == null ) {
			createOpenMessageToolbar();
		}
		return this.openMessageToolbar;
	}

	/**
	 * 
	 */
	private void createWorkbenchToolbar() {

		this.workbenchToolbar = new Toolbar();
		this.workbenchToolbar.addSpacer();

		// New Message
		ToolbarButton toolbarButton = UIFactory.createToolbarButton( ActionRegistry.NEW_MESSAGE.get() );
		this.workbenchToolbar.addButton( toolbarButton );

		// mail messages
		this.messageToolbarButtons = new ArrayList<Button>();
		this.messageToolbarItems = new ArrayList<ToolbarItem>();

		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.REFRESH_MESSAGES.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.messageToolbarButtons.add( toolbarButton );
		ToolbarSeparator separator = new ToolbarSeparator();
		this.workbenchToolbar.addItem( separator );

		Menu menu = new Menu();
		MenuItem item = UIFactory.createMenuItem( ActionRegistry.REPLY.get() );
		item.setText( TextProvider.get().toolbar_manager_to_all() );
		menu.addItem( item );
		item = UIFactory.createMenuItem( ActionRegistry.REPLY_ALL.get() );
		item.setText( TextProvider.get().toolbar_manager_to_originator() );
		menu.addItem( item );

		ToolbarMenuButton menuButton = UIFactory.createToolbarMenuButton( ActionRegistry.REPLY.get() );
		menuButton.setMenu( menu );
		this.workbenchToolbar.addButton( menuButton );
		this.messageToolbarButtons.add( menuButton );

		separator = new ToolbarSeparator();
		this.messageToolbarItems.add( separator );

		this.messageToolbarButtons.add( toolbarButton );
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.FORWARD.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.messageToolbarButtons.add( toolbarButton );
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.DELETE_MESSAGES.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.messageToolbarButtons.add( toolbarButton );

		this.markToolbarButton = new ToolbarButton( TextProvider.get().toolbar_manager_mark() );
		this.markToolbarButton.setIcon( ImageProvider.MSG_MARK );
		this.workbenchToolbar.addButton( this.markToolbarButton );
		this.messageToolbarButtons.add( this.markToolbarButton );

		menu = new Menu();
		menu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_READ.get() ) );
		menu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNREAD.get() ) );
		menu.addItem( new Separator() );
		menu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_DELETED.get() ) );
		menu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNDELETED.get() ) );
		this.markToolbarButton.setMenu( menu );

		separator = new ToolbarSeparator();
		this.workbenchToolbar.addItem( separator );
		this.messageToolbarItems.add( separator );

		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.PRINT_MESSAGE.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.messageToolbarButtons.add( toolbarButton );
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.SHOW_MESSAGE_SOURCE.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.messageToolbarButtons.add( toolbarButton );
		disableMessageToolbar( true );

		// add contact toolbar buttons
		this.contactToolbarButtons = new ArrayList<ToolbarButton>();
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.ADD_CONTACT.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.contactToolbarButtons.add( toolbarButton );
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.NEW_MESSAGE_FOR_CONTACT.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.contactToolbarButtons.add( toolbarButton );
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.EDIT_CONTACT.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.contactToolbarButtons.add( toolbarButton );
		toolbarButton = UIFactory.createToolbarButton( ActionRegistry.DELETE_CONTACT.get() );
		this.workbenchToolbar.addButton( toolbarButton );
		this.contactToolbarButtons.add( toolbarButton );
		hideContactToolbarItems();
		disableContactToolbar( true );

		// common buttons
		this.workbenchToolbar.addSeparator();
		// Reading pane

		menu = new Menu();
		item = new MenuItem();
		item.setText( TextProvider.get().actions_reading_pane_right() );
		item.setIcon( ImageProvider.MSG_READING_PANE_RIGHT );
		item.setStateId( String.valueOf( Preferences.READING_PANE_RIGHT ) );
		item.addListener( ActionRegistry.READING_PANE.get() );
		menu.addItem( item );
		item = new MenuItem();
		item.setText( TextProvider.get().actions_reading_pane_bottom() );
		item.setIcon( ImageProvider.MSG_READING_PANE_BOTTOM );
		item.setStateId( String.valueOf( Preferences.READING_PANE_BOTTOM ) );
		item.addListener( ActionRegistry.READING_PANE.get() );
		menu.addItem( item );
		item = new MenuItem();
		item.setText( TextProvider.get().actions_reading_pane_hide() );
		item.setIcon( ImageProvider.MSG_READING_PANE_HIDE );
		item.setStateId( String.valueOf( Preferences.READING_PANE_HIDE ) );
		item.addListener( ActionRegistry.READING_PANE.get() );
		menu.addItem( item );
		menuButton = UIFactory.createToolbarMenuButton( ActionRegistry.READING_PANE.get() );
		menuButton.setMenu( menu );
		this.workbenchToolbar.addButton( menuButton );

		this.workbenchToolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.OPEN_PREFERENCES.get() ) );

		this.workbenchToolbar.addFill();
		this.workbenchToolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.LOGOUT.get() ) );
		this.workbenchToolbar.addSpacer();
	}

	/**
	 * 
	 */
	private void createOpenMessageToolbar() {

		this.openMessageToolbar = new Toolbar();

		this.openMessageToolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.CLOSE_MESSAGE_WINDOW.get() ) );
		this.openMessageToolbar.addSeparator();

		Menu menu = new Menu();
		MenuItem item = UIFactory.createMenuItem( ActionRegistry.REPLY.get() );
		item.setText( TextProvider.get().toolbar_manager_to_originator() );
		menu.addItem( item );
		item = UIFactory.createMenuItem( ActionRegistry.REPLY_ALL.get() );
		item.setText( TextProvider.get().toolbar_manager_to_all() );
		menu.addItem( item );

		ToolbarMenuButton menuButton = UIFactory.createToolbarMenuButton( ActionRegistry.REPLY.get() );
		menuButton.setMenu( menu );
		this.openMessageToolbar.addButton( menuButton );
		this.openMessageToolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.FORWARD.get() ) );

		this.openMessageToolbar.addSeparator();
		this.openMessageToolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.DELETE_WINDOW_MESSAGE.get() ) );

		this.openMessageToolbar.addSeparator();
		this.openMessageToolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.PRINT_MESSAGE.get() ) );
	}

	/**
	 * @return
	 */
	public Toolbar createComposeMessageToolbar() {

		Toolbar toolbar = new Toolbar();
		toolbar.addSpacer();

		toolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.SEND_MESSAGE.get() ) );
		toolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.CANCEL_SEND_MESSAGE.get() ) );
		toolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.SAVE_MESSAGE_DRAFT.get() ) );
		toolbar.addButton( UIFactory.createToolbarButton( ActionRegistry.ADD_ATTACHMENT.get() ) );

		return toolbar;
	}

	/**
	 * 
	 */
	public void showMessageToolbarItems() {

		if ( this.messageToolbarButtons != null ) {
			for (Button button : this.messageToolbarButtons) {
				button.show();
			}
			for (ToolbarItem item : this.messageToolbarItems) {
				item.show();
			}
		}
	}

	/**
	 * 
	 */
	public void hideMessageToolbarItems() {

		if ( this.messageToolbarButtons != null ) {
			for (Button button : this.messageToolbarButtons) {
				button.hide();
			}
			for (ToolbarItem item : this.messageToolbarItems) {
				item.hide();
			}
		}
	}

	/**
	 * 
	 */
	public void showContactToolbarItems() {

		if ( this.contactToolbarButtons != null ) {
			for (ToolbarButton button : this.contactToolbarButtons) {
				button.show();
			}
		}
	}

	/**
	 * 
	 */
	public void hideContactToolbarItems() {

		if ( this.contactToolbarButtons != null ) {
			for (ToolbarButton button : this.contactToolbarButtons) {
				button.hide();
			}
		}
	}

	/**
	 * @param disabled
	 */
	public void disableMessageToolbar( boolean disabled ) {

		ActionRegistry.REPLY.get().setDisabled( disabled );
		ActionRegistry.REPLY_ALL.get().setDisabled( disabled );
		ActionRegistry.FORWARD.get().setDisabled( disabled );
		ActionRegistry.MOVE_MESSAGES.get().setDisabled( disabled );
		ActionRegistry.COPY_MESSAGES.get().setDisabled( disabled );
		ActionRegistry.MOVE_MESSAGES.get().setDisabled( disabled );
		ActionRegistry.DELETE_MESSAGES.get().setDisabled( disabled );
		ActionRegistry.MARK_AS_READ.get().setDisabled( disabled );
		ActionRegistry.MARK_AS_UNREAD.get().setDisabled( disabled );
		ActionRegistry.PRINT_MESSAGE.get().setDisabled( disabled );
		ActionRegistry.SHOW_MESSAGE_SOURCE.get().setDisabled( disabled );
		this.markToolbarButton.setDisabled( disabled );
	}

	/**
	 * @param disabled
	 */
	public void disableContactToolbar( boolean disabled ) {

		ActionRegistry.EDIT_CONTACT.get().setDisabled( disabled );
		ActionRegistry.DELETE_CONTACT.get().setDisabled( disabled );
		ActionRegistry.NEW_MESSAGE_FOR_CONTACT.get().setDisabled( disabled );
	}
}
