package com.adjazent.defrac.sandbox.apps.model;

import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.sandbox.apps.model.input.Pro7InputList;
import com.adjazent.defrac.sandbox.apps.model.scene.Pro7SceneSetList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7Model
{
	public static final Pro7SceneSetList sceneSetList = new Pro7SceneSetList();

	public static final Pro7InputList inputLive = new Pro7InputList();
	public static final Pro7InputList inputVOD = new Pro7InputList();
	public static final Pro7InputList inputImage = new Pro7InputList();

	public static final Pro7VideoProvider videoProvider = new Pro7VideoProvider();

	private static Pro7Model _instance;

	public static void initialize()
	{
		if( _instance != null )
		{
			throw new SingletonError( "Pro7Model" );
		}

		_instance = new Pro7Model();
	}

	private Pro7Model()
	{

	}

	@Override
	public String toString()
	{
		return "[Pro7Model]";
	}
}