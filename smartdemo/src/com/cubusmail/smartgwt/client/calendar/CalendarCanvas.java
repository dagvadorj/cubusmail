package com.cubusmail.smartgwt.client.calendar;

import com.cubusmail.smartgwt.client.NavigationView;
import com.cubusmail.smartgwt.client.addressbook.AddressEditForm;
import com.cubusmail.smartgwt.client.addressbook.AddressFilterToolbar;
import com.cubusmail.smartgwt.client.addressbook.AddressList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CalendarCanvas extends Canvas {
	public CalendarCanvas() {
		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		HLayout calendarLayout = new HLayout();
		calendarLayout.setWidth100();
		calendarLayout.setHeight100();

		NavigationView view = new NavigationView(
				NavigationView.VIEW_CALENDER);
		view.setWidth("25%");
		view.setShowResizeBar(true);
		view.setOverflow(Overflow.CLIP_H);

		VLayout addressListLayout = new VLayout();
		addressListLayout.addMember(new AddressFilterToolbar());

		HLayout addressHLayout = new HLayout();
		AddressList list = new AddressList();
		list.setWidth("220px");
		addressHLayout.addMember(list);
		addressHLayout.addMember(new AddressEditForm());
		addressListLayout.addMember(addressHLayout);

		calendarLayout.setMembers(view, addressListLayout);

		mainLayout.addMember(calendarLayout);
		addChild(mainLayout);
	}
}
