/* AddressDetailSubForm.java

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
import com.cubusmail.common.model.Address;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public abstract class AddressDetailSubForm extends DynamicForm {

	private final static int TITLE_WIDTH = 100;

	protected FormItem formItem;

	public AddressDetailSubForm( String title ) {

		super();
		setWidth100();
		setPadding( 2 );
		setTitleWidth( TITLE_WIDTH );
		setVisible( false );

		init( title );
	}

	protected void init( String title ) {

		this.formItem = new StaticTextItem( "textItem", title );
		setItems( this.formItem );
	}

	public void setAddress( Address address ) {

		String value = getValue( address );
		if ( GWTUtil.hasText( value ) ) {
			this.formItem.setValue( value );
			setVisible( true );
		}
		else {
			setVisible( false );
		}
	}

	protected abstract String getValue( Address address );
}
