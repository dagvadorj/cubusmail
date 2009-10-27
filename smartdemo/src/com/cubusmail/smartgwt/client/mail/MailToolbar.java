package com.cubusmail.smartgwt.client.mail;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class MailToolbar extends ToolStrip {

	public MailToolbar() {
		super();
		
		addChild(new Button("Test"));
	}
}
