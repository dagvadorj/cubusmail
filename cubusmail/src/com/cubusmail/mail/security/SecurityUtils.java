/* SecurityUtils.java

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
package com.cubusmail.mail.security;

import java.util.Set;

import javax.security.auth.Subject;

/**
 * Util class for security functions.
 * 
 * @author Jürgen Schlierf
 */
public class SecurityUtils {


	public static MailboxPrincipal getMailboxPrincipal( Subject subject ) {

		Set<MailboxPrincipal> principals = subject.getPrincipals( MailboxPrincipal.class );
		if ( principals != null && principals.size() > 0 ) {
			MailboxPrincipal principal = principals.iterator().next();
			return principal;
		}

		return null;
	}
}
