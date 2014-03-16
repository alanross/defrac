package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.system.terminal.ICommand;
import com.adjazent.defrac.system.terminal.Terminal;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandHelp implements ICommand
{
	/**
	 * Creates a new instance of CommandHelp.
	 */
	public CommandHelp()
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
			return cmd.getCommandName() + " - " + cmd.getCommandUsage();
		}
		else
		{
			return "\"" + args[0] + "\" is not a registered command";
		}
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "help";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "usage information about the requested command";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "help [command]";
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
	 * Creates and returns a string representation of the CommandHelp object.
	 */
	@Override
	public String toString()
	{
		return "[CommandUsage]";
	}
}

