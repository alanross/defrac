package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.ui.surface.skin.UISurfaceSkinFactory;
import com.adjazent.defrac.ui.texture.UITexture;
import defrac.display.DisplayObjectContainer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UISurface extends DisplayObjectContainer
{
	public String id = "";
	private IUISurfaceSkin _skin;

	public UISurface( UITexture texture )
	{
		_skin = UISurfaceSkinFactory.create( texture );
		_skin.attach( this );
	}

	public UISurface( int color )
	{
		_skin = UISurfaceSkinFactory.create( color );
		_skin.attach( this );
	}

	public void scaleToSize( int width, int height )
	{
		_skin.resizeTo( width, height );
	}

	public void dispose()
	{
		_skin.detach( this );
		_skin = null;
	}

	@Override
	public String toString()
	{
		return "[UISurface id:" + id + "]";
	}
}

