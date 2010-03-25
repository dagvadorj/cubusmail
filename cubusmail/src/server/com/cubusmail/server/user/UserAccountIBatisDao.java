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
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.UserAccount;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
class UserAccountIBatisDao extends SqlMapClientDaoSupport implements IUserAccountDao {

	// private Logger log = Logger.getLogger( this.getClass() );

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#deleteContactFolder(com.cubusmail
	 * .common.model.ContactFolder)
	 */
	public void deleteContactFolder( AddressFolder folder ) {

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#deleteContacts(java.lang.Long
	 * [])
	 */
	public void deleteContacts( Long[] ids ) {

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#saveIdentities(com.cubusmail
	 * .common.model.UserAccount)
	 */
	public void saveIdentities( UserAccount account ) {

		try {
			if ( account != null && account.getIdentities() != null ) {
				for (Identity identity : account.getIdentities()) {
					if ( identity.getUserAccountId() == null ) {
						identity.setUserAccountId( account.getId() );
					}
					if ( identity.getId() == null ) {
						Long id = (Long) getSqlMapClientTemplate().insert( "insertIdentity", identity );
						identity.setId( id );
					}
					else {
						getSqlMapClientTemplate().update( "insertIdentity", identity );
					}
				}
			}
		}
		catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#deleteIdentities(java.util.
	 * List)
	 */
	public void deleteIdentities( List<Identity> identities ) {

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#getContactById(java.lang.Long)
	 */
	public Contact getContactById( Long id ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#getUserAccountByUsername(java
	 * .lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserAccount getUserAccountByUsername( String username ) {

		try {
			UserAccount account = (UserAccount) getSqlMapClientTemplate().queryForObject(
					"selectUserAccountByUsername", username );
			if ( account != null ) {
				List<Identity> identities = (List<Identity>) getSqlMapClientTemplate().queryForList(
						"selectIdentities", account.getId() );
				if ( identities != null ) {
					account.setIdentities( identities );
				}
			}

			return account;
		}
		catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#moveContacts(java.lang.Long[],
	 * com.cubusmail.common.model.ContactFolder)
	 */
	public void moveContacts( Long[] contactIds, AddressFolder targetFolder ) {

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#retrieveContactFolders(com.
	 * cubusmail.common.model.UserAccount)
	 */
	public List<AddressFolder> retrieveContactFolders( UserAccount account ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#retrieveContactList(com.cubusmail
	 * .common.model.ContactFolder)
	 */
	public List<Contact> retrieveContactList( AddressFolder folder ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#retrieveRecipients(com.cubusmail
	 * .common.model.UserAccount, java.lang.String)
	 */
	public List<Contact> retrieveRecipients( UserAccount account, String searchString ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#saveContact(com.cubusmail.common
	 * .model.Contact)
	 */
	public Long saveContact( Contact contact ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#saveContactFolder(com.cubusmail
	 * .common.model.ContactFolder)
	 */
	public Long saveContactFolder( AddressFolder folder ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#saveRecipients(com.cubusmail
	 * .common.model.UserAccount, javax.mail.internet.InternetAddress[])
	 */
	public void saveRecipients( UserAccount account, InternetAddress[] addresses ) {

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#saveUserAccount(com.cubusmail
	 * .common.model.UserAccount)
	 */
	public Long saveUserAccount( UserAccount account ) {

		try {
			if ( account.getId() == null ) {
				Long id = (Long) getSqlMapClientTemplate().insert( "insertUserAccount", account );
				account.setId( id );
				return id;
			}
			else {
				getSqlMapClientTemplate().update( "updateUserAccount", account );
				return account.getId();
			}
		}
		catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
		return null;
	}
}
