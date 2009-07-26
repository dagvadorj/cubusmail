/* WorkbenchPanel.java

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
package com.cubusmail.gwtui.client.panels;

import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * Workbench Panel.
 * 
 * @author Jürgen Schlierf
 */
public class WorkbenchPanel extends Panel {

	public WorkbenchPanel() {

		super();

		setLayout( new BorderLayout() );

		BorderLayoutData centerLayoutData = new BorderLayoutData( RegionPosition.CENTER );
		centerLayoutData.setMargins( new Margins( 5, 0, 5, 5 ) );

		Panel centerPanelWrappper = new Panel();
		centerPanelWrappper.setLayout( new FitLayout() );
		centerPanelWrappper.setBorder( false );
		centerPanelWrappper.setBodyBorder( false );

		// this.listDetailsPanel.setMessagesPanelActive();

		// setup the west regions layout properties
		BorderLayoutData leftPanelLayoutData = new BorderLayoutData( RegionPosition.WEST );
		leftPanelLayoutData.setMargins( new Margins( 5, 5, 0, 5 ) );
		leftPanelLayoutData.setCMargins( new Margins( 5, 5, 5, 5 ) );
		leftPanelLayoutData.setMinSize( 155 );
		leftPanelLayoutData.setSplit( true );

		PanelRegistry.LEFT_PANEL.get().add( PanelRegistry.MAIL_FOLDER_PANEL.get() );
		PanelRegistry.LEFT_PANEL.get().add( PanelRegistry.CONTACT_FOLDER_PANEL.get() );

		add( PanelRegistry.LEFT_PANEL.get(), leftPanelLayoutData );

		centerPanelWrappper.add( PanelRegistry.LIST_DETAILS_PANEL.get() );
		add( centerPanelWrappper, centerLayoutData );

		setTopToolbar( ToolbarManager.get().getWorkbenchToolbar() );
	}
}
