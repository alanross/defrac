package com.adjazent.defrac.ui.text.processing;

import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyphUtils;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextComposerSingleLine implements IUITextComposer
{
	public UITextComposerSingleLine()
	{
	}

	public UITextLayout layoutText( LinkedList<UIGlyph> glyphs, LinkedList<UIGlyph> ellipsis, UIFont font, UITextFormat format, MRectangle maxSize )
	{
		LinkedList<UITextLine> lines = new LinkedList<UITextLine>();

		UITextLine line = UITextLine.build( glyphs, 0, format.tracking, Integer.MAX_VALUE );

		if( line.bounds.height > maxSize.height )
		{
			return new UITextLayout( lines, new MRectangle() ); //empty
		}

		if( line.bounds.width > maxSize.width )
		{
			double diff = Math.abs( line.bounds.width - maxSize.width );

			if( diff < glyphs.size() * ( font.getSize() * 0.1 ) )
			{
				line = UITextLine.build( glyphs, 0, format.tracking - ( diff / ( double ) glyphs.size() ), maxSize.width );
			}
			else
			{
				while( glyphs.size() > 0 )
				{
					if( line.bounds.width <= maxSize.width )
					{
						break;
					}

					int cutIndex = UIGlyphUtils.findLastIndexOf( line.glyphs, ' ' );

					cutIndex = Math.max( ( ( cutIndex != -1 ) ? cutIndex : ( glyphs.size() - 1 ) - ellipsis.size() ), 0 );

					while( glyphs.size() > cutIndex )
					{
						glyphs.removeLast();
					}

					glyphs.addAll( ellipsis );

					if( glyphs.size() <= ellipsis.size() )
					{
						return new UITextLayout( lines, new MRectangle() ); //empty
					}

					line = UITextLine.build( glyphs, 0, format.tracking, maxSize.width );
				}
			}
		}

		lines.addLast( line );

		return new UITextLayout( lines, line.bounds );
	}

	@Override
	public String toString()
	{
		return "[UITextLayouterSingleLine]";
	}
}

