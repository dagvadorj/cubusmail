/* IGWTAction.java

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

import com.google.gwt.user.client.Command;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.event.ButtonListener;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.menu.event.BaseItemListener;

/**
 * @author schlierf
 * 
 */
public interface IGWTAction extends ButtonListener, Command, BaseItemListener, KeyListener {

	public String getText();

	public String getTooltipText();

	public String getImageName();

	public void registerComponent( Component component );

	public void unregisterComponent( Component component );
	
	public void setDisabled( boolean disabled );	
}
