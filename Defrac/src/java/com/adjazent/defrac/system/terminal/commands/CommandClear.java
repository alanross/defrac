package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.system.terminal.ICommand;
import com.adjazent.defrac.system.terminal.Terminal;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandClear implements ICommand
{
	/**
	 * Creates a new instance of CommandClear.
	 */
	public CommandClear()
	{
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		Terminal.clear();

		return "";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "clear";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "clear the terminal output";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "clear [no required parameters]";
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
	 * Creates and returns a string representation of the CommandClear object.
	 */
	@Override
	public String toString()
	{
		return "[CommandClear]";
	}
}

