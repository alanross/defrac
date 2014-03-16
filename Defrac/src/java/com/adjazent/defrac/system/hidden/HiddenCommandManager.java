package com.adjazent.defrac.system.hidden;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.core.utils.IDisposable;
import defrac.display.Stage;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.lang.Procedure;

import java.util.LinkedList;

public final class HiddenCommandManager implements IDisposable
{
	private static HiddenCommandManager _instance;

	private String _input;

	private boolean _listen = false;

	private LinkedList<IHiddenCommand> _commands;


	public static void initialize( Stage stage )
	{
		if( _instance != null )
		{
			throw new SingletonError( "HiddenCommandManager" );
		}

		_instance = new HiddenCommandManager( stage );
	}

	/**
	 * Register an command to be triggered when its code word is entered
	 */
	public static void register( IHiddenCommand command )
	{
		_instance.addCommand( command );
	}

	/**
	 * Unregister command from being triggered when its code word is entered
	 */
	public static void unregister( IHiddenCommand command )
	{
		_instance.removeCommand( command );
	}

	/**
	 * Creates a new instance of HiddenCommandManager.
	 */
	public HiddenCommandManager( Stage stage )
	{
		_commands = new LinkedList<IHiddenCommand>();

		Events.onKeyDown.attach( new Procedure<KeyboardEvent>()
		{
			@Override
			public void apply( KeyboardEvent event )
			{
				onKeyDown( event );
			}
		} );
	}

	/**
	 * @private
	 */
	public void addCommand( IHiddenCommand command )
	{
		if( hasCommand( command ) )
		{
			throw new ElementAlreadyExistsError();
		}

		_commands.addFirst( command );
	}

	/**
	 * @private
	 */
	public void removeCommand( IHiddenCommand command )
	{
		if( !hasCommand( command ) )
		{
			throw new ElementDoesNotExistError();
		}

		_commands.remove( command );
	}

	/**
	 * @inheritDoc
	 */
	public void dispose()
	{
		_commands.clear();
		_commands = null;
	}

	/**
	 * Verify if command or code word already is registered.
	 */
	private boolean hasCommand( IHiddenCommand command )
	{
		final int index = _commands.indexOf( command );

		return ( -1 != index && null == getCommandByCodeWord( command.getCodeWord() ) );
	}

	/**
	 * Get the command associated with the code word. Will Return null
	 * if no registered command exists.
	 */
	private IHiddenCommand getCommandByCodeWord( String codeWord )
	{
		int n = _commands.size();
		IHiddenCommand a;

		while( --n > -1 )
		{
			a = _commands.get( n );

			if( a.getCodeWord().toLowerCase().equals( codeWord.toLowerCase() ) )
			{
				return a;
			}
		}

		return null;
	}

	/**
	 *
	 */
	private void onKeyDown( KeyboardEvent event )
	{
		if( !_listen )
		{
			//start the listening sequence
//			if( event.keyCode == Keyboard.ESCAPE )
//			{
//				_listen = true;
//				_input = "";
//			}
		}
		else
		{
			//complete the listening sequence
//			if( event.keyCode == Keyboard.ENTER )
//			{
//				IHiddenCommand a = getCommandByCodeWord( _input );
//
//				if( a != null )
//				{
//					a.execute();
//				}
//
//				_listen = false;
//			}
//			else
//			{
//				_input += String.fromCharCode( event.charCode );
//			}
		}
	}

	/**
	 * Generates and returns the string representation of the HiddenCommandManager object.
	 */
	@Override
	public String toString()
	{
		return "[HiddenCommandManager]";
	}
}