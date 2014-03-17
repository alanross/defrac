package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteInputSource;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneSet;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import com.adjazent.defrac.ui.widget.video.UIVideoTexture;
import defrac.display.Texture;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteData extends Signals implements ISignalSource
{
	public static final int SELECT_INPUT = Signals.createTypeID();
	public static final int SELECT_SCENE_SET = Signals.createTypeID();
	public static final int SELECT_SCENE_SLOT = Signals.createTypeID();

	public static final int INPUT_TYPE_LIVE = 1;
	public static final int INPUT_TYPE_VOD = 2;
	public static final int INPUT_TYPE_IMAGES = 3;

	public static final int SCENE_SLOT_A = 1;
	public static final int SCENE_SLOT_B = 2;

	public final LinkedList<LiteInputSource> inputLive = new LinkedList<LiteInputSource>();
	public final LinkedList<LiteInputSource> inputVOD = new LinkedList<LiteInputSource>();
	public final LinkedList<LiteInputSource> inputImage = new LinkedList<LiteInputSource>();

	public final LinkedList<LiteSceneSet> sceneSets = new LinkedList<LiteSceneSet>();

	public Texture output;

	private int _currentInputType = -1;
	private int _currentSceneSetIndex = -1;
	private int _currentSceneSlotId = -1;

	private UIVideoTexture videoTexture;

	public LiteData()
	{
	}

	public void setup( String videoSource, int videoWidth, int videoHeight )
	{
		videoTexture = new UIVideoTexture( videoWidth, videoHeight, videoSource );

		IUISkin skin;

		skin = UISkinFactory.create( videoTexture.createTextureTile( 400, 100, 180, 100 ) );
		inputLive.add( new LiteInputSource( "Live1", skin, skin.clone() ) );

		skin = UISkinFactory.create( videoTexture.createTextureTile( 200, 200, 180, 100 ) );
		inputLive.add( new LiteInputSource( "Live2", skin, skin.clone() ) );

		skin = UISkinFactory.create( videoTexture.createTextureTile( 0, 0, 180, 100 ) );
		inputLive.add( new LiteInputSource( "Live3", skin, skin.clone() ) );

		skin = UISkinFactory.create( videoTexture.createTextureTile( 200, 200, 180, 100 ) );
		inputVOD.add( new LiteInputSource( "VOD1", skin, skin.clone() ) );

		skin = UISkinFactory.create( videoTexture.createTextureTile( 400, 100, 180, 100 ) );
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

		output = videoTexture.createTextureTile( 0, 0, 423, 240 );
	}

	public void selectInput( int type )
	{
		_currentInputType = type;

		send( this, SELECT_INPUT );
	}

	public void selectSceneSet( int sceneSetIndex )
	{
		if( _currentSceneSetIndex != sceneSetIndex )
		{
			if( _currentSceneSetIndex != -1 )
			{
				LiteSceneSet sceneSet = sceneSets.get( _currentSceneSetIndex );
				sceneSet.reset();
			}

			_currentSceneSetIndex = sceneSetIndex;

			send( this, SELECT_SCENE_SET );
		}
	}

	public void selectSceneSlot( int slotID )
	{
		if( _currentSceneSlotId != slotID )
		{
			if( _currentSceneSetIndex != -1 && _currentSceneSlotId != -1 )
			{
				LiteSceneSet sceneSet = sceneSets.get( _currentSceneSetIndex );

				if( _currentSceneSlotId == SCENE_SLOT_A )
				{
					sceneSet.a.reset();
				}
				else
				{
					sceneSet.b.reset();
				}
			}

			_currentSceneSlotId = slotID;

			send( this, SELECT_SCENE_SLOT );
		}
	}

	public int selectedSceneSetIndex()
	{
		return _currentSceneSetIndex;
	}

	public LiteSceneSet selectedSceneSet()
	{
		return sceneSets.get( _currentSceneSetIndex );
	}

	public int selectedSceneSlotId()
	{
		return _currentSceneSlotId;
	}

	public LiteScene selectedSceneSlot()
	{
		LiteSceneSet sceneSet = sceneSets.get( _currentSceneSetIndex );

		return ( _currentSceneSlotId == SCENE_SLOT_A ) ? sceneSet.a : sceneSet.b;
	}

	public int inputType()
	{
		return _currentInputType;
	}

	@Override
	public String toString()
	{
		return "[LiteData]";
	}
}