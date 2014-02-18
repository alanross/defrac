package com.adjazent.defrac.ui.utils;

import com.adjazent.defrac.core.error.ValueError;
import defrac.geom.Point;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIScale
{
	public static final int TYPE_FILL = 0;
	public static final int TYPE_FIT = 1;
	public static final int TYPE_STRETCH = 2;

	/**
	 * Returns the scale, whereas the scale.x is the horizontal scale and scale.y the vertical scale.
	 */
	public static Point getScale( double sw, double sh, double dw, double dh, int scaleType )
	{
		final Point result = new Point();

		if( scaleType == TYPE_FILL )
		{
			float ratioFill = ( float ) Math.max( dw / sw, dh / sh );

			result.set( ratioFill, ratioFill );
		}
		else if( scaleType == TYPE_FIT )
		{
			float ratioFit = ( float ) Math.min( dw / sw, dh / sh );

			result.set( ratioFit, ratioFit );
		}
		else if( scaleType == TYPE_STRETCH )
		{
			result.set( ( float ) ( dw / sw ), ( float ) ( dh / sh ) );
		}
		else
		{
			throw new ValueError( "UIScale", "Scale type is unknown." );
		}

		return result;
	}

	/**
	 * Returns a new rectangle holding the new width and height and a centered x/y relative to
	 * the given dest x/y.
	 */
	public static Rectangle getRect( Rectangle source, Rectangle dest, int scaleType )
	{
		final Rectangle result = new Rectangle();

		if( scaleType == TYPE_FILL )
		{
			double ratioFill = Math.max( dest.width / source.width, dest.height / source.height );

//			result.width = ratioFill * source.width;
//			result.height = ratioFill * source.height;
		}
		else if( scaleType == TYPE_FIT )
		{
			double ratioFit = Math.min( dest.width / source.width, dest.height / source.height );

//			result.width = ratioFit * source.width;
//			result.height = ratioFit * source.height;
		}
		else if( scaleType == TYPE_STRETCH )
		{
//			result.width = ( dest.width / source.width ) * source.width;
//			result.height = ( dest.height / source.height ) * source.height;
		}
		else
		{
			throw new ValueError( "UIScale", "Scale type is unknown." );
		}

//		result.x = dest.x + ( dest.width - result.width ) * 0.5;
//		result.y = dest.y + ( dest.height - result.height ) * 0.5;

		return result;
	}

	/**
	 * Creates a new instance of UIScale.
	 */
	private UIScale()
	{
	}

	/**
	 * Generates and returns the string representation of the UIScale object.
	 */
	@Override
	public String toString()
	{
		return "[UIScale]";
	}
}

