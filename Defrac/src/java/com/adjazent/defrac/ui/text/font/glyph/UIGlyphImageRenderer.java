package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.ui.text.UITextFormat;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyphImageRenderer implements IUIGlyphRenderer
{
	private Layer _container;
	private LinkedList<Image> _images = new LinkedList<Image>();

	public UIGlyphImageRenderer( Layer container )
	{
		_container = container;
	}

	private void removeGlyphs()
	{
		while( !_images.isEmpty() )
		{
			_container.removeChild( _images.removeLast() );
		}
	}

	private void addGlyph( UIGlyph glyph )
	{
		Image image = new Image( glyph.getTexture() );

		image.moveTo( glyph.getX(), glyph.getY() );

		_images.addLast( image );

		_container.addChild( image );
	}

	public void renderGlyphs( LinkedList<UIGlyph> glyphs, UITextFormat format, Rectangle bounds )
	{
		removeGlyphs();

		if( bounds.width <= 0 || bounds.height <= 0 )
		{
			return;
		}

		for( UIGlyph glyph : glyphs )
		{
			addGlyph( glyph );
		}
	}

	public void dispose()
	{
	}

	@Override
	public String toString()
	{
		return "[UIGlyphImageRenderer]";
	}
}

