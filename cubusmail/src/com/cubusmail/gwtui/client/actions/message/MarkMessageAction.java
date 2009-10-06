/* MarkMessageAction.java

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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.grid.RowSelectionModel;

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.MessageListFields;

/**
 * Mark messages.
 * 
 * @author Jürgen Schlierf
 */
public class MarkMessageAction extends BaseGridAction implements AsyncCallback<Void> {

	private MarkActionType markActionType;

	/**
	 * 
	 */
	public MarkMessageAction( MarkActionType markActionType ) {

		super( null );
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
			ServiceProvider.getMailboxService().markMessage( messsageIds, this.markActionType.getFlagField(),
					this.markActionType.isMark(), this );
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

		RowSelectionModel model = getSelectionModel();
		if ( model != null ) {
			Record[] selectedMsgs = model.getSelections();
			if ( selectedMsgs != null && selectedMsgs.length > 0 ) {
				for (int i = 0; i < selectedMsgs.length; i++) {
					selectedMsgs[i].set( this.markActionType.getFlagField().name(), this.markActionType.isMark() );
				}
			}
		}
		EventBroker.get().fireMessagesChanged();
	}

	/**
	 * Type of message marks.
	 * 
	 * @author Jürgen Schlierf
	 */
	public static enum MarkActionType {
		READ(TextProvider.get().actions_markread_text(), TextProvider.get().actions_markread_tooltip(),
				ImageProvider.MSG_STATUS_READ, MessageListFields.READ_FLAG, true),

		UNREAD(TextProvider.get().actions_markunread_text(), TextProvider.get().actions_markunread_tooltip(),
				ImageProvider.MSG_STATUS_UNREAD, MessageListFields.READ_FLAG, false),

		DELETED(TextProvider.get().actions_markdeleted_text(), TextProvider.get().actions_markdeleted_tooltip(),
				ImageProvider.MSG_STATUS_DELETED, MessageListFields.DELETED_FLAG, true),

		UNDELETED(TextProvider.get().actions_markundeleted_text(), TextProvider.get().actions_markundeleted_tooltip(),
				ImageProvider.MSG_STATUS_READ, MessageListFields.DELETED_FLAG, false);

		private String text;
		private String tooltipText;
		private String image;
		private MessageListFields flagField;
		private boolean mark;

		MarkActionType( String text, String tooptipText, String image, MessageListFields flagField, boolean mark ) {

			this.text = text;
			this.tooltipText = tooptipText;
			this.image = image;
			this.flagField = flagField;
			this.mark = mark;
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

		/**
		 * @return Returns the flagField.
		 */
		public MessageListFields getFlagField() {

			return this.flagField;
		}

		/**
		 * @return Returns the mark.
		 */
		public boolean isMark() {

			return this.mark;
		}
	}
}
