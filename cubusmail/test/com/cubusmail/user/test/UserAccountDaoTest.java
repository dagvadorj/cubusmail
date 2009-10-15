/* UserAccountDaoTest.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.user.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactFolder;
import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.user.UserAccountDao;

/**
 * Unittests for UserAccountDao
 * 
 * @author Jürgen Schlierf
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:com/cubusmail/user/test/userAccountDaoTestContext.xml")
public class UserAccountDaoTest implements ApplicationContextAware {

	private ApplicationContext context;

	private UserAccountDao userAccountDao;

	@Before
	public void initDB() {

		this.userAccountDao = (UserAccountDao) this.context.getBean( "userAccountDao" );
		UserAccount userAccount = (UserAccount) this.context.getBean( "testUserAccount" );

		this.userAccountDao.saveUserAccount( userAccount );
	}

	@After
	public void closeDB() {

		this.userAccountDao.getSessionFactory().openSession();
	}

	/**
	 * Compare test user account with the persistent one.
	 */
	@Test
	public void testGetUserAccountByUsername() {

		UserAccount userAccount = (UserAccount) this.context.getBean( "testUserAccount" );
		UserAccount savedUserAccount = this.userAccountDao.getUserAccountByUsername( userAccount.getUsername() );
		Assert.assertNotNull( savedUserAccount );

		List<ContactFolder> contactFolders = this.userAccountDao.retrieveContactFolders( userAccount );

		Assert.assertNotNull( contactFolders );
		Assert.assertFalse( contactFolders.isEmpty() );

		List<Contact> contactList = this.userAccountDao.retrieveContactList( contactFolders.get( 0 ) );
		Assert.assertNotNull( contactList );
		Assert.assertFalse( contactList.isEmpty() );
		Assert.assertEquals( contactList.size(), userAccount.getContactFolders().get( 0 ).getContactList().size() );
	}

	/**
	 * Test moveContacts().
	 */
	// @Test
	public void testMoveContacts() {

		UserAccount userAccount = (UserAccount) this.context.getBean( "testUserAccount" );
		UserAccount savedUserAccount = this.userAccountDao.getUserAccountByUsername( userAccount.getUsername() );
		Assert.assertNotNull( savedUserAccount );
		List<ContactFolder> contactFolders = this.userAccountDao.retrieveContactFolders( userAccount );

		ContactFolder targetFolder = contactFolders.get( 0 );
		ContactFolder sourceFolder = contactFolders.get( 1 );
		List<Contact> contacts = this.userAccountDao.retrieveContactList( sourceFolder );
		Long[] contactIds = new Long[] { contacts.get( 0 ).getId(), contacts.get( 1 ).getId() };
		
		this.userAccountDao.moveContacts( contactIds, targetFolder );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.context = applicationContext;
	}
}
