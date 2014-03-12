package com.adjazent.defrac.sandbox.apps.lite.core.dnd;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteDnDGhost extends UISurface
{
	public LiteDnDGhost()
	{
		id = "GHOST";
	}

	public void onDragStart( float x, float y, float width, float height, IUISkin skin )
	{
		alpha( 0.6f );
		moveTo( x, y );
		resizeTo( width, height );
		setSkin( skin );
	}

	public void onDropTargetIn( boolean accepted )
	{
		alpha( ( accepted ? 1.0f : 0.6f ) );
	}

	public void onDropTargetOut()
	{
		alpha( 0.6f );
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		return null;
	}

	@Override
	public String toString()
	{
		return "[LiteDnDGhost]";
	}
}
