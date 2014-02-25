package com.adjazent.defrac.math.geom;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MRectComplex implements IMShape
{
	public final MPoint bl = new MPoint();
	public final MPoint tl = new MPoint();
	public final MPoint tr = new MPoint();
	public final MPoint br = new MPoint();

	private MPoint[] _points;
	private MRectangle _bounds;

	private double _x;
	private double _y;
	private double _width;
	private double _height;

	private boolean _dirtyBounds;

	/**
	 * Creates a new instance of MRectComplex.
	 */
	public MRectComplex( double x, double y, double width, double height )
	{
		_points = new MPoint[ 4 ];
		_points[ 0 ] = bl;
		_points[ 1 ] = tl;
		_points[ 2 ] = tr;
		_points[ 3 ] = br;

		_bounds = new MRectangle();

		setDimensions( x, y, width, height );
	}

	/**
	 * Creates a new instance of MRectComplex.
	 */
	public MRectComplex()
	{
		_points = new MPoint[ 4 ];
		_points[ 0 ] = bl;
		_points[ 1 ] = tl;
		_points[ 2 ] = tr;
		_points[ 3 ] = br;

		_bounds = new MRectangle();

		setDimensions( 0.0, 0.0, 0.0, 0.0 );
	}

	/**
	 * Reset.
	 */
	public void clear()
	{
		_x = 0;
		_y = 0;
		_width = 0;
		_height = 0;

		bl.x = 0.0;
		bl.y = 0.0;
		tl.x = 0.0;
		tl.y = 0.0;
		tr.x = 0.0;
		tr.y = 0.0;
		br.x = 0.0;
		br.y = 0.0;

		_dirtyBounds = true;
	}

	/**
	 * Define the rectangle by its coordinate and size.
	 */
	public void setDimensions( double x, double y, double width, double height )
	{
		if( width < 0 )
		{
			width = 0;
		}

		if( height < 0 )
		{
			height = 0;
		}

		_x = x;
		_y = y;
		_width = width;
		_height = height;

		bl.x = x;
		bl.y = y + height;

		tl.x = x;
		tl.y = y;

		tr.x = x + width;
		tr.y = y;

		br.x = x + width;
		br.y = y + height;

		_dirtyBounds = true;
	}

	/**
	 * @inheritDoc
	 */
	public void moveTo( double x, double y )
	{
		_x = x;
		_y = y;

		bl.x = x;
		bl.y = y + _height;

		tl.x = x;
		tl.y = y;

		tr.x = x + _width;
		tr.y = y;

		br.x = x + _width;
		br.y = y + _height;

		_dirtyBounds = true;
	}

	/**
	 * @inheritDoc
	 */
	public void resizeTo( double width, double height )
	{
		if( width < 0 )
		{
			width = 0;
		}

		if( height < 0 )
		{
			height = 0;
		}

		_width = width;
		_height = height;

		bl.y = _y + height;

		tr.x = _x + width;

		br.x = _x + width;
		br.y = _y + height;

		_dirtyBounds = true;
	}

	/**
	 * MScale the rectangle.
	 */
	public void scaleTo( double scaleX, double scaleY )
	{
		bl.x = ( _x ) * scaleX;
		bl.y = ( _y + _height ) * scaleY;
		tl.x = ( _x ) * scaleX;
		tl.y = ( _y ) * scaleY;
		tr.x = ( _x + _width ) * scaleX;
		tr.y = ( _y ) * scaleY;
		br.x = ( _x + _width ) * scaleX;
		br.y = ( _y + _height ) * scaleY;

		_dirtyBounds = true;
	}

	/**
	 * Rotate the rectangle by given radian value.
	 */
	public void rotateBy( double radian, MPoint pivot )
	{
		double dx = 0.0;
		double dy = 0.0;

		if( pivot != null )
		{
			dx = _x - pivot.x;
			dy = _y - pivot.y;
		}

		final double blX = dx;
		final double blY = dy + _height;

		final double tlX = dx;
		final double tlY = dy;

		final double trX = dx + _width;
		final double trY = dy;

		final double brX = dx + _width;
		final double brY = dy + _height;

		final double sin = Math.sin( radian );
		final double cos = Math.cos( radian );

		dx = _x - dx;
		dy = _y - dy;

		bl.setTo( cos * blX - sin * blY + dx, sin * blX + cos * blY + dy );
		tl.setTo( cos * tlX - sin * tlY + dx, sin * tlX + cos * tlY + dy );
		tr.setTo( cos * trX - sin * trY + dx, sin * trX + cos * trY + dy );
		br.setTo( cos * brX - sin * brY + dx, sin * brX + cos * brY + dy );

		_dirtyBounds = true;
	}

	/**
	 * Transform the offset, scale and rotation of the rectangle
	 */
	public void transformAll( MPoint offset, MPoint scale, double radian, MPoint pivot )
	{
		double dx = offset.x - pivot.x;
		double dy = offset.y - pivot.y;

		final double blX = ( bl.x + dx ) * scale.x;
		final double blY = ( bl.y + dy ) * scale.y;

		final double tlX = ( tl.x + dx ) * scale.x;
		final double tlY = ( tl.y + dy ) * scale.y;

		final double trX = ( tr.x + dx ) * scale.x;
		final double trY = ( tr.y + dy ) * scale.y;

		final double brX = ( br.x + dx ) * scale.x;
		final double brY = ( br.y + dy ) * scale.y;

		final double sin = Math.sin( radian );
		final double cos = Math.cos( radian );

		dx = offset.x - dx;
		dy = offset.y - dy;

		bl.setTo( cos * blX - sin * blY + dx, sin * blX + cos * blY + dy );
		tl.setTo( cos * tlX - sin * tlY + dx, sin * tlX + cos * tlY + dy );
		tr.setTo( cos * trX - sin * trY + dx, sin * trX + cos * trY + dy );
		br.setTo( cos * brX - sin * brY + dx, sin * brX + cos * brY + dy );

		_x = tl.x;
		_y = tl.y;
		_width = tr.x - tl.x;
		_height = bl.y - tl.y;

		_dirtyBounds = true;
	}

	/**
	 * @inheritDoc
	 */
	public boolean contains( double x, double y )
	{
		boolean inside = false;
		int n = _points.length;

		int j = 0;

		MPoint pi;
		MPoint pj;

		for( int i = 0; i < n; i++ )
		{
			j++;

			if( j == n )
			{
				j = 0;
			}

			pi = _points[ i ];
			pj = _points[ j ];

			if( pi.y < y && pj.y >= y || pj.y < y && pi.y >= y )
			{
				if( pi.x + ( y - pi.y ) / ( pj.y - pi.y ) * ( pj.x - pi.x ) < x )
				{
					inside = !inside;
				}
			}
		}

		return inside;
	}

	/**
	 * Returns true if the rectangle contains the given point.
	 */
	public boolean containsPoint2D( MPoint p )
	{
		return contains( p.x, p.y );
	}

	/**
	 * @inheritDoc
	 */
	public MRectangle getBounds()
	{
		if( !_dirtyBounds )
		{
			return _bounds;
		}

		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;

		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;

		MPoint p;

		for( int i = 0; i < _points.length; ++i )
		{
			p = _points[ i ];

			if( p.x < xMin )
			{
				xMin = p.x;
			}
			if( p.x > xMax )
			{
				xMax = p.x;
			}
			if( p.y < yMin )
			{
				yMin = p.y;
			}
			if( p.y > yMax )
			{
				yMax = p.y;
			}
		}

		_bounds.x = ( xMin );
		_bounds.y = ( yMin );
		_bounds.width = ( xMax - xMin );
		_bounds.height = ( yMax - yMin );

		_dirtyBounds = false;

		return _bounds;
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
		_bounds = null;

		_dirtyBounds = false;
	}

	/**
	 * The X coordinate of the rectangle.
	 */
	public double getX()
	{
		return _x;
	}

	/**
	 * The Y coordinate of the rectangle.
	 */
	public double getY()
	{
		return _y;
	}

	/**
	 * The width of the rectangle.
	 */
	public double getWidth()
	{
		return _width;
	}

	/**
	 * The height of the rectangle.
	 */
	public double getHeight()
	{
		return _height;
	}

	/**
	 * Creates and returns a string representation of the MRectComplex object.
	 */
	@Override
	public String toString()
	{
		return "[MRectComplex]";
	}
}

