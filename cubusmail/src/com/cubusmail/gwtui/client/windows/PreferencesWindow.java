/* PreferencesWindow.java

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
package com.cubusmail.gwtui.client.windows;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.util.CSS;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.panels.IdentitiesPanel;
import com.cubusmail.gwtui.client.panels.PreferencesPanel;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Preferences dialog.
 * 
 * @author Juergen Schlierf
 */
public class PreferencesWindow extends Window implements IGWTWindow {

	private UserAccount userAccountCopy;

	private TabPanel tabPanel = null;
	private PreferencesPanel preferencesPanel = null;
	private IdentitiesPanel identitiesPanel = null;

	/**
	 * 
	 */
	PreferencesWindow() {

		super( TextProvider.get().dialog_preferences_header(), 600, 560, true, true );
		setLayout( new FitLayout() );
		setModal( true );
		setButtonAlign( Position.CENTER );
		setCloseAction( Window.HIDE );

		this.tabPanel = new TabPanel();
		this.tabPanel.setBorder( false );

		this.preferencesPanel = new PreferencesPanel();
		this.tabPanel.add( this.preferencesPanel );

		this.identitiesPanel = new IdentitiesPanel();
		this.tabPanel.add( this.identitiesPanel );

		Button saveButton = new Button( TextProvider.get().common_button_save() );
		saveButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				identitiesPanel.resumeIdentities();
				preferencesPanel.updateModel();

				ServiceProvider.getUserAccountService().saveUserAccount( userAccountCopy,
						new AsyncCallbackAdapter<UserAccount>() {

							@Override
							public void onSuccess( UserAccount result ) {

								userAccountCopy = result;
								postValidateChanges();
							}
						} );
			}
		} );
		addButton( saveButton );

		Button cancelButton = new Button( TextProvider.get().common_button_cancel() );
		cancelButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				closeWindow();
			}
		} );
		addButton( cancelButton );

		add( this.tabPanel );
	}

	/**
	 * 
	 */
	private void closeWindow() {

		hide();
	}

	/**
	 * 
	 */
	private void postValidateChanges() {

		UserAccount oldAccount = GWTSessionManager.get().getUserAccount();

		boolean reload = !oldAccount.getPreferences().getLanguage().equals(
				this.userAccountCopy.getPreferences().getLanguage() )
				|| (oldAccount.getPreferences().getReadingPane() != this.userAccountCopy.getPreferences()
						.getReadingPane());

		if ( reload ) {
			MessageBox.alert( TextProvider.get().dialog_preferences_header(), TextProvider.get()
					.dialog_preferences_alert(), new MessageBox.AlertCallback() {

				public void execute() {

					executeChanges();
					com.google.gwt.user.client.Window.Location.reload();
				}
			} );
		}
		else {
			executeChanges();
		}
	}

	/**
	 * 
	 */
	private void executeChanges() {

		UserAccount oldAccount = GWTSessionManager.get().getUserAccount();

		if ( !this.userAccountCopy.getPreferences().getTheme().equals( oldAccount.getPreferences().getTheme() ) ) {
			CSS.swapStyleSheet( "theme", this.userAccountCopy.getPreferences().getTheme() );
		}

		GWTSessionManager.get().setUserAccount( this.userAccountCopy );
		EventBroker.get().firePreferencesChanged( this.userAccountCopy.getPreferences() );

		closeWindow();
	}

	@Override
	public void show() {

		super.show();
		this.tabPanel.setActiveTab( 0 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.windows.IGWTWindow#init()
	 */
	public void init() {

		this.userAccountCopy = GWTSessionManager.get().getUserAccount().clone();
		this.preferencesPanel.setUserAccount( this.userAccountCopy );
		this.identitiesPanel.setUserAccount( this.userAccountCopy );
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
