/* ComposeMessageWindow.java

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
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.core.Template;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.HtmlEditor;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;

import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.panels.ComposeAttachmentPanel;
import com.cubusmail.gwtui.client.panels.PanelRegistry;
import com.cubusmail.gwtui.client.panels.ToolbarManager;
import com.cubusmail.gwtui.client.services.AsyncCallbackAdapter;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.stores.IdentityStore;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.widgets.EmailAddressComboBox;
import com.cubusmail.gwtui.domain.GWTMailConstants;
import com.cubusmail.gwtui.domain.Identity;
import com.cubusmail.gwtui.domain.IdentityListFields;

/**
 * Window for composing new messages.
 * 
 * @author Juergen Schlierf
 */
public class ComposeMessageWindow extends Window implements IGWTWindow {

	private static final Template FROM_COMBO_TEMPLATE = new Template( "<div class=\"x-combo-list-item\">{"
			+ IdentityListFields.ESCAPED_INTERNET_ADDRESS.name() + "}</div>" );

	private static final int LABEL_WIDTH = 60;

	private static final int INDEX_HTML_EDITOR = 0;
	private static final int INDEX_PLAINTEXT_EDITOR = 1;

	private FormPanel formPanel = null;
	private Panel textPanelWrapper = null;
	private CardLayout textPanelWrapperLayout = null;
	private ComboBox priorityCombo = null;
	private ComboBox fromCombo = null;
	private Checkbox htmlCheck;
	private Checkbox acknowledgementCheck;

	private EmailAddressComboBox toText = null;
	private EmailAddressComboBox ccText = null;
	private EmailAddressComboBox bccText = null;
	private TextField subjectText = null;

	private TextArea plainMessageField = null;
	private HtmlEditor htmlMessageField = null;

	private IdentityStore identityStore = null;
	private Store priorityStore = null;

	private Panel attachmentsPanel;

	private GWTMessage message;

	// private boolean messageChanged;

