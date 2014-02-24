package com.adjazent.defrac.ui.widget.pro7;

import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;
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
		// TODO: globalToLocal does not work yet
		Point p = new Point( point.x, point.y );

		//TODO: bounds should not contain x and y pos of surface
		if( bounds.contains( p.x, p.y ) )
		{
			return this;
		}

		return null;
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
			Signals.send( UISignalTypes.BUTTON_CLICK, this );
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

		setTexture( ( _selected ? _skinDeselected : _skinSelected ) );

		Signals.send( ( _selected ) ? UISignalTypes.BUTTON_SELECT : UISignalTypes.BUTTON_DESELECT, this );
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
		return "[UIToggleButton]";
	}
}
