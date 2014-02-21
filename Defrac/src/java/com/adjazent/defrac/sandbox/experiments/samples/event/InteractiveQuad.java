package com.adjazent.defrac.sandbox.experiments.samples.event;

import defrac.display.Quad;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventFlag;
import defrac.display.event.UIEventType;

import javax.annotation.Nonnull;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class InteractiveQuad extends Quad
{
	public InteractiveQuad( final float width, final float height, final int color )
	{
		super( width, height, color );

		// We want to receive DRAG events -- drag events happen when
		// the user touches the screen, or presses the mouse button, and
		// moves over an object
		//
		// By default, those events are disabled. Setting the RECEIVE_DRAG_EVENTS
		// flag enables them for this display object
		eventFlags( UIEventFlag.RECEIVE_DRAG_EVENTS );
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent event )
	{
		// The processEvent method is called by the event system
		// Each event has a type that belongs to some kind of category
		// You can read more about then in the UIEventType documentation
		//
		// Events of type UIEvent.ACTION are UIActionEvent instances and so on...
		//
		// For this example, we just want to get some basic behaviour on MOUSE_OUT
		// and ACTION_DRAG_OUT
		//
		// Note: MOUSE events are only dispatched on devices with a mouse connected
		//       whereas ACTION events are also available on mobile platforms.
		//       Usually, working with MOUSE events is not necessary unless you want
		//       stuff like MOUSE_WHEEL
		switch( event.type )
		{
			case UIEventType.ACTION_DRAG_IN:
				scaleTo( 2.0f, 2.0f );
				alpha( 0.0f );
				rotation( ( float ) Math.PI * 2.0f );
				break;

			case UIEventType.MOUSE_IN:
				alpha( 0.5f );
				scaleTo( 0.5f, 0.5f );
				break;
		}
	}

	@Override
	public String toString()
	{
		return "[InteractiveQuad]";
	}
}
