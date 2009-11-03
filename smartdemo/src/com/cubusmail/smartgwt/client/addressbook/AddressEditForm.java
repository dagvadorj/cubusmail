package com.cubusmail.smartgwt.client.addressbook;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AddressEditForm extends Canvas {
	private FormItemIcon addIcon = null;
	private FormItemIcon removeIcon = null;

	private DynamicForm emailForm;
	private VLayout editLayout;

	public AddressEditForm() {

		super();

		this.addIcon = new FormItemIcon();
		this.addIcon.setSrc(ImageProvider.BUTTON_ADD);
		this.addIcon.setWidth(12);
		this.addIcon.setHeight(12);
		this.removeIcon = new FormItemIcon();
		this.removeIcon.setSrc(ImageProvider.BUTTON_REMOVE);
		this.removeIcon.setWidth(12);
		this.removeIcon.setHeight(12);

		this.editLayout = new VLayout();
		this.editLayout.setWidth100();
		this.editLayout.setHeight100();

		this.editLayout.addMember(createHeadForm());
		this.editLayout.addMember(createEmailForm());

		addChild(this.editLayout);
	}

	private DynamicForm createHeadForm() {
		DynamicForm headForm = new DynamicForm();
		headForm.setWidth100();
		headForm.setAutoHeight();
		headForm.setOverflow(Overflow.VISIBLE);
		headForm.setBackgroundColor("#EEEEEE");
		headForm.setPadding(5);
		headForm.setTitleOrientation(TitleOrientation.TOP);
		headForm.setNumCols(3);

		FormItemIcon picture = new FormItemIcon();
		picture.setSrc(ImageProvider.TEST_PICTURE);
		StaticTextItem item = new StaticTextItem();
		item.setIcons(picture);
		item.setShowTitle(false);
		item.setEndRow(false);
		item.setIconWidth(60);
		item.setIconHeight(75);

		TextItem lastname = new TextItem("lastName", "Last Name");
		TextItem firstname = new TextItem("firstName", "First Name");

		headForm.setFields(item, lastname, firstname);

		return headForm;
	}

	private DynamicForm createEmailForm() {
		this.emailForm = new DynamicForm();
		this.emailForm.setBorder("1px solid");
		this.emailForm.setNumCols(4);
		this.emailForm.setWrapItemTitles(false);
		this.emailForm.setColWidths("*", "20", "20");

		TextItem email = new TextItem("email", "Email Home");
		email.setEndRow(false);
		StaticTextItem remove = new StaticTextItem("remove");
		remove.setShowTitle(false);
		remove.setIcons(this.removeIcon);
		StaticTextItem add = new StaticTextItem("add");
		add.setShowTitle(false);
		add.setIcons(this.addIcon);
		this.emailForm.setFields(email, remove, add);

		add.addIconClickHandler(new IconClickHandler() {

			public void onIconClick(IconClickEvent event) {
				final TextItem email2 = new TextItem("enail", "Email Business");
				email2.setEndRow(false);
				final StaticTextItem remove2 = new StaticTextItem("remove");
				remove2.setShowTitle(false);
				remove2.setIcons(removeIcon);
				final StaticTextItem add2 = new StaticTextItem("add");
				add2.setShowTitle(false);
				add2.setIcons(addIcon);
				add2.setRedrawOnChange(true);

				DynamicForm emailForm2 = new DynamicForm();
				emailForm2.setBorder("1px solid");
				emailForm2.setNumCols(4);
				emailForm2.setWrapItemTitles(false);
				emailForm2.setColWidths("*", "20", "20");
				emailForm2.setFields(email2, remove2, add2);
				editLayout.addMember(emailForm2);
			}
		});

		return this.emailForm;
	}
}
