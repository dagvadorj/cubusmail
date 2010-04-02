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
import java.util.Date;

/**
 * Address POJO.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class Address implements Serializable {

	private Long id;

	// personal
	private String firstName;
	private String middleName;
	private String lastName;
	private String title;
	private Date birthDate;
	private String company;
	private String position;
	private String department;

	// transient
	private String displayName;

	// internet
	private String email;
	private String email2;
	private String email3;
	private String email4;
	private String email5;
	private String im;
	private String url;

	// phone numbers
	private String privatePhone;
	private String workPhone;
	private String privateMobile;
	private String workMobile;
	private String privateFax;
	private String workFax;
	private String pager;

	// addresses
	private String privateStreet;
	private String privateZipcode;
	private String privateCity;
	private String privateState;
	private String privateCountry;

	private String workStreet;
	private String workZipcode;
	private String workCity;
	private String workState;
	private String workCountry;

	private String notes;

	private AddressFolder addressFolder;

	public void setAddressFolder( AddressFolder addressFolder ) {

		this.addressFolder = addressFolder;
	}

	public AddressFolder getAddressFolder() {

		return addressFolder;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {

		return id;
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

		return firstName;
	}

	/**
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName( String firstName ) {

		this.firstName = firstName;
	}

	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {

		return middleName;
	}

	/**
	 * @param middleName
	 *            The middleName to set.
	 */
	public void setMiddleName( String middleName ) {

		this.middleName = middleName;
	}

	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {

		return lastName;
	}

	/**
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName( String lastName ) {

		this.lastName = lastName;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {

		return title;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle( String title ) {

		this.title = title;
	}

	/**
	 * @return Returns the birthDate.
	 */
	public Date getBirthDate() {

		return birthDate;
	}

	/**
	 * @param birthDate
	 *            The birthDate to set.
	 */
	public void setBirthDate( Date birthDate ) {

		this.birthDate = birthDate;
	}

	/**
	 * @return Returns the company.
	 */
	public String getCompany() {

		return company;
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

		return position;
	}

	/**
	 * @param position
	 *            The position to set.
	 */
	public void setPosition( String position ) {

		this.position = position;
	}

	/**
	 * @return Returns the department.
	 */
	public String getDepartment() {

		return department;
	}

	/**
	 * @param department
	 *            The department to set.
	 */
	public void setDepartment( String department ) {

		this.department = department;
	}

	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {

		return displayName;
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

		return email;
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

		return email2;
	}

	/**
	 * @param email2
	 *            The email2 to set.
	 */
	public void setEmail2( String email2 ) {

		this.email2 = email2;
	}

	/**
	 * @return Returns the email3.
	 */
	public String getEmail3() {

		return email3;
	}

	/**
	 * @param email3
	 *            The email3 to set.
	 */
	public void setEmail3( String email3 ) {

		this.email3 = email3;
	}

	/**
	 * @return Returns the email4.
	 */
	public String getEmail4() {

		return email4;
	}

	/**
	 * @param email4
	 *            The email4 to set.
	 */
	public void setEmail4( String email4 ) {

		this.email4 = email4;
	}

	/**
	 * @return Returns the email5.
	 */
	public String getEmail5() {

		return email5;
	}

	/**
	 * @param email5
	 *            The email5 to set.
	 */
	public void setEmail5( String email5 ) {

		this.email5 = email5;
	}

	/**
	 * @return Returns the im.
	 */
	public String getIm() {

		return im;
	}

	/**
	 * @param im
	 *            The im to set.
	 */
	public void setIm( String im ) {

		this.im = im;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {

		return url;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl( String url ) {

		this.url = url;
	}

	/**
	 * @return Returns the privatePhone.
	 */
	public String getPrivatePhone() {

		return privatePhone;
	}

	/**
	 * @param privatePhone
	 *            The privatePhone to set.
	 */
	public void setPrivatePhone( String privatePhone ) {

		this.privatePhone = privatePhone;
	}

	/**
	 * @return Returns the workPhone.
	 */
	public String getWorkPhone() {

		return workPhone;
	}

	/**
	 * @param workPhone
	 *            The workPhone to set.
	 */
	public void setWorkPhone( String workPhone ) {

		this.workPhone = workPhone;
	}

	/**
	 * @return Returns the privateMobile.
	 */
	public String getPrivateMobile() {

		return privateMobile;
	}

	/**
	 * @param privateMobile
	 *            The privateMobile to set.
	 */
	public void setPrivateMobile( String privateMobile ) {

		this.privateMobile = privateMobile;
	}

	/**
	 * @return Returns the workMobile.
	 */
	public String getWorkMobile() {

		return workMobile;
	}

	/**
	 * @param workMobile
	 *            The workMobile to set.
	 */
	public void setWorkMobile( String workMobile ) {

		this.workMobile = workMobile;
	}

	/**
	 * @return Returns the privateFax.
	 */
	public String getPrivateFax() {

		return privateFax;
	}

	/**
	 * @param privateFax
	 *            The privateFax to set.
	 */
	public void setPrivateFax( String privateFax ) {

		this.privateFax = privateFax;
	}

	/**
	 * @return Returns the workFax.
	 */
	public String getWorkFax() {

		return workFax;
	}

	/**
	 * @param workFax
	 *            The workFax to set.
	 */
	public void setWorkFax( String workFax ) {

		this.workFax = workFax;
	}

	/**
	 * @return Returns the pager.
	 */
	public String getPager() {

		return pager;
	}

	/**
	 * @param pager
	 *            The pager to set.
	 */
	public void setPager( String pager ) {

		this.pager = pager;
	}

	/**
	 * @return Returns the privateStreet.
	 */
	public String getPrivateStreet() {

		return privateStreet;
	}

	/**
	 * @param privateStreet
	 *            The privateStreet to set.
	 */
	public void setPrivateStreet( String privateStreet ) {

		this.privateStreet = privateStreet;
	}

	/**
	 * @return Returns the privateZipcode.
	 */
	public String getPrivateZipcode() {

		return privateZipcode;
	}

	/**
	 * @param privateZipcode
	 *            The privateZipcode to set.
	 */
	public void setPrivateZipcode( String privateZipcode ) {

		this.privateZipcode = privateZipcode;
	}

	/**
	 * @return Returns the privateCity.
	 */
	public String getPrivateCity() {

		return privateCity;
	}

	/**
	 * @param privateCity
	 *            The privateCity to set.
	 */
	public void setPrivateCity( String privateCity ) {

		this.privateCity = privateCity;
	}

	/**
	 * @return Returns the privateState.
	 */
	public String getPrivateState() {

		return privateState;
	}

	/**
	 * @param privateState
	 *            The privateState to set.
	 */
	public void setPrivateState( String privateState ) {

		this.privateState = privateState;
	}

	/**
	 * @return Returns the privateCountry.
	 */
	public String getPrivateCountry() {

		return privateCountry;
	}

	/**
	 * @param privateCountry
	 *            The privateCountry to set.
	 */
	public void setPrivateCountry( String privateCountry ) {

		this.privateCountry = privateCountry;
	}

	/**
	 * @return Returns the workStreet.
	 */
	public String getWorkStreet() {

		return workStreet;
	}

	/**
	 * @param workStreet
	 *            The workStreet to set.
	 */
	public void setWorkStreet( String workStreet ) {

		this.workStreet = workStreet;
	}

	/**
	 * @return Returns the workZipcode.
	 */
	public String getWorkZipcode() {

		return workZipcode;
	}

	/**
	 * @param workZipcode
	 *            The workZipcode to set.
	 */
	public void setWorkZipcode( String workZipcode ) {

		this.workZipcode = workZipcode;
	}

	/**
	 * @return Returns the workCity.
	 */
	public String getWorkCity() {

		return workCity;
	}

	/**
	 * @param workCity
	 *            The workCity to set.
	 */
	public void setWorkCity( String workCity ) {

		this.workCity = workCity;
	}

	/**
	 * @return Returns the workState.
	 */
	public String getWorkState() {

		return workState;
	}

	/**
	 * @param workState
	 *            The workState to set.
	 */
	public void setWorkState( String workState ) {

		this.workState = workState;
	}

	/**
	 * @return Returns the workCountry.
	 */
	public String getWorkCountry() {

		return workCountry;
	}

	/**
	 * @param workCountry
	 *            The workCountry to set.
	 */
	public void setWorkCountry( String workCountry ) {

		this.workCountry = workCountry;
	}

	/**
	 * @return Returns the notes.
	 */
	public String getNotes() {

		return notes;
	}

	/**
	 * @param notes
	 *            The notes to set.
	 */
	public void setNotes( String notes ) {

		this.notes = notes;
	}

	@Override
	public boolean equals( Object obj ) {

		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Address other = (Address) obj;
		if ( addressFolder == null ) {
			if ( other.addressFolder != null )
				return false;
		}
		else if ( !addressFolder.equals( other.addressFolder ) )
			return false;
		if ( birthDate == null ) {
			if ( other.birthDate != null )
				return false;
		}
		else if ( !birthDate.equals( other.birthDate ) )
			return false;
		if ( company == null ) {
			if ( other.company != null )
				return false;
		}
		else if ( !company.equals( other.company ) )
			return false;
		if ( department == null ) {
			if ( other.department != null )
				return false;
		}
		else if ( !department.equals( other.department ) )
			return false;
		if ( displayName == null ) {
			if ( other.displayName != null )
				return false;
		}
		else if ( !displayName.equals( other.displayName ) )
			return false;
		if ( email == null ) {
			if ( other.email != null )
				return false;
		}
		else if ( !email.equals( other.email ) )
			return false;
		if ( email2 == null ) {
			if ( other.email2 != null )
				return false;
		}
		else if ( !email2.equals( other.email2 ) )
			return false;
		if ( email3 == null ) {
			if ( other.email3 != null )
				return false;
		}
		else if ( !email3.equals( other.email3 ) )
			return false;
		if ( email4 == null ) {
			if ( other.email4 != null )
				return false;
		}
		else if ( !email4.equals( other.email4 ) )
			return false;
		if ( email5 == null ) {
			if ( other.email5 != null )
				return false;
		}
		else if ( !email5.equals( other.email5 ) )
			return false;
		if ( firstName == null ) {
			if ( other.firstName != null )
				return false;
		}
		else if ( !firstName.equals( other.firstName ) )
			return false;
		if ( id == null ) {
			if ( other.id != null )
				return false;
		}
		else if ( !id.equals( other.id ) )
			return false;
		if ( im == null ) {
			if ( other.im != null )
				return false;
		}
		else if ( !im.equals( other.im ) )
			return false;
		if ( lastName == null ) {
			if ( other.lastName != null )
				return false;
		}
		else if ( !lastName.equals( other.lastName ) )
			return false;
		if ( middleName == null ) {
			if ( other.middleName != null )
				return false;
		}
		else if ( !middleName.equals( other.middleName ) )
			return false;
		if ( notes == null ) {
			if ( other.notes != null )
				return false;
		}
		else if ( !notes.equals( other.notes ) )
			return false;
		if ( pager == null ) {
			if ( other.pager != null )
				return false;
		}
		else if ( !pager.equals( other.pager ) )
			return false;
		if ( position == null ) {
			if ( other.position != null )
				return false;
		}
		else if ( !position.equals( other.position ) )
			return false;
		if ( privateCity == null ) {
			if ( other.privateCity != null )
				return false;
		}
		else if ( !privateCity.equals( other.privateCity ) )
			return false;
		if ( privateCountry == null ) {
			if ( other.privateCountry != null )
				return false;
		}
		else if ( !privateCountry.equals( other.privateCountry ) )
			return false;
		if ( privateFax == null ) {
			if ( other.privateFax != null )
				return false;
		}
		else if ( !privateFax.equals( other.privateFax ) )
			return false;
		if ( privateMobile == null ) {
			if ( other.privateMobile != null )
				return false;
		}
		else if ( !privateMobile.equals( other.privateMobile ) )
			return false;
		if ( privatePhone == null ) {
			if ( other.privatePhone != null )
				return false;
		}
		else if ( !privatePhone.equals( other.privatePhone ) )
			return false;
		if ( privateState == null ) {
			if ( other.privateState != null )
				return false;
		}
		else if ( !privateState.equals( other.privateState ) )
			return false;
		if ( privateStreet == null ) {
			if ( other.privateStreet != null )
				return false;
		}
		else if ( !privateStreet.equals( other.privateStreet ) )
			return false;
		if ( privateZipcode == null ) {
			if ( other.privateZipcode != null )
				return false;
		}
		else if ( !privateZipcode.equals( other.privateZipcode ) )
			return false;
		if ( title == null ) {
			if ( other.title != null )
				return false;
		}
		else if ( !title.equals( other.title ) )
			return false;
		if ( url == null ) {
			if ( other.url != null )
				return false;
		}
		else if ( !url.equals( other.url ) )
			return false;
		if ( workCity == null ) {
			if ( other.workCity != null )
				return false;
		}
		else if ( !workCity.equals( other.workCity ) )
			return false;
		if ( workCountry == null ) {
			if ( other.workCountry != null )
				return false;
		}
		else if ( !workCountry.equals( other.workCountry ) )
			return false;
		if ( workFax == null ) {
			if ( other.workFax != null )
				return false;
		}
		else if ( !workFax.equals( other.workFax ) )
			return false;
		if ( workMobile == null ) {
			if ( other.workMobile != null )
				return false;
		}
		else if ( !workMobile.equals( other.workMobile ) )
			return false;
		if ( workPhone == null ) {
			if ( other.workPhone != null )
				return false;
		}
		else if ( !workPhone.equals( other.workPhone ) )
			return false;
		if ( workState == null ) {
			if ( other.workState != null )
				return false;
		}
		else if ( !workState.equals( other.workState ) )
			return false;
		if ( workStreet == null ) {
			if ( other.workStreet != null )
				return false;
		}
		else if ( !workStreet.equals( other.workStreet ) )
			return false;
		if ( workZipcode == null ) {
			if ( other.workZipcode != null )
				return false;
		}
		else if ( !workZipcode.equals( other.workZipcode ) )
			return false;
		return true;
	}

}
