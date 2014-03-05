package com.adjazent.defrac.ui.surface;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUISkin
{
	void attach( UISurface surface );

	void detach( UISurface surface );

	void resizeTo( float width, float height );

	float getDefaultWidth();

	float getDefaultHeight();

	IUISkin clone();
}