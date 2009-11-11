/* ComposeAttachmentPanel.java

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
package com.cubusmail.gwtui.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;

import com.cubusmail.gwtui.client.actions.message.RemoveAttachmentAction;
import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.util.UIFactory;
import com.cubusmail.gwtui.client.widgets.ImageHyperlink;
import com.cubusmail.gwtui.client.windows.ComposeMessageWindow;

/**
 * mail attachment panel for compose window.
 * 
 * @author Juergen Schlierf
 */
public class ComposeAttachmentPanel extends Composite {

	private Menu contextMenu;
	private MenuItem removeItem;

	private RemoveAttachmentAction removeAttachmentAction;

	/**
	 * 
	 */
	public ComposeAttachmentPanel( GWTAttachment attachment, ComposeMessageWindow view ) {

		FlowPanel panel = new FlowPanel();
		initWidget( panel );
		DOM.setStyleAttribute( panel.getElement(), "whiteSpace", "nowrap" );
		DOM.setStyleAttribute( getElement(), "whiteSpace", "nowrap" );

		ImageHyperlink link = new ImageHyperlink( new Image( GWT.getHostPageBaseURL() + ImageProvider.MSG_ATTACHMENT ) );
		link.addRightButtonListener( new MainLinkRightButtonListener() );
		panel.add( link );

		ImageHyperlink link2 = new ImageHyperlink();
		link2.setText( attachment.getFileName() );
		link2.addRightButtonListener( new MainLinkRightButtonListener() );
		panel.add( link2 );
		
		panel.add( new HTML( "&nbsp;(" + attachment.getSizeText() + ",&nbsp;" ) );
		ImageHyperlink previewLink = new ImageHyperlink();
		previewLink.addLeftButtonListener( new RemoveLinkLeftButtonListener() );
		previewLink.setText(  TextProvider.get().common_remove_text() );
		panel.add( previewLink );
		panel.add( new HTML( ")&nbsp;&nbsp;&nbsp;&nbsp;" ) );

		this.removeAttachmentAction = new RemoveAttachmentAction( attachment, view );
		this.contextMenu = new Menu();
		this.removeItem = UIFactory.createMenuItem( this.removeAttachmentAction );
		this.contextMenu.addItem( this.removeItem );
	}

	private class MainLinkRightButtonListener extends MouseListenerAdapter {

		@Override
		public void onMouseDown( Widget sender, int x, int y ) {

			contextMenu.showAt( sender.getAbsoluteLeft() + x + 10, sender.getAbsoluteTop() + y );
		}
	}

	private class RemoveLinkLeftButtonListener extends MouseListenerAdapter {

		@Override
		public void onMouseDown( Widget sender, int x, int y ) {

			removeAttachmentAction.execute();
		}
	}
}
