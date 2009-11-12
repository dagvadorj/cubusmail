/* MessageComparator.java

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
package com.cubusmail.server.mail.util;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.cubusmail.common.model.MessageListFields;
import com.cubusmail.server.mail.util.MessageUtils.AddressStringType;

/**
 * Comparator for message sorting.
 * 
 * @author Juergen Schlierf
 */
public class MessageComparator implements Comparator<Message> {

	private static Logger log = Logger.getLogger( MessageComparator.class.getName() );

	private String field;
	private boolean ascending;

	public MessageComparator( String field, boolean ascending ) {

		this.field = field;
		this.ascending = ascending;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare( Message msg1, Message msg2 ) {

		int result = 0;

		if ( msg1.isExpunged() || msg2.isExpunged() ) {
			return result;
		}

		try {
			if ( MessageListFields.SUBJECT.name().equals( this.field ) ) {
				if ( msg1.getSubject() != null && msg2.getSubject() != null ) {
					result = msg1.getSubject().compareToIgnoreCase( msg2.getSubject() );
				} else {
					result = -1;
				}
			} else if ( MessageListFields.FROM.name().equals( this.field ) ) {
				String fromString1 = MessageUtils.getMailAdressString( msg1.getFrom(), AddressStringType.PERSONAL );
				String fromString2 = MessageUtils.getMailAdressString( msg2.getFrom(), AddressStringType.PERSONAL );
				if ( fromString1 != null && fromString2 != null ) {
					result = fromString1.compareToIgnoreCase( fromString2 );
				} else {
					result = -1;
				}
			} else if ( MessageListFields.DATE.name().equals( this.field ) ) {
				Date date1 = msg1.getSentDate();
				Date date2 = msg2.getSentDate();
				if ( date1 != null && date2 != null ) {
					result = date1.compareTo( date2 );
				} else {
					result = -1;
				}
			} else if ( MessageListFields.SIZE.name().equals( this.field ) ) {
				int size1 = msg1.getSize();
				int size2 = msg2.getSize();
				result = Integer.valueOf( size1 ).compareTo( Integer.valueOf( size2 ) );

			} else if ( MessageListFields.ATTACHMENT_FLAG.name().equals( this.field ) ) {
				Boolean attach1 = Boolean.valueOf( MessageUtils.hasAttachments( msg1 ) );
				Boolean attach2 = Boolean.valueOf( MessageUtils.hasAttachments( msg2 ) );
				result = attach1.compareTo( attach2 );
			}
		}
		catch ( MessagingException e ) {
			log.warn( e.getMessage(), e );
		}
		catch ( IOException e ) {
			log.warn( e.getMessage(), e );
		}

		if ( !this.ascending ) {
			result = result * (-1);
		}

		return result;
	}
}
