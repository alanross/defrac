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
	private String _type;

	private Object _origin;

	private LinkedList<IActionObserver> _observer;

	public Action( String type )
	{
		_type = type;

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
		_origin = origin;

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

		_type = null;
		_origin = null;
	}

	public String getType()
	{
		return _type;
	}

	public Object getOrigin()
	{
		return _origin;
	}

	@Override
	public String toString()
	{
		return "[Action type:" + _type + ", origin:" + _origin + "]";
	}
}

