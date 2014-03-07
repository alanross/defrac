package com.adjazent.defrac.sandbox.apps.dnd;

import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class DnDGhost extends UISurface
{
	public DnDGhost()
	{
		super( UISkinFactory.create( 0xFFFF0000 ) );
		id = "GHOST";
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		return null;
	}

	@Override
	public String toString()
	{
		return "[DnDGhost]";
	}
}
