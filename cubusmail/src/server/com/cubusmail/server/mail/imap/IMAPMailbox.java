/* IMAPMailbox.java

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
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.event.FolderListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cubusmail.common.model.UserAccount;
import com.cubusmail.common.util.CubusConstants;
import com.cubusmail.server.mail.IMailFolder;
import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.exceptions.IErrorCodes;
import com.cubusmail.server.mail.exceptions.MailFolderException;
import com.cubusmail.server.mail.security.MailboxAuthenticator;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.Rights.Right;

/**
 * Implementation of an imap mailbox.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class IMAPMailbox implements IMailbox, ApplicationContextAware {

	private final Log logger = LogFactory.getLog( getClass() );

	private ApplicationContext applicationContext;

	// Javamail session
	private Session session;

	private IMAPStore store = null;

	private IMailFolder currentFolder = null;

	private Map<String, IMailFolder> mailFolderMap;
	private List<IMailFolder> mailFolderList;

	private MailboxAuthenticator mailboxAuthenticator;

	private String personalNameSpace;

	private char folderSeparator;

	private UserAccount userAccount;

	private String username;

	// configuration params
	private boolean imapPartialfetch;

	private long imapFetchsize;

	private boolean imapSSL;

	private String imapHost;

	private int imapPort;

	private boolean smtpSSL;

	private String smtpHost;

	private int smtpPort;

	private String domainName;

	public IMAPMailbox() {

		this.mailFolderMap = new TreeMap<String, IMailFolder>();
		this.mailFolderList = new ArrayList<IMailFolder>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#init(java.lang.String, java.lang.String)
	 */
	public void init( String username, String password ) {

		this.username = username;
		Properties props = new Properties();
		props.put( "mail.user", username );
		props.put( "mail.imap.partialfetch", this.imapPartialfetch );
		props.put( "mail.imap.fetchsize", this.imapFetchsize );
		String imapProtocol = this.imapSSL ? "imaps" : "imap";
		props.put( "mail.store.protocol", imapProtocol );
		props.put( "mail." + imapProtocol + ".port", this.imapPort );
		props.put( "mail." + imapProtocol + ".host", this.imapHost );
		String smtpProtocol = this.smtpSSL ? "smtps" : "smtp";
		props.put( "mail.transport.protocol", smtpProtocol );
		props.put( "mail." + smtpProtocol + ".port", this.smtpPort );
		props.put( "mail." + smtpProtocol + ".host", this.smtpHost );
		// avoid strange ssl exceptions
		props.put( "mail." + smtpProtocol + ".quitwait", "false" );
		props.put( "mail." + smtpProtocol + ".auth", "true" );

		props.put( "mail.mime.decodetext.strict", "true" );
		props.put( "mail.mime.address.strict", "false" );
		props.put( "mail.mime.charset", CubusConstants.DEFAULT_CHARSET );

		// Security.setProperty( "ssl.SocketFactory.provider",
		// AllCertificatesSSLSocketFactory.class.getName() );
		// props.put( "mail.imap.socketFactory.class",
		// AllCertificatesSSLSocketFactory.class.getName() );
		// props.put( "mail.imap.socketFactory.port",
		// Configuration.get().imapPort );
		// props.put( "mail.imap.socketFactory.fallback", "false" );
		// props.put( "mail.smtp.starttls.enable", "true" );

		this.mailboxAuthenticator.setUsername( username );
		this.mailboxAuthenticator.setPassword( password );
		Session session = Session.getInstance( props, this.mailboxAuthenticator );
		session.setDebug( false );
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailConnection#login()
	 */
	public void login() throws MessagingException {

		if ( this.store == null ) {
			this.store = createStore();
		}

		this.store.connect();
		this.personalNameSpace = "";

		try {
			Folder[] namespaces = store.getPersonalNamespaces();
			if ( namespaces != null && namespaces.length > 0 ) {
				this.personalNameSpace = namespaces[0].getFullName();
				if ( this.personalNameSpace.length() > 0 ) {
					this.personalNameSpace += this.store.getDefaultFolder().getSeparator();
				}
				this.personalNameSpace = this.personalNameSpace.trim();
			}
		}
		catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailConnection#logout()
	 */
	public void logout() throws MessagingException {

		if ( this.currentFolder != null && this.currentFolder.isOpen() ) {
			this.currentFolder.close( true );
			this.currentFolder = null;
		}

		if ( this.store != null ) {
			this.store.close();
			this.store = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#isLoggedIn()
	 */
	public boolean isLoggedIn() {

		if ( this.store != null && this.store.isConnected() ) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailConnection#setCurrentFolder(org.grouplite
	 * .mail.ui.mail.IMailFolder)
	 */
	public void setCurrentFolder( IMailFolder folder ) throws MessagingException {

		if ( this.currentFolder != null && this.currentFolder.isOpen() && !this.currentFolder.equals( folder ) ) {
			this.currentFolder.close( false );
		}
		this.currentFolder = folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailConnection#getCurrentFolder()
	 */
	public IMailFolder getCurrentFolder() {

		return this.currentFolder;
	}

	/**
	 * Deliveres the inbox folder.
	 * 
	 * @return
	 */
	private IMailFolder getInboxFolder() {

		return this.mailFolderMap.get( SessionManager.get().getPreferences().getInboxFolderName() );
	}

	/**
	 * Deliveres the draft folder.
	 * 
	 * @return
	 */
	public IMailFolder getDraftFolder() {

		return getMailFolderById( this.personalNameSpace + SessionManager.get().getPreferences().getDraftFolderName() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#getSentFolder()
	 */
	public IMailFolder getSentFolder() {

		return getMailFolderById( this.personalNameSpace + SessionManager.get().getPreferences().getSentFolderName() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#getTrashFolder()
	 */
	public IMailFolder getTrashFolder() {

		return getMailFolderById( this.personalNameSpace + SessionManager.get().getPreferences().getTrashFolderName() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailConnection#moveFolder(org.grouplite.mail
	 * .ui.mail.IMailFolder, org.grouplite.mail.ui.mail.IMailFolder)
	 */
	public IMailFolder moveFolder( String sourceFolderId, String targetFolderId ) throws MailFolderException {

		IMailFolder sourceFolder = null;
		IMailFolder targetFolder = null;

		try {
			String newFolderName;

			sourceFolder = getMailFolderById( sourceFolderId );
			targetFolder = getMailFolderById( targetFolderId );

			if ( sourceFolder != null ) {
				if ( targetFolder == null ) {
					newFolderName = sourceFolder.getName();
				}
				else {
					newFolderName = targetFolder.getId() + getFolderSeparator() + sourceFolder.getName();
				}

				if ( sourceFolder.isOpen() ) {
					sourceFolder.close( false );
				}
				if ( targetFolder != null && targetFolder.isOpen() ) {
					targetFolder.close( false );
				}

				Folder newFolder = this.store.getFolder( newFolderName );
				if ( !newFolder.exists() ) {
					sourceFolder.renameTo( newFolder );
				}
				else {
					throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_ALREADY_EXIST, null, sourceFolder );
				}
				loadMailFolder();

				return createMailFolder( newFolder );
			}
		}
		catch (MessagingException e) {
			throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_MOVE, e, sourceFolder );
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailConnection#renameFolder(org.grouplite
	 * .mail.ui.mail.IMailFolder, java.lang.String)
	 */
	public IMailFolder renameFolder( String folderId, String folderName ) throws MailFolderException {

		IMailFolder folder = getMailFolderById( folderId );
		try {
			String newName = folder.getId();
			if ( newName.lastIndexOf( getFolderSeparator() ) >= 0 ) {
				newName = newName.substring( 0, newName.lastIndexOf( getFolderSeparator() ) + 1 ) + folderName;
			}
			else {
				newName = folderName;
			}

			if ( folder.isOpen() ) {
				folder.close( false );
			}
			Folder newFolder = this.store.getFolder( newName );
			if ( !newFolder.exists() ) {
				if ( !folder.renameTo( newFolder ) ) {
					throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_RENAME, null, folder );
				}
			}
			else {
				throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_ALREADY_EXIST, null,
						createMailFolder( newFolder ) );
			}
			loadMailFolder();
			return createMailFolder( newFolder );
		}
		catch (MessagingException ex) {
			throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_RENAME, ex, folder );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#emptyFolder(java.lang.String)
	 */
	public void emptyFolder( String folderId ) throws MailFolderException {

		IMailFolder folder = getMailFolderById( folderId );

		try {
			if ( !folder.isOpen() ) {
				folder.open( Folder.READ_WRITE );
			}

			if ( folder.getMessageCount() > 0 ) {
				Message[] msgs = folder.retrieveMessages( null, true, null, null );
				for (int i = 0; i < folder.getMessageCount(); i++) {
					msgs[i].setFlag( Flags.Flag.DELETED, true );
				}
			}
			folder.close( true );
		}
		catch (MessagingException ex) {
			throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_EMPTY, ex, folder );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#deleteFolder(java.lang.String)
	 */
	public void deleteFolder( String folderId ) throws MailFolderException {

		IMailFolder folder = getMailFolderById( folderId );

		try {
			if ( folder.hasRight( Right.DELETE ) ) {
				if ( folder.isOpen() ) {
					folder.close( false );
				}

				if ( folder.delete( true ) ) {
					loadMailFolder();
					setCurrentFolder( getInboxFolder() );
				}
				else {
					throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_DELETE, null, folder );
				}
			}
		}
		catch (MessagingException ex) {
			throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_DELETE, ex, folder );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailConnection#createFolder(org.grouplite
	 * .mail.ui.mail.IMailFolder, java.lang.String)
	 */
	public IMailFolder createFolder( String parentFolderId, String folderName ) throws MailFolderException {

		try {

			String newFolderName = null;
			if ( !StringUtils.isEmpty( parentFolderId ) ) {
				newFolderName = parentFolderId + getFolderSeparator() + folderName;
			}
			else {
				newFolderName = folderName;
			}

			Folder newFolder = this.store.getFolder( newFolderName );
			if ( !newFolder.exists() ) {
				logger.debug( "Creating folder... " + newFolderName );
				boolean success = newFolder.create( Folder.HOLDS_MESSAGES );
				if ( !success ) {
					throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_CREATE, null );
				}
				newFolder.setSubscribed( true );
			}
			else {
				throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_ALREADY_EXIST, null );
			}
			loadMailFolder();

			return createMailFolder( newFolder );
		}
		catch (MessagingException ex) {
			throw new MailFolderException( IErrorCodes.EXCEPTION_FOLDER_CREATE, ex );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailConnection#reloadFolder()
	 */
	public void reloadFolder() throws MessagingException {

		this.loadMailFolder();
		this.currentFolder = getInboxFolder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#copyMessages(long[], java.lang.String)
	 */
	public void copyMessages( long[] messageIds, String targetFolderId ) throws MessagingException {

		if ( !this.currentFolder.isOpen() ) {
			this.currentFolder.open( Folder.READ_WRITE );
		}
		IMailFolder targetFolder = getMailFolderById( targetFolderId );
		if ( !targetFolder.isOpen() ) {
			targetFolder.open( Folder.READ_WRITE );
		}

		this.currentFolder.copyMessages( messageIds, targetFolder );

		targetFolder.close( false );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#deleteMessages(long[])
	 */
	public void deleteMessages( long[] messageIds ) throws MessagingException {

		if ( messageIds != null && messageIds.length > 0 ) {
			if ( !this.currentFolder.isOpen() ) {
				this.currentFolder.open( Folder.READ_WRITE );
			}

			Message[] msgs = this.currentFolder.getMessagesById( messageIds );

			for (int i = 0; i < msgs.length; i++) {
				msgs[i].setFlag( Flags.Flag.DELETED, true );
			}

			this.currentFolder.expunge( msgs );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#getMailFolderById(java.lang.String)
	 */
	public IMailFolder getMailFolderById( String id ) {

		return this.mailFolderMap.get( id );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.grouplite.mail.ui.mail.IMailConnection#addFolderListener(javax.mail
	 * .event.FolderListener)
	 */
	public void addFolderListener( FolderListener listener ) {

		this.store.addFolderListener( listener );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailConnection#getConnectionName()
	 */
	public String getUserName() {

		return this.username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#getEmailAddress()
	 */
	public String getEmailAddress() {

		if ( this.username.indexOf( "@" ) > 0 ) {
			return this.username;
		}
		else {
			return this.username + "@" + this.domainName;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#getFullName()
	 */
	public String getFullName() {

		return "Juergen Schlierf";
	}

	/**
	 * 
	 */
	private IMAPStore createStore() {

		IMAPStore store = null;
		try {
			store = (IMAPStore) session.getStore();
		}
		catch (NoSuchProviderException e) {
			logger.error( e.getMessage(), e );
		}

		return store;
	}

	/**
	 * @throws MessagingException
	 */
	private void loadMailFolder() throws MessagingException {

		logger.debug( "loading folder tree..." );
		long millis = System.currentTimeMillis();
		this.mailFolderMap.clear();
		this.mailFolderList.clear();
		Folder defaultFolder = this.store.getDefaultFolder();
		this.folderSeparator = defaultFolder.getSeparator();

		// read all folders to a map
		List<String> topFolderNames = new ArrayList<String>();
		Folder[] allFolders = defaultFolder.list( "*" );
		if ( allFolders != null && allFolders.length > 0 ) {
			for (Folder folder : allFolders) {
				this.mailFolderMap.put( folder.getFullName(), createMailFolder( folder ) );
				if ( SessionManager.get().getPreferences().getInboxFolderName().equals( folder.getFullName() ) ) {
					topFolderNames.add( 0, SessionManager.get().getPreferences().getInboxFolderName() );
				}
				else {
					String folderName = folder.getFullName();
					if ( !StringUtils.isEmpty( this.personalNameSpace )
							&& folderName.startsWith( this.personalNameSpace ) ) {
						folderName = StringUtils.substringAfter( folderName, this.personalNameSpace );
					}
					if ( StringUtils.countMatches( folderName, String.valueOf( getFolderSeparator() ) ) == 0 ) {
						topFolderNames.add( folder.getFullName() );
					}
				}
			}
		}

		// build the tree structure
		for (String folderName : topFolderNames) {
			IMailFolder mailFolder = this.mailFolderMap.get( folderName );
			this.mailFolderList.add( mailFolder );
			if ( mailFolder.hasChildren() ) {
				mailFolder.setSubfolders( getSubfolders( mailFolder ) );
			}
		}

		logger.debug( "...finish: " + (System.currentTimeMillis() - millis) + "ms" );
	}

	/**
	 * @param mailFolder
	 * @return
	 * @throws MessagingException
	 */
	private List<IMailFolder> getSubfolders( IMailFolder mailFolder ) throws MessagingException {

		List<IMailFolder> subfolders = new ArrayList<IMailFolder>();
		String searchKey = mailFolder.getId() + getFolderSeparator();

		Set<String> keys = this.mailFolderMap.keySet();
		for (String key : keys) {
			if ( key.startsWith( searchKey )
					&& !StringUtils.contains( StringUtils.substringAfter( key, searchKey ), getFolderSeparator() ) ) {

				IMailFolder subfolder = this.mailFolderMap.get( key );
				subfolders.add( subfolder );
				if ( subfolder.hasChildren() ) {
					subfolder.setSubfolders( getSubfolders( subfolder ) );
				}
			}
		}

		return subfolders;
	}

	/**
	 * @return
	 * @throws MessagingException
	 */
	private char getFolderSeparator() throws MessagingException {

		return this.folderSeparator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.grouplite.mail.ui.mail.IMailConnection#getSession()
	 */
	public Session getJavaMailSession() {

		return session;
	}

	public List<IMailFolder> getMailFolderList() {

		return this.mailFolderList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.mail.IMailbox#getUserAccount()
	 */
	public UserAccount getUserAccount() {

		return this.userAccount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.mail.IMailbox#setUserAccount(com.cubusmail.user.UserAccount
	 * )
	 */
	public void setUserAccount( UserAccount userAccount ) {

		this.userAccount = userAccount;
	}

	/**
	 * @param mailFolderList
	 *            The mailFolderList to set.
	 */
	public void setMailFolderList( List<IMailFolder> mailFolderList ) {

		this.mailFolderList = mailFolderList;
	}

	/**
	 * @param folderSeparator
	 *            The folderSeparator to set.
	 */
	public void setFolderSeparator( char folderSeparator ) {

		this.folderSeparator = folderSeparator;
	}

	/**
	 * @param imapPartialfetch
	 *            The imapPartialfetch to set.
	 */
	public void setImapPartialfetch( boolean imapPartialfetch ) {

		this.imapPartialfetch = imapPartialfetch;
	}

	/**
	 * @param imapFetchsize
	 *            The imapFetchsize to set.
	 */
	public void setImapFetchsize( long imapFetchsize ) {

		this.imapFetchsize = imapFetchsize;
	}

	/**
	 * @param imapSSL
	 *            The imapSSL to set.
	 */
	public void setImapSSL( boolean imapSSL ) {

		this.imapSSL = imapSSL;
	}

	/**
	 * @param imapHost
	 *            The imapHost to set.
	 */
	public void setImapHost( String imapHost ) {

		this.imapHost = imapHost;
	}

	/**
	 * @param imapPort
	 *            The imapPort to set.
	 */
	public void setImapPort( int imapPort ) {

		this.imapPort = imapPort;
	}

	/**
	 * @param smtpSSL
	 *            The smtpSSL to set.
	 */
	public void setSmtpSSL( boolean smtpSSL ) {

		this.smtpSSL = smtpSSL;
	}

	/**
	 * @param smtpHost
	 *            The smtpHost to set.
	 */
	public void setSmtpHost( String smtpHost ) {

		this.smtpHost = smtpHost;
	}

	/**
	 * @param smptPort
	 *            The smptPort to set.
	 */
	public void setSmtpPort( int smtpPort ) {

		this.smtpPort = smtpPort;
	}

	/**
	 * @param domainName
	 *            The domainName to set.
	 */
	public void setDomainName( String domainName ) {

		this.domainName = domainName;
	}

	/**
	 * @param mailboxAuthenticator
	 *            The mailboxAuthenticator to set.
	 */
	public void setMailboxAuthenticator( MailboxAuthenticator mailboxAuthenticator ) {

		this.mailboxAuthenticator = mailboxAuthenticator;
	}

	/**
	 * @param folder
	 * @return
	 */
	private IMailFolder createMailFolder( Folder folder ) {

		IMAPMailFolder mailFolder = this.applicationContext.getBean( IMAPMailFolder.class );
		mailFolder.init( folder );
		return mailFolder;
	}

	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}
}
