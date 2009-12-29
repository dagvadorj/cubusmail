package com.cubusmail.client.toolbars;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.common.model.ImageProvider;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

public class MailToolbar extends ToolStrip {

	public MailToolbar() {

		super();
		setBorder( "0px" );
		addMember( UIFactory.createToolbarButton( ActionRegistry.COMPOSE_MESSAGE, false ) );
		addMember( UIFactory.createToolbarButton( ActionRegistry.REFRESH_MESSAGES, false ) );

		addMember( new ToolStripSeparator() );

		MenuButton replyMenuButton = UIFactory.createMenuButton( ActionRegistry.REPLY );
		Menu replyMenu = new Menu();
		replyMenu.addItem( UIFactory.createMenuItem( ActionRegistry.REPLY ) );
		replyMenu.addItem( UIFactory.createMenuItem( ActionRegistry.REPLY_ALL ) );
		replyMenuButton.setMenu( replyMenu );
		addMember( replyMenuButton );
		addMember( UIFactory.createToolbarButton( ActionRegistry.DELETE_MESSAGES, false ) );

		MenuButton markMenuButton = new MenuButton( TextProvider.get().toolbar_manager_mark() );
		markMenuButton.setBorder( "0px" );
		markMenuButton.setIcon( ImageProvider.MSG_MARK );
		Menu markMenu = new Menu();
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_READ ) );
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNREAD ) );
		markMenu.addItem( new MenuItemSeparator() );
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_DELETED ) );
		markMenu.addItem( UIFactory.createMenuItem( ActionRegistry.MARK_AS_UNDELETED ) );
		markMenuButton.setMenu( markMenu );
		addMember( markMenuButton );

		addMember( new ToolStripSeparator() );

		addMember( UIFactory.createToolbarButton( ActionRegistry.PRINT_MESSAGE, false ) );
		addMember( UIFactory.createToolbarButton( ActionRegistry.SHOW_MESSAGE_SOURCE, false ) );
	}
}
