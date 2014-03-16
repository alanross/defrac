package com.adjazent.defrac.system.terminal;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface ICommand
{
	/**
	 *
	 */
	String commandExecute( String[] args );

	/**
	 *
	 */
	String getCommandName();

	/**
	 *
	 */
	String getCommandInfo();

	/**
	 *
	 */
	String getCommandUsage();

	/**
	 *
	 */
	int getCommandMinParams();

	/**
	 *
	 */
	int getCommandMaxParams();
}

