/**
 * 
 */
package com.cubusmail.smartgwt.client.addressbook;

import com.cubusmail.smartgwt.client.DemoData;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * @author ex71824
 * 
 */
public class AddressList extends ListGrid {
	public AddressList() {
		super();
		setAlternateRecordStyles(true);
		setCellHeight(17);
		setSelectionType(SelectionStyle.MULTIPLE);
		setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setBaseStyle("myOtherGridCell");

		ListGridField nameField = new ListGridField("displayName", "Name");

		setFields(nameField);
		setData(DemoData.getAddressListData());
	}
}
