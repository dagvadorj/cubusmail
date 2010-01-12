/* ToolbarRegistry.java

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
package com.cubusmail.client.toolbars;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Mananages all Toolbars.
 * 
 * @author Juergen Schlierf
 */
public enum ToolbarRegistry {
	MAIL, ADDRESS_BOOK;

	private static Map<ToolbarRegistry, ToolStrip> TOOLBAR_MAP = new HashMap<ToolbarRegistry, ToolStrip>();

	/**
	 * @return
	 */
	public ToolStrip get() {

		ToolStrip result = TOOLBAR_MAP.get( this );
		if ( result == null ) {
			result = create();
			TOOLBAR_MAP.put( this, result );
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends Canvas> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}
	
	/**
	 * create actions
	 */
	private ToolStrip create() {

		switch (this) {
		case MAIL:
			return new MailToolbar();
		}

		throw new IllegalArgumentException( "Toolbar missing: " + name() );
	}
}
