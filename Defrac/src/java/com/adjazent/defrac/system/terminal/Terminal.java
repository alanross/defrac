package com.adjazent.defrac.system.terminal;

import com.adjazent.defrac.core.error.*;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.ds.tree.trie.TrieTree;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Terminal
{
	private static Terminal _instance;

	private Hashtable<String, ICommand> _commands;
	private ITerminalView _view;
	private LinkedList<CommandResult> _history;

	public static void initialize( ITerminalView terminalView )
	{
		if( _instance != null )
		{
			throw new SingletonError( "Terminal" );
		}

		if( terminalView == null )
		{
			throw new NullError( "terminalView can not be null." );
		}

		_instance = new Terminal( terminalView );
	}

	public static Terminal get()
	{
		return _instance;
	}

	public static void write( String output )
	{
		_instance.internalWrite( output );
	}

	public static String exec( String command )
	{
		return _instance.internalExec( command ).getOutput();
	}

	public static LinkedList<ICommand> list()
	{
		return _instance.listCommands();
	}

	public static void clear()
	{
		_instance.getView().clear();
	}

	public static ICommand find( String command )
	{
		return _instance.getCommand( command );
	}

	public static LinkedList<CommandResult> getHistory()
	{
		return _instance._history;
	}

	public static LinkedList<String> findCandidates( String command )
	{
		return _instance.internalFindCandidates( command );
	}

	public static void register( ICommand command )
	{
		_instance.addCommand( command );
	}

	public static void unregister( ICommand command )
	{
		_instance.removeCommand( command );
	}

	public static boolean isRegistered( ICommand command )
	{
		return _instance.hasCommand( command );
	}

	public Terminal( ITerminalView view )
	{
		_commands = new Hashtable<String, ICommand>();
		_history = new LinkedList<CommandResult>();

		setView( view );
	}

	private String execCommand( String command )
	{
		String[] args = StringUtils.split( command, ' ' );

		if( args.length == 0 )
		{
			return "";
		}

		String commandName = cleanCommand( args[ 0 ] );

		ICommand cmd = getCommand( commandName );

		if( cmd == null )
		{
			return "'" + commandName + "' is not a registered command.";
		}

		String[] params = new String[ args.length - 1 ];

		System.arraycopy( args, 1, params, 0, args.length - 1 );

		if( params.length < cmd.getCommandMinParams() || params.length > cmd.getCommandMaxParams() )
		{
			return "Usage: " + cmd.getCommandUsage();
		}

		return cmd.commandExecute( params );
	}

	private void addCommand( ICommand command )
	{
		if( command == null )
		{
			throw new NullError( "Command can not be null" );
		}

		if( command.getCommandName() == null || command.getCommandName().length() <= 2 )
		{
			throw new ValueError( "Can not add a command with an invalid command name:" + command.getCommandName() );
		}

		if( hasCommand( command ) )
		{
			throw new ElementAlreadyExistsError();
		}

//		if( command.getCommandName().search( /[A - Z]/g)!=-1)
//		{
//			Log.warn( Context.DEFAULT, "Adding command '" + command.getCommandName() + "'. " +
//					"it contains upper case letters. Will convert to lower case." );
//
//			// this happens in cleanCommand
//		}

		String commandName = cleanCommand( command.getCommandName() );

		_commands.put( commandName, command );
	}

	private void removeCommand( ICommand command )
	{
		if( command == null )
		{
			throw new NullError( "Command can not be null" );
		}

		if( !hasCommand( command ) )
		{
			throw new ElementDoesNotExistError();
		}

		String commandName = cleanCommand( command.getCommandName() );

		_commands.remove( commandName );
	}

	private boolean hasCommand( ICommand command )
	{
		return ( null != _commands.get( cleanCommand( command.getCommandName() ) ) );
	}

	private LinkedList<ICommand> listCommands()
	{
		LinkedList<ICommand> result = new LinkedList<ICommand>();

		Enumeration<ICommand> items = _commands.elements();

		while( items.hasMoreElements() )
		{
			result.addLast( items.nextElement() );
		}

		return result;
	}

	private LinkedList<String> internalFindCandidates( String command )
	{
		TrieTree dict = new TrieTree();

		Enumeration<ICommand> items = _commands.elements();

		while( items.hasMoreElements() )
		{
			dict.insert( items.nextElement().getCommandName() );
		}

		return dict.find( cleanCommand( command ) );
	}

	private ICommand getCommand( String command )
	{
		return _commands.get( cleanCommand( command ) );
	}

	private String cleanCommand( String command )
	{
		int index = 0;

		char[] chars = command.toCharArray();
		char[] result = new char[ command.length() ];

		for( char c : chars )
		{
			if( c >= 'a' && c <= 'z' || c == ' ' )
			{
				result[ index++ ] = c;
			}
		}

		return ( new String( result ).toLowerCase() );
	}

	private String[] commandToParams( String command )
	{
		command = StringUtils.trim( command );

		if( StringUtils.isEmpty( command ) )
		{
			return null;
		}

		String[] args = StringUtils.split( command, ' ' );

		if( args.length == 0 )
		{
			return null;
		}

		return args;
	}

	private void internalWrite( String output )
	{
		CommandResult result = new CommandResult( "write", output );

		_history.addLast( result );

		_view.write( output );
	}

	private CommandResult internalExec( String command )
	{
		CommandResult result = new CommandResult( command, execCommand( command ) );

		_history.addLast( result );

		_view.execute( result );

		return result;
	}

	public void setView( ITerminalView view )
	{
		_view = view;
	}

	public ITerminalView getView()
	{
		return _view;
	}

	@Override
	public String toString()
	{
		return "[Terminal]";
	}
}