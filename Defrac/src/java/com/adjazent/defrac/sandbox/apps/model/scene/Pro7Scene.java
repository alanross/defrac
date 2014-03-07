package com.adjazent.defrac.sandbox.apps.model.scene;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7Scene
{
	public final String id;

	private LinkedList<Pro7SceneElement> _elements;

	public Pro7Scene( String id )
	{
		this.id = id;

		_elements = new LinkedList<Pro7SceneElement>();
	}

	public Pro7SceneElement add( Pro7SceneElement item )
	{
		if( has( item ) )
		{
			throw new ElementAlreadyExistsError( this + " " + item + " already exists" );
		}

		_elements.addLast( item );

		return item;
	}

	public Pro7SceneElement remove( Pro7SceneElement item )
	{
		if( has( item ) )
		{
			throw new ElementDoesNotExistError( this + " " + item + " does not exist" );
		}

		_elements.remove( item );

		return item;
	}

	public boolean has( Pro7SceneElement item )
	{
		return ( -1 != _elements.indexOf( item ) );
	}

	public Pro7SceneElement getAt( int index )
	{
		return _elements.get( index );
	}

	public int numElements()
	{
		return _elements.size();
	}

	@Override
	public String toString()
	{
		return "[Pro7Scene]";
	}
}