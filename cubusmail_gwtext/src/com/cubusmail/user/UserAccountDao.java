/* MessageUtils.java

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
package com.cubusmail.user;

import java.lang.reflect.Field;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.util.HtmlUtils;

import com.cubusmail.core.BeanIds;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactFolder;
import com.cubusmail.gwtui.domain.ContactFolderType;
import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.Preferences;
import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.mail.util.MessageUtils;

/**
 * DAO for user account operations.
 * 
 * @author Juergen Schlierf
 */
public class UserAccountDao extends HibernateDaoSupport implements ApplicationContextAware {

	private Logger logger = Logger.getLogger( this.getClass() );

	private ApplicationContext applicationContext;

	/**
	 * @param id
	 * @return
	 */
	public UserAccount getUserAccountById( Integer id ) {

		UserAccount account = (UserAccount) getHibernateTemplate().get( UserAccount.class, id );
		prepareAccount( account );
		return account;
	}

	/**
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserAccount getUserAccountByUsername( String username ) {

		List<UserAccount> list = getHibernateTemplate().find( "from UserAccount u where u.username = ?", username );
		if ( list.size() == 0 ) {
			return null;
		}
		else if ( list.size() == 1 ) {
			UserAccount account = list.get( 0 );
			if ( account.getPreferences() == null ) {
				account.setPreferences( (Preferences) this.applicationContext.getBean( BeanIds.PREFERENCES_BEAN ) );
			}
			if ( !StringUtils.isEmpty( account.getPreferencesJson() ) ) {
				json2Preferences( account.getPreferencesJson(), account.getPreferences() );
			}
			prepareAccount( account );
			return account;
		}
		else {
			throw new DataIntegrityViolationException( "More than one user account for the same username." );
		}
	}

	/**
	 * @param account
	 * @return
	 */
	public Long saveUserAccount( UserAccount account ) {

		JSONObject jsonObject = new JSONObject( account.getPreferences() );
		account.setPreferencesJson( jsonObject.toString() );
		prepareAccount( account );
		getHibernateTemplate().saveOrUpdate( account );
		if ( logger.isDebugEnabled() ) {
			logger.debug( "Save UserAccount with ID: " + account.getId() );
		}

		return account.getId();
	}

	/**
	 * @param identities
	 */
	public void deleteIdentities( List<Identity> identities ) {

		getHibernateTemplate().deleteAll( identities );
	}

	/**
	 * @param account
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContactFolder> retrieveContactFolders( UserAccount account ) {

		List<ContactFolder> folderList = getHibernateTemplate().find(
				"from ContactFolder cf where cf.userAccount.id=?", account.getId() );

		return folderList;
	}

	/**
	 * @param account
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ContactFolder getRecipientContactFolder( UserAccount account ) {

		List<ContactFolder> folderList = getHibernateTemplate().find(
				"from ContactFolder cf where cf.userAccount.id=? and cf.folderType="
						+ ContactFolderType.RECIPIENTS.getType(), account.getId() );

		if ( folderList != null && folderList.size() > 0 ) {
			return folderList.get( 0 );
		}

		return null;
	}

	/**
	 * @param folder
	 */
	public Long saveContactFolder( ContactFolder folder ) {

		getHibernateTemplate().saveOrUpdate( folder );
		if ( logger.isDebugEnabled() ) {
			logger.debug( "Save UserAccount with ID: " + folder.getId() );
		}

		return folder.getId();
	}

	/**
	 * @param folder
	 */
	public void deleteContactFolder( ContactFolder folder ) {

		getHibernateTemplate().delete( folder );
	}

