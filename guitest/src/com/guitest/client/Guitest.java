package com.guitest.client;

import java.util.Date;

import com.cubusmail.client.canvases.mail.MessageReadingPaneHeader;
import com.cubusmail.client.widgets.ImageHyperlink;
import com.cubusmail.common.model.GWTAddress;
import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TileLayoutPolicy;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.tile.TileLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Guitest implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.getBodyElement().removeChild(
				RootPanel.get("loadingWrapper").getElement());

		// MessageSearchForm form = new MessageSearchForm();
		// form.draw();

		// testMessageReadingPaneHeader();
		// testTileLayoutWithButtons();
		// testTileLayoutWidthAttachments();
		testFlowPanel2();
	}

	private void testFlowPanel() {
		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");
		layout.setShowEdges(true);
		layout.setOverflow(Overflow.HIDDEN);

		final FlowPanel flow = new FlowPanel();
		flow.setHeight("100");
		DOM.setStyleAttribute(flow.getElement(), "border", "1px solid #00f");
		DOM.setStyleAttribute(flow.getElement(), "whiteSpace", "wrap");
		final WidgetCanvas widgetConvas = new WidgetCanvas(flow);
		widgetConvas.setWidth100();
		widgetConvas.setHeight100();
		widgetConvas.setOverflow(Overflow.AUTO);

		Button button = new Button("kajfkasldjfkasl");
		button.setStyleName("gwt-Hyperlink");
		flow.add(new Hyperlink("akdfasklfjd", "#"));
		button = new Button("kajfkas");
		button.setStyleName("gwt-Hyperlink");
		flow.add(new Hyperlink("aaaaaaaaaaaaaaaaa", "#"));
		button = new Button("bbbbbbbbbbbbbbbb");
		button.setStyleName("gwt-Hyperlink");
		flow.add(new Hyperlink("oooooooo", "#"));

		widgetConvas.addResizedHandler(new ResizedHandler() {

			public void onResized(ResizedEvent event) {

			}
		});

		layout.setMembers(widgetConvas);

		layout.draw();
	}

	private void testFlowPanel2() {
		final Menu contextMenu = new Menu();
		MenuItem item = new MenuItem("Test 1");
		contextMenu.setItems(item);

		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");
		layout.setShowEdges(true);
		layout.setOverflow(Overflow.HIDDEN);

		FlowPanel flowPanel = new FlowPanel();
		flowPanel.ensureDebugId("cwFlowPanel");

		// Add some content to the panel
		for (int i = 0; i < 30; i++) {
			// CheckBox checkbox = new CheckBox("Item" + " " + i);
			ImageHyperlink checkbox = new ImageHyperlink(
					new Image(GWT.getHostPageBaseURL()
							+ ImageProvider.IMAGE_PREFIX
							+ ImageProvider.MSG_ATTACHMENT), "Item" + " " + i,
					"#");
			checkbox.addLeftButtonHandler(new MouseDownHandler() {

				public void onMouseDown(MouseDownEvent event) {
					GWT.log(event.getX() + "", null);
					contextMenu.showContextMenu();
				}
			});
			checkbox.addRightButtonHandler(new RightMouseDownHandler() {

				public void onRightMouseDown(RightMouseDownEvent event) {
					GWT.log(event.getX() + "", null);
				}
			});
			flowPanel.add(checkbox);
		}

		final WidgetCanvas widgetConvas = new WidgetCanvas(flowPanel);
		layout.setMembers(widgetConvas);
		layout.draw();
	}

	private void testTileLayoutWithButtons() {
		TileLayout tile = new TileLayout();
		tile.setSize("100%", "100%");
		tile.setLayoutPolicy(TileLayoutPolicy.FLOW);
		tile.setOverflow(Overflow.VISIBLE);
		tile.setAutoWrapLines(true);
		tile.setShowEdges(true);
		tile.setTileHeight(25);
		// tile.setTileWidth(130);
		tile.setCanDragResize(true);

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

	private void testTileLayoutWidthAttachments() {
		TileLayout tile = new TileLayout();
		tile.setSize("100%", "100%");
		tile.setLayoutPolicy(TileLayoutPolicy.FLOW);
		// tile.setOverflow(Overflow.VISIBLE);
		tile.setAutoWrapLines(true);
		tile.setShowEdges(true);
		tile.setTileHeight(20);
		tile.setTileWidth(170);
		tile.setTileSize(100);

		tile.addTile(new AttachmentTest("aaaaaaaaaaaaa.txt"));
		tile.addTile(new AttachmentTest("bbb.txt"));
		tile.addTile(new AttachmentTest("adfasdfasdfas_afasdfasdf.txt"));
		tile.addTile(new AttachmentTest("fffffffff.txt"));
		tile.addTile(new AttachmentTest("d.txt"));

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

	private class AttachmentTest extends HStack {
		public AttachmentTest(String name) {
			super();

			Label label = new Label(name);
			label.setIcon("attach.png");
			// label.setOverflow(Overflow.VISIBLE);
			// label.setAutoFit(true);
			setMembers(label);
			// setAutoWidth();

			// setOverflow(Overflow.VISIBLE);
		}
	}

}
