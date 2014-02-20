package com.adjazent.defrac.math.geom;

import com.adjazent.defrac.core.error.MissingImplementationError;
import com.adjazent.defrac.core.error.ValueError;
import com.adjazent.defrac.math.MMath;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MCircle implements IMShape
{
	private MRectangle _bounds;
	private MPoint _center;
	private double _radius;

	/**
	 * Creates a new instance of MCircle.
	 */
	public MCircle( double x, double y, double radius )
	{
		_bounds = new MRectangle();
		_center = new MPoint();

		moveTo( x, y );

		this.setRadius( radius );
	}

	/**
	 * Creates a new instance of MCircle.
	 */
	public MCircle()
	{
		_bounds = new MRectangle();
		_center = new MPoint();

		moveTo( 0.0, 0.0 );

		this.setRadius( 0.0 );
	}

	/**
	 * @inheritDoc
	 */
	public void moveTo( double x, double y )
	{
		_center.x = MMath.round( x );
		_center.y = MMath.round( y );

		_bounds.x = ( _center.x - _radius );
		_bounds.y = ( _center.y - _radius );
	}

	/**
	 * @inheritDoc
	 */
	public void resizeTo( double width, double height )
	{
		throw new MissingImplementationError();
	}

	/**
	 * Returns the (number)area value.
	 */
	public double getArea()
	{
		return Math.PI * _radius * _radius;
	}

	/**
	 * Returns the perimeter.
	 */
	public double getPermieter()
	{
		return 2.0 * Math.PI * _radius;
	}

	/**
	 * @inheritDoc
	 */
	public boolean contains( double x, double y )
	{
		double xd = _center.x - x;
		double yd = _center.y - y;

		return ( Math.sqrt( xd * xd + yd * yd ) <= _radius );
	}

	/**
	 * @inheritDoc
	 */
	public boolean containsPoint( MPoint p )
	{
		return contains( p.x, p.y );
	}

	/**
	 * @inheritDoc
	 */
	public MRectangle getBounds()
	{
		return _bounds;
	}

	/**
	 * @inheritDoc
	 */
	public void dispose()
	{
		_bounds = null;
		_center = null;
	}

	/**
	 * The X coordinate of the circle.
	 */
	public double getX()
	{
		return _center.x;
	}

	/**
	 * The X coordinate of the circle.
	 */
	public void setX( double value )
	{
		_center.x = value;
	}

	/**
	 * The Y coordinate of the circle.
	 */
	public double getY()
	{
		return _center.y;
	}

	/**
	 * The Y coordinate of the circle.
	 */
	public void setY( double value )
	{
		_center.y = value;
	}

	/**
	 * The width of the circle.
	 */
	public double getWidth()
	{
		return _radius * 2;
	}

	/**
	 * The height of the circle.
	 */
	public double getHeight()
	{
		return _radius * 2;
	}

	/**
	 * The radius of the circle.
	 */
	public double getRadius()
	{
		return _radius;
	}

	/**
	 * The radius of the circle.
	 */
	public void setRadius( double value )
	{
		if( value < 0 )
		{
			throw new ValueError( "Radius can not be less than zero" );
		}

		_radius = value;

		_bounds.width = ( _radius * 2 );
		_bounds.height = ( _radius * 2 );
	}

	/**
	 * Creates and returns a string representation of the MCircle object.
	 */
	@Override
	public String toString()
	{
		return "[MCircle" +
				", x: " + getX() +
				", y: " + getY() +
				", radius: " + getRadius() + "]";
	}
}

