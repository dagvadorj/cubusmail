package com.cubusmail.client.toolbars;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.util.UIFactory;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

public class MailToolbar extends ToolStrip {

	public MailToolbar() {
		super();
		addMember(UIFactory.createToolbarButton( ActionRegistry.COMPOSE_MESSAGE.get(), false ));
		addMember(UIFactory.createToolbarButton( ActionRegistry.REFRESH_MESSAGES.get(), false ));
		addMember(new ToolStripSeparator());
		addMember(UIFactory.createToolbarButton( ActionRegistry.REPLY.get(), false ));
		addMember(UIFactory.createToolbarButton( ActionRegistry.REPLY_ALL.get(), false ));
		addMember(UIFactory.createToolbarButton( ActionRegistry.DELETE_MESSAGES.get(), false ));
		
		
//		MenuButton menuButton = new MenuButton( TextProvider.get(). )
//		addMember(new Menu());
//		button = new Button("Mark");
//		button.setBorder("0px");
//		button.setIcon(ImageProvider.MSG_MARK);
//		button.setAutoFit(true);
//		addMember(button);
//		addMember(new ToolStripSeparator());
//		button = new Button("Print");
//		button.setBorder("0px");
//		button.setIcon(ImageProvider.PRINTER);
//		button.setAutoFit(true);
//		addMember(button);
//		button = new Button("Source");
//		button.setBorder("0px");
//		button.setIcon(ImageProvider.MSG_SOURCE);
//		button.setAutoFit(true);
//		addMember(button);
	}
}
