/* PreferencesPanel.java

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

import com.gwtext.client.core.Position;
import com.gwtext.client.core.Template;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListenerAdapter;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

import com.cubusmail.gwtui.client.stores.StoreProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.Preferences;
import com.cubusmail.gwtui.domain.UserAccount;

/**
 * Preferences panel.
 * 
 * @author Jürgen Schlierf
 */
public class PreferencesPanel extends Panel {

	private static final Template COMBO_TEMPLATE = new Template( "<div class=\"x-combo-list-item\">{" + "display"
			+ "}</div>" );

	private ComboBox languageCombo;
	private ComboBox timezoneCombo;
	private ComboBox reloadPeriodCombo;
	private ComboBox themeCombo;
	private ComboBox readingPanePositionCombo;

	private ModelFormPanel<Preferences> preferencesFormPanel;

	/**
	 * 
	 */
	public PreferencesPanel() {

		super( TextProvider.get().preferences_panel_title() );
		setLayout( new FitLayout() );
		setBorder( false );

		this.preferencesFormPanel = new ModelFormPanel<Preferences>();
		this.preferencesFormPanel.setFrame( true );
		this.preferencesFormPanel.setBorder( false );
		this.preferencesFormPanel.setLabelAlign( Position.RIGHT );
		this.preferencesFormPanel.setLabelWidth( 120 );

		FieldSet commonFieldset = new FieldSet();
		commonFieldset.setCheckboxToggle( false );
		commonFieldset.setFrame( true );
		commonFieldset.setTitle( TextProvider.get().preferences_panel_label_general() );

		this.languageCombo = new ComboBox( TextProvider.get().preferences_panel_label_language(), "language" );
		this.languageCombo.setForceSelection( true );
		this.languageCombo.setStore( StoreProvider.get().getLanguageStore() );
		this.languageCombo.setDisplayField( "text" );
		this.languageCombo.setValueField( "value" );
		this.languageCombo.setEditable( false );
		commonFieldset.add( this.languageCombo, new AnchorLayoutData( "70%" ) );

		this.timezoneCombo = new ComboBox( TextProvider.get().preferences_panel_label_timezone(), "timezone" );
		this.timezoneCombo.setForceSelection( true );
		this.timezoneCombo.setStore( StoreProvider.get().getTimezoneStore() );
		this.timezoneCombo.setDisplayField( "display" );
		this.timezoneCombo.setValueField( "value" );
		this.timezoneCombo.setEditable( false );
		this.timezoneCombo.setLoadingText( TextProvider.get().common_mask_text() );
		this.timezoneCombo.setMode( ComboBox.REMOTE );
		this.timezoneCombo.setTriggerAction( ComboBox.ALL );
		this.timezoneCombo.setTpl( COMBO_TEMPLATE );

		commonFieldset.add( this.timezoneCombo, new AnchorLayoutData( "90%" ) );
		commonFieldset.add( new TextField( TextProvider.get().preferences_panel_label_items(), "pageCount" ),
				new AnchorLayoutData( "40%" ) );
		commonFieldset.add( new Checkbox( TextProvider.get().preferences_panel_label_short_tieme_format(),
				"shortTimeFormat" ) );
		commonFieldset.add( new Checkbox( TextProvider.get().preferences_panel_label_show_html(), "showHtml" ) );
		commonFieldset.add( new Checkbox( TextProvider.get().preferences_panel_label_compose_html(), "createHtmlMsgs" ) );
		this.preferencesFormPanel.add( commonFieldset );

		FieldSet viewFieldset = new FieldSet();
		viewFieldset.setCheckboxToggle( false );
		viewFieldset.setFrame( true );
		viewFieldset.setTitle( TextProvider.get().preferences_panel_label_display() );

		this.readingPanePositionCombo = new ComboBox( TextProvider.get().preferences_panel_label_reading_pane() );
		this.readingPanePositionCombo.setName( "readingPane" );
		this.readingPanePositionCombo.setForceSelection( true );
		this.readingPanePositionCombo.setStore( StoreProvider.get().getReadingPanePostitionsStore() );
		this.readingPanePositionCombo.setDisplayField( "text" );
		this.readingPanePositionCombo.setValueField( "value" );
		this.readingPanePositionCombo.setEditable( false );
		viewFieldset.add( this.readingPanePositionCombo );

		this.reloadPeriodCombo = new ComboBox( TextProvider.get().preferences_panel_label_reload_period() );
		this.reloadPeriodCombo.setName( "messagesReloadPeriod" );
		this.reloadPeriodCombo.setForceSelection( true );
		this.reloadPeriodCombo.setStore( StoreProvider.get().getReloadPeriodStore() );
		this.reloadPeriodCombo.setDisplayField( "text" );
		this.reloadPeriodCombo.setValueField( "value" );
		this.reloadPeriodCombo.setEditable( false );
		viewFieldset.add( this.reloadPeriodCombo );

		this.themeCombo = new ComboBox( TextProvider.get().preferences_panel_label_theme(), "theme" );
		this.themeCombo.setForceSelection( true );
		this.themeCombo.setStore( StoreProvider.get().getThemeStore() );
		this.themeCombo.setDisplayField( "text" );
		this.themeCombo.setValueField( "value" );
		this.themeCombo.setEditable( false );
		this.themeCombo.setTypeAhead( true );
		this.themeCombo.setSelectOnFocus( true );

		viewFieldset.add( this.themeCombo );

		this.preferencesFormPanel.add( viewFieldset );

		FieldSet serverFieldset = new FieldSet();
		serverFieldset.setCheckboxToggle( false );
		serverFieldset.setFrame( true );
		serverFieldset.setTitle( TextProvider.get().preferences_panel_label_mail_server() );

		serverFieldset.add( new Checkbox( TextProvider.get().preferences_panel_label_mark_as_deleted(),
				"markAsDeletedWithoutTrash" ) );
		serverFieldset.add( new Checkbox( TextProvider.get().preferences_panel_label_empty_trash(),
				"emptyTrashAfterLogout" ) );
		this.preferencesFormPanel.add( serverFieldset );

		this.timezoneCombo.getStore().addStoreListener( new StoreListenerAdapter() {

			@Override
			public void onLoad( Store store, Record[] records ) {

				preferencesFormPanel.updateForm();
			}
		} );

		add( this.preferencesFormPanel );
	}

	public void setUserAccount( UserAccount userAccount ) {

		this.preferencesFormPanel.setModel( userAccount.getPreferences() );
		this.themeCombo.getStore().load();
		this.timezoneCombo.getStore().load();
	}

	public void updateModel() {

		this.preferencesFormPanel.updateModel();
	}
}
