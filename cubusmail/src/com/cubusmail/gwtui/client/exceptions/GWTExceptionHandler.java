/* GWTExceptionHandler.java

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
package com.cubusmail.gwtui.client.exceptions;

import com.google.gwt.core.client.GWT;
import com.gwtext.client.widgets.MessageBox;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Handle general exceptions.
 * 
 * @author Juergen Schlierf
 */
public class GWTExceptionHandler {

	public static void handleException( Throwable e ) {

		GWT.log( e.getMessage(), e );
		// Log.error( e.getMessage(), e );
		if ( e instanceof GWTInvalidSessionException ) {
			MessageBox.alert( null, TextProvider.get().exception_invalid_session(), new MessageBox.AlertCallback() {

				public void execute() {

					EventBroker.get().fireLogut();
				}
			} );
		}
	}
}
