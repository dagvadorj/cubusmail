/* IMailbox.java

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
package com.cubusmail.mail;

import java.io.Serializable;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;

import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.mail.exceptions.MailFolderException;

/**
 * Interface for Mailbox.
 *
 * @author Juergen Schlierf
 */
public interface IMailbox extends Serializable {

	public static final String TYPE_IMAP = "imap";

	/**
	 * Initialize the mailbox.
	 * 
	 * @param username
	 * @param password
	 */
	public void init( String username, String password );

	/**
	 * Login and creates a mail store
	 * 
	 * @throws MessagingException
	 */
	public void login() throws MessagingException;

	/**
	 * Logout
	 * 
	 * @throws LogoutException
	 */
	public void logout() throws MessagingException;

	/**
	 * Is the user logged in?
	 * 
	 * @return
	 */
	public boolean isLoggedIn();

	/**
	 * Retrieve the current mail folder.
	 * 
	 * @return
	 */
	public IMailFolder getCurrentFolder();

	/**
	 * Set the current mail folder.
	 * 
	 * @param folder
	 */
	public void setCurrentFolder( IMailFolder folder ) throws MessagingException;

	/**
	 * Rename the selected folder.
	 * 
	 * @param folder
	 * @param newName
	 */
	public void renameFolder( String folderId, String newName ) throws MailFolderException;

	/**
	 * Move the source folder into to target folder.
	 * 
	 * @param sourceFolder
	 * @param targetFolder
	 * @throws MailFolderException
	 */
	public void moveFolder( String sourceFolderId, String targetFolderId ) throws MailFolderException;

	/**
	 * Delete all messages of a folder.
	 * 
	 * @param folderId
	 */
	public void emptyFolder( String folderId ) throws MailFolderException;

	/**
	 * deletes a folder and all subfolders
	 * 
	 * @param folderId
	 */
	public void deleteFolder( String folderId ) throws MailFolderException;

	/**
	 * Creates a new folder.
	 * 
	 * @param parentFolder
	 * @param folderName
	 */
	public IMailFolder createFolder( String parentFolderId, String folderName ) throws MailFolderException;

	/**
	 * Copy the messages to targetFolder.
	 * 
	 * @param messageIds
	 * @param targetFolderId
	 */
	public void copyMessages( long[] messageIds, String targetFolderId ) throws MessagingException;

	/**
	 * Delete all mails with expunction.
	 * 
	 * @param messageIds
	 * @throws MessagingException
	 */
	public void deleteMessages( long[] messageIds ) throws MessagingException;

	/**
	 * Reload the folder.
	 * 
	 * @throws MessagingException
	 */
	public void reloadFolder() throws MessagingException;

	/**
	 * Find a specific mail folder by id.
	 * 
	 * @param folder
	 * @return
	 */
	public IMailFolder getMailFolderById( String id );

	/**
	 * Login user name.
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * Email address of the mailbox owner.
	 * 
	 * @return
	 */
	public String getEmailAddress();

	/**
	 * The useres real name.
	 * 
	 * @return
	 */
	public String getFullName();

	/**
	 * JavaMail sesssion
	 * 
	 * @return
	 */
	public Session getJavaMailSession();

	/**
	 * Deliveres the draft folder.
	 * 
	 * @return
	 */
	public IMailFolder getDraftFolder();

	/**
	 * Deliveres the sent folder.
	 * 
	 * @return
	 */
	public IMailFolder getSentFolder();

	/**
	 * Deliveres the trash folder.
	 * 
	 * @return
	 */
	public IMailFolder getTrashFolder();

	/**
	 * @return Returns the mailFolderList.
	 */
	public List<IMailFolder> getMailFolderList();

	/**
	 * @return Returns the userAccount.
	 */
	public UserAccount getUserAccount();

	/**
	 * @param userAccount The userAccount to set.
	 */
	public void setUserAccount( UserAccount userAccount );
}
