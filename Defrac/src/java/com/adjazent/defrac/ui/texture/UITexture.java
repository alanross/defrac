package com.adjazent.defrac.ui.texture;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ds.atlas.IAtlasElement;
import com.adjazent.defrac.ui.utils.bitmap.UISlice9Grid;
import defrac.display.Texture;
import defrac.display.TextureData;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITexture implements IAtlasElement, IDisposable
{
	private String _id;
	private Rectangle _rect;
	private UISlice9Grid _sliceGrid;
	private TextureData _textureData;
	private Texture _texture;

	public UITexture( TextureData textureData, String id, Rectangle rect )
	{
		_id = id;
		_rect = rect;
		_sliceGrid = null;

		onAttachToAtlas( textureData );
	}

	public UITexture( TextureData textureData, String id, Rectangle rect, UISlice9Grid sliceGrid )
	{
		_id = id;
		_rect = rect;
		_sliceGrid = sliceGrid;

		onAttachToAtlas( textureData );
	}

	void onAttachToAtlas( TextureData textureData )
	{
		_textureData = textureData;

		_texture = new Texture( _textureData, _rect.x, _rect.y, _rect.width, _rect.height );
	}

	void onDetachAtlas( TextureData textureData )
	{
		_textureData = null;
		_texture = null;
	}

	@Override
	public void dispose()
	{
		_rect = null;
		_sliceGrid = null;
	}

	public Rectangle getSkinRect()
	{
		return _rect;
	}

	public Rectangle getTextureRect()
	{
		return new Rectangle( 0, 0, _textureData.width(), _textureData.height() );
	}

	public TextureData getTextureData()
	{
		return _textureData;
	}

	public Texture getTexture()
	{
		return _texture;
	}

	public UISlice9Grid getSliceGrid()
	{
		return _sliceGrid;
	}

	@Override
	public String getId()
	{
		return _id;
	}

	@Override
	public String toString()
	{
		return "[UITexture id:" + getId() + "]";
	}
}