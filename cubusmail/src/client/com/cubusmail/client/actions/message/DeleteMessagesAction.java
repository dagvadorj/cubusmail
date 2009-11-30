/* DeleteMessagesAction.java

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

import com.cubusmail.client.actions.ActionRegistry;
import com.cubusmail.client.actions.BaseGridAction;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.ImageProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Action for moving messages.
 * 
 * @author Juergen Schlierf
 */
public class DeleteMessagesAction extends BaseGridAction implements AsyncCallback<Void> {

	private boolean deletionProcessed;
	private int selectedRowAfterDeletion;

	/**
	 * 
	 */
	public DeleteMessagesAction() {

		super( );
//		this.deletionStoreListener = new DeletionStoreListener();
//		if ( getStore() != null ) {
//			getStore().addStoreListener( this.deletionStoreListener );
//		}
		setText( TextProvider.get().actions_deletemessage_text() );
		setImageName( ImageProvider.MSG_DELETE );
		setTooltipText( TextProvider.get().actions_deletemessage_tooltip() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	public void execute() {

		if ( GWTSessionManager.get().getPreferences().isMarkAsDeletedWithoutTrash()
				&& GWTSessionManager.get().getMailbox().getTrashFolder() == null ) {
			ActionRegistry.MARK_AS_DELETED.get().execute();
		} else {
			long[] messageIds = getSelectedIds();
			if ( messageIds != null && messageIds.length > 0 ) {
				this.deletionProcessed = true;
				ServiceProvider.getMailboxService().deleteMessages( messageIds, this );
			}
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
		this.deletionProcessed = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess( Void result ) {

//		for ( int i = 0; i < getStore().getCount(); i++ ) {
//			if ( getSelectionModel().isSelected( i ) ) {
//				this.selectedRowAfterDeletion = i;
//				break;
//			}
//		}

		EventBroker.get().fireMessagesChanged();
		EventBroker.get().fireMessagesReload();
	}

//	private class DeletionStoreListener extends StoreListenerAdapter {
//
//		@Override
//		public void onLoad( Store store, Record[] records ) {
//
//			if ( deletionProcessed ) {
//				getSelectionModel().selectRow( selectedRowAfterDeletion );
//				deletionProcessed = false;
//				selectedRowAfterDeletion = 0;
//			}
//		}
//	}
//
//	@Override
//	public void setStore( Store store ) {
//
//		super.setStore( store );
//		store.addStoreListener( this.deletionStoreListener );
//	}
}
