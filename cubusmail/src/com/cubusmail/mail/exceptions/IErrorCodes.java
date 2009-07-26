/* IErrorCodes.java

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
package com.cubusmail.mail.exceptions;

/**
 * Detailed error codes for mail exceptions.
 * 
 * @author Jürgen Schlierf
 */
public interface IErrorCodes {

	public static final String EXCEPTION_FOLDER_ALREADY_EXIST = "exception_folder_already_exist";
	
	public static final String EXCEPTION_FOLDER_CREATE = "exception_folder_create";
	
	public static final String EXCEPTION_FOLDER_MOVE = "exception_folder_move";

	public static final String EXCEPTION_FOLDER_RENAME = "exception_folder_rename";

	public static final String EXCEPTION_FOLDER_DELETE = "exception_folder_delete";

	public static final String EXCEPTION_FOLDER_EMPTY = "exception_folder_empty";

	public static final String EXCEPTION_WRONG_ADDRESS = "exception_wrong_address";

	public static final String EXCEPTION_SEND_MESSAGE = "exception_send_message";

	public static final String EXCEPTION_AUTHENTICATION_FAILED = "exception_authentication_failed";

	public static final String EXCEPTION_CONNECT = "exception_connect_exception";
	
	public static final String EXCEPTION_GENERAL = "exception_general";
}
