package com.adjazent.defrac.sandbox.samples.event;

import defrac.display.DisplayObject;
import defrac.event.Event;
import defrac.lang.Procedure;

import javax.annotation.Nonnull;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class Animator implements Procedure<Event>
{
	@Nonnull
	private final DisplayObject[] displayObjects;
	private final float inertia;

	public Animator( @Nonnull final DisplayObject[] displayObjects, final float inertia )
	{
		this.displayObjects = displayObjects;
		this.inertia = inertia;
	}

	@Override
	public void apply( @Nonnull final Event event )
	{
		for( final DisplayObject displayObject : displayObjects )
		{
			final float alpha = displayObject.alpha();
			final float rotation = displayObject.rotation();
			final float scaleX = displayObject.scaleX();
			final float scaleY = displayObject.scaleY();

			// Perform some kind of animation here ...
			//
			// Hint: Start this sample with "jvm:run" and then type "~jvm:compile"
			//       Then tweak some of those values here and you will instantly
			//       see the result in the running application
			displayObject.
					alpha( interpolate( alpha, 1.0f ) ).
					rotation( interpolate( rotation, 0.0f ) ).
					scaleX( interpolate( scaleX, 1.0f ) ).
					scaleY( interpolate( scaleY, 1.0f ) );
		}
	}

	private float interpolate( final float src, final float dst )
	{
		return src + ( dst - src ) * inertia;
	}

	@Override
	public String toString()
	{
		return "[Animator]";
	}
}
