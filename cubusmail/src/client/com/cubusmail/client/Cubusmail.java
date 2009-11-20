/* Cubusmail.java

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

package com.cubusmail.client;

import com.cubusmail.client.actions.LoginAction;
import com.cubusmail.client.canvases.CanvasRegistry;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.LogoutListener;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.windows.LoginWindow;
import com.cubusmail.common.model.GWTMailbox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cubusmail implements EntryPoint, GWT.UncaughtExceptionHandler, LogoutListener {

	private LoginWindow loginDialog;

	public void onModuleLoad() {

		RootPanel.get();
		GWT.setUncaughtExceptionHandler( this );
		EventBroker.get().addLgoutListener( this );

		checkUserAccount();

		RootPanel.getBodyElement().removeChild( RootPanel.get( "loadingWrapper" ).getElement() );
	}

	/**
	 * 
	 */
	private void checkUserAccount() {

		ServiceProvider.getCubusService().retrieveMailbox( new AsyncCallback<GWTMailbox>() {

			public void onSuccess( GWTMailbox result ) {

				if ( result == null || !result.isLoggedIn() ) {
					// openLoginWindow();
					testLogin();
				}
				else {
					GWTSessionManager.get().setMailbox( result );
					openWorkbench();
				}
			}

			public void onFailure( Throwable caught ) {

				GWT.log( caught.getMessage(), caught );
				openLoginWindow();
			}
		} );
	}

	/**
	 * 
	 */
	private void openLoginWindow() {

		this.loginDialog = new LoginWindow();
		this.loginDialog.draw();
	}

	/**
	 * For test purposes only.
	 */
	private void testLogin() {

		try {
			new TestLoginAction().execute();
		}
		catch (Exception e) {
			GWT.log( e.getMessage(), e );
		}
	}

	/**
	 * 
	 */
	private void openWorkbench() {

		CanvasRegistry.WORKBENCH_CANVAS.get().draw();
	}

	public void onHistoryChanged( String historyToken ) {

		// do nothing
	}

	public void onUncaughtException( Throwable e ) {

		GWT.log( "Unerwartet", e );

		SC.warn( "Unwerwarteter Fehler",
				"Es ist ein unwerwarteter Fehler aufgetreten. Bitte melden Sie sich neu an: <br><br><pre>"
						+ e.toString() + "</pre>", new BooleanCallback() {

					public void execute( Boolean value ) {

						EventBroker.get().fireLogut();
					}
				}, new Dialog() );
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
	 * For test purposes only.
	 *
	 * @author Juergen Schlierf
	 */
	private class TestLoginAction extends LoginAction {

		
		public TestLoginAction() {
			super();
			setUsername( "schlierf" );
			setPassword( "schlierf" );
		}
		
		@Override
		public void onSuccess( GWTMailbox result ) {

			GWTSessionManager.get().setMailbox( result );
			openWorkbench();
		}
	}
}
