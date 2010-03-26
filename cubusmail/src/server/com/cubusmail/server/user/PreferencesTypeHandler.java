/* PreferencesTypeHandler.java

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
package com.cubusmail.server.user;

import java.lang.reflect.Field;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.cubusmail.common.model.Preferences;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * iBatis type handler for type Preferences.
 * 
 * @author Juergen Schlierf
 */
public class PreferencesTypeHandler implements TypeHandlerCallback {

	private final Log log = LogFactory.getLog( getClass() );

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#getResult(com
	 * .ibatis.sqlmap.client.extensions.ResultGetter)
	 */
	public Object getResult( ResultGetter getter ) throws SQLException {

		String preferencesJson = getter.getString();
		Preferences preferences = null;

		if ( preferencesJson != null ) {
			preferences = new Preferences();
			try {
				JSONObject object = new JSONObject( preferencesJson );
				Field[] fields = Preferences.class.getFields();
				if ( fields != null ) {
					for (Field field : fields) {
						Object value = object.has( field.getName() ) ? object.get( field.getName() ) : null;
						if ( value != null ) {
							if ( value instanceof Integer ) {
								field.setInt( preferences, ((Integer) value).intValue() );
							}
							else if ( value instanceof Boolean ) {
								field.setBoolean( preferences, ((Boolean) value).booleanValue() );
							}
							else if ( value instanceof String ) {
								field.set( preferences, value );
							}
						}
					}
				}
			}
			catch (JSONException e) {
				log.error( e.getMessage(), e );
			}
			catch (NumberFormatException e) {
				log.error( e.getMessage(), e );
			}
			catch (IllegalArgumentException e) {
				log.error( e.getMessage(), e );
			}
			catch (IllegalAccessException e) {
				log.error( e.getMessage(), e );
			}
		}

		return preferences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#setParameter(
	 * com.ibatis.sqlmap.client.extensions.ParameterSetter, java.lang.Object)
	 */
	public void setParameter( ParameterSetter setter, Object parameter ) throws SQLException {

		if ( parameter != null && parameter instanceof Preferences ) {
			try {
				JSONObject jsonObject = new JSONObject( (Preferences) parameter );
				setter.setString( jsonObject.toString() );
			}
			catch (Exception e) {
				setter.setString( "" );
				log.error( e.getMessage(), e );
			}
		}
		else {
			setter.setString( "" );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#valueOf(java.
	 * lang.String)
	 */
	public Object valueOf( String s ) {

		// TODO Auto-generated method stub
		return null;
	}
}
