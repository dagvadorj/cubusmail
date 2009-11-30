/* MarkMessageAction.java

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
package com.cubusmail.client.actions.message;

import com.cubusmail.client.actions.BaseGridAction;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.ImageProvider;
import com.cubusmail.common.model.MessageFlags;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Mark messages.
 * 
 * @author Juergen Schlierf
 */
public class MarkMessageAction extends BaseGridAction implements AsyncCallback<Void> {

	private MarkActionType markActionType;

	/**
	 * 
	 */
	public MarkMessageAction( MarkActionType markActionType ) {

		super();
		this.markActionType = markActionType;

		setText( this.markActionType.getText() );
		setTooltipText( this.markActionType.getTooltipText() );
		setImageName( this.markActionType.getImage() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		long[] messsageIds = getSelectedIds();
		if ( messsageIds != null && messsageIds.length > 0 ) {
			ServiceProvider.getMailboxService().markMessage( messsageIds, this.markActionType.getFlag(), this );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
	 * )
	 */
	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
		EventBroker.get().fireMessagesReload();
		EventBroker.get().fireMessagesChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

		if ( getSelection() != null && getSelection().length > 0 ) {
			for (int i = 0; i < getSelection().length; i++) {
				// getSelection()[i].set( MessageListFields.FLAG_IMAGE.name(),
				// this.markActionType.getImage() );
			}
		}
		EventBroker.get().fireMessagesChanged();
	}

	/**
	 * Type of message marks.
	 * 
	 * @author Juergen Schlierf
	 */
	public static enum MarkActionType {
		READ(TextProvider.get().actions_markread_text(), TextProvider.get().actions_markread_tooltip(),
				ImageProvider.MSG_STATUS_READ, MessageFlags.READ, true),

		UNREAD(TextProvider.get().actions_markunread_text(), TextProvider.get().actions_markunread_tooltip(),
				ImageProvider.MSG_STATUS_UNREAD, MessageFlags.UNREAD, false),

		DELETED(TextProvider.get().actions_markdeleted_text(), TextProvider.get().actions_markdeleted_tooltip(),
				ImageProvider.MSG_STATUS_DELETED, MessageFlags.DELETED, true),

		UNDELETED(TextProvider.get().actions_markundeleted_text(), TextProvider.get().actions_markundeleted_tooltip(),
				ImageProvider.MSG_STATUS_READ, MessageFlags.UNDELETED, false);

		private String text;
		private String tooltipText;
		private String image;
		private MessageFlags flag;

		MarkActionType( String text, String tooptipText, String image, MessageFlags flag, boolean mark ) {

			this.text = text;
			this.tooltipText = tooptipText;
			this.image = image;
			this.flag = flag;
		}

		/**
		 * @return Returns the text.
		 */
		public String getText() {

			return this.text;
		}

		/**
		 * @return Returns the tooltipText.
		 */
		public String getTooltipText() {

			return this.tooltipText;
		}

		/**
		 * @return Returns the image.
		 */
		public String getImage() {

			return this.image;
		}

		public MessageFlags getFlag() {

			return flag;
		}
	}
}
