/* CubusConstants.java

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
package com.cubusmail.core;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Globale constants for cubusmail application.
 * 
 * @author Juergen Schlierf
 */
public abstract class CubusConstants {

	public static final String APPLICATION_NAME = "Cubusmail";
	public static final String CUBUS_CONFIG_PROPERTY = "cubus.config";

	public static final String JAAS_PROPERTY_NANE = "java.security.auth.login.config";

	public static final String LOGIN_MODULE_CONFIG_FILE = "loginmodule.config";

	// resource bundle names
	public final static String TIMEZONES_BUNDLE_NAME = "timezones";
	public final static String GLOBAL_BUNDLE_NAME = "global";

	// global properties
	public static final String MESSAGELIST_DATE_FORMAT_PATTERN = "messagelist.date.format.pattern";
	public static final String MESSAGELIST_TIME_FORMAT_PATTERN = "messagelist.time.format.pattern";

	// Priority
	public final static int PRIORITY_NONE = 0;

	public final static String FETCH_ITEM_PRIORITY = "X-Priority";

	public static final double MESSAGE_SIZE_FACTOR = 0.74;

	public static final String LOG4J_PROPERTIES = "log4j.properties";
	
	// charsetts
	public static final String DEFAULT_CHARSET = "ISO-8859-1";
	public static final String US_ASCII = "us-ascii";
	

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties log4jProperties = new Properties();

		try {
			log4jProperties.load( classLoader.getResourceAsStream( LOG4J_PROPERTIES ) );
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}

		PropertyConfigurator.configure( log4jProperties );
	}
}
