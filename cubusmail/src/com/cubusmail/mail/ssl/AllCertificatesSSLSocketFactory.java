/* AllCertificatesSSLSocketFactory.java

   Copyright (c) 2009 Jürgen Schlierf, All Rights Reserved
   
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
package com.cubusmail.mail.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Trust all kinds of certificates, including self signed.
 *
 * @author Jürgen Schlierf
 */
public class AllCertificatesSSLSocketFactory extends SSLSocketFactory {

	private SSLSocketFactory factory;

	public AllCertificatesSSLSocketFactory() {

		super();
		try {
			SSLContext context = SSLContext.getInstance( "TLS" );
			context.init( null, new TrustManager[] { new AllCertificatesTrustManager() }, new SecureRandom() );
			factory = context.getSocketFactory();
		}
		catch ( NoSuchAlgorithmException e ) {
			e.printStackTrace();
		}
		catch ( KeyManagementException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public static SocketFactory getDefault() {

		return new AllCertificatesSSLSocketFactory();
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#getDefaultCipherSuites()
	 */
	public String[] getDefaultCipherSuites() {

		return factory.getDefaultCipherSuites();
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#getSupportedCipherSuites()
	 */
	public String[] getSupportedCipherSuites() {

		return factory.getSupportedCipherSuites();
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket()
	 */
	@Override
	public Socket createSocket() throws IOException {

		return factory.createSocket();
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#createSocket(java.net.Socket, java.lang.String, int, boolean)
	 */
	public Socket createSocket( final Socket s, final String host, final int port, final boolean autoClose )
			throws IOException {

		return factory.createSocket( s, host, port, autoClose );
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int)
	 */
	public Socket createSocket( final String host, final int port ) throws IOException, UnknownHostException {

		return factory.createSocket( host, port );
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
	 */
	public Socket createSocket( final String host, final int port, final InetAddress localAddress, final int localPort )
			throws IOException, UnknownHostException {

		return factory.createSocket( host, port, localAddress, localPort );
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int)
	 */
	public Socket createSocket( final InetAddress host, final int port ) throws IOException {

		return factory.createSocket( host, port );
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int, java.net.InetAddress, int)
	 */
	public Socket createSocket( final InetAddress address, final int port, final InetAddress localAddress,
			final int localPort ) throws IOException {

		return factory.createSocket( address, port, localAddress, localPort );
	}

}
