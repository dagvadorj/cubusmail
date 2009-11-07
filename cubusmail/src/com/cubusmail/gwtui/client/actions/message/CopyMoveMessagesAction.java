/* CopyMoveMessagesAction.java

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
package com.cubusmail.gwtui.client.actions.message;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.model.GWTMailFolder;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.client.windows.MailFolderWindow;
import com.cubusmail.gwtui.client.windows.WindowRegistry;

/**
 * Action for copying and moving messages.
 * 
 * @author Juergen Schlierf
 */
public class CopyMoveMessagesAction extends BaseGridAction implements AsyncCallback<Void> {

	private boolean toMove;
	private ButtonListenerAdapter okButtonListener;

	/**
	 * 
	 */
	public CopyMoveMessagesAction( boolean toMove ) {

		super( null );
		this.toMove = toMove;
		if ( toMove ) {
			setText( TextProvider.get().actions_movemessage_text() );
			setImageName( ImageProvider.MSG_MOVE );
			setTooltipText( TextProvider.get().actions_movemessage_tooltip() );
		}
		else {
			setText( TextProvider.get().actions_copymessages_text() );
			setImageName( ImageProvider.MSG_COPY );
			setTooltipText( TextProvider.get().actions_copymessage_tooltip() );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		if ( this.toMove ) {
			if ( this.okButtonListener == null ) {
				MailFolderWindow dialog = WindowRegistry.MOVE_MESSAGES_WINDOW.get( MailFolderWindow.class );
				this.okButtonListener = new OKButtonListener( dialog );
				dialog.addOKButtonListener( this.okButtonListener );
			}
			WindowRegistry.MOVE_MESSAGES_WINDOW.open();
		}
		else {
			if ( this.okButtonListener == null ) {
				MailFolderWindow dialog = WindowRegistry.COPY_MESSAGES_WINDOW.get( MailFolderWindow.class );
				this.okButtonListener = new OKButtonListener( dialog );
				dialog.addOKButtonListener( this.okButtonListener );
			}
			WindowRegistry.COPY_MESSAGES_WINDOW.open();
		}
	}

	/**
	 * For external use too.
	 * 
	 * @param targedFolder
	 */
	public void copyMoveMessagesTo( GWTMailFolder targedFolder, long[] messageIds ) {

		if ( messageIds != null && messageIds.length > 0 ) {
			ServiceProvider.getMailboxService().copyMoveMessages( messageIds, targedFolder.getId(), this.toMove, this );
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

		if ( this.toMove ) {
			Record[] records = getSelectionModel().getSelections();
			if ( records != null && records.length > 0 ) {
				for (int i = 0; i < records.length; i++) {
					this.getStore().remove( records[i] );
				}
			}
		}
		if ( this.toMove ) {
			EventBroker.get().fireMessagesReload();
		}
		EventBroker.get().fireMessagesChanged();
	}

	/**
	 * TODO: documentation
	 * 
	 * @author Juergen Schlierf
	 */
	private class OKButtonListener extends ButtonListenerAdapter {

		private MailFolderWindow dialog;

		private OKButtonListener( MailFolderWindow dialog ) {

			super();
			this.dialog = dialog;
		}

		public void onClick( Button button, EventObject e ) {

			if ( dialog.getSelectedTreeNode() != null ) {
				dialog.close();
				if ( dialog.getSelectedTreeNode().getUserObject() instanceof GWTMailFolder ) {
					GWTMailFolder targetFolder = (GWTMailFolder) dialog.getSelectedTreeNode().getUserObject();
					long[] messageIds = getSelectedIds();
					copyMoveMessagesTo( targetFolder, messageIds );
				}
				else {
					MessageBox.alert( TextProvider.get().actions_copymovemessage_alert() );
				}

			}
			else {
				MessageBox.alert( TextProvider.get().actions_copymovemessage_alert() );
			}
		}
	}
}
