package com.adjazent.defrac.sandbox;

import com.adjazent.defrac.core.stage.StageProvider;
import com.adjazent.defrac.sandbox.events.*;
import defrac.app.GenericApp;
import defrac.display.Layer;
import defrac.display.Stage;
import defrac.event.*;
import defrac.lang.Procedure;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class Experiment extends Layer implements IAppResize
{
	protected Stage stage;
	protected GenericApp app;

	public Experiment()
	{
	}

	public final void init( Stage stage, GenericApp app )
	{
		this.stage = stage;
		this.app = app;

		onInit();

		attachToEventListeners( this );
	}

	private void attachToEventListeners( Object obj )
	{
		if( obj instanceof IAppResize )
		{
			StageProvider.stage.onResize.attach( new Procedure<StageEvent.Resize>()
			{
				@Override
				public void apply( StageEvent.Resize event )
				{
					onResize( StageProvider.stage.width(), StageProvider.stage.height() );
				}
			} );
		}
		if( obj instanceof IRender )
		{
			StageProvider.stage.onRender.attach( new Procedure<StageEvent.Render>()
			{
				@Override
				public void apply( StageEvent.Render event )
				{
					onRender();
				}
			} );
		}
		if( obj instanceof IEnterFrame )
		{
			Events.onEnterFrame.attach( new Procedure<EnterFrameEvent>()
			{
				@Override
				public void apply( EnterFrameEvent event )
				{
					onEnterFrame();
				}
			} );
		}
		if( obj instanceof IExitFrame )
		{
			Events.onExitFrame.attach( new Procedure<ExitFrameEvent>()
			{
				@Override
				public void apply( ExitFrameEvent event )
				{
					onExitFrame();
				}
			} );
		}
		if( obj instanceof IKeyboard )
		{
			Events.onKeyDown.attach( new Procedure<KeyboardEvent>()
			{
				@Override
				public void apply( KeyboardEvent event )
				{
					onKeyDown( event );
				}
			} );

			Events.onKeyUp.attach( new Procedure<KeyboardEvent>()
			{
				@Override
				public void apply( KeyboardEvent event )
				{
					onKeyUp( event );
				}
			} );
		}
	}

	protected void onInit()
	{
	}

	public void onResize( float width, float height )
	{
	}

	public void onEnterFrame()
	{
	}

	public void onExitFrame()
	{
	}

	public void onRender()
	{
	}

	public void onKeyDown( KeyboardEvent event )
	{
	}

	public void onKeyUp( KeyboardEvent event )
	{
	}

	@Override
	public String toString()
	{
		return "[Experiment]";
	}
}