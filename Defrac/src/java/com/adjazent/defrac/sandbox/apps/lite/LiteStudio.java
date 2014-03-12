package com.adjazent.defrac.sandbox.apps.lite;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.apps.lite.core.*;
import com.adjazent.defrac.sandbox.apps.lite.input.LiteInputArea;
import com.adjazent.defrac.sandbox.apps.lite.input.LiteInputsChoice;
import com.adjazent.defrac.sandbox.apps.lite.playout.LitePlayoutArea;
import com.adjazent.defrac.sandbox.apps.lite.scene.LiteSceneSetChoice;
import com.adjazent.defrac.sandbox.apps.lite.scene.LiteSceneSetEditingArea;
import com.adjazent.defrac.sandbox.apps.lite.scene.LiteSceneSetSettingsArea;
import com.adjazent.defrac.ui.surface.UISurface;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteStudio extends Experiment implements ILiteSetupObserver
{
	private UISurface _background;
	private UISurface _logo;

	private LiteInputsChoice _inputChoices;
	private LiteInputArea _inputArea;

	private LiteSceneSetChoice _sceneSetChoices;
	private LiteSceneSetEditingArea _sceneSetDisplayArea;
	private LiteSceneSetSettingsArea _sceneSetSettingsArea;

	private LitePlayoutArea _playoutArea;

	public LiteStudio()
	{
	}

	@Override
	protected void onInit()
	{
		LiteCore.initialize( stage, this );
	}

	@Override
	public void onLiteSetupSuccess()
	{
		_background = LiteCore.ui.createSurface( "AppBackground" );

		_logo = LiteCore.ui.createSurface( "Logo", 0, 0, 154, 19 );

		_inputChoices = new LiteInputsChoice();
		_inputChoices.moveTo( 22, 12 );

		_sceneSetChoices = new LiteSceneSetChoice();
		_sceneSetChoices.moveTo( 18, 206 );

		_inputArea = new LiteInputArea();
		_inputArea.moveTo( 10, 60 );

		_sceneSetDisplayArea = new LiteSceneSetEditingArea();
		_sceneSetDisplayArea.moveTo( 10, 240 );

		_sceneSetSettingsArea = new LiteSceneSetSettingsArea();
		_sceneSetSettingsArea.moveTo( 9, 496 );

		_playoutArea = new LitePlayoutArea();
		_playoutArea.moveTo( 806, 60 );

		addChild( _background );
		addChild( _logo );
		addChild( _inputChoices );
		addChild( _inputArea );
		addChild( _sceneSetChoices );
		addChild( _sceneSetDisplayArea );
		addChild( _sceneSetSettingsArea );
		addChild( _playoutArea );

		onResize( stage.width(), stage.height() );

		LiteCore.state.selectInputType( LiteState.STATE_INPUT_LIVE );
		LiteCore.state.selectSceneSet( 0 );
		LiteCore.state.selectSceneSlot( LiteState.STATE_SCENE_A );
	}

	@Override
	public void onLiteSetupMessage( String message )
	{
		info( Context.DEFAULT, this, "LiteCore setup message:", message );
	}

	@Override
	public void onLiteSetupFailure( Error error )
	{
		info( Context.DEFAULT, this, "LiteCore setup failure:", error );
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
		return "[LiteStudio]";
	}
}