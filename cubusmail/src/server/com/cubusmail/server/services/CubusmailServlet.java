/* CubusmailServlet.java

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Startup servlet for cubusmail. It replaces default values in cubusmail.html. 
 * JSPs are not necessary.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class CubusmailServlet extends HttpServlet {

	private Log log = LogFactory.getLog( this.getClass() );

	@Override
	public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			IOException {

		String fileName = request.getSession().getServletContext().getRealPath( request.getServletPath() );
		BufferedReader reader = new BufferedReader( new FileReader( fileName ) );

		OutputStream outputStream = response.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter( outputStream );
		char[] inBuf = new char[1024];
		int len = 0;
		try {
			while ((len = reader.read( inBuf )) > 0) {
				writer.write( inBuf, 0, len );
			}
		}
		catch (Throwable e) {
			log.error( e.getMessage(), e );
		}

		writer.flush();
		writer.close();
		outputStream.flush();
		outputStream.close();
	}
}
