/* ContactReadingPanelPanel.java

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

import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.portal.Portal;
import com.gwtext.client.widgets.portal.PortalColumn;
import com.gwtext.client.widgets.portal.Portlet;

import com.cubusmail.gwtui.client.events.ContactSelectedListener;
import com.cubusmail.gwtui.client.events.EventBroker;
import com.cubusmail.gwtui.client.util.GWTUtil;
import com.cubusmail.gwtui.client.util.TextProvider;
import com.cubusmail.gwtui.domain.Contact;
import com.cubusmail.gwtui.domain.ContactAddress;

/**
 * Reading pane for contacts.
 * 
 * @author Juergen Schlierf
 */
public class ContactReadingPanelPanel extends Panel implements ContactSelectedListener {

	private Contact contact;

	private Panel topBanner;
	private Panel contentPanel;
	private HTMLPanel titlePanel;
	private Portlet[] contactPortlets = new Portlet[6];

	public ContactReadingPanelPanel() {

		super();

		setLayout( new BorderLayout() );
		setAutoScroll( true );

		createTopBanner();
		createContentPanel();

		BorderLayoutData topData = new BorderLayoutData( RegionPosition.NORTH );
		topData.setFloatable( true );
		topData.setMinHeight( 0 );
		BorderLayoutData centerData = new BorderLayoutData( RegionPosition.CENTER );
		centerData.setMinHeight( 0 );

		add( this.topBanner, topData );
		add( this.contentPanel, centerData );
		setBodyStyle( "background-color: #FFFFFF" );

		EventBroker.get().addContactSelectedListener( this );
	}

	/**
	 * 
	 */
	private void createTopBanner() {

		this.topBanner = new Panel();
		this.topBanner.setBorder( false );
		this.topBanner.setFrame( true );
		this.topBanner.setLayout( new FitLayout() );
		this.topBanner.setHeader( false );
		this.topBanner.setAutoScroll( false );
		this.topBanner.setAutoHeight( true );

		this.titlePanel = new HTMLPanel();
		this.titlePanel.setBodyStyle( "font: bold 16px tahoma, arial, helvetica;" );
		this.topBanner.add( this.titlePanel );
	}

	/**
	 * 
	 */
	private void createContentPanel() {

		this.contentPanel = new Panel();
		this.contentPanel.setPaddings( 5 );
		this.contentPanel.setFrame( false );
		this.contentPanel.setBorder( false );
		this.contentPanel.setAutoScroll( false );
		this.contentPanel.setAutoHeight( true );

		Portal portal = new Portal();
		portal.setBorder( false );

		PortalColumn firstCol = new PortalColumn();
		firstCol.setPaddings( 5, 5, 5, 5 );
		PortalColumn secondCol = new PortalColumn();
		secondCol.setPaddings( 5, 5, 5, 5 );

		for (int i = 0; i < this.contactPortlets.length; i++) {
			this.contactPortlets[i] = createPortlet();
		}

		firstCol.add( this.contactPortlets[0] );
		secondCol.add( this.contactPortlets[1] );
		firstCol.add( this.contactPortlets[2] );
		secondCol.add( this.contactPortlets[3] );
		firstCol.add( this.contactPortlets[4] );
		secondCol.add( this.contactPortlets[5] );

		portal.add( firstCol, new ColumnLayoutData( 0.5 ) );
		portal.add( secondCol, new ColumnLayoutData( 0.5 ) );

		this.contentPanel.add( portal );
	}

	/**
	 * @param title
	 * @return
	 */
	private Portlet createPortlet() {

		Portlet contactPortlet = new Portlet();
		contactPortlet.setDraggable( false );
		contactPortlet.setCollapsible( false );
		contactPortlet.setLayout( new FitLayout() );
		contactPortlet.setVisible( false );
		contactPortlet.setTitle( null );

		return contactPortlet;
	}

	/**
	 * @param label
	 * @param value
	 * @return
	 */
	private StringBuffer createLine( String label, String value ) {

		StringBuffer result = new StringBuffer();

		if ( GWTUtil.hasText( value ) ) {
			result.append( "<b>" );
			result.append( label );
			result.append( " </b>" );
			result.append( value );
			result.append( "<br>" );
		}

		return result;
	}

