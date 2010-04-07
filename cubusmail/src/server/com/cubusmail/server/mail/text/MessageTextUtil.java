/* MessageTextUtil.java

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
package com.cubusmail.server.mail.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.web.util.HtmlUtils;

import com.cubusmail.common.model.Preferences;
import com.cubusmail.common.util.CubusConstants;
import com.cubusmail.server.mail.MessageHandler;
import com.cubusmail.server.mail.util.MessageUtils;

/**
 * Util class for message text preparation.
 * 
 * @author Juergen Schlierf
 */
public class MessageTextUtil {

	private static final Log log = LogFactory.getLog( MessageTextUtil.class.getName() );

	private static final CleanerProperties CLEANER_PROPERTIES = new CleanerProperties();
	static {
		CLEANER_PROPERTIES.setPruneTags( "style, script" );
		CLEANER_PROPERTIES.setOmitUnknownTags( true );
	}

	public static final Pattern PATTERN_HREF = Pattern
			.compile(
					"<a\\s+href[^>]+>.*?</a>|((?:https?://|ftp://|mailto:|news\\.|www\\.)(?:[-A-Z0-9+@#/%?=~_|!:,.;]|&amp;|&(?!\\w+;))*(?:[-A-Z0-9+@#/%=~_|]|&amp;|&(?!\\w+;)))",
					Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE );

	private static final Pattern PATTERN_TARGET = Pattern.compile( "(<a[^>]*?target=\"?)([^\\s\">]+)(\"?.*</a>)",
			Pattern.CASE_INSENSITIVE );

	private static final String STR_BLANK = "_blank";
	private static final String HTML_BR = "<br />";
	private static final String REPL_LINEBREAK = "\r?\n";

	private static final int STRBLD_SIZE = 32768; // 32K
	private static final int BUFSIZE = 8192; // 8K
	private static final String STR_IMG_SRC = "src=";

	/**
	 * @param part
	 * @param messageHandler
	 * @param loadImages
	 * @param reply
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void messageTextFromPart( Part part, MessageHandler messageHandler, boolean loadImages,
			MessageTextMode mode, Preferences preferences, int level ) throws MessagingException, IOException {

		log.debug( "Content type of part: " + part.getContentType() );

		if ( mode == MessageTextMode.DISPLAY || mode == MessageTextMode.DRAFT ) {
			if ( MessageUtils.isImagepart( part ) ) {
				messageHandler.setMessageImageHtml( createImageMessageText( messageHandler.getId() ) );
			}
			else if ( !preferences.isShowHtml() && !StringUtils.isEmpty( messageHandler.getMessageTextPlain() ) ) {
				return;
			}
			else if ( preferences.isShowHtml() && !StringUtils.isEmpty( messageHandler.getMessageTextHtml() ) ) {
				return;
			}
			else if ( part.isMimeType( "text/plain" ) ) {
				String text = readPart( part );
				if ( !StringUtils.isBlank( text ) ) {
					messageHandler.setMessageTextPlain( formatPlainText( text, mode ) );
				}
			}
			else if ( part.isMimeType( "text/html" ) ) {
				if ( preferences.isShowHtml() ) {
					String text = readPart( part );
					boolean[] hasImages = new boolean[] { false };
					if ( !StringUtils.isBlank( convertHtml2PlainText( text ) ) ) {
						text = formatHTMLText( text, loadImages, hasImages );
						messageHandler.setMessageTextHtml( text );
						messageHandler.setHtmlMessage( true );
						messageHandler.setHasImages( hasImages[0] );
					}
				}
				else {
					// only if there is no plain text part found
					if ( StringUtils.isEmpty( messageHandler.getMessageTextPlain() ) ) {
						String text = readPart( part );
						text = convertHtml2PlainText( text );
						if ( !StringUtils.isBlank( text ) ) {
							text = formatPlainText( text, mode );
							messageHandler.setMessageTextPlain( text );
						}
					}
				}
			}
			else if ( part.isMimeType( "multipart/*" ) ) {
				Multipart mp = (Multipart) part.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++) {
					Part subPart = mp.getBodyPart( i );
					messageTextFromPart( subPart, messageHandler, loadImages, mode, preferences, level++ );
				}
			}
		}
		else if ( mode == MessageTextMode.REPLY ) {
			if ( !preferences.isCreateHtmlMsgs() && !StringUtils.isEmpty( messageHandler.getMessageTextPlain() ) ) {
				return;
			}
			else if ( preferences.isCreateHtmlMsgs() && !StringUtils.isEmpty( messageHandler.getMessageTextHtml() ) ) {
				return;
			}
			else if ( part.isMimeType( "text/plain" ) ) {
				String text = readPart( part );
				text = quotePlainText( text );
				if ( preferences.isCreateHtmlMsgs() ) {
					text = convertPlainText2Html( text, mode );
					messageHandler.setMessageTextHtml( text );
					messageHandler.setHtmlMessage( true );
				}
				else {
					messageHandler.setMessageTextPlain( text );
				}
			}
			else if ( part.isMimeType( "text/html" ) && StringUtils.isEmpty( messageHandler.getMessageTextPlain() ) ) {
				String text = readPart( part );
				text = convertHtml2PlainText( text );
				text = quotePlainText( text );
				if ( preferences.isCreateHtmlMsgs() ) {
					text = convertPlainText2Html( text, mode );
					messageHandler.setMessageTextHtml( text );
					messageHandler.setHtmlMessage( true );
				}
				else {
					messageHandler.setMessageTextPlain( text );
				}
			}
			else if ( part.isMimeType( "multipart/*" ) ) {
				Multipart mp = (Multipart) part.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++) {
					Part subPart = mp.getBodyPart( i );
					messageTextFromPart( subPart, messageHandler, loadImages, mode, preferences, level++ );
				}
			}
		}
	}

	/**
	 * Process the HTML message text either for display or reply/draft.
	 * 
	 * @param messageText
	 * @param charset
	 * @param imageLoad
	 * @param hasImages
	 * @return
	 */
	public static String formatHTMLText( String messageText, boolean loadImages, boolean[] hasImages ) {

		HtmlCleaner cleaner = new HtmlCleaner( CLEANER_PROPERTIES );
		String result = "";

		try {
			TagNode rootNode = cleaner.clean( new StringReader( messageText ) );

			TagNode[] nodes = rootNode.getElementsByName( "a", true );
			if ( nodes != null && nodes.length > 0 ) {
				for (TagNode tagnode : nodes) {
					tagnode.removeAttribute( "target" );
					tagnode.addAttribute( "target", "_blank" );
				}
			}

			nodes = rootNode.getElementsByName( "img", true );
			if ( nodes != null && nodes.length > 0 ) {
				hasImages[0] = true;
				if ( !loadImages ) {
					for (TagNode tagnode : nodes) {
						tagnode.removeAttribute( "src" );
						tagnode.addAttribute( "src", "NO_IMAGE" );
					}
				}
			}

			result = cleaner.getInnerHtml( rootNode );
		}
		catch (IOException e) {
			log.error( e.getMessage(), e );
		}

		return result;
	}

