package com.cubusmail.client.toolbars;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.client.util.UIFactory;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

public class MailToolbar extends ToolStrip implements SelectionChangedHandler, DataArrivedHandler {

	private Button replyButton;
	private Button replyAllButton;
	private Button forwardButton;
	private Button deleteButton;
	private MenuButton markMenuButton;
	private Button printButton;

	/**
	 * 
	 */
	public MailToolbar() {

		super();
		setBorder( "0px" );
		addMember( UIFactory.createToolbarButton( ActionRegistry.COMPOSE_MESSAGE, false ) );
		addMember( UIFactory.createToolbarButton( ActionRegistry.REFRESH_MESSAGES, false ) );

		addMember( new ToolStripSeparator() );

		addMember( this.replyButton = UIFactory.createToolbarButton( ActionRegistry.REPLY, false ) );
		addMember( this.replyAllButton = UIFactory.createToolbarButton( ActionRegistry.REPLY_ALL, false ) );
		addMember( this.forwardButton = UIFactory.createToolbarButton( ActionRegistry.FORWARD, false ) );
		addMember( this.deleteButton = UIFactory.createToolbarButton( ActionRegistry.DELETE_MESSAGES, false ) );
		addMember( this.printButton = UIFactory.createToolbarButton( ActionRegistry.PRINT_MESSAGE, false ) );

		addMember( new ToolStripSeparator() );

		this.markMenuButton = new MenuButton( TextProvider.get().toolbar_manager_mark() );
		this.markMenuButton.setBorder( "0px" );
		this.markMenuButton.setHeight( 20 );
		Menu markMenu = new Menu();
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_READ ) );
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNREAD ) );
		markMenu.addItem( new MenuItemSeparator() );
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_DELETED ) );
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNDELETED ) );
		this.markMenuButton.setMenu( markMenu );
		addMember( this.markMenuButton );

		setDisabled( true );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.smartgwt.client.widgets.grid.events.SelectionChangedHandler#
	 * onSelectionChanged
	 * (com.smartgwt.client.widgets.grid.events.SelectionEvent)
	 */
	public void onSelectionChanged( SelectionEvent event ) {

		setDisabled( !event.getState() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smartgwt.client.widgets.grid.events.DataArrivedHandler#onDataArrived
	 * (com.smartgwt.client.widgets.grid.events.DataArrivedEvent)
	 */
	public void onDataArrived( DataArrivedEvent event ) {

		setDisabled( true );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.smartgwt.client.widgets.Canvas#setDisabled(boolean)
	 */
	@Override
	public void setDisabled( boolean disabled ) {

		this.replyButton.setDisabled( disabled );
		this.replyAllButton.setDisabled( disabled );
		this.forwardButton.setDisabled( disabled );
		this.deleteButton.setDisabled( disabled );
		this.markMenuButton.setDisabled( disabled );
		this.printButton.setDisabled( disabled );
	}
}
