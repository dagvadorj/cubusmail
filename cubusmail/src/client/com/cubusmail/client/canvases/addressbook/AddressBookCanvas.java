/* AddressBookCanvas.java

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
package com.cubusmail.client.canvases.addressbook;

import com.cubusmail.client.canvases.CanvasRegistry;
import com.cubusmail.client.canvases.IWorkbenchCanvas;
import com.cubusmail.client.toolbars.ToolbarRegistry;
import com.cubusmail.client.util.GWTSessionManager;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressBookCanvas extends HLayout implements IWorkbenchCanvas {

	public AddressBookCanvas() {

		super();
		setBackgroundImage( "[SKIN]/shared/background.gif" );
		setWidth100();
		setHeight100();
		setBorder( "0px" );

		CanvasRegistry.ADDRESS_FOLDER_CANVAS.get().setWidth( 200 );
		addMember( CanvasRegistry.ADDRESS_FOLDER_CANVAS.get() );

		VLayout addressListLayout = new VLayout();
		addressListLayout.addMember( ToolbarRegistry.ADDRESS_FILTER.get() );

		HLayout addressHLayout = new HLayout();
		CanvasRegistry.ADDRESS_LIST.get().setWidth( "220px" );
		addressHLayout.addMember( CanvasRegistry.ADDRESS_LIST.get() );
		addressHLayout.addMember( CanvasRegistry.ADDRESS_DETAILS.get() );
		CanvasRegistry.ADDRESS_EDIT.get().setVisible( false );
		addressHLayout.addMember( CanvasRegistry.ADDRESS_EDIT.get() );
		addressListLayout.addMember( addressHLayout );

		CanvasRegistry.ADDRESS_DETAILS.get( AddressDetailsCanvas.class )
				.addEditButtonHandler( new EditAddressHandler() );

		addMember( addressListLayout );
	}

	private class EditAddressHandler implements ClickHandler {

		@Override
		public void onClick( ClickEvent event ) {

			CanvasRegistry.ADDRESS_DETAILS.get().setVisible( false );
			CanvasRegistry.ADDRESS_EDIT.get( AddressEditCanvas.class ).setAddress(
					GWTSessionManager.get().getCurrentAddress() );
			CanvasRegistry.ADDRESS_EDIT.get().setVisible( true );
		}
	}
}
