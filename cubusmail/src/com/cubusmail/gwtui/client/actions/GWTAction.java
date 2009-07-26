/* GWTAction.java

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

package com.cubusmail.gwtui.client.actions;

import java.util.ArrayList;
import java.util.List;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Menu;

/**
 * Base for all cubusmail actions.
 * 
 * @author schlierf
 */
public abstract class GWTAction extends ComponentListenerAdapter implements IGWTAction {

	private String text;
	private String imageName;
	private String tooltipText;
	private boolean disabled;

	private List<Component> componentList = new ArrayList<Component>();

	public String getText() {

		return text;
	}

	public void setText( String text ) {

		this.text = text;
	}

	public String getImageName() {

		return imageName;
	}

	public void setImageName( String imageName ) {

		this.imageName = imageName;
	}

	public String getTooltipText() {

		return tooltipText;
	}

	protected void setTooltipText( String tooltipText ) {

		this.tooltipText = tooltipText;
	}

	public void registerComponent( Component component ) {

		this.componentList.add( component );
	}

	public void unregisterComponent( Component component ) {

		this.componentList.remove( component );
	}

	public void setDisabled( boolean disabled ) {

		if ( this.disabled != disabled ) {
			for ( int i = 0; i < this.componentList.size(); i++ ) {
				Component comp = (Component) this.componentList.get( i );
				comp.setDisabled( disabled );
			}
		}

		this.disabled = disabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	public void execute() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onClick(com.gwtext.client
	 * .widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onClick( Button button, EventObject e ) {

		execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onMenuHide(com.gwtext.
	 * client.widgets.Button, com.gwtext.client.widgets.menu.Menu)
	 */
	public void onMenuHide( Button button, Menu menu ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onMenuShow(com.gwtext.
	 * client.widgets.Button, com.gwtext.client.widgets.menu.Menu)
	 */
	public void onMenuShow( Button button, Menu menu ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onMenuTriggerOut(com.gwtext
	 * .client.widgets.Button, com.gwtext.client.widgets.menu.Menu,
	 * com.gwtext.client.core.EventObject)
	 */
	public void onMenuTriggerOut( Button button, Menu menu, EventObject e ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onMenuTriggerOver(com.
	 * gwtext.client.widgets.Button, com.gwtext.client.widgets.menu.Menu,
	 * com.gwtext.client.core.EventObject)
	 */
	public void onMenuTriggerOver( Button button, Menu menu, EventObject e ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onMouseOut(com.gwtext.
	 * client.widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onMouseOut( Button button, EventObject e ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onMouseOver(com.gwtext
	 * .client.widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onMouseOver( Button button, EventObject e ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.event.ButtonListener#onToggle(com.gwtext.client
	 * .widgets.Button, boolean)
	 */
	public void onToggle( Button button, boolean pressed ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.menu.event.BaseItemListener#onActivate(com.
	 * gwtext.client.widgets.menu.BaseItem)
	 */
	public void onActivate( BaseItem item ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.menu.event.BaseItemListener#onClick(com.gwtext
	 * .client.widgets.menu.BaseItem, com.gwtext.client.core.EventObject)
	 */
	public void onClick( BaseItem item, EventObject e ) {

		execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtext.client.widgets.menu.event.BaseItemListener#onDeactivate(com
	 * .gwtext.client.widgets.menu.BaseItem)
	 */
	public void onDeactivate( BaseItem item ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.widgets.event.KeyListener#onKey(int,
	 * com.gwtext.client.core.EventObject)
	 */
	public void onKey( int key, EventObject e ) {

		execute();
	}
}
