package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISurfaceSkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;
import defrac.display.Image;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISurfaceTextureSkin implements IUISurfaceSkin
{
	private Image _image = new Image();

	public UISurfaceTextureSkin()
	{
	}

	public void init( UITexture texture )
	{
		_image.texture( texture.getTexture() );
	}

	@Override
	public void attach( UISurface surface )
	{
		surface.addChild( _image );
	}

	@Override
	public void detach( UISurface surface )
	{
		surface.removeChild( _image );
	}

	@Override
	public void resizeTo( int width, int height )
	{
		_image.scaleToSize( width, height );
	}

	@Override
	public String toString()
	{
		return "[UISurfaceTextureSkin]";
	}
}