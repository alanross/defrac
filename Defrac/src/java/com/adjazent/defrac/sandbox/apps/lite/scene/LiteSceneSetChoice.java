package com.adjazent.defrac.sandbox.apps.lite.scene;

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
public final class LiteSceneSetChoice extends UISurface implements IActionObserver, ISignalReceiver
{
	private UIToggleButton _buttonScene1;
	private UIToggleButton _buttonScene2;
	private UIToggleButton _buttonScene3;

	private UIToggleGroup _buttonGroup;

	public LiteSceneSetChoice()
	{
		super();

		resizeTo( 600, 23 );

		_buttonScene1 = LiteCore.ui.createToggleButton( "ButtonScene01Deselected", "ButtonScene01Selected" );
		_buttonScene1.resizeTo( 94, 23 );
		_buttonScene1.moveTo( 0, 0 );
		addChild( _buttonScene1 );

		_buttonScene2 = LiteCore.ui.createToggleButton( "ButtonScene02Deselected", "ButtonScene02Selected" );
		_buttonScene2.resizeTo( 94, 23 );
		_buttonScene2.moveTo( 102, 0 );
		addChild( _buttonScene2 );

		_buttonScene3 = LiteCore.ui.createToggleButton( "ButtonScene03Deselected", "ButtonScene03Selected" );
		_buttonScene3.resizeTo( 94, 23 );
		_buttonScene3.moveTo( 204, 0 );
		addChild( _buttonScene3 );

		_buttonGroup = new UIToggleGroup( _buttonScene1, _buttonScene2, _buttonScene3 );
		_buttonGroup.onSelect.add( this );


		LiteCore.data.addReceiver( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonScene1 )
			{
				LiteCore.data.selectSceneSet( 0 );
			}
			if( action.origin == _buttonScene2 )
			{
				LiteCore.data.selectSceneSet( 1 );
			}
			if( action.origin == _buttonScene3 )
			{
				LiteCore.data.selectSceneSet( 2 );
			}
		}
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteData.SELECT_SCENE_SET )
		{
			_buttonGroup.select( LiteCore.data.selectedSceneSetIndex() );
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSetChoice]";
	}
}

