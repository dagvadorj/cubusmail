/* CanvasRegistry.java

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
package com.cubusmail.client.canvases;

import java.util.HashMap;
import java.util.Map;

import com.cubusmail.client.canvases.addressbook.AddressBookCanvas;
import com.cubusmail.client.canvases.addressbook.AddressFolderCanvas;
import com.cubusmail.client.canvases.mail.MailCanvas;
import com.cubusmail.client.canvases.mail.MailfolderCanvas;
import com.cubusmail.client.canvases.mail.MessageListCanvas;
import com.cubusmail.client.canvases.mail.MessageReadingPaneCanvas;
import com.smartgwt.client.widgets.Canvas;

/**
 * Registry for all important canvases in Cubusmail.
 * 
 * @author Juergen Schlierf
 */
public enum CanvasRegistry {
	MAIL_CANVAS, MAIL_FOLDER_CANVAS, MESSAGE_LIST_CANVAS, MESSAGE_READING_PANE,

	ADDRESS_BOOK_CANVAS, ADDRESS_FOLDER_CANVAS;

	private final static Map<CanvasRegistry, Canvas> CANVAS_MAP = new HashMap<CanvasRegistry, Canvas>();

	public Canvas get() {

		Canvas result = CANVAS_MAP.get( this );
		if ( result == null ) {
			result = create();
			result.setID( this.name() );
			CANVAS_MAP.put( this, result );
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends Canvas> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}

	/**
	 * @return
	 */
	private Canvas create() {

		switch (this) {
		case MAIL_CANVAS:
			return new MailCanvas();
		case MAIL_FOLDER_CANVAS:
			return new MailfolderCanvas();
		case MESSAGE_LIST_CANVAS:
			return new MessageListCanvas();
		case MESSAGE_READING_PANE:
			return new MessageReadingPaneCanvas();

		case ADDRESS_BOOK_CANVAS:
			return new AddressBookCanvas();
		case ADDRESS_FOLDER_CANVAS:
			return new AddressFolderCanvas();
		}

		throw new IllegalArgumentException( "Canvas missing: " + name() );
	}
}
