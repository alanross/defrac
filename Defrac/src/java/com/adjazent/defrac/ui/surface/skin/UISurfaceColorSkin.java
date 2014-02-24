package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISurfaceSkin;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.Quad;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISurfaceColorSkin implements IUISurfaceSkin
{
	private Quad _quad = new Quad( 1, 1, 0 );

	public UISurfaceColorSkin()
	{
	}

	public void init( int color )
	{
		_quad.color( color );
	}

	@Override
	public void attach( UISurface surface )
	{
		surface.addChild( _quad );
	}

	@Override
	public void detach( UISurface surface )
	{
		surface.removeChild( _quad );
	}

	@Override
	public void resizeTo( int width, int height )
	{
		_quad.scaleToSize( width, height );
	}

	@Override
	public String toString()
	{
		return "[UISurfaceColorSkin]";
	}
}