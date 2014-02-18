package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.ui.surface.IUISurfaceSkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISurfaceSkinFactory
{
	public static IUISurfaceSkin create( UISurface surface, UITexture texture )
	{
		if( texture.getSliceGrid() == null )
		{
			return new UISurfaceSkin();
		}
		else
		{
			return new UISurfaceSkin9();
		}
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