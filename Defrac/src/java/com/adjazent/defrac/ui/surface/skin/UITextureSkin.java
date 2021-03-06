package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;
import defrac.display.Image;
import defrac.display.Texture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextureSkin implements IUISkin
{
	private Image _image = new Image();

	public UITextureSkin()
	{
	}

	public void init( UITexture texture )
	{
		_image.texture( texture.getTexture() );
	}

	public void init( Texture texture )
	{
		_image.texture( texture );
	}

	@Override
	public void attach( UISurface surface )
	{
		surface.skinLayer.addChild( _image );
	}

	@Override
	public void detach( UISurface surface )
	{
		surface.skinLayer.removeChild( _image );
	}

	@Override
	public void resizeTo( float width, float height )
	{
		_image.scaleToSize( width, height );
	}

	@Override
	public float getDefaultWidth()
	{
		return _image.texture().width;
	}

	@Override
	public float getDefaultHeight()
	{
		return _image.texture().height;
	}

	@Override
	public IUISkin clone()
	{
		UITextureSkin skin = new UITextureSkin();

		skin.init( _image.texture() );

		return skin;
	}

	@Override
	public String toString()
	{
		return "[UITextureSkin]";
	}
}