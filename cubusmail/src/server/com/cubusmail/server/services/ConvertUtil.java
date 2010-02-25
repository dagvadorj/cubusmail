/* ConvertUtil.java

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
package com.cubusmail.server.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.util.HtmlUtils;

import com.cubusmail.common.model.GWTEmailAddress;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.GWTMessageFlags;
import com.cubusmail.common.model.GWTMessageRecord;
import com.cubusmail.common.model.ImageProvider;
import com.cubusmail.common.model.Preferences;
import com.cubusmail.server.mail.IMailFolder;
import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.util.MessageUtils;
import com.cubusmail.server.mail.util.MessageUtils.AddressStringType;
import com.cubusmail.server.util.CubusConstants;
import com.sun.mail.imap.IMAPFolder;

/**
 * Utils for GWT services.
 * 
 * @author Juergen Schlierf
 */
public class ConvertUtil {

	public static GWTMailFolder[] convert( List<IMailFolder> mailFolderList ) {

		if ( mailFolderList != null && mailFolderList.size() > 0 ) {
			GWTMailFolder[] folderArray = new GWTMailFolder[mailFolderList.size()];
			int index = 0;
			for (IMailFolder mailFolder : mailFolderList) {
				folderArray[index++] = convert( mailFolder, true );
			}

			return folderArray;
		}

		return GWTMailFolder.EMPTY_FOLDER_ARRAY;
	}

	/**
	 * Convert instances of IMailFolder to GWTMailFolder including subfolders.
	 * 
	 * @param mailFolder
	 * @return
	 */
	public static GWTMailFolder convert( IMailFolder mailFolder, boolean completeTree ) {

		GWTMailFolder result = new GWTMailFolder();

		result.setId( mailFolder.getId() );
		result.setName( mailFolder.getName() );
		// result.setUnreadMessagesCount( mailFolder.getUnreadMessageCount() );
		result.setInbox( mailFolder.isInbox() );
		result.setDraft( mailFolder.isDraft() );
		result.setSent( mailFolder.isSent() );
		result.setTrash( mailFolder.isTrash() );
		result.setCreateSubfolderSupported( mailFolder.isCreateSubfolderSupported() );
		result.setMoveSupported( mailFolder.isMoveSupported() );
		result.setRenameSupported( mailFolder.isRenameSupported() );
		result.setDeleteSupported( mailFolder.isDeleteSupported() );
		result.setEmptySupported( mailFolder.isEmptySupported() );

		if ( completeTree ) {
			IMailFolder[] subfolders = mailFolder.getSubfolders();
			if ( subfolders.length > 0 ) {
				GWTMailFolder[] gwtSubfolders = new GWTMailFolder[subfolders.length];
				for (int i = 0; i < subfolders.length; i++) {
					IMailFolder subfolder = mailFolder.getSubfolders()[i];
					gwtSubfolders[i] = convert( subfolder, completeTree );
					gwtSubfolders[i].setParent( result );
				}
				result.setSubfolders( gwtSubfolders );
			}
		}

		return result;
	}

	/**
	 * @param mailbox
	 * @return
	 */
	public static GWTMailbox convert( IMailbox mailbox ) {

		GWTMailbox result = new GWTMailbox();

		result.setEmailAddress( mailbox.getEmailAddress() );
		result.setFullName( mailbox.getFullName() );
		result.setUserAccount( mailbox.getUserAccount() );
		result.setLoggedIn( true );

		// result.setMailFolders( convert( mailbox.getMailFolderList() ) );

		return result;
	}

	/**
	 * Convert dedicated messages to string arrays for GridList.
	 * 
	 * @param context
	 * @param preferences
	 * @param currentFolder
	 * @param pageSize
	 * @param msgs
	 * @return
	 * @throws MessagingException
	 */
	public static GWTMessageRecord[] convertMessagesToStringArray( ApplicationContext context, Preferences preferences,
			IMAPFolder currentFolder, int pageSize, Message msgs[] ) throws MessagingException {

		GWTMessageRecord[] result = new GWTMessageRecord[pageSize];

		// get date formats for message list date
		Locale locale = SessionManager.get().getLocale();
		TimeZone timezone = SessionManager.get().getTimeZone();
		String datePattern = context.getMessage( CubusConstants.MESSAGELIST_DATE_FORMAT_PATTERN, null, locale );
		String timePattern = context.getMessage( CubusConstants.MESSAGELIST_TIME_FORMAT_PATTERN, null, locale );

		NumberFormat sizeFormat = MessageUtils.createSizeFormat( locale );

		DateFormat dateFormat = null;
		DateFormat timeFormat = null;
		if ( preferences.isShortTimeFormat() ) {
			dateFormat = new SimpleDateFormat( datePattern, locale );
			timeFormat = new SimpleDateFormat( timePattern, locale );
			timeFormat.setTimeZone( timezone );
		}
		else {
			dateFormat = new SimpleDateFormat( datePattern + " " + timePattern, locale );
		}
		dateFormat.setTimeZone( timezone );
		Date today = Calendar.getInstance( timezone ).getTime();

		for (int i = 0; i < pageSize; i++) {
			result[i] = new GWTMessageRecord();
			if ( preferences.isShortTimeFormat() && DateUtils.isSameDay( today, msgs[i].getSentDate() ) ) {
				// show only time
				convertToStringArray( currentFolder, msgs[i], result[i], timeFormat, sizeFormat );
			}
			else {
				convertToStringArray( currentFolder, msgs[i], result[i], dateFormat, sizeFormat );
			}
		}

		return result;
	}

