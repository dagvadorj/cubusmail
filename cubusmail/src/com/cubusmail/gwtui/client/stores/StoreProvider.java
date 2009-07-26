/* StoreProvider.java

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
package com.cubusmail.gwtui.client.stores;

import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.data.event.StoreListenerAdapter;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.util.MessageListTimer;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.MessageListFields;
import com.cubusmail.gwtui.domain.Preferences;

/**
 * Provider for all important stores.
 *
 * @author Jürgen Schlierf
 */
public class StoreProvider {

	// Message List
	public static RecordDef MESSAGE_LIST_RECORD_DEF = new RecordDef( new FieldDef[] {
			new StringFieldDef( MessageListFields.ID.name() ),
			new BooleanFieldDef( MessageListFields.READ_FLAG.name() ),
			new BooleanFieldDef( MessageListFields.DELETED_FLAG.name() ),
			new BooleanFieldDef( MessageListFields.ANSWERED_FLAG.name() ),
			new BooleanFieldDef( MessageListFields.DRAFT_FLAG.name() ),
			new IntegerFieldDef( MessageListFields.PRIORITY.name() ),
			new BooleanFieldDef( MessageListFields.ATTACHMENT_FLAG.name() ),
			new StringFieldDef( MessageListFields.SUBJECT.name() ),
			new StringFieldDef( MessageListFields.FROM.name() ), new StringFieldDef( MessageListFields.TO.name() ),
			new StringFieldDef( MessageListFields.DATE.name() ), new StringFieldDef( MessageListFields.SIZE.name() ) } );

	public static final RecordDef EMAIL_COMBO_RECORD_DEF = new RecordDef( new FieldDef[] {
			new StringFieldDef( EmailAddressFieldsEnum.EMAIL_ADDRESS.toString() ),
			new StringFieldDef( EmailAddressFieldsEnum.HTML_ESCAPED_EMAIL_ADDRESS.toString() ) } );

	private static RecordDef TIMEZONE_RECORD_DEF = new RecordDef( new FieldDef[] { new StringFieldDef( "value" ),
			new StringFieldDef( "display" ) } );

	private static StoreProvider instance;

	// stores
	private Store messageListStore = null;
	private Store emailComboStore = null;
	private Store timezoneStore = null;
	private Store languageStore = null;
	private Store reloadPeriodStore = null;
	private Store themeStore = null;
	private Store readingPanePostitionsStore = null;

	// proxies
	private MessageListProxy messageListProxy;

	public StoreProvider() {

	}

	public static StoreProvider get() {

		if ( instance == null ) {
			instance = new StoreProvider();
		}

		return instance;
	}

	/**
	 * @return
	 */
	public Store getEmailComboStore() {

		if ( this.emailComboStore == null ) {
			this.emailComboStore = new Store( new EmailComboProxy(), new ArrayReader( EMAIL_COMBO_RECORD_DEF ) );
		}

		return this.emailComboStore;
	}

	/**
	 * @return
	 */
	public Store getTimezoneStore() {

		if ( this.timezoneStore == null ) {
			this.timezoneStore = new Store( new TimezonesProxy(), new ArrayReader( TIMEZONE_RECORD_DEF ) );
			this.timezoneStore.setAutoLoad( false );
		}

		return this.timezoneStore;
	}

	/**
	 * @return Returns the messageListStore.
	 */
	public Store getMessageListStore() {

		if ( this.messageListStore == null ) {
			this.messageListStore = new Store( getMessageListProxy(), new ArrayReader( 0, MESSAGE_LIST_RECORD_DEF ),
					true );
			this.messageListStore.addStoreListener( new StoreListenerAdapter() {

				@Override
				public boolean doBeforeLoad( Store store ) {

					MessageListTimer.get().stop();
					return true;
				}

				@Override
				public void onLoad( Store store, Record[] records ) {

					EventBroker.get().fireCleanReadingPane();
					EventBroker.get().fireMessagesChanged();

					MessageListTimer.get().start();
				}
			} );

		}
		return this.messageListStore;
	}

	/**
	 * @return Returns the languageStore.
	 */
	public Store getLanguageStore() {

		if ( this.languageStore == null ) {
			this.languageStore = new SimpleStore( new String[] { "value", "text" }, getLanugages() );
		}
		return this.languageStore;
	}

	/**
	 * @return Returns the reloadPeriodStore.
	 */
	public Store getReloadPeriodStore() {

		if ( this.reloadPeriodStore == null ) {
			this.reloadPeriodStore = new SimpleStore( new String[] { "value", "text" }, getReloadPeriods() );
		}
		return this.reloadPeriodStore;
	}

	/**
	 * @return Returns the themeStore.
	 */
	public Store getThemeStore() {

		if ( this.themeStore == null ) {
			this.themeStore = new SimpleStore( new String[] { "value", "text" }, getThemes() );
		}
		return this.themeStore;
	}

	/**
	 * @return Returns the messageListProxy.
	 */
	public MessageListProxy getMessageListProxy() {

		if ( this.messageListProxy == null ) {
			this.messageListProxy = new MessageListProxy();
		}
		return this.messageListProxy;
	}

	/**
	 * @return Returns the readingPanePostitionsStore.
	 */
	public Store getReadingPanePostitionsStore() {

		if ( this.readingPanePostitionsStore == null ) {
			this.readingPanePostitionsStore = new SimpleStore( new String[] { "value", "text" },
					getReadingPanePostitions() );
		}
		return this.readingPanePostitionsStore;
	}

	// Preferences
	private String[][] getLanugages() {

		return new String[][] { new String[] { "de", "Deutsch" }, new String[] { "en_UK", "English (UK)" },
				new String[] { "en_US", "English (US)" } };
	}

	private String[][] getReloadPeriods() {

		return new String[][] { new String[] { "0", TextProvider.get().common_never() },
				new String[] { "30000", "30 " + TextProvider.get().common_seconds() },
				new String[] { "60000", "60 " + TextProvider.get().common_seconds() },
				new String[] { "300000", "5 " + TextProvider.get().common_mintues() },
				new String[] { "900000", "15 " + TextProvider.get().common_mintues() },
				new String[] { "1800000", TextProvider.get().common_half_an_hour() } };
	}

	private String[][] getThemes() {

		return new String[][] { new String[] { "themes/slate/css/xtheme-slate.css", "Slate" },
				new String[] { "js/ext/resources/css/xtheme-gray.css", "Gray" },
				new String[] { "xtheme-default.css", "Aero Glass" },
				new String[] { "themes/green/css/xtheme-green.css", "Green" },
				new String[] { "themes/indigo/css/xtheme-indigo.css", "Indigo" },
				new String[] { "themes/silverCherry/css/xtheme-silverCherry.css", "Silver Cherry" } };
	}

	private String[][] getReadingPanePostitions() {

		return new String[][] {
				new String[] { String.valueOf( Preferences.READING_PANE_BOTTOM ),
						TextProvider.get().actions_reading_pane_bottom() },
				new String[] { String.valueOf( Preferences.READING_PANE_RIGHT ),
						TextProvider.get().actions_reading_pane_right() },
				new String[] { String.valueOf( Preferences.READING_PANE_HIDE ),
						TextProvider.get().actions_reading_pane_hide() } };
	}
}
