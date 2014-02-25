package com.adjazent.defrac.ui.widget.button;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;
import com.adjazent.defrac.ui.widget.UIActionType;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIButton extends UISurface
{
	public final Action onClick = new Action( UIActionType.BUTTON_CLICK );

	public UIButton( UITexture skinNormal )
	{
		super( skinNormal );
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		if( containsPoint( point.x, point.y ) )
		{
			return this;
		}

		return null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent event )
	{
		super.processEvent( event );

		if( event.type == UIEventType.ACTION_SINGLE && event.target == this )
		{
			onClick.send( this );
		}
	}

	@Override
	public String toString()
	{
		return "[UIButton id:" + id + "]";
	}
}

