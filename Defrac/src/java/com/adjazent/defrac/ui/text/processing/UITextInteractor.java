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
		int n = glyphs.size();

		UIGlyph glyph;

		for( int i = 0; i < n; ++i )
		{
			glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				MRectangle b = glyph.getSelectionRect();

				double x = point.x - ( b.x + b.width * 0.5 );

				return ( x < 0 ) ? i - 1 : i; // can and should result in -1;
			}
		}

		return n - 1;
	}

	public int getCharIndexAtPoint( LinkedList<UIGlyph> glyphs, MPoint point )
	{
		int n = glyphs.size();

		for( int i = 0; i < n; ++i )
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

	public void selectCharAtPoint( LinkedList<UIGlyph> glyphs, MPoint point, UITextSelection selection )
	{
		int n = glyphs.size();

		selection.firstIndex = -1;
		selection.lastIndex = -1;

		for( int i = 0; i < n; ++i )
		{
			if( glyphs.get( i ).containsPoint( point ) )
			{
				selection.firstIndex = i;
				selection.lastIndex = i;
				return;
			}
		}
	}

	public void selectWordAtPoint( LinkedList<UIGlyph> glyphs, MPoint point, UITextSelection selection )
	{
		int n = glyphs.size();
		UIGlyph glyph;

		selection.setTo( -1, -1 );

		for( int i = 0; i < n; ++i )
		{
			glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				if( UIGlyphUtils.isWordSeparator( glyph ) )
				{
					selection.setTo( i, i );
				}
				else
				{
					selection.setTo( 0, n - 1 );

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

						selection.lastIndex = max;
					}
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

