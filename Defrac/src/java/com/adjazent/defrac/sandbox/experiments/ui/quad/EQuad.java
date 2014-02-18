package com.adjazent.defrac.sandbox.experiments.ui.quad;

import com.adjazent.defrac.sandbox.experiments.Experiment;
import defrac.display.DisplayObject;
import defrac.display.Quad;
import defrac.event.EnterFrameEvent;
import defrac.event.Events;
import defrac.lang.Procedure;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class EQuad extends Experiment
{
	private final Procedure<EnterFrameEvent> onEnterFrameProcedure =
			new Procedure<EnterFrameEvent>()
			{
				@Override
				public void apply( EnterFrameEvent event )
				{
					onEnterFrame();
				}
			};

	public EQuad()
	{
	}

	@Override
	protected void onInit()
	{
		for( final Quad quad : createQuads() )
		{
			quad.centerRegistrationPoint();
			quad.moveBy( 50, 50 );
			addChild( quad );
		}

		moveTo( 100, 100 );
		centerRegistrationPoint().moveBy( 100, 100 );

		Events.onEnterFrame.attach( onEnterFrameProcedure );
	}

	private void onEnterFrame()
	{
		rotateBy( -0.01f );

		for( final DisplayObject child : this )
		{
			child.rotateBy( 0.05f );
		}
	}

	private static Quad[] createQuads()
	{
		final Quad[] quads = {
				new Quad( 100, 100, 0xFFFF0000 ), //aarrggbb
				new Quad( 100, 100, 0xFF00FF00 ),
				new Quad( 100, 100, 0xFF0000FF ),
				new Quad( 100, 100, 0xFFFF00FF ),
		};

		quads[ 0 ].moveTo( 0, 0 );
		quads[ 1 ].moveTo( 100, 0 );
		quads[ 2 ].moveTo( 0, 100 );
		quads[ 3 ].moveTo( 100, 100 );

		return quads;
	}

	@Override
	public String toString()
	{
		return "[EQuad]";
	}
}
