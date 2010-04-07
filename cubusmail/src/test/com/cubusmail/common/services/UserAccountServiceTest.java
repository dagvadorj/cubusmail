/* UserAccountServiceTest.java

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
package com.cubusmail.common.services;

import java.util.List;

import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.common.model.AddressFolder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class UserAccountServiceTest extends GWTTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {

		return "com.cubusmail.CubusmailUnitTests";
	}

	public void testRetrieveAddressFolders() {

		ServiceProvider.getUserAccountService().retrieveAddressFolders( new AsyncCallback<List<AddressFolder>>() {

			public void onSuccess( List<AddressFolder> result ) {
				
			}

			public void onFailure( Throwable caught ) {

				GWT.log( caught.getMessage(), caught );
				assertTrue( false );
				finishTest();
			}
		} );

		delayTestFinish( 100000 );
	}
}
