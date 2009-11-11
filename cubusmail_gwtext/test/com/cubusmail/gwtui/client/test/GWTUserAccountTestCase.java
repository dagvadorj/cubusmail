/* GWTUserAccountTestCase.java
 */
package com.cubusmail.gwtui.client.test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

import com.cubusmail.gwtui.client.model.GWTMailbox;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.ServiceProvider;

/**
 * Unit test
 * 
 * @author Juergen Schlierf
 */
public class GWTUserAccountTestCase extends GWTTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {

		return "com.cubusmail.gwtui.Cubusmail";
	}

	public void testSimple() throws Exception {

		// Log.debug( "!!!!!!!!!!!!!! Test !!!!!!!!!!!!!!!!" );

		delayTestFinish( 10000 );
		ServiceProvider.getCubusService().login( "schlierf", "schlierf", new AsyncCallbackAdapter<GWTMailbox>() {

			@Override
			public void onSuccess( GWTMailbox result ) {

				GWT.log( result.getEmailAddress(), null );
				retrieveRecipientsArray();
				finishTest();
			}

			@Override
			public void onFailure( Throwable caught ) {

				GWT.log( caught.getMessage(), caught );
				finishTest();
			}
		} );

		assertTrue( true );
	}

	private void retrieveRecipientsArray() {

		String addressLine = "schlier@localhost";

		// delayTestFinish( 10000 );
		ServiceProvider.getUserAccountService().retrieveRecipientsArray( addressLine,
				new AsyncCallbackAdapter<String[][]>() {

					@Override
					public void onSuccess( String[][] result ) {

						logArray( result );
						finishTest();
					}

					@Override
					public void onFailure( Throwable caught ) {

						GWT.log( caught.getMessage(), caught );
						// Log.error( caught.getMessage(), caught );
						finishTest();
					}
				} );
	}

	private void logArray( String[][] array ) {

		// Log.debug( array.toString() );
	}
}
