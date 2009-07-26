/* MessageQuickSearchPlugin.java

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
package com.cubusmail.gwtui.client.widgets;

import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtextux.client.widgets.grid.plugins.GridSearchPlugin;

/**
 * Quick search plugin for messages.
 * 
 * @author Jürgen Schlierf
 */
public class MessageQuickSearchPlugin extends GridSearchPlugin {

	public MessageQuickSearchPlugin( Position toolbarPosition ) {

		super( toolbarPosition );
	}

	/**
	 * Empty the search field.
	 * 
	 * @param gridSearchPlugin
	 */
	public native void clearSearchField() /*-{
	    var plugin = this.@com.gwtext.client.core.JsObject::getJsObj()();
	    if (plugin.field) {
			plugin.field.setValue('');
		}
	}-*/;

	/**
	 * Disable the search field.
	 * 
	 * @param gridSearchPlugin
	 */
	public native void disableSearchField( boolean disable ) /*-{
	    var plugin = this.@com.gwtext.client.core.JsObject::getJsObj()();
		plugin.setDisabled(disable);
	}-*/;

	/**
	 * Set the disabled Indixes
	 * 
	 * @param gridSearchPlugin
	 */
	public void setDisableIndexes( String[] disabled ) {

		JavaScriptObjectHelper.setAttribute( configJS, "disableIndexes", disabled );
	};

	/**
	 * Set the Search Tip Text.
	 * 
	 * @param searchTipText
	 */
	public void setSearchTipText( String searchTipText ) {

		JavaScriptObjectHelper.setAttribute( configJS, "searchTipText", searchTipText );
	}
}
