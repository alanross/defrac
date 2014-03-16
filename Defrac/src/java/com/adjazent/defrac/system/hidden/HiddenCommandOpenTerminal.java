package com.adjazent.defrac.system.hidden;

import com.adjazent.defrac.system.terminal.Terminal;

public final class HiddenCommandOpenTerminal implements IHiddenCommand
{
	public HiddenCommandOpenTerminal()
	{
	}

	public String getCodeWord()
	{
		return "DEBUG";
	}

	public void execute()
	{
		Terminal.exec( "terminal show" );
	}

	@Override
	public String toString()
	{
		return "[HiddenActionOpenTerminal]";
	}
}

