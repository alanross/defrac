package com.adjazent.defrac.core.notifier;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.NullError;
import com.adjazent.defrac.core.utils.IDisposable;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Notifier implements IDisposable
{
	private LinkedList<INotifierObserver> _observers;
	private int _observerCount = 0;

	/**
	 * Creates a new instance of Notifier.
	 */
	public Notifier()
	{
	}

	/**
	 * Add the given observer to the list of observers.
	 */
	public INotifierObserver add( INotifierObserver observer )
	{
		if( null == _observers )
		{
			_observers = new LinkedList<INotifierObserver>();
			_observerCount = 0;
		}

		if( null == observer )
		{
			throw new NullError( this, "observer can not be null" );
		}

		if( -1 != _observers.indexOf( observer ) )
		{
			throw new ElementAlreadyExistsError( this, observer + " already exists" );
		}

		_observers.addFirst( observer );
		_observerCount++;

		return observer;
	}

	/**
	 * Remove the given observer from the list of observers.
	 */
	public INotifierObserver remove( INotifierObserver observer )
	{
		if( null == observer )
		{
			throw new NullError( this, "observer can not be null" );
		}

		if( null == _observers )
		{
			throw new ElementDoesNotExistError( this, observer + " does not exist" );
		}

		final int index = _observers.indexOf( observer );

		if( -1 == index )
		{
			throw new ElementDoesNotExistError( this, observer + " does not exist" );
		}

		_observers.remove( observer );
		_observerCount--;

		return observer;
	}

	/**
	 * Returns true if the given observer is part of the list of observers, false otherwise.
	 */
	public boolean has( INotifierObserver observer )
	{
		if( null == observer )
		{
			throw new NullError( this, "observer can not be null" );
		}

		if( null == _observers )
		{
			return false;
		}

		final int index = _observers.indexOf( observer );

		return ( -1 != index );
	}

	/**
	 * Dispatch the event.
	 */
	public void dispatch( INotifierEvent event, boolean resetEventAfterDispatch/*=true*/ )
	{
		// ! important// the number of the observers is subject to change while
		// iterating through the list.

		if( _observers != null )
		{
			int n = _observerCount;

			while( --n > -1 )
			{
				_observers.get( n ).onNotifierEvent( event );
			}
		}

		if( resetEventAfterDispatch )
		{
			event.reset();
		}
	}

	/**
	 * Clear the list of observers. This empties the list.
	 */
	public void clear()
	{
		while( !_observers.isEmpty() )
		{
			_observers.removeLast();
		}

		_observerCount = 0;
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
		clear();

		_observers = null;
	}

	/**
	 * Returns the number of observers in the list.
	 */
	public int getNumObservers()
	{
		return _observerCount;
	}

	/**
	 * Return the list of observers to be notified on changes
	 */
	public LinkedList<INotifierObserver> getObservers()
	{
		return _observers;
	}

	/**
	 * Creates and returns a string representation of the Notifier object.
	 */
	@Override
	public String toString()
	{
		return "[Notifier]";
	}
}

