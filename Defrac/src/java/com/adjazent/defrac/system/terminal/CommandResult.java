package com.adjazent.defrac.system.terminal;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandResult
{
	private String _command;

	private String _output;

	public CommandResult( String command, String result )
	{
		_command = command;
		_output = result;
	}

	public String getCommand()
	{
		return _command;
	}

	public String getOutput()
	{
		return _output;
	}

	@Override
	public String toString()
	{
		return "[CommandResult command:" + _command + " output:" + _output + "]";
	}
}

