/* ServletUtil.java

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
package com.cubusmail.core;

import javax.servlet.http.HttpServletRequest;

import com.cubusmail.gwtui.domain.Preferences;
import com.cubusmail.mail.SessionManager;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public abstract class ServletUtil {

	public static final String getDefaultLocale( HttpServletRequest request ) {

		if ( SessionManager.isLoggedIn() ) {
			Preferences prefs = SessionManager.get().getPreferences();
			return prefs.getLanguage();
		}
		else {
			return request.getLocale().toString();
		}
	}

	public static final String getCSS() {

		if ( SessionManager.isLoggedIn() ) {
			Preferences prefs = SessionManager.get().getPreferences();
			return prefs.getTheme();
		}
		else {
			return "";
		}
	}
}
