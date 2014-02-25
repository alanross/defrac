package com.adjazent.defrac.math.geom;

/**
 * Wrapper class to provide easy access to setting x, y, width and height values.
 * Note that negative width and height values are converted to zero.
 *
 * @author Alan Ross
 * @version 0.1
 */
public class MRectangle implements IMShape
{
	public double x;
	public double y;
	public double width;
	public double height;

	/**
	 * Creates a new instance of MRectangle.
	 */
	public MRectangle( double x, double y, double width, double height )
	{
		moveTo( x, y );
		resizeTo( width, height );
	}

	/**
	 * Creates a new instance of MRectangle.
	 */
	public MRectangle()
	{
		moveTo( 0.0, 0.0 );
		resizeTo( 0.0, 0.0 );
	}

	/**
	 * Set the x, y, width and height values of the MRectangle.
	 * Negative width or height values are converted to 0.0.
	 */
	public void setTo( double x, double y, double width, double height )
	{
		moveTo( x, y );
		resizeTo( width, height );
	}

	/**
	 * Set the width and height values of the MRectangle.
	 * Negative values are converted to 0.0
	 */
	public void resizeTo( double width, double height )
	{
		this.width = ( 0 > width ) ? 0 : width;
		this.height = ( 0 > height ) ? 0 : height;
	}

	/**
	 * @inheritDoc
	 */
	public void moveTo( double x, double y )
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * @inheritDoc
	 */
	public boolean contains( double x, double y )
	{
		return ( x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height );
	}

	/**
	 * @inheritDoc
	 */
	public MRectangle getBounds()
	{
		return this;
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
	}

	public MRectangle clone()
	{
		return new MRectangle( this.x, this.y, this.width, this.height );
	}

	@Override
	public String toString()
	{
		return "[MRectangle" +
				", x: " + this.x +
				", y: " + this.y +
				", width: " + this.width +
				", height: " + this.height + "]";
	}
}

