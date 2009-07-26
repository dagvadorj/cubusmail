/* TimezonesProxy.java

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
package com.cubusmail.gwtui.client.stores;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.UrlParam;
import com.gwtextux.client.data.GWTProxy;

import com.cubusmail.gwtui.client.services.ServiceProvider;

/**
 * Proxy for preferences timezones.
 * 
 * @author Jürgen Schlierf
 */
class TimezonesProxy extends GWTProxy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtextux.client.data.GWTProxy#load(int, int, java.lang.String,
	 * java.lang.String, com.google.gwt.core.client.JavaScriptObject,
	 * com.gwtext.client.core.UrlParam[])
	 */
	public void load( int start, int limit, String sort, String dir, final JavaScriptObject o, UrlParam[] baseParams ) {

		ServiceProvider.getUserAccountService().retrieveTimezones( new AsyncCallback<String[][]>() {

			public void onFailure( Throwable caught ) {

				loadResponse( o, false, 0, (JavaScriptObject) null );
			}

			public void onSuccess( String[][] result ) {

				loadResponse( o, true, result.length, result );
			}
		} );
	}
}
