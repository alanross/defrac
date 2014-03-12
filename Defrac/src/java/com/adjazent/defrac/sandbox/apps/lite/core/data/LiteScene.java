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
	public static final int ELEMENT_ADDED = 1;
	public static final int ELEMENT_REMOVED = 2;

	public final String id;

	private final LinkedList<ILiteSceneObserver> _observers;

	private final LinkedList<LiteSceneElement> _elements;

	public LiteScene( String id )
	{
		this.id = id;

		_elements = new LinkedList<LiteSceneElement>();
		_observers = new LinkedList<ILiteSceneObserver>();
	}

	public void addObserver( ILiteSceneObserver observer )
	{
		if( hasObserver( observer ) )
		{
			throw new ElementAlreadyExistsError();
		}

		_observers.addFirst( observer );
	}

	public void removeObserver( ILiteSceneObserver observer )
	{
		if( !hasObserver( observer ) )
		{
			throw new ElementDoesNotExistError();
		}

		_observers.remove( observer );
	}

	public boolean hasObserver( ILiteSceneObserver signalObserver )
	{
		return ( -1 != _observers.indexOf( signalObserver ) );
	}

	private void notify( LiteSceneElement item, int type )
	{
		int n = _observers.size();

		while( --n > -1 )
		{
			_observers.get( n ).onLiteSceneModified( item, type );
		}
	}

	public LiteSceneElement add( LiteSceneElement item )
	{
		if( has( item ) )
		{
			throw new ElementAlreadyExistsError( this + " " + item + " already exists" );
		}

		_elements.addLast( item );

		notify( item, ELEMENT_ADDED );

		return item;
	}

	public LiteSceneElement remove( LiteSceneElement item )
	{
		if( has( item ) )
		{
			throw new ElementDoesNotExistError( this + " " + item + " does not exist" );
		}

		_elements.remove( item );

		notify( item, ELEMENT_REMOVED );

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

	@Override
	public String toString()
	{
		return "[LiteScene id:" + id + ", numItems:" + _elements.size() + "]";
	}
}