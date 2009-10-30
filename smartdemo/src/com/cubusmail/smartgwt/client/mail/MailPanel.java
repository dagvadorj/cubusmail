package com.cubusmail.smartgwt.client.mail;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MailPanel extends Canvas {

	public MailPanel() {

		addDrawHandler(new DrawHandler() {

			public void onDraw(DrawEvent event) {
				GWT.log("Drawevent " + event.getID(), null);
			}
		});

		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		HLayout mailLayout = new HLayout();
		mailLayout.setWidth100();
		mailLayout.setHeight100();

		NavigationView view = new NavigationView();
		view.setWidth("40%");
		view.setShowResizeBar(true);
		view.setOverflow(Overflow.CLIP_V);
		mailLayout.addMember(view);

		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();

		MessageListCanvas listingLabel = new MessageListCanvas();
		listingLabel.setHeight("50%");
		listingLabel.setShowResizeBar(true);

		MessageDetails messageDetails = new MessageDetails();
		messageDetails.setAlign(Alignment.CENTER);
		messageDetails.setOverflow(Overflow.HIDDEN);
		messageDetails.setHeight("50%");

		vLayout.addMember(listingLabel);
		vLayout.addMember(messageDetails);

		mailLayout.addMember(vLayout);

		mainLayout.addMember(new MailToolbar());
		mainLayout.addMember(mailLayout);

		addChild(mainLayout);

	}
}