	/**
	 * @return
	 */
	private String createContactHTML() {

		StringBuffer htmlText = new StringBuffer();

		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_firstname(), this.contact
				.getFirstName() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_secondname(), this.contact
				.getSecondName() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_lastname(), this.contact
				.getLastName() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_nickname(), this.contact
				.getNickname() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_company(), this.contact.getCompany() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_position(), this.contact
				.getNickname() ) );

		return htmlText.toString();
	}

	/**
	 * @return
	 */
	private String createInternetHTML() {

		StringBuffer htmlText = new StringBuffer();

		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_email(), this.contact.getEmail() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_email2(), this.contact.getEmail2() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_im(), this.contact.getIm() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_website(), this.contact.getWebsite() ) );

		return htmlText.toString();
	}

	/**
	 * @return
	 */
	private String createPhoneHTML() {

		StringBuffer htmlText = new StringBuffer();

		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_privatephone(), this.contact
				.getPrivatePhone() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_businessphone(), this.contact
				.getBusinessPhone() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_mobilephone(), this.contact
				.getMobilePhone() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_pager(), this.contact.getPager() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_privatefax(), this.contact
				.getPrivateFax() ) );
		htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_businessfax(), this.contact
				.getBusinessFax() ) );

		return htmlText.toString();
	}

	/**
	 * @return
	 */
	private String createAddressHTML( ContactAddress address ) {

		StringBuffer htmlText = new StringBuffer();

		if ( address != null ) {
			htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_street(), address.getStreet() ) );
			htmlText
					.append( createLine( TextProvider.get().contact_reading_pane_label_street2(), address.getStreet2() ) );
			htmlText
					.append( createLine( TextProvider.get().contact_reading_pane_label_zipcode(), address.getZipcode() ) );
			htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_city(), address.getCity() ) );
			htmlText.append( createLine( TextProvider.get().contact_reading_pane_label_state(), address.getState() ) );
			htmlText
					.append( createLine( TextProvider.get().contact_reading_pane_label_country(), address.getCountry() ) );
		}

		return htmlText.toString();
	}

	/**
	 * @return
	 */
	private String createNoticeHTML() {

		StringBuffer htmlText = new StringBuffer();

		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_custom1(), this.contact.getCustom1() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_custom2(), this.contact.getCustom2() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_custom3(), this.contact.getCustom3() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_custom4(), this.contact.getCustom4() ) );
		htmlText
				.append( createLine( TextProvider.get().contact_reading_pane_label_notice(), this.contact.getNotice() ) );

		return htmlText.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cubusmail.gwtui.client.events.ContactSelectedListener#onContactSelected
	 * (com.cubusmail.gwtui.domain.Contact)
	 */
	public void onContactSelected( Contact contact ) {

		setContact( contact );

	}

	/**
	 * 
	 */
	public void clearContent() {

		this.contact = null;
		this.titlePanel.setHtml( "" );
		this.titlePanel.setVisible( false );
		this.contentPanel.setVisible( false );
		for (Portlet portlet : this.contactPortlets) {
			portlet.setHtml( "" );
			portlet.setVisible( false );
		}
	}

	/**
	 * @param contact
	 *            The contact to set.
	 */
	public void setContact( Contact contact ) {

		this.contact = contact;

		for (Portlet portlet : this.contactPortlets) {
			portlet.setHtml( "" );
			portlet.setVisible( false );
		}

		int portletIndex = 0;

		String title = "";
		if ( GWTUtil.hasText( contact.getFirstName() ) ) {
			title += contact.getFirstName() + " ";
		}
		if ( GWTUtil.hasText( contact.getLastName() ) ) {
			title += contact.getLastName();
		}
		if ( !GWTUtil.hasText( title ) ) {
			title += contact.getEmail();
		}
		this.titlePanel.setHtml( title );

		String htmlText = createContactHTML();
		if ( GWTUtil.hasText( htmlText ) ) {
			this.contactPortlets[portletIndex].setHtml( htmlText );
			this.contactPortlets[portletIndex].setTitle( TextProvider.get().contact_reading_pane_portlet_contact() );
			this.contactPortlets[portletIndex].setVisible( true );
			portletIndex++;
		}

		htmlText = createInternetHTML();
		if ( GWTUtil.hasText( htmlText ) ) {
			this.contactPortlets[portletIndex].setHtml( htmlText );
			this.contactPortlets[portletIndex].setTitle( TextProvider.get().contact_reading_pane_portlet_internet() );
			this.contactPortlets[portletIndex].setVisible( true );
			portletIndex++;
		}

		htmlText = createPhoneHTML();
		if ( GWTUtil.hasText( htmlText ) ) {
			this.contactPortlets[portletIndex].setHtml( htmlText );
			this.contactPortlets[portletIndex].setTitle( TextProvider.get().contact_reading_pane_portlet_telecom() );
			this.contactPortlets[portletIndex].setVisible( true );
			portletIndex++;
		}

		htmlText = createAddressHTML( this.contact.getPrivateAddress() );
		if ( GWTUtil.hasText( htmlText ) ) {
			this.contactPortlets[portletIndex].setHtml( htmlText );
			this.contactPortlets[portletIndex].setTitle( TextProvider.get().contact_reading_pane_portlet_private() );
			this.contactPortlets[portletIndex].setVisible( true );
			portletIndex++;
		}

		htmlText = createAddressHTML( this.contact.getBusinessAddress() );
		if ( GWTUtil.hasText( htmlText ) ) {
			this.contactPortlets[portletIndex].setHtml( htmlText );
			this.contactPortlets[portletIndex].setTitle( TextProvider.get().contact_reading_pane_portlet_business() );
			this.contactPortlets[portletIndex].setVisible( true );
			portletIndex++;
		}

		htmlText = createNoticeHTML();
		if ( GWTUtil.hasText( htmlText ) ) {
			this.contactPortlets[portletIndex].setHtml( htmlText );
			this.contactPortlets[portletIndex].setTitle( TextProvider.get().contact_reading_pane_portlet_notice() );
			this.contactPortlets[portletIndex].setVisible( true );
			portletIndex++;
		}

		this.titlePanel.setVisible( true );
		this.contentPanel.setVisible( true );

		doLayout();
	}
}
