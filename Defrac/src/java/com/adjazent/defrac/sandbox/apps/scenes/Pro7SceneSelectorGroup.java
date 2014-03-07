package com.adjazent.defrac.sandbox.apps.scenes;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.apps.Pro7SignalTypes;
import com.adjazent.defrac.sandbox.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7SceneSelectorGroup extends UISurface implements ISignalSource, IActionObserver
{
	private UIToggleGroup _groupScenes;

	private UIToggleButton _buttonScene1;
	private UIToggleButton _buttonScene2;
	private UIToggleButton _buttonScene3;

	public Pro7SceneSelectorGroup()
	{
		super();

		resizeTo( 600, 23 );

		_buttonScene1 = Pro7Theme.get().createToggleButton( "ButtonScene01Deselected", "ButtonScene01Selected" );
		_buttonScene1.resizeTo( 94, 23 );
		_buttonScene1.moveTo( 0, 0 );
		addChild( _buttonScene1 );

		_buttonScene2 = Pro7Theme.get().createToggleButton( "ButtonScene02Deselected", "ButtonScene02Selected" );
		_buttonScene2.resizeTo( 94, 23 );
		_buttonScene2.moveTo( 102, 0 );
		addChild( _buttonScene2 );

		_buttonScene3 = Pro7Theme.get().createToggleButton( "ButtonScene03Deselected", "ButtonScene03Selected" );
		_buttonScene3.resizeTo( 94, 23 );
		_buttonScene3.moveTo( 204, 0 );
		addChild( _buttonScene3 );

		_groupScenes = new UIToggleGroup( _buttonScene1, _buttonScene2, _buttonScene3 );
		_groupScenes.onSelect.add( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonScene1 )
			{
				Signals.send( Pro7SignalTypes.SCENE_EDIT_1, this );
			}
			if( action.origin == _buttonScene2 )
			{
				Signals.send( Pro7SignalTypes.SCENE_EDIT_2, this );
			}
			if( action.origin == _buttonScene3 )
			{
				Signals.send( Pro7SignalTypes.SCENE_EDIT_3, this );
			}
		}
	}

	public void selectScene1()
	{
		_buttonScene1.setSelected( true );
	}

	public void selectScene2()
	{
		_buttonScene2.setSelected( true );
	}

	public void selectScene3()
	{
		_buttonScene3.setSelected( true );
	}

	@Override
	public String toString()
	{
		return "[Pro7SceneSelectorGroup]";
	}
}

