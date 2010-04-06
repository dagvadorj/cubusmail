/* ServiceBase.java

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
package com.cubusmail.server.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public abstract class ServiceBase implements IServiceBase, ApplicationContextAware {

	protected transient ThreadLocal<HttpServletRequest> perThreadRequest;
	protected transient ThreadLocal<HttpServletResponse> perThreadResponse;

	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}

	protected ApplicationContext getApplicationContext() {

		return applicationContext;
	}

	public void setPerThreadRequest( ThreadLocal<HttpServletRequest> perThreadRequest ) {

		this.perThreadRequest = perThreadRequest;
	}

	public void setPerThreadResponse( ThreadLocal<HttpServletResponse> perThreadResponse ) {

		this.perThreadResponse = perThreadResponse;
	}

	protected ThreadLocal<HttpServletRequest> getPerThreadRequest() {

		return perThreadRequest;
	}

	protected ThreadLocal<HttpServletResponse> getPerThreadResponse() {

		return perThreadResponse;
	}
}
