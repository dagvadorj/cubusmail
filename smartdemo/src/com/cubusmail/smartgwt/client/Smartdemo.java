package com.cubusmail.smartgwt.client;

import com.cubusmail.smartgwt.client.mail.MailPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VLayout main = new VLayout();

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

		Tab tab = new Tab("Mail");
		tab.setPane(new MailPanel());
		this.mainTabSet.addTab(tab);

		this.mainTabSet.addTab(new Tab("Address Book"));
		this.mainTabSet.addTab(new Tab("Calendar"));
		this.mainTabSet.addTab(new Tab("Preferences"));

		this.mainTabSet.addTabSelectedHandler(new TabSelectedHandler() {

			public void onTabSelected(TabSelectedEvent event) {
				if ("Calendar".equals(event.getTab().getTitle())) {
					if (event.getTab().getPane() == null) {
						final Tab tab = event.getTab();
						final Window window = new Window();
						window.setTitle("Window with footer");
						window.setWidth(200);
						window.setHeight(200);
						window.setCanDragResize(true);
						window.setShowFooter(true);
						window.addDrawHandler(new DrawHandler() {

							public void onDraw(DrawEvent event) {
								tab.setPane(new MailPanel());
							}
						});
						window.draw();
					}
				}
			}
		});
	}
}
