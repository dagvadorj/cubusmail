/* CubusImgButton.java

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

import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseUpEvent;
import com.smartgwt.client.widgets.events.MouseUpHandler;

/**
 * ImgButton which supports disabled styles.
 * 
 * @author Juergen Schlierf
 */
public class CubusImgButton extends ImgButton {

	public CubusImgButton() {

		super();
		addMouseOutHandler( new MouseOutHandler() {
			
			public void onMouseOut( MouseOutEvent event ) {
				setBorder( "" );
			}
		});
		addMouseUpHandler( new MouseUpHandler() {

			public void onMouseUp( MouseUpEvent event ) {

				setBorder( "" );
			}
		} );
		addMouseDownHandler( new MouseDownHandler() {

			public void onMouseDown( MouseDownEvent event ) {

				if ( event.isLeftButtonDown() ) {
					setBorder( "1px solid #b8cfef" );
				}
			}

		} );
	}

	@Override
	public void setDisabled( boolean disabled ) {

		if ( disabled ) {
			setOpacity( 70 );
		}
		else {
			setOpacity( 100 );
		}
		super.setDisabled( disabled );
	}
}
