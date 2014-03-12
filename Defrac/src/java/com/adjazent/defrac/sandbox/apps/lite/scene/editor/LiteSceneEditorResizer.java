package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import defrac.display.Layer;
import defrac.display.event.UIEventTarget;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneEditorResizer
{
	public final UISurface tl;
	public final UISurface tc;
	public final UISurface tr;

	public final UISurface ml;
	public final UISurface mr;

	public final UISurface bl;
	public final UISurface bc;
	public final UISurface br;

	private final float size = 6.0f;
	private final UISurface[] _handles = new UISurface[ 8 ];

	public LiteSceneEditorResizer( Layer container )
	{
		_handles[ 0 ] = tl = create( "TL", container );
		_handles[ 1 ] = tc = create( "TC", container );
		_handles[ 2 ] = tr = create( "TR", container );

		_handles[ 3 ] = ml = create( "ML", container );
		_handles[ 4 ] = mr = create( "MR", container );

		_handles[ 5 ] = bl = create( "BL", container );
		_handles[ 6 ] = bc = create( "BC", container );
		_handles[ 7 ] = br = create( "BR", container );
	}

	private UISurface create( String handleID, Layer container )
	{
		IUISkin skin = UISkinFactory.create( 0x77FFFFFF );

		UISurface handle = new UISurface( skin );
		handle.id = handleID;
		handle.resizeTo( size, size );

		container.addChild( handle );

		return handle;
	}

	public void setPosition( LiteSceneEditorElement element )
	{
		float s = size * 0.5f;

		float l = element.x() - s;
		float c = l + element.width() * 0.5f;
		float r = l + element.width();

		float t = element.y() - s;
		float b = t + element.height();
		float m = t + element.height() * 0.5f;

		tl.moveTo( l, t );
		tc.moveTo( c, t );
		tr.moveTo( r, t );
		ml.moveTo( l, m );
		mr.moveTo( r, m );
		bl.moveTo( l, b );
		bc.moveTo( c, b );
		br.moveTo( r, b );
	}

	public Rectangle calculateDimensions( UISurface handle, Rectangle orig, float dx, float dy )
	{
		Rectangle result = new Rectangle( orig.x, orig.y, orig.width, orig.height );

		if( handle == null )
		{
			return result;
		}

		if( handle == tl )
		{
			result.x += dx;
			result.y += dy;
			result.width -= dx;
			result.height -= dy;
		}
		else if( handle == tc )
		{
			result.y += dy;
			result.height -= dy;
		}
		else if( handle == tr )
		{
			result.y += dy;
			result.width += dx;
			result.height -= dy;
		}
		else if( handle == ml )
		{
			result.x += dx;
			result.width -= dx;
		}
		else if( handle == mr )
		{
			result.width += dx;
		}
		else if( handle == bl )
		{
			result.x += dx;
			result.width -= dx;
			result.height += dy;
		}
		else if( handle == bc )
		{
			result.height += dy;
		}
		else if( handle == br )
		{
			result.width += dx;
			result.height += dy;
		}

		return result;
	}

	public UISurface getHandle( UIEventTarget target )
	{
		for( UISurface handle : _handles )
		{
			if( handle == target )
			{
				return handle;
			}
		}

		return null;
	}

	@Override
	public String toString()
	{
		return "[LiteSceneEditorResizer]";
	}
}