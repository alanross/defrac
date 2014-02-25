package com.adjazent.defrac.ui.texture;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.ds.atlas.Atlas;
import com.adjazent.defrac.ds.atlas.IAtlasElement;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.utils.bitmap.UISlice9Grid;
import defrac.display.TextureData;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextureAtlas extends Atlas implements IAtlasElement
{
	private final TextureData _textureData;
	private final String _id;

	public UITextureAtlas( TextureData textureData, String id )
	{
		_textureData = textureData;
		_id = id;
	}

	public UITexture addTexture( String id, MRectangle rect )
	{
		UITexture element = new UITexture( _textureData, id, rect );

		if( addElement( element ) )
		{
			return element;
		}

		throw new GenericError( this, "There was a problem creating UITexture" );
	}

	public UITexture addTexture( String id, MRectangle rect, UISlice9Grid sliceGrid )
	{
		UITexture element = new UITexture( _textureData, id, rect, sliceGrid );

		if( addElement( element ) )
		{
			return element;
		}

		throw new GenericError( this, "There was a problem creating UITexture" );
	}

	public Boolean addTexture( UITexture element )
	{
		if( addElement( element ) )
		{
			element.onAttachToAtlas( _textureData );

			return true;
		}

		return false;
	}

	public Boolean removeTexture( UITexture element )
	{
		if( removeElement( element ) )
		{
			element.onDetachAtlas( _textureData );

			return true;
		}

		return false;
	}

	public UITexture getTexture( String id )
	{
		return ( UITexture ) getElement( id );
	}

	public boolean hasTexture( UITexture element )
	{
		return hasElement( element.getId() );
	}

	public boolean hasTexture( String id )
	{
		return hasElement( id );
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	public TextureData getTextureData()
	{
		return _textureData;
	}

	@Override
	public String getId()
	{
		return _id;
	}

	@Override
	public String toString()
	{
		return "[UITextureAtlas _id:" + getId() + "]";
	}
}

