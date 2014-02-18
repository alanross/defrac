package com.adjazent.defrac.ui.text.font.glyph;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyphLayouterMultiLine //extends UIGlyphLayouter implements IUIGlyphLayouter
{
//	public UIGlyphLayouterMultiLine()
//	{
//	}
//
//	public void layoutGlyphs( LinkedList<UIGlyph> glyphs, Rectangle size, UITextFormat format, int maxWidth, int maxHeight )
//	{
//		size.width = size.height = 0;
//
//		final int numGlyphs = glyphs.length;
//
//		if( numGlyphs == 0 || glyphs[ 0 ].lineHeight > maxHeight )
//		{
//			return;
//		}
//
//		int previousLineIndex = 0;
//		int index = 0;
//		int x = 0;
//		int y = 0;
//		UIGlyph glyph;
//
//		Rectangle lineSize = new Rectangle();
//
//		while( index < numGlyphs )
//		{
//			glyph = glyphs[ index ];
//
//			if( y + glyph.lineHeight > maxHeight )
//			{
//				y -= glyph.lineHeight;
//				size.height -= lineSize.height;
//
//				glyphs.splice( index, ( glyphs.length - index ) ); //TODO call truncate here
//				break;
//			}
//
//			previousLineIndex = index;
//
//			index = layoutLine( glyphs, format, lineSize, index, x, y, maxWidth );
//
//			if( index == numGlyphs )
//			{
//				break;
//			}
//
//			glyph = glyphs[ index ];
//
//			// next line must not start with a space/newline char
//			if( glyph.code != CHAR_SPACE && glyph.code != CHAR_NEW_LINE && index > 0 )
//			{
//				index--;
//			}
//
//			if( size.width < lineSize.width )
//			{
//				size.width = lineSize.width;
//			}
//
//			size.height += lineSize.height;
//
//			x = 0;
//			y += glyph.lineHeight;
//
//			index++;
//		}
//
//		if( size.width < lineSize.width )
//		{
//			size.width = lineSize.width;
//		}
//
//		size.height += lineSize.height;
//	}
//
//	@Override
//	public String toString()
//	{
//		return "[UIGlyphLayouterMultiLine]";
//	}
}

