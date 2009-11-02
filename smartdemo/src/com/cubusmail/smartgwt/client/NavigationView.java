/**
 * 
 */
package com.cubusmail.smartgwt.client;

import com.cubusmail.smartgwt.client.addressbook.AddressbookTree;
import com.cubusmail.smartgwt.client.mail.MailfolderTree;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author ex71824
 * 
 */
public class NavigationView extends VLayout {
	public static final int VIEW_MAIL = 0;
	public static final int VIEW_ADDRESSBOOK = 1;
	public static final int VIEW_CALENDER = 2;
	public static final int VIEW_PREFERNCES = 3;
	
	public NavigationView(final int viewid) {
		switch(viewid) {
			case VIEW_MAIL:
				addMember(new MailfolderTree());
				break;
			case VIEW_ADDRESSBOOK:
				addMember(new AddressbookTree());
				break;
		}

		DateChooser chooser = new DateChooser();
		chooser.setWidth100();
		chooser.setAutoHeight();
		chooser.setOverflow(Overflow.VISIBLE);
		addMember(chooser);
	}
}
