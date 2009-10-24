package com.cubusmail.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Smartdemo implements EntryPoint {
	private TabSet mainTabSet;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VLayout main = new VLayout();

		main.setWidth100();
		main.setHeight100();
		main.setLayoutMargin(5);
		main.setStyleName("tabSetContainer");

		main.draw();

        RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());
	}
}
