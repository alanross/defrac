package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderTexturePacker;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.texture.UITextureManager;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUISkinning extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame
{
	private UISurface _surfaceTexture;
	private UISurface _surfaceTexture9;
	private UISurface _surfaceColor;

	private int counter = 0;
	private int size = 200;
	private int reverse = 1;
	private boolean ready = false;


	public EUISkinning()
	{
	}

	@Override
	protected void onInit()
	{
		UITextureManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderTexturePacker( "skins/skins3.png", "skins/skins3.xml", "skin3" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		UITextureAtlas atlas = UITextureManager.get().getAtlas( "skin3" );

		info( Context.DEFAULT, this, "UITextureAtlas", atlas.getAllElementsInfo() );

		_surfaceTexture = new UISurface( atlas.getTexture( "rect6" ) );
		_surfaceTexture9 = new UISurface( atlas.getTexture( "rect1" ) );
		_surfaceColor = new UISurface( 0xFFFF0000 );

		addChild( _surfaceTexture ).moveTo( 100.0f, 100.0f );
		addChild( _surfaceTexture9 ).moveTo( 100.0f, 350.0f );
		addChild( _surfaceColor ).moveTo( 350.0f, 100.0f );
		info( Context.DEFAULT, this, "----" );

		ready = true;
	}

	@Override
	public void onEnterFrame()
	{
		if( !ready )
		{
			return;
		}
		if( counter++ == 1 )
		{
			size += reverse;

			_surfaceTexture.resizeTo( size, size );
			_surfaceTexture9.resizeTo( size, size );
			_surfaceColor.resizeTo( size, size );

			if( size >= 220 )
			{
				reverse = -2;
			}
			if( size <= 140 )
			{
				reverse = 2;
			}

			counter = 0;
		}
	}

	@Override
	public String toString()
	{
		return "[EUISkinning]";
	}
}