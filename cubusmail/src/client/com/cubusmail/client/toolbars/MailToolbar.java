package com.cubusmail.client.toolbars;

import com.cubusmail.common.model.ImageProvider;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

public class MailToolbar extends ToolStrip {

	public MailToolbar() {
		super();
		Button button = new Button("Compose");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_NEW);
		button.setAutoFit(true);
		addMember(button);
		button = new Button("Refresh");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_REFRESH);
		button.setAutoFit(true);
		addMember(button);
		addMember(new ToolStripSeparator());
		button = new Button("Reply");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_REPLY);
		button.setAutoFit(true);
		addMember(button);
		button = new Button("Forward");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_REPLY);
		button.setAutoFit(true);
		addMember(button);
		button = new Button("Delete");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_DELETE);
		button.setAutoFit(true);
		addMember(button);
		button = new Button("Mark");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_MARK);
		button.setAutoFit(true);
		addMember(button);
		addMember(new ToolStripSeparator());
		button = new Button("Print");
		button.setBorder("0px");
		button.setIcon(ImageProvider.PRINTER);
		button.setAutoFit(true);
		addMember(button);
		button = new Button("Source");
		button.setBorder("0px");
		button.setIcon(ImageProvider.MSG_SOURCE);
		button.setAutoFit(true);
		addMember(button);
	}
}
