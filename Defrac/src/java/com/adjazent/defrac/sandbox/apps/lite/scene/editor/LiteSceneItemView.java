package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneItem;
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
public final class LiteSceneItemView extends UISurface
{
	public final LiteSceneItem model;

	public LiteSceneItemView( LiteSceneItem model )
	{
		super( model.skin.clone() );

		this.model = model;

		update( model.dimensions(), model.alpha() );
	}

	public void update( Rectangle d, float alpha )
	{
		moveTo( d.x, d.y );
		resizeTo( d.width, d.height );
		alpha( alpha );
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
		return "[LiteSceneItemView]";
	}
}