package com.adjazent.defrac.sandbox.apps.lite.scene;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteState;
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

		UIToggleGroup group = new UIToggleGroup( _buttonScene1, _buttonScene2, _buttonScene3 );
		group.onSelect.add( this );

		LiteCore.state.addReceiver( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonScene1 )
			{
				LiteCore.state.selectSceneSet( 0 );
			}
			if( action.origin == _buttonScene2 )
			{
				LiteCore.state.selectSceneSet( 1 );
			}
			if( action.origin == _buttonScene3 )
			{
				LiteCore.state.selectSceneSet( 2 );
			}
		}
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteState.SELECT_SCENE_SET )
		{
			int index = LiteCore.state.sceneSet();

			if( index == 0 )
			{
				_buttonScene1.setSelected( true );
			}
			if( index == 1 )
			{
				_buttonScene2.setSelected( true );
			}
			if( index == 2 )
			{
				_buttonScene3.setSelected( true );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSetChoice]";
	}
}