	/**
	 * @param folder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Contact> retrieveContactList( ContactFolder folder ) {

		List<Contact> contactList = getHibernateTemplate().find( "from Contact c where c.contactFolder.id=?",
				folder.getId() );

		fillDisplayName( contactList );

		return contactList;
	}

	/**
	 * @param account
	 * @param searchString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Contact> retrieveRecipients( UserAccount account, String searchString ) {

		if ( StringUtils.isEmpty( searchString ) ) {
			return null;
		}

		searchString = searchString.toUpperCase();

		String query = "from Contact c where c.contactFolder.userAccount.id=? and (upper(c.email) like '"
				+ searchString + "%'";
		if ( searchString.indexOf( '@' ) == -1 ) {
			query += " or upper(c.firstName) like '" + searchString + "%' or upper(c.lastName) like '" + searchString
					+ "%')";
		}
		else {
			query += ")";
		}

		List<Contact> contactList = getHibernateTemplate().find( query, account.getId() );
		fillDisplayName( contactList );

		return contactList;
	}

	/**
	 * @param contactList
	 */
	private void fillDisplayName( List<Contact> contactList ) {

		if ( contactList != null ) {
			for (Contact contact : contactList) {
				String displayName = "";
				boolean isLastNameEmpty = StringUtils.isEmpty( contact.getLastName() );
				boolean isFirstNameEmpty = StringUtils.isEmpty( contact.getFirstName() );
				if ( !isLastNameEmpty ) {
					displayName = contact.getLastName();
				}
				if ( !isLastNameEmpty && !isFirstNameEmpty ) {
					displayName += ", ";
				}
				if ( !isFirstNameEmpty ) {
					displayName += contact.getFirstName();
				}
				contact.setDisplayName( displayName );
			}
		}
	}

	/**
	 * @param contact
	 * @return
	 */
	public Long saveContact( Contact contact ) {

		getHibernateTemplate().saveOrUpdate( contact );
		if ( logger.isDebugEnabled() ) {
			logger.debug( "Saved Contact with ID: " + contact.getId() );
		}

		return contact.getId();
	}

	/**
	 * @param ids
	 */
	public void deleteContacts( Long[] ids ) {

		try {
			getHibernateTemplate().bulkUpdate( "delete from ContactAddress a where contact_fk = ?", ids );
			getHibernateTemplate().bulkUpdate( "delete from Contact c where c.id=?", ids );
		}
		catch (Throwable e) {
			logger.error( e.getMessage(), e );
		}
	}

	/**
	 * @param contactIds
	 * @param targetFolder
	 */
	public void moveContacts( Long[] contactIds, ContactFolder targetFolder ) {

		try {
			getHibernateTemplate().bulkUpdate(
					"update Contact set CONTACTFOLDERS_FK = " + targetFolder.getId() + " where ID in ?", contactIds );
		}
		catch (Throwable e) {
			logger.error( e.getMessage(), e );
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public Contact getContactById( Long id ) {

		return (Contact) getHibernateTemplate().get( Contact.class, id );
	}

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Contact getContactByEmail( ContactFolder folder, String email ) {

		List<Contact> list = getHibernateTemplate().find(
				"from Contact c where c.email = ? and c.contactFolder.id=" + folder.getId(), email );

		if ( list != null && list.size() > 0 ) {
			return list.get( 0 );
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
	 * @param account
	 */
	private void prepareAccount( UserAccount account ) {

		if ( account.getIdentities() != null ) {
			for (Identity identity : account.getIdentities()) {
				identity.setInternetAddress( MessageUtils.toInternetAddress( identity.getEmail(), identity
						.getDisplayName() ) );
				identity.setEscapedInternetAddress( HtmlUtils.htmlEscape( identity.getInternetAddress() ) );
			}
		}
	}

	/**
	 * @param addresses
	 */
	public void saveRecipients( UserAccount account, InternetAddress[] addresses ) {

		ContactFolder recipientFolder = getRecipientContactFolder( account );

		if ( recipientFolder == null ) {
			recipientFolder = new ContactFolder( ContactFolderType.RECIPIENTS );
			recipientFolder.setFolderName( "Recipients" );
			recipientFolder.setUserAccount( account );
			saveContactFolder( recipientFolder );
		}
		if ( addresses != null ) {
			for (InternetAddress address : addresses) {
				Contact contact = getContactByEmail( recipientFolder, address.getAddress() );
				if ( contact == null ) {
					contact = new Contact();
					contact.setLastName( address.getPersonal() );
					contact.setEmail( address.getAddress() );
					contact.setContactFolder( recipientFolder );
					saveContact( contact );
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}
}
