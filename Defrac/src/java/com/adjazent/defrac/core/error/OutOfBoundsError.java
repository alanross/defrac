package com.adjazent.defrac.core.error;

/**
 * @author Alan Ross
 * @version 0.1
 */
@SuppressWarnings( "serial" )
public final class OutOfBoundsError extends Error
{
	public OutOfBoundsError( double value, double min, double max )
	{
		super( "Value '" + value + "' is out of bounds [ " + min + "," + max + " ]" );

		System.out.println( "Throw error: " + this );
	}

	public OutOfBoundsError( float value, float min, float max )
	{
		super( "Value '" + value + "' is out of bounds [ " + min + "," + max + " ]" );

		System.out.println( "Throw error: " + this );
	}

	public OutOfBoundsError( int value, int min, int max )
	{
		super( "Value '" + value + "' is out of bounds [ " + min + "," + max + " ]" );

		System.out.println( "Throw error: " + this );
	}

	@Override
	public String toString()
	{
		return "[OutOfBoundsError message:" + getMessage() + "]";
	}
}

