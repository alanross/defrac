package com.adjazent.defrac.ui.widget.range;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;
import com.adjazent.defrac.ui.widget.UIActionType;
import defrac.display.event.UIActionEvent;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventType;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISlider extends UISurface// implements IUIEventListener
{
	public final Action changeAction = new Action( UIActionType.SLIDER_VALUE_CHANGED );
	public final Action completeAction = new Action( UIActionType.SLIDER_VALUE_COMPLETED );

	private UISurface _surfaceKnob;
	private UISurface _surfaceValue;

	private double _minValue = 0.0;
	private double _maxValue = 1.0;
	private double _value = 0.0;
	private double _stepSize = 0.0;

	private boolean _mouseDown;
	private double _dragStart = 0.0;
	private double _lastValue = 0.0;

	public UISlider( UITexture skinTrack, UITexture skinThumb, UITexture skinValue )
	{
		super( skinTrack );

		_surfaceKnob = new UISurface( skinThumb );
//		_surfaceKnob.scaleToSize( 17, 17 );
		_surfaceKnob.moveTo( 17, 0 );

		_surfaceValue = new UISurface( skinValue );
//		_surfaceValue.scaleToSize( 17, 17 );

		addChild( _surfaceValue );
		addChild( _surfaceKnob );

		valToPos( 0.0 );
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent event )
	{
		super.processEvent( event );

		if( ( event.type & UIEventType.ACTION ) != 0 )
		{
			switch( event.type )
			{
				case UIEventType.ACTION_BEGIN:
					onMouseDown( ( UIActionEvent ) event );
					break;

				case UIEventType.ACTION_MOVE:
					onMouseMove( ( UIActionEvent ) event );
					break;

				case UIEventType.ACTION_END:
					onMouseUp( ( UIActionEvent ) event );
					break;
			}
		}
	}

	private void onMouseDown( UIActionEvent event )
	{
		_mouseDown = true;

		_lastValue = _value;

		if( event.target == _surfaceKnob )
		{
//			_dragStart = _surfaceKnob.x() - event.pos.x;
//
//			if( !stage.hasEventListener( this ) )
//			{
//				stage.addEventListener( this );
//			}
		}
		else
		{
//			posToVal( globalToLocal( event.pos ).x );
		}
	}

	private void onMouseMove( UIActionEvent event )
	{
		//if( _mouseDown && event.currentTarget == stage )
//		{
//			posToVal( _dragStart + event.pos.x );
//		}
	}

	private void onMouseUp( UIActionEvent event )
	{
		_mouseDown = false;

//		if( stage.hasEventListener( this ) )
//		{
//			stage.removeEventListener( this );
//		}

		if( _lastValue != _value )
		{
			completeAction.send( this );
		}
	}

	private void posToVal( int pos )
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
		double size = width() - _surfaceKnob.width();

		double pos = MMath.round( ( v - _minValue ) * size / ( _maxValue - _minValue ) );

		_surfaceKnob.moveTo( ( float ) pos, 0 );

		pos += _surfaceKnob.width() * 0.5;

		if( pos <= 2 )
		{
			pos = 2;
		}

		_surfaceValue.resizeTo( ( int ) pos, ( int ) _surfaceValue.height() );
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

			changeAction.send( this );
		}
	}

	@Override
	public String toString()
	{
		return "[UISlider]";
	}
}

