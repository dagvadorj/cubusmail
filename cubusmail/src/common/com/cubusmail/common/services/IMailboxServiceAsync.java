/* IMailboxServiceAsync.java

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
package com.cubusmail.common.services;

import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.GWTMessageList;
import com.cubusmail.common.model.MessageFlags;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface for MailboxService.
 *
 * @author Juergen Schlierf
 */
public interface IMailboxServiceAsync {

	/**
	 * Load the folder tree from server.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void retrieveFolderTree( AsyncCallback<GWTMailFolder[]> callback );

	/**
	 * Create a new mail folder.
	 * 
	 * @param parentFolderId
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public void createFolder( String parentFolderId, String folderName, AsyncCallback<GWTMailFolder> callback );

	/**
	 * Move a mail folder into an other
	 * 
	 * @param sourceFolderId
	 * @param targetFolderId
	 * @throws Exception
	 */
	public void moveFolder( String sourceFolderId, String targetFolderId, AsyncCallback<Void> callback );

	/**
	 * Rename a mail folder.
	 * 
	 * @param folderId
	 * @param newName
	 * @throws Exception
	 */
	public void renameFolder( String folderId, String newName, AsyncCallback<String> callback );

	/**
	 * Delete a mail folder.
	 * 
	 * @param folderId
	 * @throws Exception
	 */
	public void deleteFolder( String folderId, AsyncCallback<Void> callback );

	/**
	 * Empty a mail folder.
	 * 
	 * @param folderId
	 * @throws Exception
	 */
	public void emptyFolder( String folderId, AsyncCallback<Void> callback );

	/**
	 * Retrieve all messages and set this mail folder to the current folder.
	 * 
	 * @param folderId
	 * @return
	 * @throws Exception
	 */
	public void retrieveMessages( String folderId, int start, int limit, String sortColumn, boolean ascending, String[][] params,
			AsyncCallback<GWTMessageList> callback );

	/**
	 * Retrieve a single message including message content.
	 * 
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public void retrieveMessage( String folderId, long messageId, boolean loadImages, AsyncCallback<GWTMessage> callback );

	/**
	 * Retrieve the name of the current mail folder including unread message
	 * count.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getFormattedMessageCount( String folderId, AsyncCallback<String> callback );

	/**
	 * @param messageIds
	 * @param flagField
	 * @param mark
	 * @throws Exception
	 */
	public void markMessage( long[] messageIds, MessageFlags flag, boolean mark, AsyncCallback<Void> callback );

	/**
	 * Copy or move messages to a targed mail folder.
	 * 
	 * @param messageIds
	 * @param targetFolderId
	 * @param move
	 * @throws Exception
	 */
	public void copyMoveMessages( long[] messageIds, String targetFolderId, boolean move, AsyncCallback<Void> callback );

	/**
	 * Delete messages in the current folder.
	 * 
	 * @param messageIds
	 * @throws Exception
	 */
	public void deleteMessages( long[] messageIds, AsyncCallback<Void> callback );

	/**
	 * Send a message.
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void sendMessage( GWTMessage message, AsyncCallback<Void> callback );

	/**
	 * Prepare a new message.
	 */
	public void prepareNewMessage( AsyncCallback<Void> callback );

	/**
	 * Prepare the reply message.
	 */
	public void prepareReplyMessage( long messageId, boolean replyAll, AsyncCallback<GWTMessage> callback );

	/**
	 * Prepare the forward message.
	 */
	public void prepareForwardMessage( long messageId, AsyncCallback<GWTMessage> callback );

	/**
	 * 
	 */
	public void cancelComposeMessage( AsyncCallback<Void> callback );

	/**
	 * Open a draft message.
	 * 
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	public void openDraftMessage( long messageId, AsyncCallback<GWTMessage> callback );

	/**
	 * Retrieve the current compose message from session.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void retrieveCurrentComposeMessageAttachments( AsyncCallback<GWTAttachment[]> callback );

	/**
	 * Remove the attachment of the compose message.
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public void removeAttachmentFromComposeMessage( int index, AsyncCallback<GWTAttachment[]> callback );

	/**
	 * Save the message as draft.
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void saveMessageAsDraft( GWTMessage message, AsyncCallback<Void> callback );

	/**
	 * @param htmlText
	 * @return
	 */
	public void convert2PlainText( String htmlText, AsyncCallback<String> callback );

	/**
	 * @param plainText
	 * @return
	 */
	public void convert2Html( String plainText, AsyncCallback<String> callback );
}
