package com.adjazent.defrac.sandbox.apps.scenes;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.apps.Pro7SignalTypes;
import com.adjazent.defrac.sandbox.apps.dnd.IDropTarget;
import com.adjazent.defrac.sandbox.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7PanelScenes extends UISurface implements ISignalReceiver, ISignalSource, IActionObserver
{
	private Pro7SceneSelectorGroup _sceneEditButtons;

	private UISurface _background;
	private UISurface _arrow;

	private UIToggleGroup _groupSettings;

	private Pro7SceneSlot _sceneSlot1;
	private Pro7SceneSlot _sceneSlot2;

	private UIToggleButton _buttonSettingsA;
	private UIToggleButton _buttonSettingsB;

	public Pro7PanelScenes()
	{
		super();

		resizeTo( 790, 305 );

		_background = Pro7Theme.get().createSurface( "PanelGeneric", 0, 34, 788, 270 );
		addChild( _background );

		_arrow = Pro7Theme.get().createSurface( "PanelGenericArrow", 0, 0, 17, 12 );
		addChild( _arrow );

		_sceneEditButtons = new Pro7SceneSelectorGroup();
		_sceneEditButtons.moveTo( 8, 0 );
		addChild( _sceneEditButtons );

		_sceneSlot1 = new Pro7SceneSlot();
		_sceneSlot1.moveTo( 16, 16 );
		_background.addChild( _sceneSlot1 );

		_sceneSlot2 = new Pro7SceneSlot();
		_sceneSlot2.moveTo( 398, 16 );
		_background.addChild( _sceneSlot2 );

		_buttonSettingsA = Pro7Theme.get().createToggleButton( "ButtonSceneSettingsDeselected", "ButtonSceneSettingsSelected" );
		_buttonSettingsA.resizeTo( 21, 20 );
		_buttonSettingsA.moveTo( 184, 235 );
		_background.addChild( _buttonSettingsA );

		_buttonSettingsB = Pro7Theme.get().createToggleButton( "ButtonSceneSettingsDeselected", "ButtonSceneSettingsSelected" );
		_buttonSettingsB.resizeTo( 21, 20 );
		_buttonSettingsB.moveTo( 570, 235 );
		_background.addChild( _buttonSettingsB );

		_groupSettings = new UIToggleGroup( _buttonSettingsA, _buttonSettingsB );
		_groupSettings.onSelect.add( this );

		Signals.addReceiver( this );

		_buttonSettingsA.setSelected( true );
	}

	@Override
	public void onSignal( int signalType, ISignalSource signalSource )
	{
		switch( signalType )
		{
			case Pro7SignalTypes.SCENE_EDIT_1:
				_arrow.moveTo( 45, 25 );
				break;

			case Pro7SignalTypes.SCENE_EDIT_2:
				_arrow.moveTo( 145, 25 );
				break;

			case Pro7SignalTypes.SCENE_EDIT_3:
				_arrow.moveTo( 245, 25 );
				break;
		}
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonSettingsA )
			{
				Signals.send( Pro7SignalTypes.SCENE_EDIT_SETTINGS_A, this );
			}
			if( action.origin == _buttonSettingsB )
			{
				Signals.send( Pro7SignalTypes.SCENE_EDIT_SETTINGS_B, this );
			}
		}
	}

	public void selectScene1()
	{
		_sceneEditButtons.selectScene1();
	}

	public void selectScene2()
	{
		_sceneEditButtons.selectScene2();
	}

	public void selectScene3()
	{
		_sceneEditButtons.selectScene3();
	}

	public IDropTarget getSceneSlot1()
	{
		return _sceneSlot1;
	}

	public IDropTarget getSceneSlot2()
	{
		return _sceneSlot2;
	}

	@Override
	public String toString()
	{
		return "[Pro7PanelScenes]";
	}
}

