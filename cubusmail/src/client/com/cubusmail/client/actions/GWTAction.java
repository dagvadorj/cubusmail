/* GWTAction.java

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

package com.cubusmail.client.actions;


/**
 * Base for all cubusmail actions.
 * 
 * @author schlierf
 */
public abstract class GWTAction  implements IGWTAction {

	private String text;
	private String imageName;
	private String tooltipText;
	private boolean disabled;

	// private List<Component> componentList = new ArrayList<Component>();

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

//	public void registerComponent( Component component ) {
//
//		this.componentList.add( component );
//	}
//
//	public void unregisterComponent( Component component ) {
//
//		this.componentList.remove( component );
//	}

//	public void setDisabled( boolean disabled ) {
//
//		if ( this.disabled != disabled ) {
//			for ( int i = 0; i < this.componentList.size(); i++ ) {
//				Component comp = (Component) this.componentList.get( i );
//				comp.setDisabled( disabled );
//			}
//		}
//
//		this.disabled = disabled;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	abstract public void execute();
}
