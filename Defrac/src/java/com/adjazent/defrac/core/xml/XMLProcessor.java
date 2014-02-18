package com.adjazent.defrac.core.xml;

import java.util.LinkedList;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class XMLProcessor
{
	public XMLProcessor()
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
			String atts = node.getAttributes();

			if( atts.length() > 0 )
			{
				atts = " " + atts;
			}

			if( node.isLeaf() )
			{
				if( node.hasValue() )
				{
					result.append( level + "<" + node.name + atts + ">" + node.value + "</" + node.name + ">\n" );
				}
				else
				{
					result.append( level + "<" + node.name + atts + "/>\n" );
				}
			}
			else
			{
				result.append( level + "<" + node.name + atts + ">\n" );

				Iterator<XMLNode> it = node.children.iterator();

				while( it.hasNext() )
				{
					recursiveGetInfo( result, it.next(), level + "\t" );
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

		Iterator<XMLNode> it = node.children.iterator();

		while( it.hasNext() )
		{
			recursiveGetByName( result, it.next(), needle );
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

			Iterator<XMLNode> it = node.children.iterator();

			while( it.hasNext() )
			{
				XMLNode result = recursiveGetFirstByName( it.next(), needle );

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
				continue;
			}
		}

		Iterator<XMLNode> it = node.children.iterator();

		while( it.hasNext() )
		{
			recursiveGetByAttribute( result, it.next(), needle );
		}
	}

	@Override
	public String toString()
	{
		return "[XMLProcessor]";
	}
}