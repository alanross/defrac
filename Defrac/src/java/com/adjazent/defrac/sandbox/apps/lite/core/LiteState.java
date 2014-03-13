package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneSet;

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

	private int _currentInputType = -1;
	private int _currentSceneSetIndex = -1;
	private LiteSceneSet _currentSceneSet = null;
	private LiteScene _currentScene = null;
	private int _currentSceneSlotType = -1;

	public LiteState()
	{
	}

	public void selectInputType( int type )
	{
		_currentInputType = type;

		send( this, SELECT_INPUT_TYPE );
	}

	public void selectSceneSet( int index )
	{
		_currentSceneSetIndex = index;

		_currentSceneSet = LiteCore.data.sceneSets.get( index );

		send( this, SELECT_SCENE_SET );
	}

	public void selectSceneSlot( int type )
	{
		_currentSceneSlotType = type;

		if( type == STATE_SCENE_A )
		{
			_currentScene = _currentSceneSet.a;
		}
		else
		{
			_currentScene = _currentSceneSet.b;
		}

		send( this, SELECT_SCENE_SLOT );
	}

	public int inputType()
	{
		return _currentInputType;
	}

	public int sceneSetIndex()
	{
		return _currentSceneSetIndex;
	}

	public LiteSceneSet sceneSet()
	{
		return _currentSceneSet;
	}

	public int sceneSlotType()
	{
		return _currentSceneSlotType;
	}

	public LiteScene currentScene()
	{
		return _currentScene;
	}

	@Override
	public String toString()
	{
		return "[LiteState]";
	}
}