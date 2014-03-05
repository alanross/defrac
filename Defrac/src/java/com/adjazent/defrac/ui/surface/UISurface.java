package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.core.error.UnsupportedOperationError;
import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;
import defrac.display.Layer;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

import java.util.Iterator;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UISurface extends Layer implements IDisposable
{
	public String id = "";
	public Layer skinLayer;
	public Layer contentLayer;

	private MRectangle _bounds;

	private IUISkin _skin;

	public UISurface()
	{
		super();

		skinLayer = new Layer();
		contentLayer = new Layer();

		super.addChild( skinLayer );
		super.addChild( contentLayer );

		_bounds = new MRectangle( 0, 0, 0, 0 );
	}

	public UISurface( IUISkin skin )
	{
		super();

		skinLayer = new Layer();
		contentLayer = new Layer();

		super.addChild( skinLayer );
		super.addChild( contentLayer );

		_bounds = new MRectangle( 0, 0, skin.getDefaultWidth(), skin.getDefaultHeight() );

		attachSkin( skin );
	}

	private void detachSkin( IUISkin skin )
	{
		if( skin != null )
		{
			skin.detach( this );

			UISkinFactory.release( skin );
		}
	}

	private void attachSkin( IUISkin skin )
	{
		_skin = skin;
		_skin.attach( this );
		_skin.resizeTo( ( float ) _bounds.width, ( float ) _bounds.height );
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		if( _bounds.contains( local.x, local.y ) )
		{
			Iterator<DisplayObject> it = contentLayer.iterator();

			while( it.hasNext() )
			{
				DisplayObject d = it.next();

				if( d.captureEventTarget( point ) != null )
				{
					return d;
				}
			}

			return this;
		}

		return null;
	}

	public void setSkin( IUISkin skin )
	{
		detachSkin( _skin );

		_skin = null;

		attachSkin( skin );
	}

	public IUISkin getSkin()
	{
		return _skin;
	}

	public void moveTo( int x, int y )
	{
		super.moveTo( x, y );
	}

	public DisplayObject resizeTo( float width, float height )
	{
		_bounds.resizeTo( width, height );

		if( _skin != null )
		{
			_skin.resizeTo( width, height );
		}

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

	@Override
	public <D extends DisplayObject> D addChild( @javax.annotation.Nonnull D child )
	{
		return contentLayer.addChild( child );
	}

//	// for some reason overriding this will cause an stack overflow
//	@Override
//	public <D extends defrac.display.DisplayObject> D addChildAt(@javax.annotation.Nonnull D child, int index)
//	{
//		return contentLayer.addChildAt( child, index );
//	}

	@Override
	public boolean contains( @javax.annotation.Nonnull defrac.display.DisplayObject child )
	{
		return contentLayer.contains( child );
	}

	@Override
	public DisplayObject getChildAt( int index )
	{
		return contentLayer.getChildAt( index );
	}

	@Override
	public int getChildIndex( @javax.annotation.Nonnull DisplayObject child )
	{
		return contentLayer.getChildIndex( child );
	}

	@Override
	public <D extends defrac.display.DisplayObject> D removeChild( @javax.annotation.Nonnull D child )
	{
		return contentLayer.removeChild( child );
	}

	@Override
	public defrac.display.DisplayObject removeChildAt( int index )
	{
		return contentLayer.removeChildAt( index );
	}

	@Override
	public defrac.display.DisplayObjectContainer removeAllChildren()
	{
		return contentLayer.removeAllChildren();
	}

	public boolean containsPoint( float x, float y )
	{
		return _bounds.contains( x, y );
	}

	public DisplayObjectContainer getRoot()
	{
		DisplayObjectContainer o = this;

		while( o.parent() != null )
		{
			o = o.parent();
		}

		return o;
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

