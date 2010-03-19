/* ContactAddress.java

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
 * Contact address POJO.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
//@Entity
//@Table(name = "addresses")
public class ContactAddress implements Serializable {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String street;
	private String street2;
	private String zipcode;
	private String city;
	private String state;
	private String country;

//	@OneToOne
//	@JoinColumn(name = "contact_fk", nullable = false)
	private Contact contact;

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
	 * @return Returns the street.
	 */
	public String getStreet() {

		return this.street;
	}

	/**
	 * @param street
	 *            The street to set.
	 */
	public void setStreet( String street ) {

		this.street = street;
	}

	/**
	 * @return Returns the street2.
	 */
	public String getStreet2() {

		return this.street2;
	}

	/**
	 * @param street2
	 *            The street2 to set.
	 */
	public void setStreet2( String street2 ) {

		this.street2 = street2;
	}

	/**
	 * @return Returns the city.
	 */
	public String getCity() {

		return this.city;
	}

	/**
	 * @param city
	 *            The city to set.
	 */
	public void setCity( String city ) {

		this.city = city;
	}

	/**
	 * @return Returns the state.
	 */
	public String getState() {

		return this.state;
	}

	/**
	 * @param state
	 *            The state to set.
	 */
	public void setState( String state ) {

		this.state = state;
	}

	/**
	 * @return Returns the zipcode.
	 */
	public String getZipcode() {

		return this.zipcode;
	}

	/**
	 * @param zipcode
	 *            The zipcode to set.
	 */
	public void setZipcode( String zipcode ) {

		this.zipcode = zipcode;
	}

	/**
	 * @return Returns the country.
	 */
	public String getCountry() {

		return this.country;
	}

	/**
	 * @param country
	 *            The country to set.
	 */
	public void setCountry( String country ) {

		this.country = country;
	}

	/**
	 * @return Returns the contact.
	 */
	public Contact getContact() {

		return this.contact;
	}

	/**
	 * @param contact
	 *            The contact to set.
	 */
	public void setContact( Contact contact ) {

		this.contact = contact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public ContactAddress clone() {

		ContactAddress clone = new ContactAddress();
		clone.city = this.city;
		clone.contact = this.contact;
		clone.country = this.country;
		clone.id = this.id;
		clone.state = this.state;
		clone.street = this.street;
		clone.street2 = this.street2;
		return clone;
	}
}
