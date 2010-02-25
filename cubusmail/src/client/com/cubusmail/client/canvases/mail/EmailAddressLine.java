/* EmailAddressLine.java

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
package com.cubusmail.client.canvases.mail;

import com.cubusmail.client.widgets.EmailAddressLink;
import com.cubusmail.common.model.GWTEmailAddress;
import com.cubusmail.common.model.GWTMailConstants;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;

/**
 * Address line for message reading pane header.
 * 
 * @author Juergen Schlierf
 */
public class EmailAddressLine extends HLayout {

	private Label label;
	private FlowPanel emailAddresses;
	private Menu contextMenu;

	/**
	 * 
	 */
	public EmailAddressLine( String labelText ) {

		super();
		setWidth100();
		setAutoHeight();
		setVisible( false );

		this.label = new Label( labelText );
		this.label.setWidth( GWTMailConstants.MESSAGE_READING_PANE_LABEL_WIDTH );
		this.label.setAutoHeight();

		this.emailAddresses = new FlowPanel();
		this.emailAddresses.setWidth( "100%" );

		addMember( this.label );
		addMember( this.emailAddresses );
	}

	/**
	 * @param addresses
	 */
	public void setAddresses( GWTEmailAddress[] addresses ) {

		this.emailAddresses.clear();

		if ( addresses != null ) {

			for (int i = 0; i < addresses.length; i++) {
				EmailAddressLink link = new EmailAddressLink( addresses[i] );
				this.emailAddresses.add( link );
				if ( this.contextMenu != null ) {
					link.setContextMenu( this.contextMenu );
				}
				if ( i < (addresses.length - 1) ) {
					this.emailAddresses.add( new Separator() );
				}
			}
		}
	}

	private class Separator extends HTML {

		public Separator() {

			// include whitespace for wrapping
			setHTML( ", " );
			DOM.setStyleAttribute( getElement(), "rightPadding", "15px" );
		}
	}

	@Override
	public void setContextMenu( Menu contextMenu ) {

		this.contextMenu = contextMenu;
	}
}
