package com.adjazent.defrac.sandbox.apps;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.apps.dnd.DnDManager;
import com.adjazent.defrac.sandbox.apps.input.Pro7PanelInputTypeSelectorGroup;
import com.adjazent.defrac.sandbox.apps.input.Pro7PanelInputs;
import com.adjazent.defrac.sandbox.apps.model.Pro7Model;
import com.adjazent.defrac.sandbox.apps.playout.Pro7PanelPlayout;
import com.adjazent.defrac.sandbox.apps.scenes.Pro7PanelScenes;
import com.adjazent.defrac.sandbox.apps.settings.Pro7PanelSettings;
import com.adjazent.defrac.sandbox.apps.theme.IPro7ThemeObserver;
import com.adjazent.defrac.sandbox.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7Studio extends Experiment implements IPro7ThemeObserver
{
	private UISurface _background;

	private UISurface _logo;
	private Pro7PanelInputTypeSelectorGroup _inputGroup;
	private Pro7PanelInputs _panelInputs;
	private Pro7PanelScenes _panelScenes;
	private Pro7PanelSettings _panelSettings;
	private Pro7PanelPlayout _panelPlayout;

	public Pro7Studio()
	{
	}

	@Override
	protected void onInit()
	{
		Pro7Theme.initialize( stage, this );
	}

	@Override
	public void onPro7CoreUISetupSuccess()
	{
		Pro7Model.initialize();

		Signals.addType( Pro7SignalTypes.SCENE_EDIT_1 );
		Signals.addType( Pro7SignalTypes.SCENE_EDIT_2 );
		Signals.addType( Pro7SignalTypes.SCENE_EDIT_3 );
		Signals.addType( Pro7SignalTypes.INPUT_TYPE_LIVE );
		Signals.addType( Pro7SignalTypes.INPUT_TYPE_ON_DEMAND );
		Signals.addType( Pro7SignalTypes.INPUT_TYPE_IMAGE );
		Signals.addType( Pro7SignalTypes.INPUT_TYPE_USER );
		Signals.addType( Pro7SignalTypes.SCENE_EDIT_SETTINGS_A );
		Signals.addType( Pro7SignalTypes.SCENE_EDIT_SETTINGS_B );
		Signals.addType( Pro7SignalTypes.BUTTON_SELECT );
		Signals.addType( Pro7SignalTypes.BUTTON_DESELECT );
		Signals.addType( Pro7SignalTypes.BUTTON_CLICK );

		_background = Pro7Theme.get().createSurface( "AppBackground" );

		_logo = Pro7Theme.get().createSurface( "Logo", 0, 0, 154, 19 );

		_inputGroup = new Pro7PanelInputTypeSelectorGroup();
		_inputGroup.moveTo( 22, 12 );

		_panelInputs = new Pro7PanelInputs();
		_panelInputs.moveTo( 10, 60 );

		_panelScenes = new Pro7PanelScenes();
		_panelScenes.moveTo( 10, 206 );

		_panelSettings = new Pro7PanelSettings();
		_panelSettings.moveTo( 9, 496 );

		_panelPlayout = new Pro7PanelPlayout();
		_panelPlayout.moveTo( 806, 60 );

		addChild( _background );
		addChild( _logo );
		addChild( _inputGroup );
		addChild( _panelInputs );

		addChild( _panelScenes );
		addChild( _panelSettings );
		addChild( _panelPlayout );

		_inputGroup.selectLive();
		_panelScenes.selectScene1();

		DnDManager.register( _panelInputs.getDemoContent1(), _panelScenes.getSceneSlot1(), _panelScenes.getSceneSlot2() );
		DnDManager.register( _panelInputs.getDemoContent2(), _panelScenes.getSceneSlot1(), _panelScenes.getSceneSlot2() );
		DnDManager.register( _panelInputs.getDemoContent3(), _panelScenes.getSceneSlot1(), _panelScenes.getSceneSlot2() );

		onResize( stage.width(), stage.height() );
	}

	@Override
	public void onPro7CoreUISetupFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public void onResize( float width, float height )
	{
		_background.resizeTo( width, height );
		_logo.moveTo( ( int ) ( ( width - _logo.width() ) * 0.5 ), 20 );
	}

	@Override
	public String toString()
	{
		return "[Pro7Studio]";
	}
}