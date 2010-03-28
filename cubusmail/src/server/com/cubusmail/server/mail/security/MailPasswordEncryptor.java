/* MailPasswordEncryptor.java

   Copyright (c) 2009 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.server.mail.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Encrypt und decrypt and passwords for mailboxes. 
 * 
 * @author Juergen Schlierf
 */
public class MailPasswordEncryptor implements IMailPasswordEncryptor {

	private final Log log = LogFactory.getLog( getClass() );

	private String algorithm;

	private KeyPair keyPair;

	/**
	 * 
	 */
	public void init() {

		try {
			if ( log.isDebugEnabled() ) {
				log.debug( "Create instance of KeyPair..." );
			}
			this.keyPair = KeyPairGenerator.getInstance( this.algorithm ).generateKeyPair();
			if ( log.isDebugEnabled() ) {
				log.debug( "...finish" );
			}
		}
		catch ( NoSuchAlgorithmException e ) {
			log.error( e.getMessage(), e );
			throw new IllegalStateException( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.mail.security.IMailPasswordEncryptor#encryptPassowrd(java
	 * .lang.String)
	 */
	public byte[] encryptPassowrd( String password ) {

		Cipher cipher;
		try {
			if ( log.isDebugEnabled() ) {
				log.debug( "encrypt..." );
			}
			cipher = Cipher.getInstance( this.algorithm );
			cipher.init( Cipher.ENCRYPT_MODE, this.keyPair.getPublic() );

			ByteArrayOutputStream baosEncryptedData = new ByteArrayOutputStream();
			CipherOutputStream cos = new CipherOutputStream( baosEncryptedData, cipher );

			cos.write( password.getBytes( "UTF-8" ) );
			cos.flush();
			cos.close();

			if ( log.isDebugEnabled() ) {
				log.debug( "...finish" );
			}
			
			return baosEncryptedData.toByteArray();
		}
		catch ( Exception e ) {
			log.error( e.getMessage(), e );
			throw new IllegalStateException( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.mail.security.IMailPasswordEncryptor#decryptPassword(byte
	 * [])
	 */
	public String decryptPassword( byte[] encryptedPassword ) {

		Cipher cipher;
		try {
			if ( log.isDebugEnabled() ) {
				log.debug( "decrypt..." );
			}
			cipher = Cipher.getInstance( this.algorithm );
			cipher.init( Cipher.DECRYPT_MODE, this.keyPair.getPrivate() );

			CipherInputStream cis = new CipherInputStream( new ByteArrayInputStream( encryptedPassword ), cipher );
			ByteArrayOutputStream baosDecryptedData = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int len = 0;
			while ( (len = cis.read( buffer )) > 0 ) {
				baosDecryptedData.write( buffer, 0, len );
			}
			baosDecryptedData.flush();
			cis.close();

			if ( log.isDebugEnabled() ) {
				log.debug( "...finish" );
			}

			return new String( baosDecryptedData.toByteArray() );
		}
		catch ( Exception e ) {
			log.error( e.getMessage(), e );
			throw new IllegalStateException( e.getMessage(), e );
		}
	}

	/**
	 * @param keyPair The keyPair to set.
	 */
	public void setKeyPair( KeyPair keyPair ) {

		this.keyPair = keyPair;
	}

	/**
	 * @param algorithm The algorithm to set.
	 */
	public void setAlgorithm( String algorithm ) {

		this.algorithm = algorithm;
	}
}
