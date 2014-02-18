package com.adjazent.defrac.ui.utils.bitmap;

import com.adjazent.defrac.core.error.NullError;
import com.adjazent.defrac.core.error.ValueError;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UISlice9Grid
{
	public int left;
	public int right;
	public int top;
	public int bottom;

	public static Rectangle[] createSlices( Rectangle rect, UISlice9Grid sliceGrid )
	{
		if( null == rect )
		{
			throw new NullError( "UISkin9", "rect rect can not be null" );
		}

		final int left = sliceGrid.left;
		final int right = sliceGrid.right;
		final int top = sliceGrid.top;
		final int bottom = sliceGrid.bottom;

		if( left < 0 || right < 0 || top < 0 || bottom < 0 )
		{
			throw new ValueError( "UISkin9", "Slice value can not be smaller than zero. " +
					"Left:" + left + ", Right:" + right + ", Top:" + top + ", Bottom:" + bottom );
		}

		if( rect.width < ( left + right ) )
		{
			throw new ValueError( "UISkin9", "Source width is smaller than combined left+right slice width. " +
					"Source Width:" + rect.width + ", Left:" + left + ", Right:" + right );
		}

		if( rect.height < ( top + bottom ) )
		{
			throw new ValueError( "UISkin9", "Source height is smaller than combined top+bottom slice height. " +
					"Source Height:" + rect.height + ", Top:" + top + ", Bottom:" + bottom );
		}

		final int offsetRight = ( int ) ( rect.width - right );
		final int offsetBottom = ( int ) ( rect.height - bottom );

		final int middleCenterWidth = ( int ) ( rect.width - ( left + right ) );
		final int middleCenterHeight = ( int ) ( rect.height - ( top + bottom ) );

		final int offsetX = ( int ) rect.x;
		final int offsetY = ( int ) rect.y;

		Rectangle[] slices = new Rectangle[ 9 ];

		//tl
		slices[ 0 ] = new Rectangle( offsetX, offsetY, left, top );
		//tc
		slices[ 1 ] = new Rectangle( offsetX + left, offsetY, middleCenterWidth, top );
		//tr
		slices[ 2 ] = new Rectangle( offsetX + offsetRight, offsetY, right, top );

		//ml
		slices[ 3 ] = new Rectangle( offsetX, offsetY + top, left, middleCenterHeight );
		//mc
		slices[ 4 ] = new Rectangle( offsetX + left, offsetY + top, middleCenterWidth, middleCenterHeight );
		//mr
		slices[ 5 ] = new Rectangle( offsetX + offsetRight, offsetY + top, right, middleCenterHeight );

		//bl
		slices[ 6 ] = new Rectangle( offsetX, offsetY + offsetBottom, left, bottom );
		//bc
		slices[ 7 ] = new Rectangle( offsetX + left, offsetY + offsetBottom, middleCenterWidth, bottom );
		//br
		slices[ 8 ] = new Rectangle( offsetX + offsetRight, offsetY + offsetBottom, right, bottom );

		return slices;
	}

	/**
	 * Creates a new instance of Slice9Grid.
	 */
	public UISlice9Grid( int left, int right, int top, int bottom )
	{
		all( left, right, top, bottom );
	}

	/**
	 *
	 */
	public void all( int left, int right, int top, int bottom )
	{
		this.top = ( 0 < top ) ? top : 0;
		this.right = ( 0 < right ) ? right : 0;
		this.bottom = ( 0 < bottom ) ? bottom : 0;
		this.left = ( 0 < left ) ? left : 0;
	}

	/**
	 *
	 */
	public void leftright( int left, int right )
	{
		this.left = ( 0 > left ) ? left : 0;
		this.right = ( 0 > right ) ? right : 0;
	}

	/**
	 *
	 */
	public void topbottom( int top, int bottom )
	{
		this.top = ( 0 > top ) ? top : 0;
		this.bottom = ( 0 > bottom ) ? bottom : 0;
	}

	/**
	 *
	 */
	public void clear()
	{
		this.top = 0;
		this.right = 0;
		this.bottom = 0;
		this.left = 0;
	}

	/**
	 *
	 */
	public UISlice9Grid clone()
	{
		return new UISlice9Grid( left, right, top, bottom );
	}

	/**
	 * Creates and returns a string representation of the Slice9Grid object.
	 */
	@Override
	public String toString()
	{
		return "[UISlice9Grid left:" + left + ", right:" + right + ", top:" + top + ", bottom:" + bottom + "]";
	}
}

