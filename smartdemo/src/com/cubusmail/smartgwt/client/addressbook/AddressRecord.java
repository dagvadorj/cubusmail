package com.cubusmail.smartgwt.client.addressbook;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AddressRecord extends ListGridRecord {

	public AddressRecord(String displayName) {
		super();
		setDisplayName(displayName);
	}

	public String getDisplayName() {
		return getAttribute("displayName");
	}

	public void setDisplayName(String displayName) {
		setAttribute("displayName", displayName);
	}
}
