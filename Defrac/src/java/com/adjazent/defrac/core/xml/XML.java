package com.adjazent.defrac.core.xml;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class XML
{
	private XMLParser _parser;
	private XMLProcessor _processor;
	private XMLNode _root;

	public XML( String value )
	{
		_parser = new XMLParser();
		_processor = new XMLProcessor();

		_root = _parser.process( value );
	}

	public LinkedList<XMLNode> getAllByAttribute( String needle )
	{
		return _processor.getAllByAttribute( _root, needle );
	}

	public LinkedList<XMLNode> getAllByAttribute( XMLNode node, String needle )
	{
		return _processor.getAllByAttribute( node, needle );
	}

	public LinkedList<XMLNode> getAllByName( String needle )
	{
		return _processor.getAllByName( _root, needle );
	}

	public LinkedList<XMLNode> getAllByName( XMLNode node, String needle )
	{
		return _processor.getAllByName( node, needle );
	}

	public XMLNode getFirstByName( String needle )
	{
		return _processor.getFirstByName( _root, needle );
	}

	public String getInfo()
	{
		return _processor.getInfo( _root );
	}

	public String getInfo( XMLNode node )
	{
		return _processor.getInfo( node );
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