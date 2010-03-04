/* MessageReadingPaneHeader.java

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
package com.cubusmail.client.canvases.mail;

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.client.util.UIFactory;
import com.cubusmail.common.model.GWTMailConstants;
import com.cubusmail.common.model.GWTMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;

/**
 * Header for message reading pane.
 * 
 * @author Juergen Schlierf
 */
public class MessageReadingPaneHeader extends VLayout {

	private Label subject;
	private EmailAddressLine from;
	private EmailAddressLine to;
	private EmailAddressLine cc;
	private EmailAddressLine replyTo;
	private HLayout dateLine;
	private Label date;
	private AttachmentLine attachmentLine;

	public MessageReadingPaneHeader() {

		super();
		setStyleName( "messageReadingPaneHeader" );
		setWidth100();
		setPadding( 4 );
		setOverflow( Overflow.VISIBLE );
		setShowEdges( true );

		this.subject = new Label( "" );
		this.subject.setWidth100();
		this.subject.setAutoHeight();
		this.subject.setStyleName( "message-subject" );
		this.subject.setOverflow( Overflow.CLIP_H );

		this.from = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_from() );
		this.to = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_to() );
		this.cc = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_cc() );
		this.replyTo = new EmailAddressLine( TextProvider.get().message_reading_pane_panel_replyto() );

		this.dateLine = new HLayout();
		this.dateLine.setWidth100();
		this.dateLine.setAutoHeight();
		this.dateLine.setVisible( false );
		this.date = new Label( "" );
		this.date.setWidth100();
		this.date.setAutoHeight();
		Label dateLabel = new Label( TextProvider.get().message_reading_pane_panel_date() );
		dateLabel.setWidth( GWTMailConstants.MESSAGE_READING_PANE_LABEL_WIDTH );
		dateLabel.setAutoHeight();
		this.dateLine.setMembers( dateLabel, this.date );

		this.attachmentLine = new AttachmentLine();

		WidgetCanvas widgetCanvas = new WidgetCanvas( this.attachmentLine );
		widgetCanvas.setWidth100();
		widgetCanvas.setAutoHeight();
		widgetCanvas.setOverflow( Overflow.CLIP_H );

		setMembers( this.subject, this.from, this.to, this.cc, this.replyTo, this.dateLine, widgetCanvas );

		addDrawHandler( new DrawHandler() {

			public void onDraw( DrawEvent event ) {

				GWT.log( "Draw", null );
				layoutChildren( "test" );
				parentResized();
				EmailContextMenu emailContextMenu = new EmailContextMenu();
				emailContextMenu.setParentElement( (VLayout) event.getSource() );
				from.setContextMenu( emailContextMenu );
				to.setContextMenu( emailContextMenu );
				cc.setContextMenu( emailContextMenu );
			}
		} );

		Window.addResizeHandler( new ResizeHandler() {

			public void onResize( ResizeEvent event ) {

				GWT.log( event.toDebugString() );
			}
		} );
	}

	/**
	 * @param message
	 */
	public void setMessage( GWTMessage message ) {

		this.attachmentLine.clear();
		this.subject.setContents( "" );

		if ( GWTUtil.hasText( message.getSubject() ) ) {
			this.subject.setContents( GWTUtil.htmlEncode( message.getSubject() ) );
		}
		if ( !GWTUtil.isEmpty( message.getFromArray() ) ) {
			this.from.setVisible( true );
			this.from.setAddresses( message.getFromArray() );
		}
		else {
			this.from.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getToArray() ) ) {
			this.to.setVisible( true );
			this.to.setAddresses( message.getToArray() );
		}
		else {
			this.to.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getCcArray() ) ) {
			this.cc.setVisible( true );
			this.cc.setAddresses( message.getCcArray() );
		}
		else {
			this.cc.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getReplyToArray() ) ) {
			this.replyTo.setVisible( true );
			this.replyTo.setAddresses( message.getReplyToArray() );
		}
		else {
			this.replyTo.setVisible( false );
		}
		if ( message.getDate() != null ) {
			this.date.setContents( message.getDate().toGMTString() );
			this.dateLine.setVisible( true );
		}
		else {
			this.dateLine.setVisible( false );
		}
		if ( !GWTUtil.isEmpty( message.getAttachments() ) ) {
			this.attachmentLine.setAttachments( message.getAttachments() );
			this.attachmentLine.setVisible( true );
		}
		else {
			this.attachmentLine.clear();
			this.attachmentLine.setHeight( "0px" );
			this.attachmentLine.setVisible( false );
		}

		this.reflowNow();

		int height = this.subject.getOffsetHeight() + this.from.getOffsetHeight() + this.to.getOffsetHeight()
				+ this.cc.getOffsetHeight() + this.date.getOffsetHeight() + attachmentLine.getOffsetHeight();
		setHeight( height + 20 );
	}

	@Override
	protected void onDraw() {

		GWT.log( "Draw", null );
		super.onDraw();
	}

	private class EmailContextMenu extends Menu {

		public EmailContextMenu() {

			super();
			addItem( UIFactory.createMenuItem( ActionRegistry.ADD_CONTACT_FROM_EMAILADDRESS ) );
			addItem( UIFactory.createMenuItem( ActionRegistry.COMPOSE_MESSAGE_FOR_EMAIL ) );

			addItemClickHandler( new ItemClickHandler() {

				public void onItemClick( ItemClickEvent event ) {

					hide();
				}
			} );

			addMouseOutHandler( new MouseOutHandler() {

				public void onMouseOut( MouseOutEvent event ) {

					if ( isVisible() ) {
						hide();
					}
				}
			} );
		}
	}
}
