/* CubusService.java

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
package com.cubusmail.gwtui.server.services;

import java.util.Date;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import net.sf.hibernate4gwt.core.HibernateBeanManager;
import net.sf.hibernate4gwt.gwt.HibernateRemoteService;

import org.apache.log4j.Logger;

import com.google.gwt.user.client.rpc.SerializationException;

import com.cubusmail.core.BeanFactory;
import com.cubusmail.core.BeanIds;
import com.cubusmail.gwtui.client.exceptions.GWTAuthenticationException;
import com.cubusmail.gwtui.client.exceptions.GWTConnectionException;
import com.cubusmail.gwtui.client.exceptions.GWTLoginException;
import com.cubusmail.gwtui.client.exceptions.GWTLogoutException;
import com.cubusmail.gwtui.client.model.GWTMailbox;
import com.cubusmail.gwtui.client.services.ICubusService;
import com.cubusmail.gwtui.domain.ContactFolder;
import com.cubusmail.gwtui.domain.ContactFolderType;
import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.mail.IMailbox;
import com.cubusmail.mail.SessionManager;
import com.cubusmail.mail.exceptions.IErrorCodes;
import com.cubusmail.mail.security.MailboxCallbackHandler;
import com.cubusmail.mail.security.MailboxLoginModule;
import com.cubusmail.user.UserAccountDao;

/**
 * CubusService implementation.
 * 
 * @author Jürgen Schlierf
 */
public class CubusService extends HibernateRemoteService implements ICubusService {

	private Logger log = Logger.getLogger( getClass().getName() );

	private static final long serialVersionUID = -3660151485467438601L;

	public CubusService() {

		setBeanManager( (HibernateBeanManager) BeanFactory.getBean( "hibernateBeanManager" ) );
	}

	/**
	 * @return
	 */
	private UserAccountDao getUserAccountDao() {

		return (UserAccountDao) BeanFactory.getBean( BeanIds.USER_ACCOUNT_DAO );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.server.rpc.RemoteServiceServlet#processCall(java.
	 * lang.String)
	 */
	@Override
	public String processCall( String payload ) throws SerializationException {

		try {
			return super.processCall( payload );
		}
		catch (SerializationException e) {
			log.error( e.getMessage(), e );
			throw e;
		}
	}

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

			UserAccount account = getUserAccountDao().getUserAccountByUsername( username );
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
			}
			getUserAccountDao().saveUserAccount( account );
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

		UserAccount account = (UserAccount) BeanFactory.getBean( "userAccount" );
		account.setUsername( mailbox.getUserName() );
		account.setCreated( new Date() );
		account.setLastLogin( new Date() );

		// check Identities
		Identity defaultIdentity = createDefaultIdentity( mailbox );
		account.addIdentity( defaultIdentity );

		ContactFolder folder = new ContactFolder( ContactFolderType.STANDARD );
		folder.setFolderName( "Standard" );
		folder.setUserAccount( account );
		account.addContactFolder( folder );

		folder = new ContactFolder( ContactFolderType.RECIPIENTS );
		folder.setFolderName( "Recipients" );
		folder.setUserAccount( account );
		account.addContactFolder( folder );

		return account;
	}

	/**
	 * @param mailbox
	 * @return
	 */
	private Identity createDefaultIdentity( IMailbox mailbox ) {

		Identity defaultIdentity = new Identity();
		defaultIdentity.setEmail( mailbox.getEmailAddress() );
		defaultIdentity.setStandard( true );

		return defaultIdentity;
	}
}
