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
package com.cubusmail.client.util;

import java.util.List;

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.AddressFolderType;
import com.cubusmail.common.model.GWTMailFolder;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.GWTMessage;
import com.cubusmail.common.model.Preferences;
import com.cubusmail.common.model.UserAccount;

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
	private GWTMessage currentMessage;

	private List<AddressFolder> addressFolderList;
	private Address currentAddress;
	private AddressFolder currentAddressFolder;
	private String currentBeginChars;

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
	public AddressFolder getCurrentAddressFolder() {

		return this.currentAddressFolder;
	}

	/**
	 * @param currentContactFolder
	 *            The currentContactFolder to set.
	 */
	public void setCurrentAddressFolder( AddressFolder currentContactFolder ) {

		this.currentAddressFolder = currentContactFolder;
	}

	/**
	 * @return Returns the addressFolderList.
	 */
	public List<AddressFolder> getAddressFolderList() {

		return this.addressFolderList;
	}

	public AddressFolder getStandardContactFolder() {

		if ( getAddressFolderList() != null ) {
			for (AddressFolder folder : getAddressFolderList()) {
				if ( AddressFolderType.STANDARD == folder.getType() ) {
					return folder;
				}
			}
		}
		return null;
	}

	/**
	 * @param addressFolderList
	 *            The addressFolderList to set.
	 */
	public void setAddressFolderList( List<AddressFolder> contactFolderList ) {

		this.addressFolderList = contactFolderList;
	}

	/**
	 * @return Returns the currentAddress.
	 */
	public Address getCurrentAddress() {

		return this.currentAddress;
	}

	/**
	 * @param currentAddress
	 *            The currentAddress to set.
	 */
	public void setCurrentAddress( Address currentContact ) {

		this.currentAddress = currentContact;
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

	/**
	 * @return Returns the currentBeginChars.
	 */
	public String getCurrentBeginChars() {

		return currentBeginChars;
	}

	/**
	 * @param currentBeginChars
	 *            The currentBeginChars to set.
	 */
	public void setCurrentBeginChars( String currentBeginChars ) {

		this.currentBeginChars = currentBeginChars;
	}
}
