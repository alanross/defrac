package com.adjazent.defrac.sandbox.experiments.system;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.system.terminal.Terminal;
import com.adjazent.defrac.system.terminal.TerminalView;
import com.adjazent.defrac.system.terminal.commands.*;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFontManager;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class ETerminal extends Experiment implements IUIResourceLoaderQueueObserver
{
	public ETerminal()
	{

	}

	@Override
	protected void onInit()
	{
		UIFontManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica11.png", "fonts/helvetica11.fnt", "helvetica" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		TerminalView view = new TerminalView( new UITextFormat( "helvetica" ) );

		Terminal.initialize( view );
		Terminal.register( new CommandClear() );
		Terminal.register( new CommandEcho() );
		Terminal.register( new CommandError() );
		Terminal.register( new CommandHelp() );
		Terminal.register( new CommandInfo() );
		Terminal.register( new CommandList() );
		Terminal.register( new CommandShow( stage, view ) );
		Terminal.register( new CommandSystem() );

		Log.info( Context.DEFAULT, this, "CommandShow:", Terminal.exec( "terminal show" ) );
		Log.info( Context.DEFAULT, this, "CommandList:", Terminal.exec( "list" ) );
		Log.info( Context.DEFAULT, this, "CommandInfo:", Terminal.exec( "info list" ) );
		Log.info( Context.DEFAULT, this, "CommandHelp:", Terminal.exec( "help list" ) );
		Log.info( Context.DEFAULT, this, "CommandEcho:", Terminal.exec( "echo hello world" ) );
		Log.info( Context.DEFAULT, this, "CommandSystem:", Terminal.exec( "system" ) );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public String toString()
	{
		return "[ETerminal]";
	}
}