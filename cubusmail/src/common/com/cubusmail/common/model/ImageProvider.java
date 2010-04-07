/* ImageProvider.java

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
package com.cubusmail.common.model;

/**
 * File name constants for images.
 * 
 * @author Juergen Schlierf
 */
public interface ImageProvider {
	public static final String IMAGE_PREFIX = "images/";
	
	// Message related icons
	public static final String MSG_NEW = "mail-message-new.png";
	public static final String MSG_REFRESH = "mail_refresh.png";
	public static final String MSG_REPLY = "mail-reply-sender.png";
	public static final String MSG_REPLY_ALL = "mail-reply-all.png";
	public static final String MSG_FORWARD = "mail-forward.png";
	public static final String MSG_READING_PANE_RIGHT = "message/preview-right.gif";
	public static final String MSG_READING_PANE_BOTTOM = "message/preview-bottom.gif";
	public static final String MSG_READING_PANE_HIDE = "message/preview-hide.gif";
	public static final String MSG_COPY = "edit-copy.png";
	public static final String MSG_MOVE = "shape_move_forwards.png";
	public static final String MSG_DELETE = "user-trash.png";
	public static final String MSG_SOURCE = "email_source.png";
	public static final String MSG_MARK = "message/mark.gif";

	public static final String MSG_STATUS_READ = "internet-mail.png";
	public static final String MSG_STATUS_UNREAD = "mail_unread.png";
	public static final String MSG_STATUS_DELETED = "message/email_deleted.png";
	public static final String MSG_STATUS_ANSWERED = "mail-reply-sender.png";
	public static final String MSG_STATUS_DRAFT = "message/mail_draft.png";

	public static final String MSG_SEND = "message/email_go.png";
	public static final String MSG_SAVE_DRAFT = "message/email_edit.png";
	public static final String MSG_ADD_ATTACHMENT = "message/email_attach.png";

	public static final String MSG_ATTACHMENT = "attach.png";
	public static final String MSG_DOWNLOAD = "message/disk.png";
	public static final String MSG_IMAGES_WARNING = "message/warning.gif";

	// Folder related icons
	public static final String MAIL_FOLDER_NEW = "folder_add.png";
	public static final String MAIL_FOLDER_DELETE = "folder_delete.png";
	public static final String MAIL_FOLDER_MOVE = "shape_move_forwards.png";
	public static final String MAIL_FOLDER_RENAME = "folder_edit.png";
	public static final String MAIL_FOLDER_REFRESH = "mail_refresh.png";
	public static final String MAIL_FOLDER_EMPTY = "user-trash.png";
	public static final String MAIL_FOLDER = "folder.png";
	public static final String MAIL_FOLDER_INBOX = "folder/folder_table.png";
	public static final String MAIL_FOLDER_SENT = "folder_go.png";
	public static final String MAIL_FOLDER_DRAFT = "folder_edit.png";
	public static final String MAIL_FOLDER_TRASH_FULL = "user-trash-full.png";
	public static final String MAIL_FOLDER_TRASH_EMPTY = "user-trash.png";
	public static final String MAIL_FOLDER_MAILBOX = "email_link.png";

	// Contact related icons
	public static final String CONTACT_ADD = "contact/user_add.png";
	public static final String CONTACT_EDIT = "contact/user_edit.png";
	public static final String CONTACT_DELETE = "contact/user_delete.png";
	public static final String CONTACT_FOLDER_NEW = "folder_add.png";
	public static final String CONTACT_FOLDER_DELETE = "folder_delete.png";
	public static final String CONTACT_FOLDER_RENAME = "folder_edit.png";
	public static final String ADDRESS_FOLDER = "contact/folder_user.png";

	// Identitiy related icons
	public static final String IDENTITY_ADD = "newspaper_add.png";
	public static final String IDENTITY_DELETE = "newspaper_delete.png";

	// global icons
	public static final String SYSTEM_LOGOUT = "system-log-out.png";
	public static final String FILE_BLANK = "file.png";
	public static final String PRINTER = "printer.gif";

	public static final String PRIORITY_HIGH = "important.gif";
	public static final String PRIORITY_LOW = "arrow_down.png";
	public static final String PRIORITY_NORMAL = "arrow_in.png";

	public static final String LOADING = "loading.gif";
	public static final String PREFERENCES = "preferences.png";
	public static final String CLOSE = "cross.png";
	public static final String CANCEL = "cancel.png";
	public static final String FIND = "find.png";
}
