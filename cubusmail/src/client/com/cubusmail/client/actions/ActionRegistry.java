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
package com.cubusmail.client.actions;

import java.util.HashMap;
import java.util.Map;

import com.cubusmail.client.actions.contact.AddContactFromEmailAddressAction;
import com.cubusmail.client.actions.contact.ComposeMessageForEmailAddressAction;
import com.cubusmail.client.actions.folder.DeleteFolderAction;
import com.cubusmail.client.actions.folder.EmptyFolderAction;
import com.cubusmail.client.actions.folder.MoveFolderAction;
import com.cubusmail.client.actions.folder.NewFolderAction;
import com.cubusmail.client.actions.folder.RefreshFolderAction;
import com.cubusmail.client.actions.folder.RenameFolderAction;
import com.cubusmail.client.actions.message.ComposeMessageAction;
import com.cubusmail.client.actions.message.DeleteMessagesAction;
import com.cubusmail.client.actions.message.DownloadAttachmentAction;
import com.cubusmail.client.actions.message.ForwardAction;
import com.cubusmail.client.actions.message.LoadMessageAction;
import com.cubusmail.client.actions.message.MarkMessageAction;
import com.cubusmail.client.actions.message.PrintMessageAction;
import com.cubusmail.client.actions.message.RefreshMessagesAction;
import com.cubusmail.client.actions.message.ReplyAction;
import com.cubusmail.client.actions.message.ReplyAllAction;
import com.cubusmail.client.actions.message.ShowMessageSourceAction;
import com.cubusmail.client.actions.message.ViewAttachmentAction;
import com.cubusmail.client.actions.message.MarkMessageAction.MarkActionType;

/**
 * Registry for all actions used by Cubusmail.
 * 
 * @author Juergen Schlierf
 */
public enum ActionRegistry implements IGWTAction {
	// comman actions
	LOGIN,

	// message actions
	REFRESH_MESSAGES, COMPOSE_MESSAGE, REPLY, REPLY_ALL, FORWARD, COPY_MESSAGES, MOVE_MESSAGES, DELETE_MESSAGES, DELETE_WINDOW_MESSAGE, MARK_AS_READ, MARK_AS_UNREAD, MARK_AS_UNDELETED, SHOW_MESSAGE_SOURCE, PRINT_MESSAGE, LOAD_MESSAGE, MARK_AS_DELETED, DOWNLOAD_ATTACHMENT, VIEW_ATTACHMENT,

	// mail folder actions
	REFRESH_FOLDER, NEW_FOLDER, MOVE_FOLDER, RENAME_FOLDER, DELETE_FOLDER, EMPTY_FOLDER,

	// contact actions
	ADD_CONTACT_FROM_EMAILADDRESS, COMPOSE_MESSAGE_FOR_EMAIL;

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
	 * create actions
	 */
	private IGWTAction create() {

		switch (this) {
		case LOGIN:
			return new LoginAction();

			// messages
		case LOAD_MESSAGE:
			return new LoadMessageAction();
		case REFRESH_MESSAGES:
			return new RefreshMessagesAction();
		case COMPOSE_MESSAGE:
			return new ComposeMessageAction();
		case REPLY:
			return new ReplyAction();
		case REPLY_ALL:
			return new ReplyAllAction();
		case FORWARD:
			return new ForwardAction();
		case DELETE_MESSAGES:
			return new DeleteMessagesAction();
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
		case DOWNLOAD_ATTACHMENT:
			return new DownloadAttachmentAction();
		case VIEW_ATTACHMENT:
			return new ViewAttachmentAction();

			// mail folder
		case REFRESH_FOLDER:
			return new RefreshFolderAction();
		case NEW_FOLDER:
			return new NewFolderAction();
		case RENAME_FOLDER:
			return new RenameFolderAction();
		case DELETE_FOLDER:
			return new DeleteFolderAction();
		case EMPTY_FOLDER:
			return new EmptyFolderAction();
		case MOVE_FOLDER:
			return new MoveFolderAction();

			// contact
		case ADD_CONTACT_FROM_EMAILADDRESS:
			return new AddContactFromEmailAddressAction();
		case COMPOSE_MESSAGE_FOR_EMAIL:
			return new ComposeMessageForEmailAddressAction();
		}

		throw new IllegalArgumentException( "Action missing: " + name() );
	}

	/**
	 * 
	 */
	public void execute() {

		get().execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.actions.IGWTAction#getIcon()
	 */
	public String getIcon() {

		return get().getIcon();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.actions.IGWTAction#getText()
	 */
	public String getText() {

		return get().getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.client.actions.IGWTAction#getTooltip()
	 */
	public String getTooltip() {

		return get().getTooltip();
	}
}
