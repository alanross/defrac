package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.core.utils.ArrayUtils;
import com.adjazent.defrac.system.terminal.ICommand;
import com.adjazent.defrac.system.terminal.Terminal;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandList implements ICommand
{
	/**
	 * Creates a new instance of CommandList.
	 */
	public CommandList()
	{
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		LinkedList<ICommand> commands = Terminal.list();

		String[] result = new String[ commands.size() ];

		final int n = commands.size();

		for( int i = 0; i < n; ++i )
		{
			result[ i ] = commands.get( i ).getCommandName();
		}

		Arrays.sort( result );

		return ArrayUtils.join( result, "\t" );
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "list";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "list all registered commands";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "list [no required parameters]";
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
	 * Creates and returns a string representation of the CommandList object.
	 */
	@Override
	public String toString()
	{
		return "[CommandList]";
	}
}

