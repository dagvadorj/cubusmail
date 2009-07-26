/* ViewAttachmentAction.java

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
package com.cubusmail.gwtui.client.actions.message;

import com.google.gwt.user.client.Window;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Open attachments.
 * 
 * @author Jürgen Schlierf
 */
public class ViewAttachmentAction extends GWTAction {

	private GWTAttachment attachment;

	/**
	 * 
	 */
	public ViewAttachmentAction( GWTAttachment attachment ) {

		super();
		this.attachment = attachment;
		setImageName( ImageProvider.FILE_BLANK );
		setText( TextProvider.get().actions_view_attachment_text() );
	}

	@Override
	public void execute() {

		long messageId = this.attachment.getMessageId();
		int index = this.attachment.getIndex();
		String url = ServiceProvider.getRetrieveAttachmentServletUrl( messageId, index );
		url += "&view=1";
		Window.open( url, "Download", "" );
	}
}
