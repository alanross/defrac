package com.adjazent.defrac.math.geom;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MPoint
{
	public double x;
	public double y;

	/**
	 * Creates a new instance of MPoint.
	 */
	public MPoint( double px, double py )
	{
		this.x = px;
		this.y = py;
	}

	/**
	 * Creates a new instance of MPoint.
	 */
	public MPoint()
	{
		this.x = 0.0;
		this.y = 0.0;
	}

	/**
	 * Add values to x and y.
	 */
	public void add( double px, double py )
	{
		this.x += px;
		this.y += py;
	}

	/**
	 * Subtract values from x and y.
	 */
	public void sub( double px, double py )
	{
		this.x -= px;
		this.y -= py;
	}

	/**
	 * Multiply value with x and y.
	 */
	public void mul( double value )
	{
		this.x *= value;
		this.y *= value;
	}

	/**
	 * Divide x and y by value.
	 */
	public void div( double value )
	{
		this.x /= value;
		this.y /= value;
	}

	/**
	 * The distance between this point and given coordinates.
	 */
	public double dist( double px, double py )
	{
		double xd = this.x - px;
		double yd = this.y - py;

		return Math.sqrt( xd * xd + yd * yd );
	}

	/**
	 * The magnitude of the point.
	 */
	public double magnitude()
	{
		return Math.sqrt( this.x * this.x + this.y * this.y );
	}

	/**
	 * The dot product of the point and given coordinates.
	 */
	public double dot( double px, double py )
	{
		return this.x * px + this.y * py;
	}

	/**
	 * The cross product of the point and given coordinates.
	 */
	public double cross( double px, double py )
	{
		return ( this.x * py ) - ( px * this.y );
	}

	/**
	 * Normalize the point.
	 */
	public void normalize()
	{
		final double m = magnitude();

		if( m != 0 && m != 1 )
		{
			div( m );
		}
	}

	/**
	 * limit the point.
	 */
	public void limit( double max )
	{
		if( magnitude() > max )
		{
			normalize();

			mul( max );
		}
	}

	/**
	 * The heading / angle of the point in radian
	 */
	public double heading()
	{
		final double angle = Math.atan2( -this.y, this.x );

		return -1 * angle;
	}

	/**
	 * Move the point to given coordinates.
	 */
	public void setTo( double x, double y )
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Add the x and y of given point to the x and y values of this point.
	 */
	public void addPoint( MPoint point )
	{
		this.x += point.x;
		this.y += point.y;
	}

	/**
	 * Subtract the x and y of given point from the x and y values of this point.
	 */
	public void subPoint( MPoint point )
	{
		this.x -= point.x;
		this.y -= point.y;
	}

	/**
	 * Multiply the x and y values of this point with the x and y values of given point.
	 */
	public void multPoint( MPoint point )
	{
		this.x *= point.x;
		this.y *= point.y;
	}

	/**
	 * Divide the x and y values of this point by the x and y values of given point.
	 */
	public void divPoint( MPoint point )
	{
		this.x /= point.x;
		this.y /= point.y;
	}

	/**
	 * The dot product of this and given point.
	 */
	public double dotPoint( MPoint point )
	{
		return this.x * point.x + this.y * point.y;
	}

	/**
	 * Get the distance between this and given point.
	 */
	public double distPoint( MPoint point )
	{
		double xd = this.x - point.x;
		double yd = this.y - point.y;

		return Math.sqrt( xd * xd + yd * yd );
	}

	/**
	 * Creates and returns a string representation of the MPoint object.
	 */
	@Override
	public String toString()
	{
		return "[MPoint x:" + this.x + ", y:" + this.y + "]";
	}
}