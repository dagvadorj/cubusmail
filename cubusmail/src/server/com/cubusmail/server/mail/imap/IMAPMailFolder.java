/* IMAPMailFolder.java

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
package com.cubusmail.server.mail.imap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageChangedListener;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sun.mail.imap.ACL;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.Rights;

import com.cubusmail.server.mail.IMailFolder;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.util.MessageUtils;

/**
 * Implementation of an imap mail folder.
 * 
 * @author Juergen Schlierf
 */
public class IMAPMailFolder implements IMailFolder, ApplicationContextAware {

	private static final long serialVersionUID = -8334924396229313354L;

	private final static String ATTRIBUTE_HAS_CHILDREN = "\\HasChildren";

	private Logger logger = Logger.getLogger( this.getClass() );

	private IMAPFolder folder;

	private List<IMailFolder> subfolders;

	private ApplicationContext applicationContext;

	public void init( Folder f ) {

		this.folder = (IMAPFolder) f;
		this.subfolders = new ArrayList<IMailFolder>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getName()
	 */
	public String getName() {

		Locale locale = SessionManager.get().getLocale();

		if ( isInbox() ) {
			return this.applicationContext.getMessage( "mailfolder.name.inbox", null, locale );
		}
		else if ( isDraft() ) {
			return this.applicationContext.getMessage( "mailfolder.name.draft", null, locale );
		}
		else if ( isSent() ) {
			return this.applicationContext.getMessage( "mailfolder.name.sent", null, locale );
		}
		else if ( isTrash() ) {
			return this.applicationContext.getMessage( "mailfolder.name.trash", null, locale );
		}
		else {
			return this.folder.getName();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getSubfolders()
	 */
	public IMailFolder[] getSubfolders() {

		if ( this.subfolders != null )
			return this.subfolders.toArray( new IMAPMailFolder[0] );
		return EMPTY_FOLDER_ARRAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#setSubfolders(java.util.List)
	 */
	public void setSubfolders( List<IMailFolder> subfolders ) {

		this.subfolders = subfolders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getFolder()
	 */
	public Folder getFolder() {

		return folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getMessages()
	 */
	public Message[] retrieveMessages( String sortfield ) {

		Message[] msgs = EMPTY_MESSAGE_ARRAY;
		if ( this.folder != null ) {
			try {
				if ( !this.folder.isOpen() ) {
					this.folder.open( Folder.READ_WRITE );
				}

				long time = System.currentTimeMillis();
				logger.debug( "Start getting messages..." );
				msgs = folder.getMessages();
				logger
						.debug( "Millis for getting " + msgs.length + " messages: "
								+ (System.currentTimeMillis() - time) );
				time = System.currentTimeMillis();
				FetchProfile fp = MessageUtils.createFetchProfile( false, sortfield );

				logger.debug( "Start fetching messages..." );
				folder.fetch( msgs, fp );
				logger.debug( "Millis for fetching " + msgs.length + " Messages: "
						+ (System.currentTimeMillis() - time) );

			}
			catch (MessagingException e) {
				logger.error( e.getMessage(), e );
			}
		}

		return msgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getUnreadMessageCount()
	 */
	public int getUnreadMessageCount() {

		try {
			return this.folder.getUnreadMessageCount();
		}
		catch (MessagingException ex) {
			logger.error( ex.getMessage(), ex );
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		int unread = getUnreadMessageCount();
		if ( unread == 0 ) {
			return getName();
		}
		else {
			return getName() + " (" + unread + ")";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#isInbox()
	 */
	public boolean isInbox() {

		if ( this.folder.getName() != null ) {
			return SessionManager.get().getPreferences().getInboxFolderName().equals( getId() );
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#isDraft()
	 */
	public boolean isDraft() {

		String draftFolderId = SessionManager.get().getPreferences().getDraftFolderName();
		if ( !draftFolderId.equals( getId() ) ) {
			return (SessionManager.get().getPreferences().getInboxFolderName() + getSeparator() + draftFolderId)
					.equals( getId() );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#isSent()
	 */
	public boolean isSent() {

		String sentFolderId = SessionManager.get().getPreferences().getSentFolderName();
		if ( !sentFolderId.equals( getId() ) ) {
			return (SessionManager.get().getPreferences().getInboxFolderName() + getSeparator() + sentFolderId)
					.equals( getId() );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#isTrash()
	 */
	public boolean isTrash() {

		String trashFolderId = SessionManager.get().getPreferences().getTrashFolderName();
		if ( !trashFolderId.equals( getId() ) ) {
			return (SessionManager.get().getPreferences().getInboxFolderName() + getSeparator() + trashFolderId)
					.equals( getId() );
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#isMailbox()
	 */
	public boolean isMailbox() {

		return false;
	}

	public boolean isCreateSubfolderSupported() {

		if ( hasRight( Rights.Right.CREATE ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isMoveSupported() {

		if ( hasRight( Rights.Right.CREATE ) && !isInbox() && !isMailbox() && !isTrash() && !isSent() && !isDraft() ) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isRenameSupported() {

		if ( hasRight( Rights.Right.CREATE ) && !isInbox() && !isMailbox() && !isTrash() && !isSent() && !isDraft() ) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDeleteSupported() {

		if ( hasRight( Rights.Right.CREATE ) && !isInbox() && !isMailbox() ) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isEmptySupported() {

		if ( hasRight( Rights.Right.DELETE ) && !isMailbox() ) {
			return true;
		}
		else {
			return false;
		}
	}

	// Delegates for Folder
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#open(int)
	 */
	public void open( int mode ) throws MessagingException {

		folder.open( mode );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailFolder#addMessageChangedListener(javax
	 * .mail.event.MessageChangedListener)
	 */
	public void addMessageChangedListener( MessageChangedListener arg0 ) {

		folder.addMessageChangedListener( arg0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#close(boolean)
	 */
	public void close( boolean arg0 ) throws MessagingException {

		folder.close( arg0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailFolder#copyMessages(javax.mail.Message[],
	 * org.grouplite.mail.ui.mail.IMailFolder)
	 */
	public void copyMessages( Message[] msgs, IMailFolder mailFolder ) throws MessagingException {

		folder.copyMessages( msgs, mailFolder.getFolder() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#copyMessages(long[],
	 * com.cubusmail.mail.IMailFolder)
	 */
	public void copyMessages( long[] messageIds, IMailFolder mailFolder ) throws MessagingException {

		Message[] msgs = this.folder.getMessagesByUID( messageIds );
		folder.copyMessages( msgs, mailFolder.getFolder() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#delete(boolean)
	 */
	public boolean delete( boolean arg0 ) throws MessagingException {

		return folder.delete( arg0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#expunge()
	 */
	public Message[] expunge( Message[] msgs ) throws MessagingException {

		return folder.expunge( msgs );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getFullName()
	 */
	public String getId() {

		return folder.getFullName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getMessageCount()
	 */
	public int getMessageCount() throws MessagingException {

		return folder.getMessageCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#getMessageById(int)
	 */
	public Message getMessageById( long messageId ) throws MessagingException {

		if ( !this.folder.isOpen() ) {
			this.folder.open( Folder.READ_WRITE );
		}
		return this.folder.getMessageByUID( messageId );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#getMessagesById(long[])
	 */
	public Message[] getMessagesById( long[] messageIds ) throws MessagingException {

		if ( !this.folder.isOpen() ) {
			this.folder.open( Folder.READ_WRITE );
		}
		return this.folder.getMessagesByUID( messageIds );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#getSeparator()
	 */
	public char getSeparator() {

		try {
			return folder.getSeparator();
		}
		catch (MessagingException e) {
			return '.';
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#isOpen()
	 */
	public boolean isOpen() {

		return folder.isOpen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailFolder#removeMessageChangedListener(javax
	 * .mail.event.MessageChangedListener)
	 */
	public void removeMessageChangedListener( MessageChangedListener arg0 ) {

		folder.removeMessageChangedListener( arg0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailFolder#renameTo(javax.mail.Folder)
	 */
	public boolean renameTo( Folder arg0 ) throws MessagingException {

		return folder.renameTo( arg0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#exists()
	 */
	public boolean exists() throws MessagingException {

		return folder.exists();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailFolder#hasRight(com.sun.mail.imap.Rights
	 * .Right)
	 */
	public boolean hasRight( Rights.Right right ) {

		try {
			// first check user rights
			Rights userRights = this.folder.myRights();
			if ( userRights != null ) {
				if ( userRights.contains( right ) ) {
					// than check folder rights
					ACL[] acl = this.folder.getACL();
					if ( acl != null && acl.length > 0 ) {
						for (int i = 0; i < acl.length; i++) {
							if ( acl[i].getRights() != null && acl[i].getRights().contains( right ) ) {
								return true;
							}
						}
					}
				}
			}
		}
		catch (MessagingException e) {
			// if ACL not supperted always return true
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#getUID(javax.mail.Message)
	 */
	public long getUID( Message msg ) throws MessagingException {

		return folder.getUID( msg );
	}

	/**
	 * @param arg0
	 * @throws MessagingException
	 * @see com.sun.mail.imap.IMAPFolder#appendMessages(javax.mail.Message[])
	 */
	public void appendMessages( Message[] msgs ) throws MessagingException {

		folder.appendMessages( msgs );
	}

	/**
	 * @param searchterm
	 * @param amessage
	 * @return
	 * @throws MessagingException
	 * @see com.sun.mail.imap.IMAPFolder#search(javax.mail.search.SearchTerm,
	 *      javax.mail.Message[])
	 */
	public Message[] search( SearchTerm searchterm, Message[] amessage ) throws MessagingException {

		return folder.search( searchterm, amessage );
	}

	/**
	 * @param searchterm
	 * @return
	 * @throws MessagingException
	 * @see com.sun.mail.imap.IMAPFolder#search(javax.mail.search.SearchTerm)
	 */
	public Message[] search( SearchTerm searchterm ) throws MessagingException {

		return folder.search( searchterm );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {

		boolean result = false;
		IMailFolder folder = (IMailFolder) obj;
		if ( folder.getId() != null ) {
			if ( folder.getId().equals( getId() ) ) {
				return true;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#hasChildren()
	 */
	public boolean hasChildren() throws MessagingException {

		return ArrayUtils.contains( this.folder.getAttributes(), ATTRIBUTE_HAS_CHILDREN );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailFolder#fetch(javax.mail.Message[],
	 * javax.mail.FetchProfile)
	 */
	public void fetch( Message[] msgs, FetchProfile fp ) throws MessagingException {

		folder.fetch( msgs, fp );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.mail.IMailFolder#getParent()
	 */
	public IMailFolder getParent() throws MessagingException {

		Folder parentFolder = this.folder.getParent();
		if ( parentFolder != null ) {
			IMAPMailFolder newfolder = new IMAPMailFolder();
			newfolder.init( parentFolder );
			return newfolder;
		}

		return null;
	}
}
