package com.adjazent.defrac.ui.text.font.glyph;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UIKerningPair
{
	public int firstGlyphCode;
	public int secondGlyphCode;
	public int kerning;

	/**
	 * Creates a new instance of UIKerningPair.
	 */
	public UIKerningPair( int firstGlyphCode, int secondGlyphCode, int kerningAmount )
	{
		this.firstGlyphCode = firstGlyphCode;
		this.secondGlyphCode = secondGlyphCode;
		this.kerning = kerningAmount;
	}

	/**
	 * Creates and returns a string representation of the UIKerningPair object.
	 */
	@Override
	public String toString()
	{
		return "[UIKerningPair" +
				" first" + firstGlyphCode +
				", second" + secondGlyphCode +
				", kerning" + kerning + "]";
	}
}

