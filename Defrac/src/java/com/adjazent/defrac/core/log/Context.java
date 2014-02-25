package com.adjazent.defrac.core.log;

/**
 * The context the message is in when sent to log.
 * This helps to separate different messages and give them different priorities.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class Context
{
	public static final ContextInfo DEFAULT = new ContextInfo( "DEFAULT", Level.INFO, Level.FATAL );
	public static final ContextInfo UI = new ContextInfo( "UI", Level.INFO, Level.FATAL );
	public static final ContextInfo NET = new ContextInfo( "NET", Level.INFO, Level.FATAL );
	public static final ContextInfo TIME = new ContextInfo( "TIME", Level.INFO, Level.FATAL );

	/**
	 * Set the min and max level of logging for given context.
	 */
	public static void setLevels( ContextInfo context, Level minLevel, Level maxLevel )
	{
		context.minLevel = minLevel;
		context.maxLevel = maxLevel;
	}

	/**
	 * Creates and returns a string representation of the Context object.
	 */
	@Override
	public String toString()
	{
		return "[Context]";
	}
}

