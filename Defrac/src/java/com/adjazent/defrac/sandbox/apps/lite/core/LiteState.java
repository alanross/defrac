package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteState extends Signals implements ISignalSource
{
	public static final int SELECT_INPUT_TYPE = Signals.createTypeID();
	public static final int SELECT_SCENE_SET = Signals.createTypeID();
	public static final int SELECT_SCENE_SLOT = Signals.createTypeID();

	public static final int STATE_INPUT_LIVE = 1;
	public static final int STATE_INPUT_VOD = 2;
	public static final int STATE_INPUT_IMAGES = 3;

	public static final int STATE_SCENE_A = 1;
	public static final int STATE_SCENE_B = 2;

	private int _inputType = -1;
	private int _sceneSet = -1;
	private int _sceneSlot = -1;

	public LiteState()
	{
	}

	public void selectInputType( int type )
	{
		_inputType = type;

		send( this, SELECT_INPUT_TYPE );
	}

	public void selectSceneSet( int index )
	{
		_sceneSet = index;

		send( this, SELECT_SCENE_SET );
	}

	public void selectSceneSlot( int type )
	{
		_sceneSlot = type;

		send( this, SELECT_SCENE_SLOT );
	}

	public int inputType()
	{
		return _inputType;
	}

	public int sceneSet()
	{
		return _sceneSet;
	}

	public int sceneSlot()
	{
		return _sceneSlot;
	}

	@Override
	public String toString()
	{
		return "[LiteState]";
	}
}