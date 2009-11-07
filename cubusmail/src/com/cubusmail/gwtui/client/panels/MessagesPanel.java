/* MessagesPanel.java

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

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.menu.Menu;

import com.cubusmail.gwtui.client.actions.ActionRegistry;
import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.events.FolderSelectedListener;
import com.cubusmail.gwtui.client.events.PreferencesChangedListener;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.util.UIFactory;
import com.cubusmail.gwtui.domain.Preferences;

/**
 * Container panel for messages list and preview.
 * 
 * @author Juergen Schlierf
 */
public class MessagesPanel extends Panel implements FolderSelectedListener, PreferencesChangedListener {

	private MessageListPanel messageListPanel;
	private Panel readingPanePanel;
	private BorderLayoutData readingPaneLayoutData;

	private Menu contextMenu;

	public MessagesPanel() {

		super();
		setLayout( new BorderLayout() );
		setPaddings( 10 );

		BorderLayoutData data = new BorderLayoutData( RegionPosition.CENTER );
		this.messageListPanel = new MessageListPanel();
		this.messageListPanel.getGridPanel().addGridRowListener( new ContextMenuListener() );
		this.messageListPanel.getGridPanel().getSelectionModel().addListener( new MessageRowSelectionListener() );
		this.messageListPanel.getGridPanel().addGridListener( new MessageGridListener() );
		add( this.messageListPanel, data );

		setReadingPaneState();

		Preferences preferences = GWTSessionManager.get().getPreferences();
		this.readingPanePanel.setVisible( preferences.isPreviewWindow() );

		initActions();
		createContextMenu();

		EventBroker.get().addFolderSelectedListener( this );
		EventBroker.get().addPreferencesChangedListener( this );
	}

	/**
	 * 
	 */
	private void setReadingPaneState() {

		Preferences preferences = GWTSessionManager.get().getPreferences();

		RegionPosition readingPanePos = null;
		if ( preferences.getReadingPane() == Preferences.READING_PANE_RIGHT ) {
			readingPanePos = RegionPosition.EAST;
		} else {
			readingPanePos = RegionPosition.SOUTH;
		}

		this.readingPaneLayoutData = new BorderLayoutData( readingPanePos );
		this.readingPaneLayoutData.setSplit( true );

		this.readingPanePanel = PanelRegistry.MESSAGE_READING_PANE_PREVIEW.get();
		if ( readingPanePos.equals( RegionPosition.SOUTH ) ) {
			this.readingPanePanel.setHeight( (Window.getClientHeight() - 80) / 2 );
		} else {
			this.readingPanePanel.setWidth( 400 );
		}
		add( this.readingPanePanel, this.readingPaneLayoutData );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.FolderSelectedListener#onFolderSelected
	 * (java.lang.String)
	 */
	public void onFolderSelected( GWTMailFolder mailFolder ) {

		setTitle( mailFolder.getName() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.cubusmail.gwtui.client.events.PreferencesChangedListener#
	 * onPreferencesChanged(com.cubusmail.gwtui.domain.Preferences)
	 */
	public void onPreferencesChanged( Preferences preferences ) {

		if ( preferences.isPreviewWindow() != this.readingPanePanel.isVisible() ) {
			this.readingPanePanel.setVisible( preferences.isPreviewWindow() );
			doLayout();
		}
	}

	/**
	 * 
	 */
	private void initActions() {

		this.messageListPanel.registerMessageAction( ActionRegistry.REPLY.get( BaseGridAction.class ) );
		this.messageListPanel.registerMessageAction( ActionRegistry.REPLY_ALL.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.FORWARD.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.COPY_MESSAGES.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.MOVE_MESSAGES.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.DELETE_MESSAGES.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.DELETE_WINDOW_MESSAGE.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.MARK_AS_READ.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.MARK_AS_UNREAD.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.MARK_AS_DELETED.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.MARK_AS_UNDELETED.get(BaseGridAction.class) );
		this.messageListPanel.registerMessageAction( ActionRegistry.SHOW_MESSAGE_SOURCE.get(BaseGridAction.class) );

		((BaseGridAction) ActionRegistry.COPY_MESSAGES.get()).setStore( this.messageListPanel.getMessageStore() );
		((BaseGridAction) ActionRegistry.MOVE_MESSAGES.get()).setStore( this.messageListPanel.getMessageStore() );
		((BaseGridAction) ActionRegistry.DELETE_MESSAGES.get()).setStore( this.messageListPanel.getMessageStore() );
		((BaseGridAction) ActionRegistry.DELETE_WINDOW_MESSAGE.get()).setStore( this.messageListPanel.getMessageStore() );
	}

	/**
	 * 
	 */
	private void createContextMenu() {

		this.contextMenu = new Menu();

		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.COPY_MESSAGES.get() ) );
		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MOVE_MESSAGES.get() ) );
		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.DELETE_MESSAGES.get() ) );
		this.contextMenu.addSeparator();
		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_READ.get() ) );
		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNREAD.get() ) );
		this.contextMenu.addSeparator();
		this.contextMenu.addItem( UIFactory.createMenuItem( ActionRegistry.SHOW_MESSAGE_SOURCE.get() ) );
	}

	/**
	 * ContextMenuListener that shows the context menu.
	 * 
	 * @author Juergen Schlierf
	 */
	private class ContextMenuListener extends GridRowListenerAdapter {

		@Override
		public void onRowContextMenu( GridPanel grid, int rowIndex, EventObject e ) {

			if ( messageListPanel.getGridPanel().getSelectionModel().getCount() > 0 ) {
				contextMenu.showAt( e.getXY() );
			}
			e.stopEvent();
		}
	}

	private class MessageRowSelectionListener extends RowSelectionListenerAdapter {

		@Override
		public void onSelectionChange( RowSelectionModel sm ) {

			if ( sm.getCount() > 0 ) {
				ToolbarManager.get().disableMessageToolbar( false );
			} else {
				ToolbarManager.get().disableMessageToolbar( true );
			}
		}
	}

	/**
	 * Listener for key events (e.g. DEL key).
	 * 
	 * @author Juergen Schlierf
	 */
	private class MessageGridListener extends GridListenerAdapter {

		@Override
		public void onKeyDown( EventObject e ) {

			if ( e.getKey() == EventObject.DELETE ) {
				ActionRegistry.DELETE_MESSAGES.get().execute();
			}
		}
	}
}
