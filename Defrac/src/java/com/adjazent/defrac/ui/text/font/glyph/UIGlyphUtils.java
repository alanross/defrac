package com.adjazent.defrac.ui.text.font.glyph;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyphUtils
{
	private static LinkedList<Integer> _wordSeparatorChars;

	public static int findLastIndexOf( LinkedList<UIGlyph> glyphs, char character )
	{
		int code = UIGlyph.charToCode( character );
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

	public static boolean isWordSeparator( UIGlyph glyph )
	{
		if( _wordSeparatorChars == null )
		{
			_wordSeparatorChars = new LinkedList<Integer>();
			_wordSeparatorChars.addLast( UIGlyph.charToCode( ' ' ) );
			_wordSeparatorChars.addLast( UIGlyph.charToCode( '.' ) );
			_wordSeparatorChars.addLast( UIGlyph.charToCode( ',' ) );
			_wordSeparatorChars.addLast( UIGlyph.charToCode( ';' ) );
			_wordSeparatorChars.addLast( UIGlyph.charToCode( '!' ) );
			_wordSeparatorChars.addLast( UIGlyph.charToCode( '?' ) );
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

	private UIGlyphUtils()
	{
	}

	@Override
	public String toString()
	{
		return "[UIGlyphUtils]";
	}
}

