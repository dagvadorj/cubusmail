package com.cubusmail.smartgwt.client.addressbook;

import com.cubusmail.smartgwt.client.mail.NavigationView;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AddressbookCanvas extends Canvas {

	public AddressbookCanvas() {
		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		HLayout mailLayout = new HLayout();
		mailLayout.setWidth100();
		mailLayout.setHeight100();

		NavigationView view = new NavigationView(NavigationView.VIEW_MAIL);
		view.setWidth("40%");
		view.setShowResizeBar(true);
		view.setOverflow(Overflow.CLIP_V);
		mailLayout.addMember(view);

		addChild(mainLayout);
	}
}
