package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneSet;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteDataProvider extends Job
{
	public final LinkedList<LiteInputSource> inputLive = new LinkedList<LiteInputSource>();
	public final LinkedList<LiteInputSource> inputVOD = new LinkedList<LiteInputSource>();
	public final LinkedList<LiteInputSource> inputImage = new LinkedList<LiteInputSource>();
	
	public final LinkedList<LiteSceneSet> sceneSets = new LinkedList<LiteSceneSet>();

	public LiteDataProvider()
	{
	}

	@Override
	protected void onStart()
	{
		IUISkin skin;

		skin = UISkinFactory.create( LiteCore.video.createTextureTile( 400, 100, 180, 100 ) );
		inputLive.add( new LiteInputSource( "Live1", skin, skin.clone() ) );

		skin = UISkinFactory.create( LiteCore.video.createTextureTile( 200, 200, 180, 100 ) );
		inputLive.add( new LiteInputSource( "Live2", skin, skin.clone() ) );

		skin = UISkinFactory.create( LiteCore.video.createTextureTile( 0, 0, 180, 100 ) );
		inputLive.add( new LiteInputSource( "Live3", skin, skin.clone() ) );

		skin = UISkinFactory.create( LiteCore.video.createTextureTile( 200, 200, 180, 100 ) );
		inputVOD.add( new LiteInputSource( "VOD1", skin, skin.clone() ) );

		skin = UISkinFactory.create( LiteCore.video.createTextureTile( 400, 100, 180, 100 ) );
		inputVOD.add( new LiteInputSource( "VOD2", skin, skin.clone() ) );

		skin = LiteCore.ui.createSkin( "DemoInput1" );
		inputImage.add( new LiteInputSource( "IMG1", skin, skin.clone() ) );

		skin = LiteCore.ui.createSkin( "DemoInput2" );
		inputImage.add( new LiteInputSource( "IMG2", skin, skin.clone() ) );

		skin = LiteCore.ui.createSkin( "DemoInput3" );
		inputImage.add( new LiteInputSource( "IMG3", skin, skin.clone() ) );
		
		sceneSets.add( new LiteSceneSet( 1, new LiteScene( "1A" ), new LiteScene( "1B" ) ) );
		sceneSets.add( new LiteSceneSet( 2, new LiteScene( "2A" ), new LiteScene( "2B" ) ) );
		sceneSets.add( new LiteSceneSet( 3, new LiteScene( "3A" ), new LiteScene( "3B" ) ) );

		complete();
	}

	@Override
	public String toString()
	{
		return "[LiteDataProvider]";
	}
}