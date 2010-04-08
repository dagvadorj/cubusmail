/* AddressFilterToolbar.java

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
package com.cubusmail.client.toolbars;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressFilterToolbar extends ToolStrip {

	private final static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public AddressFilterToolbar() {

		super();
		setBorder( "0px" );
		IButton button = new IButton( "All" );
		button.setSelected( true );
		button.setAutoFit( true );
		button.setActionType( SelectionType.RADIO );
		button.setRadioGroup( "addressFilter" );
		addMember( button );
		button = new IButton( "123" );
		button.setWidth( 35 );
		button.setActionType( SelectionType.RADIO );
		button.setRadioGroup( "addressFilter" );
		addMember( button );

		for (int i = 0; i < alpha.length(); i++) {
			IButton but = new IButton( String.valueOf( alpha.charAt( i ) ) );
			but.setWidth( 21 );
			but.setActionType( SelectionType.RADIO );
			but.setRadioGroup( "addressFilter" );
			addMember( but );
		}
	}
}
