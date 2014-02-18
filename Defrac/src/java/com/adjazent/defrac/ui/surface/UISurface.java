package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.ui.surface.skin.UISurfaceSkinFactory;
import com.adjazent.defrac.ui.texture.UITexture;
import defrac.display.Layer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UISurface extends Layer
{
	private String _id = "";
	private IUISurfaceSkin _skin;

	public UISurface( UITexture texture )
	{
		_skin = UISurfaceSkinFactory.create( this, texture );
		_skin.attach( this, texture );
	}

	public void resizeTo( int width, int height )
	{
		_skin.resizeTo( width, height );
	}

	public void dispose()
	{
		_skin.detach( this );
		_skin = null;
	}

	public final void setId( String value )
	{
		if( _id != value )
		{
			_id = value;
		}
	}

	public final String getId()
	{
		return _id;
	}

	@Override
	public String toString()
	{
		return "[UISurface  id" + getId() + "]";
	}
}

