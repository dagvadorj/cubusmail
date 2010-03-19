/* Identity.java

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

/**
 * Identity POJO.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
//@Entity
//@Table(name = "identities")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Identity implements Serializable {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String displayName;
	private String email;
	private String organisation;
	private String replyTo;
	private String bcc;
	private String signature;
	private boolean htmlSignature;
	private boolean standard;

//	@Transient
	private String internetAddress;

//	@Transient
	private String escapedInternetAddress;

//	@ManyToOne
//	@JoinColumn(name = "users_fk")
	private UserAccount userAccount;

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
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {

		return this.displayName;
	}

	/**
	 * @param displayName
	 *            The displayName to set.
	 */
	public void setDisplayName( String displayName ) {

		this.displayName = displayName;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {

		return this.email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail( String email ) {

		this.email = email;
	}

	/**
	 * @return Returns the organisation.
	 */
	public String getOrganisation() {

		return this.organisation;
	}

	/**
	 * @param organisation
	 *            The organisation to set.
	 */
	public void setOrganisation( String organisation ) {

		this.organisation = organisation;
	}

	/**
	 * @return Returns the replyTo.
	 */
	public String getReplyTo() {

		return this.replyTo;
	}

	/**
	 * @param replyTo
	 *            The replyTo to set.
	 */
	public void setReplyTo( String replyTo ) {

		this.replyTo = replyTo;
	}

	/**
	 * @return Returns the bcc.
	 */
	public String getBcc() {

		return this.bcc;
	}

	/**
	 * @param bcc
	 *            The bcc to set.
	 */
	public void setBcc( String bcc ) {

		this.bcc = bcc;
	}

	/**
	 * @return Returns the signature.
	 */
	public String getSignature() {

		return this.signature;
	}

	/**
	 * @param signature
	 *            The signature to set.
	 */
	public void setSignature( String signature ) {

		this.signature = signature;
	}

	/**
	 * @return Returns the htmlSignature.
	 */
	public boolean isHtmlSignature() {

		return this.htmlSignature;
	}

	/**
	 * @param htmlSignature
	 *            The htmlSignature to set.
	 */
	public void setHtmlSignature( boolean htmlSignature ) {

		this.htmlSignature = htmlSignature;
	}

	/**
	 * @return Returns the standard.
	 */
	public boolean isStandard() {

		return this.standard;
	}

	/**
	 * @param standard
	 *            The standard to set.
	 */
	public void setStandard( boolean standard ) {

		this.standard = standard;
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
	 * GWT doesn't support native cloning.
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Identity clone() {

		Identity cloned = new Identity();
		cloned.bcc = this.bcc;
		cloned.displayName = this.displayName;
		cloned.email = this.email;
		cloned.htmlSignature = this.htmlSignature;
		cloned.id = this.id;
		cloned.organisation = this.organisation;
		cloned.replyTo = this.replyTo;
		cloned.signature = this.signature;
		cloned.standard = this.standard;

		return cloned;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {

		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Identity other = (Identity) obj;
		if ( id == null ) {
			if ( other.id != null )
				return false;
		}
		else if ( !id.equals( other.id ) )
			return false;
		return true;
	}

	/**
	 * @return Returns the internetAddress.
	 */
	public String getInternetAddress() {

		return this.internetAddress;
	}

	/**
	 * @param internetAddress
	 *            The internetAddress to set.
	 */
	public void setInternetAddress( String internetAddress ) {

		this.internetAddress = internetAddress;
	}

	/**
	 * @return Returns the escapedInternetAddress.
	 */
	public String getEscapedInternetAddress() {

		return this.escapedInternetAddress;
	}

	/**
	 * @param escapedInternetAddress
	 *            The escapedInternetAddress to set.
	 */
	public void setEscapedInternetAddress( String escapedInternetAddress ) {

		this.escapedInternetAddress = escapedInternetAddress;
	}
}
