/* AddressListDataSource.java

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
package com.cubusmail.client.datasource;

import java.util.List;

import com.cubusmail.client.exceptions.GWTExceptionHandler;
import com.cubusmail.client.util.GWTSessionManager;
import com.cubusmail.client.util.ServiceProvider;
import com.cubusmail.common.model.Address;
import com.cubusmail.common.model.AddressFolder;
import com.cubusmail.common.model.AddressListFields;
import com.cubusmail.common.model.GWTConstants;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public class AddressListDataSource extends GwtRpcDataSource {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.datasource.GwtRpcDataSource#executeAdd(java.lang
	 * .String, com.smartgwt.client.data.DSRequest,
	 * com.smartgwt.client.data.DSResponse)
	 */
	@Override
	protected void executeAdd( String requestId, DSRequest request, DSResponse response ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.datasource.GwtRpcDataSource#executeFetch(java.lang
	 * .String, com.smartgwt.client.data.DSRequest,
	 * com.smartgwt.client.data.DSResponse)
	 */
	@Override
	protected void executeFetch( final String requestId, final DSRequest request, final DSResponse response ) {

		AddressFolder folder = GWTSessionManager.get().getCurrentAddressFolder();
		String beginChars = request.getCriteria().getAttribute( GWTConstants.ADDRESS_BEGIN_CHARS );
		ServiceProvider.getUserAccountService().retrieveAddressList( folder, beginChars,
				new AsyncCallback<List<Address>>() {

					@Override
					public void onSuccess( List<Address> result ) {

						mapResponse( response, result );
						response.setStatus( RPCResponse.STATUS_SUCCESS );
						processResponse( requestId, response );
					}

					@Override
					public void onFailure( Throwable caught ) {

						GWTExceptionHandler.handleException( caught );
						response.setStatus( RPCResponse.STATUS_FAILURE );
						processResponse( requestId, response );
					}
				} );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.datasource.GwtRpcDataSource#executeRemove(java.lang
	 * .String, com.smartgwt.client.data.DSRequest,
	 * com.smartgwt.client.data.DSResponse)
	 */
	@Override
	protected void executeRemove( String requestId, DSRequest request, DSResponse response ) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.client.datasource.GwtRpcDataSource#executeUpdate(java.lang
	 * .String, com.smartgwt.client.data.DSRequest,
	 * com.smartgwt.client.data.DSResponse)
	 */
	@Override
	protected void executeUpdate( String requestId, DSRequest request, DSResponse response ) {

	}

	/**
	 * @param response
	 * @param addressList
	 */
	private void mapResponse( DSResponse response, List<Address> addressList ) {

		if ( addressList != null && addressList.size() > 0 ) {
			ListGridRecord[] records = new ListGridRecord[addressList.size()];
			for (int i = 0; i < addressList.size(); i++) {
				records[i] = new ListGridRecord();
				records[i].setAttribute( AddressListFields.ID.name(), addressList.get( i ).getId() );
				records[i].setAttribute( AddressListFields.DISPLAY_NAME.name(), addressList.get( i ).getDisplayName() );
				records[i].setAttribute( AddressListFields.ADDRESS_OBJECT.name(), addressList.get( i ) );
			}

			response.setData( records );
			response.setTotalRows( addressList.size() );
		}
	}
}