	/**
	 * 
	 */
	ComposeMessageWindow() {

		super( TextProvider.get().window_compose_message_title(), false, true );

		setPaddings( 2 );
		setLayout( new BorderLayout() );
		setClosable( false );
		setMaximizable( true );
		setCollapsible( true );

		setTopToolbar( ToolbarManager.get().createComposeMessageToolbar() );

		this.identityStore = new IdentityStore();
		this.identityStore.setUserAccount( GWTSessionManager.get().getUserAccount() );
		this.priorityStore = new SimpleStore( new String[] { "code", "text" }, getPriorities() );

		this.formPanel = new FormPanel();
		this.formPanel.setLabelAlign( Position.RIGHT );
		this.formPanel.setAutoHeight( true );
		this.formPanel.setFrame( true );
		this.formPanel.setBorder( true );
		this.formPanel.setLabelWidth( LABEL_WIDTH );

		MultiFieldPanel multiField = new MultiFieldPanel();
		this.fromCombo = new ComboBox( TextProvider.get().window_compose_message_label_from() );
		this.fromCombo.setForceSelection( true );
		this.fromCombo.setStore( this.identityStore );
		this.fromCombo.setDisplayField( IdentityListFields.INTERNET_ADDRESS.name() );
		this.fromCombo.setValueField( IdentityListFields.ID.name() );
		this.fromCombo.setEditable( false );
		this.fromCombo.setWidth( 450 );
		this.fromCombo.setTpl( FROM_COMBO_TEMPLATE );
		multiField.addToRow( this.fromCombo, 520 );

		this.priorityCombo = new ComboBox( TextProvider.get().window_compose_message_label_priority() );
		this.priorityCombo.setForceSelection( true );
		this.priorityCombo.setWidth( 100 );
		this.priorityCombo.setStore( this.priorityStore );
		this.priorityCombo.setValueField( "code" );
		this.priorityCombo.setDisplayField( "text" );
		this.priorityCombo.setEditable( false );
		multiField.addToRow( this.priorityCombo, 170 );

		this.htmlCheck = new Checkbox();
		this.htmlCheck.addListener( new HtmlCheckboxListener() );
		this.htmlCheck.setHideLabel( true );
		this.htmlCheck.setWidth( 20 );
		multiField.addToRow( this.htmlCheck, 30 );
		multiField.addToRow( new Label( "HTML" ), 40 );

		this.acknowledgementCheck = new Checkbox();
		this.acknowledgementCheck.setHideLabel( true );
		this.acknowledgementCheck.setWidth( 20 );
		multiField.addToRow( this.acknowledgementCheck, 30 );
		multiField.addToRow( new Label( TextProvider.get().window_compose_message_label_acknowledgement() ),
				new ColumnLayoutData( 1.0 ) );
		this.formPanel.add( multiField, new AnchorLayoutData( "100%" ) );

		this.toText = new EmailAddressComboBox( TextProvider.get().window_compose_message_label_to() );
		this.formPanel
				.add( createAddressLine( this.toText, ContactListWindow.TO_FIELD ), new AnchorLayoutData( "100%" ) );

		this.ccText = new EmailAddressComboBox( TextProvider.get().window_compose_message_label_cc() );
		this.formPanel
				.add( createAddressLine( this.ccText, ContactListWindow.CC_FIELD ), new AnchorLayoutData( "100%" ) );

		this.bccText = new EmailAddressComboBox( TextProvider.get().window_compose_message_label_bcc() );
		this.formPanel.add( createAddressLine( this.bccText, ContactListWindow.BCC_FIELD ), new AnchorLayoutData(
				"100%" ) );

		this.subjectText = new TextField( TextProvider.get().window_compose_message_label_subject() );
		this.formPanel.add( this.subjectText, new AnchorLayoutData( "100%" ) );

		this.attachmentsPanel = new Panel();
		this.attachmentsPanel.setLayout( new ColumnLayout() );
		this.formPanel.add( this.attachmentsPanel, new AnchorLayoutData( "100%" ) );

		this.textPanelWrapperLayout = new CardLayout( true );
		this.textPanelWrapper = new Panel();
		this.textPanelWrapper.setBorder( false );
		this.textPanelWrapper.setLayout( this.textPanelWrapperLayout );

		this.htmlMessageField = new HtmlEditor();
		this.textPanelWrapper.add( this.htmlMessageField );
		this.plainMessageField = new TextArea();
		this.plainMessageField.setStyle( "font-family: monospace; font-size: 10pt;" );
		this.plainMessageField.addListener( new FieldListenerAdapter() {

			@Override
			public void onFocus( Field field ) {

				((TextArea) field).setCaretPosition( 0, 0 );
			}
		} );
		this.textPanelWrapper.add( this.plainMessageField );

		add( this.formPanel, new BorderLayoutData( RegionPosition.NORTH ) );
		add( this.textPanelWrapper, new BorderLayoutData( RegionPosition.CENTER ) );

		WindowRegistry.CONTACT_LIST_WINDOW.get( ContactListWindow.class ).addOkButtonListener(
				new ButtonListenerAdapter() {

					@Override
					public void onClick( Button button, EventObject e ) {

						ContactListWindow window = WindowRegistry.CONTACT_LIST_WINDOW.get( ContactListWindow.class );
						toText.setValue( window.getTo().getValueAsString() );
						ccText.setValue( window.getCc().getValueAsString() );
						bccText.setValue( window.getBcc().getValueAsString() );
						WindowRegistry.CONTACT_LIST_WINDOW.close();
					}
				} );
	}

	/**
	 * 
	 */
	public void init() {

		// this.messageChanged = false;
		clearFields();

		this.identityStore.setUserAccount( GWTSessionManager.get().getUserAccount() );
		this.identityStore.load();
		this.priorityStore.load();

		Identity identity = GWTSessionManager.get().getUserAccount().getStandardIdentity();
		this.fromCombo.setValue( identity.getId().toString() );
		setFieldsForIdentity( identity );

		this.priorityCombo.setValue( String.valueOf( GWTMailConstants.PRIORITY_NORMAL ) );
		this.htmlCheck.setChecked( GWTSessionManager.get().getPreferences().isCreateHtmlMsgs() );
		this.acknowledgementCheck.setChecked( false );
		if ( GWTSessionManager.get().getPreferences().isCreateHtmlMsgs() ) {
			this.textPanelWrapper.setActiveItem( INDEX_HTML_EDITOR );
		}
		else {
			this.textPanelWrapper.setActiveItem( INDEX_PLAINTEXT_EDITOR );
		}

		calculateSize();
	}

