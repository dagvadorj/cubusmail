/* GWTMessageRecord.java

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
package com.cubusmail.common.model;

import java.io.Serializable;

/**
 * POJO für message record representing a message in a grid.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class GWTMessageRecord implements Serializable {

	private String id;

	/**
	 * Flags can change on client side. So the flag images are rendered on
	 * client.
	 */
	private GWTMessageFlags flags;
	private String attachmentImage;
	private String prorityImage;
	private String subject;
	private String from;
	private String to;
	private String sendDateString;
	private String sizeString;

	public String getSubject() {

		return subject;
	}

	public void setSubject( String subject ) {

		this.subject = subject;
	}

	public String getFrom() {

		return from;
	}

	public void setFrom( String from ) {

		this.from = from;
	}

	public String getTo() {

		return to;
	}

	public void setTo( String to ) {

		this.to = to;
	}

	public String getSendDateString() {

		return sendDateString;
	}

	public void setSendDateString( String sendDateString ) {

		this.sendDateString = sendDateString;
	}

	public String getSizeString() {

		return sizeString;
	}

	public void setSizeString( String sizeString ) {

		this.sizeString = sizeString;
	}

	public String getId() {

		return id;
	}

	public void setId( String id ) {

		this.id = id;
	}

	public String getAttachmentImage() {

		return attachmentImage;
	}

	public void setAttachmentImage( String attachmentImage ) {

		this.attachmentImage = attachmentImage;
	}

	public void setProrityImage( String prorityImage ) {

		this.prorityImage = prorityImage;
	}

	public String getProrityImage() {

		return prorityImage;
	}

	public GWTMessageFlags getFlags() {

		return flags;
	}

	public void setFlags( GWTMessageFlags flags ) {

		this.flags = flags;
	}
}
