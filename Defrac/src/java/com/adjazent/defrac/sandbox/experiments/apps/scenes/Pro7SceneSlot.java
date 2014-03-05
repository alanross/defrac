package com.adjazent.defrac.sandbox.experiments.apps.scenes;

import com.adjazent.defrac.sandbox.experiments.apps.dnd.IDragable;
import com.adjazent.defrac.sandbox.experiments.apps.dnd.IDropTarget;
import com.adjazent.defrac.sandbox.experiments.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7SceneSlot extends UISurface implements IDropTarget
{
	private UISurface _outline;

	public Pro7SceneSlot()
	{
		super( Pro7Theme.get().createSkin( "AreaScene" ) );

		resizeTo( 375, 211 );

		_outline = Pro7Theme.get().createSurface( "ActiveOutline", 0, 0, ( int ) width(), ( int ) height() );
		_outline.visible( false );

		addChild( _outline );
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	public void onDragEnter( IDragable dragable )
	{
		_outline.visible( true );
	}

	public void onDragExit( IDragable dragable )
	{
		_outline.visible( false );
	}

	public void onDragDone( IDragable dragable )
	{
		_outline.visible( false );
	}

	@Override
	public String toString()
	{
		return "[Pro7SceneSlot]";
	}
}

