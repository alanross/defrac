package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.Quad;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIColorSkin implements IUISkin
{
	private Quad _quad = new Quad( 1, 1, 0 );

	public UIColorSkin()
	{
	}

	public void init( int color )
	{
		_quad.color( color );
	}

	@Override
	public void attach( UISurface surface )
	{
		surface.skinLayer.addChild( _quad );
	}

	@Override
	public void detach( UISurface surface )
	{
		surface.skinLayer.removeChild( _quad );
	}

	@Override
	public void resizeTo( float width, float height )
	{
		_quad.scaleToSize( width, height );
	}

	@Override
	public float getDefaultWidth()
	{
		return 1;
	}

	@Override
	public float getDefaultHeight()
	{
		return 1;
	}

	@Override
	public String toString()
	{
		return "[UIColorSkin]";
	}
}