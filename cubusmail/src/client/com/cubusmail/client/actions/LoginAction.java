/* LoginAction.java

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
package com.cubusmail.client.actions;

import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.exceptions.GWTAuthenticationException;
import com.cubusmail.common.exceptions.GWTConnectionException;
import com.cubusmail.common.model.GWTMailbox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;

/**
 * Action for the Login.
 * 
 * @author Juergen Schlierf
 */
public class LoginAction extends GWTAction implements AsyncCallback<GWTMailbox> {

	private String username;
	private String password;
	private BooleanCallback actionCallback;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.
	 * Throwable)
	 */
	public void onFailure( Throwable e ) {

		this.actionCallback.execute( false );

		if ( e instanceof GWTAuthenticationException ) {
			SC.warn( TextProvider.get().logindialog_title(), TextProvider.get().exception_login_authentication(), null,
					new Dialog() );
		}
		else if ( e instanceof GWTConnectionException ) {
			SC.warn( TextProvider.get().logindialog_title(), TextProvider.get().exception_login_connection(), null,
					new Dialog() );
		}
		else {
			SC.warn( TextProvider.get().logindialog_title(), TextProvider.get().exception_login_connection(), null,
					new Dialog() );
		}

		GWTExceptionHandler.handleException( e );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.
	 * Object)
	 */
	public void onSuccess( GWTMailbox result ) {

		// load Preferences
		GWTSessionManager.get().setMailbox( result );
		Window.Location.reload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		if ( !GWTUtil.hasText( this.username ) ) {
			SC.warn( TextProvider.get().common_error(), TextProvider.get().logindialog_empty_username(), null, new Dialog() );
			return;
		}

		this.actionCallback.execute( true );

		ServiceProvider.getCubusService().login( this.username, this.password, this );
		this.password = null;
	}

	public void setUsername( String username ) {

		this.username = username;
	}

	public void setPassword( String password ) {

		this.password = password;
	}

	public void setActionCallback( BooleanCallback actionCallback ) {

		this.actionCallback = actionCallback;
	}
}
