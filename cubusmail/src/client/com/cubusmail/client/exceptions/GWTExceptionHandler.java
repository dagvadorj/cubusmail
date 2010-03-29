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
package com.cubusmail.client.exceptions;

import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.exceptions.GWTInvalidSessionException;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

/**
 * Handle general exceptions.
 * 
 * @author Juergen Schlierf
 */
public class GWTExceptionHandler {

	public static void handleException( Throwable e ) {

		GWT.log( e.getMessage(), e );
		// logger.error( e.getMessage(), e );
		if ( e instanceof GWTInvalidSessionException ) {
			SC.warn( TextProvider.get().exception_invalid_session(), new BooleanCallback() {

				public void execute( Boolean value ) {

					EventBroker.get().fireLogut();
				}
			} );
		}
	}
}
