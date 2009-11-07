package com.cubusmail.smartgwt.client.addressbook;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AddressEditForm extends Canvas {
	private static final Integer LABEL_WIDTH = 80;
	private static final Integer BUTTON_WIDTH = 18;

	private FormItemIcon addIcon = null;
	private FormItemIcon removeIcon = null;

	private DynamicForm emailForm;
	private VLayout editLayout;

	private LinkedHashMap<String, String> phoneTypes;
	private LinkedHashMap<String, String> emailTypes;
	private LinkedHashMap<String, String> addressTypes;

	public AddressEditForm() {

		super();
		init();
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
		this.editLayout.setPadding(5);

		this.editLayout.addMember(createHeadForm());
		this.editLayout.addMember(createHeadForm());
		// this.editLayout.addMember(createEmailForm());
		createEmailForms();
		createPhoneForms();

		addChild(this.editLayout);
	}

	
	private void init() {
		this.phoneTypes = new LinkedHashMap<String, String>();
		this.phoneTypes.put("Private Phone", "Private Phone");
		this.phoneTypes.put("Private Mobile", "Private Mobile");
		this.phoneTypes.put("Private Fax", "Private Fax");
		this.phoneTypes.put("Business Phone", "Business Phone");
		this.phoneTypes.put("Business Mobile", "Business Mobile");
		this.phoneTypes.put("Business Fax", "Business Fax");

		this.emailTypes = new LinkedHashMap<String, String>();
		this.emailTypes.put("Email 1", "Email 1");
		this.emailTypes.put("Email 2", "Email 2");
		this.emailTypes.put("Email 3", "Email 3");
		this.emailTypes.put("Email 4", "Email 4");
		this.emailTypes.put("Email 5", "Email 5");

		this.addressTypes = new LinkedHashMap<String, String>();
		this.addressTypes.put("Work Address", "Work Address");
		this.addressTypes.put("Private Address", "Private Address");
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

	private void createPhoneForms() {

		for (Iterator<String> iterator = this.phoneTypes.values().iterator(); iterator
				.hasNext();) {
			String value = iterator.next();

			DynamicForm form = new DynamicForm();
			form.setNumCols(4);
			form.setColWidths(LABEL_WIDTH, "*", BUTTON_WIDTH, BUTTON_WIDTH);
			SelectItem phoneTypesSelect = new SelectItem();
			phoneTypesSelect.setShowTitle(false);
			phoneTypesSelect.setValueMap(this.phoneTypes);
			TextItem phone = new TextItem("phone" + value, "phone");
			phone.setEndRow(false);
			phone.setShowTitle(false);
			StaticTextItem remove = new StaticTextItem("remove");
			remove.setShowTitle(false);
			remove.setIcons(this.removeIcon);
			StaticTextItem add = new StaticTextItem("add");
			add.setShowTitle(false);
			add.setIcons(this.addIcon);
			form.setFields(phoneTypesSelect, phone, remove, add);
			phoneTypesSelect.setValue(value);
			this.editLayout.addMember(form);
		}

	}

	private void createEmailForms() {

		for (Iterator<String> iterator = this.emailTypes.values().iterator(); iterator
				.hasNext();) {
			String value = iterator.next();
			DynamicForm form = new DynamicForm();
			form.setNumCols(4);
			form.setColWidths(LABEL_WIDTH, "*", BUTTON_WIDTH, BUTTON_WIDTH);
			SelectItem phoneTypesSelect = new SelectItem();
			phoneTypesSelect.setShowTitle(false);
			phoneTypesSelect.setValueMap(this.phoneTypes);
			TextItem phone = new TextItem("email" + value, "email");
			phone.setEndRow(false);
			phone.setShowTitle(false);
			StaticTextItem remove = new StaticTextItem("remove");
			remove.setShowTitle(false);
			remove.setIcons(this.removeIcon);
			StaticTextItem add = new StaticTextItem("add");
			add.setShowTitle(false);
			add.setIcons(this.addIcon);
			form.setFields(phoneTypesSelect, phone, remove, add);
			phoneTypesSelect.setValue(value);
			this.editLayout.addMember(form);
		}
	}
}
