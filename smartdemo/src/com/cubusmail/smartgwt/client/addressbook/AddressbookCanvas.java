package com.cubusmail.smartgwt.client.addressbook;

import com.cubusmail.smartgwt.client.NavigationView;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AddressbookCanvas extends Canvas {

	public AddressbookCanvas() {
		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		HLayout addressBookLayout = new HLayout();
		addressBookLayout.setWidth100();
		addressBookLayout.setHeight100();

		NavigationView view = new NavigationView(
				NavigationView.VIEW_ADDRESSBOOK);
		view.setWidth("25%");
		view.setShowResizeBar(true);
		view.setOverflow(Overflow.CLIP_H);

		VLayout addressListLayout = new VLayout();
		addressListLayout.addMember(new AddressFilterToolbar());

		HLayout addressHLayout = new HLayout();
		AddressList list = new AddressList();
		list.setWidth("30%");
		addressHLayout.addMember(list);
		addressListLayout.addMember(addressHLayout);

		addressBookLayout.setMembers(view, addressListLayout);

		mainLayout.addMember(addressBookLayout);
		addChild(mainLayout);
	}
}
