/* WorkbenchCanvas.java

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
package com.cubusmail.client.canvases;

import com.cubusmail.client.toolbars.ToolbarRegistry;
import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Workbench Canvas.
 * 
 * @author Juergen Schlierf
 */
public class WorkbenchCanvas extends TabSet {

	private VLayout mailCanvas;

	public WorkbenchCanvas() {

		super();

		// setTabBarThickness( 23 );
		setWidth100();
		setHeight100();

		this.mailCanvas = new VLayout();
		this.mailCanvas.setBackgroundImage( "[SKIN]/shared/background.gif" );
		this.mailCanvas.setWidth100();
		this.mailCanvas.setHeight100();
		this.mailCanvas.setBorder( "0px" );

		this.mailCanvas.addMember( ToolbarRegistry.MAIL.get() );
		
		HLayout contentCanvas = new HLayout();
		contentCanvas.setWidth100();
		contentCanvas.setHeight100();
	
		CanvasRegistry.MAIL_FOLDER_CANVAS.get().setWidth( 200 );
		contentCanvas.addMember( CanvasRegistry.MAIL_FOLDER_CANVAS.get() );
		contentCanvas.addMember( CanvasRegistry.MESSAGE_LIST_CANVAS.get() );

		this.mailCanvas.addMember( contentCanvas );
		
		Tab tab = new Tab( TextProvider.get().tab_email() );
		tab.setPane( this.mailCanvas );

		addTab( tab );
		addTab( new Tab( TextProvider.get().tab_address_book() ) );
		addTab( new Tab( TextProvider.get().tab_preferences() ) );
	}
}
