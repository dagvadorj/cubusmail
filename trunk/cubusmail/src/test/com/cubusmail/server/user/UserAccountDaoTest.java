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
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubusmail.common.model.UserAccount;
import com.ibatis.common.jdbc.ScriptRunner;
import com.ibatis.common.resources.Resources;

/**
 * Unittests for UserAccountDao
 * 
 * @author Juergen Schlierf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml", "classpath*:com/cubusmail/testDBContext.xml",
		"classpath*:com/cubusmail/testUserAcountContext.xml" })
public class UserAccountDaoTest implements ApplicationContextAware {

	private ApplicationContext applicationContext;

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

	@BeforeClass
	public void initDB() {

		try {
			Connection con = getConnection();
			ScriptRunner runner = new ScriptRunner( con, true, true );
			runner.runScript( Resources.getResourceAsReader( "sql/creation_h2.sql" ) );

		}
		catch (SQLException e) {
			e.printStackTrace();
			Assert.fail( e.getMessage() );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( e.getMessage() );
		}
	}

	@Test
	public void testCreateUpdateUserAccount() {

		IUserAccountDao userAccountDao = (IUserAccountDao) this.applicationContext.getBean( "userAccountDao" );

		UserAccount testUserAccount = (UserAccount) this.applicationContext.getBean( "testUserAccount" );
		Long id = userAccountDao.saveUserAccount( testUserAccount );

		Assert.assertTrue( id > 0 );

		UserAccount userAccount = userAccountDao.getUserAccountByUsername( "testuser" );
		Assert.assertNotNull( userAccount );

		userAccount.getPreferences().setTheme( "Testtheme" );
		userAccountDao.saveUserAccount( userAccount );

		UserAccount userAccount2 = userAccountDao.getUserAccountByUsername( "testuser" );
		Assert.assertEquals( "Testtheme", userAccount2.getPreferences().getTheme() );
	}

	@Test
	public void testUserAccountWithIdentities() {
		IUserAccountDao userAccountDao = (IUserAccountDao) this.applicationContext.getBean( "userAccountDao" );
		UserAccount testUserAccount = (UserAccount) this.applicationContext.getBean( "testUserAccount" );
		
		userAccountDao.saveUserAccount( testUserAccount );
		userAccountDao.saveIdentities( testUserAccount );
	}

	private Connection getConnection() throws BeansException, SQLException {

		return ((DataSource) this.applicationContext.getBean( "dataSource" )).getConnection();
	}
}