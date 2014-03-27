package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.ui.text.UICharCode;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyphUtils
{
	private static int[] _wordSeparators = new int[]{
			UICharCode.toCode( ' ' ),
			UICharCode.toCode( '.' ),
			UICharCode.toCode( ',' ),
			UICharCode.toCode( ';' ),
			UICharCode.toCode( '!' ),
			UICharCode.toCode( '?' )
	};

	public static int findLastIndexOf( LinkedList<UIGlyph> glyphs, char character )
	{
		int code = UICharCode.toCode( character );

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
		StringBuilder result = new StringBuilder();

		for( UIGlyph glyph : glyphs )
		{
			result.append( UICharCode.toChar( glyph.getCode() ) );
		}

		return result.toString();
	}

	public static boolean isWordSeparator( UIGlyph glyph )
	{
		int code = glyph.getCode();

		for( int c : _wordSeparators )
		{
			if( c == code )
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

