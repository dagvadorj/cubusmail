package com.cubusmail.smartgwt.client.mail;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MailPanel extends Canvas {

	public MailPanel() {
		HLayout mainLayout = new HLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		MailfolderTree tree = new MailfolderTree();
		tree.setWidth("30%");
		tree.setShowResizeBar(true);
		tree.setOverflow(Overflow.HIDDEN);

		mainLayout.addMember(tree);

		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();

		Label listingLabel = new Label();
		listingLabel.setContents("Listing");
		listingLabel.setAlign(Alignment.CENTER);
		listingLabel.setOverflow(Overflow.HIDDEN);
		listingLabel.setHeight("30%");
		listingLabel.setBorder("1px solid blue");
		listingLabel.setAutoFit(true);
		listingLabel.setShowResizeBar(true);

		Label detailsLabel = new Label();
		detailsLabel.setContents("Details");
		detailsLabel.setAlign(Alignment.CENTER);
		detailsLabel.setOverflow(Overflow.HIDDEN);
		detailsLabel.setHeight("70%");
		detailsLabel.setBorder("1px solid blue");
		detailsLabel.setAutoFit(true);

		vLayout.addMember(listingLabel);
		vLayout.addMember(detailsLabel);

		mainLayout.addMember(vLayout);

		addChild(mainLayout);
	}
}