	/**
	 * @param identity
	 */
	private void setFieldsForIdentity( Identity identity ) {

		if ( !GWTUtil.hasText( this.bccText.getValueAsString() ) ) {
			this.bccText.setValue( identity.getBcc() );
		}

		if ( GWTUtil.hasText( identity.getSignature() ) ) {
			if ( this.htmlCheck.getValue() ) {
				this.htmlMessageField.setValue( "<br><br>" + identity.getSignature() );
			}
			else {
				this.plainMessageField.setValue( "\n\n" + identity.getSignature() );
			}
		}
	}

	/**
	 * 
	 */
	private void clearFields() {

		this.toText.setValue( "" );
		this.ccText.setValue( "" );
		this.bccText.setValue( "" );
		this.subjectText.setValue( "" );
		this.attachmentsPanel.setVisible( false );
		this.attachmentsPanel.clear();
		this.htmlMessageField.setValue( "" );
		this.plainMessageField.setValue( "" );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		this.message = message;

		if ( this.message != null ) {
			this.toText.setValue( message.getTo() );
			this.ccText.setValue( message.getCc() );
			this.bccText.setValue( message.getBcc() );
			this.subjectText.setValue( message.getSubject() );
			this.htmlCheck.setValue( message.isHtmlMessage() );
			this.htmlCheck.setChecked( this.message.isHtmlMessage() );
			setAttachments( message.getAttachments() );
			if ( message.isHtmlMessage() ) {

				this.htmlMessageField.setValue( (message.isDraft() ? "" : "<br><br>") + message.getMessageText() );
			}
			else {
				this.plainMessageField.setValue( (message.isDraft() ? "" : "\n\n") + message.getMessageText() );
			}
		}
	}

	/**
	 * @param textField
	 * @return
	 */
	private MultiFieldPanel createAddressLine( final ComboBox textField, final int currentField ) {

		MultiFieldPanel result = new MultiFieldPanel();

		Button addressBtton = new Button( textField.getFieldLabel() );
		addressBtton.setMinWidth( LABEL_WIDTH );
		result.addToRow( addressBtton, LABEL_WIDTH + 5 );
		result.addToRow( textField, new ColumnLayoutData( 1.0 ) );

		addressBtton.addListener( new ButtonListenerAdapter() {

			@Override
			public void onClick( Button button, EventObject e ) {

				ContactListWindow window = WindowRegistry.CONTACT_LIST_WINDOW.get( ContactListWindow.class );
				window.getTo().setValue( toText.getValueAsString() );
				window.getCc().setValue( ccText.getValueAsString() );
				window.getBcc().setValue( bccText.getValueAsString() );

				WindowRegistry.CONTACT_LIST_WINDOW.open( currentField );
			}
		} );

		// setWidth("100%") doesn't work
		result.addListener( new ContainerListenerAdapter() {

			@Override
			public void onAfterLayout( Container self ) {

				textField.setWidth( self.getWidth() - LABEL_WIDTH - 5 );
			}
		} );

		return result;
	}

	/**
	 * @param attachments
	 */
	public void setAttachments( GWTAttachment[] attachments ) {

		this.attachmentsPanel.clear();
		if ( attachments != null && attachments.length > 0 ) {
			this.attachmentsPanel.setVisible( true );
			for (GWTAttachment attachment : attachments) {
				this.attachmentsPanel.add( new ComposeAttachmentPanel( attachment, this ) );
			}
		}
		else {
			this.attachmentsPanel.setVisible( false );
		}
		this.attachmentsPanel.doLayout();
		doLayout();
	}

