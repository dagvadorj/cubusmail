/* ComposeMessageCanvas.java

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

import com.cubusmail.client.events.DelayedResizeHandlerProxy;
import com.cubusmail.client.util.TextProvider;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Compose canvas for email messages.
 * 
 * @author Juergen Schlierf
 */
public class ComposeMessageCanvas extends VLayout {

	private EmailAddressInputLine toInput;
	private EmailAddressInputLine ccInput;
	private EmailAddressInputLine bccInput;

	private RichTextEditor richtTextEditor;

	public ComposeMessageCanvas() {

		super();
		setShowEdges( false );
		setWidth100();
		setHeight100();
		setOverflow( Overflow.HIDDEN );

		this.toInput = new EmailAddressInputLine( "to", TextProvider.get().window_compose_message_label_to() );
		this.ccInput = new EmailAddressInputLine( "cc", TextProvider.get().window_compose_message_label_cc() );
		this.bccInput = new EmailAddressInputLine( "bcc", TextProvider.get().window_compose_message_label_bcc() );

		this.richtTextEditor = new RichTextEditor();
		this.richtTextEditor.setWidth100();
		this.richtTextEditor.setHeight100();
		this.richtTextEditor.setOverflow( Overflow.VISIBLE );
		this.richtTextEditor.setShowEdges( true );

		setMembers( this.toInput, this.ccInput, this.bccInput, this.richtTextEditor );

		addResizedHandler( DelayedResizeHandlerProxy.get( new ResizedHandler() {

			public void onResized( ResizedEvent event ) {

				resizeFields();
			}
		} ) );
	}

	private void resizeFields() {
		this.toInput.resize();
		this.ccInput.resize();
		this.bccInput.resize();
	}
}
