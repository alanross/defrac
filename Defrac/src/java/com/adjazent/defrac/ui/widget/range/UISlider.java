package com.adjazent.defrac.ui.widget.range;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import defrac.display.event.*;
import defrac.geom.Point;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISlider extends UISurface implements UIProcessHook
{
	public final Action onValueChange = new Action( UIActionType.SLIDER_VALUE_CHANGED );
	public final Action onValueComplete = new Action( UIActionType.SLIDER_VALUE_COMPLETED );

	private UISurface _surfaceKnob;
	private UISurface _surfaceValue;

	private double _minValue = 0.0;
	private double _maxValue = 1.0;
	private double _value = 0.0;
	private double _stepSize = 0.0;

	private boolean _mouseDown = false;
	private double _dragStart = 0.0;
	private double _lastValue = 0.0;

	public UISlider( IUISkin skinTrack, IUISkin skinThumb, IUISkin skinValue )
	{
		super( skinTrack );

		_surfaceKnob = new UISurface( skinThumb );
		_surfaceValue = new UISurface( skinValue );

		addChild( _surfaceValue );
		addChild( _surfaceKnob );

		valToPos( 0.0 );
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );
		Point local2 = _surfaceKnob.globalToLocal( new Point( point.x, point.y ) );

		if( _surfaceKnob.containsPoint( local2.x, local2.y ) )
		{
			return _surfaceKnob;
		}

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	public void processEvent( @Nonnull final UIEvent event )
	{
		super.processEvent( event );

		if( ( event.type & UIEventType.ACTION ) != 0 )
		{
			switch( event.type )
			{
				case UIEventType.ACTION_BEGIN:
					onDragBegin( ( UIActionEvent ) event );
					break;

				case UIEventType.ACTION_MOVE:
					onDragUpdate( ( UIActionEvent ) event );
					break;

				case UIEventType.ACTION_END:
					onDragEnd( ( UIActionEvent ) event );
					break;
			}
		}
	}

	private void onDragBegin( UIActionEvent event )
	{
		_mouseDown = true;

		_lastValue = _value;

		if( event.target == _surfaceKnob )
		{
			_dragStart = _surfaceKnob.x() - event.pos.x;

			if( !getRoot().containsProcessHook( this ) )
			{
				getRoot().addProcessHook( this );
			}
		}
		else
		{
			Point p = this.globalToLocal( new Point( event.pos.x, event.pos.y ) );

			p.x -= _surfaceKnob.width() * 0.5;

			posToVal( p.x );
		}
	}

	private void onDragUpdate( UIActionEvent event )
	{
		if( _mouseDown && event.currentTarget == getRoot() )
		{
			posToVal( ( float ) ( _dragStart + event.pos.x ) );
		}
	}

	private void onDragEnd( UIActionEvent event )
	{
		_mouseDown = false;

		if( getRoot().containsProcessHook( this ) )
		{
			getRoot().removeProcessHook( this );
		}

		if( _lastValue != _value )
		{
			onValueComplete.send( this );
		}
	}

	private void posToVal( float pos )
	{
		final double size = width() - _surfaceKnob.width();

		if( size != 0 )
		{
			if( _stepSize != 0 )
			{
				this.setValue( MMath.round( ( ( pos * ( _maxValue - _minValue ) / size ) + _minValue ) / _stepSize ) * _stepSize );
			}
			else
			{
				this.setValue( ( pos * ( _maxValue - _minValue ) / size ) + _minValue );
			}
		}
	}

	private void valToPos( double v )
	{
		final double size = width() - _surfaceKnob.width();

		double pos = MMath.round( ( v - _minValue ) * size / ( _maxValue - _minValue ) );

		_surfaceKnob.moveTo( ( float ) pos, 0 );

		pos += _surfaceKnob.width() * 0.5;

		if( pos < 2 )
		{
			pos = 2;
		}

		_surfaceValue.resizeTo( ( float ) pos, _surfaceValue.height() );
	}

	public double getValue()
	{
		return _value;
	}

	public void setValue( double val )
	{
		if( val > _maxValue )
		{
			val = _maxValue;
		}
		else if( val < _minValue )
		{
			val = _minValue;
		}

		if( _value != val )
		{
			_value = val;

			valToPos( _value );

			onValueChange.send( this );
		}
	}

	public UISurface getKnobSurface()
	{
		return _surfaceKnob;
	}

	public UISurface getValueSurface()
	{
		return _surfaceValue;
	}

	@Override
	public String toString()
	{
		return "[UISlider id:" + id + ", value:" + _value + "]";
	}
}

