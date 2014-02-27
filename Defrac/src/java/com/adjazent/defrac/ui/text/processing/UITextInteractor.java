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

	public int getCursorIndexForPoint( LinkedList<UIGlyph> glyphs, MPoint point )
	{
		int n = glyphs.size();

		UIGlyph glyph;

		for( int i = 0; i < n; ++i )
		{
			glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				MRectangle b = glyph.getSelectionRect();

				int x = ( int ) ( point.x - ( b.x + b.width * 0.5 ) );

				if( x < 0 )
				{
					return i - 1; // can and should result in -1;
				}
				else
				{
					return i;
				}
			}
		}

		return -1;
	}

	public UIGlyph getGlyphUnderPoint( LinkedList<UIGlyph> glyphs, MPoint point )
	{
		int n = glyphs.size();

		for( int i = 0; i < n; ++i )
		{
			if( glyphs.get( i ).containsPoint( point ) )
			{
				return glyphs.get( i );
			}
		}

		return null;
	}

	public void getCharUnderPoint( LinkedList<UIGlyph> glyphs, MPoint point, UITextSelection selection )
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

	public void getWordUnderPoint( LinkedList<UIGlyph> glyphs, MPoint point, UITextSelection selection )
	{
		int n = glyphs.size();
		UIGlyph glyph;

		selection.firstIndex = -1;
		selection.lastIndex = -1;

		for( int i = 0; i < n; ++i )
		{
			glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				if( UIGlyphUtils.isWordSeparator( glyph ) )
				{
					selection.firstIndex = i;
					selection.lastIndex = i;
				}
				else
				{
					selection.firstIndex = 0;
					selection.lastIndex = n - 1;

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

