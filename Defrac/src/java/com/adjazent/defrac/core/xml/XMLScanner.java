package com.adjazent.defrac.core.xml;

import java.util.Enumeration;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class XMLScanner
{
	public XMLScanner()
	{
	}

	public LinkedList<XMLNode> getAllByAttribute( XMLNode node, String needle )
	{
		LinkedList<XMLNode> result = new LinkedList<XMLNode>();

		recursiveGetByAttribute( result, node, needle );

		return result;
	}

	public LinkedList<XMLNode> getAllByName( XMLNode node, String needle )
	{
		LinkedList<XMLNode> result = new LinkedList<XMLNode>();

		recursiveGetByName( result, node, needle );

		return result;
	}

	public XMLNode getFirstByName( XMLNode node, String needle )
	{
		return recursiveGetFirstByName( node, needle );
	}

	public String getInfo( XMLNode node )
	{
		StringBuilder result = new StringBuilder();

		recursiveGetInfo( result, node, "" );

		return result.toString();
	}

	private void recursiveGetInfo( StringBuilder result, XMLNode node, String level )
	{
		if( node != null )
		{
			String att = node.getAttributes();

			if( att.length() > 0 )
			{
				att = " " + att;
			}

			if( node.isLeaf() )
			{
				if( node.hasValue() )
				{
					result.append( level + "<" + node.name + att + ">" + node.value + "</" + node.name + ">\n" );
				}
				else
				{
					result.append( level + "<" + node.name + att + "/>\n" );
				}
			}
			else
			{
				result.append( level + "<" + node.name + att + ">\n" );

				for( XMLNode child : node.children )
				{
					recursiveGetInfo( result, child, level + "\t" );
				}

				result.append( level + "</" + node.name + ">\n" );
			}
		}
	}

	private void recursiveGetByName( LinkedList<XMLNode> result, XMLNode node, String needle )
	{
		if( node == null )
		{
			return;
		}

		if( node.name.equals( needle ) )
		{
			result.add( node );
		}

		for( XMLNode child : node.children )
		{
			recursiveGetByName( result, child, needle );
		}
	}

	private XMLNode recursiveGetFirstByName( XMLNode node, String needle )
	{
		if( node != null )
		{
			if( node.name.equals( needle ) )
			{
				return node;
			}

			for( XMLNode child : node.children )
			{
				XMLNode result = recursiveGetFirstByName( child, needle );

				if( result != null )
				{
					return result;
				}
			}
		}

		return null;
	}

	private void recursiveGetByAttribute( LinkedList<XMLNode> result, XMLNode node, String needle )
	{
		if( node == null )
		{
			return;
		}

		Enumeration<String> items = node.attributes.keys();

		while( items.hasMoreElements() )
		{
			String key = items.nextElement();

			if( key.equalsIgnoreCase( needle ) )
			{
				result.add( node );
			}
		}

		for( XMLNode child : node.children )
		{
			recursiveGetByAttribute( result, child, needle );
		}
	}

	@Override
	public String toString()
	{
		return "[XMLScanner]";
	}
}