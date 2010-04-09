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
import java.util.List;

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

import com.cubusmail.common.model.Address;
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

	private final static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ0123456789";

	private final Log log = LogFactory.getLog( getClass() );

	private ApplicationContext applicationContext;
	private IUserAccountDao userAccountDao;

	@Test
	public void createTestData() {

		try {
			UserAccount account = this.userAccountDao.getUserAccountByUsername( "schlierf" );
			List<AddressFolder> folderList = this.userAccountDao.retrieveAddressFolders( account );
			AddressFolder addressFolder = folderList.get( 0 );
			for (int i = 0; i < alpha.length(); i++) {
				char begin = alpha.charAt( i );
				for (int j = 1; j < 10; j++) {
					Address address = this.applicationContext.getBean( Address.class );
					address.setFirstName( begin + "firstName" + j );
					address.setMiddleName( begin + "middleName" + j );
					address.setLastName( begin + "lastName" + j );
					address.setTitle( begin + "title" + j );
					address.setBirthDate( new Date() );
					address.setCompany( begin + "company" + j );
					address.setPosition( begin + "position" + j );
					address.setDepartment( begin + "department" + j );

					address.setEmail( begin + "email" + j );
					address.setEmail2( begin + "email2" + j );
					address.setEmail3( begin + "email3" + j );
					address.setEmail4( begin + "email4" + j );
					address.setEmail5( begin + "email5" + j );
					address.setIm( begin + "im" + j );
					address.setUrl( begin + "url" + j );

					address.setPrivatePhone( begin + "privatePhone" + j );
					address.setWorkPhone( begin + "workPhone" + j );
					address.setPrivateMobile( begin + "privateMobile" + j );
					address.setWorkMobile( begin + "workMobile" + j );
					address.setPrivateFax( begin + "privateFax" + j );
					address.setWorkFax( begin + "workFax" + j );
					address.setPager( begin + "pager" + j );

					address.setPrivateStreet( begin + "privateStreet" + j );
					address.setPrivateZipcode( begin + "privateZipcode" + j );
					address.setPrivateCity( begin + "privateCity" + j );
					address.setPrivateState( begin + "privateState" + j );
					address.setPrivateCountry( begin + "privateCountry" + j );

					address.setWorkStreet( begin + "workStreet" + j );
					address.setWorkZipcode( begin + "workZipcode" + j );
					address.setWorkCity( begin + "workCity" + j );
					address.setWorkState( begin + "workState" + j );
					address.setWorkCountry( begin + "workCountry" + j );

					address.setNotes( begin + "notes" + j );

					address.setAddressFolder( addressFolder );
					this.userAccountDao.saveAddress( address );
				}

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
