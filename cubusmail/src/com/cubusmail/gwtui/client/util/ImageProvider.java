/* ImageProvider.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.gwtui.client.util;

/**
 * File name constants for images.
 *
 * @author Jürgen Schlierf
 */
public interface ImageProvider {

	// Message related icons
	public static final String MSG_NEW = "icons/mail-message-new.png";
	public static final String MSG_REFRESH = "icons/mail_refresh.png";
	public static final String MSG_REPLY = "icons/mail-reply-sender.png";
	public static final String MSG_REPLY_ALL = "icons/mail-reply-all.png";
	public static final String MSG_FORWARD = "icons/mail-forward.png";
	public static final String MSG_READING_PANE_RIGHT = "icons/message/preview-right.gif";
	public static final String MSG_READING_PANE_BOTTOM = "icons/message/preview-bottom.gif";
	public static final String MSG_READING_PANE_HIDE = "icons/message/preview-hide.gif";
	public static final String MSG_COPY = "icons/edit-copy.png";
	public static final String MSG_MOVE = "icons/shape_move_forwards.png";
	public static final String MSG_DELETE = "icons/user-trash.png";
	public static final String MSG_SOURCE = "icons/email_source.png";
	public static final String MSG_MARK = "icons/message/mark.gif";

	public static final String MSG_STATUS_READ = "icons/internet-mail.png";
	public static final String MSG_STATUS_UNREAD = "icons/mail_unread.png";
	public static final String MSG_STATUS_DELETED = "icons/message/email_deleted.png";
	public static final String MSG_STATUS_ANSWERED = "icons/mail-reply-sender.png";
	public static final String MSG_STATUS_DRAFT = "icons/message/mail_draft.png";

	public static final String MSG_SEND = "icons/message/email_go.png";
	public static final String MSG_SAVE_DRAFT = "icons/message/email_edit.png";
	public static final String MSG_ADD_ATTACHMENT = "icons/message/email_attach.png";

	public static final String MSG_ATTACHMENT = "icons/attach.png";
	public static final String MSG_DOWNLOAD = "icons/message/disk.png";
	public static final String MSG_IMAGES_WARNING = "icons/message/warning.gif";

	// Folder related icons
	public static final String MAIL_FOLDER_NEW = "icons/folder_add.png";
	public static final String MAIL_FOLDER_DELETE = "icons/folder_delete.png";
	public static final String MAIL_FOLDER_MOVE = "icons/shape_move_forwards.png";
	public static final String MAIL_FOLDER_RENAME = "icons/folder_edit.png";
	public static final String MAIL_FOLDER_REFRESH = "icons/mail_refresh.png";
	public static final String MAIL_FOLDER_EMPTY = "icons/user-trash.png";
	public static final String MAIL_FOLDER = "icons/folder.png";
	public static final String MAIL_FOLDER_INBOX = "icons/folder/folder_table.png";
	public static final String MAIL_FOLDER_SENT = "icons/folder_go.png";
	public static final String MAIL_FOLDER_DRAFT = "icons/folder_edit.png";
	public static final String MAIL_FOLDER_TRASH_FULL = "icons/user-trash-full.png";
	public static final String MAIL_FOLDER_TRASH_EMPTY = "icons/user-trash.png";
	public static final String MAIL_FOLDER_MAILBOX = "icons/email_link.png";

	// Contact related icons
	public static final String CONTACT_ADD = "icons/contact/user_add.png";
	public static final String CONTACT_EDIT = "icons/contact/user_edit.png";
	public static final String CONTACT_DELETE = "icons/contact/user_delete.png";
	public static final String CONTACT_FOLDER_NEW = "icons/folder_add.png";
	public static final String CONTACT_FOLDER_DELETE = "icons/folder_delete.png";
	public static final String CONTACT_FOLDER_RENAME = "icons/folder_edit.png";
	public static final String CONTACT_FOLDER = "icons/contact/folder_user.png";

	// Identitiy related icons
	public static final String IDENTITY_ADD = "icons/newspaper_add.png";
	public static final String IDENTITY_DELETE = "icons/newspaper_delete.png";

	// global icons
	public static final String SYSTEM_LOGOUT = "icons/system-log-out.png";
	public static final String FILE_BLANK = "icons/file.png";
	public static final String PRINTER = "icons/printer.gif";

	public static final String PRIORITY_HIGH = "icons/important.gif";
	public static final String PRIORITY_LOW = "icons/arrow_down.png";
	public static final String PRIORITY_NORMAL = "icons/arrow_in.png";

	public static final String PREFERENCES = "icons/preferences.png";
	public static final String CLOSE = "icons/cross.png";
	public static final String CANCEL = "icons/cancel.png";
}
