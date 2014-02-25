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
public final class UITextComposerMultiLine implements IUITextComposer
{
	public UITextComposerMultiLine()
	{
	}

	private LinkedList<UIGlyph> splice( LinkedList<UIGlyph> glyphs, int startIndex, int deleteCount )
	{
		LinkedList<UIGlyph> result = new LinkedList<UIGlyph>();

		int n = startIndex + deleteCount;

		for( int i = startIndex; i < n; i++ )
		{
			result.addLast( glyphs.remove( startIndex ) );
		}

		return result;
	}

	private UITextLine truncate( LinkedList<UIGlyph> glyphs, LinkedList<UIGlyph> ellipsis, double tracking, double offset, int maxWidth )
	{
		if( glyphs.get( glyphs.size() - 1 ).getCharacter() == ' ' )
		{
			glyphs.pollLast();
		}

		while( glyphs.size() > 0 )
		{
			glyphs.addAll( ellipsis );

			if( glyphs.size() <= ellipsis.size() )
			{
				return null;
			}

			UITextLine line = UITextLine.build( glyphs, offset, tracking, maxWidth );

			if( line.bounds.width <= maxWidth )
			{
				return line;
			}

			int cutIndex = Math.max( ( ( glyphs.size() - 1 ) - ellipsis.size() ), 0 );

			while( glyphs.size() > cutIndex )
			{
				glyphs.removeLast();
			}
		}

		return null;
	}

	public UITextLayout process( LinkedList<UIGlyph> glyphs, LinkedList<UIGlyph> ellipsis, UIFont font, UITextFormat format, MRectangle maxSize )
	{
		LinkedList<UITextLine> lines = new LinkedList<UITextLine>();
		UITextLine line;
		int offset = 0;
		int offsetStep = font.getLineHeight() + format.lineSpacing;
		boolean truncateLine = false;

		while( glyphs.size() > 0 )
		{
			line = UITextLine.build( glyphs, offset, format.tracking, maxSize.width );

			if( line.bounds.y + line.bounds.height > maxSize.height )
			{
				offset -= offsetStep;
				truncateLine = ( lines.size() > 0 );
				break;
			}
			else if( line.result == UITextLine.COMPLETE )
			{
				glyphs.clear();
			}
			else if( line.result == UITextLine.TRUNCATE_LINE_BREAK )
			{
				splice( glyphs, 0, line.glyphs.size() + 1 );
			}
			else if( line.result == UITextLine.TRUNCATE_WIDTH )
			{
				int cutIndex = UIGlyphUtils.findLastIndexOf( line.glyphs, ' ' );

				if( cutIndex == -1 )
				{
					if( lines.size() == 0 )
					{
						lines.addLast( line );
					}

					truncateLine = true;
					break;
				}

				LinkedList<UIGlyph> removed = splice( glyphs, 0, cutIndex + 1 );

				line = UITextLine.build( removed, offset, format.tracking, maxSize.width );
			}

			offset += offsetStep;

			lines.addLast( line );
		}

		if( truncateLine )
		{
			line = truncate( lines.pollLast().glyphs, ellipsis, format.tracking, offset, ( int ) maxSize.width );

			if( line != null )
			{
				lines.addLast( line );
			}
		}

		MRectangle bounds = new MRectangle();

		for( UITextLine l : lines )
		{
			if( bounds.width < l.bounds.width )
			{
				bounds.width = l.bounds.width;
			}

			bounds.height += offsetStep;
		}


		return new UITextLayout( lines, bounds );
	}

	@Override
	public String toString()
	{
		return "[UITextComposerMultiLine]";
	}
}

