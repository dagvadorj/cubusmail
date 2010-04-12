/* CubusService.java

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
package com.cubusmail.server.services;

import java.util.Date;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cubusmail.common.exceptions.GWTAuthenticationException;
import com.cubusmail.common.exceptions.GWTConnectionException;
import com.cubusmail.common.exceptions.GWTLoginException;
import com.cubusmail.common.exceptions.GWTLogoutException;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.AddressFolderType;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.UserAccount;
import com.cubusmail.common.services.ICubusService;
import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.exceptions.IErrorCodes;
import com.cubusmail.server.mail.security.MailboxCallbackHandler;
import com.cubusmail.server.mail.security.MailboxLoginModule;
import com.cubusmail.server.user.IUserAccountDao;

/**
 * CubusService implementation.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class CubusService extends AbstractGwtService implements ICubusService {

	private final Log log = LogFactory.getLog( getClass() );

	private IUserAccountDao userAccountDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.ICubusService#login(java.lang.String,
	 * java.lang.String)
	 */
	public GWTMailbox login( String username, String password ) throws Exception {

		try {

			LoginContext context = new LoginContext( MailboxLoginModule.class.getSimpleName(),
					new MailboxCallbackHandler( username, password ) );

			context.login();

			// if no exception thrown, login was successful
			SessionManager.createSession( context.getSubject() );

			IMailbox mailbox = SessionManager.get().getMailbox();

			UserAccount account = this.userAccountDao.getUserAccountByUsername( username );
			// create useraccount
			if ( account == null ) {
				account = createUserAccount( mailbox );
				if ( getThreadLocalRequest().getLocale() != null ) {
					String lang = getThreadLocalRequest().getLocale().getLanguage();
					account.getPreferences().setLanguage( lang );
				}
			}
			else {
				if ( account.getIdentities() == null || account.getIdentities().size() == 0 ) {
					account.addIdentity( createDefaultIdentity( mailbox ) );
				}
				account.setLastLogin( new Date() );
				this.userAccountDao.saveUserAccount( account );
			}
			
			mailbox.setUserAccount( account );
			GWTMailbox gwtMailbox = ConvertUtil.convert( mailbox );

			return gwtMailbox;
		}
		catch (LoginException e) {
			log.error( e.getMessage(), e );
			if ( IErrorCodes.EXCEPTION_AUTHENTICATION_FAILED.equals( e.getMessage() ) ) {
				throw new GWTAuthenticationException( e.getMessage() );
			}
			else if ( IErrorCodes.EXCEPTION_CONNECT.equals( e.getMessage() ) ) {
				throw new GWTConnectionException( e.getMessage() );
			}
			else {
				throw new GWTLoginException( e.getMessage() );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.services.ICubusService#logout()
	 */
	public void logout() throws Exception {

		try {
			LoginContext context = new LoginContext( MailboxLoginModule.class.getSimpleName(), SessionManager.get()
					.getSubject() );
			context.logout();
			SessionManager.invalidateSession();
		}
		catch (LoginException e) {
			log.error( e.getMessage(), e );
			throw new GWTLogoutException( e.getMessage() );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.services.ICubusService#retrieveMailbox()
	 */
	public GWTMailbox retrieveMailbox() {

		if ( SessionManager.get() != null && SessionManager.get().getMailbox() != null ) {
			IMailbox mailbox = SessionManager.get().getMailbox();
			return ConvertUtil.convert( mailbox );
		}
		else {
			return null;
		}
	}

	/**
	 * Prepare a new user account.
	 * 
	 * @param account
	 * @param mailbox
	 */
	private UserAccount createUserAccount( IMailbox mailbox ) {

		UserAccount account = getApplicationContext().getBean( UserAccount.class );
		account.setUsername( mailbox.getUserName() );
		account.setCreated( new Date() );
		account.setLastLogin( new Date() );
		this.userAccountDao.saveUserAccount( account );

		// check Identities
		Identity defaultIdentity = createDefaultIdentity( mailbox );
		account.addIdentity( defaultIdentity );
		this.userAccountDao.saveIdentities( account );

		AddressFolder folder = getApplicationContext().getBean( AddressFolder.class );
		folder.setType( AddressFolderType.STANDARD );
		folder.setName( "Standard" );
		folder.setUserAccount( account );
		account.addAddressFolder( folder );
		this.userAccountDao.saveAddressFolder( folder );

		folder = getApplicationContext().getBean( AddressFolder.class );
		folder.setType( AddressFolderType.RECIPIENTS );
		folder.setName( "Recipients" );
		folder.setUserAccount( account );
		account.addAddressFolder( folder );
		this.userAccountDao.saveAddressFolder( folder );

		return account;
	}

	/**
	 * @param mailbox
	 * @return
	 */
	private Identity createDefaultIdentity( IMailbox mailbox ) {

		Identity defaultIdentity = getApplicationContext().getBean( "identity", Identity.class );
		defaultIdentity.setEmail( mailbox.getEmailAddress() );
		defaultIdentity.setStandard( true );

		return defaultIdentity;
	}

	/**
	 * @param userAccountDao
	 */
	public void setUserAccountDao( IUserAccountDao userAccountDao ) {

		this.userAccountDao = userAccountDao;
	}
}
