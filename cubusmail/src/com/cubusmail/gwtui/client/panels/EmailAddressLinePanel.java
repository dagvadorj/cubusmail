/* EmailAddressLinePanel.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.gwtui.client.panels;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.ColumnLayout;

import com.cubusmail.gwtui.client.model.GWTAddress;
import com.cubusmail.gwtui.client.widgets.EmailAddressLink;

/**
 * Panel for email address lines.
 * 
 * @author Jürgen Schlierf
 */
public class EmailAddressLinePanel extends Panel {

	public EmailAddressLinePanel() {

		setLayout( new ColumnLayout() );
		setBorder( true );
	}

	/**
	 * @param addresses
	 */
	public void setAddresses( GWTAddress[] addresses ) {

		clear();
		if ( addresses != null ) {
			for (int i = 0; i < addresses.length; i++) {
				if ( i < (addresses.length - 1) ) {
					add( new EmailAddressLink( addresses[i], true ) );
				}
				else {
					add( new EmailAddressLink( addresses[i], false ) );
				}
			}
		}
	}
}
