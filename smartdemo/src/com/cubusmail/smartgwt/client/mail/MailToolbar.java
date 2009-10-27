package com.cubusmail.smartgwt.client.mail;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

public class MailToolbar extends ToolStrip {

	public MailToolbar() {
		super();

		Button button = new Button("Compose");
		button.setIcon(ImageProvider.MSG_NEW);
		addMember(button);
		button = new Button("Refresh");
		button.setIcon(ImageProvider.MSG_REFRESH);
		addMember(button);
		button = new Button("Reply");
		button.setIcon(ImageProvider.MSG_REPLY);
		addMember(button);
		button = new Button("Forward");
		button.setIcon(ImageProvider.MSG_REPLY);
		addMember(button);
		button = new Button("Delete");
		button.setIcon(ImageProvider.MSG_DELETE);
		addMember(button);
		button = new Button("Mark");
		button.setIcon(ImageProvider.MSG_MARK);
		addMember(button);
		addMember(new ToolStripSeparator());
		button = new Button("Print");
		button.setIcon(ImageProvider.PRINTER);
		addMember(button);
		button = new Button("Source");
		button.setIcon(ImageProvider.MSG_SOURCE);
		addMember(button);
	}
}
