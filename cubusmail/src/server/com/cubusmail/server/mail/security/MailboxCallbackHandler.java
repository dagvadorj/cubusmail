/* MailboxCallbackHandler.java

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
package com.cubusmail.server.mail.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * Security callback handler for authentication.
 * 
 * @author Juergen Schlierf
 */
public class MailboxCallbackHandler implements CallbackHandler {

	private String username;
	private String password;

	public MailboxCallbackHandler( String username, String  password) {

		this.username = username;
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.security.auth.callback.CallbackHandler#handle(javax.security.auth
	 * .callback.Callback[])
	 */
	public void handle( Callback[] callbacks ) throws IOException, UnsupportedCallbackException {

		for ( Callback callback : callbacks ) {
			if ( callback instanceof NameCallback ) {
				NameCallback nc = (NameCallback) callback;
				nc.setName( this.username );
			} else if ( callback instanceof PasswordCallback ) {
				PasswordCallback pc = (PasswordCallback) callback;
				pc.setPassword( this.password.toCharArray() );
			} else {
				throw new UnsupportedCallbackException( callback, "Unrecognized Callback" );
			}
		}
	}

}
