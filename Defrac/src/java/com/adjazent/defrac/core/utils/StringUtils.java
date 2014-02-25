package com.adjazent.defrac.core.utils;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.error.NullError;

import java.util.LinkedList;

/**
 * A collection of static util functions associated with strings.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class StringUtils
{
	/**
	 * Returns true if the given string is empty after trimming empty characters from it.
	 */
	public static Boolean isEmpty( String str )
	{
		if( str == null || str.length() == 0 )
		{
			return true;
		}

		return trim( str ).length() == 0;
	}

	/**
	 * Reversing entire string.
	 */
	public static String reverse( String input )
	{
		int start = 0;
		int end = input.length() - 1;
		char tmp;
		char[] array = input.toCharArray();

		while( start < end )
		{
			tmp = array[ start ];
			array[ start++ ] = array[ end ];
			array[ end-- ] = tmp;
		}

		return new String( array );
	}

	public static String cut( String str, String first, String last )
	{
		if( str.contains( first ) && str.contains( last ) )
		{
			int l = str.indexOf( first );
			int r = str.indexOf( last ) + ( last.length() );

			if( l > r )
			{
				throw new GenericError( "StringUtils", "Left larger than right" );
			}

			if( l == 0 )
			{
				return str.substring( r );
			}
			if( r >= str.length() )
			{
				return str.substring( 0, l );
			}

			return str.substring( 0, l ) + str.substring( r, str.length() );
		}
		else
		{
			return str;
		}
	}

	/**
	 * Reversing entire string.
	 */
	public static String trim( String str )
	{
		return trim( str, ' ' );
	}

	public static String trim( String str, char trimmer )
	{
		int min = 0;
		int max = str.length();

		char[] arrayOfChar = str.toCharArray();

		while( ( min < max ) && ( arrayOfChar[ ( min ) ] <= trimmer ) )
		{
			++min;
		}
		while( ( min < max ) && ( arrayOfChar[ ( max - 1 ) ] <= trimmer ) )
		{
			--max;
		}
		return ( ( ( min > 0 ) || ( max < str.length() ) ) ? str.substring( min, max ) : str );
	}

	public static String[] split( String str, char separator )
	{
		LinkedList<String> result = new LinkedList<String>();
		StringBuilder token = new StringBuilder();

		int n = str.length();
		char[] chars = str.toCharArray();
		char c;

		for( int i = 0; i < n; i++ )
		{
			c = chars[ i ];

			if( c == separator )
			{
				result.addLast( token.toString() );
				token.delete( 0, token.length() );
			}
			else
			{
				token.append( c );
			}
		}

		if( token.length() > 0 )
		{
			result.addLast( token.toString() );
			token.delete( 0, token.length() );
		}

		return result.toArray( new String[ result.size() ] );
	}


	/**
	 * Returns the Levenshtein distance of a given string.
	 * <p/>
	 * http//www.merriampark.com/ldjava.htm
	 * http//www.java2s.com/Code/Java/Data-Type/FindtheLevenshteindistancebetweentwoStrings.htm
	 * http//www.miislita.com/searchito/levenshtein-edit-distance.html
	 */
	public static int getLevenshteinDistance( String a, String b )
	{
		if( a == null || b == null )
		{
			throw new NullError( "StringUtils", "Strings for Levenshtein distance must not be null" );
		}

		int[][] distance = new int[ a.length() + 1 ][ b.length() + 1 ];

		for( int i = 0; i <= a.length(); i++ )
		{
			distance[ i ][ 0 ] = i;
		}
		for( int j = 1; j <= b.length(); j++ )
		{
			distance[ 0 ][ j ] = j;
		}

		for( int i = 1; i <= a.length(); i++ )
		{
			for( int j = 1; j <= b.length(); j++ )
			{
				distance[ i ][ j ] = Math.min(
						Math.min(
								distance[ i - 1 ][ j ] + 1,
								distance[ i ][ j - 1 ] + 1 ),
						distance[ i - 1 ][ j - 1 ] + ( ( a.charAt( i - 1 ) == b.charAt( j - 1 ) ) ? 0 : 1 )
				);
			}
		}

		return distance[ a.length() ][ b.length() ];
	}

	public static String randomSequence( int size )
	{
		final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final int charsCount = chars.length() - 1;

		String result = "";

		for( int i = 0; i < size; ++i )
		{
			result += chars.charAt( ( int ) Math.round( Math.random() * charsCount ) );
		}

		return result;
	}

	/**
	 * StringUtils class is static container only.
	 */
	private StringUtils()
	{
	}

	/**
	 * Creates and returns a string representation of the StringUtils object.
	 */
	@Override
	public String toString()
	{
		return "[StringUtils]";
	}
}

