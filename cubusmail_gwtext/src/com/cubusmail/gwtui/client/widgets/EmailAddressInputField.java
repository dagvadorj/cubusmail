/* EmailAddressInputField.java

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

import com.gwtext.client.widgets.form.TextField;

/**
 * Input field for email addresses.
 * 
 * @author Juergen Schlierf
 */
public class EmailAddressInputField extends TextField {

	public EmailAddressInputField() {

		super();
	}

	public EmailAddressInputField( String fieldLabel ) {

		super( fieldLabel );
	}

	/**
	 * @param address
	 */
	public void addEmailAddress( String address ) {

		String addresses = getValueAsString();
		if ( addresses == null || addresses.length() == 0 ) {
			addresses = address;
		} else {
			addresses += ", " + address;
		}
		setValue( addresses );
	}
}
