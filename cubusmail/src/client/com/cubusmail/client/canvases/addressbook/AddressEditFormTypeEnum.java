/* AddressEditFormTypeEnum.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.client.canvases.addressbook;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public enum AddressEditFormTypeEnum {
	DETAIL_NAME(""),

	PRIVATE_PHONE("Private Phone"), WORK_PHONE("Work Phone"), PRIVATE_MOBILE("Private Mobile"), WORK_MOBILE(
			"Work Mobile"), PRIVATE_FAX("Private Fax"), WORK_FAX("Work Fax"),

	EMAIL1("Email 1"), EMAIL2("Email 2"), EMAIL3("Email 3"), EMAIL4("Email 4"), EMAIL5("Email 5"), IM("IM"), URL(
			"Website"),

	// other information
	TITLE("Title"), BIRTHDATE("Birthdate"), COMPANY("Company"), POSITION("Position"), DEPARTMENT("Department"), PAGER(
			"Pager");

	public final static AddressEditFormTypeEnum[] PHONE_GROUP = { PRIVATE_PHONE, WORK_PHONE, PRIVATE_MOBILE,
			WORK_MOBILE, PRIVATE_FAX, WORK_FAX };

	public final static AddressEditFormTypeEnum[] EMAIL_GROUP = { EMAIL1, EMAIL2, EMAIL3, EMAIL4, EMAIL5 };

	public final static AddressEditFormTypeEnum[] MORE_INFO_GROUP = { TITLE, COMPANY, POSITION, DEPARTMENT, URL, IM,
			PAGER };

	private String title;

	private AddressEditFormTypeEnum( String title ) {

		this.title = title;
	}

	/**
	 * @return
	 */
	public String getTitle() {

		return title;
	}

	public static AddressEditFormTypeEnum getByTitle( String title ) {

		for (AddressEditFormTypeEnum type : values()) {
			if ( type.getTitle().equals( title ) ) {
				return type;
			}
		}

		return null;
	}
}
