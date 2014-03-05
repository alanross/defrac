package com.adjazent.defrac.core.notification.signals;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Signals
{
	private static final LinkedList<ISignalReceiver> receivers = new LinkedList<ISignalReceiver>();
	private static final LinkedList<Integer> types = new LinkedList<Integer>();

	public static void send( int signalType, ISignalSource signalSource )
	{
		if( !hasType( signalType ) )
		{
			throw new ElementDoesNotExistError( "Signals send." );
		}

		int n = receivers.size();

		while( --n > -1 )
		{
			receivers.get( n ).onSignal( signalType, signalSource );
		}
	}

	public static void addType( int signalType )
	{
		if( hasType( signalType ) )
		{
			throw new ElementAlreadyExistsError( "Signals addType: " + signalType );
		}

		types.addFirst( signalType );
	}

	public static boolean hasType( int type )
	{
		return ( -1 != types.indexOf( type ) );
	}

	public static void addReceiver( ISignalReceiver receiver )
	{
		if( hasReceiver( receiver ) )
		{
			throw new ElementAlreadyExistsError();
		}

		receivers.addFirst( receiver );
	}

	public static void removeReceiver( ISignalReceiver receiver )
	{
		if( !hasReceiver( receiver ) )
		{
			throw new ElementDoesNotExistError();
		}

		receivers.remove( receiver );
	}

	public static boolean hasReceiver( ISignalReceiver receiver )
	{
		return ( -1 != receivers.indexOf( receiver ) );
	}

	private Signals()
	{

	}

	@Override
	public String toString()
	{
		return "[Signals]";
	}
}


