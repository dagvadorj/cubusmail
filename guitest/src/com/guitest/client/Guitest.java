package com.guitest.client;

import java.util.Date;

import com.cubusmail.client.canvases.addressbook.AddressDetailsCanvas;
import com.cubusmail.client.canvases.addressbook.AddressDetailsFormsManagerEnum;
import com.cubusmail.client.canvases.addressbook.AddressEditAddressForm;
import com.cubusmail.client.canvases.addressbook.AddressEditCanvas;
import com.cubusmail.client.canvases.mail.ComposeMessageCanvas;
import com.cubusmail.client.canvases.mail.EmailAddressLine;
import com.cubusmail.client.canvases.mail.MessageReadingPaneHeader;
import com.cubusmail.client.widgets.ImageHyperlink;
import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.GWTEmailAddress;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TileLayoutPolicy;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
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
		// testFlowPanel2();
		// testEmailAddressLine();
		// testComposeMessageCanvas();
		// testAddressDetialCanvas();
		testAddressEditCanvas();
		// testForms();
		// testAddressForm();
	}

	private void testAddressForm() {
		AddressEditAddressForm form = new AddressEditAddressForm();
		form.draw();
	}

	private void testAddressEditCanvas() {
		final AddressEditCanvas edit = new AddressEditCanvas();
		edit.setWidth100();
		edit.setHeight100();

		edit.setAddress(createTestAddress());

		edit
				.addSaveButtonHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						GWT.log(edit.getAddress().toString());
					}
				});

		edit.draw();
	}

	private void testForms() {
		final DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();

		final StaticTextItem iconButton = new StaticTextItem();
		final FormItemIcon addIcon = new FormItemIcon();
		addIcon.setWidth(12);
		addIcon.setHeight(12);
		addIcon.setSrc(ImageProvider.BUTTON_ADD);
		iconButton.setIcons(addIcon);
		iconButton.setRedrawOnChange(true);

		ButtonItem button = new ButtonItem("Test", "test");
		button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				boolean visible = !iconButton.getVisible();
				iconButton.setVisible(visible);
				// iconButton.redraw();
				form.redraw();
			}
		});

		form.setItems(iconButton, button);

		form.draw();
	}

	private void testAddressDetialCanvas() {
		AddressDetailsCanvas detail = new AddressDetailsCanvas();
		detail.setWidth100();
		detail.setHeight100();

		AddressDetailsFormsManagerEnum.setAddress(createTestAddress());

		detail.draw();
	}

	private Address createTestAddress() {
		Address address = new Address();
		address.setFirstName("firstname");
		address.setLastName("lastname");
		address.setWorkPhone("089/35345345");
		address.setPrivatePhone("089/3333333333");
		address.setPrivateMobile("0175/35345345");
		address.setWorkMobile("0175/33333333332");
		// address.setPrivateFax("089/11111111111");
		// address.setWorkFax("089/666666666666");
		address.setPager("098/234123424");
		address.setEmail("test@test.de");
		address.setEmail2("test2@test.de");
		address.setEmail3("test3@test.de");
		address.setEmail4("test4@test.de");
		address.setEmail5("test5@test.de");
		address.setPager("098/234123424");
		address.setIm("3425345234534");
		address.setUrl("http://test.de");
		address.setTitle("Title");
		address.setBirthDate(new Date());
		address.setCompany("Company");
		address.setPosition("Position");
		address.setDepartment("Department");

		address.setPrivateStreet("Teststreet 5");
		address.setPrivateZipcode("81345");
		address.setPrivateCity("Citiy");
		address.setPrivateState("State");
		address.setPrivateCountry("Country");

		address.setWorkStreet("Workstreet 5");
		address.setWorkZipcode("345345");
		address.setWorkCity("Worksitiy");
		address.setWorkState("State");
		address.setWorkCountry("Country");

		address.setNotes("Notes");

		return address;
	}

	private void testComposeMessageCanvas() {
		ComposeMessageCanvas compose = new ComposeMessageCanvas();

		compose.draw();
	}

	private void testEmailAddressLine() {
		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");
		layout.setShowEdges(true);
		layout.setOverflow(Overflow.CLIP_H);

		EmailAddressLine line = new EmailAddressLine("From:");
		line.setShowEdges(true);
		line.setVisible(true);

		layout.setMembers(line);
		layout.draw();

		line.setAddresses(createAddresses());
	}

	private void testFlowPanel2() {
		final Menu contextMenu = new Menu();
		MenuItem item = new MenuItem("Test 1");
		contextMenu.setItems(item);
		contextMenu.setCanSelectParentItems(true);
		contextMenu.setAutoDraw(true);

		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");
		layout.setShowEdges(true);
		layout.setOverflow(Overflow.VISIBLE);
		contextMenu.setParentElement(layout);
		// layout.setContextMenu(contextMenu);

		FlowPanel flowPanel = new FlowPanel();

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
					contextMenu.setLeft(event.getX());
					contextMenu.setTop(event.getY());
					contextMenu.setVisibility(Visibility.VISIBLE);
					contextMenu.draw();
				}
			});
			checkbox.addRightButtonHandler(new RightMouseDownHandler() {

				public void onRightMouseDown(RightMouseDownEvent event) {
					GWT.log(event.getX() + "", null);
					contextMenu.setLeft(event.getX());
					contextMenu.setTop(event.getY());
					contextMenu.setVisibility(Visibility.VISIBLE);
					contextMenu.draw();
				}
			});
			flowPanel.add(checkbox);
		}

		final WidgetCanvas widgetConvas = new WidgetCanvas(flowPanel);
		contextMenu.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				if (contextMenu.isVisible()) {
					contextMenu.hide();
				}
			}
		});
		layout.setMembers(widgetConvas);
		layout.draw();
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

	private GWTEmailAddress[] createAddresses() {
		int COUNT = 10;
		GWTEmailAddress[] addresses = new GWTEmailAddress[COUNT];

		for (int i = 0; i < COUNT; i++) {
			addresses[i] = new GWTEmailAddress();
			addresses[i].setName("Hans Meier" + i);
			addresses[i].setEmail("hans" + i + "@meier.de");
			addresses[i].setInternetAddress("Hans Meier" + i + " <hans" + i
					+ "@meier.de>");
		}

		return addresses;
	}

	private GWTMessage getTestMessage() {
		GWTMessage message = new GWTMessage();
		message.setSubject("Test Betreff");

		message.setFromArray(createAddresses());

		message.setToArray(createAddresses());
		message.setCcArray(createAddresses());

		message.setDate(new Date());

		GWTAttachment[] attachments = new GWTAttachment[5];
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
		attachments[3] = new GWTAttachment();
		attachments[3].setFileName("asdfasdfasd sdafsd.pdf");
		attachments[3].setIndex(0);
		attachments[3].setMessageId(1234);
		attachments[3].setSize(23453);
		attachments[3].setSizeText("343 kb");
		attachments[4] = new GWTAttachment();
		attachments[4].setFileName("cccccccccccccc.pdf");
		attachments[4].setIndex(0);
		attachments[4].setMessageId(1234);
		attachments[4].setSize(23453);
		attachments[4].setSizeText("343 kb");
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
