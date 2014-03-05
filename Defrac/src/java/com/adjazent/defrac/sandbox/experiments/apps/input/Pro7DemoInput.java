package com.adjazent.defrac.sandbox.experiments.apps.input;

import com.adjazent.defrac.sandbox.experiments.apps.dnd.IDragable;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7DemoInput extends UISurface implements IDragable
{
	public Pro7DemoInput( IUISkin demoSkin )
	{
		super( demoSkin );

		resizeTo( 180, 100 );
	}

	@Override
	public double getX()
	{
		return x();
	}

	@Override
	public double getY()
	{
		return y();
	}

	@Override
	public double getWidth()
	{
		return width();
	}

	@Override
	public double getHeight()
	{
		return height();
	}

	@Override
	public String toString()
	{
		return "[Pro7DemoInput]";
	}
}

