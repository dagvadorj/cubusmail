/* SessionManager.java

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
package com.cubusmail.mail;

import java.util.Locale;
import java.util.TimeZone;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.LocaleUtils;

import com.cubusmail.gwtui.domain.Preferences;
import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.mail.security.SecurityUtils;

/**
 * Contains all Instances for a user session.
 * 
 * @author Jürgen Schlierf
 */
public class SessionManager {

	private static final String SESSION_MANAGER_NAME = SessionManager.class.getName();

	private static ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<HttpServletRequest>();

	private MessageHandler currentComposeMessage;
	private Subject subject;

	/**
	 * @param session
	 * @return
	 */
	public static SessionManager get( HttpSession session ) {

		return (SessionManager) session.getAttribute( SESSION_MANAGER_NAME );
	}

	/**
	 * @return
	 */
	public static SessionManager get() {

		return (SessionManager) getHttpSession().getAttribute( SESSION_MANAGER_NAME );
	}

	public SessionManager( Subject subject ) {

		this.subject = subject;
	}

	/**
	 * @param request
	 */
	public static void setRequest( HttpServletRequest request ) {

		threadLocalRequest.set( request );
	}

	/**
	 * create a new session and store the mailbox.
	 * 
	 * @param mailbox
	 */
	public static void createSession( Subject subject ) {

		if ( getHttpSession() != null ) {
			getHttpSession().invalidate();
		}

		createHttpSession().setAttribute( SESSION_MANAGER_NAME, new SessionManager( subject ) );
	}

	/**
	 * Destroy session.
	 */
	public static void invalidateSession() {

		getHttpSession().invalidate();
	}

	/**
	 * Get mailbox
	 * 
	 * @return
	 */
	public IMailbox getMailbox() {

		if ( this.subject != null ) {
			return SecurityUtils.getMailboxPrincipal( this.subject ).getMailbox();
		}

		return null;
	}

	public Preferences getPreferences() {

		IMailbox mailbox = getMailbox();

		if ( mailbox != null ) {
			return mailbox.getUserAccount().getPreferences();
		}
		else {
			return null;
		}
	}

	public UserAccount getUserAccount() {

		IMailbox mailbox = getMailbox();

		if ( mailbox != null ) {
			return mailbox.getUserAccount();
		}
		else {
			return null;
		}
	}

	public void setUserAccount( UserAccount account ) {

		IMailbox mailbox = getMailbox();

		if ( mailbox != null ) {
			mailbox.setUserAccount( account );
		}
	}

	/**
	 * Is session valid?
	 * 
	 * @return
	 */
	public static boolean isValid() {

		return getHttpSession() != null && getHttpSession().getAttribute( SESSION_MANAGER_NAME ) != null;
	}

	/**
	 * Is the user logged in?
	 * 
	 * @return
	 */
	public static boolean isLoggedIn() {

		if ( isValid() ) {
			Subject subject = SessionManager.get().getSubject();
			if ( subject != null && SecurityUtils.getMailboxPrincipal( subject ) != null && get().getMailbox() != null
					&& get().getMailbox().isLoggedIn() ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * get current http session
	 * 
	 * @return
	 */
	private static HttpSession getHttpSession() {

		if ( threadLocalRequest != null ) {
			return threadLocalRequest.get().getSession( false );
		}

		return null;
	}

	/**
	 * create a new http session
	 * 
	 * @return
	 */
	private static HttpSession createHttpSession() {

		if ( threadLocalRequest != null ) {
			return threadLocalRequest.get().getSession( true );
		}

		return null;
	}

	/**
	 * @return Returns the currentComposeMessage.
	 */
	public MessageHandler getCurrentComposeMessage() {

		return this.currentComposeMessage;
	}

	/**
	 * @param currentComposeMessage
	 *            The currentComposeMessage to set.
	 */
	public void setCurrentComposeMessage( MessageHandler currentComposeMessage ) {

		this.currentComposeMessage = currentComposeMessage;
	}

	/**
	 * @return Returns the subject.
	 */
	public Subject getSubject() {

		return this.subject;
	}

	/**
	 * @return
	 */
	public Locale getLocale() {

		return LocaleUtils.toLocale( getUserAccount().getPreferences().getLanguage() );
	}

	/**
	 * @return
	 */
	public TimeZone getTimeZone() {

		return TimeZone.getTimeZone( getUserAccount().getPreferences().getTimezone() );
	}
}
