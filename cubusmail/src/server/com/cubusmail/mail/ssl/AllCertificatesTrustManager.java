/* AllCertificatesTrustManager.java

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
package com.cubusmail.mail.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Trusmanager for all kinds of certificates, eg. self signed.
 * 
 * @author Juergen Schlierf
 */
class AllCertificatesTrustManager implements X509TrustManager {

	AllCertificatesTrustManager() {

	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkClientTrusted( final X509Certificate[] chain, final String authType ) throws CertificateException {

	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkServerTrusted( final X509Certificate[] chain, final String authType ) throws CertificateException {

	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	public X509Certificate[] getAcceptedIssuers() {

		return new X509Certificate[0];
	}

}
