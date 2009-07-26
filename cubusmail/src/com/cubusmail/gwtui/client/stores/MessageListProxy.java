/* MessageListProxy.java

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

import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTMessageList;
import com.cubusmail.gwtui.client.services.ServiceProvider;

/**
 * Proxy for mail messages.
 * 
 * @author Jürgen Schlierf
 */
public class MessageListProxy extends GWTProxy {

	private String currentFolderId;

	/**
	 * 
	 */
	public MessageListProxy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtextux.client.data.GWTProxy#load(int, int, java.lang.String,
	 * java.lang.String, com.google.gwt.core.client.JavaScriptObject,
	 * com.gwtext.client.core.UrlParam[])
	 */
	@Override
	public void load( int start, int limit, String sort, String dir, final JavaScriptObject o, UrlParam[] baseParams ) {

		if ( this.currentFolderId != null ) {
			String[][] params = new String[baseParams.length][];
			for ( int i = 0; i < baseParams.length; i++ ) {
				params[i] = new String[] { baseParams[i].getName(), baseParams[i].getValue() };
			}
			ServiceProvider.getMailboxService().retrieveMessages( this.currentFolderId, start, limit, sort, dir,
					params, new AsyncCallback<GWTMessageList>() {

						public void onFailure( Throwable caught ) {

							GWTExceptionHandler.handleException( caught );
							loadResponse( o, false, 0, new String[0][] );
						}

						public void onSuccess( GWTMessageList result ) {

							if ( result != null ) {
								loadResponse( o, true, result.getTotalRecords(), result.getMessages() );
							} else {
								loadResponse( o, false, 0, new String[0][] );
							}
						}
					} );
		} else {
			loadResponse( o, false, 0, new String[0][] );
		}
	}

	/**
	 * @return Returns the currentFolderId.
	 */
	public String getCurrentFolderId() {

		return this.currentFolderId;
	}

	/**
	 * @param currentFolderId The currentFolderId to set.
	 */
	public void setCurrentFolderId( String currentFolderId ) {

		this.currentFolderId = currentFolderId;
	}
}
