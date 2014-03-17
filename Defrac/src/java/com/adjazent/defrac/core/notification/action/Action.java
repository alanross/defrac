package com.adjazent.defrac.core.notification.action;


import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class Action
{
	private static int __id = -1;

	public final int type;

	public Object origin;

	private LinkedList<IActionObserver> _observer;

	public static int createUniqueType()
	{
		return ++__id;
	}

	public Action( int type )
	{
		this.type = type;

		_observer = new LinkedList<IActionObserver>();
	}

	public void add( IActionObserver observer )
	{
		if( has( observer ) )
		{
			throw new ElementAlreadyExistsError();
		}

		_observer.addFirst( observer );
	}

	public void remove( IActionObserver observer )
	{
		if( !has( observer ) )
		{
			throw new ElementDoesNotExistError();
		}

		_observer.remove( observer );
	}

	public boolean has( IActionObserver observer )
	{
		return ( -1 != _observer.indexOf( observer ) );
	}

	public void send( Object origin )
	{
		this.origin = origin;

		int n = _observer.size();

		while( --n > -1 )
		{
			_observer.get( n ).onActionEvent( this );
		}
	}

	public void dispose()
	{
		_observer.clear();
		_observer = null;
	}

	@Override
	public String toString()
	{
		return "[Action type:" + type + ", origin:" + origin + "]";
	}
}

