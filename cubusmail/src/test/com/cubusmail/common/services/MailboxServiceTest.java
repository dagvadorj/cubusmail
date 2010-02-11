/* MailboxServiceTest.java

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

import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.common.model.GWTMailbox;
import com.cubusmail.common.model.GWTMessageList;
import com.cubusmail.common.model.GWTMessageRecord;
import com.cubusmail.common.model.MessageListFields;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Unittests for mailbox service.
 * 
 * @author Juergen Schlierf
 */
public class MailboxServiceTest extends GWTTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {

		return "com.cubusmail.CubusmailUnitTests";
	}

	/**
	 * 
	 */
	public void testLogin() {

		ServiceProvider.getCubusService().login( "schlierf", "schlierf", new AsyncCallback<GWTMailbox>() {

			public void onSuccess( GWTMailbox result ) {

				retrieveMessages();
			}

			public void onFailure( Throwable caught ) {

				GWT.log( caught.getMessage(), caught );
				assertTrue( false );
				finishTest();
			}
		} );

		delayTestFinish( 100000 );

		GWT.log( "Ready", null );
	}

	/**
	 * 
	 */
	private void retrieveMessages() {

		MessageListFields[] fields = new MessageListFields[] { MessageListFields.CONTENT };
		String[] values = new String[] { "wissen", "wie" };
		ServiceProvider.getMailboxService().retrieveMessages( "INBOX", 0, 100, null, true, fields, values,
				new AsyncCallback<GWTMessageList>() {

					public void onSuccess( GWTMessageList result ) {

						assertNotNull( result );
						assertNotNull( result.getMessages() );

						for (GWTMessageRecord record : result.getMessages()) {
							GWT.log( record.getId() + " : " + record.getFrom() + " : " + record.getSubject(), null );
						}

						finishTest();
					}

					public void onFailure( Throwable caught ) {

						finishTest();
						GWT.log( caught.getMessage(), caught );
						assertTrue( false );
					}
				} );
	}

}
