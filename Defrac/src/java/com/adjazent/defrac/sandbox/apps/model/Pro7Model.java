package com.adjazent.defrac.sandbox.apps.model;

import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.sandbox.apps.model.input.Pro7InputImage;
import com.adjazent.defrac.sandbox.apps.model.input.Pro7InputList;
import com.adjazent.defrac.sandbox.apps.model.input.Pro7InputLive;
import com.adjazent.defrac.sandbox.apps.model.input.Pro7InputVOD;
import com.adjazent.defrac.sandbox.apps.model.scene.Pro7Scene;
import com.adjazent.defrac.sandbox.apps.model.scene.Pro7SceneSet;
import com.adjazent.defrac.sandbox.apps.model.scene.Pro7SceneSetList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7Model
{
	public static final Pro7SceneSetList sceneSetList = new Pro7SceneSetList();

	public static final Pro7InputList<Pro7InputLive> inputLive = new Pro7InputList<Pro7InputLive>();
	public static final Pro7InputList<Pro7InputVOD> inputVOD = new Pro7InputList<Pro7InputVOD>();
	public static final Pro7InputList<Pro7InputImage> inputImage = new Pro7InputList<Pro7InputImage>();

	public static final Pro7VideoProvider videoProvider = new Pro7VideoProvider();

	private static Pro7Model _instance;

	public static void initialize()
	{
		if( _instance != null )
		{
			throw new SingletonError( "Pro7Model" );
		}

		_instance = new Pro7Model();

		sceneSetList.add( new Pro7SceneSet( "1", new Pro7Scene( "1A" ), new Pro7Scene( "1B" ) ) );
		sceneSetList.add( new Pro7SceneSet( "2", new Pro7Scene( "2A" ), new Pro7Scene( "2B" ) ) );
		sceneSetList.add( new Pro7SceneSet( "3", new Pro7Scene( "3A" ), new Pro7Scene( "3B" ) ) );

		inputLive.add( new Pro7InputLive( "Live1" ) );
		inputLive.add( new Pro7InputLive( "Live2" ) );
		inputLive.add( new Pro7InputLive( "Live3" ) );

		inputVOD.add( new Pro7InputVOD( "VOD1" ) );
		inputVOD.add( new Pro7InputVOD( "VOD2" ) );

		inputImage.add( new Pro7InputImage( "IMG1" ) );
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