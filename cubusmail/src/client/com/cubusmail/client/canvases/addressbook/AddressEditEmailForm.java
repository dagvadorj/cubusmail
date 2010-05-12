/* AddressEditEmailForm.java

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

import com.cubusmail.client.util.GWTUtil;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressEditEmailForm extends AddressEditAbstractForm {

	private TextItem emailItem;

	public AddressEditEmailForm() {

		super();
		setVisible( false );

		this.emailItem = new TextItem( "emailItem" );
		this.emailItem.setHint( "Email Address" );
		this.emailItem.setShowHintInField( true );
		this.emailItem.setShowTitle( false );

		setItems( this.typeSelectionItem, this.emailItem, this.removeItem, this.addItem );
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
	public String getValue() {

		return (String) this.emailItem.getValue();
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

		if ( GWTUtil.hasText( (String) value ) ) {
			this.emailItem.setValue( (String) value );
		}
		else {
			if ( this.emailItem.getValue() != null ) {
				this.emailItem.clearValue();
			}
		}
	}

}
