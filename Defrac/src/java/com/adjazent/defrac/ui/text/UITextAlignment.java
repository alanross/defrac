package com.adjazent.defrac.ui.text;

import com.adjazent.defrac.core.utils.StringUtils;

/**
 * Enumeration of the different alignment types a text can have.
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextAlignment
{
	public static int LEFT = 1;
	public static int CENTER = 2;
	public static int RIGHT = 4;

	public static int TOP = 8;
	public static int MIDDLE = 16;
	public static int BOTTOM = 32;

	/**
	 * Creates and returns a string representation of alignment type.
	 */
	public static String typeToString( int type )
	{
		String result = "";

		if( ( type & TOP ) == TOP )
		{
			result += "TOP";
		}
		if( ( type & MIDDLE ) == MIDDLE )
		{
			result += "MIDDLE";
		}
		if( ( type & BOTTOM ) == BOTTOM )
		{
			result += "BOTTOM";
		}
		if( ( type & LEFT ) == LEFT )
		{
			result += "LEFT";
		}
		if( ( type & CENTER ) == CENTER )
		{
			result += "CENTER";
		}
		if( ( type & RIGHT ) == RIGHT )
		{
			result += "RIGHT";
		}

		return ( StringUtils.isEmpty( result ) ) ? "unknown" : result;
	}

	/**
	 * UITextAlignment class is static container only.
	 */
	private UITextAlignment()
	{
	}

	/**
	 * Creates and returns a string representation of the UITextAlignment object.
	 */
	@Override
	public String toString()
	{
		return "[UITextAlignment]";
	}
}

