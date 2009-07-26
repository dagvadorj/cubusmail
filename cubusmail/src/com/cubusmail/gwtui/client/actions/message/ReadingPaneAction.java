/* ReadingPaneAction.java

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
package com.cubusmail.gwtui.client.actions.message;

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.menu.BaseItem;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.Preferences;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Action for adjusting the reading panel.
 * 
 * @author Jürgen Schlierf
 */
public class ReadingPaneAction extends GWTAction {

	private Button mainButton;

	/**
	 * 
	 */
	public ReadingPaneAction() {

		super();
		setText( TextProvider.get().actions_reading_pane_text() );
		setImageName( ImageProvider.MSG_READING_PANE_RIGHT );
		setTooltipText( TextProvider.get().actions_reading_pane_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.actions.GWTAction#onClick(com.gwtext.client
	 * .widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onClick( Button button, EventObject e ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.actions.GWTAction#onMouseOver(com.gwtext.client
	 * .widgets.Button, com.gwtext.client.core.EventObject)
	 */
	public void onMouseOver( Button button, EventObject e ) {

		this.mainButton = button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.actions.GWTAction#onClick(com.gwtext.client
	 * .widgets.menu.BaseItem, com.gwtext.client.core.EventObject)
	 */
	public void onClick( BaseItem item, EventObject e ) {

		int state = Integer.valueOf( item.getStateId() );
		setStateIcon( state );

		UserAccount account = GWTSessionManager.get().getUserAccount();
		account.getPreferences().setReadingPane( state );
		ServiceProvider.getUserAccountService().saveUserAccount( account, new AsyncCallbackAdapter<UserAccount>() {

			@Override
			public void onSuccess( UserAccount result ) {

				Window.Location.reload();
			}
		} );
	}

	/**
	 * @param state
	 */
	private void setStateIcon( int state ) {

		if ( Preferences.READING_PANE_RIGHT == state ) {
			this.mainButton.setIcon( ImageProvider.MSG_READING_PANE_RIGHT );
		} else if ( Preferences.READING_PANE_BOTTOM == state ) {
			this.mainButton.setIcon( ImageProvider.MSG_READING_PANE_BOTTOM );
		} else if ( Preferences.READING_PANE_HIDE == state ) {
			this.mainButton.setIcon( ImageProvider.MSG_READING_PANE_HIDE );
		}
	}
}
