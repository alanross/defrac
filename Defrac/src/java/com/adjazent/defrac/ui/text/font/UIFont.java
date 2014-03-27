package com.adjazent.defrac.ui.text.font;

import com.adjazent.defrac.ds.atlas.Atlas;
import com.adjazent.defrac.ds.atlas.IAtlasElement;
import com.adjazent.defrac.ui.text.UICharCode;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import defrac.display.TextureData;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UIFont extends Atlas implements IAtlasElement
{
	private TextureData _textureData;
	private String _id;

	private String _face;
	private int _size;
	private int _lineHeight;
	private boolean _bold;
	private boolean _italic;

	public UIFont( TextureData textureData, String id, String face, int size, boolean bold, boolean italic, int lineHeight )
	{
		_textureData = textureData;
		_id = id;
		_face = face;
		_size = size;
		_bold = bold;
		_italic = italic;
		_lineHeight = lineHeight;
	}

	public boolean addGlyph( UIGlyph glyph )
	{
		return addElement( glyph );
	}

	public boolean removeGlyph( UIGlyph glyph )
	{
		return removeElement( glyph );
	}

	public UIGlyph getGlyph( char character )
	{
		return getGlyph( UICharCode.toCode( character ) );
	}

	public UIGlyph getGlyph( int charCode )
	{
		String id = Integer.toString( charCode );

		return ( hasElement( id ) ) ? ( ( UIGlyph ) getElement( id ) ).clone() : null;
	}

	public UIGlyph getSubstituteGlyph()
	{
		return getGlyph( '_' );
	}

	public boolean hasGlyph( char character )
	{
		return hasGlyph( UICharCode.toCode( character ) );
	}

	public boolean hasGlyph( int charCode )
	{
		return hasElement( Integer.toString( charCode ) );
	}

	@Override
	public void dispose()
	{
		super.dispose();

		_face = null;
	}

	public TextureData getTextureData()
	{
		return _textureData;
	}

	public String getFace()
	{
		return _face;
	}

	public int getSize()
	{
		return _size;
	}

	public boolean getBold()
	{
		return _bold;
	}

	public boolean getItalic()
	{
		return _italic;
	}

	public int getLineHeight()
	{
		return _lineHeight;
	}

	public String getId()
	{
		return _id;
	}

	@Override
	public String toString()
	{
		return "[UIFont" +
				" id:" + getId() +
				", fontId:" + getFace() +
				", size:" + getSize() +
				", bold:" + getBold() +
				", italic:" + getItalic() + "]";
	}
}