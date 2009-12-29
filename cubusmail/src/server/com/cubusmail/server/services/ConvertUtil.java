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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;

import org.apache.commons.lang.StringUtils;

import com.cubusmail.common.model.GWTAddress;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.ImageProvider;
import com.cubusmail.common.model.MessageListFields;
import com.cubusmail.server.mail.IMailFolder;
import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.util.MessageUtils;
import com.cubusmail.server.mail.util.MessageUtils.AddressStringType;
import com.cubusmail.server.util.CubusConstants;

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
	 * @param messages
	 * @return
	 * @throws MessagingException
	 */
	public static GWTMessage[] convert( Message[] messages ) throws MessagingException {

		if ( messages != null ) {
			GWTMessage[] result = new GWTMessage[messages.length];
			for (int i = 0; i < messages.length; i++) {
				result[i] = convert( messages[i] );
			}
			return result;

		}
		else {
			return null;
		}
	}

	/**
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	public static GWTMessage convert( Message msg ) throws MessagingException {

		GWTMessage result = new GWTMessage();
		result.setId( msg.getMessageNumber() );
		result.setSubject( msg.getSubject() );
		result.setFrom( MessageUtils.getMailAdressString( msg.getFrom(), AddressStringType.COMPLETE ) );
		result.setTo( MessageUtils.getMailAdressString( msg.getRecipients( Message.RecipientType.TO ),
				AddressStringType.COMPLETE ) );
		result.setCc( MessageUtils.getMailAdressString( msg.getRecipients( Message.RecipientType.CC ),
				AddressStringType.COMPLETE ) );
		result.setDate( msg.getSentDate() );
		result.setSize( MessageUtils.calculateAttachmentSize( msg.getSize() ) );

		return result;
	}

	/**
	 * @param folder
	 * @param msg
	 * @param result
	 * @throws MessagingException
	 */
	public static void convertToStringArray( IMailFolder folder, Message msg, String[] result, DateFormat dateFormat,
			NumberFormat decimalFormat ) throws MessagingException {

		result[MessageListFields.ID.ordinal()] = Long.toString( folder.getUID( msg ) );
		try {
			result[MessageListFields.ATTACHMENT_IMAGE.ordinal()] = MessageUtils.hasAttachments( msg ) ? ImageProvider.MSG_ATTACHMENT
					: null;
		}
		catch (IOException e) {
			// do nothing
		}
		result[MessageListFields.FLAG_IMAGE.ordinal()] = getFlagImage( msg );
		result[MessageListFields.PRIORITY_IMAGE.ordinal()] = getPriorityImage( msg );
		if ( !StringUtils.isEmpty( msg.getSubject() ) ) {
			result[MessageListFields.SUBJECT.ordinal()] = msg.getSubject();
		}
		result[MessageListFields.FROM.ordinal()] = MessageUtils.getMailAdressString( msg.getFrom(),
				AddressStringType.PERSONAL );
		result[MessageListFields.TO.ordinal()] = MessageUtils.getMailAdressString(
				msg.getRecipients( RecipientType.TO ), AddressStringType.PERSONAL );
		if ( msg.getSentDate() != null ) {
			result[MessageListFields.SEND_DATE.ordinal()] = dateFormat.format( msg.getSentDate() );
		}
		result[MessageListFields.SIZE.ordinal()] = MessageUtils.formatPartSize( MessageUtils
				.calculateAttachmentSize( msg.getSize() ), decimalFormat );
	}

	/**
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	private static String getFlagImage( Message msg ) throws MessagingException {

		if ( msg.isSet( Flags.Flag.DELETED ) ) {
			return ImageProvider.MSG_STATUS_DELETED;
		}
		else if ( msg.isSet( Flags.Flag.ANSWERED ) ) {
			return ImageProvider.MSG_STATUS_ANSWERED;
		}
		else if ( msg.isSet( Flags.Flag.DRAFT ) ) {
			return ImageProvider.MSG_STATUS_DRAFT;
		}
		else if ( !msg.isSet( Flags.Flag.SEEN ) ) {
			return ImageProvider.MSG_STATUS_UNREAD;
		}

		return null;
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
	public static GWTAddress[] convertAddress( Address[] addresses ) throws MessagingException {

		if ( addresses != null ) {
			GWTAddress[] gwtAdresses = new GWTAddress[addresses.length];
			for (int i = 0; i < addresses.length; i++) {
				GWTAddress gwtAddress = new GWTAddress();
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
