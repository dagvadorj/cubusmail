/* UserAccountIBatisDao.java

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
package com.cubusmail.server.user;

import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.cubusmail.common.model.Contact;
import com.cubusmail.common.model.ContactFolder;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.UserAccount;


/**
 * TODO: documentation
 *
 * @author Juergen Schlierf
 */
public class UserAccountIBatisDao extends SqlMapClientDaoSupport implements IUserAccountDao {

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#deleteContactFolder(com.cubusmail.common.model.ContactFolder)
	 */
	public void deleteContactFolder( ContactFolder folder ) {

		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#deleteContacts(java.lang.Long[])
	 */
	public void deleteContacts( Long[] ids ) {

		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#deleteIdentities(java.util.List)
	 */
	public void deleteIdentities( List<Identity> identities ) {

		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#getContactByEmail(com.cubusmail.common.model.ContactFolder, java.lang.String)
	 */
	public Contact getContactByEmail( ContactFolder folder, String email ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#getContactById(java.lang.Long)
	 */
	public Contact getContactById( Long id ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#getRecipientContactFolder(com.cubusmail.common.model.UserAccount)
	 */
	public ContactFolder getRecipientContactFolder( UserAccount account ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#getUserAccountById(java.lang.Integer)
	 */
	public UserAccount getUserAccountById( Integer id ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#getUserAccountByUsername(java.lang.String)
	 */
	public UserAccount getUserAccountByUsername( String username ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#moveContacts(java.lang.Long[], com.cubusmail.common.model.ContactFolder)
	 */
	public void moveContacts( Long[] contactIds, ContactFolder targetFolder ) {

		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#retrieveContactFolders(com.cubusmail.common.model.UserAccount)
	 */
	public List<ContactFolder> retrieveContactFolders( UserAccount account ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#retrieveContactList(com.cubusmail.common.model.ContactFolder)
	 */
	public List<Contact> retrieveContactList( ContactFolder folder ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#retrieveRecipients(com.cubusmail.common.model.UserAccount, java.lang.String)
	 */
	public List<Contact> retrieveRecipients( UserAccount account, String searchString ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#saveContact(com.cubusmail.common.model.Contact)
	 */
	public Long saveContact( Contact contact ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#saveContactFolder(com.cubusmail.common.model.ContactFolder)
	 */
	public Long saveContactFolder( ContactFolder folder ) {

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#saveRecipients(com.cubusmail.common.model.UserAccount, javax.mail.internet.InternetAddress[])
	 */
	public void saveRecipients( UserAccount account, InternetAddress[] addresses ) {

		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.cubusmail.server.user.IUserAccountDao#saveUserAccount(com.cubusmail.common.model.UserAccount)
	 */
	public Long saveUserAccount( UserAccount account ) {

		// TODO Auto-generated method stub
		return null;
	}

}
