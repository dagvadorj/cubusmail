/* IUserAccountServiceAsync.java

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
package com.cubusmail.gwtui.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactFolder;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Interface for UserAccountService.
 * 
 * @author Juergen Schlierf
 */
public interface IUserAccountServiceAsync {

	/**
	 * 
	 * @param account
	 */
	public void saveUserAccount( UserAccount account, AsyncCallback<UserAccount> callback );

	/**
	 * 
	 * @return
	 */
	public void retrieveUserAccount( AsyncCallback<UserAccount> callback );

	/**
	 * @return
	 */
	public void retrieveContactFolders( AsyncCallback<List<ContactFolder>> callback );

	/**
	 * @param folder
	 */
	public void createContactFolder( String folderName, AsyncCallback<ContactFolder> callback );

	/**
	 * @param folder
	 */
	public void saveContactFolder( ContactFolder folder, AsyncCallback<Void> callback );

	/**
	 * @param folder
	 */
	public void deleteContactFolder( ContactFolder folder, AsyncCallback<Void> callback );

	/**
	 * Retrieve the contacts especially for the grid.
	 * 
	 * @return
	 */
	public void retrieveContactArray( ContactFolder folder, String sortField, String sortDirection,
			AsyncCallback<String[][]> callback );

	/**
	 * @param contact
	 */
	public void saveContact( Contact contact, AsyncCallback<Void> callback );

	/**
	 * @param contacts
	 */
	public void deleteContacts( Long[] ids, AsyncCallback<Void> callback );

	/**
	 * @param contactIds
	 * @param targetFolder
	 */
	public void moveContacts( Long[] contactIds, ContactFolder targetFolder, AsyncCallback<Void> callback );

	/**
	 * @param id
	 * @return
	 */
	public void retrieveContact( Long id, AsyncCallback<Contact> callback );

	/**
	 * @return
	 */
	public void retrieveTimezones( AsyncCallback<String[][]> callback );

	/**
	 * @return
	 */
	public void retrieveRecipientsArray( String filterLine, AsyncCallback<String[][]> callback );
}
