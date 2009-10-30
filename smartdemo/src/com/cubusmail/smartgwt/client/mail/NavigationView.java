/**
 * 
 */
package com.cubusmail.smartgwt.client.mail;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author ex71824
 * 
 */
public class NavigationView extends VLayout {

	public NavigationView() {
		MailfolderTree tree = new MailfolderTree();
		addMember(tree);

		DateChooser chooser = new DateChooser();
		chooser.setWidth100();
		chooser.setAutoHeight();
		chooser.setOverflow(Overflow.VISIBLE);
		addMember(chooser);
	}
}
