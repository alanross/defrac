package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.texture.UITexture;
import defrac.display.Texture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISkinFactory
{
	public static IUISkin create( UITexture texture )
	{
		if( texture.getSliceGrid() == null )
		{
			UITextureSkin skin = new UITextureSkin();
			skin.init( texture );
			return skin;
		}
		else
		{
			UITexture9Skin skin = new UITexture9Skin();
			skin.init( texture );
			return skin;
		}
	}

	public static IUISkin create( int color )
	{
		UIColorSkin skin = new UIColorSkin();
		skin.init( color );
		return skin;
	}

	public static IUISkin create( Texture texture )
	{
		UITextureSkin skin = new UITextureSkin();
		skin.init( texture );
		return skin;
	}

	public static void release( IUISkin skin )
	{
	}

	private UISkinFactory()
	{
	}

	@Override
	public String toString()
	{
		return "[UISkinFactory]";
	}
}