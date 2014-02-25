package com.adjazent.defrac.ui.texture;

import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.ds.atlas.IAtlasElement;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.utils.bitmap.UISlice9Grid;
import defrac.display.Texture;
import defrac.display.TextureData;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITexture implements IAtlasElement, IDisposable
{
	private String _id;
	private MRectangle _rect;
	private UISlice9Grid _sliceGrid;
	private TextureData _textureData;
	private Texture _texture;

	public UITexture( TextureData textureData, String id, MRectangle rect )
	{
		_id = id;
		_rect = rect;
		_sliceGrid = null;

		onAttachToAtlas( textureData );
	}

	public UITexture( TextureData textureData, String id, MRectangle rect, UISlice9Grid sliceGrid )
	{
		_id = id;
		_rect = rect;
		_sliceGrid = sliceGrid;

		onAttachToAtlas( textureData );
	}

	void onAttachToAtlas( TextureData textureData )
	{
		_textureData = textureData;

		_texture = new Texture( _textureData, ( float ) _rect.x, ( float ) _rect.y, ( float ) _rect.width, ( float ) _rect.height );
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

	public MRectangle getSkinRect()
	{
		return _rect;
	}

	public MRectangle getTextureRect()
	{
		return new MRectangle( 0, 0, _textureData.width(), _textureData.height() );
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