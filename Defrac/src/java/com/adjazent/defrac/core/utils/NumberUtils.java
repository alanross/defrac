package com.adjazent.defrac.core.utils;

import com.adjazent.defrac.core.error.ValueError;

/**
 * A collection of static util functions associated with numbers.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class NumberUtils
{
	/**
	 * Return a string representation of the given integer value.
	 * Digits is the total (minimum) amount of digits the resulting value will have.
	 * If digits is setTo 4, 0001 will be returned for the value 1 AND 12345 will be returned for 12345.
	 */
	public static String intToDigits( int value )
	{
		return intToDigits( value, 2 );
	}

	/**
	 * Return a string representation of the given integer value.
	 * Digits is the total (minimum) amount of digits the resulting value will have.
	 * If digits is setTo 4, 0001 will be returned for the value 1 AND 12345 will be returned for 12345.
	 */
	public static String intToDigits( int value, int digits )
	{
		if( digits < 1 || digits > 10 )
		{
			throw new ValueError( "NumberUtils", "Choose a value for digits between 1 and 10" );
		}

		boolean negative = ( value < 0 );

		int dezCount = 1;

		String result = "";

		value = Math.abs( value );

		while( --digits > 0 )
		{
			if( value < ( dezCount *= 10 ) )
			{
				result += "0";
			}
		}

		return ( negative ) ? ( "-" + result + value ) : ( result + value );
	}

	/**
	 * Creates a new instance of NumberUtils.
	 */
	private NumberUtils()
	{
	}

	/**
	 * Generates and returns the string representation of the NumberUtils object.
	 */
	@Override
	public String toString()
	{
		return "[NumberUtils]";
	}
}

