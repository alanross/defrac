package com.adjazent.defrac.sandbox.apps.lite.core.data;

import com.adjazent.defrac.ui.surface.IUISkin;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneElement
{
	public final Rectangle dim;
	public final IUISkin skin;
	public final String id;

	public LiteSceneElement( String id, Rectangle dim, IUISkin skin )
	{
		this.id = id;
		this.dim = dim;
		this.skin = skin;
	}

	@Override
	public String toString()
	{
		return "[LiteSceneElement id:" + id + "]";
	}
}