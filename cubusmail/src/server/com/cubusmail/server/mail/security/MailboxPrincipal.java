/* MailboxPrincipal.java

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

import java.io.Serializable;
import java.security.Principal;

import com.cubusmail.server.mail.IMailbox;

/**
 * Principal for the mailbox.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class MailboxPrincipal implements Principal, Serializable {

	private String username;
	private IMailbox mailbox;

	public MailboxPrincipal( String username ) {

		this.username = username;
	}

	public MailboxPrincipal( String username, IMailbox mailbox ) {

		this( username );
		this.mailbox = mailbox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.Principal#getName()
	 */
	public String getName() {

		return this.username;
	}

	/**
	 * @return Returns the mailbox.
	 */
	public IMailbox getMailbox() {

		return this.mailbox;
	}

	/**
	 * @param mailbox The mailbox to set.
	 */
	public void setMailbox( IMailbox mailbox ) {

		this.mailbox = mailbox;
	}
}
