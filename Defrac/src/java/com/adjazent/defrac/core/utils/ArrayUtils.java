package com.adjazent.defrac.core.utils;

import java.util.*;

/**
 * A collection of static util functions associated with arrays.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class ArrayUtils
{
	public static String join( Collection<?> array, String separator )
	{
		return join( array.toArray(), separator );
	}

	public static String join( Object[] array, String separator )
	{
		StringBuilder result = new StringBuilder();

		int n = array.length - 1;
		int i = 0;

		while( i <= n )
		{
			Object item = array[ i ];

			if( item != null )
			{
				if( item.getClass().isArray() )
				{
					Class<?> type = item.getClass().getComponentType();

					if( type.isPrimitive() )
					{
						result.append( arrayToString( boxPrimitiveArray( item ) ) );
					}
					else
					{
						result.append( arrayToString( ( Object[] ) item ) );
					}
				}
				else
				{
					result.append( String.valueOf( item ) );
				}
			}
			else
			{
				result.append( "null" );
			}

			if( n <= i++ )
			{
				break;
			}

			result.append( separator );
		}

		return result.toString();
	}

	public static String arrayToString( Object[] array )
	{
		if( array == null )
		{
			return "null";
		}

		if( array.length == 0 )
		{
			return "[]";
		}

		return "[" + join( array, ", " ) + "]";
	}

	public static Object[] boxPrimitiveArray( Object array )
	{
		Class<?> type = array.getClass().getComponentType();

		if( type == boolean.class )
		{
			System.out.println( "BOOLEAN" );
		}
		if( type == byte.class )
		{
			return boxByteArray( ( byte[] ) array );
		}
		if( type == short.class )
		{
			return boxShortArray( ( short[] ) array );
		}
		if( type == int.class )
		{
			return boxIntArray( ( int[] ) array );
		}
		if( type == long.class )
		{
			return boxLongArray( ( long[] ) array );
		}
		if( type == float.class )
		{
			return boxFloatArray( ( float[] ) array );
		}
		if( type == double.class )
		{
			return boxDoubleArray( ( double[] ) array );
		}
		if( type == char.class )
		{
			return boxCharArray( ( char[] ) array );
		}

		return new Object[0];
	}

	public static Byte[] boxByteArray( byte[] a )
	{
		int n = a.length;

		Byte[] b = new Byte[ n ];

		while( --n > -1 )
		{
			b[ n ] = a[ n ];
		}

		return b;
	}

	public static Short[] boxShortArray( short[] a )
	{
		int n = a.length;

		Short[] b = new Short[ n ];

		while( --n > -1 )
		{
			b[ n ] = a[ n ];
		}

		return b;
	}

	public static Integer[] boxIntArray( int[] a )
	{
		int n = a.length;

		Integer[] b = new Integer[ n ];

		while( --n > -1 )
		{
			b[ n ] = a[ n ];
		}

		return b;
	}

	public static Long[] boxLongArray( long[] a )
	{
		int n = a.length;

		Long[] b = new Long[ n ];

		while( --n > -1 )
		{
			System.out.println( n + " "+ a[n] );
			b[ n ] = a[ n ];
		}

		return b;
	}

	public static Float[] boxFloatArray( float[] a )
	{
		int n = a.length;

		Float[] b = new Float[ n ];

		while( --n > -1 )
		{
			b[ n ] = a[ n ];
		}

		return b;
	}

	public static Double[] boxDoubleArray( double[] a )
	{
		int n = a.length;

		Double[] b = new Double[ n ];

		while( --n > -1 )
		{
			b[ n ] = a[ n ];
		}

		return b;
	}

	public static Character[] boxCharArray( char[] a )
	{
		int n = a.length;

		Character[] b = new Character[ n ];

		while( --n > -1 )
		{
			b[ n ] = a[ n ];
		}

		return b;
	}

	/**
	 * Compares to arrays. Returns true if both array are the same length and hold the same values.
	 */
	public static boolean isEqual( Object[] a, Object[] b )
	{
		if( a.length != b.length )
		{
			return false;
		}

		int n = a.length;

		while( --n > -1 )
		{
			if( a[ n ] != b[ n ] )
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Reverses the indexes of given array.
	 */
	public static Object[] reverse( Object[] input )
	{
		int start = 0;
		int end = input.length - 1;

		Object tmp;

		while( start < end )
		{
			tmp = input[ start ];
			input[ start++ ] = input[ end ];
			input[ end-- ] = tmp;
		}

		return input;
	}

	/**
	 * The modern version of the Fisher–Yates shuffle runs in O(n)
	 */
	public static Object[] shuffleFisherYates( Object[] array )
	{
		final int n = array.length - 1;

		for( int i = n; i > 0; --i )
		{
			//random number between 0 and i
			int j = ( int ) ( Math.random() * i );

			//swap
			Object tmp = array[ j ];
			array[ j ] = array[ i ];
			array[ i ] = tmp;
		}

		return array;
	}

	/**
	 * Shuffle array of size n such that each element has 1/n probability to
	 * remain in its original spot. The best solution has O(n) complexity.
	 */
	public static Object[] shuffle( Object[] array )
	{
		int size = array.length;

		for( int i = 0; i < size; ++i )
		{
			//random number between 0 and size
			int j = ( int ) ( Math.random() * size );

			if( j != i )
			{
				//swap
				Object tmp = array[ j ];
				array[ j ] = array[ i ];
				array[ i ] = tmp;
			}
		}

		return array;
	}

	/**
	 * performs the swapping operation twice. First time to generate all possible
	 * permutations of elements at that level and second time to restore to the original string.
	 * http//n1b-algo.blogspot.com/2009/01/string-permutations.html
	 */
	public static LinkedList<Object[]> permute( Object[] array )
	{
		LinkedList<Object[]> result = new LinkedList<Object[]>();

		internalPermute( array, 0, result );

		return result;
	}

	/**
	 * @private
	 */
	private static void internalPermute( Object[] array, int d, LinkedList<Object[]> result )
	{
		final int n = array.length;

		if( d == n )
		{
			// clone the result, else LinkedList would only be holding same result multiple times
			result.addLast( array.clone() );
		}
		else
		{
			for( int i = d; i < n; ++i )
			{
				// swap the characters for permutation
				Object tmp = array[ i ];
				array[ i ] = array[ d ];
				array[ d ] = tmp;

				internalPermute( array, d + 1, result );

				// undo the swapping for parent call
				tmp = array[ i ];
				array[ i ] = array[ d ];
				array[ d ] = tmp;
			}
		}
	}

	/**
	 * ArrayUtils class is static container only.
	 */
	private ArrayUtils()
	{
	}

	/**
	 * Creates and returns a string representation of the ArrayUtils object.
	 */
	@Override
	public String toString()
	{
		return "[ArrayUtils]";
	}
}

