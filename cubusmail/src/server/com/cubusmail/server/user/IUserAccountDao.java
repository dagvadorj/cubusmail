/* UserAccountService.java

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
package com.cubusmail.server.user;

import java.util.List;

import javax.mail.internet.InternetAddress;

import com.cubusmail.common.model.Contact;
import com.cubusmail.common.model.ContactFolder;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.UserAccount;

/**
 * TODO: documentation
 *
 * @author Juergen Schlierf
 */
public interface IUserAccountDao {


	/**
	 * @param name
	 * @return
	 */
	public abstract UserAccount getUserAccountByUsername( String username );

	/**
	 * @param account
	 * @return
	 */
	public abstract Long saveUserAccount( UserAccount account );

	/**
	 * @param identities
	 */
	public abstract void deleteIdentities( List<Identity> identities );

	/**
	 * @param account
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List<ContactFolder> retrieveContactFolders( UserAccount account );

	/**
	 * @param account
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract ContactFolder getRecipientContactFolder( UserAccount account );

	/**
	 * @param folder
	 */
	public abstract Long saveContactFolder( ContactFolder folder );

	/**
	 * @param folder
	 */
	public abstract void deleteContactFolder( ContactFolder folder );

	/**
	 * @param folder
	 * @return
	 */
	public abstract List<Contact> retrieveContactList( ContactFolder folder );

	/**
	 * @param account
	 * @param searchString
	 * @return
	 */
	public abstract List<Contact> retrieveRecipients( UserAccount account, String searchString );

	/**
	 * @param contact
	 * @return
	 */
	public abstract Long saveContact( Contact contact );

	/**
	 * @param ids
	 */
	public abstract void deleteContacts( Long[] ids );

	/**
	 * @param contactIds
	 * @param targetFolder
	 */
	public abstract void moveContacts( Long[] contactIds, ContactFolder targetFolder );

	/**
	 * @param id
	 * @return
	 */
	public abstract Contact getContactById( Long id );

	/**
	 * @param id
	 * @return
	 */
	public abstract Contact getContactByEmail( ContactFolder folder, String email );

	/**
	 * @param addresses
	 */
	public abstract void saveRecipients( UserAccount account, InternetAddress[] addresses );

}