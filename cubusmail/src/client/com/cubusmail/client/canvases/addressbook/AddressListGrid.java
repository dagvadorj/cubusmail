/* AddressListGrid.java

   Copyright (c) 2010 Juergen Schlierf, All Rights Reserved
   
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
package com.cubusmail.client.canvases.addressbook;

import com.cubusmail.client.canvases.CanvasRegistry;
import com.cubusmail.client.datasource.DataSourceRegistry;
import com.cubusmail.client.events.DelayedResizeHandlerProxy;
import com.cubusmail.client.events.EventBroker;
import com.cubusmail.client.events.ReloadAddressListListener;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.AddressListFields;
import com.cubusmail.common.model.GWTConstants;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressListGrid extends ListGrid implements ReloadAddressListListener {

	public AddressListGrid() {

		super();
		setAlternateRecordStyles( true );
		setSelectionType( SelectionStyle.MULTIPLE );
		setSelectionAppearance( SelectionAppearance.ROW_STYLE );
		setAutoFetchData( false );
		setDataSource( DataSourceRegistry.ADDRESS_LIST.get() );
		setShowHeaderContextMenu( false );
		setFields( generateField() );
		setShowResizeBar( true );
		setCanDrag( true );
		setDragTarget( CanvasRegistry.ADDRESS_FOLDER_CANVAS.get() );
		setCanDragRecordsOut( true );

		addResizedHandler( DelayedResizeHandlerProxy.get( new ResizedHandler() {

			public void onResized( ResizedEvent event ) {

				resizeField();
			}
		} ) );

		addSelectionChangedHandler( new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged( SelectionEvent event ) {

				Address address = (Address) event.getRecord().getAttributeAsObject(
						AddressListFields.ADDRESS_OBJECT.name() );
				if ( !(GWTSessionManager.get().getCurrentAddress() != null && GWTSessionManager.get()
						.getCurrentAddress().equals( address )) ) {
					GWTSessionManager.get().setCurrentAddress( address );
					CanvasRegistry.ADDRESS_DETAILS.get( AddressDetailsCanvas.class ).setAddress( address );
					GWT.log( address.toString() );
				}
			}
		} );

		EventBroker.get().addReloadAddressListListener( this );
	}

	/**
	 * @return
	 */
	private ListGridField generateField() {

		ListGridField nameField = new ListGridField( AddressListFields.DISPLAY_NAME.name(), TextProvider.get()
				.contact_list_panel_col_name() );
		nameField.setAlign( Alignment.LEFT );
		nameField.setCanGroupBy( false );
		nameField.setShowGridSummary( false );
		nameField.setCanFreeze( false );

		return nameField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.events.ReloadAddressListListener#onReloadAddressList
	 * (com.cubusmail.common.model.AddressFolder)
	 */
	public void onReloadAddressList( AddressFolder folder, String beginChars ) {

		CanvasRegistry.ADDRESS_EDIT.get( AddressEditCanvas.class ).setAddress( null );
		Criteria criteria = new Criteria();
		criteria.addCriteria( GWTConstants.ADDRESS_FOLDER_ID, String.valueOf( folder.getId() ) );
		criteria.addCriteria( GWTConstants.ADDRESS_BEGIN_CHARS, beginChars );
		fetchData( criteria );
	}

	/**
	 * 
	 */
	private void resizeField() {

		int width = getWidth() - 2 - getScrollbarSize();
		resizeField( 0, width );
	}
}
