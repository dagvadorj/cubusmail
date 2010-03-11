/* SuggestBoxItem.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
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

import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.CanvasItem;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class SuggestBoxItem extends CanvasItem {

	public static final int HEIGHT = 20;
	private Canvas canvas = new Canvas();
	private SuggestBox suggestBoxField;

	public SuggestBoxItem( String s, String s1, SuggestOracle suggestOracle ) {

		super( s, s1 );

		suggestBoxField = new SuggestBox( suggestOracle );
		suggestBoxField.setWidth( "100%" );

		suggestBoxField.setStyleName( "gwt-SuggestBox" );
		suggestBoxField.setHeight( getHeight() + "px" );

		canvas.setHeight( getHeight() );
		canvas.setStyleName( "gwt-SuggestBoxCanvas" );
		canvas.addChild( suggestBoxField );

		setCanvas( canvas );
	}

	public Canvas getCanvas() {

		return canvas;
	}

	public int getHeight() {

		return HEIGHT;
	}
}
