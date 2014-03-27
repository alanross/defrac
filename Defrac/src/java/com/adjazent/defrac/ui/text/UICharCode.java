package com.adjazent.defrac.ui.text;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UICharCode
{
	public static final int NULL = 0;
	public static final int BACK_SPACE = 8;
	public static final int TAB = 9;
	public static final int LINE_FEED = 10;
	public static final int VERTICAL_TAB = 11;
	public static final int FORM_FEED = 12;
	public static final int RETURN = 13;
	public static final int CANCEL = 24;
	public static final int ESC = 27;
	public static final int SPACE = 32;

	public static int toCode( char character )
	{
		//Character.codePointAt( new char[]{character}, 0 );

		return ( int ) character;
	}

	public static char toChar( int code )
	{
		return Character.toString( ( char ) code ).charAt( 0 );
	}

	public UICharCode()
	{

	}

	@Override
	public String toString()
	{
		return "[UICharCode]";
	}
}