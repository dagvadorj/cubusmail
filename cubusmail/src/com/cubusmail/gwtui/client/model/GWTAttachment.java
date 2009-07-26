/* GWTAttachment.java

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
 * client side attachment representation.
 * 
 * @author Jürgen Schlierf
 */
public class GWTAttachment implements IsSerializable {

	private String fileName;
	private int size;
	private String sizeText;
	private boolean preview = false;
	private long messageId;
	private int index;

	/**
	 * 
	 */
	public GWTAttachment() {

	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {

		return this.fileName;
	}

	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName( String fileName ) {

		this.fileName = fileName;
	}

	/**
	 * @return Returns the size.
	 */
	public int getSize() {

		return this.size;
	}

	/**
	 * @param size The size to set.
	 */
	public void setSize( int size ) {

		this.size = size;
	}

	/**
	 * @return Returns the preview.
	 */
	public boolean isPreview() {

		return this.preview;
	}

	/**
	 * @param preview The preview to set.
	 */
	public void setPreview( boolean preview ) {

		this.preview = preview;
	}

	public String getSizeText() {

		return this.sizeText;
	}

	public void setSizeText( String sizeText ) {

		this.sizeText = sizeText;
	}

	public int getIndex() {

		return this.index;
	}

	public void setIndex( int index ) {

		this.index = index;
	}

	
	public long getMessageId() {
	
		return this.messageId;
	}

	
	public void setMessageId( long messageId ) {
	
		this.messageId = messageId;
	}
}
