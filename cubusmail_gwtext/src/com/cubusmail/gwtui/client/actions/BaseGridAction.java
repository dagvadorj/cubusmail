/* BaseGridAction.java

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

package com.cubusmail.gwtui.client.actions;

import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.RowSelectionListener;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;

/**
 * Base for all grid related actions.
 * 
 * @author Juergen Schlierf
 */
public abstract class BaseGridAction extends GWTAction {

	private RowSelectionModel selectionModel;
	private Store store;

	private RowSelectionListener rowSelectionListener = new RowSelectionListenerAdapter() {

		public void onSelectionChange( RowSelectionModel sm ) {

			selectionModel = sm;
		}
	};

	public BaseGridAction() {

		this( null );
	}

	public BaseGridAction( Store store ) {

		super();
		this.store = store;
	}

	/**
	 * @return Returns the selectionModel.
	 */
	protected RowSelectionModel getSelectionModel() {

		return this.selectionModel;
	}

	/**
	 * @return Returns the rowSelectionListener.
	 */
	public RowSelectionListener getRowSelectionListener() {

		return this.rowSelectionListener;
	}

	protected long[] getSelectedIds() {

		if ( this.selectionModel != null ) {
			Record[] selectedRecords = this.selectionModel.getSelections();
			if ( selectedRecords != null && selectedRecords.length > 0 ) {
				long[] ids = new long[selectedRecords.length];
				for ( int i = 0; i < selectedRecords.length; i++ ) {
					ids[i] = Long.parseLong( selectedRecords[i].getId() );
				}
				return ids;
			}
		} else {
			throw new IllegalStateException( "Missing SelectionModel" );
		}

		return null;
	}

	/**
	 * @return Returns the store.
	 */
	public Store getStore() {

		return this.store;
	}

	/**
	 * @param store The store to set.
	 */
	public void setStore( Store store ) {

		this.store = store;
	}
}
