/* DaoTest.java
 */
package com.cubusmail.mail.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.UserAccount;
import com.cubusmail.user.UserAccountDao;

/**
 * 
 * @author Jürgen Schlierf
 */
public class DaoTest {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		ApplicationContext context = new ClassPathXmlApplicationContext( "cubus_beans.xml" );
		UserAccountDao dao = (UserAccountDao) context.getBean( "userAccountDao" );

		
		UserAccount account = (UserAccount) context.getBean( "userAccount" );
		account.setCreated( new Date() );
		account.setLastLogin( new Date() );
		account.setUsername( "schlierf" );

		for ( int i = 0; i < 5; i++ ) {
			Identity identity = (Identity) context.getBean( "identity" );
			identity.setBcc( "bcc" + 1 );
			identity.setDisplayName( "Displayname" + 1 );
			identity.setEmail( "email" + 1 );
			identity.setOrganisation( "organisation" + 1 );
			account.addIdentity( identity );
		}

		Long id = dao.saveUserAccount( account );
		System.out.println( "id " + id );
	}

}
