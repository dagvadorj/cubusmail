/* AddressEditMoreInfoForm.java

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

import java.util.Date;

import com.cubusmail.client.util.GWTUtil;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditMoreInfoForm extends AddressEditAbstractForm {

	private TextItem infoItem;
	private DateItem birthdateItem;

	public AddressEditMoreInfoForm() {

		super();
		setVisible( false );

		this.infoItem = new TextItem( "infoItem" );
		this.infoItem.setShowHintInField( true );
		this.infoItem.setShowTitle( false );

		this.birthdateItem = new DateItem( "birthdateItem" );
		this.birthdateItem.setShowTitle( false );
		this.birthdateItem.setUseMask( false );
		this.birthdateItem.setUseTextField( false );
		this.setVisible( false );

		this.typeSelectionItem.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged( ChangedEvent event ) {

				AddressEditFormTypeEnum typeEnum = AddressEditFormTypeEnum.getByTitle( (String) event.getValue() );
				setType( typeEnum );
			}
		} );

		setItems( this.typeSelectionItem, this.birthdateItem, this.infoItem, this.removeItem, this.addItem );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.canvases.addressbook.AddressEditAbstractForm#getValue
	 * ()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getValue() {

		if ( getType() == AddressEditFormTypeEnum.BIRTHDATE ) {
			return this.birthdateItem.getValue();
		}
		else {
			return this.infoItem.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.canvases.addressbook.AddressEditAbstractForm#setValue
	 * (java.lang.String)
	 */
	@Override
	public void setValue( Object value ) {

		if ( getType() == AddressEditFormTypeEnum.BIRTHDATE ) {
			if ( value != null ) {
				this.birthdateItem.setValue( (Date) value );
			}
			else {
				this.birthdateItem.setValue( new Date() );
			}
		}
		else {
			if ( GWTUtil.hasText( (String) value ) ) {
				this.infoItem.setValue( (String) value );
			}
			else {
				if ( this.infoItem.getValue() != null ) {
					this.infoItem.clearValue();
				}
			}
		}
	}

	@Override
	public void setType( AddressEditFormTypeEnum type ) {

		super.setType( type );

		if ( type == AddressEditFormTypeEnum.BIRTHDATE ) {
			this.infoItem.setVisible( false );
			this.birthdateItem.setVisible( true );
		}
		else {
			this.infoItem.setVisible( true );
			this.birthdateItem.setVisible( false );
		}
		redraw();
	}
}
