package com.adjazent.defrac.sandbox.apps.lite.core.data;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteScene
{
	public final String id;

	private LinkedList<LiteSceneElement> _elements;

	private boolean _active = false;

	public LiteScene( String id )
	{
		this.id = id;

		_elements = new LinkedList<LiteSceneElement>();
	}

	public LiteSceneElement add( LiteSceneElement item )
	{
		if( has( item ) )
		{
			throw new ElementAlreadyExistsError( this + " " + item + " already exists" );
		}

		_elements.addLast( item );

		return item;
	}

	public LiteSceneElement remove( LiteSceneElement item )
	{
		if( has( item ) )
		{
			throw new ElementDoesNotExistError( this + " " + item + " does not exist" );
		}

		_elements.remove( item );

		return item;
	}

	public boolean has( LiteSceneElement item )
	{
		return ( -1 != _elements.indexOf( item ) );
	}

	public LiteSceneElement get( int index )
	{
		return _elements.get( index );
	}

	public int numElements()
	{
		return _elements.size();
	}

	public boolean active()
	{
		return _active;
	}

	public void active( boolean value )
	{
		_active = value;
	}

	@Override
	public String toString()
	{
		return "[LiteScene]";
	}
}