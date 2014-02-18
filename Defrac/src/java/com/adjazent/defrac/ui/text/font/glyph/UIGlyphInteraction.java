package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.UITextUtil;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyphInteraction implements IUIGlyphInteraction
{
	public UIGlyphInteraction()
	{
	}

	@Override
	public int getCursorIndexForPoint( LinkedList<UIGlyph> glyphs, Point point )
	{
		int n = glyphs.size();

		UIGlyph glyph;

		for( int i = 0; i < n; ++i )
		{
			glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				Rectangle b = glyph.getSelectionRect();

				int x = ( int ) ( point.x - ( b.x + b.width * 0.5 ) );

				if( x < 0 )
				{
					return i - 1; // can result in -1;
				}
				else
				{
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public UIGlyph getGlyphUnderPoint( LinkedList<UIGlyph> glyphs, Point point )
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

	@Override
	public void getWordUnderPoint( LinkedList<UIGlyph> glyphs, Point point, UITextSelection selection )
	{
		int n = glyphs.size();

		selection.firstIndex = 0;
		selection.lastIndex = 0;

		for( int i = 0; i < n; ++i )
		{
			UIGlyph glyph = glyphs.get( i );

			if( glyph.containsPoint( point ) )
			{
				if( UITextUtil.isWordSeparator( glyph ) )
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
						if( UITextUtil.isWordSeparator( glyphs.get( min ) ) )
						{
							break;
						}

						selection.firstIndex = min;
					}

					for( int max = i; max < n; ++max )
					{
						if( UITextUtil.isWordSeparator( glyphs.get( max ) ) )
						{
							break;
						}

						selection.lastIndex = max;
					}
				}

				break;
			}
		}
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public String toString()
	{
		return "[UIGlyphInteraction]";
	}
}