	/**
	 * Process plain text messages for display.
	 * 
	 * @param plainText
	 * @return
	 */
	public static String formatPlainText( String plainText, MessageTextMode mode ) {

		if ( !StringUtils.isEmpty( plainText ) ) {
			if ( mode == MessageTextMode.REPLY ) {
				return quotePlainText( plainText );
			}
			else if ( mode == MessageTextMode.DISPLAY ) {
				return convertPlainText2Html( plainText, mode );
			}
		}

		return plainText;
	}

	/**
	 * Convert html text to plain text.
	 * 
	 * @param htmlText
	 * @return
	 */
	public static String convertHtml2PlainText( String htmlText ) {

		HtmlCleaner cleaner = new HtmlCleaner( CLEANER_PROPERTIES );

		try {
			TagNode rootNode = cleaner.clean( new StringReader( htmlText ) );
			return rootNode.getText().toString();
		}
		catch (IOException e) {
			log.error( e.getMessage(), e );
		}

		return "";
	}

	/**
	 * Convert a plaint text to html.
	 * 
	 * @param plainText
	 * @return
	 */
	public static String convertPlainText2Html( String plainText, MessageTextMode mode ) {

		try {
			plainText = HtmlUtils.htmlEscape( plainText ).replaceAll( REPL_LINEBREAK, HTML_BR );

			final Matcher m = PATTERN_HREF.matcher( plainText );
			final StringBuffer sb = new StringBuffer( plainText.length() );
			final StringBuilder tmp = new StringBuilder( 256 );
			while (m.find()) {
				final String nonHtmlLink = m.group( 1 );
				if ( (nonHtmlLink == null) || (hasSrcAttribute( plainText, m.start( 1 ) )) ) {
					m.appendReplacement( sb, Matcher.quoteReplacement( checkTarget( m.group() ) ) );
				}
				else {
					tmp.setLength( 0 );
					m.appendReplacement( sb, tmp.append( "<a href=\"" ).append(
							(nonHtmlLink.startsWith( "www" ) || nonHtmlLink.startsWith( "news" ) ? "http://" : "") )
							.append( "$1\" target=\"_blank\">$1</a>" ).toString() );
				}
			}
			m.appendTail( sb );

			if ( mode == MessageTextMode.DISPLAY ) {
				sb.insert( 0, "<p style=\"font-family: monospace; font-size: 10pt;\">" );
				sb.append( "</p>" );
			}

			return sb.toString();
		}
		catch (final Exception e) {
			log.error( e.getMessage(), e );
		}
		catch (final StackOverflowError error) {
			log.error( StackOverflowError.class.getName(), error );
		}

		return plainText;
	}

