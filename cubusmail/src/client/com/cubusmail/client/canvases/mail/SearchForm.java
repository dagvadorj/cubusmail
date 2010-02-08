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

import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Quick search form for messages
 * 
 * @author Juergen Schlierf
 */
public class SearchForm extends DynamicForm {

	private TextItem searchItem;

	public SearchForm() {

		super();

		this.searchItem = new TextItem();
		this.searchItem.setTitle( TextProvider.get().grid_messages_search() );
		this.searchItem.setTooltip( TextProvider.get().grid_messages_search_tooltip() );
		this.searchItem.setWidth( 200 );

		setItems( this.searchItem );
	}
	
	
}
