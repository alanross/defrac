package com.adjazent.defrac.system.terminal;

import com.adjazent.defrac.core.utils.IDisposable;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface ITerminalView extends IDisposable
{
	/**
	 * Display the given result.
	 */
	void execute( CommandResult result );

	/**
	 * Write directly to the terminal output view.
	 */
	void write( String output );

	/**
	 * Clear the terminal output view.
	 */
	void clear();
}