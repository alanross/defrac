package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneElement;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneElementView extends UISurface
{
	public final float minWidth = 40;
	public final float minHeight = 40;

	public final LiteSceneElement model;

	public LiteSceneElementView( LiteSceneElement model )
	{
		super( model.skin );

		this.model = model;

		Rectangle d = model.getDimensions();

		super.moveTo( d.x, d.y );
		super.resizeTo( d.width, d.height );
	}

	public void setDimensions( Rectangle d )
	{
		super.moveTo( d.x, d.y );
		super.resizeTo( d.width, d.height );

		model.setDimensions( d.x, d.y, d.width, d.height );
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent uiEvent )
	{
		if( uiEvent.target == this )
		{
			if( uiEvent.type == UIEventType.FOCUS_IN )
			{
				model.selected( true );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneElementView]";
	}
}