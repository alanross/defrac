package com.adjazent.defrac.core.xml;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class XML
{
	private XMLParser _parser = new XMLParser();
	private XMLScanner _scanner = new XMLScanner();
	private XMLNode _root;

	public XML( String value )
	{
		_root = _parser.process( value );
	}

	public LinkedList<XMLNode> getAllByAttribute( String needle )
	{
		return _scanner.getAllByAttribute( _root, needle );
	}

	public LinkedList<XMLNode> getAllByAttribute( XMLNode node, String needle )
	{
		return _scanner.getAllByAttribute( node, needle );
	}

	public LinkedList<XMLNode> getAllByName( String needle )
	{
		return _scanner.getAllByName( _root, needle );
	}

	public LinkedList<XMLNode> getAllByName( XMLNode node, String needle )
	{
		return _scanner.getAllByName( node, needle );
	}

	public XMLNode getFirstByName( String needle )
	{
		return _scanner.getFirstByName( _root, needle );
	}

	public String getInfo()
	{
		return _scanner.getInfo( _root );
	}

	public String getInfo( XMLNode node )
	{
		return _scanner.getInfo( node );
	}

	public XMLNode getRoot()
	{
		return _root;
	}

	@Override
	public String toString()
	{
		return "[XML]";
	}
}