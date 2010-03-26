/* ContactFolder.java

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Address folder POJO
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class AddressFolder implements Serializable {

	private Long id;

	private String folderName;

	private AddressFolderType folderType;

	private List<Address> addressList;

	private UserAccount userAccount;

	public AddressFolder() {

		this( AddressFolderType.USER );
	}

	public AddressFolder( AddressFolderType type ) {

		this.folderType = type;
	}

	/**
	 * @param address
	 */
	public void addAddress( Address address ) {

		if ( this.addressList == null ) {
			this.addressList = new ArrayList<Address>();
		}
		address.setAddressFolder( this );
		this.addressList.add( address );
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {

		return this.id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId( Long id ) {

		this.id = id;
	}

	/**
	 * @return Returns the folderName.
	 */
	public String getFolderName() {

		return this.folderName;
	}

	/**
	 * @param folderName
	 *            The folderName to set.
	 */
	public void setFolderName( String folderName ) {

		this.folderName = folderName;
	}

	/**
	 * @return Returns the contactList.
	 */
	public List<Address> getAddressList() {

		return this.addressList;
	}

	/**
	 * @param addressList
	 *            The contactList to set.
	 */
	public void setAddressList( List<Address> addressList ) {

		this.addressList = addressList;
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

	/**
	 * @return Returns the folderType.
	 */
	public AddressFolderType getFolderType() {

		return this.folderType;
	}

	/**
	 * @param folderType
	 *            The folderType to set.
	 */
	public void setFolderType( AddressFolderType folderType ) {

		this.folderType = folderType;
	}
}
