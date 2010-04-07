/* MailboxLoginModule.java

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
import java.util.Map;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.MailboxFactory;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.exceptions.IErrorCodes;

/**
 * LoginModule for the login procedure.
 * 
 * @author Juergen Schlierf
 */
public class MailboxLoginModule implements LoginModule {

	private final Log log = LogFactory.getLog( getClass() );

	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;

	// the authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	private MailboxPrincipal mailboxPrincipal;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject
	 * , javax.security.auth.callback.CallbackHandler, java.util.Map,
	 * java.util.Map)
	 */
	public void initialize( Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options ) {

		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#login()
	 */
	public boolean login() throws LoginException {

		if ( this.callbackHandler == null ) {
			log.fatal( "callbackHandler is null" );
			throw new LoginException( IErrorCodes.EXCEPTION_AUTHENTICATION_FAILED );
		}

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback( "Username" );
		callbacks[1] = new PasswordCallback( "Password", false );

		try {
			this.callbackHandler.handle( callbacks );
			String username = ((NameCallback) callbacks[0]).getName();

			char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
			if ( tmpPassword == null ) {
				// treat a NULL password as an empty password
				tmpPassword = new char[0];
			}
			char[] password = new char[tmpPassword.length];
			System.arraycopy( tmpPassword, 0, password, 0, tmpPassword.length );
			((PasswordCallback) callbacks[1]).clearPassword();

			// start authentication
			// TODO: very dirty, must be replaced by Spring Security stuff
			ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext( SessionManager
					.getRequest().getSession().getServletContext() );
			MailboxFactory factory = context.getBean( MailboxFactory.class );
			IMailbox mailbox = factory.createMailbox( IMailbox.TYPE_IMAP );
			mailbox.init( username, new String( password ) );

			log.debug( "Start login..." );
			mailbox.login();
			log.debug( "Login successful" );

			this.mailboxPrincipal = new MailboxPrincipal( username, mailbox );
			this.succeeded = true;
		}
		catch (IOException ioe) {
			log.error( ioe.getMessage(), ioe );
			throw new LoginException( ioe.toString() );
		}
		catch (UnsupportedCallbackException uce) {
			log.error( uce.getMessage(), uce );
			throw new LoginException( IErrorCodes.EXCEPTION_AUTHENTICATION_FAILED );
		}
		catch (MessagingException e) {
			log.error( e.getMessage(), e );
			mapMessagingException( e );
		}

		return this.succeeded;
	}

	/**
	 * Map a MessagingException to a LoginException.
	 * 
	 * @param e
	 * @throws LoginException
	 */
	private void mapMessagingException( MessagingException e ) throws LoginException {

		if ( e instanceof AuthenticationFailedException ) {
			throw new LoginException( IErrorCodes.EXCEPTION_AUTHENTICATION_FAILED );
		}
		else if ( e.getCause() != null && e.getCause() instanceof java.net.ConnectException ) {
			throw new LoginException( IErrorCodes.EXCEPTION_CONNECT );
		}
		else {
			throw new LoginException( IErrorCodes.EXCEPTION_GENERAL );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#commit()
	 */
	public boolean commit() throws LoginException {

		if ( this.succeeded ) {
			if ( !subject.getPrincipals().contains( this.mailboxPrincipal ) ) {
				subject.getPrincipals().add( this.mailboxPrincipal );
			}
			this.commitSucceeded = true;
			return this.commitSucceeded;
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#abort()
	 */
	public boolean abort() throws LoginException {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.security.auth.spi.LoginModule#logout()
	 */
	public boolean logout() throws LoginException {

		log.debug( "Start logout..." );
		try {
			SecurityUtils.getMailboxPrincipal( this.subject ).getMailbox().logout();
		}
		catch (MessagingException e) {
			// nothing to do
			log.warn( e.getMessage() );
		}
		log.debug( "Logout successful" );

		this.subject.getPrincipals().remove( SecurityUtils.getMailboxPrincipal( this.subject ) );

		return true;
	}
}
