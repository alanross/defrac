package com.adjazent.defrac.ui.widget.text;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIKeyCode
{
	// all keyCode values less than this are special codes
	public static final int SPECIAL = 48;

	public static final int BACKSPACE = 8;
	public static final int DEL = 8;
	public static final int TAB = 9;
	public static final int ENTER = 13;
	public static final int SHIFT = 16;
	public static final int CTRL = 17;
	public static final int ALT = 18;
	public static final int BREAK = 19;
	public static final int CAPS_LOCK = 20;
	public static final int ESCAPE = 27;
	public static final int SPACE = 32;
	public static final int PAGE_UP = 33;
	public static final int PAGE_DOWN = 34;
	public static final int END = 35;
	public static final int HOME = 36;
	public static final int ARROW_LEFT = 37;
	public static final int ARROW_UP = 38;
	public static final int ARROW_RIGHT = 39;
	public static final int ARROW_DOWN = 40;
	public static final int INSERT = 45;
	public static final int DELETE = 46;
	public static final int CMD = 91;
	public static final int F1 = 112;
	public static final int F2 = 113;
	public static final int F3 = 114;
	public static final int F4 = 115;
	public static final int F5 = 116;
	public static final int F6 = 117;
	public static final int F7 = 118;
	public static final int F8 = 119;
	public static final int F9 = 120;
	public static final int F10 = 121;
	public static final int F11 = 122;
	public static final int F12 = 123;

	public static boolean isSpecial( int keyCode )
	{
		return ( ( keyCode < SPECIAL || keyCode > F1 ) && keyCode != SPACE );
	}

	public UIKeyCode()
	{
	}

	@Override
	public String toString()
	{
		return "[UIKeyCode]";
	}
}