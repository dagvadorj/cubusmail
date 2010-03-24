/* UserAccount.java

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
import java.util.Date;
import java.util.List;

/**
 * UserAccount POJO.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
//@Entity
//@Table(name = "users")
public class UserAccount implements Serializable {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@Column(name = "username", nullable = false, unique = true)
	private String username;

//	@Column(name = "created")
//	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

//	@Column(name = "lastLogin")
	private Date lastLogin;

//	@Transient
	private Preferences preferences;


//	@OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Identity> identities;

//	@OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ContactFolder> contactFolders;

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
	 * @return Returns the username.
	 */
	public String getUsername() {

		return this.username;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername( String username ) {

		this.username = username;
	}

	/**
	 * @return Returns the created.
	 */
	public Date getCreated() {

		return this.created;
	}

	/**
	 * @param created The created to set.
	 */
	public void setCreated( Date created ) {

		this.created = created;
	}

	/**
	 * @return Returns the lastLogin.
	 */
	public Date getLastLogin() {

		return this.lastLogin;
	}

	/**
	 * @param lastLogin The lastLogin to set.
	 */
	public void setLastLogin( Date lastLogin ) {

		this.lastLogin = lastLogin;
	}

	/**
	 * @return Returns the preferences.
	 */
	public Preferences getPreferences() {

		return this.preferences;
	}

	/**
	 * @param preferences The preferences to set.
	 */
	public void setPreferences( Preferences preferences ) {

		this.preferences = preferences;
	}

	/**
	 * @return Returns the identities.
	 */
	public List<Identity> getIdentities() {

		return this.identities;
	}

	/**
	 * @return
	 */
	public Identity getStandardIdentity() {

		for ( Identity identity : this.identities ) {
			if ( identity.isStandard() ) {
				return identity;
			}
		}

		// should never happen
		return null;
	}

	/**
	 * @param identities The identities to set.
	 */
	public void setIdentities( List<Identity> identities ) {

		this.identities = identities;
	}

	public void addIdentity( Identity identity ) {

		if ( this.identities == null ) {
			this.identities = new ArrayList<Identity>();
		}
		identity.setUserAccount( this );
		identity.setUserAccountId( this.id );
		this.identities.add( identity );
	}

	/**
	 * @param identity
	 */
	public void removeIdentity( Identity identity ) {

		if ( this.identities != null ) {
			this.identities.remove( identity );
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public Identity getIdentityById( Long id ) {

		for ( Identity identity : this.identities ) {
			if ( id.equals( identity.getId() ) ) {
				return identity;
			}
		}

		return null;
	}

	/**
	 * @return Returns the contactFolders.
	 */
	public List<ContactFolder> getContactFolders() {

		return this.contactFolders;
	}

	/**
	 * @param contactFolders The contactFolders to set.
	 */
	public void setContactFolders( List<ContactFolder> contactFolders ) {

		this.contactFolders = contactFolders;
	}

	public void addContactFolder( ContactFolder folder ) {

		if ( this.contactFolders == null ) {
			this.contactFolders = new ArrayList<ContactFolder>();
		}
		folder.setUserAccount( this );
		this.contactFolders.add( folder );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public UserAccount clone() {

		UserAccount clone = new UserAccount();
		clone.created = this.created;
		clone.id = this.id;
		if ( this.identities != null ) {
			for ( Identity identity : this.identities ) {
				clone.addIdentity( identity.clone() );
			}
		}
		clone.lastLogin = this.lastLogin;
		clone.preferences = this.preferences.clone();
		clone.username = this.username;

		return clone;
	}
}
