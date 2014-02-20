package com.adjazent.defrac.math;

import com.adjazent.defrac.core.error.ValueError;
import com.adjazent.defrac.math.geom.MPoint;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MMath
{
	/**
	 * Return the absolute value of a given number.
	 */
	public static double abs( double value )
	{
		return value < 0 ? -value : value;
	}

	/**
	 * Return the average value of given array of numbers.
	 */
	public static double average( double[] values )
	{
		double result = 0.0;

		int n = values.length;

		for( int i = 0; i < n; ++i )
		{
			// dividing must be done here, as else adding
			// two max values would result in infinity
			// also n could be zero if length of array is zero.
			// that would result in a zero division.
			result += values[ i ] / n;
		}

		return result;
	}

	/**
	 *
	 */
	public static double bezierCubic( double a, double b, double c, double d, double t )
	{
		double s = 1 - t;

		return s * s * s * a + 3 * s * s * t * b + 3 * s * t * t * c + t * t * t * d;
	}

	/**
	 *
	 */
	public static double bezierQuadratic( double a, double b, double c, double t )
	{
		double s = 1 - t;

		return s * s * a + 2 * s * t * b + t * t * c;
	}

	public static double circleArea( double diameter )
	{
		double radius = diameter / 2;

		// The formula is Pi * radius * radius.
		return Math.PI * radius * radius;
	}

	public static double circleCircumference( double diameter )
	{
		return Math.PI * diameter;
	}

	/**
	 * Return the given value, clamped by given min and max value.
	 */
	public static double clamp( double val, double min, double max )
	{
		if( val < min )
		{
			return min;
		}
		if( val > max )
		{
			return max;
		}

		return val;
	}

	/**
	 * Return the given integer value, clamped by given min and max value.
	 */
	public static int clampInt( int val, int min, int max )
	{
		if( val < min )
		{
			return min;
		}
		if( val > max )
		{
			return max;
		}

		return val;
	}

	/**
	 *
	 */
	public static double cumulatedPercent( double val, int times, double percent )
	{
		for( int i = 0; i < times; ++i )
		{
			val += ( val / 100.0 ) * percent;
		}

		return val;
	}

	/**
	 * convert degree to radian
	 */
	public static double deg2rad( double deg )
	{
		return deg * Math.PI / 180;
	}

	/**
	 * Return the distance between two points defined by their x and y value.
	 */
	public static double dist( double x0, double y0, double x1, double y1 )
	{
		double dx = x1 - x0;
		double dy = y1 - y0;

		return Math.sqrt( dx * dx + dy * dy );
	}

	/**
	 * getDistance of given point to center of circle
	 */
	public static double distToCircleCenter( int px, int py, int radius )
	{
		double pctX = ( px - radius ) / radius;
		double pctY = ( py - radius ) / radius;

		double pctDistance = Math.sqrt( pctX * pctX + pctY * pctY );

		return pctDistance;
	}

	/**
	 * Compares two given values according to provided precision.
	 * 1 equals 1.09 if precision is 0.1
	 */
	public static boolean equals( double a, double b, double precision )
	{
		double dif = a - b;

		return ( ( dif < 0 ? -dif : dif ) <= precision );
	}

	/**
	 * Returns true if two points are within the bounds of precision, false other wise.
	 */
	public static boolean equals2D( MPoint a, MPoint b, double precision )
	{
		double dx = b.x - a.x;
		double dy = b.y - a.y;

		return ( Math.sqrt( dx * dx + dy * dy ) <= precision );
	}

	/**
	 *      6
	 *    5   7
	 *  4  -1   0
	 *    3   1
	 *      2
	 */
	public static int getDirection2D( MPoint center, MPoint relative )
	{
		//right side
		if( center.x < relative.x )
		{
			if( center.y > relative.y )
			{
				return 7;
			}
			if( center.y < relative.y )
			{
				return 1;
			}

			return 0;
		}

		//left side
		else if( center.x > relative.x )
		{
			if( center.y > relative.y )
			{
				return 5;
			}
			if( center.y < relative.y )
			{
				return 3;
			}

			return 4;
		}

		// center
		else
		{
			if( center.y > relative.y )
			{
				return 6;
			}
			if( center.y < relative.y )
			{
				return 2;
			}

			return -1;
		}
	}

	/**
	 *
	 */
	public static LinkedList<MPoint> grid( int rows, int cols, int spacingX, int spacingY, int offsetX, int offsetY )
	{
		LinkedList<MPoint> points = new LinkedList<MPoint>();

		for( int r = 0; r < rows; ++r )
		{
			for( int c = 0; c < cols; ++c )
			{
				points.addLast( new MPoint( ( c * spacingX ) + offsetX, ( r * spacingY ) + offsetY ) );
			}
		}

		return points;
	}

	/**
	 *
	 */
	public static LinkedList<MPoint> grid2( int width, int height, int spacingX, int spacingY, int offsetX, int offsetY )
	{
		int rows = ( int ) Math.floor( height / spacingY );
		int cols = ( int ) Math.floor( width / spacingX );

		LinkedList<MPoint> points = new LinkedList<MPoint>();

		for( int r = 0; r < rows; ++r )
		{
			for( int c = 0; c < cols; ++c )
			{
				points.addLast( new MPoint( ( c * spacingX ) + offsetX, ( r * spacingY ) + offsetY ) );
			}
		}

		return points;
	}

	/**
	 *
	 */
	public static String int2Binary( int value )
	{
		return Integer.toBinaryString(value);
	}

	/**
	 *
	 */
	public static boolean isEven( int value )
	{
		// (value % 2) == 0					-> 10106ms
		// ((value * 0.5) – (i >> 1)) == 0	-> 2974ms
		// !(value & 1)						-> 2612ms
		// (value & 1) == 0					-> 2277ms
		return ( ( value & 1 ) == 0 );
	}

	/**
	 *
	 */
	public static boolean isInBounds( double val, double min, double max )
	{
		return !( val < min || val > max );
	}

	/**
	 * Return the larger of two numbers.
	 */
	public static double max( double a, double b )
	{
		return ( a > b ) ? a : b;
	}

	/**
	 * Return the lesser of two numbers.
	 */
	public static double min( double a, double b )
	{
		return ( a < b ) ? a : b;
	}

	/**
	 * Return the next power of two of given value.
	 * 5 Example returns 8, 1000 returns 1024.
	 */
	public static int nextPowerOfTwo( int value )
	{
		if( value < 0 )
		{
			throw new ValueError( "nextPowerOfTwo does not compute negative values" );
		}

		value--;
		value |= value >> 1;
		value |= value >> 2;
		value |= value >> 4;
		value |= value >> 8;
		value |= value >> 16;
		value++;

		return value;
	}

	/**
	 * Normalize a given value.
	 */
	public static double normalize( double value, double low, double high )
	{
		return ( value - low ) / ( high - low );
	}

	/**
	 *
	 */
	public static void quantize2D( MPoint pos, double hq, double vq )
	{
		pos.x = (int)( Math.floor( pos.x / ( hq ) ) * hq );
		pos.y = (int)( Math.floor( pos.y / ( vq ) ) * vq );
	}

	/**
	 * convert radian to degree
	 */
	public static double rad2deg( double rad )
	{
		return rad * 180 / Math.PI;
	}

	/**
	 *
	 */
	public static double rand( double min, double max)
	{
		return min + Math.random() * ( max - min );
	}

	/**
	 * Calculates and sets X and Y position of point. MPoint is defines by defined by radius and degree.
	 */
	public static void rotateBy( double deg, int radius, MPoint point )
	{
		final double rad = deg * Math.PI / 180.0;

		point.x = Math.sin( rad ) * radius;
		point.y = Math.cos( rad ) * radius;
	}

	/**
	 * Round a given number to an integer value.
	 */
	public static int round( double value )
	{
		if( value > 0 )
		{
			return (int)( value + 0.5 );
		}
		if( value < 0 )
		{
			return -(int)( -value + 0.5 );
		}
		else
		{
			return 0;
		}
	}

	/**
	 * roundToPrecision( 0.1515, 0 ) // 0 Output
	 * roundToPrecision( 0.1515, 1 ) // 0 Output.2
	 * roundToPrecision( 0.1515, 2 ) // 0 Output.15
	 * roundToPrecision( 0.1515, 3 ) // 0 Output.152
	 */
	public static double roundToPrecision( double value, int precision )
	{
		double decimalPlaces = Math.pow( 10, precision );

		return Math.round( decimalPlaces * value ) / decimalPlaces;
	}

	/**
	 * convert a given value of one scale to another
	 * result = ( ( (inVal - inMin) * (outMax - outMin) / (inMax - inMin) ) + outMin )
	 */
	public static double scale( double inVal, double inMin, double inMax, double outMin, double outMax )
	{
		return ( ( ( inVal - inMin ) * ( outMax - outMin ) / ( inMax - inMin ) ) + outMin );
	}

	/**
	 * Return the sum of all given numbers
	 */
	public static double sum( double[] values )
	{
		double result = 0.0;

		int n = values.length;

		for( int i = 0; i < n; ++i )
		{
			result += values[ i ];
		}

		return result;
	}

	/**
	 * Creates a new instance of MMath.
	 */
	private MMath()
	{
	}

	/**
	 * Creates and returns a string representation of the MMath object.
	 */
	@Override
	public String toString()
	{
		return "[MMath]";
	}
}

