/* GWTLoginException.java

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
package com.cubusmail.gwtui.client.services.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Exception for login operations.
 * 
 * @author Juergen Schlierf
 */
public class GWTLoginException extends Exception implements IsSerializable {

	private static final long serialVersionUID = 4550425548109772965L;

	/**
	 * 
	 */
	public GWTLoginException() {

	}

	/**
	 * @param msg
	 */
	public GWTLoginException( String msg ) {

		super( msg );
	}

}
