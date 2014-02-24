package com.adjazent.defrac.ui.surface;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUISurfaceSkin
{
	void attach( UISurface surface );

	void detach( UISurface surface );

	void resizeTo( int width, int height );
}