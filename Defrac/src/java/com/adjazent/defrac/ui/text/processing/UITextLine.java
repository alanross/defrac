package com.adjazent.defrac.ui.text.processing;

import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UICharCode;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextLine
{
	public static final int COMPLETE = 1;
	public static final int TRUNCATE_WIDTH = 2;
	public static final int TRUNCATE_LINE_BREAK = 3;

	public LinkedList<UIGlyph> glyphs;
	public MRectangle bounds;
	public int result = -1;

	public static UITextLine build( LinkedList<UIGlyph> glyphs, double offsetY, double tracking, double maxWidth )
	{
		int n = glyphs.size();
		int i = 0;
		UIGlyph glyph;

		double ox = 0;
		double oy = offsetY;

		LinkedList<UIGlyph> result = new LinkedList<UIGlyph>();
		MRectangle bounds = new MRectangle( ox, oy, 0, 0 );

		while( i < n )
		{
			glyph = glyphs.get( i );
			glyph.setTo( ( int ) ox, ( int ) oy );

			if( glyph.getCode() == UICharCode.LINE_FEED )
			{
				return new UITextLine( result, bounds, TRUNCATE_LINE_BREAK );
			}

			result.addLast( glyph );

			if( bounds.width < glyph.getX() + glyph.getWidth() )
			{
				bounds.width = glyph.getX() + glyph.getWidth();
			}

			if( bounds.height < glyph.getHeight() )
			{
				bounds.height = glyph.getHeight();
			}

			if( ox + glyph.getWidth() > maxWidth )
			{
				return new UITextLine( result, bounds, TRUNCATE_WIDTH );
			}

			if( n > ++i )
			{
				ox += glyph.getXAdvance() + glyph.kerning( glyphs.get( i ).getCode() ) + tracking;
			}
		}

		return new UITextLine( result, bounds, COMPLETE );
	}

	public UITextLine( LinkedList<UIGlyph> glyphs, MRectangle bounds, int result )
	{
		this.glyphs = glyphs;
		this.bounds = bounds;
		this.result = result;
	}

	@Override
	public String toString()
	{
		return "[UITextLine]";
	}
}

