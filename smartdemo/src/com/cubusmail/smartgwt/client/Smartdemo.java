package com.cubusmail.smartgwt.client;

import com.cubusmail.smartgwt.client.addressbook.AddressbookCanvas;
import com.cubusmail.smartgwt.client.mail.MailCanvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Smartdemo implements EntryPoint {
	private TabSet mainTabSet;

	private static final String TITLE_MAIL = "Mail";
	private static final String TITLE_ADDRESSBOOK = "Address Book";
	private static final String TITLE_CALENDAR = "Calendar";
	private static final String TITLE_PREFERNCES = "Preferences";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VLayout main = new VLayout();
		main.setBackgroundImage("[SKIN]/shared/background.gif");

		main.setWidth100();
		main.setHeight100();
		main.setLayoutMargin(5);

		createTabs();

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

	private void createTabs() {
		this.mainTabSet = new TabSet();
		mainTabSet.setTabBarThickness(23);
		mainTabSet.setWidth100();
		mainTabSet.setHeight100();

		this.mainTabSet.addTab(new Tab(TITLE_MAIL));
		Tab addressTab = new Tab(TITLE_ADDRESSBOOK);
		addressTab.setPane(new AddressbookCanvas());
		this.mainTabSet.addTab(addressTab);
		this.mainTabSet.addTab(new Tab(TITLE_CALENDAR));
		this.mainTabSet.addTab(new Tab(TITLE_PREFERNCES));
		this.mainTabSet.setSelectedTab(1);

		this.mainTabSet.addTabSelectedHandler(new TabSelectedHandler() {

			public void onTabSelected(TabSelectedEvent event) {
				if (TITLE_MAIL.equals(event.getTab().getTitle())) {
					if (event.getTab().getPane() == null) {
						final Tab tab = event.getTab();
						tab.setPane(new MailCanvas());
					}
				}
				if (TITLE_ADDRESSBOOK.equals(event.getTab().getTitle())) {
					if (event.getTab().getPane() == null) {
						final Tab tab = event.getTab();
						tab.setPane(new AddressbookCanvas());
					}
				}
			}
		});
	}
}
