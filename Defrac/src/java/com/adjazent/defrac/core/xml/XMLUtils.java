package com.adjazent.defrac.core.xml;

import com.adjazent.defrac.core.utils.StringUtils;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class XMLUtils
{
	public static final char TAG_OPEN = '<';
	public static final char TAG_CLOSE = '>';
	public static final char QUOTE_SINGLE = '\'';
	public static final char QUOTE_DOUBLE = '\"';
	public static final char EQUAL = '=';
	public static final char SPACE = ' ';

	public static String extractNodeName( String str )
	{
		// strip tag from brackets
		str = stripBrackets( str );

		int index = str.indexOf( SPACE );

		return ( ( index > -1 ) ? str.substring( 0, index ) : str );
	}

	public static String extractNodeAttributes( String str )
	{
		str = stripBrackets( str );

		int index = str.indexOf( SPACE );

		return ( index > -1 ) ? str.substring( index, str.length() ).trim() : "";
	}

	public static String stripComments( String str )
	{
		while( str.contains( "<!--" ) )
		{
			str = StringUtils.cut( str, "<!--", "-->" );
		}

		return str;
	}

	public static String stripBrackets( String str )
	{
		return str.replace( "</", "" ).replace( "<", "" ).replace( "/>", "" ).replace( ">", "" );
	}

	public static Boolean isSelfClosingTag( String str )
	{
		return str.endsWith( "/>" );
	}

	public static Boolean isClosingTag( String str )
	{
		return str.startsWith( "</" );
	}

	public static Boolean isValidTag( String str )
	{
		return str.startsWith( "<" );
	}

	public static Boolean isXmlDefinitionTag( String str )
	{
		return str.startsWith( "<?xml" );
	}

	public static Boolean isQuote( char c )
	{
		return ( c == QUOTE_SINGLE || c == QUOTE_DOUBLE );
	}

	public static Boolean isNonSpecialChar( char c )
	{
		return ( ( int ) c >= 32 ); // ascii check if not special chars
	}

	private XMLUtils()
	{
	}

	@Override
	public String toString()
	{
		return "[XMLUtils]";
	}
}