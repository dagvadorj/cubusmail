/* MailboxAuthenticator.java

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

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * JavaMail Authenticator for mailboxes.
 *
 * @author Juergen Schlierf
 */
public class MailboxAuthenticator extends Authenticator {

	private IMailPasswordEncryptor mailPasswordEncryptor;

	private String username;
	private byte[] encryptedPassword;

	/**
	 * @return the passwordAuthentication
	 */
	public PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication( this.username,
				this.mailPasswordEncryptor.decryptPassword( this.encryptedPassword ) );
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername( String username ) {

		this.username = username;
	}

	public void setPassword( String password ) {

		this.encryptedPassword = this.mailPasswordEncryptor.encryptPassowrd( password );
	}

	/**
	 * @param mailPasswordEncryptor The mailPasswordEncryptor to set.
	 */
	public void setMailPasswordEncryptor( IMailPasswordEncryptor mailPasswordEncryptor ) {

		this.mailPasswordEncryptor = mailPasswordEncryptor;
	}
}
