package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.system.terminal.ICommand;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandError implements ICommand
{
	/**
	 * Creates a new instance of CommandError.
	 */
	public CommandError()
	{
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		throw new GenericError( "This error was triggered by user via Terminal." );
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "error";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "error the terminal output";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "error [no required parameters]";
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
	 * Creates and returns a string representation of the CommandError object.
	 */
	@Override
	public String toString()
	{
		return "[CommandError]";
	}
}