	private String[][] getPriorities() {

		return new String[][] {
				new String[] { String.valueOf( GWTMailConstants.PRIORITY_VERY_HIGH ),
						TextProvider.get().window_compose_message_priority_high() },
				new String[] { String.valueOf( GWTMailConstants.PRIORITY_NORMAL ),
						TextProvider.get().window_compose_message_priority_normal() },
				new String[] { String.valueOf( GWTMailConstants.PRIORITY_VERY_LOW ),
						TextProvider.get().window_compose_message_priority_low() } };
	}

	private class HtmlCheckboxListener extends CheckboxListenerAdapter {

		@Override
		public void onCheck( Checkbox field, boolean checked ) {

			if ( checked ) {
				setHtmlActive();
			}
			else {
				setPlainActive();
			}
		}
	}

	private void setHtmlActive() {

		String plainText = this.plainMessageField.getValueAsString();

		this.textPanelWrapper.setActiveItem( INDEX_HTML_EDITOR );
		this.plainMessageField.setValue( "" );

		if ( plainText != null && plainText.length() > 0 ) {
			ServiceProvider.getMailboxService().convert2Html( plainText, new AsyncCallbackAdapter<String>() {

				@Override
				public void onSuccess( String result ) {

					htmlMessageField.setValue( result );
				}
			} );
		}
	}

	private void setPlainActive() {

		String htmlText = this.htmlMessageField.getValueAsString();

		this.textPanelWrapper.setActiveItem( INDEX_PLAINTEXT_EDITOR );
		this.htmlMessageField.setValue( "" );

		if ( htmlText != null && htmlText.length() > 0 ) {
			ServiceProvider.getMailboxService().convert2PlainText( htmlText, new AsyncCallbackAdapter<String>() {

				@Override
				public void onSuccess( String result ) {

					plainMessageField.setValue( result );
				}
			} );
		}
	}

	public String getMessageText() {

		if ( this.htmlCheck.getValue() ) {
			return this.htmlMessageField.getValueAsString();
		}
		else {
			return this.plainMessageField.getText();
		}
	}

	public GWTMessage getMessage() {

		GWTMessage message = this.message;
		if ( message == null ) {
			message = new GWTMessage();
		}
		message.setTo( this.toText.getValueAsString() );
		message.setCc( this.ccText.getValueAsString() );
		message.setBcc( this.bccText.getValueAsString() );
		message.setSubject( this.subjectText.getValueAsString() );
		message.setMessageText( getMessageText() );
		message.setHtmlMessage( this.htmlCheck.getValue() );
		message.setIdentityId( Long.parseLong( this.fromCombo.getValue() ) );
		message.setAcknowledgement( this.acknowledgementCheck.getValue() );
		message.setPriority( Integer.parseInt( this.priorityCombo.getValue() ) );

		return message;
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

	@Override
	public void hide() {

		super.hide();
		clearFields();
	}

	@Override
	public void show() {

		super.show();
		setFocus();
	}

	/**
	 * 
	 */
	public void mask() {

		if ( getEl() != null ) {
			getEl().mask();
		}
	}

	/**
	 * 
	 */
	public void unmask() {

		if ( getEl() != null ) {
			getEl().unmask();
		}
	}

	/**
	 * @param to
	 */
	public void setTo( String to ) {

		this.toText.setValue( to );
	}

	/**
	 * 
	 */
	private void setFocus() {

		if ( this.message == null ) {
			if ( GWTUtil.hasText( this.toText.getValue() ) ) {
				this.subjectText.focus( false, 200 );
			}
			else {
				this.toText.focus( false, 200 );
			}
		}
		else {
			if ( this.htmlCheck.getValue() ) {
				this.htmlMessageField.focus( false, 200 );
			}
			else {
				this.plainMessageField.focus( false, 200 );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.windows.IGWTWindow#validate()
	 */
	public boolean validate() {

		if ( !GWTUtil.hasText( this.toText.getValue() ) && !GWTUtil.hasText( this.ccText.getValue() )
				&& !GWTUtil.hasText( this.bccText.getValue() ) ) {
			MessageBox.alert( TextProvider.get().window_compose_message_validate_input() );
			return false;
		}
		return true;
	}
}
