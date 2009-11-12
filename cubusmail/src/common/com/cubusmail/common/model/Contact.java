/* Contact.java

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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.hibernate4gwt.pojo.java5.LazyPojo;

/**
 * Contact POJO.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "contacts")
public class Contact extends LazyPojo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// personal
	private String firstName;
	private String secondName;
	private String lastName;
	private String nickname;
	private String company;
	private String position;

	@Transient
	private String displayName;

	// internet
	private String email;
	private String email2;
	private String im;
	private String website;

	// phone numbers
	private String privatePhone;
	private String businessPhone;
	private String mobilePhone;
	private String pager;
	private String privateFax;
	private String businessFax;

	// addresses
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "contact")
	private ContactAddress privateAddress;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "contact")
	private ContactAddress businessAddress;

	// custom fields
	private String custom1;
	private String custom2;
	private String custom3;
	private String custom4;

	private String notice;

	@ManyToOne
	@JoinColumn(name = "contactfolders_fk")
	private ContactFolder contactFolder;

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
	 * @return Returns the firstName.
	 */
	public String getFirstName() {

		return this.firstName;
	}

	/**
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName( String firstName ) {

		this.firstName = firstName;
	}

	/**
	 * @return Returns the secondName.
	 */
	public String getSecondName() {

		return this.secondName;
	}

	/**
	 * @param secondName
	 *            The secondName to set.
	 */
	public void setSecondName( String secondName ) {

		this.secondName = secondName;
	}

	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {

		return this.lastName;
	}

	/**
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName( String lastName ) {

		this.lastName = lastName;
	}

	/**
	 * @return Returns the nickname.
	 */
	public String getNickname() {

		return this.nickname;
	}

	/**
	 * @param nickname
	 *            The nickname to set.
	 */
	public void setNickname( String nickname ) {

		this.nickname = nickname;
	}

	/**
	 * @return Returns the company.
	 */
	public String getCompany() {

		return this.company;
	}

	/**
	 * @param company
	 *            The company to set.
	 */
	public void setCompany( String company ) {

		this.company = company;
	}

	/**
	 * @return Returns the position.
	 */
	public String getPosition() {

		return this.position;
	}

	/**
	 * @param position
	 *            The position to set.
	 */
	public void setPosition( String position ) {

		this.position = position;
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
	 * @return Returns the email2.
	 */
	public String getEmail2() {

		return this.email2;
	}

	/**
	 * @param email2
	 *            The email2 to set.
	 */
	public void setEmail2( String email2 ) {

		this.email2 = email2;
	}

	/**
	 * @return Returns the im.
	 */
	public String getIm() {

		return this.im;
	}

	/**
	 * @param im
	 *            The im to set.
	 */
	public void setIm( String im ) {

		this.im = im;
	}

	/**
	 * @return Returns the website.
	 */
	public String getWebsite() {

		return this.website;
	}

	/**
	 * @param website
	 *            The website to set.
	 */
	public void setWebsite( String website ) {

		this.website = website;
	}

	/**
	 * @return Returns the privatePhone.
	 */
	public String getPrivatePhone() {

		return this.privatePhone;
	}

	/**
	 * @param privatePhone
	 *            The privatePhone to set.
	 */
	public void setPrivatePhone( String privatePhone ) {

		this.privatePhone = privatePhone;
	}

	/**
	 * @return Returns the businessPhone.
	 */
	public String getBusinessPhone() {

		return this.businessPhone;
	}

	/**
	 * @param businessPhone
	 *            The businessPhone to set.
	 */
	public void setBusinessPhone( String businessPhone ) {

		this.businessPhone = businessPhone;
	}

	/**
	 * @return Returns the mobilePhone.
	 */
	public String getMobilePhone() {

		return this.mobilePhone;
	}

	/**
	 * @param mobilePhone
	 *            The mobilePhone to set.
	 */
	public void setMobilePhone( String mobilePhone ) {

		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return Returns the privateFax.
	 */
	public String getPrivateFax() {

		return this.privateFax;
	}

	/**
	 * @param privateFax
	 *            The privateFax to set.
	 */
	public void setPrivateFax( String privateFax ) {

		this.privateFax = privateFax;
	}

	/**
	 * @return Returns the businessFax.
	 */
	public String getBusinessFax() {

		return this.businessFax;
	}

	/**
	 * @param businessFax
	 *            The businessFax to set.
	 */
	public void setBusinessFax( String businessFax ) {

		this.businessFax = businessFax;
	}

	/**
	 * @return Returns the pager.
	 */
	public String getPager() {

		return this.pager;
	}

	/**
	 * @param pager
	 *            The pager to set.
	 */
	public void setPager( String pager ) {

		this.pager = pager;
	}

	/**
	 * @return Returns the privateAddress.
	 */
	public ContactAddress getPrivateAddress() {

		return this.privateAddress;
	}

	/**
	 * @param privateAddress
	 *            The privateAddress to set.
	 */
	public void setPrivateAddress( ContactAddress privateAddress ) {

		this.privateAddress = privateAddress;
	}

	/**
	 * @return Returns the businessAddress.
	 */
	public ContactAddress getBusinessAddress() {

		return this.businessAddress;
	}

	/**
	 * @param businessAddress
	 *            The businessAddress to set.
	 */
	public void setBusinessAddress( ContactAddress businessAddress ) {

		this.businessAddress = businessAddress;
	}

	/**
	 * @return Returns the custom1.
	 */
	public String getCustom1() {

		return this.custom1;
	}

	/**
	 * @param custom1
	 *            The custom1 to set.
	 */
	public void setCustom1( String custom1 ) {

		this.custom1 = custom1;
	}

	/**
	 * @return Returns the custom2.
	 */
	public String getCustom2() {

		return this.custom2;
	}

	/**
	 * @param custom2
	 *            The custom2 to set.
	 */
	public void setCustom2( String custom2 ) {

		this.custom2 = custom2;
	}

	/**
	 * @return Returns the custom3.
	 */
	public String getCustom3() {

		return this.custom3;
	}

	/**
	 * @param custom3
	 *            The custom3 to set.
	 */
	public void setCustom3( String custom3 ) {

		this.custom3 = custom3;
	}

	/**
	 * @return Returns the custom4.
	 */
	public String getCustom4() {

		return this.custom4;
	}

	/**
	 * @param custom4
	 *            The custom4 to set.
	 */
	public void setCustom4( String custom4 ) {

		this.custom4 = custom4;
	}

	/**
	 * @return Returns the notice.
	 */
	public String getNotice() {

		return this.notice;
	}

	/**
	 * @param notice
	 *            The notice to set.
	 */
	public void setNotice( String notice ) {

		this.notice = notice;
	}

	/**
	 * @return Returns the contactFolder.
	 */
	public ContactFolder getContactFolder() {

		return this.contactFolder;
	}

	/**
	 * @param contactFolder
	 *            The contactFolder to set.
	 */
	public void setContactFolder( ContactFolder contactFolder ) {

		this.contactFolder = contactFolder;
	}

	public Contact clone() {

		Contact clone = new Contact();
		clone.businessAddress = this.businessAddress.clone();
		clone.businessFax = this.businessFax;
		clone.businessPhone = this.businessPhone;
		clone.company = this.company;
		clone.contactFolder = this.contactFolder;
		clone.custom1 = this.custom1;
		clone.custom2 = this.custom2;
		clone.custom3 = this.custom3;
		clone.custom4 = this.custom4;
		clone.email = this.email;
		clone.email2 = this.email2;
		clone.firstName = this.firstName;
		clone.id = this.id;
		clone.im = this.im;
		clone.lastName = this.lastName;
		clone.mobilePhone = this.mobilePhone;
		clone.nickname = this.nickname;
		clone.notice = this.notice;
		clone.pager = this.pager;
		clone.position = this.position;
		clone.privateAddress = this.privateAddress.clone();
		clone.privateFax = this.privateFax;
		clone.privatePhone = this.privatePhone;
		clone.secondName = this.secondName;
		clone.website = this.website;
		clone.displayName = this.displayName;

		return clone;
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
}