	/**
	 * @param folder
	 * @param msg
	 * @param result
	 * @throws MessagingException
	 */
	private static void convertToStringArray( IMAPFolder folder, Message msg, GWTMessageRecord result,
			DateFormat dateFormat, NumberFormat decimalFormat ) throws MessagingException {

		result.setId( Long.toString( folder.getUID( msg ) ) );
		try {
			result.setAttachmentImage( MessageUtils.hasAttachments( msg ) ? ImageProvider.MSG_ATTACHMENT : null );
		}
		catch (IOException e) {
			// do nothing
		}
		GWTMessageFlags flags = new GWTMessageFlags();
		flags.setDeleted( msg.isSet( Flags.Flag.DELETED ) );
		flags.setUnread( !msg.isSet( Flags.Flag.SEEN ) );
		flags.setAnswered( msg.isSet( Flags.Flag.ANSWERED ) );
		flags.setDraft( msg.isSet( Flags.Flag.DRAFT ) );
		result.setFlags( flags );

		result.setProrityImage( getPriorityImage( msg ) );
		result.setSubject( HtmlUtils.htmlEscape( msg.getSubject() ) );

		result.setFrom( HtmlUtils.htmlEscape( MessageUtils.getMailAdressString( msg.getFrom(),
				AddressStringType.PERSONAL ) ) );
		result.setTo( HtmlUtils.htmlEscape( MessageUtils.getMailAdressString( msg.getRecipients( RecipientType.TO ),
				AddressStringType.PERSONAL ) ) );
		if ( msg.getSentDate() != null ) {
			result.setSendDateString( HtmlUtils.htmlEscape( dateFormat.format( msg.getSentDate() ) ) );
			result.setSendDate( msg.getSentDate() );
		}

		int msgSize = MessageUtils.calculateAttachmentSize( msg.getSize() );
		result.setSizeString( HtmlUtils.htmlEscape( MessageUtils.formatPartSize( msgSize, decimalFormat ) ) );
		result.setSize( msgSize );
	}

	/**
	 * @param msg
	 * @return
	 */
	private static String getPriorityImage( Message msg ) {

		int priority = MessageUtils.getMessagePriority( msg );
		if ( priority == GWTMailConstants.PRIORITY_NONE ) {
			return null;
		}
		else if ( priority == GWTMailConstants.PRIORITY_VERY_LOW || priority == GWTMailConstants.PRIORITY_LOW ) {
			return ImageProvider.PRIORITY_LOW;
		}
		else if ( priority == GWTMailConstants.PRIORITY_VERY_HIGH || priority == GWTMailConstants.PRIORITY_VERY_HIGH ) {
			return ImageProvider.PRIORITY_HIGH;
		}

		return null;
	}

	/**
	 * @param addresses
	 * @return
	 */
	public static GWTEmailAddress[] convertAddress( Address[] addresses ) throws MessagingException {

		if ( addresses != null ) {
			GWTEmailAddress[] gwtAdresses = new GWTEmailAddress[addresses.length];
			for (int i = 0; i < addresses.length; i++) {
				GWTEmailAddress gwtAddress = new GWTEmailAddress();
				gwtAddress.setInternetAddress( MessageUtils.getMailAdressString( addresses[i],
						AddressStringType.COMPLETE ) );
				gwtAddress.setName( MessageUtils.getMailAdressString( addresses[i], AddressStringType.PERSONAL_ONLY ) );
				gwtAddress.setEmail( MessageUtils.getMailAdressString( addresses[i], AddressStringType.EMAIL ) );
				gwtAdresses[i] = gwtAddress;
			}

			return gwtAdresses;
		}

		return null;
	}

	/**
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getTimezonesBundle( Locale locale ) {

		return ResourceBundle.getBundle( CubusConstants.TIMEZONES_BUNDLE_NAME, locale );
	}
}
