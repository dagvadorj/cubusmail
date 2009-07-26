/* GWTAddress.java

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
package com.cubusmail.gwtui.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * client side email address representation.
 * 
 * @author Jürgen Schlierf
 */
public class GWTAddress implements IsSerializable {

	private String name;
	private String email;
	private String internetAddress;

	public String getName() {

		return this.name;
	}

	public void setName( String name ) {

		this.name = name;
	}

	public String getEmail() {

		return this.email;
	}

	public void setEmail( String email ) {

		this.email = email;
	}

	public String getInternetAddress() {

		return this.internetAddress;
	}

	public void setInternetAddress( String internetAddress ) {

		this.internetAddress = internetAddress;
	}
}
