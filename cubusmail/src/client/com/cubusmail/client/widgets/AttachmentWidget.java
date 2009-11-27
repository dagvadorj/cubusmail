/* AttachmentPanel.java

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

import com.cubusmail.client.actions.message.DownloadAttachmentAction;
import com.cubusmail.client.actions.message.ViewAttachmentAction;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.common.model.GWTAttachment;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;

/**
 * Panel for mail attachments.
 * 
 * @author Juergen Schlierf
 */
public class AttachmentWidget extends Composite {

	private Menu contextMenu;

	private DownloadAttachmentAction downloadAttachmentAction;
	private ViewAttachmentAction viewAttachmentAction;

	// Context menu items
	private MenuItem downloadItem;
	private MenuItem viewItem;

	/**
	 * @param title
	 */
	public AttachmentWidget( GWTAttachment attachment ) {

		super();

		FlowPanel panel = new FlowPanel();
		initWidget( panel );
		DOM.setStyleAttribute( panel.getElement(), "whiteSpace", "nowrap" );
		DOM.setStyleAttribute( getElement(), "whiteSpace", "nowrap" );

		ImageHyperlink link = new ImageHyperlink( new Image( GWT.getHostPageBaseURL() + ImageProvider.IMAGE_PREFIX
				+ ImageProvider.MSG_ATTACHMENT ) );
		link.addLeftButtonHandler( new MainLinkLeftButtonListener() );
		link.addRightButtonHandler( new MainLinkRightButtonListener() );
		panel.add( link );

		ImageHyperlink link2 = new ImageHyperlink();
		link2.setText( attachment.getFileName() );
		link2.addLeftButtonHandler( new MainLinkLeftButtonListener() );
		link2.addRightButtonHandler( new MainLinkRightButtonListener() );
		panel.add( link2 );

		panel.add( new HTML( "&nbsp;(" + attachment.getSizeText() + ")&nbsp;" ) );
		// if ( attachment.isPreview() ) {
		// ToolTip tip = new ToolTip();
		// String url = ServiceProvider.getRetrieveImageServletUrl(
		// attachment.getMessageId(), attachment.getIndex(),
		// true );
		// tip.setHtml( "<img src=\"" + url + "\"/>" );
		// tip.setWidth( 300 );
		// tip.setHeight( 200 );
		// tip.applyTo( this.getElement() );
		// }
		ImageHyperlink downloadLink = new ImageHyperlink( new Image( GWT.getHostPageBaseURL()
				+ ImageProvider.IMAGE_PREFIX + ImageProvider.MSG_DOWNLOAD ) );
		downloadLink.addLeftButtonHandler( new DownloadLeftButtonListener() );
		panel.add( downloadLink );
		panel.add( new HTML( "&nbsp;&nbsp;&nbsp;" ) );

		this.downloadAttachmentAction = new DownloadAttachmentAction( attachment );
		this.viewAttachmentAction = new ViewAttachmentAction( attachment );

		this.contextMenu = new Menu();
		this.contextMenu.setWidth( 100 );
		this.downloadItem = UIFactory.createMenuItem( this.downloadAttachmentAction );
		this.viewItem = UIFactory.createMenuItem( this.viewAttachmentAction );
		this.contextMenu.setItems( this.viewItem, this.downloadItem );

		getElement().getStyle().setProperty( "display", "inline" );
	}

	private class MainLinkLeftButtonListener implements MouseDownHandler {

		public void onMouseDown( MouseDownEvent event ) {

			contextMenu.showContextMenu();
			event.cancel();
			// viewAttachmentAction.execute();
		}
	}

	private class MainLinkRightButtonListener implements RightMouseDownHandler {

		public void onRightMouseDown( RightMouseDownEvent event ) {

			contextMenu.showContextMenu();
			// contextMenu.showAt( event..getAbsoluteLeft() + x + 10,
			// sender.getAbsoluteTop() + y );
		}
	}

	private class DownloadLeftButtonListener implements MouseDownHandler {

		public void onMouseDown( MouseDownEvent event ) {

			downloadAttachmentAction.execute();
		}
	}
}
