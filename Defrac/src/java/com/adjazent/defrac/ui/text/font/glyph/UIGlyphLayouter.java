package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.ui.text.UITextFormat;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UIGlyphLayouter
{
	protected static final int CHAR_SPACE = UIGlyph.charToCode( ' ' );
	protected static final int CHAR_NEW_LINE = UIGlyph.NEW_LINE;

	protected LinkedList<UIGlyph> ellipsis = new LinkedList<UIGlyph>();


	public UIGlyphLayouter()
	{
	}

	protected int getCropIndex( LinkedList<UIGlyph> glyphs, int index )
	{
		// crop until last word if more than one word, else crop char by char

		int i = index;

		while( --i > -1 )
		{
			if( glyphs.get( i ).getCode() == CHAR_SPACE )
			{
				return i;
			}
		}

		return index;
	}

	protected void truncate( LinkedList<UIGlyph> glyphs, UITextFormat format, Rectangle size, int startIndex, int cropIndex, int x, int y, int maxWidth )
	{
		// leave at least one char
		if( cropIndex < 1 )
		{
			cropIndex = 1;
		}

		//glyphs.splice( cropIndex, ( glyphs.size() - cropIndex ) );

		int j = cropIndex;
		int k = ( glyphs.size() - cropIndex );

		while( k > j )
		{
			glyphs.remove( k );
			--k;
		}

		int n = ellipsis.size();

		// append ellipsis
		for( int i = 0; i < n; ++i )
		{
			glyphs.addLast( ellipsis.get( i ) );
		}

		int e = glyphs.size() - ellipsis.size();

		// repeat layout procedure until line fits into maxWidth or there is only one char left
		while( layoutLine( glyphs, format, size, startIndex, x, y, maxWidth ) < glyphs.size() )
		{
			if( --e <= startIndex )
			{
				int o = startIndex;
				int p = ( glyphs.size() - startIndex );

				while( p > o )
				{
					glyphs.remove( p );
					--p;
				}


//				glyphs.splice( startIndex, ( glyphs.size() - startIndex ) );
				size.width = size.height = 0;
				break;
			}

			glyphs.remove( e );//splice( e, 1 );
		}
	}

	protected int layoutLine( LinkedList<UIGlyph> glyphs, UITextFormat format, Rectangle size, int start, int x, int y, int maxWidth )
	{
		int tracking = format.tracking;
		int n = glyphs.size();
		int i = start;
		UIGlyph glyph = null;

		size.x = x;
		size.y = y;

		while( i < n )
		{
			glyph = glyphs.get( i );

			if( x + glyph.getWidth() > maxWidth || glyph.getCode() == CHAR_NEW_LINE )
			{
				i = getCropIndex( glyphs, i );

				glyph = glyphs.get( i );

				size.width = glyph.getX() + glyph.getWidth();
				size.height = glyph.getLineHeight();

				return i;
			}

			glyph.setPosition( x, y );

			++i;

			if( i < n )
			{
				x += glyph.getXAdvance() + glyph.getKerning( glyphs.get( i ).getCode() ) + tracking;
			}
		}

		if( glyph != null )
		{
			size.width = glyph.getX() + glyph.getWidth();
			size.height = glyph.getLineHeight();
		}

		return n;
	}

	public void setEllipsis( LinkedList<UIGlyph> glyphs, UITextFormat format )
	{
		ellipsis = glyphs;
	}

	public void dispose()
	{
		ellipsis = null;
	}

	@Override
	public String toString()
	{
		return "[UIGlyphLayouter]";
	}
}

