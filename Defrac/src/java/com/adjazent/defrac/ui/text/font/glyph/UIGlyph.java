package com.adjazent.defrac.ui.text.font.glyph;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ds.atlas.IAtlasElement;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.font.UIFont;
import defrac.display.Texture;
import defrac.display.TextureData;

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
	private char _character;

	// the font of which the glyph is part of
	private UIFont _font;

	// the source texture bounds of the glyph
	private MRectangle _sourceRect;

	// the selection hit test bounds of the glyph
	private MRectangle _selectionRect;

	// the real bounds of the glyph
	private MRectangle _bounds;

	private Texture _texture;

	// metrics
	private int _xAdvance;
	private MPoint _offset;
	private int _lineHeight;
	private int _base;
	private LinkedList<UIKerningPair> _kerning;

	public static int charToCode( char character )
	{
		return ( int ) character;
	}

	public static char codeToChar( int code )
	{
		return Character.toString( ( char ) code ).charAt( 0 );
	}


	public UIGlyph( UIFont font, int code, MRectangle sourceRect, MRectangle bounds, MPoint offset, int xAdvance, int lineHeight, int base, LinkedList<UIKerningPair> kerning )
	{
		_font = font;

		_code = code;
		_character = codeToChar( code );

		_sourceRect = sourceRect;
		_selectionRect = new MRectangle( 0, 0, xAdvance, lineHeight );
		_bounds = bounds;

		_texture = new Texture( _font.getTextureData(), ( float ) _sourceRect.x, ( float ) _sourceRect.y, ( float ) _sourceRect.width, ( float ) _sourceRect.height );

		_offset = offset;
		_xAdvance = xAdvance;
		_lineHeight = lineHeight;
		_base = base;
		_kerning = kerning;

		setTo( 0, 0 );
	}

	public UIGlyph clone()
	{
		return new UIGlyph( _font, _code, _sourceRect, _bounds.clone(), _offset, _xAdvance, _lineHeight, _base, _kerning );
	}

	public void setTo( int x, int y )
	{
		_selectionRect.x = x;
		_selectionRect.y = y;

		_bounds.x = getXOffset() + x;
		_bounds.y = getYOffset() + y;
	}

	public boolean containsPoint( MPoint point )
	{
		int px = ( int ) ( point.x - _selectionRect.x );
		int py = ( int ) ( point.y - _selectionRect.y );

		return !( px < 0 || px > _selectionRect.width || py < 0 || py > _selectionRect.height );
	}

	public void dispose()
	{
		_font = null;
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

	public char getCharacter()
	{
		return _character;
	}

	public MRectangle getSourceRect()
	{
		return _sourceRect;
	}

	public MRectangle getSelectionRect()
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
				" code:" + getCode() +
				", character:" + getCharacter() +
				", width:" + getWidth() +
				", height:" + getHeight() +
				", lineHeight:" + getLineHeight() +
				", base:" + getBase() +
				", xAdvance:" + getXAdvance() +
				", xOffset:" + getXOffset() +
				", yOffset:" + getYOffset() + "]";
	}
}

