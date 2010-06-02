/* AddressFilterToolbar.java

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
package com.cubusmail.client.toolbars;

import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.util.GWTSessionManager;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressFilterToolbar extends ToolStrip {

	private final static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public AddressFilterToolbar() {

		super();
		setBorder( "0px" );
		IButton button = new IButton( "All" );
		button.setSelected( true );
		button.setWidth( 60 );
		button.setActionType( SelectionType.RADIO );
		button.setRadioGroup( "addressFilter" );
		button.addClickHandler( new ClickHandler() {

			@Override
			public void onClick( ClickEvent event ) {

				GWTSessionManager.get().setCurrentBeginChars( null );
				EventBroker.get().fireReloadAddressList( GWTSessionManager.get().getCurrentAddressFolder(), null );
			}
		} );
		addMember( button );

		button = new IButton( "123" );
		button.setWidth( 35 );
		button.setActionType( SelectionType.RADIO );
		button.setRadioGroup( "addressFilter" );
		button.addClickHandler( new ClickHandler() {

			@Override
			public void onClick( ClickEvent event ) {

				GWTSessionManager.get().setCurrentBeginChars( "0123456789" );
				EventBroker.get().fireReloadAddressList( GWTSessionManager.get().getCurrentAddressFolder(),
						"0123456789" );
			}
		} );
		addMember( button );

		for (int i = 0; i < alpha.length(); i++) {
			final String beginChar = String.valueOf( alpha.charAt( i ) );
			IButton but = new IButton( beginChar );
			but.setWidth( 21 );
			but.setActionType( SelectionType.RADIO );
			but.setRadioGroup( "addressFilter" );
			but.addClickHandler( new ClickHandler() {

				@Override
				public void onClick( ClickEvent event ) {
					
					GWTSessionManager.get().setCurrentBeginChars( beginChar);
					EventBroker.get().fireReloadAddressList( GWTSessionManager.get().getCurrentAddressFolder(),
							beginChar );
				}
			} );
			addMember( but );
		}
	}
}
