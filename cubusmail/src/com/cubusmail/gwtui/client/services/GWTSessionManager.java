/* GWTSessionManager.java

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

import java.util.List;

import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.GWTMailbox;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactFolder;
import com.cubusmail.gwtui.domain.ContactFolderType;
import com.cubusmail.gwtui.domain.Preferences;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Client side session data.
 * 
 * @author Juergen Schlierf
 */
public class GWTSessionManager {

	private static GWTSessionManager instance;

	private GWTMailbox mailbox;
	private GWTMailFolder mailFolder;
	private GWTMailFolder currentMailFolder;
	private ContactFolder currentContactFolder;
	private GWTMessage currentMessage;

	private List<ContactFolder> contactFolderList;
	private Contact currentContact;

	/**
	 * 
	 */
	private GWTSessionManager() {

	}

	public static GWTSessionManager get() {

		if ( instance == null ) {
			instance = new GWTSessionManager();
		}

		return instance;
	}

	/**
	 * @return Returns the mailbox.
	 */
	public GWTMailbox getMailbox() {

		return this.mailbox;
	}

	/**
	 * @param mailbox
	 *            The mailbox to set.
	 */
	public void setMailbox( GWTMailbox mailBox ) {

		this.mailbox = mailBox;
	}

	/**
	 * @return Returns the mailFolder.
	 */
	public GWTMailFolder getTopMailFolder() {

		return this.mailFolder;
	}

	/**
	 * @param mailFolder
	 *            The mailFolder to set.
	 */
	public void setTopMailFolder( GWTMailFolder mailFolder ) {

		this.mailFolder = mailFolder;
	}

	/**
	 * @return Returns the preferences.
	 */
	public Preferences getPreferences() {

		return this.mailbox.getUserAccount().getPreferences();
	}

	/**
	 * @param preferences
	 *            The preferences to set.
	 */
	public void setPreferences( Preferences preferences ) {

		this.mailbox.getUserAccount().setPreferences( preferences );
	}

	/**
	 * @return Returns the currentMailFolder.
	 */
	public GWTMailFolder getCurrentMailFolder() {

		return this.currentMailFolder;
	}

	/**
	 * @param currentMailFolder
	 *            The currentMailFolder to set.
	 */
	public void setCurrentMailFolder( GWTMailFolder currentMailFolder ) {

		this.currentMailFolder = currentMailFolder;
	}

	/**
	 * @return Returns the userAccount.
	 */
	public UserAccount getUserAccount() {

		return this.mailbox.getUserAccount();
	}

	/**
	 * @param userAccount
	 *            The userAccount to set.
	 */
	public void setUserAccount( UserAccount userAccount ) {

		this.mailbox.setUserAccount( userAccount );
	}

	/**
	 * @return Returns the currentContactFolder.
	 */
	public ContactFolder getCurrentContactFolder() {

		return this.currentContactFolder;
	}

	/**
	 * @param currentContactFolder
	 *            The currentContactFolder to set.
	 */
	public void setCurrentContactFolder( ContactFolder currentContactFolder ) {

		this.currentContactFolder = currentContactFolder;
	}

	/**
	 * @return Returns the contactFolderList.
	 */
	public List<ContactFolder> getContactFolderList() {

		return this.contactFolderList;
	}

	public ContactFolder getStandardContactFolder() {

		if ( getContactFolderList() != null ) {
			for (ContactFolder folder : getContactFolderList()) {
				if ( ContactFolderType.STANDARD == folder.getFolderType() ) {
					return folder;
				}
			}
		}
		return null;
	}

	/**
	 * @param contactFolderList
	 *            The contactFolderList to set.
	 */
	public void setContactFolderList( List<ContactFolder> contactFolderList ) {

		this.contactFolderList = contactFolderList;
	}

	/**
	 * @return Returns the currentContact.
	 */
	public Contact getCurrentContact() {

		return this.currentContact;
	}

	/**
	 * @param currentContact
	 *            The currentContact to set.
	 */
	public void setCurrentContact( Contact currentContact ) {

		this.currentContact = currentContact;
	}

	/**
	 * @param currentMessage
	 *            The currentMessage to set.
	 */
	public void setCurrentMessage( GWTMessage currentMessage ) {

		this.currentMessage = currentMessage;
	}

	/**
	 * @return Returns the currentMessage.
	 */
	public GWTMessage getCurrentMessage() {

		return this.currentMessage;
	}
}
