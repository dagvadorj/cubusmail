/* AddressEditAbstractForm.java

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

import com.cubusmail.common.model.GWTConstants;
import com.cubusmail.common.model.ImageProvider;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
abstract class AddressEditAbstractForm extends DynamicForm {

	protected SelectItem typeSelectionItem;

	protected StaticTextItem removeItem;
	protected StaticTextItem addItem;

	public AddressEditAbstractForm() {

		super();
		setWidth100();
		setNumCols( 4 );
		setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, 15, "*" );

		this.typeSelectionItem = new SelectItem( "typeSelectionItem" );
		this.typeSelectionItem.setShowTitle( false );

		this.removeItem = new StaticTextItem( "removeItem" );
		FormItemIcon removeIcon = new FormItemIcon();
		removeIcon.setWidth( 12 );
		removeIcon.setHeight( 12 );
		removeIcon.setSrc( ImageProvider.BUTTON_REMOVE );
		this.removeItem.setIcons( removeIcon );
		this.removeItem.setShowTitle( false );

		this.addItem = new StaticTextItem( "addItem" );
		FormItemIcon addIcon = new FormItemIcon();
		addIcon.setWidth( 12 );
		addIcon.setHeight( 12 );
		addIcon.setSrc( ImageProvider.BUTTON_ADD );
		this.addItem.setIcons( addIcon );
		this.addItem.setShowTitle( false );
		this.addItem.setVisible( false );
	}

	public void setSelectionTypes( String[] values ) {

		this.typeSelectionItem.setValueMap( values );
	}

	public String[] getSelectionTypes() {

		return this.typeSelectionItem.getValues();
	}

	public StaticTextItem getRemoveItem() {

		return removeItem;
	}

	public StaticTextItem getAddItem() {

		return addItem;
	}

	public void setType( AddressEditFormTypeEnum type ) {

		this.typeSelectionItem.setValue( type.getTitle() );
	}

	public AddressEditFormTypeEnum getType() {

		return AddressEditFormTypeEnum.getByTitle( this.typeSelectionItem.getValue().toString() );
	}

	public void setFormTypes( AddressEditFormTypeEnum[] types ) {

		if ( types != null ) {
			String[] typeStrings = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				typeStrings[i] = types[i].getTitle();
			}
			this.typeSelectionItem.setValueMap( typeStrings );
		}
	}

	public abstract <T extends Object> T getValue();

	public abstract void setValue( Object value );
}
