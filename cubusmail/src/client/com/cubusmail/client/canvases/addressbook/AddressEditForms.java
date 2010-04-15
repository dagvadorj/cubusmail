/* AddressEditForms.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
   This file is part of Cubusmail (http://code.google.com/p/cubusmail/).
	
   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.
	
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.
	
   You should have received a copy of the GNU Lesser General Public
   License along with Cubusmail. If not, see <http://www.gnu.org/licenses/>.
 */
package com.cubusmail.client.canvases.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cubusmail.common.model.GWTConstants;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.user.client.rpc.core.java.util.Arrays;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public enum AddressEditForms {
	DETAIL_HEADER(""), PRIVATE_PHONE("Private Phone"), WORK_PHONE("Work Phone"), PRIVATE_MOBILE("Private Mobile"), WORK_MOBILE(
			"Work Mobile"), PRIVATE_FAX("Private Fax"), WORK_FAX("Work Fax");

	private final static Map<AddressEditForms, DynamicForm> FORM_MAP = new HashMap<AddressEditForms, DynamicForm>();

	private final static AddressEditForms[] PHONE_GROUP = { PRIVATE_PHONE, WORK_PHONE, PRIVATE_MOBILE, WORK_MOBILE,
			PRIVATE_FAX, WORK_FAX };

	private String title;

	private AddressEditForms( String title ) {

		this.title = title;
	}

	public DynamicForm get() {

		DynamicForm result = FORM_MAP.get( this );
		if ( result == null ) {
			result = create();
			result.setID( this.name() );
			FORM_MAP.put( this, result );
		}

		return result;
	}

	private DynamicForm create() {

		switch (this) {
		case DETAIL_HEADER:
			return createHeaderEditForm();
		case PRIVATE_PHONE:
		case WORK_PHONE:
		case PRIVATE_MOBILE:
		case WORK_MOBILE:
		case PRIVATE_FAX:
		case WORK_FAX:
			return createPhoneEditForm();
		}

		throw new IllegalArgumentException( "AddressEditForms type missing: " + name() );
	}

	/**
	 * @return
	 */
	public String getTitle() {

		return title;
	}

	/**
	 * @return
	 */
	private DynamicForm createHeaderEditForm() {

		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setTitleWidth( GWTConstants.ADDRESS_TITLE_WIDTH );
		form.setBackgroundColor( "#EEEEEE" );
		form.setTitleSuffix( "" );
		form.setNumCols( 3 );
		form.setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, "*" );

		TextItem firstNameItem = new TextItem( "firstName", "" );
		firstNameItem.setTextBoxStyle( "addressDetailsHeader" );
		firstNameItem.setHeight( 30 );
		TextItem lastNameItem = new TextItem( "lastName" );
		lastNameItem.setShowTitle( false );
		lastNameItem.setTextBoxStyle( "addressDetailsHeader" );
		lastNameItem.setHeight( 30 );

		form.setItems( firstNameItem, lastNameItem );

		return form;
	}

	private DynamicForm createPhoneEditForm() {

		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setNumCols( 4 );
		form.setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, 15, "*" );

		ComboBoxItem phoneTypeItem = new ComboBoxItem( "phoneTypeItem" );
		phoneTypeItem.setShowTitle( false );
		TextItem phoneItem = new TextItem( "phoneItem" );
		phoneItem.setShowTitle( false );

		FormItemIcon removeIcon = new FormItemIcon();
		removeIcon.setWidth( 12 );
		removeIcon.setHeight( 12 );
		removeIcon.setSrc( ImageProvider.BUTTON_REMOVE );
		StaticTextItem removeItem = new StaticTextItem( "removeItem" );
		removeItem.setIcons( removeIcon );
		removeItem.setShowTitle( false );

		FormItemIcon addIcon = new FormItemIcon();
		addIcon.setWidth( 12 );
		addIcon.setHeight( 12 );
		addIcon.setSrc( ImageProvider.BUTTON_ADD );
		StaticTextItem addItem = new StaticTextItem( "removeItem" );
		addItem.setIcons( addIcon );
		addItem.setShowTitle( false );

		form.setItems( phoneTypeItem, phoneItem, removeItem, addItem );

		return form;
	}

	private static String[] getUnusedPhoneTypes() {

		List<String> types = new ArrayList<String>();
		for (AddressEditForms forms : PHONE_GROUP) {
			if ( !forms.get().isVisible() ) {
				types.add( forms.title );
			}
		}
		return types.toArray( new String[0] );
	}
}
