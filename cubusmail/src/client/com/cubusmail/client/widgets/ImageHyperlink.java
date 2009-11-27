/* ImageHyperlink.java

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
package com.cubusmail.client.widgets;

import com.cubusmail.client.util.GWTUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;

/**
 * Image hyperlink implementation with right and left button handlers.
 * 
 * @author Juergen Schlierf
 */
public class ImageHyperlink extends Hyperlink {

	public ImageHyperlink() {

		this( null );
	}

	public ImageHyperlink( Image img ) {

		this( img, "" );
	}

	public ImageHyperlink( Image img, String targetHistoryToken ) {

		super();
		setStyleName( "gwt-Hyperlink" );
		if ( img != null ) {
			DOM.insertChild( getElement(), img.getElement(), 0 );
			img.unsinkEvents( Event.ONCLICK | Event.MOUSEEVENTS );
		}
		setTargetHistoryToken( targetHistoryToken );

		sinkEvents( Event.ONCLICK | Event.ONMOUSEDOWN );
		GWTUtil.disableContextMenu( getElement() );
	}

	public void addLeftButtonHandler( MouseDownHandler handler ) {

		addHandler( handler, MouseDownEvent.getType() );
	}

	public void addRightButtonHandler( RightMouseDownHandler handler ) {

		addHandler( handler, RightMouseDownEvent.getType() );
	}

	public void onBrowserEvent( Event event ) {

		super.onBrowserEvent( event );

		switch (DOM.eventGetType( event )) {
		case Event.ONMOUSEDOWN: {
			if ( Event.BUTTON_LEFT == event.getButton() ) {
				fireEvent( new MouseDownEvent( event ) );
			}
			else if ( Event.BUTTON_RIGHT == event.getButton() ) {
				fireEvent( new RightMouseDownEvent( event ) );
			}
		}
		}
	}
}
