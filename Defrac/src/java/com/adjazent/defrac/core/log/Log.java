package com.adjazent.defrac.core.log;

import com.adjazent.defrac.core.error.NullError;
import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.core.utils.ArrayUtils;
import com.adjazent.defrac.core.utils.IDisposable;

import java.util.LinkedList;

/**
 * Provides functions to write messages to an output medium.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class Log implements IDisposable
{
	private static Log _instance;

	private static LinkedList<ILogOutput> _outputs;

	private LinkedList<String> _logRecord;

	private static long _timeStart;

	/**
	 * Creates an instance of Log and initializes it.
	 */
	public static void initialize()
	{
		if( _instance != null )
		{
			throw new SingletonError( "Log" );
		}

		_instance = new Log();
	}

	/**
	 * Returns the single instance of Log.
	 */
	public static Log get()
	{
		return _instance;
	}

	/**
	 * Write a trace message to the log.
	 */
	public static void trace( ContextInfo category, Object... params )
	{
		if( _instance == null )
		{
			throw new NullError( "Log instance trace is null" );
		}

		if( category.permitOutput( Level.TRACE ) )
		{
			final String message = ArrayUtils.join( params, " " );

			_instance.write( Level.TRACE, category, message );
		}
	}

	/**
	 * Write an info message to the log.
	 */
	public static void info( ContextInfo category, Object... params )
	{
		if( _instance == null )
		{
			throw new NullError( "Log instance info is null" );
		}

		if( category.permitOutput( Level.INFO ) )
		{
			final String message = ArrayUtils.join( params, " " );

			_instance.write( Level.INFO, category, message );
		}
	}

	/**
	 * Write a warning message to the log.
	 */
	public static void warn( ContextInfo category, Object... params )
	{
		if( _instance == null )
		{
			throw new NullError( "Log instance warn is null" );
		}

		if( category.permitOutput( Level.WARN ) )
		{
			final String message = ArrayUtils.join( params, " " );

			_instance.write( Level.WARN, category, message );
		}
	}

	/**
	 * Write an error message to the log.
	 */
	public static void error( ContextInfo category, Object... params )
	{
		if( _instance == null )
		{
			throw new NullError( "Log instance error is null" );
		}

		if( category.permitOutput( Level.ERROR ) )
		{
			final String message = ArrayUtils.join( params, " " );

			_instance.write( Level.ERROR, category, message );
		}
	}

	/**
	 * Write an fatal error message to the log.
	 */
	public static void fatal( ContextInfo category, Object... params )
	{
		if( _instance == null )
		{
			throw new NullError( "Log instance fatal is null" );
		}

		if( category.permitOutput( Level.FATAL ) )
		{
			final String message = ArrayUtils.join( params, " " );

			_instance.write( Level.FATAL, category, message );
		}
	}

	/**
	 * Creates a new instance of Log.
	 */
	private Log()
	{
		_outputs = new LinkedList<ILogOutput>();

		_logRecord = new LinkedList<String>();
	}

	/**
	 * Add an output for the log message to be written to.
	 */
	public void addOutput( ILogOutput output )
	{
		if( !hasOutput( output ) )
		{
			_outputs.push( output );
		}
	}

	/**
	 * Remove given output from the log.
	 */
	public void removeOutput( ILogOutput output )
	{
		if( hasOutput( output ) )
		{
			_outputs.remove( output );
		}
	}

	/**
	 * Returns true, if the log has given output, false otherwise.
	 */
	public boolean hasOutput( ILogOutput output )
	{
		final int index = _outputs.indexOf( output );

		return ( -1 != index );
	}

	/**
	 * Create and return a string containing all log messages.
	 */
	public String dump()
	{
		return dump( "\n" );
	}

	/**
	 * Create and return a string containing all log messages.
	 */
	public String dump( String separator )
	{
		return ArrayUtils.join( _logRecord, separator );
	}

	public static void timeStart()
	{
		_timeStart = System.currentTimeMillis();
	}

	public static void timeEnd()
	{
		timeEnd( "" );
	}

	public static void timeEnd( String msg )
	{
		long delta = System.currentTimeMillis() - _timeStart;

		if( msg != null )
		{
			info( Context.TIME, msg + " : " + delta + "ms" );
		}
		else
		{
			info( Context.TIME, delta + "ms" );
		}
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
		_logRecord = null;

		while( !_outputs.isEmpty() )
		{
			_outputs.removeLast();
		}

		_outputs = null;
	}

	/**
	 * Direct the log message to attached output medium.
	 */
	private void write( Level level, ContextInfo context, String message )
	{
		_logRecord.addLast( ( "[" + level.getName().toUpperCase() + " :: " + context.getName().toUpperCase() + "] " + message ) );

		int n = _outputs.size();

		while( --n > -1 )
		{
			_outputs.get( n ).write( level, context, message );
		}
	}

	/**
	 * Creates and returns a string representation of the Log object.
	 */
	@Override
	public String toString()
	{
		return "[Log]";
	}
}
