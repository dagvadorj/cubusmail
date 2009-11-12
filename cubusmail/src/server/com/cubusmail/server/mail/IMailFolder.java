/* IMailFolder.java

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

import java.io.Serializable;
import java.util.List;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageChangedListener;
import javax.mail.search.SearchTerm;

import com.sun.mail.imap.Rights;

/**
 * Interface for a mail folder.
 *
 * @author Juergen Schlierf
 */
public interface IMailFolder extends Serializable {

	public final static IMailFolder[] EMPTY_FOLDER_ARRAY = new IMailFolder[0];
	public final static Message[] EMPTY_MESSAGE_ARRAY = new Message[0];

	/**
	 * Name of the folder;
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * all subfolders.
	 * 
	 * @return
	 */
	public IMailFolder[] getSubfolders();

	/**
	 * @param subfolders
	 *            The subfolders to set.
	 */
	public void setSubfolders( List<IMailFolder> subfolders );

	/**
	 * Retrieves all messages.
	 * 
	 * @return
	 */
	public Message[] retrieveMessages( String sortfield );

	/**
	 * Number of unread messages.
	 * 
	 * @return
	 */
	public int getUnreadMessageCount();

	/**
	 * For tracing.
	 * 
	 * @return
	 */
	public String toString();

	/**
	 * Get the java mail folder implementation.
	 * 
	 * @return
	 */
	public Folder getFolder();

	/**
	 * inbox folder?
	 * 
	 * @return
	 */
	public boolean isInbox();

	/**
	 * draft folder?
	 * 
	 * @return
	 */
	public boolean isDraft();

	/**
	 * sent folder?
	 * 
	 * @return
	 */
	public boolean isSent();

	/**
	 * trash folder?
	 * 
	 * @return
	 */
	public boolean isTrash();

	/**
	 * mailbox folder?
	 * 
	 * @return
	 */
	public boolean isMailbox();

	/**
	 * Is creating of sub mail folders allowed.
	 * 
	 * @return
	 */
	public boolean isCreateSubfolderSupported();

	/**
	 * Is moving of mail folders allowed.
	 * 
	 * @return
	 */
	public boolean isMoveSupported();

	/**
	 * Is renaming mail folders allowed.
	 * 
	 * @return
	 */
	public boolean isRenameSupported();

	/**
	 * Is deleting of mail folders allowed.
	 * 
	 * @return
	 */
	public boolean isDeleteSupported();

	/**
	 * Is cleaning up of mail folders allowed.
	 * 
	 * @return
	 */
	public boolean isEmptySupported();

	// Delegates for Folder
	/**
	 * Open this folder.
	 * 
	 * @param mode
	 * @throws MessagingException
	 */
	public void open( int mode ) throws MessagingException;

	/**
	 * @param arg0
	 * @see javax.mail.Folder#addMessageChangedListener(javax.mail.event.MessageChangedListener)
	 */
	public void addMessageChangedListener( MessageChangedListener arg0 );

	/**
	 * @param arg0
	 * @throws MessagingException
	 * @see javax.mail.Folder#close(boolean)
	 */
	public void close( boolean arg0 ) throws MessagingException;

	/**
	 * @param msgs
	 * @param mailFolder
	 * @throws MessagingException
	 */
	public void copyMessages( Message[] msgs, IMailFolder mailFolder ) throws MessagingException;

	/**
	 * @param messageIds
	 * @param mailFolder
	 * @throws MessagingException
	 */
	public void copyMessages( long[] messageIds, IMailFolder mailFolder ) throws MessagingException;

	/**
	 * @param arg0
	 * @return
	 * @throws MessagingException
	 * @see javax.mail.Folder#delete(boolean)
	 */
	public boolean delete( boolean arg0 ) throws MessagingException;

	/**
	 * @param msgs
	 * @return
	 * @throws MessagingException
	 */
	public Message[] expunge( Message[] msgs ) throws MessagingException;

	/**
	 * @return
	 * @see javax.mail.Folder#getId()
	 */
	public String getId();

	/**
	 * @return
	 * @throws MessagingException
	 * @see javax.mail.Folder#getMessageCount()
	 */
	public int getMessageCount() throws MessagingException;

	/**
	 * @param messageId
	 * @return
	 * @throws MessagingException
	 */
	public Message getMessageById( long messageId ) throws MessagingException;

	/**
	 * @param messageIds
	 * @return
	 * @throws MessagingException
	 */
	public Message[] getMessagesById( long[] messageIds ) throws MessagingException;

	/**
	 * @return
	 * @throws MessagingException
	 * @see javax.mail.Folder#getSeparator()
	 */
	public char getSeparator() throws MessagingException;

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode();

	/**
	 * @return
	 * @see javax.mail.Folder#isOpen()
	 */
	public boolean isOpen();

	/**
	 * @return
	 * @throws MessagingException
	 * @see com.sun.mail.imap.IMAPFolder#exists()
	 */
	public boolean exists() throws MessagingException;

	/**
	 * @param arg0
	 * @see javax.mail.Folder#removeMessageChangedListener(javax.mail.event.MessageChangedListener)
	 */
	public void removeMessageChangedListener( MessageChangedListener arg0 );

	/**
	 * @param arg0
	 * @return
	 * @throws MessagingException
	 * @see javax.mail.Folder#renameTo(javax.mail.Folder)
	 */
	public boolean renameTo( Folder arg0 ) throws MessagingException;

	/**
	 * Check the rights of this folder for certain operations.
	 * 
	 * @param right
	 * @return
	 */
	public boolean hasRight( Rights.Right right );

	/**
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	public long getUID( Message msg ) throws MessagingException;

	/**
	 * @param msgs
	 * @throws MessagingException
	 */
	public void appendMessages( Message[] msgs ) throws MessagingException;

	/**
	 * @param searchterm
	 * @param amessage
	 * @return
	 * @throws MessagingException
	 * @see com.sun.mail.imap.IMAPFolder#search(javax.mail.search.SearchTerm,
	 *      javax.mail.Message[])
	 */
	public Message[] search( SearchTerm searchterm, Message[] amessage ) throws MessagingException;

	/**
	 * @param searchterm
	 * @return
	 * @throws MessagingException
	 * @see com.sun.mail.imap.IMAPFolder#search(javax.mail.search.SearchTerm)
	 */
	public Message[] search( SearchTerm searchterm ) throws MessagingException;

	/**
	 * @return
	 * @throws MessagingException
	 */
	public boolean hasChildren() throws MessagingException;

	/**
	 * @param msgs
	 * @param fp
	 * @throws MessagingException
	 */
	public void fetch( Message[] msgs, FetchProfile fp ) throws MessagingException;
}