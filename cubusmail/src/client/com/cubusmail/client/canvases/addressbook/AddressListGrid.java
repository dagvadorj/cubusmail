/* AddressListGrid.java

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

import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressListGrid extends ListGrid {

	public AddressListGrid() {

		super();
		setAlternateRecordStyles( true );
		setSelectionType( SelectionStyle.MULTIPLE );
		setSelectionAppearance( SelectionAppearance.CHECKBOX );
		setBaseStyle( "myOtherGridCell" );

		ListGridField nameField = new ListGridField( "displayName", TextProvider.get().contact_list_panel_col_name() );
		setFields(nameField);
	}
}
