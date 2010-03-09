/* EmailAddressInputLine.java

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
package com.cubusmail.client.canvases.mail;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Email address input line for the compose canvas.
 * 
 * @author Juergen Schlierf
 */
public class EmailAddressInputLine extends HLayout {

	private TextItem addressLine;

	public EmailAddressInputLine( String name, String label ) {

		super();
		setHeight( 25 );
		
		IButton addressBookButton = new IButton( label );
		addressBookButton.setAutoFit( true );

		DynamicForm form = new DynamicForm();
		form.setNumCols( 1 );

		this.addressLine = new TextItem( name );
		this.addressLine.setShowTitle( false );
		form.setItems( this.addressLine );

		setMembersMargin( 5 );
		setMembers( addressBookButton, form );
	}
}
