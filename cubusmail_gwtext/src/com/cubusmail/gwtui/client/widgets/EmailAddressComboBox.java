/* EmailAddressComboBox.java

   Copyright (c) 2009 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.gwtui.client.widgets;

import com.gwtext.client.core.Template;
import com.gwtext.client.widgets.form.ComboBox;

import com.cubusmail.gwtui.client.stores.EmailAddressFieldsEnum;
import com.cubusmail.gwtui.client.stores.StoreProvider;

/**
 * Email combobox for identities.
 * 
 * @author Juergen Schlierf
 */
public class EmailAddressComboBox extends ComboBox {

	private static final Template COMBO_TEMPLATE = new Template( "<div class=\"x-combo-list-item\">{"
			+ EmailAddressFieldsEnum.HTML_ESCAPED_EMAIL_ADDRESS.toString() + "}</div>" );

	public EmailAddressComboBox( String label ) {

		super();
		setLabel( label );
		setForceSelection( false );
		setEditable( true );
		setMaxHeight( 300 );
		setTriggerAction( ComboBox.ALL );
		setMode( ComboBox.REMOTE );
		setDisplayField( EmailAddressFieldsEnum.EMAIL_ADDRESS.toString() );
		setValueField( EmailAddressFieldsEnum.EMAIL_ADDRESS.toString() );
		setHideTrigger( true );
		setStore( StoreProvider.get().getEmailComboStore() );
		setHideLabel( true );
		setMinChars( 1 );
		setTypeAhead( false );
		setTpl( COMBO_TEMPLATE );
		setLoadingText( null );
		setQueryDelay( 150 );
	}

	@Override
	public String getValueAsString() {

		return getRawValue();
	}

	@Override
	public String getValue() {

		return getRawValue();
	}
}
