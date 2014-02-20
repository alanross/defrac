package com.adjazent.defrac.ui.text.processing;

import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import defrac.display.Image;
import defrac.display.Layer;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextRenderer extends Layer
{
	private LinkedList<Image> _images = new LinkedList<Image>();

	public UITextRenderer()
	{
	}

	private void clear()
	{
		while( !_images.isEmpty() )
		{
			removeChild( _images.removeLast() );
		}
	}

	private void addGlyph( UIGlyph glyph )
	{
		Image image = new Image( glyph.getTexture() );

		image.moveTo( glyph.getX(), glyph.getY() );

		_images.addLast( image );

		addChild( image );
	}

	public void process( UITextLayout block, UITextFormat format )
	{
		clear();

		if( block.bounds.width <= 0 || block.bounds.height <= 0 )
		{
			return;
		}

		int n = block.lines.size();

		for( int i = 0; i < n; ++i )
		{
			UITextLine line = block.lines.get( i );

			LinkedList<UIGlyph> glyphs = line.glyphs;

			int m = glyphs.size();

			for( int j = 0; j < m; ++j )
			{
				UIGlyph g = glyphs.get( j );

				addGlyph( g );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[UITextRenderer]";
	}
}

