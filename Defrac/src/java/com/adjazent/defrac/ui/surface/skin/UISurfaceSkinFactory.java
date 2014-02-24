package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISurfaceSkin;
import com.adjazent.defrac.ui.texture.UITexture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISurfaceSkinFactory
{
	public static IUISurfaceSkin create( UITexture texture )
	{
		if( texture.getSliceGrid() == null )
		{
			UISurfaceTextureSkin skin = new UISurfaceTextureSkin();
			skin.init( texture );
			return skin;
		}
		else
		{
			UISurfaceTexture9Skin skin = new UISurfaceTexture9Skin();
			skin.init( texture );
			return skin;
		}
	}

	public static IUISurfaceSkin create( int color )
	{
		UISurfaceColorSkin skin = new UISurfaceColorSkin();
		skin.init( color );

		return skin;
	}

	private UISurfaceSkinFactory()
	{
	}

	@Override
	public String toString()
	{
		return "[UISurfaceSkinFactory]";
	}
}