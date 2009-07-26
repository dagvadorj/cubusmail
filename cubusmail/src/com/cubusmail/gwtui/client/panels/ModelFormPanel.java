/* ModelFormPanel.java

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
package com.cubusmail.gwtui.client.panels;

import com.google.gwt.core.client.GWT;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Method;
import com.totsp.gwittir.client.beans.Property;

/**
 * Base form panel. It maps values between form panel and POJOs via reflection.
 * 
 * @author Jürgen Schlierf
 */
public class ModelFormPanel<T extends Object> extends FormPanel {

	private T model;
	private BeanDescriptor beanDescriptor;

	/**
	 * @return Returns the model.
	 */
	public T getModel() {

		return this.model;
	}

	/**
	 * @param model
	 *            The model to set.
	 */
	public void setModel( T model ) {

		this.model = model;
		try {
			this.beanDescriptor = Introspector.INSTANCE.getDescriptor( this.model );
		}
		catch (Exception e) {
			GWT.log( e.getMessage(), e );
			// Log.error( e.getMessage(), e );
			throw new RuntimeException( e );
		}
	}

	/**
	 * 
	 */
	public void updateForm() {

		if ( this.model != null ) {
			Field[] formFields = getFields();
			if ( formFields != null && formFields.length > 0 ) {

				for (Field formField : formFields) {
					String fieldName = formField.getName();

					if ( fieldName != null ) {
						Property modelProperty = this.beanDescriptor.getProperty( fieldName );

						if ( modelProperty != null ) {
							Method accessorMethod = modelProperty.getAccessorMethod();
							Object value = null;
							try {
								value = accessorMethod.invoke( this.model, null );
								if ( value == null ) {
									value = "";
								}
							}
							catch (Exception e) {
								value = "";
							}

							if ( formField instanceof Checkbox ) {
								((Checkbox) formField).setValue( Boolean.parseBoolean( value.toString() ) );
							}
							else {
								formField.setValue( value.toString() );
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	public void updateModel() {

		if ( this.model != null ) {
			Field[] formFields = getFields();
			if ( formFields != null && formFields.length > 0 ) {

				Object[] args = new Object[1];
				for (Field formField : formFields) {
					String fieldName = formField.getName();

					if ( fieldName != null ) {
						Property modelProperty = this.beanDescriptor.getProperty( fieldName );

						if ( modelProperty != null ) {
							String fieldValue = null;
							if ( formField instanceof ComboBox ) {
								fieldValue = ((ComboBox) formField).getValue();
							}
							else {
								fieldValue = formField.getValueAsString();
							}

							Class<?> propertyType = modelProperty.getType();
							if ( propertyType.equals( String.class ) ) {
								args[0] = fieldValue;
							}
							else if ( "int".equals( propertyType.getName() ) ) {
								if ( fieldValue.length() > 0 ) {
									args[0] = Integer.parseInt( fieldValue );
								}
								else {
									args[0] = 0;
								}
							}
							else if ( "long".equals( propertyType.getName() ) ) {
								if ( fieldValue.length() > 0 ) {
									args[0] = Long.parseLong( fieldValue );
								}
								else {
									args[0] = 0;
								}
							}
							else if ( "boolean".equals( propertyType.getName() ) ) {
								args[0] = Boolean.parseBoolean( fieldValue );
							}

							try {
								Method mutatorMethod = modelProperty.getMutatorMethod();
								mutatorMethod.invoke( this.model, args );
							}
							catch (Exception e) {
								GWT.log( e.getMessage(), e );
							}
						}
					}
				}
			}
		}
	}
}
