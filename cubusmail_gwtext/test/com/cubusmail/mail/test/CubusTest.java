/* CubusTest.java
 */
package com.cubusmail.mail.test;

import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;


/**
 *
 * @author Juergen Schlierf
 */
public class CubusTest {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		Locale locale = LocaleUtils.toLocale( "de" );
		Locale locale2 = LocaleUtils.toLocale( "de_DE" );
		Locale locale3 = LocaleUtils.toLocale( "en_US" );
		Locale locale4 = LocaleUtils.toLocale( "en_UK" );
	}

}
