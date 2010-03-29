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

import java.sql.SQLException;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.UserAccount;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
class UserAccountIBatisDao extends SqlMapClientDaoSupport implements IUserAccountDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#deleteContactFolder(com.cubusmail
	 * .common.model.ContactFolder)
	 */
	public void deleteAddressFolders( List<Long> ids ) {

		if ( ids != null && ids.size() > 0 ) {
			getSqlMapClientTemplate().delete( "deleteAddressFolders", ids );
		}
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

		if ( account != null && account.getIdentities() != null ) {
			for (Identity identity : account.getIdentities()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#deleteIdentities(java.util.
	 * List)
	 */
	public void deleteIdentities( List<Long> ids ) throws SQLException {

		if ( ids != null && ids.size() > 0 ) {
			getSqlMapClientTemplate().delete( "deleteIdentities", ids );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#getContactById(java.lang.Long)
	 */
	public Address getContactById( Long id ) {

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

		UserAccount account = (UserAccount) getSqlMapClientTemplate().queryForObject( "selectUserAccountByUsername",
				username );
		if ( account != null ) {
			List<Identity> identities = (List<Identity>) getSqlMapClientTemplate().queryForList( "selectIdentities",
					account.getId() );
			if ( identities != null ) {
				account.setIdentities( identities );
			}
		}

		return account;
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
	public List<AddressFolder> retrieveAddressFolders( UserAccount account ) {

		List<AddressFolder> result = getSqlMapClientTemplate().queryForList( "selectAddressFolders", account.getId() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#retrieveContactList(com.cubusmail
	 * .common.model.ContactFolder)
	 */
	public List<Address> retrieveContactList( AddressFolder folder ) {

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
	public List<Address> retrieveRecipients( UserAccount account, String searchString ) {

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
	public Long saveContact( Address contact ) {

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
	public Long saveAddressFolder( AddressFolder folder ) {

		if ( folder.getId() == null ) {
			Long id = (Long) getSqlMapClientTemplate().insert( "insertAddressFolder", folder );
			folder.setId( id );
			return id;
		}
		else {
			getSqlMapClientTemplate().update( "updateAddressFolder", folder );
			return folder.getId();
		}
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
}
