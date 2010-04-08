/* CreateAddressTestData.java

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

import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.AddressFolderType;
import com.cubusmail.common.model.UserAccount;
import com.cubusmail.server.util.DBManager;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationDataSourceContext.xml",
		"classpath:applicationMailContext.xml" })
public class CreateAddressTestData implements ApplicationContextAware {

	private final Log log = LogFactory.getLog( getClass() );

	private ApplicationContext applicationContext;
	private IUserAccountDao userAccountDao;

	@Test
	public void createTestData() {

		try {
			UserAccount account = this.userAccountDao.getUserAccountByUsername( "schlierf" );
			if ( account == null ) {
				account = this.applicationContext.getBean( UserAccount.class );
				account.setCreated( new Date() );
				account.setUsername( "schlierf" );
				this.userAccountDao.saveUserAccount( account );
			}
			for (int i = 1; i < 10; i++) {
				AddressFolder folder = this.applicationContext.getBean( AddressFolder.class );
				folder.setName( "Folder " + i );
				folder.setType( AddressFolderType.STANDARD );
				account.addAddressFolder( folder );
				this.userAccountDao.saveAddressFolder( folder );
			}
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Before
	public void init() {

		DBManager manager = this.applicationContext.getBean( DBManager.class );
		try {
			manager.initInternalDB();
			this.userAccountDao = this.applicationContext.getBean( IUserAccountDao.class );
		}
		catch (Exception e) {
			log.error( e.getMessage(), e );
			Assert.fail( e.getMessage() );
		}
	}

	@Override
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}
}
