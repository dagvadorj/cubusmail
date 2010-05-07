/* AddressEditSubCanvas.java

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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * TODO: documentation
 * 
 * @author Juergen Schlierf
 */
public abstract class AddressEditSubCanvas extends VLayout {

	protected List<AddressEditAbstractForm> forms = new ArrayList<AddressEditAbstractForm>();

	private AddressEditFormTypeEnum[] enumGroup = null;

	public AddressEditSubCanvas( Class<? extends AddressEditAbstractForm> type, AddressEditFormTypeEnum[] enumGroup ) {

		super();
		setOverflow( Overflow.VISIBLE );
		setShowEdges( true );
		setAutoHeight();

		this.enumGroup = enumGroup;

		for (int i = 0; i < this.enumGroup.length; i++) {
			final AddressEditAbstractForm form = GWT.create( type );
			form.getAddItem().addIconClickHandler( new IconClickHandler() {

				@Override
				public void onIconClick( IconClickEvent event ) {

					addForm( null, null );
				}
			} );

			form.getRemoveItem().addIconClickHandler( new IconClickHandler() {

				@Override
				public void onIconClick( IconClickEvent event ) {

					removeForm( form );
				}
			} );
			if ( i == 0 ) {
				form.getRemoveItem().setVisible( false );
			}

			this.forms.add( form );
			addMember( form );
		}
	}

	/**
	 * 
	 */
	public void init() {

		// remove other forms
		for (AddressEditAbstractForm form : this.forms) {
			if ( form.isVisible() ) {
				form.setVisible( false );
			}
		}

		addForm( this.enumGroup[0], null );
	}

	/**
	 * 
	 */
	protected void addForm( AddressEditFormTypeEnum type, String value ) {

		for (AddressEditAbstractForm form : this.forms) {
			if ( !form.isVisible() ) {
				AddressEditFormTypeEnum[] types = getAvailableFormTypes();
				form.setFormTypes( types );
				if ( type == null ) {
					form.setType( types[0] );
				}
				else {
					form.setType( type );
				}
				form.setValue( value );
				form.setVisible( true );
				break;
			}
		}
		setAddButtonVisibility();
	}

	/**
	 * @param form
	 */
	protected void removeForm( AddressEditAbstractForm form ) {

		int index = this.forms.indexOf( form );
		for (int i = index; i < this.forms.size() - 1; i++) {
			AddressEditAbstractForm target = this.forms.get( i );
			AddressEditAbstractForm source = this.forms.get( i + 1 );
			if ( source.isVisible() ) {
				target.setValue( (String) source.getValue() );
				target.setType( source.getType() );
			}
		}
		for (int i = this.forms.size() - 1; i >= 0; i--) {
			if ( this.forms.get( i ).isVisible() ) {
				this.forms.get( i ).setVisible( false );
				break;
			}
		}

		setAddButtonVisibility();
	}

	/**
	 * 
	 */
	private void setAddButtonVisibility() {

		// all input fields visible
		boolean visible = true;
		if ( getAvailableFormTypesCount() == 0 ) {
			visible = false;
		}

		for (int i = this.forms.size() - 2; i >= 0; i--) {
			if ( this.forms.get( i ).isVisible() ) {
				this.forms.get( i ).getAddItem().setVisible( visible );
				if ( this.forms.get( i ).isDrawn() ) {
					this.forms.get( i ).redraw();
				}
				visible = false;
			}
		}
	}

	/**
	 * @return
	 */
	private AddressEditFormTypeEnum[] getAvailableFormTypes() {

		List<AddressEditFormTypeEnum> typeList = new ArrayList<AddressEditFormTypeEnum>();
		for (int i = 0; i < this.forms.size(); i++) {
			if ( !this.forms.get( i ).isVisible() ) {
				typeList.add( this.enumGroup[i] );
			}
		}

		return typeList.toArray( new AddressEditFormTypeEnum[0] );
	}

	/**
	 * @return
	 */
	private int getAvailableFormTypesCount() {

		int count = 0;
		for (AddressEditAbstractForm form : this.forms) {
			if ( !form.isVisible() ) {
				count++;
			}
		}

		return count;
	}
}
