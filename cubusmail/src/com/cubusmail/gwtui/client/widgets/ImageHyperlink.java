/* ImageHyperlink.java

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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;

import com.cubusmail.gwtui.client.util.GWTUtil;

/**
 * Image hyperlink implementation with right and left button listener.
 * 
 * @author Jürgen Schlierf
 */
public class ImageHyperlink extends Hyperlink {

	private MouseListenerCollection leftButtonListeners;
	private MouseListenerCollection rightButtonListeners;

	public ImageHyperlink() {

		this( null );
	}

	public ImageHyperlink( Image img ) {

		this( img, "" );
	}

	public ImageHyperlink( Image img, String targetHistoryToken ) {

		super();
	    setStyleName("gwt-Hyperlink");
		if ( img != null ) {
			DOM.insertChild( getElement(), img.getElement(), 0 );
			img.unsinkEvents( Event.ONCLICK | Event.MOUSEEVENTS );
		}
		setTargetHistoryToken( targetHistoryToken );

		sinkEvents( Event.ONCLICK | Event.ONMOUSEDOWN );
		GWTUtil.disableContextMenu( getElement() );
	}

	public void addLeftButtonListener( MouseListener listener ) {

		if ( leftButtonListeners == null )
			leftButtonListeners = new MouseListenerCollection();
		leftButtonListeners.add( listener );
	}

	public void removeLeftButtonListener( MouseListener listener ) {

		if ( leftButtonListeners != null )
			leftButtonListeners.remove( listener );
	}

	public void addRightButtonListener( MouseListener listener ) {

		if ( rightButtonListeners == null )
			rightButtonListeners = new MouseListenerCollection();
		rightButtonListeners.add( listener );
	}

	public void removeRightButtonListener( MouseListener listener ) {

		if ( rightButtonListeners != null )
			rightButtonListeners.remove( listener );
	}

	public void onBrowserEvent( Event event ) {

		super.onBrowserEvent( event );

		switch ( DOM.eventGetType( event ) )
		{
			case Event.ONMOUSEDOWN:
			{
				if ( Event.BUTTON_LEFT == event.getButton() ) {
					if ( leftButtonListeners != null )
						leftButtonListeners.fireMouseEvent( this, event );
					break;
				} else if ( Event.BUTTON_RIGHT == event.getButton() ) {
					if ( rightButtonListeners != null )
						rightButtonListeners.fireMouseEvent( this, event );
					break;
				}
			}
		}
	}

}
