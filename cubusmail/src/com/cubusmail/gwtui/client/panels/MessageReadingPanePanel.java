/* MessageReadingPanePanel.java

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

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.RowLayout;
import com.gwtext.client.widgets.layout.RowLayoutData;
import com.gwtextux.client.widgets.ManagedIFramePanel;

import com.cubusmail.gwtui.client.events.CleanReadingPaneListener;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.events.FolderSelectedListener;
import com.cubusmail.gwtui.client.events.MessageSelectedListener;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.model.GWTMessage;
import com.cubusmail.gwtui.client.services.GWTSessionManager;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.widgets.ImageHyperlink;

/**
 * Panel for reading a single message.
 * 
 * @author Jürgen Schlierf
 */
public class MessageReadingPanePanel extends Panel implements MessageSelectedListener, CleanReadingPaneListener,
		FolderSelectedListener, AsyncCallback<GWTMessage> {

	private static final Image WARNING_IMAGE = new Image( GWT.getHostPageBaseURL() + ImageProvider.MSG_IMAGES_WARNING );

	private final static int LABEL_WIDTH = 80;

	private Panel topBanner;

	private EmailAddressLinePanel fromPanel;
	private EmailAddressLinePanel toPanel;
	private EmailAddressLinePanel ccPanel;
	private EmailAddressLinePanel replyToPanel;
	private HTMLPanel datePanel;
	private HTMLPanel subjectPanel;
	private Panel attachmentPanel;

	private Panel fromLine;
	private Panel toLine;
	private Panel ccLine;
	private Panel replyToLine;
	private Panel dateLine;
	private Panel subjectLine;
	private Panel attachmentLine;
	private Panel imageLoadLine;

	private ManagedIFramePanel contentPanel;

	private GWTMessage currentMessage;

	private HashMap<Long, Boolean> messagesImageLoaded;

	// private HashMap<Long, Boolean> messagesAcknoleged;

	/**
	 * 
	 */
	public MessageReadingPanePanel() {

		super();
		setLayout( new BorderLayout() );

		setPaddings( 5 );
		setBorder( false );
		setFrame( false );
		setAutoScroll( true );

		createTopBanner();

		BorderLayoutData topData = new BorderLayoutData( RegionPosition.NORTH );
		topData.setFloatable( true );
		topData.setMinHeight( 0 );
		add( this.topBanner, topData );

		this.contentPanel = new ManagedIFramePanel();
		this.contentPanel.setFrame( false );
		this.contentPanel.setBorder( false );
		this.contentPanel.setAutoScroll( false );
		this.contentPanel.setAutoHeight( true );

		BorderLayoutData centerData = new BorderLayoutData( RegionPosition.CENTER );
		centerData.setMinHeight( 0 );
		add( this.contentPanel, centerData );

		addListener( new ContainerListenerAdapter() {

			@Override
			public void onAfterLayout( Container self ) {

				topBanner.doLayout();
				attachmentLine.doLayout();
				contentPanel.doLayout();
			}
		} );

		EventBroker.get().addMessageSelectedListener( this );
		EventBroker.get().addCleanReadingPaneListener( this );
		EventBroker.get().addFolderSelectedListener( this );

		setBodyStyle( "background-color: #FFFFFF" );

		this.messagesImageLoaded = new HashMap<Long, Boolean>();
		// this.messagesAcknoleged = new HashMap<Long, Boolean>();
	}

	/**
	 * 
	 */
	private void createTopBanner() {

		this.topBanner = new Panel();
		this.topBanner.setBorder( false );
		this.topBanner.setFrame( true );
		this.topBanner.setLayout( new RowLayout() );
		this.topBanner.setHeader( false );
		this.topBanner.setAutoHeight( true );

		this.subjectLine = new Panel();
		this.subjectLine.setPaddings( 1, 0, 0, 2 );
		this.subjectLine.setAutoHeight( true );
		this.subjectLine.setLayout( new ColumnLayout() );
		this.subjectPanel = new HTMLPanel();
		this.subjectPanel.addStyleName( "message-subject" );
		this.subjectLine.add( this.subjectPanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.subjectLine, new RowLayoutData() );
		this.subjectLine.setVisible( false );

		this.fromLine = new Panel();
		this.fromLine.setPaddings( 1, 0, 0, 2 );
		this.fromLine.setLayout( new ColumnLayout() );
		this.fromLine.setAutoHeight( true );
		HTMLPanel label = new HTMLPanel( "<b>" + TextProvider.get().message_reading_pane_panel_from() + "<b>" );
		label.setWidth( LABEL_WIDTH );
		this.fromLine.add( label );
		this.fromPanel = new EmailAddressLinePanel();
		this.fromLine.add( this.fromPanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.fromLine, new RowLayoutData() );
		this.fromLine.setVisible( false );

		this.toLine = new Panel();
		this.toLine.setPaddings( 1, 0, 0, 2 );
		this.toLine.setLayout( new ColumnLayout() );
		this.toLine.setAutoHeight( true );
		label = new HTMLPanel( "<b>" + TextProvider.get().message_reading_pane_panel_to() + "<b>" );
		label.setWidth( LABEL_WIDTH );
		this.toLine.add( label );
		this.toPanel = new EmailAddressLinePanel();
		this.toLine.add( this.toPanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.toLine, new RowLayoutData() );
		this.toLine.setVisible( false );

		this.ccLine = new Panel();
		this.ccLine.setPaddings( 1, 0, 0, 2 );
		this.ccLine.setAutoHeight( true );
		this.ccLine.setLayout( new ColumnLayout() );
		label = new HTMLPanel( "<b>" + TextProvider.get().message_reading_pane_panel_cc() + "<b>" );
		label.setWidth( LABEL_WIDTH );
		this.ccLine.add( label );
		this.ccPanel = new EmailAddressLinePanel();
		this.ccLine.add( this.ccPanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.ccLine, new RowLayoutData() );
		this.ccLine.setVisible( false );

		this.replyToLine = new Panel();
		this.replyToLine.setPaddings( 1, 0, 0, 2 );
		this.replyToLine.setAutoHeight( true );
		this.replyToLine.setLayout( new ColumnLayout() );
		label = new HTMLPanel( "<b>" + TextProvider.get().message_reading_pane_panel_replyto() + "<b>" );
		label.setWidth( LABEL_WIDTH );
		this.replyToLine.add( label );
		this.replyToPanel = new EmailAddressLinePanel();
		this.replyToLine.add( this.replyToPanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.replyToLine, new RowLayoutData() );
		this.replyToLine.setVisible( false );

		this.dateLine = new Panel();
		this.dateLine.setPaddings( 1, 0, 0, 2 );
		this.dateLine.setAutoHeight( true );
		this.dateLine.setLayout( new ColumnLayout() );
		label = new HTMLPanel( "<b>" + TextProvider.get().message_reading_pane_panel_date() + "<b>" );
		label.setWidth( LABEL_WIDTH );
		this.dateLine.add( label );
		this.datePanel = new HTMLPanel();
		this.datePanel.setHtml( GWTUtil.formatDate( new Date() ) );
		this.dateLine.add( this.datePanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.dateLine, new RowLayoutData() );
		this.dateLine.setVisible( false );

		this.attachmentLine = new Panel();
		this.attachmentLine.setAutoHeight( true );
		this.attachmentLine.setBorder( false );
		this.attachmentLine.setFrame( false );
		this.attachmentLine.setPaddings( 1, 0, 0, 2 );
		this.attachmentLine.setLayout( new ColumnLayout() );
		label = new HTMLPanel( "<b>" + TextProvider.get().message_reading_pane_panel_attachment() + "<b>" );
		label.setWidth( LABEL_WIDTH );
		this.attachmentLine.add( label );
		this.attachmentPanel = new Panel();
		this.attachmentPanel.setLayout( new ColumnLayout() );
		this.attachmentLine.add( this.attachmentPanel, new ColumnLayoutData( 1.0 ) );
		this.topBanner.add( this.attachmentLine, new RowLayoutData() );
		this.attachmentLine.setVisible( false );

		this.imageLoadLine = new Panel();
		this.imageLoadLine.setAutoHeight( true );
		this.imageLoadLine.setBorder( false );
		this.imageLoadLine.setFrame( false );
		this.imageLoadLine.setPaddings( 1, 0, 0, 2 );
		this.imageLoadLine.setLayout( new ColumnLayout() );
		this.imageLoadLine.add( new ImagesNotLoadedWarning() );
		this.topBanner.add( this.imageLoadLine, new RowLayoutData() );
		this.imageLoadLine.setVisible( false );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.MessageSelectedListener#onMessageSelected
	 * (java.lang.String)
	 */
	public void onMessageSelected( int messageId ) {

		if ( isVisible() ) {
			Boolean loadImages = this.messagesImageLoaded.get( Long.valueOf( messageId ) );
			if ( loadImages != null ) {
				loadMessage( messageId, loadImages.booleanValue() );
			}
			else {
				loadMessage( messageId, false );
			}
		}
	}

	private void loadMessage( long messageId, boolean loadImages ) {

		PanelRegistry.MESSAGE_READING_PANE_PREVIEW.mask();
		ServiceProvider.getMailboxService().retrieveMessage( null, messageId, loadImages, this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		clearContent();
		PanelRegistry.MESSAGE_READING_PANE_PREVIEW.unmask();
		GWTExceptionHandler.handleException( caught );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( GWTMessage message ) {

		EventBroker.get().fireMessageLoaded( message );
		GWTSessionManager.get().setCurrentMessage( message );
		setMessage( message );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		this.currentMessage = message;

		if ( !this.topBanner.isVisible() ) {
			this.topBanner.setVisible( true );
		}

		if ( !GWTUtil.isEmpty( message.getFromArray() ) ) {
			this.fromPanel.setAddresses( message.getFromArray() );
			this.fromLine.setVisible( true );
		}
		else {
			this.fromLine.setVisible( false );
		}

		if ( !GWTUtil.isEmpty( message.getToArray() ) ) {
			this.toPanel.setAddresses( message.getToArray() );
			this.toLine.setVisible( true );
		}
		else {
			this.toLine.setVisible( false );
		}

		if ( !GWTUtil.isEmpty( message.getCcArray() ) ) {
			this.ccPanel.setAddresses( message.getToArray() );
			this.ccLine.setVisible( true );
		}
		else {
			this.ccLine.setVisible( false );
		}

		if ( !GWTUtil.isEmpty( message.getReplyToArray() ) ) {
			this.replyToPanel.setAddresses( message.getReplyToArray() );
			this.replyToLine.setVisible( true );
		}
		else {
			this.replyToLine.setVisible( false );
		}

		if ( message.getDate() != null ) {
			this.datePanel.setHtml( GWTUtil.formatDate( message.getDate() ) );
			this.dateLine.setVisible( true );
		}
		else {
			this.dateLine.setVisible( false );
		}

		if ( GWTUtil.hasText( Format.htmlEncode( message.getSubject() ) ) ) {
			this.subjectPanel.setHtml( message.getSubject() );
			this.subjectLine.setVisible( true );
		}
		else {
			this.subjectLine.setVisible( false );
		}

		if ( message.getAttachments().length > 0 ) {
			refreshAttachments( message.getAttachments() );
			this.attachmentLine.setVisible( true );
		}
		else {
			this.attachmentLine.setVisible( false );
		}

		if ( message.isHasImages() && !message.isTrustImages()
				&& this.messagesImageLoaded.get( Long.valueOf( message.getId() ) ) == null ) {
			this.imageLoadLine.setVisible( true );
		}
		else {
			this.imageLoadLine.setVisible( false );
		}

		this.contentPanel.setHtml( "" ); // reset scroll
		if ( this.contentPanel.getBody() != null ) {
			this.contentPanel.getBody().repaint();
		}
		this.contentPanel.setHtml( message.getMessageText() );
		this.contentPanel.setVisible( true );

		doLayout();

		PanelRegistry.MESSAGE_READING_PANE_PREVIEW.unmask();

		EventBroker.get().fireMessagesChanged();

		// if ( message.isAcknowledgement() && !message.isReadBefore()
		// && this.messagesAcknoleged.get( Long.valueOf( currentMessage.getId()
		// ) ) == null ) {
		// this.messagesAcknoleged.put( Long.valueOf( currentMessage.getId() ),
		// Boolean.TRUE );
		// MessageBox.confirm(
		// TextProvider.get().message_reading_pane_panel_acknolegement_title(),
		// TextProvider.get()
		// .message_reading_pane_panel_acknolegement_text() );
		// }
	}

	/**
	 * Make the reading panel clean.
	 */
	private void clearContent() {

		this.currentMessage = null;

		this.topBanner.setVisible( false );
		this.fromLine.setVisible( false );
		this.fromPanel.clear();
		this.toLine.setVisible( false );
		this.toPanel.clear();
		this.ccLine.setVisible( false );
		this.ccPanel.clear();
		this.replyToLine.setVisible( false );
		this.replyToPanel.clear();
		this.dateLine.setVisible( false );
		this.subjectLine.setVisible( false );
		this.attachmentPanel.clear();
		this.attachmentLine.setVisible( false );
		this.contentPanel.setHtml( "" );
		this.contentPanel.setVisible( false );

		doLayout();
	}

	private void refreshAttachments( GWTAttachment[] attachments ) {

		this.attachmentPanel.clear();
		for (int i = 0; i < attachments.length; i++) {
			this.attachmentPanel.add( new AttachmentPanel( attachments[i] ) );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.cubusmail.gwtui.client.events.CleanReadingPaneListener#
	 * onReadingPaneCleared()
	 */
	public void onReadingPaneCleared() {

		clearContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.FolderSelectedListener#onFolderSelected
	 * (com.cubusmail.gwtui.client.model.GWTMailFolder)
	 */
	public void onFolderSelected( GWTMailFolder mailFolder ) {

		this.messagesImageLoaded = new HashMap<Long, Boolean>();
	}

	/**
	 * Warning hint for image messages.
	 * 
	 * @author Jürgen Schlierf
	 */
	private class ImagesNotLoadedWarning extends Composite {

		public ImagesNotLoadedWarning() {

			super();

			FlowPanel panel = new FlowPanel();
			initWidget( panel );
			DOM.setStyleAttribute( panel.getElement(), "whiteSpace", "nowrap" );
			DOM.setStyleAttribute( getElement(), "whiteSpace", "nowrap" );

			Hyperlink link = new Hyperlink( "<b>Grafiken laden</b>", true, "#" );
			link.addClickListener( new ClickListener() {

				public void onClick( Widget sender ) {

					loadMessage( currentMessage.getId(), true );
					messagesImageLoaded.put( Long.valueOf( currentMessage.getId() ), Boolean.TRUE );
				}
			} );

			ImageHyperlink imageLlink = new ImageHyperlink( WARNING_IMAGE );
			panel.add( imageLlink );
			HTML warningText = new HTML( "&nbsp;<b>" + TextProvider.get().message_reading_pane_panel_warning()
					+ "</b>&nbsp;" );
			panel.add( warningText );
			panel.add( link );
		}
	}

	public GWTMessage getCurrentMessage() {

		return this.currentMessage;
	}
}
