/* MessageFlags.java

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
package com.cubusmail.common.model;

import java.io.Serializable;

/**
 * Message flags similar to JavaMail flags.
 * 
 * @author Juergen Schlierf
 */
@SuppressWarnings("serial")
public class GWTMessageFlags implements Serializable {

	public static final int READ = 1;
	public static final int UNREAD = 2;
	public static final int DELETED = 4;
	public static final int UNDELETED = 8;
	public static final int DRAFT = 16;
	public static final int ANSWERED = 32;

	private boolean unread;
	private boolean deleted;
	private boolean draft;
	private boolean answered;

	public boolean isUnread() {

		return unread;
	}

	public void setUnread( boolean unread ) {

		this.unread = unread;
	}

	public boolean isDeleted() {

		return deleted;
	}

	public void setDeleted( boolean deleted ) {

		this.deleted = deleted;
	}

	public boolean isDraft() {

		return draft;
	}

	public void setDraft( boolean draft ) {

		this.draft = draft;
	}

	public boolean isAnswered() {

		return answered;
	}

	public void setAnswered( boolean answered ) {

		this.answered = answered;
	}
}
