/* GWTMailbox.java

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
package com.cubusmail.gwtui.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Representing user.
 * 
 * @author Juergen Schlierf
 */
public class GWTMailbox implements IGWTFolder, IsSerializable {

	private static final long serialVersionUID = -4747377005134237284L;

	private String emailAddress;
	private String fullName;

	private UserAccount userAccount;

	private GWTMailFolder[] mailFolders = GWTMailFolder.EMPTY_FOLDER_ARRAY;

	private boolean loggedIn;

	public String getFullName() {

		return fullName;
	}

	public void setFullName( String fullName ) {

		this.fullName = fullName;
	}

	public String getEmailAddress() {

		return this.emailAddress;
	}

	/**
	 * @return Returns the loggedIn.
	 */
	public boolean isLoggedIn() {

		return this.loggedIn;
	}

	/**
	 * @param loggedIn
	 *            The loggedIn to set.
	 */
	public void setLoggedIn( boolean loggedIn ) {

		this.loggedIn = loggedIn;
	}

	/**
	 * @param emailAddress
	 *            The emailAddress to set.
	 */
	public void setEmailAddress( String emailAddress ) {

		this.emailAddress = emailAddress;
	}

	/**
	 * @return Returns the mailFolders.
	 */
	public GWTMailFolder[] getMailFolders() {

		return this.mailFolders;
	}

	/**
	 * @param mailFolders
	 *            The mailFolders to set.
	 */
	public void setMailFolders( GWTMailFolder[] mailFolders ) {

		if ( mailFolders != null ) {
			for (GWTMailFolder folder : mailFolders) {
				folder.setParent( this );
			}
		}
		this.mailFolders = mailFolders;
	}

	/**
	 * @return Returns the userAccount.
	 */
	public UserAccount getUserAccount() {

		return this.userAccount;
	}

	/**
	 * @param userAccount
	 *            The userAccount to set.
	 */
	public void setUserAccount( UserAccount userAccount ) {

		this.userAccount = userAccount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.model.IGWTFolder#getId()
	 */
	public String getId() {

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.model.IGWTFolder#getParent()
	 */
	public IGWTFolder getParent() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.model.IGWTFolder#isMoveSupported()
	 */
	public boolean isMoveSupported() {

		return false;
	}

	/**
	 * @return
	 */
	public IGWTFolder getTrashFolder() {

		if ( this.mailFolders != null ) {
			for (IGWTFolder folder : this.mailFolders) {
				if ( folder instanceof GWTMailFolder ) {
					if ( ((GWTMailFolder) folder).isTrash() ) {
						return folder;
					}
				}
			}
		}

		return null;
	}

	public boolean isCreateSubfolderSupported() {

		return true;
	}

	public boolean isDeleteSupported() {

		return false;
	}

	public boolean isEmptySupported() {

		return false;
	}

	public boolean isRenameSupported() {

		return false;
	}
}
