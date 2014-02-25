package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.texture.UITextureManager;
import com.adjazent.defrac.ui.resource.*;
import com.adjazent.defrac.ui.text.font.UIFontManager;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIResourceLoading extends Experiment implements IUIResourceLoaderQueueObserver
{
	public EUIResourceLoading()
	{
	}

	@Override
	protected void onInit()
	{
		UITextureManager.initialize();
		UIFontManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderTexturePacker( "skins/skins1.png", "skins/skins1.xml", "skin1" ) );
		queue.add( new UIResourceLoaderTexturePacker( "skins/skins3.png", "skins/skins3.xml", "skin3" ) );
		queue.add( new UIResourceLoaderTexturePacker( "skins/skins5.png", "skins/skins5.xml", "skin5" ) );
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica11.png", "fonts/helvetica11.fnt", "helvetica11" ) );
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica24.png", "fonts/helvetica24.fnt", "helvetica24" ) );
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica24-bold.png", "fonts/helvetica24-bold.fnt", "helvetica24bold" ) );
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica64.png", "fonts/helvetica64.fnt", "helvetica64" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		Log.info( Context.DEFAULT, this, "UIFontManager", UIFontManager.get().getAllElementsInfo() );
		Log.info( Context.DEFAULT, this, "UITextureManager", UITextureManager.get().getAllElementsInfo() );
		Log.info( Context.DEFAULT, this, "onResourceLoadingSuccess" );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	@Override
	public String toString()
	{
		return "[EUIResourceLoading]";
	}
}