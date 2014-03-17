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
	public static final int ITEM_ATTACHED = 1;
	public static final int ITEM_DETACHED = 2;
	public static final int ITEM_SELECTED = 3;
	public static final int ITEM_MODIFIED = 4;

	public final String id;

	private final LinkedList<ILiteSceneObserver> _observers;

	private final LinkedList<LiteSceneItem> _items;

	private LiteSceneItem _selectedItem;

	public LiteScene( String id )
	{
		this.id = id;

		_items = new LinkedList<LiteSceneItem>();
		_observers = new LinkedList<ILiteSceneObserver>();
	}

	void onItemAttached( LiteSceneItem item )
	{
		notify( item, ITEM_ATTACHED );
	}

	void onItemDetached( LiteSceneItem item )
	{
		notify( item, ITEM_DETACHED );
	}

	void onItemSelected( LiteSceneItem item )
	{
		if( item != _selectedItem )
		{
			if( _selectedItem != null )
			{
				_selectedItem.selected( false );
			}

			_selectedItem = item;

			notify( item, ITEM_SELECTED );
		}
	}

	void onItemModified( LiteSceneItem item )
	{
		notify( item, ITEM_MODIFIED );
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

	private void notify( LiteSceneItem item, int type )
	{
		int n = _observers.size();

		while( --n > -1 )
		{
			_observers.get( n ).onLiteSceneModified( item, type );
		}
	}

	public void add( LiteSceneItem item )
	{
		if( has( item ) )
		{
			throw new ElementAlreadyExistsError( this + " " + item + " already exists" );
		}

		_items.addLast( item );

		item.attach( this );
	}

	public void remove( int index )
	{
		remove( _items.get( index ) );
	}

	public void remove( LiteSceneItem item )
	{
		if( !has( item ) )
		{
			throw new ElementDoesNotExistError( this + " " + item + " does not exist" );
		}

		if( _selectedItem == item )
		{
			_selectedItem = null;
		}

		_items.remove( item );

		item.detach( this );
	}

	public boolean has( LiteSceneItem item )
	{
		return ( -1 != _items.indexOf( item ) );
	}

	public LiteSceneItem get( int index )
	{
		return _items.get( index );
	}

	public int numItems()
	{
		return _items.size();
	}

	public void reset()
	{
		if( _selectedItem != null )
		{
			_selectedItem.selected( false );
			_selectedItem = null;
		}
	}

	@Override
	public String toString()
	{
		return "[LiteScene id:" + id + ", numItems:" + _items.size() + "]";
	}
}