/* DeleteContactAction.java

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
package com.cubusmail.gwtui.client.actions.contact;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.event.StoreListenerAdapter;

import com.cubusmail.gwtui.client.actions.BaseGridAction;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.exceptions.GWTExceptionHandler;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Delete contact.
 * 
 * @author Juergen Schlierf
 */
public class DeleteContactAction extends BaseGridAction implements AsyncCallback<Void> {

	private DeletionStoreListener deletionStoreListener;
	private boolean deletionProcessed;
	private int selectedRowAfterDeletion;

	/**
	 * 
	 */
	public DeleteContactAction() {

		super();
		setText( TextProvider.get().actions_delete_contact_text() );
		setImageName( ImageProvider.CONTACT_DELETE );
		setTooltipText( TextProvider.get().actions_delete_contact_text() );
		this.deletionStoreListener = new DeletionStoreListener();
		if ( getStore() != null ) {
			getStore().addStoreListener( this.deletionStoreListener );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		long[] ids = getSelectedIds();
		if ( ids != null && ids.length > 0 ) {
			Long[] longIds = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				longIds[i] = Long.valueOf( ids[i] );
			}
			this.deletionProcessed = true;
			ServiceProvider.getUserAccountService().deleteContacts( longIds, this );
		}
	}

	public void onSuccess( Void result ) {

		for (int i = 0; i < getStore().getCount(); i++) {
			if ( getSelectionModel().isSelected( i ) ) {
				this.selectedRowAfterDeletion = i;
				break;
			}
		}

		EventBroker.get().fireReloadContacts();
	}

	public void onFailure( Throwable caught ) {

		GWTExceptionHandler.handleException( caught );
		EventBroker.get().fireReloadContacts();
	}

	private class DeletionStoreListener extends StoreListenerAdapter {

		@Override
		public void onLoad( Store store, Record[] records ) {

			if ( deletionProcessed ) {
				getSelectionModel().selectRow( selectedRowAfterDeletion );
				deletionProcessed = false;
				selectedRowAfterDeletion = 0;
			}
		}
	}

	@Override
	public void setStore( Store store ) {

		super.setStore( store );
		store.addStoreListener( this.deletionStoreListener );
	}
}
