/* PrintManager.java

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
package com.cubusmail.gwtui.client.util;

import com.google.gwt.user.client.ui.UIObject;
import com.gwtext.client.util.Format;

import com.cubusmail.gwtui.client.model.GWTMessage;

/**
 * Manage the printing.
 * 
 * @author Juergen Schlierf
 */
public abstract class PrintManager {

	public static native void print( String html ) /*-{
		var frame = $doc.getElementById('__printingFrame');
		if (!frame) {
		    $wnd.alert("Error: Can't find printing frame.");
		    return;
		       }
		frame = frame.contentWindow;
		var doc	= frame.document;
		doc.open();
		doc.write(html);
		doc.close();
		frame.focus();
		frame.print();
	}-*/;

	/**
	 * @param message
	 */
	public static void print( GWTMessage message ) {

		StringBuffer htmlMessage = generateMessageHeader( message );

		htmlMessage.append( "<div>" );
		htmlMessage.append( message.getMessageText() );
		htmlMessage.append( "</div>" );

		print( htmlMessage.toString() );
	}

	public static void print( UIObject obj ) {

		print( obj.getElement().getInnerHTML() );
	}

	/**
	 * @param message
	 * @return
	 */
	private static StringBuffer generateMessageHeader( GWTMessage message ) {

		StringBuffer result = new StringBuffer();

		result.append( "<html style=\"overflow: auto;\">" );
		result.append( "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" );
		result.append( "<title>" );
		result.append( Format.htmlEncode( message.getSubject() ) );
		result.append( "</title>" );
		result.append( "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/print.css\">" );
		result.append( "</head>" );
		result.append( "<body style=\"width: 95%; height: 95%;\">" );
		result
				.append( "<table class=\"detailprint\" style=\"width: 100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" );
		result.append( "<tbody>" );
		result.append( "<tr>" );
		result.append( "<td class=\"printtitle\" colspan=\"2\">" );
		result.append( Format.htmlEncode( message.getSubject() ) );
		result
				.append( "</td></tr><tr><td colspan=\"2\" style=\"background-color: #000000; color: #000000; height: 5px;\"></td></tr>" );
		result.append( "<tr><td colspan=\"2\">&nbsp;</td></tr>" );

		if ( GWTUtil.hasText( message.getFrom() ) ) {
			result.append( "<tr class=\"printcontent\"><td class=\"printcontenttitle\">" );
			result.append( TextProvider.get().message_reading_pane_panel_from() );
			result.append( "</td><td class=\"printcontentname\">" );
			result.append( Format.htmlEncode( message.getFrom() ) );
			result.append( "</td></tr>" );
		}
		if ( GWTUtil.hasText( message.getTo() ) ) {
			result.append( "<tr class=\"printcontent\"><td class=\"printcontenttitle\">" );
			result.append( TextProvider.get().message_reading_pane_panel_to() );
			result.append( "</td><td class=\"printcontentname\">" );
			result.append( Format.htmlEncode( message.getTo() ) );
			result.append( "</td></tr>" );
		}
		if ( GWTUtil.hasText( message.getCc() ) ) {
			result.append( "<tr class=\"printcontent\"><td class=\"printcontenttitle\">" );
			result.append( TextProvider.get().message_reading_pane_panel_cc() );
			result.append( "</td><td class=\"printcontentname\">" );
			result.append( Format.htmlEncode( message.getCc() ) );
			result.append( "</td></tr>" );
		}
		if ( GWTUtil.hasText( message.getReplyTo() ) ) {
			result.append( "<tr class=\"printcontent\"><td class=\"printcontenttitle\">" );
			result.append( TextProvider.get().message_reading_pane_panel_replyto() );
			result.append( "</td><td class=\"printcontentname\">" );
			result.append( Format.htmlEncode( message.getReplyTo() ) );
			result.append( "</td></tr>" );
		}
		if ( message.getDate() != null ) {
			result.append( "<tr class=\"printcontent\"><td class=\"printcontenttitle\">" );
			result.append( TextProvider.get().message_reading_pane_panel_date() );
			result.append( "</td><td class=\"printcontentname\">" );
			result.append( GWTUtil.formatDate( message.getDate() ) );
			result.append( "</td></tr>" );
		}

		result.append( "<tr><td class=\"printline\" colspan=\"2\" style=\"height: 2px;\"></td></tr>" );
		result.append( "<tr><td colspan=\"2\">&nbsp;</td></tr></tbody></table>" );

		return result;
	}
}
