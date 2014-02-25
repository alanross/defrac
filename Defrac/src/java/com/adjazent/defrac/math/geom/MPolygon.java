package com.adjazent.defrac.math.geom;

import com.adjazent.defrac.core.error.MissingImplementationError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MPolygon implements IMShape
{
	private LinkedList<MPoint> _points;
	private MRectangle _bounds;

	/**
	 * Creates a new instance of MPolygon.
	 */
	public MPolygon()
	{
		_points = new LinkedList<MPoint>();
		_bounds = new MRectangle();
	}

	/**
	 * @private
	 */
	private void updateBounds()
	{
		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;

		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;

		MPoint p;

		for( int i = 0; i < _points.size(); ++i )
		{
			p = _points.get( i );

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

		_bounds.setTo( xMin, yMin, xMax, yMax );
	}

	/**
	 * @inheritDoc
	 */
	public void moveTo( double x, double y )
	{
		final double dx = x - _bounds.x;
		final double dy = y - _bounds.y;

		final int n = _points.size();

		MPoint p;

		for( int i = 0; i < n; ++i )
		{
			p = _points.get( i );
			p.x += dx;
			p.y += dy;
		}

		updateBounds();
	}

	/**
	 * @inheritDoc
	 */
	public void resizeTo( double width, double height )
	{
		throw new MissingImplementationError();
	}

	/**
	 * @inheritDoc
	 */
	public boolean contains( double x, double y )
	{
		boolean inside = false;
		int n = _points.size();

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

			pi = _points.get( i );
			pj = _points.get( j );

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
	 * @inheritDoc
	 */
	public MRectangle getBounds()
	{
		return _bounds;
	}

	/**
	 * Add a point defining the polygon.
	 */
	public void addPoint( MPoint p )
	{
		_points.addLast( p );

		updateBounds();
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
		_points.clear();
		_points = null;

		_bounds = null;
	}

	/**
	 * The points defining the polygon.
	 */
	public LinkedList<MPoint> getPoints()
	{
		return _points;
	}

	/**
	 * Creates and returns a string representation of the MPolygon object.
	 */
	@Override
	public String toString()
	{
		return "[MPolygon points:" + ( ( _points != null ) ? _points.size() : "0" ) + "]";
	}
}

