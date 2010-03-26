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
package com.cubusmail.server.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;

import com.cubusmail.common.exceptions.GWTInvalidSessionException;
import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.ContactListFields;
import com.cubusmail.common.model.Identity;
import com.cubusmail.common.model.UserAccount;
import com.cubusmail.common.services.IUserAccountService;
import com.cubusmail.server.mail.IMailbox;
import com.cubusmail.server.mail.SessionManager;
import com.cubusmail.server.mail.util.MessageUtils;
import com.cubusmail.server.mail.util.MessageUtils.AddressStringType;
import com.cubusmail.server.user.IUserAccountDao;
import com.cubusmail.server.util.BeanFactory;
import com.cubusmail.server.util.BeanIds;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of UserAccountService.
 * 
 * @author Juergen Schlierf
 */
public class UserAccountService extends RemoteServiceServlet implements IUserAccountService {

	private static final long serialVersionUID = 3680152258110434790L;

	private Logger log = Logger.getLogger( getClass().getName() );

	public UserAccountService() {

	}

	/**
	 * @return
	 */
	private IUserAccountDao getUserAccountDao() {

		return (IUserAccountDao) BeanFactory.getBean( BeanIds.USER_ACCOUNT_DAO );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.server.rpc.RemoteServiceServlet#processCall(java.
	 * lang.String)
	 */
	@Override
	public String processCall( String payload ) throws SerializationException {

		if ( SessionManager.isLoggedIn() ) {
			try {
				return super.processCall( payload );
			}
			catch (SerializationException e) {
				log.error( e.getMessage(), e );
				throw e;
			}
		}
		else {
			RPCRequest rpcRequest = RPC.decodeRequest( payload, this.getClass(), this );
			return RPC.encodeResponseForFailure( rpcRequest.getMethod(), new GWTInvalidSessionException() );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#saveUserAccount
	 * (com.cubusmail.gwtui.domain.UserAccount)
	 */
	public UserAccount saveUserAccount( UserAccount account ) {

		UserAccount origin = SessionManager.get().getUserAccount();

		List<Identity> identitiesToDelete = new ArrayList<Identity>();
		for (Identity identity : origin.getIdentities()) {
			if ( !account.getIdentities().contains( identity ) ) {
				identitiesToDelete.add( identity );
				account.removeIdentity( identity );
			}
		}
		if ( identitiesToDelete.size() > 0 ) {
			getUserAccountDao().deleteIdentities( identitiesToDelete );
		}
		getUserAccountDao().saveUserAccount( account );
		SessionManager.get().setUserAccount( account );

		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#retrieveUserAccount
	 * ()
	 */
	public UserAccount retrieveUserAccount() {

		IMailbox mailbox = SessionManager.get().getMailbox();

		return getUserAccountDao().getUserAccountByUsername( mailbox.getUserName() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.cubusmail.gwtui.client.services.IUserAccountService#
	 * retrieveContactFolders()
	 */
	public List<AddressFolder> retrieveContactFolders() {

		UserAccount account = SessionManager.get().getUserAccount();
		return getUserAccountDao().retrieveContactFolders( account );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#createContactFolder
	 * (com.cubusmail.gwtui.domain.ContactFolder)
	 */
	public AddressFolder createContactFolder( String folderName ) {

		AddressFolder folder = new AddressFolder();
		folder.setFolderName( folderName );

		UserAccount account = SessionManager.get().getUserAccount();
		folder.setUserAccount( account );
		getUserAccountDao().saveContactFolder( folder );

		return folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#saveContactFolder
	 * (com.cubusmail.gwtui.domain.ContactFolder)
	 */
	public void saveContactFolder( AddressFolder folder ) {

		UserAccount account = SessionManager.get().getUserAccount();
		folder.setUserAccount( account );
		getUserAccountDao().saveContactFolder( folder );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#deleteContactFolder
	 * (com.cubusmail.gwtui.domain.ContactFolder)
	 */
	public void deleteContactFolder( AddressFolder folder ) {

		getUserAccountDao().deleteContactFolder( folder );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#retrieveContactArray
	 * ()
	 */
	@SuppressWarnings("unchecked")
	public String[][] retrieveContactArray( AddressFolder folder, String sortField, String sortDirection ) {

		List<Address> contactList = getUserAccountDao().retrieveContactList( folder );

		if ( contactList != null && contactList.size() > 0 ) {
			if ( !StringUtils.isEmpty( sortField ) ) {
				boolean ascending = "ASC".equals( sortDirection );
				String fieldName = null;
				if ( sortField.equals( ContactListFields.DISPLAY_NAME.name() ) ) {
					fieldName = "displayName";
				}
				else if ( sortField.equals( ContactListFields.EMAIL.name() ) ) {
					fieldName = "email";
				}
				else if ( sortField.equals( ContactListFields.COMPANY.name() ) ) {
					fieldName = "company";
				}
				Comparator comparator = null;
				if ( ascending ) {
					comparator = new BeanComparator( fieldName );
				}
				else {
					comparator = new ReverseComparator( new BeanComparator( fieldName ) );
				}
				Collections.sort( contactList, comparator );
			}

			String[][] array = new String[contactList.size()][ContactListFields.values().length];
			for (int i = 0; i < contactList.size(); i++) {
				array[i][ContactListFields.ID.ordinal()] = Long.toString( contactList.get( i ).getId() );
				array[i][ContactListFields.DISPLAY_NAME.ordinal()] = contactList.get( i ).getDisplayName();
				array[i][ContactListFields.EMAIL.ordinal()] = contactList.get( i ).getEmail();
				array[i][ContactListFields.COMPANY.ordinal()] = contactList.get( i ).getCompany();
				array[i][ContactListFields.INTERNET_ADDRESS.ordinal()] = MessageUtils.toInternetAddress( contactList
						.get( i ).getEmail(), contactList.get( i ).getDisplayName() );
			}

			return array;
		}
		else {
			return new String[0][0];
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.cubusmail.gwtui.client.services.IUserAccountService#
	 * retrieveRecipientsArray()
	 */
	public String[][] retrieveRecipientsArray( String addressLine ) {

		UserAccount account = SessionManager.get().getUserAccount();

		String[] addressStrings = parsePreviousAndLastAddress( addressLine );

		String searchString = addressStrings[1];
		List<Address> contacts = getUserAccountDao().retrieveRecipients( account, searchString );

		if ( contacts != null ) {
			String[][] array = new String[contacts.size()][2];
			for (int i = 0; i < contacts.size(); i++) {
				if ( !StringUtils.isEmpty( addressStrings[0] ) ) {
					array[i][0] = addressStrings[0]
							+ ", "
							+ MessageUtils.toInternetAddress( contacts.get( i ).getEmail(), contacts.get( i )
									.getDisplayName() );
				}
				else {
					array[i][0] = MessageUtils.toInternetAddress( contacts.get( i ).getEmail(), contacts.get( i )
							.getDisplayName() );
				}
				array[i][1] = HtmlUtils.htmlEscape( array[i][0] );
			}

			return array;
		}

		return new String[][] { { addressLine, addressLine } };
	}

	/**
	 * Parse out, the uncomplete Adresses
	 * 
	 * @param addressLine
	 * @return
	 * @throws MessagingException
	 */
	public String[] parsePreviousAndLastAddress( String addressLine ) {

		String[] result = new String[] { "", "" };

		try {
			InternetAddress[] addresses = InternetAddress.parse( addressLine, false );

			if ( addresses != null ) {
				if ( addresses.length > 1 ) {
					InternetAddress[] dest = new InternetAddress[addresses.length - 1];
					for (int i = 0; i < addresses.length - 1; i++) {
						dest[i] = addresses[i];
					}
					result[0] = MessageUtils.getMailAdressString( dest, AddressStringType.COMPLETE );
				}
				result[1] = addresses[addresses.length - 1].toUnicodeString();
			}
		}
		catch (MessagingException e) {
			// should never happen
			log.error( e.getMessage() );
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#deleteContacts
	 * (java.util.List)
	 */
	public void deleteContacts( Long[] ids ) {

		getUserAccountDao().deleteContacts( ids );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#moveContacts(
	 * java.lang.Long[], com.cubusmail.gwtui.domain.ContactFolder)
	 */
	public void moveContacts( Long[] contactIds, AddressFolder targetFolder ) {

		getUserAccountDao().moveContacts( contactIds, targetFolder );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#saveContact(com
	 * .cubusmail.gwtui.domain.Contact)
	 */
	public void saveContact( Address contact ) {

		getUserAccountDao().saveContact( contact );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#retrieveContact
	 * (java.lang.Long)
	 */
	public Address retrieveContact( Long id ) {

		return getUserAccountDao().getContactById( id );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.services.IUserAccountService#retrieveTimezones
	 * ()
	 */
	public String[][] retrieveTimezones() {

		NumberFormat format = new DecimalFormat( "00" );
		Locale locale = SessionManager.get().getLocale();
		ResourceBundle bundle = ConvertUtil.getTimezonesBundle( locale );
		Enumeration<String> ids = bundle.getKeys();
		List<TimeZone> timeZoneList = toSortedZimeZoneList( ids );
		String[][] result = new String[timeZoneList.size()][2];
		int index = 0;
		for (TimeZone timeZone : timeZoneList) {
			result[index][0] = timeZone.getID();
			String hours = format.format( timeZone.getRawOffset() / 3600000 );
			if ( timeZone.getRawOffset() >= 0 ) {
				result[index][1] = "(+" + hours + ":00) " + bundle.getString( timeZone.getID() );
			}
			else {
				result[index][1] = "(" + hours + ":00) " + bundle.getString( timeZone.getID() );
			}
			index++;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<TimeZone> toSortedZimeZoneList( Enumeration<String> ids ) {

		List<TimeZone> result = new ArrayList<TimeZone>();
		while (ids.hasMoreElements()) {
			result.add( TimeZone.getTimeZone( ids.nextElement() ) );
		}

		Collections.sort( result, new BeanComparator( "rawOffset" ) );

		return result;
	}
}
