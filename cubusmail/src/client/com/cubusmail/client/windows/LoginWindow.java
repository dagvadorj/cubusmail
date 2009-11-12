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

import com.cubusmail.client.actions.IGWTAction;
import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Login dialog.
 * 
 * @author Juergen Schlierf
 */
public class LoginWindow extends Window {

	private TextItem username;
	private TextItem password;

	public LoginWindow() {

		super();
		setTitle( TextProvider.get().logindialog_title() );
		setWidth( 300 );
		setHeight( 180 );
		setAlign( Alignment.CENTER );
		centerInPage();
		setIsModal( true );
		setShowCloseButton( false );
		setShowMinimizeButton( false );
		setShowMaximizeButton( false );

		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();

		this.username = new TextItem( "username", TextProvider.get().logindialog_label_username() );
		this.username.setRequired( true );
		this.username.setDefaultValue( "schlierf" );

		this.password = new TextItem( "password", TextProvider.get().logindialog_label_password() );
		this.password.setRequired( false );
		this.password.setDefaultValue( "schlierf" );
		this.password.setType( "password" );

		form.setFields( this.username, this.password );

		Button loginButton = new Button( TextProvider.get().logindialog_button_login() );
		addItem( form );
		addItem( loginButton );
	}

	public void addListener( IGWTAction action ) {

		// this.loginButton.addListener( action );
		// this.username.addKeyListener( KeyboardListener.KEY_ENTER, action );
		// this.password.addKeyListener( KeyboardListener.KEY_ENTER, action );
	}
}
