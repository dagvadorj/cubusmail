/* ActionRegistry.java

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

package com.cubusmail.gwtui.client.actions;

import java.util.HashMap;
import java.util.Map;

import com.cubusmail.gwtui.client.actions.contact.AddContactAction;
import com.cubusmail.gwtui.client.actions.contact.DeleteContactAction;
import com.cubusmail.gwtui.client.actions.contact.EditContactAction;
import com.cubusmail.gwtui.client.actions.contact.NewMessageForContactAction;
import com.cubusmail.gwtui.client.actions.folder.DeleteFolderAction;
import com.cubusmail.gwtui.client.actions.folder.EmptyFolderAction;
import com.cubusmail.gwtui.client.actions.folder.MoveFolderAction;
import com.cubusmail.gwtui.client.actions.folder.NewFolderAction;
import com.cubusmail.gwtui.client.actions.folder.RefreshFolderAction;
import com.cubusmail.gwtui.client.actions.folder.RenameFolderAction;
import com.cubusmail.gwtui.client.actions.message.AddAttachmentAction;
import com.cubusmail.gwtui.client.actions.message.CancelSendAction;
import com.cubusmail.gwtui.client.actions.message.CloseMessageWindowAction;
import com.cubusmail.gwtui.client.actions.message.CopyMoveMessagesAction;
import com.cubusmail.gwtui.client.actions.message.DeleteMessagesAction;
import com.cubusmail.gwtui.client.actions.message.DeleteWindowMessageAction;
import com.cubusmail.gwtui.client.actions.message.ForwardAction;
import com.cubusmail.gwtui.client.actions.message.MarkMessageAction;
import com.cubusmail.gwtui.client.actions.message.NewMessageAction;
import com.cubusmail.gwtui.client.actions.message.PrintMessageAction;
import com.cubusmail.gwtui.client.actions.message.ReadingPaneAction;
import com.cubusmail.gwtui.client.actions.message.RefreshMessagesAction;
import com.cubusmail.gwtui.client.actions.message.ReplyAction;
import com.cubusmail.gwtui.client.actions.message.ReplyAllAction;
import com.cubusmail.gwtui.client.actions.message.SaveMessageDraftAction;
import com.cubusmail.gwtui.client.actions.message.SendMessageAction;
import com.cubusmail.gwtui.client.actions.message.ShowMessageSourceAction;
import com.cubusmail.gwtui.client.actions.message.MarkMessageAction.MarkActionType;

/**
 * Registry of all actions used by Cubusmail.
 * 
 * @author Juergen Schlierf
 */
public enum ActionRegistry {
	// comman actions
	READING_PANE, OPEN_PREFERENCES, REFRESH_MESSAGES, LOGOUT,

	// message actions
	NEW_MESSAGE, REPLY, REPLY_ALL, FORWARD, COPY_MESSAGES, MOVE_MESSAGES, DELETE_MESSAGES, DELETE_WINDOW_MESSAGE, MARK_AS_READ, MARK_AS_UNREAD, MARK_AS_DELETED, MARK_AS_UNDELETED, SHOW_MESSAGE_SOURCE, PRINT_MESSAGE, CLOSE_MESSAGE_WINDOW,

	// compose message actions
	SEND_MESSAGE, CANCEL_SEND_MESSAGE, SAVE_MESSAGE_DRAFT, ADD_ATTACHMENT,

	// mail folder actions
	REFRESH_FOLDER, NEW_FOLDER, MOVE_FOLDER, RENAME_FOLDER, DELETE_FOLDER, EMPTY_FOLDER,

	// contact actions
	ADD_CONTACT, EDIT_CONTACT, DELETE_CONTACT, NEW_MESSAGE_FOR_CONTACT;

	private static Map<ActionRegistry, IGWTAction> ACTION_MAP = new HashMap<ActionRegistry, IGWTAction>();

	/**
	 * @return
	 */
	public IGWTAction get() {

		IGWTAction result = ACTION_MAP.get( this );
		if ( result == null ) {
			result = create();
			ACTION_MAP.put( this, result );
		}

		return result;
	}

	/**
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IGWTAction> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}

	/**
	 * create actions for the main toolbar
	 */
	private IGWTAction create() {

		switch (this) {
		case READING_PANE:
			return new ReadingPaneAction();
		case OPEN_PREFERENCES:
			return new OpenPreferencesAction();
		case REFRESH_MESSAGES:
			return new RefreshMessagesAction();
		case LOGOUT:
			return new LogoutAction();

		case NEW_MESSAGE:
			return new NewMessageAction();
		case REPLY:
			return new ReplyAction();
		case REPLY_ALL:
			return new ReplyAllAction();
		case FORWARD:
			return new ForwardAction();
		case COPY_MESSAGES:
			return new CopyMoveMessagesAction( false );
		case MOVE_MESSAGES:
			return new CopyMoveMessagesAction( true );
		case DELETE_MESSAGES:
			return new DeleteMessagesAction();
		case DELETE_WINDOW_MESSAGE:
			return new DeleteWindowMessageAction();
		case MARK_AS_READ:
			return new MarkMessageAction( MarkActionType.READ );
		case MARK_AS_UNREAD:
			return new MarkMessageAction( MarkActionType.UNREAD );
		case MARK_AS_DELETED:
			return new MarkMessageAction( MarkActionType.DELETED );
		case MARK_AS_UNDELETED:
			return new MarkMessageAction( MarkActionType.UNDELETED );
		case SHOW_MESSAGE_SOURCE:
			return new ShowMessageSourceAction();
		case PRINT_MESSAGE:
			return new PrintMessageAction();
		case CLOSE_MESSAGE_WINDOW:
			return new CloseMessageWindowAction();

		case SEND_MESSAGE:
			return new SendMessageAction();
		case CANCEL_SEND_MESSAGE:
			return new CancelSendAction();
		case SAVE_MESSAGE_DRAFT:
			return new SaveMessageDraftAction();
		case ADD_ATTACHMENT:
			return new AddAttachmentAction();

		case REFRESH_FOLDER:
			return new RefreshFolderAction();
		case NEW_FOLDER:
			return new NewFolderAction();
		case MOVE_FOLDER:
			return new MoveFolderAction();
		case RENAME_FOLDER:
			return new RenameFolderAction();
		case DELETE_FOLDER:
			return new DeleteFolderAction();
		case EMPTY_FOLDER:
			return new EmptyFolderAction();

		case ADD_CONTACT:
			return new AddContactAction();
		case EDIT_CONTACT:
			return new EditContactAction();
		case DELETE_CONTACT:
			return new DeleteContactAction();
		case NEW_MESSAGE_FOR_CONTACT:
			return new NewMessageForContactAction();
		}

		throw new IllegalArgumentException( "Action missing: " + name() );
	}

	/**
	 * 
	 */
	public void execute() {

		get().execute();
	}
}
