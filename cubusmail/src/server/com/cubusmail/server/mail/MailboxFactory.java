/* MailboxFactory.java

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
package com.cubusmail.server.mail;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Create a mailbox (IMAP only at the moment)
 * 
 * @author Juergen Schlierf
 */
public class MailboxFactory implements ApplicationContextAware {

	private Map<String, String> mailboxMap = null;

	private ApplicationContext applicationContext;

	public IMailbox createMailbox( String type ) {

		if ( this.mailboxMap != null ) {
			return this.applicationContext.getBean( this.mailboxMap.get( type ), IMailbox.class );
		}
		else {
			return null;
		}
	}

	/**
	 * @param mailboxMap
	 *            The mailboxMap to set.
	 */
	public void setMailboxMap( Map<String, String> mailboxMap ) {

		this.mailboxMap = mailboxMap;
	}

	@Override
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}
}
