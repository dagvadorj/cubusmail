/* AbstractGwtService.java

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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public abstract class AbstractGwtService extends RemoteServiceServlet implements Controller, ApplicationContextAware,
		ServletContextAware {

	private ApplicationContext applicationContext;

	private ServletContext servletContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {

		super.doPost( request, response );

		return null;
	}

	@Override
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {

		this.applicationContext = applicationContext;
	}

	protected ApplicationContext getApplicationContext() {

		return applicationContext;
	}

	@Override
	public void setServletContext( ServletContext servletContext ) {

		this.servletContext = servletContext;
	}

	@Override
	public ServletContext getServletContext() {

		return this.servletContext;
	}
}
