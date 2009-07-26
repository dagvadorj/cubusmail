/* ShowMessageWindow.java

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
package com.cubusmail.gwtui.client.windows;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.layout.FitLayout;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.panels.MessageReadingPanePanel;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.panels.ToolbarManager;

/**
 * Message reading pane in a window.
 * 
 * @author Jürgen Schlierf
 */
public class ShowMessageWindow extends Window implements IGWTWindow {

	private MessageReadingPanePanel messageReadingPanePanel;

	/**
	 * 
	 */
	ShowMessageWindow() {

		super( "", false, true );
		setClosable( true );
		setMaximizable( true );
		setCollapsible( true );
		setLayout( new FitLayout() );
		setTopToolbar( ToolbarManager.get().getOpenMessageToolbar() );
		setCloseAction( Window.HIDE );

		this.messageReadingPanePanel = (MessageReadingPanePanel) PanelRegistry.MESSAGE_READING_PANE_FOR_WINDOW.get();
		add( this.messageReadingPanePanel );

		EventBroker.get().removeMessageSelectedListener( this.messageReadingPanePanel );
	}

	/**
	 * 
	 */
	public void init() {

		calculateSize();
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		setTitle( message.getSubject() );
		this.messageReadingPanePanel.setMessage( message );
	}

	/**
	 * Set the proper window size and position.
	 */
	private void calculateSize() {

		Panel panel = PanelRegistry.LIST_DETAILS_PANEL.get();
		int posx = panel.getAbsoluteLeft();
		int posy = panel.getAbsoluteTop();
		int width = panel.getWidth();
		int height = panel.getHeight();
		setPosition( posx, posy );
		setWidth( width );
		setHeight( height );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.windows.IGWTWindow#validate()
	 */
	public boolean validate() {

		return true;
	}
}
