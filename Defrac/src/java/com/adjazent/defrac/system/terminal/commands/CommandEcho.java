package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.core.utils.ArrayUtils;
import com.adjazent.defrac.system.terminal.ICommand;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandEcho implements ICommand
{
	/**
	 * Creates a new instance of CommandEcho.
	 */
	public CommandEcho()
	{
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		return ArrayUtils.join( args, " " );
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "echo";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "write arguments to the terminal output";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "echo [string ...]";
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
		return Integer.MAX_VALUE;
	}

	/**
	 * Creates and returns a string representation of the CommandEcho object.
	 */
	@Override
	public String toString()
	{
		return "[CommandWrite]";
	}
}

