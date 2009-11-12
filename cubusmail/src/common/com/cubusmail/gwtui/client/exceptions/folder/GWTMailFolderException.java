/* GWTMailFolderException.java

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
package com.cubusmail.gwtui.client.exceptions.folder;


/**
 * General exception for all mailfoler operations.
 * 
 * @author Juergen Schlierf
 */
public class GWTMailFolderException extends Exception {

	private static final long serialVersionUID = -8451960274387257938L;

	private String folderId;

	private String folderName;

	/**
	 * 
	 */
	public GWTMailFolderException() {

	}

	/**
	 * @param folderId
	 * @param folderName
	 */
	public GWTMailFolderException( String folderId, String folderName ) {

		super();
		this.folderId = folderId;
		this.folderName = folderName;
	}

	/**
	 * @return Returns the folderId.
	 */
	public String getFolderId() {

		return this.folderId;
	}

	/**
	 * @param folderId The folderId to set.
	 */
	public void setFolderId( String folderId ) {

		this.folderId = folderId;
	}

	/**
	 * @return Returns the folderName.
	 */
	public String getFolderName() {

		return this.folderName;
	}

	/**
	 * @param folderName The folderName to set.
	 */
	public void setFolderName( String folderName ) {

		this.folderName = folderName;
	}

}
