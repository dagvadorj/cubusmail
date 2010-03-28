/* CubusStartupListener.java

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
package com.cubusmail.server.services;

import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cubusmail.server.util.BeanFactory;
import com.cubusmail.server.util.CubusConstants;

/**
 * Initialize application context and JAAS.
 * 
 * @author Juergen Schlierf
 */
public class CubusStartupListener implements ServletContextListener {

	private final Log log = LogFactory.getLog( getClass() );

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed( ServletContextEvent servletcontextevent ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized( ServletContextEvent servletcontextevent ) {


		try {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext( servletcontextevent.getServletContext() );
			BeanFactory.setContext( context );
		}
		catch ( Throwable e ) {
			log.fatal( e.getMessage(), e );
			throw new IllegalStateException( "Could not load " + CubusConstants.LOGIN_MODULE_CONFIG_FILE );
		}

		try {
			URL test = CubusStartupListener.class.getClassLoader().getResource( CubusConstants.LOGIN_MODULE_CONFIG_FILE );
			System.setProperty( CubusConstants.JAAS_PROPERTY_NANE, test.getFile() );
		}
		catch ( Throwable e ) {
			log.fatal( e.getMessage(), e );
			throw new IllegalStateException( "Could not load " + CubusConstants.LOGIN_MODULE_CONFIG_FILE );
		}
	}
}
