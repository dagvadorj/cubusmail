/* GwtRpcController.java

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

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class GwtRpcController extends RemoteServiceServlet implements Controller, ServletContextAware {

	private ServletContext servletContext;

	private IServiceBase remoteService;

	private Class<?> remoteServiceClass;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {

		super.doPost( request, response );
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.server.rpc.RemoteServiceServlet#processCall(java.
	 * lang.String)
	 */
	@Override
	public String processCall( String payload ) throws SerializationException {

		try {
			this.remoteService.setPerThreadRequest( this.perThreadRequest );
			this.remoteService.setPerThreadResponse( this.perThreadResponse );

			RPCRequest rpcRequest = RPC.decodeRequest( payload, this.remoteServiceClass, this );
			onAfterRequestDeserialized( rpcRequest );
			return RPC.invokeAndEncodeResponse( this.remoteService, rpcRequest.getMethod(), rpcRequest.getParameters(),
					rpcRequest.getSerializationPolicy(), rpcRequest.getFlags() );
		}
		catch (IncompatibleRemoteServiceException ex) {
			log( "An IncompatibleRemoteServiceException was thrown while processing this call.", ex );
			return RPC.encodeResponseForFailure( null, ex );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {

		return this.servletContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.context.ServletContextAware#setServletContext
	 * (javax.servlet.ServletContext)
	 */
	public void setServletContext( ServletContext servletContext ) {

		this.servletContext = servletContext;
	}

	/**
	 * @param remoteService
	 */
	public void setRemoteService( IServiceBase remoteService ) {

		this.remoteService = remoteService;
		this.remoteServiceClass = this.remoteService.getClass();
	}
}
