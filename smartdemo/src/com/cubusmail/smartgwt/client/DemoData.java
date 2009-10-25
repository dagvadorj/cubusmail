/**
 * 
 */
package com.cubusmail.smartgwt.client;

/**
 * @author schlierf
 * 
 */
public class DemoData {

	private static ExplorerTreeNode[] data;

	public static ExplorerTreeNode[] getData() {
		if (data == null) {
			data = new ExplorerTreeNode[] {
					new ExplorerTreeNode("Inbox", "mailfolder_inbox",
							"mailaccount", ImageProvider.MAIL_FOLDER_INBOX),
					new ExplorerTreeNode("Sent", "mailfolder_sent",
							"mailaccount", ImageProvider.MAIL_FOLDER_SENT),
					new ExplorerTreeNode("Drafts", "mailfolder_sent",
							"mailaccount", ImageProvider.MAIL_FOLDER_DRAFTS),
					new ExplorerTreeNode("Trash", "mailfolder_sent",
							"mailaccount", ImageProvider.MAIL_FOLDER_TRASH_EMPTY),
					new ExplorerTreeNode("Spam", "mailfolder_spam",
							"mailaccount", ImageProvider.MAIL_FOLDER_SPAM) };
		}
		return data;
	}
}
