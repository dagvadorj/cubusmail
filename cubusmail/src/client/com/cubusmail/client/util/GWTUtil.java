/* GWTUtil.java

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
package com.cubusmail.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cubusmail.common.model.ContactFolder;
import com.cubusmail.common.model.ContactFolderType;
import com.cubusmail.common.model.IGWTFolder;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.Element;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * Several utils for gwt frontend.
 * 
 * @author Juergen Schlierf
 */
public abstract class GWTUtil {

	public static final String STYLE_BOLD = "style=\"font-weight:bold\"";
	public static final String STYLE_LINE_THROUGH = "style=\"text-decoration:line-through\"";
	public static final String STYLE_BOLD_LINE_THROUGH = "style=\"text-decoration:line-through; font-weight:bold;\"";

	public static final String HTML_AMPERSAND = "&amp;";
	public static final String HTML_LESS_THAN = "&lt;";
	public static final String HTML_GREATER_THAN = "&gt;";
	public static final String HTML_DASH_POINT = "&acute;";

	public static final String GWT_FOLDER = "gwtFolder";

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( TextProvider.get()
			.common_message_date_format() );

	public static String formatDate( Date date, int timeZoneOffset ) {

		TimeZone zone = TimeZone.createTimeZone( timeZoneOffset );
		return DATE_FORMAT.format( date, zone );
	}

	/**
	 * @param text
	 * @return
	 */
	public static boolean hasText( String text ) {

		if ( text != null ) {
			if ( text.trim().length() > 0 ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param array
	 * @return
	 */
	public static boolean isEmpty( Object[] array ) {

		return array == null || array.length == 0;
	}

	/**
	 * Disables the browsers default context menu for the specified element.
	 * 
	 * @param elem
	 *            the element whos context menu will be disabled
	 */
	public static native void disableContextMenu( Element elem ) /*-{
		elem.oncontextmenu=function() {  return false};
	}-*/;

	/**
	 * @param folder
	 * @return
	 */
	public static String getTranslatedFolderName( ContactFolder folder ) {

		if ( ContactFolderType.RECIPIENTS.equals( folder.getFolderType() ) ) {
			return TextProvider.get().contact_folder_panel_recipients();
		}

		return folder.getFolderName();
	}

	/**
	 * Convert certain characters (&, <, >, and ') to their HTML character
	 * equivalents for literal display in web pages.
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlEncode( String html ) {

		if ( hasText( html ) ) {
			String result = html.replace( "&", HTML_AMPERSAND );
			result = result.replace( "<", HTML_GREATER_THAN );
			result = result.replace( ">", HTML_LESS_THAN );
			result = result.replace( "'", HTML_DASH_POINT );
			return result;
		}

		return null;
	}

	/**
	 * @param node
	 * @return
	 */
	public static IGWTFolder getGwtFolder( TreeNode node ) {

		return (IGWTFolder) node.getAttributeAsObject( GWT_FOLDER );
	}

	/**
	 * @param node
	 * @param value
	 */
	public static void setGwtFolder( TreeNode node, IGWTFolder value ) {

		node.setAttribute( GWT_FOLDER, value );
	}

	/**
	 * @param node
	 * @return
	 */
	public static List<String> getChildrenStringList( Tree treeData, TreeNode node ) {

		TreeNode[] children = treeData.getChildren( node );
		if ( children != null ) {
			List<String> nodeNames = new ArrayList<String>();
			for (TreeNode child : children) {
				nodeNames.add( child.getName() );
			}
			return nodeNames;
		}
		return null;
	}
}
