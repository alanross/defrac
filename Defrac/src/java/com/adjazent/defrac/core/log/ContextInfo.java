package com.adjazent.defrac.core.log;

import com.adjazent.defrac.core.error.ArgumentError;

/**
 * Defines the level range of a context.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class ContextInfo
{
	public Level minLevel;
	public Level maxLevel;

	private String _name;

	/**
	 * Creates a new instance of ContextInfo.
	 */
	public ContextInfo( String name, Level minLevel, Level maxLevel )
	{
		if( minLevel.getPriority() >= maxLevel.getPriority() )
		{
			throw new ArgumentError( this, "minLevel can not be greater or equal maxLevel" );
		}

		_name = name;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
	}

	/**
	 * Returns true if provided level is in range of permitted output, false otherwise.
	 */
	public boolean permitOutput( Level level )
	{
		final int priority = level.getPriority();

		return !( priority == -1 || priority < minLevel.getPriority() || priority > maxLevel.getPriority() );
	}

	/**
	 * Returns the name property of the ContextInfo.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * Creates and returns a string representation of the ContextInfo object.
	 */
	@Override
	public String toString()
	{
		return "[ContextInfo]";
	}
}

