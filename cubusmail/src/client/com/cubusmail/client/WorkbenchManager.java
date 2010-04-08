/* WorkbenchManager.java

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
package com.cubusmail.client;

import com.cubusmail.client.canvases.CanvasRegistry;
import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class WorkbenchManager {

	private static WorkbenchManager instance;

	private TabSet workbenchTabset;

	private Tab mailTab;
	private Tab addressBookTab;
	private Tab settingsTab;

	public static WorkbenchManager get() {

		if ( instance == null ) {
			instance = new WorkbenchManager();
		}

		return instance;
	}

	public WorkbenchManager() {

		this.workbenchTabset = new TabSet();
		this.workbenchTabset.setWidth100();
		this.workbenchTabset.setHeight100();

		this.mailTab = new Tab( TextProvider.get().tab_email() );
		this.addressBookTab = new Tab( TextProvider.get().tab_address_book() );
		this.settingsTab = new Tab( TextProvider.get().tab_preferences() );

		this.workbenchTabset.addTab( this.mailTab );
		this.workbenchTabset.addTab( this.addressBookTab );
		this.workbenchTabset.addTab( this.settingsTab );

		this.workbenchTabset.addTabSelectedHandler( new WorkbenchTabSelectedHandler() );
	}

	public void createWorkbench() {

		// this.mailTab.setPane( CanvasRegistry.MAIL_CANVAS.get() );
		this.addressBookTab.setPane( CanvasRegistry.ADDRESS_BOOK_CANVAS.get() );
		this.workbenchTabset.setSelectedTab( 1 );
		this.workbenchTabset.draw();
	}

	private class WorkbenchTabSelectedHandler implements TabSelectedHandler {

		@Override
		public void onTabSelected( TabSelectedEvent event ) {

			if ( TextProvider.get().tab_email().equals( event.getTab().getTitle() ) ) {
				if ( event.getTab().getPane() == null ) {
					final Tab tab = event.getTab();
					tab.setPane( CanvasRegistry.MAIL_CANVAS.get() );
				}
			}
			else if ( TextProvider.get().tab_address_book().equals( event.getTab().getTitle() ) ) {
				if ( event.getTab().getPane() == null ) {
					final Tab tab = event.getTab();
					tab.setPane( CanvasRegistry.ADDRESS_BOOK_CANVAS.get() );
				}
			}
		}
	}
}
