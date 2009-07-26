/* DownloadAttachmentAction.java

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

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;

import com.cubusmail.gwtui.client.actions.GWTAction;
import com.cubusmail.gwtui.client.model.GWTAttachment;
import com.cubusmail.gwtui.client.services.ServiceProvider;
import com.cubusmail.gwtui.client.util.ImageProvider;
import com.cubusmail.gwtui.client.util.TextProvider;

/**
 * Start the attachment download.
 * 
 * @author Jürgen Schlierf
 */
public class DownloadAttachmentAction extends GWTAction {

	private GWTAttachment attachment;

	/**
	 * @param messageStore
	 */
	public DownloadAttachmentAction( GWTAttachment attachment ) {

		super();
		this.attachment = attachment;
		setText( TextProvider.get().actions_download_attachment_text() );
		setImageName( ImageProvider.MSG_DOWNLOAD );
		setTooltipText( TextProvider.get().actions_download_attachment_tooltip() );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cubusmail.gwtui.client.actions.GWTAction#execute()
	 */
	@Override
	public void execute() {

		long messageId = this.attachment.getMessageId();
		int index = this.attachment.getIndex();
		String url = ServiceProvider.getRetrieveAttachmentServletUrl( messageId, index );
		Frame frame = new Frame( url );
		frame.setVisible( false );
		RootPanel.get().add( frame );
	}
}
