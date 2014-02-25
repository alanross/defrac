package com.adjazent.defrac.math;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MColor
{
	/**
	 * Set color value by separate rgb values.
	 * All values must range between 0x0 and 0xFF.
	 */
	public static int argb2hex( int a, int r, int g, int b )
	{
		return ( a << 24 | r << 16 | g << 8 | b );
	}

	/**
	 * Return the values of the alpha, red, green and blue channels
	 * (entries)separately in an array.
	 */
	public static int[] hex2argb( int color )
	{
		return new int[]{
				( color >> 24 ) & 0xff,
				( color >> 16 ) & 0xff,
				( color >> 8 ) & 0xff,
				color & 0xff
		};
	}

	/**
	 * Converts a hex value in uint representation to a string representation.
	 * E.g. the uint value 16711680 which is 0xFF0000 will return the string "0xFF0000".
	 */
	public static String hex2str( int value )
	{
		StringBuilder sb = new StringBuilder( Integer.toHexString( value ) );

		if( sb.length() < 2 )
		{
			sb.insert( 0, '0' ); // pad with leading zero if needed
		}
		return "#" + sb.toString();
	}

	/**
	 * 0 H.0 - 360.0;
	 * 0 S.0 - 1.0;
	 * 0 V.0 - 1.0;
	 */
	public static int hsv2rgb( double H, double S, double V )
	{
		double R = 0.0;
		double G = 0.0;
		double B = 0.0;

		if( V == 0 )
		{
			return 0;
		}
		else if( S == 0 )
		{
			R = G = B = V;
		}
		else
		{
			final double hf = H / 60.0;
			final int hfInt = ( int ) hf;
			final double f = hf - hfInt;
			final double pv = V * ( 1.0 - S );
			final double qv = V * ( 1.0 - S * f );
			final double tv = V * ( 1.0 - S * ( 1.0 - f ) );

			switch( hfInt )
			{
				case 0:
					R = V;
					G = tv;
					B = pv;
					break;
				case 1:
					R = qv;
					G = V;
					B = pv;
					break;
				case 2:
					R = pv;
					G = V;
					B = tv;
					break;
				case 3:
					R = pv;
					G = qv;
					B = V;
					break;
				case 4:
					R = tv;
					G = pv;
					B = V;
					break;
				case 5:
					R = V;
					G = pv;
					B = qv;
					break;
				case -1:
					R = V;
					G = pv;
					B = qv;
					break;
			}
		}

		int r = ( int ) ( R * 256 );
		int g = ( int ) ( G * 256 );
		int b = ( int ) ( B * 256 );

		if( r > 0xFF )
		{
			r = 0xFF;
		}
		if( g > 0xFF )
		{
			g = 0xFF;
		}
		if( b > 0xFF )
		{
			b = 0xFF;
		}

		return r << 16 | g << 8 | b;
	}

	/**
	 * Converts a hex value, given in string representation, to an uint value.
	 * E.g. "0xFF0000" will return 16711680
	 */
	public static int str2hex( String value )
	{
		value = value.replace( "0x", "" ).replace( "#", "" );

		return ( int ) Long.parseLong( value, 16 );
	}

	private MColor()
	{
	}

	@Override
	public String toString()
	{
		return "[MColor]";
	}
}

