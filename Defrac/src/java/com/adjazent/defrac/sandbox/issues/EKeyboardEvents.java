package com.adjazent.defrac.sandbox.issues;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.lang.Procedure;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EKeyboardEvents extends Experiment
{
	public EKeyboardEvents()
	{

	}

	@Override
	protected void onInit()
	{
		// charCode is null in web mode (safari)

		Events.onKeyDown.attach( new Procedure<KeyboardEvent>()
		{
			@Override
			public void apply( KeyboardEvent event )
			{
				System.out.println( "onKeyDown charCode:" + event.charCode + ", keyCode:" + event.keyCode );
			}
		} );

		Events.onKeyUp.attach( new Procedure<KeyboardEvent>()
		{
			@Override
			public void apply( KeyboardEvent event )
			{
				System.out.println( "onKeyUp charCode:" + event.charCode + ", keyCode:" + event.keyCode );
			}
		} );
	}

	@Override
	public String toString()
	{
		return "[EKeyboardEvents]";
	}
}