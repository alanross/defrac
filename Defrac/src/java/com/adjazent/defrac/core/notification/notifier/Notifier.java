package com.adjazent.defrac.core.notification.notifier;

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

	public Notifier()
	{
	}

	public INotifierObserver add( INotifierObserver observer )
	{
		if( null == _observers )
		{
			_observers = new LinkedList<INotifierObserver>();
			_observerCount = 0;
		}

		if( null == observer )
		{
			throw new NullError( "observer can not be null" );
		}

		if( -1 != _observers.indexOf( observer ) )
		{
			throw new ElementAlreadyExistsError( observer + " already exists" );
		}

		_observers.addFirst( observer );
		_observerCount++;

		return observer;
	}

	public INotifierObserver remove( INotifierObserver observer )
	{
		if( null == observer )
		{
			throw new NullError( "observer can not be null" );
		}

		if( null == _observers )
		{
			throw new ElementDoesNotExistError( observer + " does not exist" );
		}

		final int index = _observers.indexOf( observer );

		if( -1 == index )
		{
			throw new ElementDoesNotExistError( observer + " does not exist" );
		}

		_observers.remove( observer );
		_observerCount--;

		return observer;
	}

	public boolean has( INotifierObserver observer )
	{
		if( null == observer )
		{
			throw new NullError( "observer can not be null" );
		}

		if( null == _observers )
		{
			return false;
		}

		final int index = _observers.indexOf( observer );

		return ( -1 != index );
	}

	public void dispatch( INotifierEvent event, boolean resetEventAfterDispatch )
	{
		// !important:
		// the number of the observers is subject to change while
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

	public void clear()
	{
		_observers.clear();
		_observerCount = 0;
	}

	@Override
	public void dispose()
	{
		clear();

		_observers = null;
	}

	public int getNumObservers()
	{
		return _observerCount;
	}

	public LinkedList<INotifierObserver> getObservers()
	{
		return _observers;
	}

	@Override
	public String toString()
	{
		return "[Notifier]";
	}
}

