package com.cubusmail.smartgwt.client.addressbook;

import com.cubusmail.smartgwt.client.ImageProvider;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

public class AddressEditForm extends DynamicForm {
	public AddressEditForm() {
		super();
		FormItemIcon picture = new FormItemIcon();
		picture.setSrc(ImageProvider.CONTACT_FOLDER);
		StaticTextItem item = new StaticTextItem();
		item.setIcons(picture);
		item.setShowTitle(false);
		
		
		setFields(item);
	}
}
