package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.ui.texture.UITexture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUISurfaceSkin
{
	void attach( UISurface surface, UITexture texture );

	void detach( UISurface surface );

	void resizeTo( int width, int height );
}