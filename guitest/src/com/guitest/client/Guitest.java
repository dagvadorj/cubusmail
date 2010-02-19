package com.guitest.client;

import java.util.Date;

import com.cubusmail.client.canvases.mail.MessageReadingPaneHeader;
import com.cubusmail.common.model.GWTAddress;
import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.GWTMessage;
import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TileLayoutPolicy;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.tile.TileLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Guitest implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	// public void onModuleLoad() {
	// RootPanel.getBodyElement().removeChild(
	// RootPanel.get("loadingWrapper").getElement());
	//
	// // MessageSearchForm form = new MessageSearchForm();
	// // form.draw();
	//
	// // testMessageReadingPaneHeader();
	// testTileLayoutWithButtons();
	// }
	public void onModuleLoad() {
		TileLayout tile = new TileLayout();
		tile.setSize("100%", "100%");
		tile.setLayoutPolicy(TileLayoutPolicy.FLOW);
		tile.setOverflow(Overflow.VISIBLE);
		tile.setAutoWrapLines(true);
		tile.setShowEdges(true);
		tile.setTileHeight(25);

		Button button = new Button("askldjfa_faaskdfjksadfsfa.txt");
		button.setOverflow(Overflow.VISIBLE);
		button.setAutoWidth();
		tile.addTile(button);

		button = new Button("as.png");
		button.setAutoWidth();
		button.setOverflow(Overflow.VISIBLE);
		tile.addTile(button);

		button = new Button("afa_fasfa.txt");
		button.setOverflow(Overflow.VISIBLE);
		tile.addTile(button);

		tile.draw();
	}

	private void testTileLayoutWithButtons() {
		TileLayout tile = new TileLayout();
		tile.setSize("100%", "100%");
		tile.setLayoutPolicy(TileLayoutPolicy.FLOW);
		tile.setOverflow(Overflow.VISIBLE);
		tile.setAutoWrapLines(true);
		tile.setShowEdges(true);
		tile.setTileHeight(25);

		Button button = new Button("askldjfa_faaskdfjksadfsfa.txt");
		button.setOverflow(Overflow.VISIBLE);
		button.setAutoWidth();
		tile.addTile(button);

		button = new Button("as.png");
		button.setAutoWidth();
		button.setOverflow(Overflow.VISIBLE);
		tile.addTile(button);

		button = new Button("afa_fasfa.txt");
		button.setOverflow(Overflow.VISIBLE);
		tile.addTile(button);

		tile.draw();
	}

	private void testMessageReadingPaneHeader() {
		MessageReadingPaneHeader header = new MessageReadingPaneHeader();
		header.setWidth100();
		header.draw();
		header.setMessage(getTestMessage());
	}

	private GWTMessage getTestMessage() {
		GWTMessage message = new GWTMessage();
		message.setSubject("Test Betreff");

		GWTAddress from = new GWTAddress();
		from.setName("Hans Meier");
		from.setEmail("hans@meier.de");
		from.setInternetAddress("Hans Meier <hans@meier.de>");
		message.setFromArray(new GWTAddress[] { from });

		GWTAddress to = new GWTAddress();
		to.setName("Hans Moser");
		to.setEmail("hans@moser.de");
		to.setInternetAddress("Hans Moser <hans@moser.de>");
		message.setToArray(new GWTAddress[] { to });

		message.setDate(new Date());

		GWTAttachment[] attachments = new GWTAttachment[3];
		attachments[0] = new GWTAttachment();
		attachments[0].setFileName("askldjfa_faaskdfjksadfsfa.txt");
		attachments[0].setIndex(0);
		attachments[0].setMessageId(1234);
		attachments[0].setSize(23453);
		attachments[0].setSizeText("343 kb");
		attachments[1] = new GWTAttachment();
		attachments[1].setFileName("as.png");
		attachments[1].setIndex(0);
		attachments[1].setMessageId(1234);
		attachments[1].setSize(23453);
		attachments[1].setSizeText("343 kb");
		attachments[2] = new GWTAttachment();
		attachments[2].setFileName("afa_fasfa.txt");
		attachments[2].setIndex(0);
		attachments[2].setMessageId(1234);
		attachments[2].setSize(23453);
		attachments[2].setSizeText("343 kb");
		message.setAttachments(attachments);

		return message;
	}
}
