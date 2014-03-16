package com.adjazent.defrac.system.terminal.commands;

import com.adjazent.defrac.system.terminal.ICommand;
import com.adjazent.defrac.system.terminal.TerminalView;
import defrac.display.Stage;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class CommandShow implements ICommand
{
	private Stage _stage;

	private TerminalView _view;

	/**
	 * Creates a new instance of CommandTerminalOpen.
	 */
	public CommandShow( Stage stage, TerminalView view )
	{
		_stage = stage;

		_view = view;
	}

	/**
	 * @inheritDoc
	 */
	public String commandExecute( String[] args )
	{
		String visibility = args[ 0 ];

		if( visibility.toLowerCase().equals( "show" ) )
		{
			if( _view.parent() == null )
			{
				_stage.addChild( _view );
			}

			onResize();

			return "terminal showing.";
		}
		else if( visibility.toLowerCase().equals( "hide" ) )
		{
			if( _view.parent() != null )
			{
				_stage.removeChild( _view );
			}

			return "terminal hidden.";
		}

		return "command \"" + getCommandName() + "\" does not understand: " + visibility;
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandName()
	{
		return "terminal";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandInfo()
	{
		return "open or close the terminal view";
	}

	/**
	 * @inheritDoc
	 */
	public String getCommandUsage()
	{
		return "terminal [show|hide]";
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

	private void onResize()
	{
		_view.resizeTo( _stage.width(), _stage.height() * 0.5f );
	}

	public Boolean isShowing()
	{
		return ( _view.parent() != null );
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String toString()
	{
		return "[CommandShow]";
	}
}