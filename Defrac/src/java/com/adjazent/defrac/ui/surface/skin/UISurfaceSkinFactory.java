package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.texture.UITexture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISurfaceSkinFactory
{
	public static IUISkin create( UITexture texture )
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

	public static IUISkin create( int color )
	{
		UISurfaceColorSkin skin = new UISurfaceColorSkin();
		skin.init( color );

		return skin;
	}

	public static void release( IUISkin skin )
	{
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