/* ContactFolder.java

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
package com.cubusmail.gwtui.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sf.hibernate4gwt.pojo.java5.LazyPojo;

/**
 * Contact folder POJO
 * 
 * @author Jürgen Schlierf
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "contactfolders")
public class ContactFolder extends LazyPojo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String folderName;

	private ContactFolderType folderType;

	@OneToMany(mappedBy = "contactFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Contact> contactList;

	@ManyToOne
	@JoinColumn(name = "users_fk")
	private UserAccount userAccount;

	public ContactFolder() {

		this( ContactFolderType.USER );
	}

	public ContactFolder( ContactFolderType type ) {

		this.folderType = type;
	}
	
	/**
	 * @param contact
	 */
	public void addContact( Contact contact ) {

		if ( this.contactList == null ) {
			this.contactList = new ArrayList<Contact>();
		}
		contact.setContactFolder( this );
		this.contactList.add( contact );
	}


	/**
	 * @return Returns the id.
	 */
	public Long getId() {

		return this.id;
	}

	/**
	 * @param id The id to set.
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
	 * @param folderName The folderName to set.
	 */
	public void setFolderName( String folderName ) {

		this.folderName = folderName;
	}

	/**
	 * @return Returns the contactList.
	 */
	public List<Contact> getContactList() {

		return this.contactList;
	}

	/**
	 * @param contactList The contactList to set.
	 */
	public void setContactList( List<Contact> contactList ) {

		this.contactList = contactList;
	}

	/**
	 * @return Returns the userAccount.
	 */
	public UserAccount getUserAccount() {

		return this.userAccount;
	}

	/**
	 * @param userAccount The userAccount to set.
	 */
	public void setUserAccount( UserAccount userAccount ) {

		this.userAccount = userAccount;
	}

	/**
	 * @return Returns the folderType.
	 */
	public ContactFolderType getFolderType() {

		return this.folderType;
	}

	/**
	 * @param folderType The folderType to set.
	 */
	public void setFolderType( ContactFolderType folderType ) {

		this.folderType = folderType;
	}
}
