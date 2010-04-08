/* UserAccountDaoTest.java

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.UserAccount;
import com.cubusmail.server.util.DBManager;

/**
 * Unittests for UserAccountDao
 * 
 * @author Juergen Schlierf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:com/cubusmail/server/user/testDataSourceContext.xml",
		"classpath:com/cubusmail/server/user/testUserAcountContext.xml" })
@SuppressWarnings("unchecked")
public class UserAccountDaoTest implements ApplicationContextAware {

	private final Log log = LogFactory.getLog( getClass() );

	private ApplicationContext applicationContext;

	private SingleConnectionDataSource dataSource;

	private IUserAccountDao userAccountDao;

	private UserAccount testUserAccount;

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

	@Before
	public void initDB() {

		this.userAccountDao = this.applicationContext.getBean( IUserAccountDao.class );
		this.testUserAccount = this.applicationContext.getBean( "testUserAccount", UserAccount.class );
		this.dataSource = this.applicationContext.getBean( SingleConnectionDataSource.class );
		DBManager manager = this.applicationContext.getBean( DBManager.class );

		try {
			manager.initInternalDB();
			Long id = this.userAccountDao.saveUserAccount( testUserAccount );
			Assert.assertNotNull( id );
		}
		catch (SQLException e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
		catch (IOException e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@After
	public void resetDB() {

		this.dataSource.resetConnection();
	}

	@Test
	public void testCreateUpdateUserAccount() {

		try {
			UserAccount userAccount = this.userAccountDao.getUserAccountByUsername( "testuser" );
			Assert.assertNotNull( userAccount );

			userAccount.getPreferences().setTheme( "Testtheme" );
			this.userAccountDao.saveUserAccount( userAccount );

			UserAccount userAccount2 = this.userAccountDao.getUserAccountByUsername( "testuser" );
			Assert.assertEquals( "Testtheme", userAccount2.getPreferences().getTheme() );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testInsertIdentities() {

		try {
			this.userAccountDao.saveIdentities( this.testUserAccount );

			UserAccount userAccount = userAccountDao.getUserAccountByUsername( "testuser" );
			Assert.assertNotNull( userAccount.getIdentities() );
			Assert.assertTrue( "identities not loaded!", userAccount.getIdentities().size() >= 4 );
			Assert.assertEquals( userAccount, userAccount.getIdentities().get( 0 ).getUserAccount() );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testDeleteIdentities() {

		try {
			int identityCount = this.testUserAccount.getIdentities().size();
			this.userAccountDao.saveIdentities( this.testUserAccount );

			List<Long> ids = new ArrayList<Long>();
			ids.add( this.testUserAccount.getIdentities().get( 0 ).getId() );
			ids.add( this.testUserAccount.getIdentities().get( 1 ).getId() );
			this.userAccountDao.deleteIdentities( ids );

			UserAccount testUserAccount2 = this.userAccountDao.getUserAccountByUsername( "testuser" );
			Assert.assertEquals( testUserAccount2.getIdentities().size(), identityCount - 2 );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testInsertAddressFolder() {

		List<AddressFolder> folders = (List<AddressFolder>) this.applicationContext.getBean( "testAddressFolders" );

		try {
			for (AddressFolder folder : folders) {
				folder.setUserAccount( this.testUserAccount );
				this.userAccountDao.saveAddressFolder( folder );
			}

			List<AddressFolder> savedAdressFolders = this.userAccountDao.retrieveAddressFolders( this.testUserAccount );
			Assert.assertNotNull( savedAdressFolders );
			Assert.assertEquals( folders.size(), savedAdressFolders.size() );
			Assert.assertEquals( folders.get( 0 ).getName(), savedAdressFolders.get( 0 ).getName() );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testUpdateAddressFolders() {

		try {
			List<AddressFolder> folders = (List<AddressFolder>) this.applicationContext.getBean( "testAddressFolders" );
			for (AddressFolder folder : folders) {
				folder.setUserAccount( this.testUserAccount );
				this.userAccountDao.saveAddressFolder( folder );
			}

			folders.get( 0 ).setName( "NewName" );
			this.userAccountDao.saveAddressFolder( folders.get( 0 ) );

			List<AddressFolder> updatedFolders = this.userAccountDao.retrieveAddressFolders( this.testUserAccount );
			Assert.assertNotNull( updatedFolders );
			Assert.assertTrue( updatedFolders.size() > 0 );
			Assert.assertEquals( updatedFolders.get( 0 ).getName(), folders.get( 0 ).getName() );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testDeleteAddressFolders() {

		try {
			List<AddressFolder> folders = (List<AddressFolder>) this.applicationContext.getBean( "testAddressFolders" );
			int foldersCount = folders.size();
			for (AddressFolder folder : folders) {
				folder.setUserAccount( this.testUserAccount );
				this.userAccountDao.saveAddressFolder( folder );
			}

			List<Long> ids = new ArrayList<Long>();
			ids.add( folders.get( 0 ).getId() );
			ids.add( folders.get( 1 ).getId() );
			this.userAccountDao.deleteAddressFolders( ids );

			List<AddressFolder> savedAdressFolders = this.userAccountDao.retrieveAddressFolders( this.testUserAccount );
			Assert.assertNotNull( savedAdressFolders );
			Assert.assertEquals( savedAdressFolders.size(), foldersCount - 2 );
			Assert.assertEquals( savedAdressFolders.get( 0 ).getName(), folders.get( 2 ).getName() );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testInsertAddress() {

		try {
			List<AddressFolder> folders = (List<AddressFolder>) this.applicationContext.getBean( "testAddressFolders" );
			folders.get( 0 ).setUserAccount( this.testUserAccount );
			this.userAccountDao.saveAddressFolder( folders.get( 0 ) );

			List<Address> testAddressList = (List<Address>) this.applicationContext.getBean( "testAddresses" );
			for (Address address : testAddressList) {
				address.setAddressFolder( folders.get( 0 ) );
				this.userAccountDao.saveAddress( address );
			}

			List<Address> savedAddressList = this.userAccountDao.retrieveAddressList( folders.get( 0 ) );
			Assert.assertEquals( testAddressList.get( 0 ), savedAddressList.get( 0 ) );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testUpdateAddress() {

		try {
			List<AddressFolder> folders = (List<AddressFolder>) this.applicationContext.getBean( "testAddressFolders" );
			folders.get( 0 ).setUserAccount( this.testUserAccount );
			this.userAccountDao.saveAddressFolder( folders.get( 0 ) );

			List<Address> testAddressList = (List<Address>) this.applicationContext.getBean( "testAddresses" );
			for (Address address : testAddressList) {
				address.setAddressFolder( folders.get( 0 ) );
				this.userAccountDao.saveAddress( address );
			}

			List<Address> savedAddressList = this.userAccountDao.retrieveAddressList( folders.get( 0 ) );
			Assert.assertNotNull( savedAddressList );
			Assert.assertTrue( savedAddressList.size() > 0 );
			Assert.assertEquals( testAddressList.get( 0 ), savedAddressList.get( 0 ) );

			Address savedAddress = savedAddressList.get( 0 );
			Address testAddress2 = testAddressList.get( 1 );

			// copy modify properties
			Long saveId = savedAddress.getId();
			AddressFolder savedFolder = savedAddress.getAddressFolder();
			BeanUtils.copyProperties( savedAddress, testAddress2 );
			savedAddress.setId( saveId );
			savedAddress.setAddressFolder( savedFolder );
			this.userAccountDao.saveAddress( savedAddress );

			List<Address> savedAddressList2 = this.userAccountDao.retrieveAddressList( folders.get( 0 ) );
			Assert.assertNotNull( savedAddressList2 );
			Assert.assertTrue( savedAddressList2.size() > 0 );
			Assert.assertEquals( savedAddress, savedAddressList2.get( 0 ) );

		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}
}