	/**
	 * @param line
	 * @param urlStart
	 * @return
	 */
	private static boolean hasSrcAttribute( final String line, final int urlStart ) {

		return (urlStart >= 5)
				&& ((STR_IMG_SRC.equalsIgnoreCase( line.substring( urlStart - 5, urlStart - 1 ) )) || (STR_IMG_SRC
						.equalsIgnoreCase( line.substring( urlStart - 4, urlStart ) )));
	}

	/**
	 * @param anchorTag
	 * @return
	 */
	private static String checkTarget( final String anchorTag ) {

		final Matcher m = PATTERN_TARGET.matcher( anchorTag );
		if ( m.matches() ) {
			if ( !STR_BLANK.equalsIgnoreCase( m.group( 2 ) ) ) {
				final StringBuilder sb = new StringBuilder( 128 );
				return sb.append( m.group( 1 ) ).append( STR_BLANK ).append( m.group( 3 ) ).toString();
			}
			return anchorTag;
		}

		final int pos = anchorTag.indexOf( '>' );
		if ( pos == -1 ) {
			return anchorTag;
		}
		final StringBuilder sb = new StringBuilder( anchorTag.length() + 16 );
		return sb.append( anchorTag.substring( 0, pos ) ).append( " target=\"" ).append( STR_BLANK ).append( '"' )
				.append( anchorTag.substring( pos ) ).toString();
	}

	/**
	 * Reads the string out of part's input stream. On first try the input
	 * stream retrieved by <code>javax.mail.Part.getInputStream()</code> is
	 * used. If an I/O error occurs (<code>java.io.IOException</code>) then the
	 * next try is with part's raw input stream. If everything fails an empty
	 * string is returned.
	 * 
	 * @param p
	 *            - the <code>javax.mail.Part</code> object
	 * @param ct
	 *            - the part's content type
	 * @return the string read from part's input stream or the empty string ""
	 *         if everything failed
	 * @throws MessagingException
	 *             - if an error occurs in part's getter methods
	 */
	public static String readPart( final Part p ) throws MessagingException {

		String contentType = p.getContentType();
		ContentType type = new ContentType( contentType );

		/*
		 * Use specified charset if available else use default one
		 */
		String charset = type.getParameter( "charset" );
		if ( null == charset || charset.equalsIgnoreCase( CubusConstants.US_ASCII ) ) {
			charset = CubusConstants.DEFAULT_CHARSET;
		}
		try {
			return readStream( p.getInputStream(), charset );
		}
		catch (final IOException e) {
			/*
			 * Try to get data from raw input stream
			 */
			final InputStream inStream;
			if ( p instanceof MimeBodyPart ) {
				final MimeBodyPart mpb = (MimeBodyPart) p;
				inStream = mpb.getRawInputStream();
			}
			else if ( p instanceof MimeMessage ) {
				final MimeMessage mm = (MimeMessage) p;
				inStream = mm.getRawInputStream();
			}
			else {
				inStream = null;
			}
			if ( inStream == null ) {
				/*
				 * Neither a MimeBodyPart nor a MimeMessage
				 */
				return "";
			}
			try {
				return readStream( inStream, charset );
			}
			catch (final IOException e1) {
				log.error( e1.getLocalizedMessage(), e1 );
				return e1.getLocalizedMessage();
				// return STR_EMPTY;
			}
			finally {
				try {
					inStream.close();
				}
				catch (final IOException e1) {
					log.error( e1.getLocalizedMessage(), e1 );
				}
			}
		}
	}

	/**
	 * Reads a string from given input stream using direct buffering
	 * 
	 * @param inStream
	 *            - the input stream
	 * @param charset
	 *            - the charset
	 * @return the <code>String</code> read from input stream
	 * @throws IOException
	 *             - if an I/O error occurs
	 */
	public static String readStream( final InputStream inStream, final String charset ) throws IOException {

		InputStreamReader isr = null;
		try {
			int count = 0;
			final char[] c = new char[BUFSIZE];
			isr = new InputStreamReader( inStream, charset );
			if ( (count = isr.read( c )) > 0 ) {
				final StringBuilder sb = new StringBuilder( STRBLD_SIZE );
				do {
					sb.append( c, 0, count );
				}
				while ((count = isr.read( c )) > 0);
				return sb.toString();
			}
			return "";
		}
		catch (final UnsupportedEncodingException e) {
			log.error( "Unsupported encoding in a message detected and monitored.", e );
			return "";
		}
		finally {
			if ( null != isr ) {
				try {
					isr.close();
				}
				catch (final IOException e) {
					log.error( e.getLocalizedMessage(), e );
				}
			}
		}
	}

	private static String createImageMessageText( long id ) {

		String imageText = "<img src=\"" + "cubusmail/retrieveImage.rpc?messageId=" + id
				+ "&attachmentIndex=0&thumbnail=false" + "\" />";
		return imageText;
	}

	/**
	 * @param textContent
	 * @return
	 */
	private static String quotePlainText( final String textContent ) {

		return textContent.replaceAll( "(?m)^", "> " );
	}
}
