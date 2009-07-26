/* ContactListWindow.java

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

import com.google.gwt.core.client.GWT;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListener;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayoutData;

import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.panels.contact.ContactListPanel;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.widgets.EmailAddressInputField;
import com.cubusmail.gwtui.domain.ContactListFields;

/**
 * Contact list dialog.
 * 
 * @author Jürgen Schlierf
 */
public class ContactListWindow extends Window implements IGWTWindow {

	private static final int LABEL_WIDTH = 60;

	public final static int TO_FIELD = 0;
	public final static int CC_FIELD = 1;
	public final static int BCC_FIELD = 2;

	private int currentField;

	private EmailAddressInputField to;
	private EmailAddressInputField cc;
	private EmailAddressInputField bcc;

	private ContactListPanel contactListPanel;

	private Button okButton;
	private Button cancelButton;

	private Button toButton;
	private Button ccButton;
	private Button bccButton;

	/**
	 * @param currentField
	 */
	ContactListWindow() {

		super( TextProvider.get().dialog_contactlist_title(), 600, 410, true, true );
		setLayout( new BorderLayout() );
		setCloseAction( Window.HIDE );

		this.contactListPanel = (ContactListPanel) PanelRegistry.CONTACT_LIST_PANEL_FOR_WINDOW.get();
		this.contactListPanel.setAutoHeight( false );
		this.contactListPanel.setHeight( 250 );
		this.contactListPanel.addGridRowListener( new ContactGridRowListener() );
		add( this.contactListPanel, new BorderLayoutData( RegionPosition.NORTH ) );

		FormPanel addressPanel = createAddressLines();
		add( addressPanel, new BorderLayoutData( RegionPosition.CENTER ) );

		this.okButton = new Button( TextProvider.get().common_button_ok() );
		addButton( this.okButton );

		this.cancelButton = new Button( TextProvider.get().common_button_cancel() );
		this.cancelButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				try {
					close();
				}
				catch (Throwable ex) {
					GWT.log( ex.getMessage(), ex );
				}
			}
		} );
		addButton( cancelButton );
		setButtonAlign( Position.CENTER );
	}

	/**
	 * @param listener
	 */
	public void addOkButtonListener( ButtonListener listener ) {

		this.okButton.addListener( listener );
	}

	/**
	 * @param listener
	 */
	public void addCancelButtonListener( ButtonListener listener ) {

		this.cancelButton.addListener( listener );
	}

	/**
	 * @param textField
	 * @return
	 */
	private FormPanel createAddressLines() {

		FormPanel addressPanel = new FormPanel();
		addressPanel.setFrame( true );
		addressPanel.setAutoHeight( true );

		this.to = new EmailAddressInputField( TextProvider.get().dialog_contactlist_label_to() );
		MultiFieldPanel multiField = new MultiFieldPanel();
		this.to.setWidth( "100%" );
		this.to.setHideLabel( true );
		this.toButton = new Button( this.to.getFieldLabel() );
		this.toButton.setMinWidth( LABEL_WIDTH );
		this.toButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				RowSelectionModel sm = contactListPanel.getGridPanel().getSelectionModel();
				if ( sm.getCount() > 0 ) {
					String address = sm.getSelected().getAsString( ContactListFields.INTERNET_ADDRESS.name() );
					if ( address != null && address.length() > 0 ) {
						to.addEmailAddress( address );
					}
				}
			}
		} );
		multiField.addToRow( this.toButton, LABEL_WIDTH + 5 );
		multiField.addToRow( this.to, new ColumnLayoutData( 1.0 ) );
		addressPanel.add( multiField, new AnchorLayoutData( "100%" ) );

		this.cc = new EmailAddressInputField( TextProvider.get().dialog_contactlist_label_cc() );
		multiField = new MultiFieldPanel();
		this.cc.setWidth( "100%" );
		this.cc.setHideLabel( true );
		this.ccButton = new Button( this.cc.getFieldLabel() );
		this.ccButton.setMinWidth( LABEL_WIDTH );
		this.ccButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				RowSelectionModel sm = contactListPanel.getGridPanel().getSelectionModel();
				if ( sm.getCount() > 0 ) {
					String address = sm.getSelected().getAsString( ContactListFields.INTERNET_ADDRESS.name() );
					if ( address != null && address.length() > 0 ) {
						cc.addEmailAddress( address );
					}
				}
			}
		} );
		multiField.addToRow( this.ccButton, LABEL_WIDTH + 5 );
		multiField.addToRow( this.cc, new ColumnLayoutData( 1.0 ) );
		addressPanel.add( multiField, new AnchorLayoutData( "100%" ) );

		this.bcc = new EmailAddressInputField( TextProvider.get().dialog_contactlist_label_bcc() );
		multiField = new MultiFieldPanel();
		this.bcc.setWidth( "100%" );
		this.bcc.setHideLabel( true );
		this.bccButton = new Button( this.bcc.getFieldLabel() );
		this.bccButton.setMinWidth( LABEL_WIDTH );
		this.bccButton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				RowSelectionModel sm = contactListPanel.getGridPanel().getSelectionModel();
				if ( sm.getCount() > 0 ) {
					String address = sm.getSelected().getAsString( ContactListFields.INTERNET_ADDRESS.name() );
					if ( address != null && address.length() > 0 ) {
						bcc.addEmailAddress( address );
					}
				}
			}
		} );
		multiField.addToRow( this.bccButton, LABEL_WIDTH + 5 );
		multiField.addToRow( this.bcc, new ColumnLayoutData( 1.0 ) );
		addressPanel.add( multiField, new AnchorLayoutData( "100%" ) );

		return addressPanel;
	}

	/**
	 * @return Returns the to.
	 */
	public TextField getTo() {

		return this.to;
	}

	/**
	 * @return Returns the cc.
	 */
	public TextField getCc() {

		return this.cc;
	}

	/**
	 * @return Returns the bcc.
	 */
	public TextField getBcc() {

		return this.bcc;
	}

	/**
	 * Grid row listener.
	 * 
	 * @author Jürgen Schlierf
	 */
	private class ContactGridRowListener extends GridRowListenerAdapter {

		@Override
		public void onRowDblClick( GridPanel grid, int rowIndex, EventObject e ) {

			RowSelectionModel sm = grid.getSelectionModel();

			if ( sm.getCount() > 0 ) {
				String address = sm.getSelected().getAsString( ContactListFields.INTERNET_ADDRESS.name() );
				if ( address != null && address.length() > 0 ) {
					if ( currentField == TO_FIELD ) {
						to.addEmailAddress( address );
					}
					else if ( currentField == CC_FIELD ) {
						cc.addEmailAddress( address );
					}
					else if ( currentField == BCC_FIELD ) {
						bcc.addEmailAddress( address );
					}
				}
			}
		}
	}

	/**
	 * @param currentField
	 *            The currentField to set.
	 */
	public void setCurrentField( int currentField ) {

		this.currentField = currentField;
	}

	public void init() {

	}

	/* (non-Javadoc)
	 * @see com.cubusmail.gwtui.client.windows.IGWTWindow#validate()
	 */
	public boolean validate() {
		return true;
	}
}
