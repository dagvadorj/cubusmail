/* GWTConstants.java

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

/**
 * Global constants.
 * 
 * @author Juergen Schlierf
 */
public interface GWTConstants {

	// constants for mails
	public final static int PRIORITY_NONE = 0;
	public final static int PRIORITY_VERY_HIGH = 1;
	public final static int PRIORITY_HIGH = 2;
	public final static int PRIORITY_NORMAL = 3;
	public final static int PRIORITY_LOW = 4;
	public final static int PRIORITY_VERY_LOW = 5;

	public final static String PARAM_FOLDER_ID = "folderId";
	public final static String PARAM_PARENT_FOLDER = "parentFolder";
	public final static String PARAM_SEARCH_FIELDS = "PARAM_SEARCH_FIELDS";
	public final static String PARAM_SEARCH_VALUES = "PARAM_SEARCH_VALUES";

	public final static int MESSAGE_LIST_PAGE_SIZE = 100;

	public final static int MESSAGE_READING_PANE_LABEL_WIDTH = 50;
	
	// Address folders and list
	public final static String ADDRESS_FOLDER = "addressFolder";
	public final static String ADDRESS_FOLDER_ID = "addressFolder";
	public final static String ADDRESS_BEGIN_CHARS = "addressBeginChars";
	
	// address detials
	public final static int ADDRESS_TITLE_WIDTH = 120;
}
