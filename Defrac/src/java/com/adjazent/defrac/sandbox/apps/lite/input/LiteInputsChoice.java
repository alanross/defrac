package com.adjazent.defrac.sandbox.apps.lite.input;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteData;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteInputsChoice extends UISurface implements IActionObserver, ISignalReceiver
{
	private UIToggleButton _buttonLive;
	private UIToggleButton _buttonVOD;
	private UIToggleButton _buttonImage;

	public LiteInputsChoice()
	{
		super();

		resizeTo( 180, 48 );

		_buttonLive = LiteCore.ui.createToggleButton( "ButtonSourceLiveDeselected", "ButtonSourceLiveSelected" );
		_buttonLive.resizeTo( 45, 36 );
		_buttonLive.moveTo( 0, 0 );
		addChild( _buttonLive );

		_buttonVOD = LiteCore.ui.createToggleButton( "ButtonSourceOnDemandDeselected", "ButtonSourceOnDemandSelected" );
		_buttonVOD.resizeTo( 45, 36 );
		_buttonVOD.moveTo( 45, 0 );
		addChild( _buttonVOD );

		_buttonImage = LiteCore.ui.createToggleButton( "ButtonSourceImagesDeselected", "ButtonSourceImagesSelected" );
		_buttonImage.resizeTo( 45, 36 );
		_buttonImage.moveTo( 90, 0 );
		addChild( _buttonImage );

		UIToggleGroup group = new UIToggleGroup( _buttonLive, _buttonVOD, _buttonImage );
		group.onSelect.add( this );

		LiteCore.data.addReceiver( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonLive )
			{
				LiteCore.data.selectInput( LiteData.INPUT_TYPE_LIVE );
			}
			if( action.origin == _buttonVOD )
			{
				LiteCore.data.selectInput( LiteData.INPUT_TYPE_VOD );
			}
			if( action.origin == _buttonImage )
			{
				LiteCore.data.selectInput( LiteData.INPUT_TYPE_IMAGES );
			}
		}
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteData.SELECT_INPUT )
		{
			if( LiteCore.data.inputType() == LiteData.INPUT_TYPE_LIVE )
			{
				_buttonLive.selected( true );
			}
			else if( LiteCore.data.inputType() == LiteData.INPUT_TYPE_VOD )
			{
				_buttonVOD.selected( true );
			}
			else if( LiteCore.data.inputType() == LiteData.INPUT_TYPE_IMAGES )
			{
				_buttonImage.selected( true );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteInputsChoice]";
	}
}

