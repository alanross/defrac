package com.adjazent.defrac.ui.text.processing;


import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyphUtils;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextInteractor
{
	public UITextInteractor()
	{
	}

	public int getCaretIndexAtPoint( LinkedList<UIGlyph> glyphs, MPoint point )
	{
		for( int i = 0; i < glyphs.size(); ++i )
		{
			UIGlyph glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				MRectangle b = glyph.getSelectionRect();

				double x = point.x - ( b.x + b.width * 0.5 );

				return ( x < 0 ) ? i : i + 1;
			}
		}

		return -1;
	}

	public int getCharIndexAtPoint( LinkedList<UIGlyph> glyphs, MPoint point )
	{
		for( int i = 0; i < glyphs.size(); ++i )
		{
			if( glyphs.get( i ).containsPoint( point ) )
			{
				return i;
			}
		}

		return -1;
	}

	public UIGlyph getGlyphAtPoint( LinkedList<UIGlyph> glyphs, MPoint point )
	{
		for( UIGlyph glyph : glyphs )
		{
			if( glyph.containsPoint( point ) )
			{
				return glyph;
			}
		}

		return null;
	}

	public void selectChars( LinkedList<UIGlyph> glyphs, MPoint p0, MPoint p1, UITextSelection selection )
	{
		int i0;
		int i1;

		if( p0.x >= p1.x ) //negative selection
		{
			i0 = getCaretIndexAtPoint( glyphs, p1 );
			i1 = getCaretIndexAtPoint( glyphs, p0 );
		}
		else //positive selection
		{
			i0 = getCaretIndexAtPoint( glyphs, p0 );
			i1 = getCaretIndexAtPoint( glyphs, p1 );
		}

		if( i0 == i1 ) // same char
		{
			i0 = i1 = -1;
		}

		if( i0 > -1 && i1 == -1 ) // fill till end
		{
			i1 = glyphs.size();
		}

		if( i0 == -1 && i1 > -1 ) // fill till start
		{
			i0 = 0;
		}

		selection.setTo( i0, i1 );
	}

	public void selectWord( LinkedList<UIGlyph> glyphs, MPoint point, UITextSelection selection )
	{
		int n = glyphs.size();

		selection.setTo( -1, -1 );

		for( int i = 0; i < n; ++i )
		{
			UIGlyph glyph = glyphs.get( i );

			if( !glyph.containsPoint( point ) )
			{
				continue;
			}

			if( UIGlyphUtils.isWordSeparator( glyph ) )
			{
				selection.setTo( i, i );
			}
			else
			{
				selection.setTo( 0, n );

				for( int min = i; min > -1; --min )
				{
					if( UIGlyphUtils.isWordSeparator( glyphs.get( min ) ) )
					{
						break;
					}

					selection.firstIndex = min;
				}

				for( int max = i; max < n; ++max )
				{
					if( UIGlyphUtils.isWordSeparator( glyphs.get( max ) ) )
					{
						break;
					}

					selection.lastIndex = max + 1;
				}
			}
		}
	}

	@Override
	public String toString()
	{
		return "[UITextInteractor]";
	}
}

