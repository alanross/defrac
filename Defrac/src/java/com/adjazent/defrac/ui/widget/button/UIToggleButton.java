package com.adjazent.defrac.ui.widget.button;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
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
public final class UIToggleButton extends UISurface implements ISignalSource
{
	public final Action onClick = new Action( UIActionType.BUTTON_CLICK );
	public final Action onSelect = new Action( UIActionType.BUTTON_SELECT );

	private UITexture _skinDeselected;
	private UITexture _skinSelected;

	private boolean _selected;
	private boolean _lockSelected;

	public UIToggleButton( UITexture skinDeselected, UITexture skinSelected )
	{
		super( skinDeselected );

		_skinDeselected = skinDeselected;
		_skinSelected = skinSelected;

		_selected = false;
		_lockSelected = false;
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent event )
	{
		super.processEvent( event );

		if( event.type == UIEventType.ACTION_BEGIN && event.target == this )
		{
			setSelected( ( getLockIfSelected() ) ? true : !getSelected() );
		}
		if( event.type == UIEventType.ACTION_SINGLE && event.target == this )
		{
			onClick.send( this );
		}
	}

	public boolean getSelected()
	{
		return _selected;
	}

	public void setSelected( boolean value )
	{
		if( _selected == value )
		{
			return;
		}

		_selected = value;

		setTexture( ( _selected ? _skinSelected : _skinDeselected ) );

		onSelect.send( this );
	}

	public boolean getLockIfSelected()
	{
		return _lockSelected;
	}

	public void setLockIfSelected( boolean value )
	{
		_lockSelected = value;
	}

	@Override
	public String toString()
	{
		return "[UIToggleButton id:" + id + ", selected:" + _selected + "]";
	}
}

