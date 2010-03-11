/* EmailAddressInputLine.java

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

import com.cubusmail.client.widgets.SuggestBoxItem;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Email address input line for the compose canvas.
 * 
 * @author Juergen Schlierf
 */
public class EmailAddressInputLine extends HLayout {

	private TextItem addressLine;
	private DynamicForm form;
	private SuggestBoxItem suggestBox;

	// private InputListWidget inputList;

	public EmailAddressInputLine( String name, String label ) {

		super();
		setHeight( 25 );

		IButton addressBookButton = new IButton( label );
		addressBookButton.setAutoFit( true );

		// this.inputList = new InputListWidget();

		// MultipleTextBox txt = new MultipleTextBox();
		//
		// SuggestBox box = new SuggestBox( getSuggestions(), txt );
		// box.addStyleName( "original-token-input" );
		// box.setAnimationEnabled( true );

		// WidgetCanvas wc = new WidgetCanvas( box );
		// wc.setWidth100();
		this.form = new DynamicForm();
		this.form.setNumCols( 1 );
		this.form.setWidth100();
		this.suggestBox = new SuggestBoxItem( "test", "test", getSuggestions() );

		// this.addressLine = new TextItem( name );
		// this.addressLine.setShowTitle( false );
		this.form.setItems( this.suggestBox );

		setMembersMargin( 5 );
		setMembers( addressBookButton, this.form );
	}

	public void resize() {

		// this.addressLine.setWidth( this.form.getInnerWidth() );
	}

	private MultiWordSuggestOracle getSuggestions() {

		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		oracle.add( "Amy Kesic" );
		oracle.add( "Jason Weston" );
		oracle.add( "Dave Johnson" );
		oracle.add( "Paul Hammant" );
		oracle.add( "Jesse Kuhnert" );
		oracle.add( "Ben Alex" );
		oracle.add( "Tom Bender" );
		oracle.add( "Alexandru Popescu" );
		oracle.add( "Kaveh Arabfakhry" );
		oracle.add( "Steven Hong" );
		oracle.add( "Jason van Zyl" );
		oracle.add( "Alex Vauthey" );
		oracle.add( "Kiran Karnati" );
		oracle.add( "Kalpana Nagireddy" );
		oracle.add( "Ramnivas Laddad" );
		oracle.add( "Arjé Cahn" );
		oracle.add( "Amy Anne Rasberry" );
		oracle.add( "Vincent Stoessel" );
		oracle.add( "Steven Leija" );
		oracle.add( "Brian Burke" );
		oracle.add( "John Ipson" );
		oracle.add( "Candy Chastain Mielke" );
		oracle.add( "Scott Mark" );
		oracle.add( "Dov B. Katz" );
		oracle.add( "Alef Arendsen" );
		oracle.add( "David Jencks" );
		oracle.add( "Alexey Belikov" );
		oracle.add( "Bryan Vial" );
		oracle.add( "Dror Bereznitsky" );
		oracle.add( "David Moskowitz" );
		oracle.add( "Oscar Chan" );
		oracle.add( "Sergey Sundukovskiy" );
		oracle.add( "John Newton" );
		oracle.add( "Chris Buzzetta" );
		oracle.add( "Peter Svensson" );
		oracle.add( "Riccardo Ferretti" );
		oracle.add( "Christian Parker" );
		oracle.add( "Ann (Jaksa) Skaehill" );
		oracle.add( "Justin Blue" );
		oracle.add( "Sean Dawson" );
		oracle.add( "Devaraj NS" );
		oracle.add( "Robert Gadd" );
		oracle.add( "Diego Campodonico" );
		oracle.add( "Bryan Field-Elliot" );
		oracle.add( "Scott Delap" );
		oracle.add( "Kevin Koster" );
		oracle.add( "Fernand Galiana" );
		oracle.add( "Christopher Shuler" );
		oracle.add( "Geir Magnusson Jr" );
		oracle.add( "Tyler Hansen" );
		oracle.add( "Olivier Lamy" );
		oracle.add( "J. Thomas Richardson" );
		oracle.add( "Russell Beattie" );
		oracle.add( "Martin Ouellet" );
		oracle.add( "Scott Ferguson" );
		oracle.add( "Guillaume Laforge" );
		oracle.add( "Eric Weidner" );
		oracle.add( "Troy McKinnon" );
		oracle.add( "Max Hays" );
		oracle.add( "Phillip Rhodes" );
		oracle.add( "Eugene Kulechov" );
		oracle.add( "Bob Johnson" );
		oracle.add( "Richard Tucker, PMP" );
		oracle.add( "Mats Henricson" );
		oracle.add( "Floyd Marinescu" );
		oracle.add( "Ed Burns" );
		oracle.add( "Michael Root" );
		oracle.add( "Dana Busch" );
		oracle.add( "Borislav Roussev" );
		oracle.add( "Harris Tsim" );
		oracle.add( "Jason Thrasher" );
		oracle.add( "Soo-il Kim" );
		oracle.add( "Lindsey Bowman" );
		oracle.add( "Ganesh Hariharan" );
		oracle.add( "Judy Herilla" );
		oracle.add( "Jevgeni Kabanov" );
		oracle.add( "Craig Whitacre" );
		oracle.add( "Paul M. Garvey" );
		oracle.add( "Jeremy Whitlock" );
		oracle.add( "Fabrizio Giustina" );
		oracle.add( "Todd Fredrich" );
		oracle.add( "Matt Stine" );
		oracle.add( "João Vitor Lacerda Guimarães" );
		oracle.add( "Yassine Hinnach" );
		oracle.add( "Chris Huston" );
		oracle.add( "Jodi Behrens-Stark" );
		oracle.add( "John Greenhill" );
		oracle.add( "Roy Porter" );
		oracle.add( "Paul Tuckey" );
		oracle.add( "Arjun Ram" );
		oracle.add( "Merrill Bennett" );
		oracle.add( "James Richards" );
		oracle.add( "Franz Garsombke" );
		oracle.add( "Kimberly Horan" );
		oracle.add( "Hani Suleiman" );
		oracle.add( "Thomas Dudziak" );
		oracle.add( "Andrew Penrose" );
		oracle.add( "Igor Polyakov" );
		oracle.add( "Steve Runkel" );

		return oracle;
	}
}
