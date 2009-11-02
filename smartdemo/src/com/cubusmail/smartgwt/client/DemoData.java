/**
 * 
 */
package com.cubusmail.smartgwt.client;

import com.cubusmail.smartgwt.client.addressbook.AddressRecord;

/**
 * @author schlierf
 * 
 */
public class DemoData {

	private static ExplorerTreeNode[] mailTreeData;
	private static ExplorerTreeNode[] addressBookTreeData;
	private static AddressRecord[] addressListData;

	public static ExplorerTreeNode[] getMailTreeData() {
		if (mailTreeData == null) {
			mailTreeData = new ExplorerTreeNode[] {
					new ExplorerTreeNode("Inbox", "mailfolder_inbox",
							"mailaccount", ImageProvider.MAIL_FOLDER_INBOX),
					new ExplorerTreeNode("Sent", "mailfolder_sent",
							"mailaccount", ImageProvider.MAIL_FOLDER_SENT),
					new ExplorerTreeNode("Drafts", "mailfolder_sent",
							"mailaccount", ImageProvider.MAIL_FOLDER_DRAFTS),
					new ExplorerTreeNode("Trash", "mailfolder_sent",
							"mailaccount",
							ImageProvider.MAIL_FOLDER_TRASH_EMPTY),
					new ExplorerTreeNode("Spam", "mailfolder_spam",
							"mailaccount", ImageProvider.MAIL_FOLDER_SPAM) };
		}
		return mailTreeData;
	}

	public static ExplorerTreeNode[] getAddressBookTreeData() {
		if (addressBookTreeData == null) {
			addressBookTreeData = new ExplorerTreeNode[] {
					new ExplorerTreeNode("Contacts",
							"addressbookfolder_contacts", "",
							ImageProvider.CONTACT_FOLDER),
					new ExplorerTreeNode("Recipients",
							"addressbookfolder_recipients", "",
							ImageProvider.CONTACT_FOLDER),
					new ExplorerTreeNode("Special",
							"addressbookfolder_special", "",
							ImageProvider.CONTACT_FOLDER) };
		}
		return addressBookTreeData;
	}

	public static AddressRecord[] getAddressListData() {
		if (addressListData == null) {
			addressListData = new AddressRecord[] {
					new AddressRecord("White, Jack"),
					new AddressRecord("Rabbit, Black"),
					new AddressRecord("Depp, Jonny"),
					new AddressRecord("Croft, Lara"),
					new AddressRecord("Idea, No") };
		}
		return addressListData;
	}

}
