/* SearchForm.java

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
package com.cubusmail.client.canvases.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.cubusmail.client.util.GWTUtil;
import com.cubusmail.client.util.TextProvider;
import com.cubusmail.common.model.MessageListFields;
import com.google.gwt.event.shared.EventHandler;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

/**
 * Quick search form for messages.
 * 
 * @author Juergen Schlierf
 */
public class MessageSearchForm extends DynamicForm {

	private static final int DIALOG_WIDTH = 130;

	private static final int DIALOG_HEIGHT = 200;

	private TextItem searchItem;

	private List<SearchHandler> searchHandlerList;

	private FieldsDialog fieldsDialog;

	private MessageListFields[] searchFields = new MessageListFields[] { MessageListFields.FROM, MessageListFields.TO,
			MessageListFields.CC, MessageListFields.SUBJECT };

	public MessageSearchForm() {

		super();

		this.searchItem = new TextItem();
		this.searchItem.setTitle( TextProvider.get().grid_messages_search() );
		this.searchItem.setTooltip( TextProvider.get().grid_messages_search_tooltip() );
		this.searchItem.setWidth( 250 );

		final PickerIcon searchIcon = new PickerIcon( PickerIcon.SEARCH );
		final PickerIcon clearIcon = new PickerIcon( PickerIcon.CLEAR );
		final PickerIcon filedsIcon = new PickerIcon( PickerIcon.COMBO_BOX );
		this.searchItem.setIcons( searchIcon, clearIcon, filedsIcon );
		this.searchItem.addIconClickHandler( new IconClickHandler() {

			public void onIconClick( IconClickEvent event ) {

				FormItemIcon icon = event.getIcon();
				if ( icon.getSrc().equals( clearIcon.getSrc() ) ) {
					resetFilter();
					fireEvent();
				}
				else if ( icon.getSrc().equals( filedsIcon.getSrc() ) ) {
					if ( fieldsDialog == null ) {
						fieldsDialog = new FieldsDialog();
					}
					Rectangle iconRect = searchItem.getIconPageRect( event.getIcon() );
					fieldsDialog.setFields( searchFields );
					fieldsDialog.show();
					fieldsDialog.moveTo( iconRect.getLeft() - DIALOG_WIDTH, iconRect.getTop() );
				}
				else {
					fireEvent();
				}
			}
		} );
		this.searchItem.addKeyPressHandler( new KeyPressHandler() {

			public void onKeyPress( KeyPressEvent event ) {

				if ( "enter".equalsIgnoreCase( event.getKeyName() ) ) {
					fireEvent();
				}
			}
		} );

		setItems( this.searchItem );
	}

	private void fireEvent() {

	}

	public void addSearchHandler( SearchHandler handler ) {

		if ( this.searchHandlerList == null ) {
			this.searchHandlerList = new ArrayList<SearchHandler>();
		}
		this.searchHandlerList.add( handler );
	}

	public void resetFilter() {

		this.searchItem.clearValue();
	}

	/**
	 * In this dialog the user can choose which message fields are concerned by
	 * the search including message body.
	 * 
	 * @author Juergen Schlierf
	 */
	private class FieldsDialog extends Dialog {

		private CheckboxItem checkBoxFrom = null;
		private CheckboxItem checkBoxTo = null;
		private CheckboxItem checkBoxCc = null;
		private CheckboxItem checkBoxSubject = null;
		private CheckboxItem checkBoxContent = null;

		public FieldsDialog() {

			super();
			setIsModal( true );
			setShowHeader( false );
			setShowEdges( true );
			setEdgeSize( 2 );
			setWidth( DIALOG_WIDTH );
			setHeight( DIALOG_HEIGHT );

			Map<String, Integer> bodyDefaults = new HashMap<String, Integer>();
			bodyDefaults.put( "layoutLeftMargin", 10 );
			bodyDefaults.put( "layoutTopMargin", 15 );
			setBodyDefaults( bodyDefaults );

			DynamicForm fieldForm = new DynamicForm();
			fieldForm.setTitleWidth( 20 );
			fieldForm.setWidth( DIALOG_WIDTH - 12 );
			fieldForm.setHeight( DIALOG_HEIGHT - 25 );
			fieldForm.setBorder( "2px" );

			this.checkBoxFrom = new CheckboxItem( MessageListFields.FROM.name(), TextProvider.get()
					.extended_search_panel_from() );
			this.checkBoxTo = new CheckboxItem( MessageListFields.TO.name(), TextProvider.get()
					.extended_search_panel_to() );
			this.checkBoxCc = new CheckboxItem( MessageListFields.CC.name(), TextProvider.get()
					.extended_search_panel_cc() );
			this.checkBoxSubject = new CheckboxItem( MessageListFields.SUBJECT.name(), TextProvider.get()
					.extended_search_panel_subject() );
			this.checkBoxContent = new CheckboxItem( MessageListFields.CONTENT.name(), TextProvider.get()
					.extended_search_panel_body() );

			final ButtonItem searchButton = new ButtonItem( TextProvider.get().extended_search_panel_search() );
			searchButton.setAutoFit( true );
			searchButton.addClickHandler( new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

				public void onClick( com.smartgwt.client.widgets.form.fields.events.ClickEvent event ) {

					searchFields = getFields();
					hide();
				}
			} );

			fieldForm.setItems( this.checkBoxFrom, this.checkBoxTo, this.checkBoxCc, this.checkBoxSubject,
					this.checkBoxContent, searchButton );

			addItem( fieldForm );
		}

		/**
		 * @param searchFields
		 */
		public void setFields( MessageListFields[] searchFields ) {

			this.checkBoxFrom.setValue( GWTUtil.indexOf( searchFields, MessageListFields.FROM ) >= 0 );
			this.checkBoxTo.setValue( GWTUtil.indexOf( searchFields, MessageListFields.TO ) >= 0 );
			this.checkBoxCc.setValue( GWTUtil.indexOf( searchFields, MessageListFields.CC ) >= 0 );
			this.checkBoxSubject.setValue( GWTUtil.indexOf( searchFields, MessageListFields.SUBJECT ) >= 0 );
			this.checkBoxContent.setValue( GWTUtil.indexOf( searchFields, MessageListFields.CONTENT ) >= 0 );
		}

		/**
		 * @return
		 */
		public MessageListFields[] getFields() {

			List<MessageListFields> fields = new ArrayList<MessageListFields>();
			if ( this.checkBoxFrom.getValueAsBoolean() ) {
				fields.add( MessageListFields.FROM );
			}
			if ( this.checkBoxTo.getValueAsBoolean() ) {
				fields.add( MessageListFields.TO );
			}
			if ( this.checkBoxCc.getValueAsBoolean() ) {
				fields.add( MessageListFields.CC );
			}
			if ( this.checkBoxSubject.getValueAsBoolean() ) {
				fields.add( MessageListFields.SUBJECT );
			}
			if ( this.checkBoxContent.getValueAsBoolean() ) {
				fields.add( MessageListFields.CONTENT );
			}

			return (MessageListFields[]) fields.toArray( new MessageListFields[0] );
		}
	}

	/**
	 * This interface defines search handler to excute message filtering.
	 * 
	 * @author Juergen Schlierf
	 */
	public interface SearchHandler extends EventHandler {

		public void onSearch( MessageListFields[] fields, String[] values );
	}
}
