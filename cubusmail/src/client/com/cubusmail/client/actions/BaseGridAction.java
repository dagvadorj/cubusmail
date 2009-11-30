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

package com.cubusmail.client.actions;

import com.cubusmail.common.model.MessageListFields;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

/**
 * Base for all grid related actions.
 * 
 * @author Juergen Schlierf
 */
public abstract class BaseGridAction extends GWTAction implements RecordClickHandler {

	private ListGridRecord[] selection;

	/**
	 * Delivers id array of selected messages.
	 * 
	 * @return
	 */
	protected long[] getSelectedIds() {

		if ( selection != null && selection.length > 0 ) {
			long[] ids = new long[selection.length];
			for (int i = 0; i < selection.length; i++) {
				ids[i] = Long.parseLong( selection[i].getAttribute( MessageListFields.ID.name() ) );
			}
			return ids;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.smartgwt.client.widgets.grid.events.RecordClickHandler#onRecordClick
	 * (com.smartgwt.client.widgets.grid.events.RecordClickEvent)
	 */
	public void onRecordClick( RecordClickEvent event ) {

		this.selection = event.getViewer().getSelection();
	}

	public ListGridRecord[] getSelection() {

		return selection;
	}
}
