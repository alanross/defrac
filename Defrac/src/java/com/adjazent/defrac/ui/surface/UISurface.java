package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.core.error.UnsupportedOperationError;
import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.surface.skin.UISurfaceSkinFactory;
import com.adjazent.defrac.ui.texture.UITexture;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UISurface extends DisplayObjectContainer implements IDisposable
{
	public String id = "";

	private MRectangle _aabb;

	private IUISkin _skin;

	public UISurface( UITexture texture )
	{
		super();

		_aabb = new MRectangle( 0, 0, ( float ) texture.getSkinRect().width, ( float ) texture.getSkinRect().height );

		setTexture( texture );
	}

	public UISurface( int color )
	{
		super();

		_aabb = new MRectangle( 0, 0, 1, 1 );

		setColor( color );
	}

	private void detachSkin( IUISkin skin )
	{
		if( skin != null )
		{
			skin.detach( this );

			UISurfaceSkinFactory.release( skin );
		}
	}

	private void attachSkin( IUISkin skin )
	{
		_skin = skin;
		_skin.attach( this );
		_skin.resizeTo( ( float ) _aabb.width, ( float ) _aabb.height );
	}

	public void setTexture( UITexture texture )
	{
		detachSkin( _skin );

		_skin = null;

		attachSkin( UISurfaceSkinFactory.create( texture ) );
	}

	public void setColor( int color )
	{
		detachSkin( _skin );

		_skin = null;

		attachSkin( UISurfaceSkinFactory.create( color ) );
	}

	public void moveTo( int x, int y )
	{
		_aabb.moveTo( x, y );

		super.moveTo( x, y );
	}

	public DisplayObject resizeTo( float width, float height )
	{
		_aabb.resizeTo( width(), height() );
		_skin.resizeTo( width(), height() );

		return this;
	}

	@Override
	public DisplayObject scaleToSize( float width, float height )
	{
		throw new UnsupportedOperationError( this );
	}

	@Override
	public DisplayObject scaleTo( float sx, float sy )
	{
		throw new UnsupportedOperationError( this );
	}

	@Override
	public DisplayObject scaleBy( float dx, float dy )
	{
		throw new UnsupportedOperationError( this );
	}

	public boolean containsPoint( float x, float y )
	{
		// TODO: globalToLocal does not work yet
		//Point p = new Point( point.x, point.y );

		return _aabb.contains( x, y );
	}

	@Override
	public void dispose()
	{
		detachSkin( _skin );

		_skin = null;
	}

	@Override
	public String toString()
	{
		return "[UISurface id:" + id + "]";
	}
}

