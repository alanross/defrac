/*
	TERMS OF USE - EASING EQUATIONS
	---------------------------------------------------------------------------------
	Open source under the BSD License.

	Copyright © 2001 Robert Penner All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions are Redistributions met of source code must retain the above copyright notice, this
	list of conditions and the following disclaimer. Redistributions in binary
	form must reproduce the above copyright notice, this list of conditions and
	the following disclaimer in the documentation and/or other materials provided
	with the distribution. Neither the name of the author nor the names of
	contributors may be used to endorse or promote products derived from this
	software without specific prior written permission.

	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
	AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
	IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
	DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
	FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
	DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
	SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
	CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
	OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
	OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	---------------------------------------------------------------------------------
*/
package com.adjazent.defrac.math;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MEasing
{
	private static final double PI_M2 = Math.PI * 2;
	private static final double PI_D2 = Math.PI / 2;

	public static double linear( double t, double b, double c, double d )
	{
		return c * t / d + b;
	}

	public static double sineIn( double t, double b, double c, double d )
	{
		return -c * Math.cos( t / d * PI_D2 ) + c + b;
	}

	public static double sineOut( double t, double b, double c, double d )
	{
		return c * Math.sin( t / d * PI_D2 ) + b;
	}

	public static double sineInOut( double t, double b, double c, double d )
	{
		return -c / 2 * ( Math.cos( Math.PI * t / d ) - 1 ) + b;
	}

	public static double quintIn( double t, double b, double c, double d )
	{
		return c * ( t /= d ) * t * t * t * t + b;
	}

	public static double quintOut( double t, double b, double c, double d )
	{
		return c * ( ( t = t / d - 1 ) * t * t * t * t + 1 ) + b;
	}

	public static double quintInOut( double t, double b, double c, double d )
	{
		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * t * t * t * t * t + b;
		}
		return c / 2 * ( ( t -= 2 ) * t * t * t * t + 2 ) + b;
	}

	public static double quartIn( double t, double b, double c, double d )
	{
		return c * ( t /= d ) * t * t * t + b;
	}

	public static double quartOut( double t, double b, double c, double d )
	{
		return -c * ( ( t = t / d - 1 ) * t * t * t - 1 ) + b;
	}

	public static double quartInOut( double t, double b, double c, double d )
	{
		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * t * t * t * t + b;
		}
		return -c / 2 * ( ( t -= 2 ) * t * t * t - 2 ) + b;
	}

	public static double quadIn( double t, double b, double c, double d )
	{
		return c * ( t /= d ) * t + b;
	}

	public static double quadOut( double t, double b, double c, double d )
	{
		return -c * ( t /= d ) * ( t - 2 ) + b;
	}

	public static double quadInOut( double t, double b, double c, double d )
	{
		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * t * t + b;
		}
		return -c / 2 * ( ( --t ) * ( t - 2 ) - 1 ) + b;
	}

	public static double exponentialIn( double t, double b, double c, double d )
	{
		return ( t == 0 ) ? b : c * Math.pow( 2, 10 * ( t / d - 1 ) ) + b;
	}

	public static double exponentialOut( double t, double b, double c, double d )
	{
		return ( t == d ) ? b + c : c * ( -Math.pow( 2, -10 * t / d ) + 1 ) + b;
	}

	public static double exponentialInOut( double t, double b, double c, double d )
	{
		if( t == 0 )
		{
			return b;
		}
		if( t == d )
		{
			return b + c;
		}
		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * Math.pow( 2, 10 * ( t - 1 ) ) + b;
		}
		return c / 2 * ( -Math.pow( 2, -10 * --t ) + 2 ) + b;
	}

	public static double elasticIn( double t, double b, double c, double d )
	{
		return elasticIn( t, b, c, d, -1, -1 );
	}

	public static double elasticIn( double t, double b, double c, double d, double a, double p )
	{
		double s;
		if( t == 0 )
		{
			return b;
		}
		if( ( t /= d ) == 1 )
		{
			return b + c;
		}
		if( p == -1 )
		{
			p = d * .3;
		}
		if( a == -1 || a < Math.abs( c ) )
		{
			a = c;
			s = p / 4;
		}
		else
		{
			s = p / PI_M2 * Math.asin( c / a );
		}
		return -( a * Math.pow( 2, 10 * ( t -= 1 ) ) * Math.sin( ( t * d - s ) * PI_M2 / p ) ) + b;
	}

	public static double elasticOut( double t, double b, double c, double d )
	{
		return elasticOut( t, b, c, d, -1, -1 );
	}

	public static double elasticOut( double t, double b, double c, double d, double a, double p )
	{
		double s;
		if( t == 0 )
		{
			return b;
		}
		if( ( t /= d ) == 1 )
		{
			return b + c;
		}
		if( p == -1 )
		{
			p = d * .3;
		}
		if( a == -1 || a < Math.abs( c ) )
		{
			a = c;
			s = p / 4;
		}
		else
		{
			s = p / PI_M2 * Math.asin( c / a );
		}
		return ( a * Math.pow( 2, -10 * t ) * Math.sin( ( t * d - s ) * PI_M2 / p ) + c + b );
	}

	public static double elasticInOut( double t, double b, double c, double d )
	{
		return elasticInOut( t, b, c, d, -1, -1 );
	}

	public static double elasticInOut( double t, double b, double c, double d, double a, double p )
	{
		double s;
		if( t == 0 )
		{
			return b;
		}
		if( ( t /= d / 2 ) == 2 )
		{
			return b + c;
		}
		if( p == -1 )
		{
			p = d * ( .3 * 1.5 );
		}
		if( a == -1 || a < Math.abs( c ) )
		{
			a = c;
			s = p / 4;
		}
		else
		{
			s = p / PI_M2 * Math.asin( c / a );
		}
		if( t < 1 )
		{
			return -.5 * ( a * Math.pow( 2, 10 * ( t -= 1 ) ) * Math.sin( ( t * d - s ) * PI_M2 / p ) ) + b;
		}
		return a * Math.pow( 2, -10 * ( t -= 1 ) ) * Math.sin( ( t * d - s ) * PI_M2 / p ) * .5 + c + b;
	}

	public static double circularIn( double t, double b, double c, double d )
	{
		return -c * ( Math.sqrt( 1 - ( t /= d ) * t ) - 1 ) + b;
	}

	public static double circularOut( double t, double b, double c, double d )
	{
		return c * Math.sqrt( 1 - ( t = t / d - 1 ) * t ) + b;
	}

	public static double circularInOut( double t, double b, double c, double d )
	{
		if( ( t /= d / 2 ) < 1 )
		{
			return -c / 2 * ( Math.sqrt( 1 - t * t ) - 1 ) + b;
		}
		return c / 2 * ( Math.sqrt( 1 - ( t -= 2 ) * t ) + 1 ) + b;
	}

	public static double backIn( double t, double b, double c, double d )
	{
		double s = 1.70158;

		return c * ( t /= d ) * t * ( ( s + 1 ) * t - s ) + b;
	}

	public static double backOut( double t, double b, double c, double d )
	{
		double s = 1.70158;

		return c * ( ( t = t / d - 1 ) * t * ( ( s + 1 ) * t + s ) + 1 ) + b;
	}

	public static double backInOut( double t, double b, double c, double d )
	{
		double s = 1.70158;

		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * ( t * t * ( ( ( s *= ( 1.525 ) ) + 1 ) * t - s ) ) + b;
		}
		return c / 2 * ( ( t -= 2 ) * t * ( ( ( s *= ( 1.525 ) ) + 1 ) * t + s ) + 2 ) + b;
	}

	public static double bounceIn( double t, double b, double c, double d )
	{
		return c - bounceOut( d - t, 0, c, d ) + b;
	}

	public static double bounceOut( double t, double b, double c, double d )
	{
		if( ( t /= d ) < ( 1 / 2.75 ) )
		{
			return c * ( 7.5625 * t * t ) + b;
		}
		else if( t < ( 2 / 2.75 ) )
		{
			return c * ( 7.5625 * ( t -= ( 1.5 / 2.75 ) ) * t + .75 ) + b;
		}
		else if( t < ( 2.5 / 2.75 ) )
		{
			return c * ( 7.5625 * ( t -= ( 2.25 / 2.75 ) ) * t + .9375 ) + b;
		}
		else
		{
			return c * ( 7.5625 * ( t -= ( 2.625 / 2.75 ) ) * t + .984375 ) + b;
		}
	}

	public static double bounceInOut( double t, double b, double c, double d )
	{
		if( t < d / 2 )
		{
			return bounceIn( t * 2, 0, c, d ) * .5 + b;
		}
		else
		{
			return bounceOut( t * 2 - d, 0, c, d ) * .5 + c * .5 + b;
		}
	}

	public static double cubicIn( double t, double b, double c, double d )
	{
		return c * ( t /= d ) * t * t + b;
	}

	public static double cubicOut( double t, double b, double c, double d )
	{
		return c * ( ( t = t / d - 1 ) * t * t + 1 ) + b;
	}

	public static double cubicInOut( double t, double b, double c, double d )
	{
		if( ( t /= d / 2 ) < 1 )
		{
			return c / 2 * t * t * t + b;
		}
		return c / 2 * ( ( t -= 2 ) * t * t + 2 ) + b;
	}

	/**
	 * Creates a new instance of MEasing.
	 */
	private MEasing()
	{
	}

	/**
	 * Creates and returns a string representation of the MEasing object.
	 */
	@Override
	public String toString()
	{
		return "[MEasing]";
	}
}

