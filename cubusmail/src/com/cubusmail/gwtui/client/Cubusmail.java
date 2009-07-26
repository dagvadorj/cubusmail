/* Cubusmail.java

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

package com.cubusmail.gwtui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.MessageBox.AlertCallback;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.events.LogoutListener;
import com.cubusmail.gwtui.client.exceptions.GWTAuthenticationException;
import com.cubusmail.gwtui.client.exceptions.GWTConnectionException;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTMailbox;
import com.cubusmail.gwtui.client.panels.WorkbenchPanel;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.LoginWindow;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cubusmail implements EntryPoint, ValueChangeHandler<String>, GWT.UncaughtExceptionHandler, LogoutListener {

	private WorkbenchPanel worbenchPanel;

	private LoginAction loginAction;

	private LoginWindow loginDialog;

	public void onModuleLoad() {

		RootPanel.get();
		GWT.setUncaughtExceptionHandler( this );
		EventBroker.get().addLgoutListener( this );

		checkUserAccount();
	}

	/**
	 * 
	 */
	private void checkUserAccount() {

		ServiceProvider.getCubusService().retrieveMailbox( new AsyncCallback<GWTMailbox>() {

			public void onSuccess( GWTMailbox result ) {

				if ( result == null || !result.isLoggedIn() ) {
					openLogin();
				}
				else {
					GWTSessionManager.get().setMailbox( result );
					openWorkbench();
				}
			}

			public void onFailure( Throwable caught ) {

				GWT.log( caught.getMessage(), caught );
				openLogin();
			}
		} );
	}

	/**
	 * 
	 */
	private void openLogin() {

		this.loginAction = new LoginAction();
		this.loginDialog = new LoginWindow();
		this.loginDialog.addListener( this.loginAction );
		this.loginDialog.show();
	}

	/**
	 * 
	 */
	private void openWorkbench() {

		this.worbenchPanel = new WorkbenchPanel();
		new Viewport( this.worbenchPanel );
	}

	public void onHistoryChanged( String historyToken ) {

		// do nothing
	}

	public void onUncaughtException( Throwable e ) {

		GWT.log( "Unerwartet", e );

		MessageBox.alert( "Unwerwarteter Fehler",
				"Es ist ein unwerwarteter Fehler aufgetreten. Bitte melden Sie sich neu an: <br><br><pre>"
						+ e.toString() + "</pre>", new AlertCallback() {

					public void execute() {

						EventBroker.get().fireLogut();
					}
				} );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.events.LogoutListener#onLogout()
	 */
	public void onLogout() {

		Window.Location.reload();
	}

	/**
	 * Action for the Login.
	 * 
	 * @author Jürgen Schlierf
	 */
	private class LoginAction extends GWTAction implements AsyncCallback<GWTMailbox> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.
		 * Throwable)
		 */
		public void onFailure( Throwable e ) {

			LoginWindow.stopProgress();
			MessageBox.setIconCls( MessageBox.ERROR );

			if ( e instanceof GWTAuthenticationException ) {
				MessageBox.alert( TextProvider.get().logindialog_title(), TextProvider.get()
						.exception_login_authentication() );
			}
			else if ( e instanceof GWTConnectionException ) {
				MessageBox.alert( TextProvider.get().logindialog_title(), TextProvider.get()
						.exception_login_connection() );
			}
			else {
				MessageBox.alert( TextProvider.get().logindialog_title(), TextProvider.get()
						.exception_login_connection() );
			}

			GWTExceptionHandler.handleException( e );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.
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

			if ( !GWTUtil.hasText( loginDialog.getUsername() ) ) {
				MessageBox.alert( TextProvider.get().common_error(), TextProvider.get().logindialog_empty_username() );
				return;
			}

			LoginWindow.startProgress();

			ServiceProvider.getCubusService().login( loginDialog.getUsername(), loginDialog.getPassword(), this );
			loginDialog.deletePassword();
		}
	}

	public void onValueChange( ValueChangeEvent<String> event ) {

		// TODO Auto-generated method stub

	}
}
