package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderTexturePacker;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.texture.UITextureManager;
import defrac.event.Event;
import defrac.event.Events;
import defrac.lang.Procedure;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUISkinning extends Experiment implements IUIResourceLoaderQueueObserver, Procedure<Event>
{
	private UISurface _surface1;
	private UISurface _surface2;

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
		Log.info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingSuccess" );

		Log.info( Context.DEFAULT, this, "UITextureManager", UITextureManager.get().getAllElementsInfo() );

		UITextureAtlas atlas = UITextureManager.get().getAtlas( "skin3" );

		Log.info( Context.DEFAULT, this, "UITextureAtlas", atlas.getAllElementsInfo() );

		_surface1 = new UISurface( atlas.getTexture( "rect6" ) );
		_surface2 = new UISurface( atlas.getTexture( "rect1" ) );

		addChild( _surface1 ).moveTo( 100.0f, 100.0f );
		addChild( _surface2 ).moveTo( 100.0f, 350.0f );

		Events.onEnterFrame.attach( this );
	}

	private int counter = 0;
	private int size = 200;
	private int reverse = 1;

	@Override
	public void apply( @Nonnull final Event event )
	{
		if( counter++ == 1 )
		{
			size += reverse;

			_surface1.resizeTo( size, size );
			_surface2.resizeTo( size, size );

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