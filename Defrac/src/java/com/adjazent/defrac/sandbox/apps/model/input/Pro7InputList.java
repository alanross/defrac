package com.adjazent.defrac.sandbox.apps.model.input;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7InputList<T>
{
	private LinkedList<T> _inputs;

	public Pro7InputList()
	{
		_inputs = new LinkedList<T>();
	}

	public T add( T input )
	{
		if( has( input ) )
		{
			throw new ElementAlreadyExistsError( this + " " + input + " already exists" );
		}

		_inputs.addLast( input );

		return input;
	}

	public T remove( T input )
	{
		if( has( input ) )
		{
			throw new ElementDoesNotExistError( this + " " + input + " does not exist" );
		}

		_inputs.remove( input );

		return input;
	}

	public boolean has( T input )
	{
		return ( -1 != _inputs.indexOf( input ) );
	}

	public T getAt( int index )
	{
		return _inputs.get( index );
	}

	public int numInputs()
	{
		return _inputs.size();
	}

	@Override
	public String toString()
	{
		return "[Pro7Input]";
	}
}