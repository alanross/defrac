package com.adjazent.defrac.sandbox.apps.playout;

import defrac.display.Image;
import defrac.display.Texture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7VideoArea extends Image
{
	public Pro7VideoArea( Texture videoTexture )
	{
		super( videoTexture );
	}

	@Override
	@javax.annotation.Nullable
	public defrac.display.event.UIEventTarget captureEventTarget( @javax.annotation.Nonnull defrac.geom.Point point )
	{
		return null;
	}

	@Override
	public String toString()
	{
		return "[Pro7VideoArea]";
	}
}