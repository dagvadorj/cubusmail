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
package com.cubusmail.common.services;

import java.util.List;

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.UserAccount;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
	public void retrieveContactFolders( AsyncCallback<List<AddressFolder>> callback );

	/**
	 * @param folder
	 */
	public void createContactFolder( String folderName, AsyncCallback<AddressFolder> callback );

	/**
	 * @param folder
	 */
	public void saveContactFolder( AddressFolder folder, AsyncCallback<Void> callback );

	/**
	 * @param folder
	 */
	public void deleteContactFolder( AddressFolder folder, AsyncCallback<Void> callback );

	/**
	 * Retrieve the contacts especially for the grid.
	 * 
	 * @return
	 */
	public void retrieveContactArray( AddressFolder folder, String sortField, String sortDirection,
			AsyncCallback<String[][]> callback );

	/**
	 * @param contact
	 */
	public void saveContact( Address contact, AsyncCallback<Void> callback );

	/**
	 * @param contacts
	 */
	public void deleteContacts( List<Long> ids, AsyncCallback<Void> callback );

	/**
	 * @param contactIds
	 * @param targetFolder
	 */
	public void moveContacts( Long[] contactIds, AddressFolder targetFolder, AsyncCallback<Void> callback );

	/**
	 * @param id
	 * @return
	 */
	public void retrieveContact( Long id, AsyncCallback<Address> callback );

	/**
	 * @return
	 */
	public void retrieveTimezones( AsyncCallback<String[][]> callback );

	/**
	 * @return
	 */
	public void retrieveRecipientsArray( String filterLine, AsyncCallback<String[][]> callback );
}
