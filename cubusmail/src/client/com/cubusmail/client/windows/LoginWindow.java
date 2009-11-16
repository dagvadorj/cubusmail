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
package com.cubusmail.client.windows;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.actions.IGWTAction;
import com.cubusmail.client.actions.LoginAction;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.ImageProvider;
import com.cubusmail.client.util.TextProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.smartgwt.client.event.AbstractSmartEvent;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Login dialog.
 * 
 * @author Juergen Schlierf
 */
public class LoginWindow extends Window {

	private Button loginButton;
	private Button resetButton;
	private TextItem username;
	private TextItem password;
	private Img loadingImage;

	private LoginAction loginAction;

	public LoginWindow() {

		super();
		setTitle( TextProvider.get().logindialog_title() );
		setWidth( 290 );
		setHeight( 130 );
		setAlign( Alignment.CENTER );
		centerInPage();
		setIsModal( true );
		setShowCloseButton( false );
		setShowMinimizeButton( false );
		setShowMaximizeButton( false );

		DynamicForm form = new DynamicForm();
		setPadding( 10 );
		form.setWidth100();
		form.setHeight100();

		this.username = new TextItem( "username", TextProvider.get().logindialog_label_username() );
		this.username.setRequired( true );

		this.password = new TextItem( "password", TextProvider.get().logindialog_label_password() );
		this.password.setRequired( false );
		this.password.setType( "password" );

		form.setFields( this.username, this.password );

		this.resetButton = new Button( "Reset" );
		this.resetButton.setLeft( 200 );
		this.resetButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				username.clearValue();
				password.clearValue();
			}
		} );

		this.loadingImage = new Img( ImageProvider.LOADING, 20, 20 );
		this.loadingImage.setImageType( ImageStyle.STRETCH );
		this.loadingImage.setVisible( false );
		this.loginButton = new Button( TextProvider.get().logindialog_button_login() );
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin( 15 );
		buttonLayout.setWidth100();
		buttonLayout.setAutoHeight();
		buttonLayout.setPadding( 5 );
		buttonLayout.setMembers( this.resetButton, this.loginButton, this.loadingImage );

		addItem( form );
		addItem( buttonLayout );

		addDrawHandler( new DrawHandler() {

			public void onDraw( DrawEvent event ) {

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

				username.focusInItem();
			}
		} );

		this.loginAction = ActionRegistry.LOGIN.get( LoginAction.class );
		addHandlers();
	}

	/**
	 * Add all handlers.
	 */
	private void addHandlers() {

		this.loginAction.setActionCallback( new BooleanCallback() {

			public void execute( Boolean value ) {

				startProgress( value );
			}
		} );

		this.loginButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				login();
			}
		} );

		this.username.addKeyPressHandler( new KeyPressHandler() {

			public void onKeyPress( KeyPressEvent event ) {

				if ( "Enter".equals( event.getKeyName() ) ) {
					login();
				}
			}
		} );

		this.password.addKeyPressHandler( new KeyPressHandler() {

			public void onKeyPress( KeyPressEvent event ) {

				if ( "Enter".equals( event.getKeyName() ) ) {
					login();
				}
			}
		} );
	}

	/**
	 * @param event
	 * @param action
	 */
	private void login() {

		this.loginAction.setUsername( getUsername() );
		this.loginAction.setPassword( getPassword() );
		this.loginAction.execute();
		this.password.setValue( "" );
	}

	private String getUsername() {

		return this.username.getValue() != null ? this.username.getValue().toString() : null;
	}

	private String getPassword() {

		return this.password.getValue() != null ? this.password.getValue().toString() : null;
	}

	/**
	 * @param start
	 */
	private void startProgress( boolean start ) {

		if ( start ) {
			this.loadingImage.show();
			this.resetButton.disable();
			this.loginButton.disable();
		}
		else {
			this.loadingImage.hide();
			this.resetButton.enable();
			this.loginButton.enable();
		}
	}
}
