package com.adjazent.defrac.core.log;

/**
 * Defines the level of the log messages.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class Level
{
	//enumeration of default level types.

	public static final Level OFF = new Level( "OFF", -1 );
	public static final Level TRACE = new Level( "TRACE", 1 );
	public static final Level INFO = new Level( "INFO", 2 );
	public static final Level WARN = new Level( "WARN", 3 );
	public static final Level ERROR = new Level( "ERROR", 4 );
	public static final Level FATAL = new Level( "FATAL", 5 );

	private String _name;
	private int _priority;

	/**
	 * Creates a new instance of Level.
	 */
	public Level( String theName, int thePriority )
	{
		_name = theName;
		_priority = thePriority;
	}

	/**
	 * Returns the name of the level.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * Returns the priority of the level.
	 */
	public int getPriority()
	{
		return _priority;
	}

	/**
	 * Creates and returns a string representation of the Level object.
	 */
	@Override
	public String toString()
	{
		return "[Level]";
	}
}

