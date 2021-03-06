package com.adjazent.defrac.sandbox.apps.lite.scene;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteData;
import com.adjazent.defrac.sandbox.apps.lite.scene.editor.LiteSceneEditor;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneSetEditingArea extends UISurface implements IActionObserver, ISignalReceiver
{
	private UISurface _arrow;

	private LiteSceneEditor _sceneEditorA;
	private LiteSceneEditor _sceneEditorB;

	private UIToggleButton _buttonSettingsA;
	private UIToggleButton _buttonSettingsB;

	public LiteSceneSetEditingArea()
	{
		super( LiteCore.ui.createSkin( "PanelGeneric" ) );

		resizeTo( 788, 270 );

		_arrow = LiteCore.ui.createSurface( "PanelGenericArrow", 0, 0, 17, 12 );

		_sceneEditorA = new LiteSceneEditor( LiteCore.ui.createSkin( "AreaScene" ) );
		_sceneEditorA.moveTo( 16, 16 );
		_sceneEditorA.resizeTo( 375, 211 );

		_sceneEditorB = new LiteSceneEditor( LiteCore.ui.createSkin( "AreaScene" ) );
		_sceneEditorB.moveTo( 398, 16 );
		_sceneEditorB.resizeTo( 375, 211 );

		_buttonSettingsA = LiteCore.ui.createToggleButton( "ButtonSceneSettingsDeselected", "ButtonSceneSettingsSelected" );
		_buttonSettingsA.resizeTo( 21, 20 );
		_buttonSettingsA.moveTo( 184, 235 );

		_buttonSettingsB = LiteCore.ui.createToggleButton( "ButtonSceneSettingsDeselected", "ButtonSceneSettingsSelected" );
		_buttonSettingsB.resizeTo( 21, 20 );
		_buttonSettingsB.moveTo( 570, 235 );

		UIToggleGroup group = new UIToggleGroup( _buttonSettingsA, _buttonSettingsB );
		group.onSelect.add( this );

		addChild( _arrow );
		addChild( _sceneEditorA );
		addChild( _sceneEditorB );
		addChild( _buttonSettingsA );
		addChild( _buttonSettingsB );

		LiteCore.data.addReceiver( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.BUTTON_SELECT )
		{
			if( action.origin == _buttonSettingsA )
			{
				LiteCore.data.selectSceneSlot( LiteData.SCENE_SLOT_A );
			}
			if( action.origin == _buttonSettingsB )
			{
				LiteCore.data.selectSceneSlot( LiteData.SCENE_SLOT_B );
			}
		}
	}

	@Override
	public void onSignal( ISignalSource signalSource, int signalType )
	{
		if( signalType == LiteData.SELECT_SCENE_SET )
		{
			int index = LiteCore.data.selectedSceneSetIndex();

			if( index == 0 )
			{
				_arrow.moveTo( 45, -9 );
			}
			if( index == 1 )
			{
				_arrow.moveTo( 145, -9 );
			}
			if( index == 2 )
			{
				_arrow.moveTo( 245, -9 );
			}

			int type = LiteCore.data.selectedSceneSlotId();

			if( type == LiteData.SCENE_SLOT_A )
			{
				_buttonSettingsA.selected( true );
				_sceneEditorA.enabled( true );
				_sceneEditorB.enabled( false );
			}
			if( type == LiteData.SCENE_SLOT_B )
			{
				_buttonSettingsB.selected( true );
				_sceneEditorA.enabled( false );
				_sceneEditorB.enabled( true );
			}

			_sceneEditorA.populate( LiteCore.data.selectedSceneSet().a );
			_sceneEditorB.populate( LiteCore.data.selectedSceneSet().b );
		}
		if( signalType == LiteData.SELECT_SCENE_SLOT )
		{
			int type = LiteCore.data.selectedSceneSlotId();

			if( type == LiteData.SCENE_SLOT_A )
			{
				_buttonSettingsA.selected( true );
				_sceneEditorA.enabled( true );
				_sceneEditorB.enabled( false );
			}
			if( type == LiteData.SCENE_SLOT_B )
			{
				_buttonSettingsB.selected( true );
				_sceneEditorA.enabled( false );
				_sceneEditorB.enabled( true );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSetEditingArea]";
	}
}

