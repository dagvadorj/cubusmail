/* LoginWindow.java

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
package com.cubusmail.gwtui.client.windows;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;

import com.cubusmail.gwtui.client.actions.IGWTAction;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Login dialog.
 *
 * @author Juergen Schlierf
 */
public class LoginWindow extends Window {

	private TextField username;

	private TextField password;

	private Button loginButton;

	private FormPanel formPanel;

	/**
	 * 
	 */
	public LoginWindow() {

		setTitle( TextProvider.get().logindialog_title() );
		setWidth( 300 );
		setHeight( 180 );
		setPlain( true );
		setClosable( false );
		setBorder( false );
		this.formPanel = new FormPanel();
		this.formPanel.setFrame( true );
		this.formPanel.setBorder( false );
		this.formPanel.setPaddings( 10 );
		this.formPanel.setLabelWidth( 100 );
		this.formPanel.add( new HTMLPanel( TextProvider.get().logindialog_message() ) );
		this.username = new TextField( TextProvider.get().logindialog_label_username(), "username" );
		// this.username.setAllowBlank( false );
		// this.username.setBlankText(
		// TextProvider.get().logindialog_empty_username() );
		this.formPanel.add( this.username );
		this.password = new TextField( TextProvider.get().logindialog_label_password(), "password" );
		// this.password.setAllowBlank( false );
		// this.password.setBlankText(
		// TextProvider.get().logindialog_empty_password() );
		this.password.setPassword( true );
		this.formPanel.add( this.password );
		this.loginButton = new Button( TextProvider.get().logindialog_button_login() );
		this.formPanel.addButton( this.loginButton );
		setLayout( new FitLayout() );
		add( this.formPanel );

		addListener( new ComponentListenerAdapter() {

			@Override
			public void onRender( Component component ) {

				// is demo?
				if ( !GWT.isScript() ) {
					username.setValue( "schlierf" );
				}
				else {
					if ( GWTUtil.hasText( TextProvider.get().demo_username() ) ) {
						username.setValue( TextProvider.get().demo_username() );
					}
				}

				if ( !GWT.isScript() ) {
					password.setValue( "schlierf" );
				}
				else {
					if ( GWTUtil.hasText( TextProvider.get().demo_password() ) ) {
						password.setValue( TextProvider.get().demo_password() );
					}
				}

				username.focus( true, 500 );
			}
		} );
	}

	public void addListener( IGWTAction action ) {

		this.loginButton.addListener( action );
		this.username.addKeyListener( KeyboardListener.KEY_ENTER, action );
		this.password.addKeyListener( KeyboardListener.KEY_ENTER, action );
	}

	public String getUsername() {

		return this.username.getValueAsString();
	}

	public String getPassword() {

		return this.password.getValueAsString();
	}

	public void deletePassword() {

		this.password.setValue( "" );
	}

	public static void startProgress() {

		MessageBox.show( new MessageBoxConfig() {

			{
				setMsg( TextProvider.get().logindialog_progress_msg() );
				setProgressText( TextProvider.get().logindialog_progress_text() );
				setClosable( true );
				setWidth( 300 );
				setWait( true );
				setWaitConfig( new WaitConfig() {

					{
						setInterval( 200 );
					}
				} );
			}
		} );
	}

	public static void stopProgress() {

		MessageBox.hide();
	}

	public static void updateProgressText( String text ) {

		MessageBox.updateText( text );
	}
}
