package com.adjazent.defrac.core.utils;

import java.util.Date;

/**
 * A collection of static util functions associated with the date object.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class DateUtils
{
	private static final int MINUTES_IN_SECONDS = 60;
	private static final int HOURS_IN_SECONDS = MINUTES_IN_SECONDS * 60;
	private static final int DAYS_IN_SECONDS = HOURS_IN_SECONDS * 24;
	private static final int WEEKS_IN_SECONDS = DAYS_IN_SECONDS * 7;
	private static final int MONTHS_IN_SECONDS = WEEKS_IN_SECONDS * 4;
	private static final int YEARS_IN_SECONDS = MONTHS_IN_SECONDS * 12;

	private static final String[] monthsShort = new String[]{ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private static final String[] monthsLong = new String[]{ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

	/**
	 * Returns a string time approximation of given date in milliseconds.
	 * E.g. If milliseconds amount to a time less than 60 seconds ago
	 * "moments ago" will be returned.
	 */
	public static String getApproximation( long milliseconds )
	{
		int seconds = ( int ) ( milliseconds * 0.001 );

		double val;
		String unit;

		if( seconds < MINUTES_IN_SECONDS )
		{
			return "moments ago";
		}
		else if( seconds < HOURS_IN_SECONDS )
		{
			val = Math.ceil( seconds / MINUTES_IN_SECONDS );
			unit = ( val == 1 ? "minute" : "minutes" );
		}
		else if( seconds < ( DAYS_IN_SECONDS ) )
		{
			val = Math.ceil( seconds / HOURS_IN_SECONDS );
			unit = ( val == 1 ? "hour" : "hours" );
		}
		else if( seconds < ( WEEKS_IN_SECONDS ) )
		{
			val = Math.ceil( seconds / DAYS_IN_SECONDS );
			unit = ( val == 1 ? "day" : "days" );
		}
		else if( seconds < ( MONTHS_IN_SECONDS ) )
		{
			val = Math.ceil( seconds / WEEKS_IN_SECONDS );
			unit = ( val == 1 ? "week" : "weeks" );
		}
		else if( seconds < ( YEARS_IN_SECONDS ) )
		{
			val = Math.ceil( seconds / MONTHS_IN_SECONDS );
			unit = ( val == 1 ? "month" : "months" );
		}
		else
		{
			val = Math.floor( seconds / YEARS_IN_SECONDS );
			unit = ( val == 1 ? "year" : "years" );
		}

		return val + " " + unit + " ago";
	}

	/**
	 *
	 */
	public static int getYear( Date date )
	{
		// deprecated!
		return ( date.getYear() + 1900 );
	}

	/**
	 *
	 */
	public static String getMonth( Date date )
	{
		return monthsShort[ date.getMonth() ];
	}

	/**
	 *
	 */
	public static String getHours( Date date )
	{
		return NumberUtils.intToDigits( date.getHours() );
	}

	/**
	 *
	 */
	public static String getMinutes( Date date )
	{
		return NumberUtils.intToDigits( date.getMinutes() );
	}

	/**
	 *
	 */
	public static String getSeconds( Date date )
	{
		return NumberUtils.intToDigits( date.getSeconds() );
	}

	/**
	 * Return a readable string representation of
	 * a time given in milliseconds.
	 *
	 * @param millisecond Time in milliseconds.
	 * @return A string representation containing minutes and seconds.
	 */
	public static String milliToMinSec( int millisecond )
	{
		int sec = ( int ) ( millisecond * 0.001 );

		int min = ( int ) ( sec * 1.666666666667e-02 );

		sec -= min * 60;

		return ( NumberUtils.intToDigits( min ) + " " + NumberUtils.intToDigits( sec ) );
	}

	/**
	 *
	 */
	public static String getDDMonthYYYYHHMMSS( Date date )
	{
		// we should be using Calendar here...

		return (
				date.getDate() + " " +
						getMonth( date ) + " " +
						getYear( date ) + ", " +
						NumberUtils.intToDigits( date.getHours() + 1 ) + ":" +
						NumberUtils.intToDigits( date.getMinutes() ) + ":" +
						NumberUtils.intToDigits( date.getSeconds() )
		);
	}

	/**
	 *
	 */
	public static String getTimeStamp()
	{
		final Date date = new Date();

		return (
				date.getDate() + ". " +
						getMonth( date ) + ", " +
						NumberUtils.intToDigits( date.getHours() + 1 ) + ":" +
						NumberUtils.intToDigits( date.getMinutes() ) + ":" +
						NumberUtils.intToDigits( date.getSeconds() )
		);
	}

	/**
	 * DateUtils class is static container only.
	 */
	private DateUtils()
	{
	}

	/**
	 * Creates and returns a string representation of the DateUtils object.
	 */
	@Override
	public String toString()
	{
		return "[DateUtils]";
	}
}

