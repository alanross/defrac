package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.system.terminal.ICommand;
import com.adjazent.defrac.system.terminal.Terminal;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandInfo implements ICommand
{
	/**
	 * Creates a new instance of CommandInfo.
	 */
	public CommandInfo()
	{
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		ICommand cmd = Terminal.find( args[0] );

		if( cmd != null )
		{
			return cmd.getCommandName() + " - " + cmd.getCommandInfo();
		}
		else
		{
			return args[ 0 ] + " is not a registered command";
		}
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "info";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "information about given command.";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "info [command]";
	}

	/**
	 * @inheritDoc
	 */
	public int getCommandMinParams()
	{
		return 1;
	}

	/**
	 * @inheritDoc
	 */
	public int getCommandMaxParams()
	{
		return 1;
	}

	/**
	 * Creates and returns a string representation of the CommandInfo object.
	 */
	@Override
	public String toString()
	{
		return "[CommandInfo]";
	}
}

