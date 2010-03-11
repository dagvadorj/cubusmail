/* AttachmentLine.java

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
package com.cubusmail.client.canvases.mail;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.client.widgets.AttachmentWidget;
import com.cubusmail.common.model.GWTAttachment;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;

/**
 * FlowPanel which contains the attachments in the reading pane.
 * 
 * @author Juergen Schlierf
 */
public class AttachmentLine extends FlowPanel {

	private Menu contextMenu;

	public AttachmentLine() {

		super();
		this.contextMenu = new AttachmentContextMenu();		
	}

	public void setAttachments( GWTAttachment[] attachments ) {

		clear();
		for (int i = 0; i < attachments.length; i++) {
			AttachmentWidget widget = new AttachmentWidget( attachments[i] );
			widget.setContextMenu( this.contextMenu );
			add( widget );
			if ( i < (attachments.length - 1) ) {
				add( new AttachmentSeparator() );
			}
		}
	}

	private class AttachmentSeparator extends HTML {

		public AttachmentSeparator() {

			// include whitespace for wrapping
			setHTML( " " );
			DOM.setStyleAttribute( getElement(), "rightPadding", "15px" );
		}
	}

	
	private class AttachmentContextMenu extends Menu {

		public AttachmentContextMenu() {

			super();
			addItem( UIFactory.createMenuItem( ActionRegistry.DOWNLOAD_ATTACHMENT ) );
			addItem( UIFactory.createMenuItem( ActionRegistry.VIEW_ATTACHMENT) );

			addItemClickHandler( new ItemClickHandler() {

				public void onItemClick( ItemClickEvent event ) {

					hide();
				}
			} );

			addMouseOutHandler( new MouseOutHandler() {

				public void onMouseOut( MouseOutEvent event ) {

					if ( isVisible() ) {
						hide();
					}
				}
			} );
		}
	}
}
