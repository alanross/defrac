package com.adjazent.defrac.sandbox.events;

import defrac.event.KeyboardEvent;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IKeyboard
{
	void onKeyDown( KeyboardEvent event );

	void onKeyUp( KeyboardEvent event );
}