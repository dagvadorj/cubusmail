/* IMailboxService.java

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
package com.cubusmail.gwtui.client.services;

import com.google.gwt.user.client.rpc.RemoteService;

import com.cubusmail.gwtui.client.exceptions.GWTInvalidSessionException;
import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.model.GWTMessageList;
import com.cubusmail.gwtui.domain.MessageListFields;

/**
 * Interface for MailboxService.
 *
 * @author Juergen Schlierf
 */
public interface IMailboxService extends RemoteService {

	/**
	 * Load the folder tree from server.
	 * 
	 * @return
	 * @throws Exception
	 */
	public GWTMailFolder[] retrieveFolderTree() throws GWTInvalidSessionException, Exception;

	/**
	 * Create a new mail folder.
	 * 
	 * @param parentFolderId
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public GWTMailFolder createFolder( String parentFolderId, String folderName ) throws Exception;

	/**
	 * Move a mail folder into an other
	 * 
	 * @param sourceFolderId
	 * @param targetFolderId
	 * @throws Exception
	 */
	public void moveFolder( String sourceFolderId, String targetFolderId ) throws Exception;

	/**
	 * Rename a mail folder.
	 * 
	 * @param folderId
	 * @param newName
	 * @throws Exception
	 */
	public String renameFolder( String folderId, String newName ) throws Exception;

	/**
	 * Delete a mail folder.
	 * 
	 * @param folderId
	 * @throws Exception
	 */
	public void deleteFolder( String folderId ) throws Exception;

	/**
	 * Empty a mail folder.
	 * 
	 * @param folderId
	 * @throws Exception
	 */
	public void emptyFolder( String folderId ) throws Exception;

	/**
	 * Retrieve all messages and set this mail folder to the current folder.
	 * 
	 * @param folderId
	 * @return
	 * @throws Exception
	 */
	public GWTMessageList retrieveMessages( String folderId, int start, int limit, String sort, String dir,
			String[][] params ) throws Exception;

	/**
	 * Retrieve a single message including message content.
	 * 
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public GWTMessage retrieveMessage( String folderId, long messageId, boolean loadImages ) throws Exception;

	/**
	 * Retrieve the name of the current mail folder including unread message
	 * count.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getFormattedMessageCount( String folderId ) throws Exception;

	/**
	 * @param messageIds
	 * @param flagField
	 * @param mark
	 * @throws Exception
	 */
	public void markMessage( long[] messageIds, MessageListFields flagField, boolean mark ) throws Exception;

	/**
	 * Copy or move messages to a targed mail folder.
	 * 
	 * @param messageIds
	 * @param targetFolderId
	 * @param move
	 * @throws Exception
	 */
	public void copyMoveMessages( long[] messageIds, String targetFolderId, boolean move ) throws Exception;

	/**
	 * Delete messages in the current folder.
	 * 
	 * @param messageIds
	 * @throws Exception
	 */
	public void deleteMessages( long[] messageIds ) throws Exception;

	/**
	 * Send a message.
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void sendMessage( GWTMessage message ) throws Exception;

	/**
	 * Prepare a new message.
	 */
	public void prepareNewMessage();

	/**
	 * Prepare the reply message.
	 */
	public GWTMessage prepareReplyMessage( long messageId, boolean replyAll ) throws Exception;

	/**
	 * Prepare the forward message.
	 */
	public GWTMessage prepareForwardMessage( long messageId ) throws Exception;

	/**
	 * 
	 */
	public void cancelComposeMessage();
	
	/**
	 * Open a draft message.
	 * 
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public GWTMessage openDraftMessage( long messageId ) throws Exception;
	
	/**
	 * Retrieve the current compose message from session.
	 * 
	 * @return
	 * @throws Exception
	 */
	public GWTAttachment[] retrieveCurrentComposeMessageAttachments() throws Exception;

	/**
	 * Remove the attachment of the compose message.
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public GWTAttachment[] removeAttachmentFromComposeMessage( int index ) throws Exception;

	/**
	 * Save the message as draft.
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void saveMessageAsDraft( GWTMessage message ) throws Exception;

	/**
	 * @param htmlText
	 * @return
	 */
	public String convert2PlainText( String htmlText );

	/**
	 * @param plainText
	 * @return
	 */
	public String convert2Html( String plainText );
}
