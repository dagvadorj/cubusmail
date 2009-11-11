/* HtmlParserTest.java
 */
package com.cubusmail.mail.text.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

/**
 * 
 * @author Juergen Schlierf
 */
public class HtmlParserTest {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		File file = new File( "C:\\projects\\cubusmail\\projects\\cubusmail\\docs\\testhtml.html" );
		StringBuilder contents = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader( new FileReader( file ) );
			String line = null;
			while ((line = reader.readLine()) != null) {
				contents.append( line );
				contents.append( System.getProperty( "line.separator" ) );
			}

			// String parsed = javaHtmlParser( contents.toString() );
			String parsed = htmlCleaner( contents.toString() );
			System.out.println( parsed );

			FileWriter writer = new FileWriter( new File( "C:\\Temp\\htmlout.html" ) );
			writer.write( parsed, 0, parsed.length() );
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String javaHtmlParser( String content ) throws ParserException {

		final NodeVisitor linkVisitor = new NodeVisitor() {

			@Override
			public void visitTag( Tag tag ) {

				String name = tag.getTagName();

				if ( "a".equalsIgnoreCase( name ) ) {
					tag.setAttribute( "target", "_blank" );
				} else if ( "style".equalsIgnoreCase( name ) && tag instanceof StyleTag ) {
					StyleTag styleTag = (StyleTag) tag;
					if ( styleTag.getChildCount() > 0 ) {
						styleTag.removeChild( 0 );
					}
				} else if ( "img".equalsIgnoreCase( name ) && tag instanceof ImageTag ) {
					ImageTag imageTag = (ImageTag) tag;
					imageTag.setImageURL( "NOIMAGE" );
				}
			}

		};

		Parser parser = new Parser();
		parser.setFeedback( new DefaultParserFeedback() );
		parser.setInputHTML( content );

		NodeList list = parser.parse( null );
		list.visitAllNodesWith( linkVisitor );

		System.out.println( list.elementAt( 0 ).toPlainTextString() );

		if ( list.size() > 0 ) {
			return list.elementAt( 0 ).toHtml();
		} else {
			return null;
		}
	}

	private static String htmlCleaner( String content ) {

		CleanerProperties props = new CleanerProperties();
		props.setUseCdataForScriptAndStyle( false );
		props.setAllowHtmlInsideAttributes( false );
		props.setPruneTags( "style, script" );

		HtmlCleaner cleaner = new HtmlCleaner( props );
		try {
			TagNode node = cleaner.clean( new StringReader( content ) );

			TagNode[] nodes = node.getElementsByName( "a", true );
			for (TagNode tagnode : nodes) {
				tagnode.removeAttribute( "target" );
				tagnode.addAttribute( "target", "_blank" );
			}
			nodes = node.getElementsByName( "img", true );
			for (TagNode tagnode : nodes) {
				tagnode.removeAttribute( "src" );
				tagnode.addAttribute( "src", "NO_IMAGE" );
			}
			System.out.println( node.getText() );

			return cleaner.getInnerHtml( node );

		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}
}
