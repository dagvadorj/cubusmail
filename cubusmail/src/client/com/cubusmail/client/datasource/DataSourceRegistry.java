/* DataSourceRegestry.java

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
package com.cubusmail.client.datasource;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DataSource;

/**
 * Regestry for all DataSources in Cubusmail.
 * 
 * @author Juergen Schlierf
 */
public enum DataSourceRegistry {
	
	MAIL_FOLDER, MESSAGE_LIST,

	ADDRESS_FOLDER, ADDRESS_LIST;

	private static Map<DataSourceRegistry, DataSource> DATASOURCE_MAP = new HashMap<DataSourceRegistry, DataSource>();

	/**
	 * @return
	 */
	public DataSource get() {

		DataSource result = DATASOURCE_MAP.get( this );
		if ( result == null ) {
			result = create();
			DATASOURCE_MAP.put( this, result );
		}
		return result;
	}

	/**
	 * create data sources
	 */
	private DataSource create() {

		switch (this) {
		case MAIL_FOLDER:
			return new MailfolderDataSource();
		case MESSAGE_LIST:
			return new MessageListDataSource();
			
		case ADDRESS_FOLDER:
			return new AddressFolderDataSource();
		case ADDRESS_LIST:
			return new AddressListDataSource();
		}

		throw new IllegalArgumentException( "DataSource missing: " + name() );
	}

	/**
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends DataSource> T get( Class<T> type ) {

		// type.cast() is not pssible with GWT
		return (T) get();
	}
}
