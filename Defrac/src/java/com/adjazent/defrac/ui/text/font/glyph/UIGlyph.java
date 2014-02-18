package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ds.atlas.IAtlasElement;
import com.adjazent.defrac.ui.text.font.UIFont;
import defrac.display.Texture;
import defrac.display.TextureData;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIGlyph implements IAtlasElement, IDisposable
{
	public static int NEW_LINE = 10;

	// id
	private int _code;
	private String _letter;

	// the atlas of which the glyph is part of
	private UIFont _font;

	// the source texture bounds of the glyph
	private Rectangle _sourceRect;

	// the selection hit test bounds of the glyph
	private Rectangle _selectionRect;

	// the real bounds of the glyph
	private Rectangle _bounds;

	private Texture _texture;

	// metrics
	private int _xAdvance;
	private Point _offset;
	private int _lineHeight;
	private int _base;
	private LinkedList<UIKerningPair> _kerning;

	public static int charToCode( char letter )
	{
		return ( int ) letter;
	}

	public static String codeToChar( int code )
	{
		return Character.toString( ( char ) code );
	}

	public UIGlyph( UIFont font, int code, Rectangle sourceRect, Rectangle bounds, Point offset, int xAdvance, int lineHeight, int base, LinkedList<UIKerningPair> kerning )
	{
		_font = font;

		_code = code;
		_letter = codeToChar( code );

		_sourceRect = sourceRect;
		_selectionRect = new Rectangle( 0, 0, xAdvance, lineHeight );
		_bounds = bounds;

		_texture = new Texture( _font.getTextureData(), _sourceRect.x, _sourceRect.y, _sourceRect.width, _sourceRect.height );

		_offset = offset;
		_xAdvance = xAdvance;
		_lineHeight = lineHeight;
		_base = base;
		_kerning = kerning;

		setPosition( 0, 0 );
	}

	public UIGlyph clone()
	{
		Rectangle b = new Rectangle( _bounds.x, _bounds.y, _bounds.width, _bounds.height ); //_bounds.clone()
		return new UIGlyph( _font, _code, _sourceRect, b, _offset, _xAdvance, _lineHeight, _base, _kerning );
	}

	public void setPosition( int x, int y )
	{
		_selectionRect.x = x;
		_selectionRect.y = y;

		_bounds.x = getXOffset() + x;
		_bounds.y = getYOffset() + y;
	}

	public boolean containsPoint( Point point )
	{
		int px = ( int ) ( point.x - _selectionRect.x );
		int py = ( int ) ( point.y - _selectionRect.y );

		return !( px < 0 || px > _selectionRect.width || py < 0 || py > _selectionRect.height );
	}

	public void dispose()
	{
		_font = null;
		_letter = null;
		_sourceRect = null;
		_selectionRect = null;
		_bounds = null;
		_offset = null;
	}

	public int getKerning( int code )
	{
		// amount of kerning between this glyph and the following one.
		// the code of the following glyph is passed into this function

		int n = _kerning.size();
		UIKerningPair pair;

		while( --n > -1 )
		{
			pair = _kerning.get( n );

			if( pair.getSecondGlyphCode() == code )
			{
				return pair.getKerning();
			}
		}

		return 0;
	}

	public TextureData getTextureData()
	{
		return _texture.textureData;
	}

	public Texture getTexture()
	{
		return _texture;
	}

	public int getCode()
	{
		return _code;
	}

	public String getLetter()
	{
		return _letter;
	}

	public Rectangle getSourceRect()
	{
		return _sourceRect;
	}

	public Rectangle getSelectionRect()
	{
		return _selectionRect;
	}

	public int getX()
	{
		return ( int ) _bounds.x;
	}

	public int getY()
	{
		return ( int ) _bounds.y;
	}

	public int getWidth()
	{
		return ( int ) _bounds.width;
	}

	public int getHeight()
	{
		return ( int ) _bounds.height;
	}

	public int getXAdvance()
	{
		return _xAdvance;
	}

	public int getXOffset()
	{
		return ( int ) _offset.x;
	}

	public int getYOffset()
	{
		return ( int ) _offset.y;
	}

	public int getLineHeight()
	{
		return _lineHeight;
	}

	public int getBase()
	{
		return _base;
	}

	@Override
	public String getId()
	{
		return Integer.toString( _code );
	}

	@Override
	public String toString()
	{
		return "[UIGlyph" +
				" id:" + getId() +
				", code:" + getCode() +
				", letter:" + getLetter() +
				", width:" + getWidth() +
				", height:" + getHeight() +
				", lineHeight:" + getLineHeight() +
				", base:" + getBase() +
				", xAdvance:" + getXAdvance() +
				", xOffset:" + getXOffset() +
				", yOffset:" + getYOffset() + "]";
	}
}

