package com.adjazent.defrac.ui.text;

import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextUtil
{
	private static LinkedList<Integer> _wordSeparatorChars;

	/**
	 * Find and return index of last occurrence of given letter in glyph array.
	 * If letter is found its index is returned, otherwise -1 is returned.
	 */
	public static int findLastIndexOf( LinkedList<UIGlyph> glyphs, char letter )
	{
		int code = UIGlyph.charToCode( letter );
		int n = glyphs.size();

		while( --n > -1 )
		{
			if( glyphs.get( n ).getCode() == code )
			{
				return n;
			}
		}

		return -1;
	}

	/**
	 * Return a string representation of glyph list.
	 */
	public static String glyphsToString( LinkedList<UIGlyph> glyphs )
	{
		final int n = glyphs.size();
		String str = "";

		for( int i = 0; i < n; ++i )
		{
			str += UIGlyph.codeToChar( glyphs.get( i ).getCode() );
		}

		return str;
	}

	/**
	 * Check if given glyph (a)acts word separator, such as "," or " "
	 */
	public static boolean isWordSeparator( UIGlyph glyph )
	{
		if( _wordSeparatorChars == null )
		{
			_wordSeparatorChars = new LinkedList<Integer>();
			_wordSeparatorChars.push( UIGlyph.charToCode( ' ' ) );
			_wordSeparatorChars.push( UIGlyph.charToCode( '.' ) );
			_wordSeparatorChars.push( UIGlyph.charToCode( ',' ) );
			_wordSeparatorChars.push( UIGlyph.charToCode( ';' ) );
			_wordSeparatorChars.push( UIGlyph.charToCode( '!' ) );
			_wordSeparatorChars.push( UIGlyph.charToCode( '?' ) );
		}

		int code = glyph.getCode();

		final int n = _wordSeparatorChars.size();

		for( int i = 0; i < n; ++i )
		{
			if( _wordSeparatorChars.get( i ) == code )
			{
				return true;
			}
		}

		return false;
	}

//	/**
//	 * Paint the glyph with its current bounds onto given bitmap data.
//	 */
//	public static void paintGlyph( UIGlyph glyph, BitmapData bmd )
//	{
//		if( glyph.width <= 0 || glyph.height <= 0 )
//		{
//			return;
//		}
//
//		BitmapData bg = new BitmapData( glyph.width, glyph.height, true, 0x55FF0000 );
//
//		bmd.copyPixels( bg, bg.rect, glyph.topLeft, null, null, true );
//		bmd.copyPixels( glyph.atlasTexture.bitmapData, glyph.sourceRect, glyph.topLeft, null, null, true );
//	}

	/**
	 * Creates a new instance of UITextUtil.
	 */
	private UITextUtil()
	{
	}

	/**
	 * Creates and returns a string representation of the UITextUtil object.
	 */
	@Override
	public String toString()
	{
		return "[UITextUtil]";
	}
}

