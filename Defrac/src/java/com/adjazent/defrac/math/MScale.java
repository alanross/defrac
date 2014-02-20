package com.adjazent.defrac.math;

import com.adjazent.defrac.core.error.ValueError;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MScale
{
	public static final int TYPE_FILL = 0;
	public static final int TYPE_FIT = 1;
	public static final int TYPE_STRETCH = 2;

	/**
	 * Returns the scale, whereas the scale.x is the horizontal scale and scale.y the vertical scale.
	 */
	public static MPoint getScale( double sw, double sh, double dw, double dh, int scaleType )
	{
		if( scaleType == TYPE_FILL )
		{
			double ratioFill = Math.max( dw / sw, dh / sh );

			return new MPoint( ratioFill, ratioFill );
		}
		else if( scaleType == TYPE_FIT )
		{
			double ratioFit = Math.min( dw / sw, dh / sh );

			return new MPoint( ratioFit, ratioFit );
		}
		else if( scaleType == TYPE_STRETCH )
		{
			return new MPoint( ( dw / sw ), ( dh / sh ) );
		}

		throw new ValueError( "MScale type is unknown." );
	}

	/**
	 * Returns a new rectangle holding the new width and height and a centered x/y relative to
	 * the given dest x/y.
	 */
	public static MRectangle getRect( MRectangle source, MRectangle dest, int scaleType )
	{
		final MRectangle result = new MRectangle();

		if( scaleType == TYPE_FILL )
		{
			double ratioFill = Math.max( dest.width / source.width, dest.height / source.height );

			result.width = ( ratioFill * source.width );
			result.height = ( ratioFill * source.height );
		}
		else if( scaleType == TYPE_FIT )
		{
			double ratioFit = Math.min( dest.width / source.width, dest.height / source.height );

			result.width = ( ratioFit * source.width );
			result.height = ( ratioFit * source.height );
		}
		else if( scaleType == TYPE_STRETCH )
		{
			result.width = ( ( dest.width / source.width ) * source.width );
			result.height = ( ( dest.height / source.height ) * source.height );
		}
		else
		{
			throw new ValueError( "MScale type is unknown." );
		}

		result.x = ( dest.x + ( dest.width - result.width ) * 0.5 );
		result.y = ( dest.y + ( dest.height - result.height ) * 0.5 );

		return result;
	}

	/**
	 * Creates a new instance of MScale.
	 */
	private MScale()
	{
	}

	/**
	 * Generates and returns the string representation of the MScale object.
	 */
	@Override
	public String toString()
	{
		return "[MScale]";
	}
}

