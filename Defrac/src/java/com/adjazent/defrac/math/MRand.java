package com.adjazent.defrac.math;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MRand
{
	/**
	 * Possibility to setA seed value to be used a random value for all seeded random functions.
	 * The value mus be between 0.0 and 1.0.
	 */
	public static double seed = Math.random();

	/**
	 * generates a random number between 0 and given max value. Default max is 1.0.
	 */
	public static double random( double max )
	{
		return Math.random() * max;
	}

	/**
	 * generates a random number between -1 and 1.
	 */
	public static double randomNegPos()
	{
		return Math.random() - Math.random();
	}

	/**
	 * generates a random number between min and max.
	 */
	public static double randomMinMax( double min, double max )
	{
		return min + Math.random() * ( max - min );
	}

	/**
	 * Generates a random number between given min and max integer values.
	 */
	public static int randomMinMaxInt( int min, int max )
	{
		return MMath.round( randomMinMax( min, max ) );
	}

	/**
	 * Returns a random boolean value. The return value is weighted by the chance value.
	 * E.g if chance is 0.8 returns true in 80% cases.
	 */
	public static boolean randomBool( double chance )
	{
		return ( random( 1.0 ) < chance );
	}

	/**
	 * generates a seeded random number between 0 and given max value. Default max is 1.0.
	 */
	public static double sRandom( double max ) throws Exception
	{
		if( !MMath.isInBounds( seed, 0.0, 1.0 ) )
		{
			throw new Exception( "Seed value must be between 0.0 and 1.0. It is " + seed );
		}

		return seed * max;
	}

	/**
	 * generates a seeded random number between min and max.
	 */
	public static double sRandomMinMax( double min, double max ) throws Exception
	{
		if( !MMath.isInBounds( seed, 0.0, 1.0 ) )
		{
			throw new Exception( "Seed value must be between 0.0 and 1.0. It  is" + seed );
		}

		return min + seed * ( max - min );
	}

	/**
	 * Generates a seeded random number between given min and max integer values.
	 */
	public static int sRandomMinMaxInt( int min, int max )
	{
		return MMath.round( randomMinMax( min, max ) );
	}

	/**
	 * Returns a seeded random boolean value. The return value is weighted by the chance value.
	 * E.g if chance is 0.8 returns true in 80% cases.
	 */
	public static boolean sRandomBool( double chance ) throws Exception
	{
		if( !MMath.isInBounds( seed, 0.0, 1.0 ) )
		{
			throw new Exception( "Seed value must be between 0.0 and 1.0. It is " + seed );
		}

		return ( seed < chance );
	}

	/**
	 * Creates a new instance of MRand.
	 */
	private MRand()
	{
	}

	/**
	 * Creates and returns a string representation of the MRand object.
	 */
	@Override
	public String toString()
	{
		return "[MRand]";
	}
}

