package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.math.geom.MRectangle;
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

	protected MRectangle bounds;

	private IUISurfaceSkin _skin;

	public UISurface( UITexture texture )
	{
		bounds = new MRectangle();

		setTexture( texture );
	}

	public UISurface( int color )
	{
		setColor( color );
	}

	public void scaleToSize( int width, int height )
	{
		bounds.resizeTo( width, height );

		_skin.resizeTo( width, height );
	}

	public void moveTo( int x, int y )
	{
		bounds.moveTo( x, y );

		super.moveTo( x, y );
	}

	public void setTexture( UITexture texture )
	{
		if( _skin != null )
		{
			_skin.detach( this );
		}

		_skin = UISurfaceSkinFactory.create( texture );
		_skin.attach( this );
		scaleToSize( (int)texture.getSkinRect().width, (int)texture.getSkinRect().height );
		//_skin.resizeTo( ( int ) width(), ( int ) height() );
	}

	public void setColor( int color )
	{
		if( _skin != null )
		{
			_skin.detach( this );
		}

		_skin = UISurfaceSkinFactory.create( color );
		_skin.attach( this );
		//_skin.resizeTo( ( int ) width(), ( int ) height() );
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

