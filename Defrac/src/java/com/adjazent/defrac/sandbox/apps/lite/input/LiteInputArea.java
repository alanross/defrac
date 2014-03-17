package com.adjazent.defrac.sandbox.apps.lite.input;

import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteData;
import com.adjazent.defrac.ui.surface.UISurface;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteInputArea extends UISurface implements ISignalReceiver
{
	private UISurface _arrow;

	private LiteInputList _inputsLive;
	private LiteInputList _inputsVOD;
	private LiteInputList _inputsImages;

	private LiteInputList _current;

	public LiteInputArea()
	{
		super( LiteCore.ui.createSkin( "PanelGeneric" ) );

		resizeTo( 788, 128 );

		_arrow = LiteCore.ui.createSurface( "PanelGenericArrow", 0, 0, 17, 12 );

		_inputsLive = new LiteInputList();
		_inputsLive.populate( LiteCore.data.inputLive );

		_inputsVOD = new LiteInputList();
		_inputsVOD.populate( LiteCore.data.inputVOD );

		_inputsImages = new LiteInputList();
		_inputsImages.populate( LiteCore.data.inputImage );

		addChild( _arrow );
		addChild( _inputsLive );
		addChild( _inputsVOD );
		addChild( _inputsImages );

		LiteCore.data.addReceiver( this );
	}

	private void activate( LiteInputList input )
	{
		if( _current != null )
		{
			_current.activate( false );
		}

		_current = input;
		_current.activate( true );
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteData.SELECT_INPUT )
		{
			if( LiteCore.data.inputType() == LiteData.INPUT_TYPE_LIVE )
			{
				_arrow.moveTo( 24, -9 );
				activate( _inputsLive );
			}
			else if( LiteCore.data.inputType() == LiteData.INPUT_TYPE_VOD )
			{
				_arrow.moveTo( 70, -9 );
				activate( _inputsVOD );
			}
			else if( LiteCore.data.inputType() == LiteData.INPUT_TYPE_IMAGES )
			{
				_arrow.moveTo( 116, -9 );
				activate( _inputsImages );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteInputArea]";
	}
}