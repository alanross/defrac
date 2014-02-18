package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.ui.text.UITextFormat;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyphLayouterSingleLine extends UIGlyphLayouter implements IUIGlyphLayouter
{
	public UIGlyphLayouterSingleLine()
	{
	}

	public void layoutGlyphs( LinkedList<UIGlyph> glyphs, Rectangle size, UITextFormat format, int maxWidth, int maxHeight )
	{
		size.width = size.height = 0;

		final int numGlyphs = glyphs.size();

		if( numGlyphs == 0 || glyphs.get( 0 ).getLineHeight() > maxHeight )
		{
			return;
		}

		int lineNumGlyphs = layoutLine( glyphs, format, size, 0, 0, 0, maxWidth );

		if( lineNumGlyphs < numGlyphs )
		{
			truncate( glyphs, format, size, 0, lineNumGlyphs, 0, 0, maxWidth );
		}
	}

	@Override
	public String toString()
	{
		return "[UIGlyphLayouterSingleLine]";
	}
}

