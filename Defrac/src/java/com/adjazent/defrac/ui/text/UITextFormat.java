package com.adjazent.defrac.ui.text;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UITextFormat
{
	public String fontId;

	public int color;
	public int leading;
	public int tracking;
	public int alignType;
	public int lineSpacing;

	public UITextFormat( String fontId, int color, int leading, int tracking, int alignType, int lineSpacing )
	{
		this.fontId = fontId;

		this.color = color;
		this.leading = leading;
		this.tracking = tracking;
		this.alignType = alignType;
		this.lineSpacing = lineSpacing;
	}

	public UITextFormat( String fontId )
	{
		this.fontId = fontId;

		this.color = 0;
		this.leading = 0;
		this.tracking = 0;
		this.alignType = 0;
		this.lineSpacing = 0;
	}

	public UITextFormat clone()
	{
		return new UITextFormat( fontId, color, leading, tracking, alignType, lineSpacing );
	}

	@Override
	public String toString()
	{
		return "[UITextFormat" +
				", fontId:" + fontId +
				", color:" + color +
				", leading:" + leading +
				", tracking:" + tracking +
				", alignType:" + alignType +
				", lineSpacing:" + lineSpacing +
				"]";
	}
}

