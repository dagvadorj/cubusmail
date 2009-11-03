/**
 * 
 */
package com.cubusmail.smartgwt.client.addressbook;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class AddressFilterToolbar extends ToolStrip {
	private final static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public AddressFilterToolbar() {
		super();
		setBorder("0px");
		IButton button = new IButton("All");
		button.setSelected(true);
		button.setAutoFit(true);
		button.setActionType(SelectionType.RADIO);
		button.setRadioGroup("addressFilter");
		addMember(button);
		button = new IButton("123");
		button.setWidth(35);
		button.setActionType(SelectionType.RADIO);
		button.setRadioGroup("addressFilter");
		addMember(button);

		for (int i = 0; i < alpha.length(); i++) {
			IButton but = new IButton(String.valueOf(alpha.charAt(i)));
			but.setWidth(21);
			but.setActionType(SelectionType.RADIO);
			but.setRadioGroup("addressFilter");
			addMember(but);
		}
	}
}
