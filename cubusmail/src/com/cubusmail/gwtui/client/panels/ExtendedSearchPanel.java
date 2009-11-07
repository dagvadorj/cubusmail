/* ExtendedSearchPanel.java

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
package com.cubusmail.gwtui.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListener;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.TableLayout;
import com.gwtext.client.widgets.layout.TableLayoutData;

import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.GWTMailConstants;
import com.cubusmail.gwtui.domain.SearchFields;

/**
 * Extended search.
 * 
 * @author Juergen Schlierf
 */
public class ExtendedSearchPanel extends Panel {

	private TextField from;
	private TextField to;
	private TextField cc;
	private TextField subject;
	private TextField content;
	private DateField dateFrom;
	private DateField dateTo;

	private Button searchButton;

	public ExtendedSearchPanel( boolean visible ) {

		super();
		setFrame( true );
		setBorder( false );
		setLayout( new FitLayout() );
		setVisible( visible );
		setPaddings( 1, 1, 1, 1 );

		this.from = new TextField( TextProvider.get().extended_search_panel_from(), SearchFields.FROM.name(), 150 );
		this.to = new TextField( TextProvider.get().extended_search_panel_to(), SearchFields.TO.name(), 150 );
		this.cc = new TextField( TextProvider.get().extended_search_panel_cc(), SearchFields.CC.name(), 150 );
		this.subject = new TextField( TextProvider.get().extended_search_panel_subject(), SearchFields.SUBJECT.name(),
				150 );
		this.content = new TextField( TextProvider.get().extended_search_panel_body(), SearchFields.CONTENT.name(), 400 );
		this.dateFrom = new DateField( TextProvider.get().extended_search_panel_date_from(),
				SearchFields.DATE_FROM.name(), 100 );
		this.dateFrom.setFormat( TextProvider.get().extended_search_panel_date_format() );
		this.dateTo = new DateField( TextProvider.get().extended_search_panel_date_to(), SearchFields.DATE_TO.name(),
				100 );
		this.dateTo.setFormat( TextProvider.get().extended_search_panel_date_format() );

		FieldSet fieldSet = new FieldSet( TextProvider.get().extended_search_panel_fieldset() );
		fieldSet.setLayout( new TableLayout( 3 ) );

		fieldSet.add( createFieldPanel( this.from ) );
		fieldSet.add( createFieldPanel( this.to ) );
		fieldSet.add( createFieldPanel( this.cc ) );
		fieldSet.add( createFieldPanel( this.subject ) );
		fieldSet.add( createFieldPanel( this.content ), new TableLayoutData( 2 ) );
		fieldSet.add( createFieldPanel( this.dateFrom ) );
		fieldSet.add( createFieldPanel( this.dateTo ) );

		this.searchButton = new Button( TextProvider.get().extended_search_panel_search() );
		fieldSet.add( this.searchButton );

		add( fieldSet );
	}

	public void addSearchButtonListener( ButtonListener listener ) {

		this.searchButton.addListener( listener );
	}

	public void addKeyListener( KeyListener listener ) {

		this.from.addKeyListener( EventObject.ENTER, listener );
		this.to.addKeyListener( EventObject.ENTER, listener );
		this.cc.addKeyListener( EventObject.ENTER, listener );
		this.subject.addKeyListener( EventObject.ENTER, listener );
		this.content.addKeyListener( EventObject.ENTER, listener );
		this.dateFrom.addKeyListener( EventObject.ENTER, listener );
		this.dateTo.addKeyListener( EventObject.ENTER, listener );
	}

	public void clearFields() {

		this.from.setValue( null );
		this.to.setValue( null );
		this.cc.setValue( null );
		this.subject.setValue( null );
		this.content.setValue( null );
		this.dateFrom.setValue( (String) null );
		this.dateTo.setValue( (String) null );
	}

	/**
	 * @return
	 */
	public UrlParam[] getParams() {

		List<String> searchFieldList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();

		if ( GWTUtil.hasText( this.from.getText() ) ) {
			searchFieldList.add( this.from.getName() );
			valueList.add( this.from.getText() );
		}
		if ( GWTUtil.hasText( this.to.getText() ) ) {
			searchFieldList.add( this.to.getName() );
			valueList.add( this.to.getText() );
		}
		if ( GWTUtil.hasText( this.cc.getText() ) ) {
			searchFieldList.add( this.cc.getName() );
			valueList.add( this.cc.getText() );
		}
		if ( GWTUtil.hasText( this.subject.getText() ) ) {
			searchFieldList.add( this.subject.getName() );
			valueList.add( this.subject.getText() );
		}
		if ( GWTUtil.hasText( this.content.getText() ) ) {
			searchFieldList.add( this.content.getName() );
			valueList.add( this.content.getText() );
		}
		if ( GWTUtil.hasText( this.dateFrom.getText() ) ) {
			searchFieldList.add( this.dateFrom.getName() );
			valueList.add( String.valueOf( this.dateFrom.getValue().getTime() ) );
		}
		if ( GWTUtil.hasText( this.dateTo.getText() ) ) {
			searchFieldList.add( this.dateTo.getName() );
			valueList.add( String.valueOf( this.dateTo.getValue().getTime() ) );
		}

		if ( searchFieldList.size() > 0 ) {
			UrlParam[] params = new UrlParam[searchFieldList.size() + 1];
			String searchFields = "";
			for ( int i = 0; i < searchFieldList.size(); i++ ) {
				searchFields += searchFields + searchFieldList.get( i );
				if ( i < (searchFieldList.size() - 1) ) {
					searchFields += ",";
				}
				params[i] = new UrlParam( searchFieldList.get( i ), valueList.get( i ) );
			}
			params[searchFieldList.size()] = new UrlParam( GWTMailConstants.EXTENDED_SEARCH_FIELDS, searchFields );

			return params;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtext.client.widgets.Component#initComponent()
	 */
	@Override
	protected void initComponent() {

		super.initComponent();
	}

	private FormPanel createFieldPanel( TextField field ) {

		FormPanel fieldPanel = new FormPanel();
		fieldPanel.setPaddings( 1, 0, 20, 0 );
		fieldPanel.setBorder( false );
		fieldPanel.setLabelWidth( 70 );
		fieldPanel.add( field );

		return fieldPanel;
	}
}
