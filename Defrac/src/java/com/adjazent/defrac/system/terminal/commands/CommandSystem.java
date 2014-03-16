package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.system.terminal.ICommand;
import defrac.util.Platform;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandSystem implements ICommand
{
	/**
	 * Creates a new instance of CommandSystem.
	 */
	public CommandSystem()
	{
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		StringBuilder result = new StringBuilder();

		result.append( "System info: [Platform:" );
		if( Platform.isAndroid() )
		{
			result.append( "Android" );
		}
		else if( Platform.isIOS() )
		{
			result.append( "IOS" );
		}
		else if( Platform.isJVM() )
		{
			result.append( "JVM" );
		}
		else if( Platform.isWeb() )
		{
			result.append( "Web" );
		}
		else
		{
			result.append( "Unknown" );
		}

		result.append( ", Strict mode:" + ( Platform.strictMode() ? "On" : "Off" ) );
		result.append( ", Debug mode:" + ( Platform.debug() ? "On" : "Off" ) );
		result.append( "]" );

		return result.toString();
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "system";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "information about the environment settings";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "system [no required parameters]";
	}

	/**
	 * @inheritDoc
	 */
	public int getCommandMinParams()
	{
		return 0;
	}

	/**
	 * @inheritDoc
	 */
	public int getCommandMaxParams()
	{
		return 0;
	}

	/**
	 * Creates and returns a string representation of the CommandSystem object.
	 */
	@Override
	public String toString()
	{
		return "[CommandSystem]";
	}
}

