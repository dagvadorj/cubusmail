/* UIFactory.java

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

import com.cubusmail.client.actions.IGWTAction;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Factory for UI elements.
 * 
 * @author Juergen Schlierf
 */
public abstract class UIFactory {

	/**
	 * Creates buttons.
	 * 
	 * @param action
	 * @return
	 */
	public static Button createButton( final IGWTAction action ) {

		Button button = new Button();
		if ( action.getText() != null ) {
			button.setTitle( action.getText() );
		}
		if ( action.getImageName() != null ) {
			button.setIcon( action.getImageName() );
		}
		if ( action.getTooltipText() != null ) {
			button.setTooltip( action.getTooltipText() );
		}

		button.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				action.execute();
			}
		} );
		return button;
	}
}
