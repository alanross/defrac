package com.adjazent.defrac.ui.text;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextFormat
{
	public String fontId;
	public int size;
	public boolean bold;
	public boolean italic;

	public int color;
	public int leading;
	public int tracking;
	public int alignType;

	/**
	 * Creates a new instance of UITextFormat.
	 */
	public UITextFormat( String fontId, int color/*=0xFF000000*/, int leading/*=0*/, int tracking/*=0*/, int alignType/*=0*/ )
	{
		this.fontId = fontId;

		this.color = color;
		this.leading = leading;
		this.tracking = tracking;
		this.alignType = alignType;
	}

	/**
	 * Make a real copy of the UITextFormat object.
	 */
	public UITextFormat clone()
	{
		return new UITextFormat( fontId, color, leading, tracking, alignType );
	}

	/**
	 * Creates and returns a string representation of the UITextFormat object.
	 */
	@Override
	public String toString()
	{
		return "[UITextFormat" +
				"  fontId" + fontId +
				"  color" + color +
				"  leading" + leading +
				"  tracking" + tracking +
				"  alignType" + alignType +
				"]";
	}
}

