package com.adjazent.defrac.sandbox.experiments;

import defrac.app.GenericApp;
import defrac.display.Layer;
import defrac.display.Stage;
import defrac.event.StageEvent;
import defrac.lang.Procedure;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class Experiment extends Layer
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

		this.stage.onResize.attach( new Procedure<StageEvent.Resize>()
			{
				@Override
				public void apply( StageEvent.Resize event )
				{
					resize();
				}
			} );

		onInit();
	}

	public final void resize()
	{
		System.out.print( stage.width() + " x " + stage.height() );

		onResize( stage.width(), stage.height() );
	}

	protected void onInit()
	{
	}

	protected void onResize( float width, float height )
	{
	}

	@Override
	public String toString()
	{
		return "[Experiment]";
	}
}