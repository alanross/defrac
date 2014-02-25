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
public final class UITextRenderer extends Layer implements IUITextRenderer
{
	private LinkedList<Image> _images = new LinkedList<Image>();

	public UITextRenderer()
	{
	}

	public void process( UITextLayout block, UITextFormat format )
	{
		while( !_images.isEmpty() )
		{
			removeChild( _images.removeLast() );
		}

		if( block.bounds.width <= 0 || block.bounds.height <= 0 )
		{
			return;
		}

		int n = block.lines.size();

		for( int i = 0; i < n; ++i )
		{
			UITextLine line = block.lines.get( i );

			LinkedList<UIGlyph> glyphs = line.glyphs;

			for( UIGlyph g : glyphs )
			{
				Image image = new Image( g.getTexture() );

				image.moveTo( g.getX(), g.getY() );

				_images.addLast( image );

				addChild( image );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[UITextRenderer]";
	}
}

