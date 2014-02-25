package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.core.utils.IDisposable;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UIKerningPair implements IDisposable
{
	private int _firstGlyphCode;
	private int _secondGlyphCode;
	private int _kerning;

	/**
	 * Creates a new instance of UIKerningPair.
	 */
	public UIKerningPair( int firstGlyphCode, int secondGlyphCode, int kerningAmount )
	{
		_firstGlyphCode = firstGlyphCode;
		_secondGlyphCode = secondGlyphCode;
		_kerning = kerningAmount;
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
	}

	/**
	 *
	 */
	public int getFirstGlyphCode()
	{
		return _firstGlyphCode;
	}

	/**
	 *
	 */
	public int getSecondGlyphCode()
	{
		return _secondGlyphCode;
	}

	/**
	 *
	 */
	public int getKerning()
	{
		return _kerning;
	}

	/**
	 * Creates and returns a string representation of the UIKerningPair object.
	 */
	@Override
	public String toString()
	{
		return "[UIKerningPair" +
				" first" + _firstGlyphCode +
				", second" + _secondGlyphCode +
				", kerning" + _kerning + "]";
	}
}

