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

import java.lang.reflect.Field;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.cubusmail.common.model.Contact;
import com.cubusmail.common.model.ContactFolder;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.Preferences;
import com.cubusmail.common.model.UserAccount;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
class UserAccountIBatisDao extends SqlMapClientDaoSupport implements IUserAccountDao {

	private Logger logger = Logger.getLogger( this.getClass() );

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#deleteContactFolder(com.cubusmail
	 * .common.model.ContactFolder)
	 */
	public void deleteContactFolder( ContactFolder folder ) {

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
	 * com.cubusmail.server.user.IUserAccountDao#getContactByEmail(com.cubusmail
	 * .common.model.ContactFolder, java.lang.String)
	 */
	public Contact getContactByEmail( ContactFolder folder, String email ) {

		// TODO Auto-generated method stub
		return null;
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
	 * com.cubusmail.server.user.IUserAccountDao#getRecipientContactFolder(com
	 * .cubusmail.common.model.UserAccount)
	 */
	public ContactFolder getRecipientContactFolder( UserAccount account ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#getUserAccountById(java.lang
	 * .Integer)
	 */
	public UserAccount getUserAccountById( Integer id ) {

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
	public UserAccount getUserAccountByUsername( String username ) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#moveContacts(java.lang.Long[],
	 * com.cubusmail.common.model.ContactFolder)
	 */
	public void moveContacts( Long[] contactIds, ContactFolder targetFolder ) {

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.server.user.IUserAccountDao#retrieveContactFolders(com.
	 * cubusmail.common.model.UserAccount)
	 */
	public List<ContactFolder> retrieveContactFolders( UserAccount account ) {

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
	public List<Contact> retrieveContactList( ContactFolder folder ) {

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
	public Long saveContactFolder( ContactFolder folder ) {

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
			account.setPreferencesJson( preferences2json( account.getPreferences() ) );
			return (Long) getSqlMapClientTemplate().insert( "insertAccount", account );
		}
		catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
		return null;
	}

	/**
	 * @param preferencesJson
	 * @param preferences
	 */
	private void json2Preferences( String preferencesJson, Preferences preferences ) {

		try {
			JSONObject object = new JSONObject( preferencesJson );
			Field[] fields = Preferences.class.getFields();
			if ( fields != null ) {
				for (Field field : fields) {
					Object value = object.has( field.getName() ) ? object.get( field.getName() ) : null;
					if ( value != null ) {
						if ( value instanceof Integer ) {
							field.setInt( preferences, ((Integer) value).intValue() );
						}
						else if ( value instanceof Boolean ) {
							field.setBoolean( preferences, ((Boolean) value).booleanValue() );
						}
						else if ( value instanceof String ) {
							field.set( preferences, value );
						}
					}
				}
			}
		}
		catch (JSONException e) {
			logger.error( e.getMessage(), e );
		}
		catch (NumberFormatException e) {
			logger.error( e.getMessage(), e );
		}
		catch (IllegalArgumentException e) {
			logger.error( e.getMessage(), e );
		}
		catch (IllegalAccessException e) {
			logger.error( e.getMessage(), e );
		}
	}

	/**
	 * @param preferences
	 * @return
	 */
	private String preferences2json( Preferences preferences ) {

		JSONObject jsonObject = new JSONObject( preferences );
		return jsonObject.toString();
	}
}
