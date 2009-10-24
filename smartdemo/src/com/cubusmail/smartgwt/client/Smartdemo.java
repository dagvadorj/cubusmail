package com.cubusmail.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
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

		this.mainTabSet = new TabSet();
		mainTabSet.setTabBarThickness(23);
		mainTabSet.setWidth100();
		mainTabSet.setHeight100();

        this.mainTabSet.addTab(new Tab("Mail"));
        this.mainTabSet.addTab(new Tab("Address Book"));
        this.mainTabSet.addTab(new Tab("Calender"));
        this.mainTabSet.addTab(new Tab("Preferences"));
        
		HLayout hLayout = new HLayout();
		hLayout.setWidth100();
		hLayout.setHeight100();

		Canvas canvas = new Canvas();
		canvas.setBackgroundImage("[SKIN]/shared/background.gif");
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.addChild(mainTabSet);

		hLayout.addMember(canvas);
		main.addMember(hLayout);

		main.draw();

		RootPanel.getBodyElement().removeChild(
				RootPanel.get("loadingWrapper").getElement());
	}
}
