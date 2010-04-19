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

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.GWTConstants;
import com.cubusmail.common.model.ImageProvider;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
abstract class AddressEditAbstractForm extends DynamicForm implements IAddressEditForm {

	protected final static FormItemIcon ADD_ICON = new FormItemIcon();
	protected final static FormItemIcon REMOVE_ICON = new FormItemIcon();
	static {
		ADD_ICON.setWidth( 12 );
		ADD_ICON.setHeight( 12 );
		ADD_ICON.setSrc( ImageProvider.BUTTON_ADD );
		REMOVE_ICON.setWidth( 12 );
		REMOVE_ICON.setHeight( 12 );
		REMOVE_ICON.setSrc( ImageProvider.BUTTON_REMOVE );
	}

	protected AddressEditFormsManagerEnum managerEnum;
	protected SelectItem typeSelectionItem;

	protected StaticTextItem removeItem;
	protected StaticTextItem addItem;

	public AddressEditAbstractForm( AddressEditFormsManagerEnum managerEnum ) {

		setWidth100();
		setNumCols( 4 );
		setColWidths( GWTConstants.ADDRESS_TITLE_WIDTH, 100, 15, "*" );

		this.managerEnum = managerEnum;

		this.typeSelectionItem = new SelectItem( "typeSelectionItem" );
		this.typeSelectionItem.setShowTitle( false );
		// this.typeSelectionItem.setRedrawOnChange(true);

		this.removeItem = new StaticTextItem( "removeItem" );
		// this.removeItem.setRedrawOnChange(true);
		this.removeItem.setIcons( REMOVE_ICON );
		this.removeItem.setShowTitle( false );
		if ( this.managerEnum == AddressEditFormsManagerEnum.PHONE_GROUP[0] ) {
			this.removeItem.setVisible( false );
		}

		this.addItem = new StaticTextItem( "addItem" );
		// this.addItem.setRedrawOnChange(true);
		this.addItem.setIcons( ADD_ICON );
		this.addItem.setShowTitle( false );
	}

	public void setSelectionTypes( String[] values ) {

		this.typeSelectionItem.setValueMap( values );
	}

	public void setSelectionType( String value ) {

		this.typeSelectionItem.setValue( value );
	}

	public StaticTextItem getRemoveItem() {

		return removeItem;
	}

	public StaticTextItem getAddItem() {

		return addItem;
	}

	public abstract void setAddress( Address address );
}
