package com.adjazent.defrac.math;

import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MathUtil
{
	public static double abs( double value )
	{
		return ( ( value < 0 ) ? -value : value );
	}

	public static int argb2hex( int a, int r, int g, int b )
	{
		return ( a << 24 | r << 16 | g << 8 | b );
	}

	public static double average( int[] values )
	{
		double result = 0.0;

		int n = values.length;

		for( int i = 0; i < n; ++i )
		{
			// dividing must be done here, as else adding
			// two max values would result in infinity
			// also n could be zero if length of LinkedList is zero.
			// that would result in a zero division.
			result += values[ i ] / n;
		}

		return result;
	}

	public static double bezierCubic( double a, double b, double c, double d, double t )
	{
		double s = 1 - t;

		return s * s * s * a + 3 * s * s * t * b + 3 * s * t * t * c + t * t * t * d;
	}

	public static double bezierQuadratic( double a, double b, double c, double t )
	{
		double s = 1 - t;

		return s * s * a + 2 * s * t * b + t * t * c;
	}

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

	public static double deg2rad( double deg )
	{
		return deg * Math.PI / 180;
	}

	public static double dist( double x0, double y0, double x1, double y1 )
	{
		double dx = x1 - x0;
		double dy = y1 - y0;

		return Math.sqrt( dx * dx + dy * dy );
	}

	public static boolean equals( double a, double b, double precision )
	{
		double dif = a - b;

		return ( ( dif < 0 ? -dif : dif ) <= precision );
	}

	public static boolean equals2D( Point a, Point b, double precision )
	{
		final double dx = b.x - a.x;
		final double dy = b.y - a.y;

		return ( Math.sqrt( dx * dx + dy * dy ) <= precision );
	}

	public static int[] hex2argb( int color )
	{
		return ( new int[]{
				( color >> 24 ) & 0xff,
				( color >> 16 ) & 0xff,
				( color >> 8 ) & 0xff,
				color & 0xff
		} );
	}

	public static String hex2str( int value )
	{
		StringBuilder sb = new StringBuilder( Integer.toHexString( value ) );

		if( sb.length() < 2 )
		{
			sb.insert( 0, '0' ); // pad with leading zero if needed
		}
		return "0x" + sb.toString();
	}

	/**
	 * @author Alan Ross
	 * @version 0.1
	 */
	public static int hsv2rgb( double h, double s, double v )
	{
		double r = 0.0;
		double g = 0.0;
		double b = 0.0;

		if( v == 0 )
		{
			return 0;
		}
		else if( s == 0 )
		{
			r = g = b = v;
		}
		else
		{
			final double hf = h / 60.0;
			final int hfInt = ( int ) hf;
			final double f = hf - hfInt;
			final double pv = v * ( 1.0 - s );
			final double qv = v * ( 1.0 - s * f );
			final double tv = v * ( 1.0 - s * ( 1.0 - f ) );

			switch( hfInt )
			{
				case 0:
					r = v;
					g = tv;
					b = pv;
					break;
				case 1:
					r = qv;
					g = v;
					b = pv;
					break;
				case 2:
					r = pv;
					g = v;
					b = tv;
					break;
				case 3:
					r = pv;
					g = qv;
					b = v;
					break;
				case 4:
					r = tv;
					g = pv;
					b = v;
					break;
				case 5:
					r = v;
					g = pv;
					b = qv;
					break;
				case -1:
					r = v;
					g = pv;
					b = qv;
					break;
			}
		}

		return clampInt( (int)(r * 256), 0, 0xFF ) << 16 | clampInt( (int)(g * 256), 0, 0xFF ) << 8 | clampInt( (int)(b * 256), 0, 0xFF );
	}

	public static boolean isEven( int value )
	{
		// (value % 2) == 0					-> 10106ms
		// ((value * 0.5) – (i >> 1)) == 0	-> 2974ms
		// (value & 1) == 1					-> 2277ms
		// !(value & 1)						-> 2612ms
		return ( ( value & 1 ) == 0 );
	}

	public static boolean isInBounds( double val, double min, double max )
	{
		return !( val < min || val > max );
	}

	public static double max( double a, double b )
	{
		return ( a > b ) ? b : a;
	}

	public static double min( double a, double b )
	{
		return ( a < b ) ? b : a;
	}

	public static int nextPowerOfTwo( int value ) throws Exception
	{
		if( value < 0 )
		{
			throw new Exception( "nextPowerOfTwo does not compute negative values" );
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

	public static double normalize( double value, double low, double high )
	{
		return ( value - low ) / ( high - low );
	}

	public static double rad2deg( double rad )
	{
		return rad * 180 / Math.PI;
	}

	public static void rotateBy( double deg, int radius, Point point )
	{
		final double rad = deg * Math.PI / 180.0;

		point.x = ( float ) ( Math.sin( rad ) * radius );
		point.y = ( float ) ( Math.cos( rad ) * radius );
	}

	public static int round( double value )
	{
		if( value > 0 )
		{
			return ( int ) ( value + 0.5 );
		}
		if( value < 0 )
		{
			return -( int ) ( -value + 0.5 );
		}
		else
		{
			return 0;
		}
	}

	public static double roundToPrecision( double value, int precision )
	{
		double decimalPlaces = Math.pow( 10, precision );

		return Math.round( decimalPlaces * value ) / decimalPlaces;
	}

	public static double scale( double inVal, double inMin, double inMax, double outMin, double outMax )
	{
		return ( ( ( inVal - inMin ) * ( outMax - outMin ) / ( inMax - inMin ) ) + outMin );
	}

	public static int str2hex( String value )
	{
		return ( int ) Long.parseLong( value, 16 );
	}

	public static double sum( int[] values )
	{
		double result = 0.0;

		int n = values.length;

		for( int i = 0; i < n; ++i )
		{
			result += values[ i ];
		}

		return result;
	}

	public MathUtil()
	{
	}

	@Override
	public String toString()
	{
		return "[MathUtil]";
	}
}