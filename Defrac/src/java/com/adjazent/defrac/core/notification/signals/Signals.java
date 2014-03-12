package com.adjazent.defrac.core.notification.signals;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class Signals
{
	private static final LinkedList<ISignalReceiver> receivers = new LinkedList<ISignalReceiver>();
	private static final LinkedList<Integer> types = new LinkedList<Integer>();

	private static int __id = -1;

	public static int createTypeID()
	{
		__id++;

		if( hasType( __id ) )
		{
			throw new ElementAlreadyExistsError();
		}

		types.addFirst( __id );

		return __id;
	}

	public static void send( ISignalSource signalSource, int signalType )
	{
		if( !hasType( signalType ) )
		{
			throw new ElementDoesNotExistError( "Signals send." );
		}

		int n = receivers.size();

		while( --n > -1 )
		{
			receivers.get( n ).onSignal( signalSource, signalType );
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

	public static boolean hasType( int signalType )
	{
		return ( -1 != types.indexOf( signalType ) );
	}

	public static void addReceiver( ISignalReceiver signalReceiver )
	{
		if( hasReceiver( signalReceiver ) )
		{
			throw new ElementAlreadyExistsError();
		}

		receivers.addFirst( signalReceiver );
	}

	public static void removeReceiver( ISignalReceiver signalReceiver )
	{
		if( !hasReceiver( signalReceiver ) )
		{
			throw new ElementDoesNotExistError();
		}

		receivers.remove( signalReceiver );
	}

	public static boolean hasReceiver( ISignalReceiver signalReceiver )
	{
		return ( -1 != receivers.indexOf( signalReceiver ) );
	}

	public Signals()
	{

	}

	@Override
	public String toString()
	{
		return "[Signals]";
	}
}


