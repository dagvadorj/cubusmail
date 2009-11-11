/* ContactWindow.java

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
package com.cubusmail.gwtui.client.panels.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayout;

import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.panels.ModelFormPanel;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.IGWTWindow;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactAddress;
import com.cubusmail.gwtui.domain.ContactFolder;

/**
 * Contact window.
 * 
 * @author Juergen Schlierf
 */
public class ContactWindow extends Window implements IGWTWindow {

	private static final int DEFAULT_TEXTFIELD_WIDTH = 200;
	private static final int DEFAULT_LABEL_WIDTH = 100;
	private static final int DEFAULT_FIELDSET_WIDTH = DEFAULT_TEXTFIELD_WIDTH * 2 + DEFAULT_LABEL_WIDTH * 2 + 40;
	private static final int DEFAULT_WINDOW_WIDTH = DEFAULT_FIELDSET_WIDTH + 50;

	private Contact contactToEdit;

	private TabPanel tabPanel;
	private ModelFormPanel<Contact> contactFormPanel;
	private ModelFormPanel<ContactAddress> privateAddressFormPanel;
	private ModelFormPanel<ContactAddress> businessAddressFormPanel;

	private ModelFormPanel<Contact> noticeFormPanel;

	private TextField firstNameField;

	/**
	 * 
	 */
	public ContactWindow() {

		super( "", DEFAULT_WINDOW_WIDTH, 465, true, true );
		setLayout( new FitLayout() );
		setButtonAlign( Position.CENTER );
		setCloseAction( Window.HIDE );

		this.tabPanel = new TabPanel();
		this.tabPanel.setBorder( false );

		this.contactFormPanel = createContactFormPanel();
		this.tabPanel.add( this.contactFormPanel );

		Panel addressPanel = new Panel( TextProvider.get().contact_window_address_panel() );
		addressPanel.setBorder( false );
		addressPanel.setAutoScroll( true );
		addressPanel.setLayout( new RowLayout() );

		this.privateAddressFormPanel = createPrivateAddressFormPanel();
		addressPanel.add( this.privateAddressFormPanel );
		this.businessAddressFormPanel = createBusinessAddressFormPanel();
		addressPanel.add( this.businessAddressFormPanel );
		this.tabPanel.add( addressPanel );

		this.noticeFormPanel = createNoticeFormPanel();
		this.tabPanel.add( this.noticeFormPanel );

		add( tabPanel );

		Button saveButton = new Button( TextProvider.get().common_button_save() );
		saveButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				save();
			}
		} );
		addButton( saveButton );

		Button cancelButton = new Button( TextProvider.get().common_button_cancel() );
		cancelButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				hide();
			}
		} );
		addButton( cancelButton );
	}

	/**
	 * @param contactToEdit
	 */
	public void setContactToEdit( Contact contactToEdit ) {

		this.contactToEdit = contactToEdit;
		if ( this.contactToEdit.getPrivateAddress() == null ) {
			ContactAddress newAddress = GWT.create( ContactAddress.class );
			this.contactToEdit.setPrivateAddress( newAddress );
			this.contactToEdit.getPrivateAddress().setContact( this.contactToEdit );
		}
		if ( this.contactToEdit.getBusinessAddress() == null ) {
			ContactAddress newAddress = GWT.create( ContactAddress.class );
			this.contactToEdit.setBusinessAddress( newAddress );
			this.contactToEdit.getBusinessAddress().setContact( this.contactToEdit );
		}

		updateForms();
	}

	private void updateForms() {

		try {
			this.contactFormPanel.setModel( this.contactToEdit );
			this.privateAddressFormPanel.setModel( this.contactToEdit.getPrivateAddress() );
			this.businessAddressFormPanel.setModel( this.contactToEdit.getBusinessAddress() );
			this.noticeFormPanel.setModel( this.contactToEdit );

			this.contactFormPanel.updateForm();
			this.privateAddressFormPanel.updateForm();
			this.businessAddressFormPanel.updateForm();
			this.noticeFormPanel.updateForm();
		}
		catch (Throwable e) {
			GWT.log( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.widgets.Component#afterRender()
	 */
	@Override
	protected void afterRender() {

		this.firstNameField.focus( false, 200 );
	}

	/**
	 * @return
	 */
	private ModelFormPanel<Contact> createContactFormPanel() {

		ModelFormPanel<Contact> formPanel = new ModelFormPanel<Contact>();
		formPanel.setAutoScroll( true );
		formPanel.setFrame( true );
		formPanel.setTitle( TextProvider.get().contact_window_label_contact_data() );
		formPanel.setLabelWidth( DEFAULT_LABEL_WIDTH );
		formPanel.setLabelAlign( Position.RIGHT );

		// personal data
		FieldSet fieldset = createFieldSet( TextProvider.get().contact_window_label_person() );
		MultiFieldPanel multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( firstNameField = createTextField( TextProvider.get().contact_window_label_firstname(),
				"firstName" ), new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_secondname(), "secondName" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_lastname(), "lastName" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_nickname(), "nickname" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_company(), "company" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_position(), "position" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );
		formPanel.add( fieldset );

		// internet data
		fieldset = createFieldSet( TextProvider.get().contact_window_label_internet() );
		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_email(), "email" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_secondemail(), "email2" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_im(), "im" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_website(), "website" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );
		formPanel.add( fieldset );

		// phone numbers
		fieldset = createFieldSet( TextProvider.get().contact_window_label_telecom() );
		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_privatephone(), "privatePhone" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow(
				createTextField( TextProvider.get().contact_window_label_businessphone(), "businessPhone" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_mobilephone(), "mobilePhone" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_pager(), "pager" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_privatefax(), "privateFax" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_businessfax(), "businessFax" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );
		formPanel.add( fieldset );

		return formPanel;
	}

	/**
	 * @return
	 */
	private ModelFormPanel<ContactAddress> createPrivateAddressFormPanel() {

		ModelFormPanel<ContactAddress> formPanel = new ModelFormPanel<ContactAddress>();
		formPanel.setFrame( true );
		formPanel.setBorder( false );
		formPanel.setLabelWidth( DEFAULT_LABEL_WIDTH );
		formPanel.setLabelAlign( Position.RIGHT );

		FieldSet fieldset = createFieldSet( "Privat" );
		MultiFieldPanel multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_street(), "street" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_street2(), "street2" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		TextField zip = new TextField( TextProvider.get().contact_window_label_zipcode() + " / "
				+ TextProvider.get().contact_window_label_city(), "zipcode" );
		zip.setWidth( 70 );
		multiPanel.addToRow( zip, DEFAULT_LABEL_WIDTH + 80 );
		TextField city = new TextField( TextProvider.get().contact_window_label_city(), "city", 127 );
		city.setHideLabel( true );
		multiPanel.addToRow( city, 127 );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_state(), "state" ),
				new ColumnLayoutData( 1 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_country(), "country" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );
		formPanel.add( fieldset );

		return formPanel;
	}

	/**
	 * @return
	 */
	private ModelFormPanel<ContactAddress> createBusinessAddressFormPanel() {

		ModelFormPanel<ContactAddress> formPanel = new ModelFormPanel<ContactAddress>();
		formPanel.setFrame( true );
		formPanel.setBorder( false );
		formPanel.setLabelWidth( DEFAULT_LABEL_WIDTH );
		formPanel.setLabelAlign( Position.RIGHT );

		// business address
		FieldSet fieldset = createFieldSet( TextProvider.get().contact_window_label_private() );
		MultiFieldPanel multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_street(), "street" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_street2(), "street2" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		TextField zip = new TextField( TextProvider.get().contact_window_label_zipcode() + " / "
				+ TextProvider.get().contact_window_label_city(), "zipcode" );
		zip.setWidth( 70 );
		multiPanel.addToRow( zip, DEFAULT_LABEL_WIDTH + 80 );
		TextField city = new TextField( TextProvider.get().contact_window_label_city(), "city", 127 );
		city.setHideLabel( true );
		multiPanel.addToRow( city, 127 );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_state(), "state" ),
				new ColumnLayoutData( 1 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_country(), "country" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );
		formPanel.add( fieldset );

		return formPanel;
	}

	/**
	 * @return
	 */
	private ModelFormPanel<Contact> createNoticeFormPanel() {

		ModelFormPanel<Contact> formPanel = new ModelFormPanel<Contact>();
		formPanel.setAutoScroll( true );
		formPanel.setFrame( true );
		formPanel.setTitle( TextProvider.get().contact_window_label_notice() );
		formPanel.setLabelWidth( DEFAULT_LABEL_WIDTH );
		formPanel.setLabelAlign( Position.RIGHT );

		FieldSet fieldset = createFieldSet( TextProvider.get().contact_window_label_custom() );
		MultiFieldPanel multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_custom1(), "custom1" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_custom2(), "custom2" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );

		multiPanel = new MultiFieldPanel();
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_custom3(), "custom3" ),
				new ColumnLayoutData( 0.5 ) );
		multiPanel.addToRow( createTextField( TextProvider.get().contact_window_label_custom4(), "custom4" ),
				new ColumnLayoutData( 0.5 ) );
		fieldset.add( multiPanel, new AnchorLayoutData( "100%" ) );
		formPanel.add( fieldset );

		TextArea editor = new TextArea( TextProvider.get().contact_window_label_notice(), "notice" );
		editor.setHideLabel( true );
		editor.setWidth( DEFAULT_FIELDSET_WIDTH );
		editor.setHeight( 240 );
		formPanel.add( editor );

		return formPanel;
	}

	/**
	 * 
	 */
	private void save() {

		contactFormPanel.updateModel();
		privateAddressFormPanel.updateModel();
		businessAddressFormPanel.updateModel();
		noticeFormPanel.updateModel();

		if ( validateContact( contactToEdit ) ) {

			ContactFolder folder = GWTSessionManager.get().getCurrentContactFolder();
			if ( folder == null ) {
				folder = GWTSessionManager.get().getContactFolderList().get( 0 );
			}
			contactToEdit.setContactFolder( folder );

			ServiceProvider.getUserAccountService().saveContact( contactToEdit, new AsyncCallbackAdapter<Void>() {

				@Override
				public void onSuccess( Void result ) {

					hide();
					EventBroker.get().fireReloadContacts();
				}

				@Override
				public void onFailure( Throwable caught ) {

					super.onFailure( caught );
				}
			} );
		}
		else {
			MessageBox.alert( TextProvider.get().contact_window_alert_title(), TextProvider.get()
					.contact_window_alert_text() );
		}
	}

	/**
	 * @param label
	 * @param name
	 * @return
	 */
	private TextField createTextField( String label, String name ) {

		TextField field = new TextField( label, name );
		field.setWidth( DEFAULT_TEXTFIELD_WIDTH );
		field.addKeyListener( KeyboardListener.KEY_ENTER, new KeyListener() {

			public void onKey( int key, EventObject e ) {

				save();
			}
		} );

		return field;
	}

	/**
	 * @param title
	 * @return
	 */
	private FieldSet createFieldSet( String title ) {

		FieldSet fieldset = new FieldSet();
		fieldset.setPaddings( 2 );
		fieldset.setCheckboxToggle( false );
		fieldset.setFrame( true );
		fieldset.setTitle( title );
		fieldset.setWidth( DEFAULT_FIELDSET_WIDTH );

		return fieldset;
	}

	/**
	 * @param contact
	 * @return
	 */
	private boolean validateContact( Contact contact ) {

		return GWTUtil.hasText( contact.getFirstName() ) || GWTUtil.hasText( contact.getLastName() )
				|| GWTUtil.hasText( contact.getEmail() ) || GWTUtil.hasText( contact.getCompany() );
	}

	public void init() {

	}

	public boolean validate() {

		return true;
	}

	@Override
	public void show() {

		super.show();
		this.tabPanel.setActiveItem( 0 );
	}
}
