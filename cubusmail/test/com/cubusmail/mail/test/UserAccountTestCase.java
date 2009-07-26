/* PreferencesTestCase.java
 */
package com.cubusmail.mail.test;

import java.util.Date;

import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;

import com.cubusmail.core.BeanFactory;
import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.user.UserAccountDao;

/**
 * @author Jürgen Schlierf
 */
public class UserAccountTestCase extends AbstractAnnotationAwareTransactionalTests {

	private UserAccountDao userAccountDao;

	public void testWriteRead() {

		UserAccount account = this.userAccountDao.getUserAccountByUsername( "schlierf3" );
		account.setCreated( new Date() );
		account.setLastLogin( new Date() );
		account.setUsername( "schlierf3" );

		Identity identity = new Identity();
		identity.setDisplayName( "Jürgen Schlierf" );
		identity.setEmail( "test@test.de" );
		account.addIdentity( identity );

		Long id = this.userAccountDao.saveUserAccount( account );
		assertNotNull( id );
		assertTrue( id.intValue() > 0 );

		UserAccount readAccount = this.userAccountDao.getUserAccountByUsername( "schlierf3" );
		assertNotNull( readAccount );
		assertEquals( "schlierf3", readAccount.getUsername() );
	}

	@Override
	protected String[] getConfigLocations() {

		return new String[] { "cubus_beans.xml" };
	}

	/**
	 * @param userAccountDao The userAccountDao to set.
	 */
	public void setUserAccountDao( UserAccountDao userAccountDao ) {

		this.userAccountDao = userAccountDao;
	}

	@Override
	protected boolean isDefaultRollback() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.test.AbstractTransactionalSpringContextTests#onSetUp
	 * ()
	 */
	@Override
	protected void onSetUp() throws Exception {

		BeanFactory.setContext( getApplicationContext() );
	}
}
