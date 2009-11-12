/* MailFolderException.java

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
package com.cubusmail.server.mail.exceptions;

import com.cubusmail.server.mail.IMailFolder;

/**
 * Server side execptions for mail folder operations.
 * 
 * @author Juergen Schlierf
 */
public class MailFolderException extends Exception {

	private static final long serialVersionUID = 1254290207185224330L;

	private IMailFolder folder;

	private String errorCode;

	/**
	 * @param message
	 * @param cause
	 */
	public MailFolderException( String errorCode, Throwable cause ) {

		super( cause );
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 * @param cause
	 * @param folder
	 */
	public MailFolderException( String errorCode, Throwable cause, IMailFolder folder ) {

		super( cause );
		this.errorCode = errorCode;
		this.folder = folder;
	}

	public IMailFolder getFolder() {

		return folder;
	}

	public void setFolder( IMailFolder folder ) {

		this.folder = folder;
	}

	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode() {

		return this.errorCode;
	}

	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode( String errorCode ) {

		this.errorCode = errorCode;
	}

	public boolean hasErrorCode( String errorCode ) {

		return errorCode.equals( this.errorCode );
	}
}
