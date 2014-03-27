package com.adjazent.defrac.core.xml;


import com.adjazent.defrac.core.utils.StringUtils;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class XMLParser
{
	private XMLNode _currentNode;

	private static final String SUB_QUOTE_SPACE = "{{QUOTE_WHITE_PACE}}";
	private static final String SUB_QUOTE_OPEN = "{{QUOTE_TAG_OPEN}}";
	private static final String SUB_QUOTE_CLOSE = "{{QUOTE_TAG_CLOSE}}";

	public XMLParser()
	{
	}

	public XMLNode process( String data )
	{
		_currentNode = null;

		data = encodeSpecialKeys( data );

		Boolean insideOfTag = false;

		String tag = "";
		String val = "";

		for( int i = 0; i < data.length(); i++ )
		{
			char character = data.charAt( i );

			if( character == XMLUtils.TAG_OPEN )
			{
				insideOfTag = true;
			}

			if( insideOfTag )
			{
				if( XMLUtils.isNonSpecialChar( character ) )
				{
					tag += character;
				}

				if( character == XMLUtils.TAG_CLOSE )
				{
					tag = tag.trim();
					val = val.trim();

					if( XMLUtils.isValidTag( tag ) )
					{
						if( XMLUtils.isXmlDefinitionTag( tag ) )
						{
							System.out.println( "XML INFO: " + tag );
						}
						else if( XMLUtils.isClosingTag( tag ) )
						{
							decrementNode( tag, val );
						}
						else
						{
							incrementNode( tag );
						}
					}
					else
					{
						System.out.println( "Ignoring: " + tag + " : " + val );
					}

					val = "";
					tag = "";
					insideOfTag = false;
				}
			}
			else
			{
				if( XMLUtils.isNonSpecialChar( character ) )
				{
					val += character;
				}
			}
		}

		return _currentNode;
	}

	private void incrementNode( String tag )
	{
		XMLNode node = new XMLNode( XMLUtils.extractNodeName( tag ) );

		addAttributes( node, tag );

		// root tag
		if( _currentNode == null )
		{
			_currentNode = node;
		}
		else
		{
			node.parent = _currentNode;
			node.parent.children.add( node );

			if( !XMLUtils.isSelfClosingTag( tag ) )
			{
				_currentNode = node;
			}
		}
	}

	private void decrementNode( String tag, String value )
	{
		if( _currentNode == null )
		{
			return;
		}

		if( XMLUtils.extractNodeName( tag ).equals( _currentNode.name ) )
		{
			_currentNode.value = decodeSpecialKeys( value );

			if( _currentNode.parent != null )
			{
				_currentNode = _currentNode.parent;
			}
		}
	}

	private void addAttributes( XMLNode node, String str )
	{
		String cleaned = XMLUtils.extractNodeAttributes( str );

		if( cleaned.length() == 0 )
		{
			return; // no attributes found in this node
		}

		String[] attributes = StringUtils.split( cleaned, ' ' );

		for( String att : attributes )
		{
			int div = att.indexOf( XMLUtils.EQUAL );

			if( div > -1 )
			{
				String attId = att.substring( 0, div );
				String attVal = att.substring( div + 1 ).trim();

				attVal = StringUtils.trim( attVal, XMLUtils.QUOTE_SINGLE );
				attVal = StringUtils.trim( attVal, XMLUtils.QUOTE_DOUBLE );
				attVal = decodeSpecialKeys( attVal );

				node.attributes.put( attId.trim(), attVal );
			}
		}
	}

	private String encodeSpecialKeys( String str )
	{
		// there is actually more to be done here. Special chars '>' anywhere in the
		// str value or node value have to encoded not just single chars

		str = XMLUtils.stripComments( str );

		str = str.replace( "\' \'", SUB_QUOTE_SPACE );
		str = str.replace( "\" \"", SUB_QUOTE_SPACE );
		str = str.replace( "\"<\"", SUB_QUOTE_OPEN );
		str = str.replace( "\">\"", SUB_QUOTE_CLOSE );

		return str;
	}

	private String decodeSpecialKeys( String str )
	{
		str = str.replace( SUB_QUOTE_SPACE, "\" \"" );
		str = str.replace( SUB_QUOTE_OPEN, "\"<\"" );
		str = str.replace( SUB_QUOTE_CLOSE, "\">\"" );

		return str;
	}

	@Override
	public String toString()
	{
		return "[XMLParser]";
	}
}