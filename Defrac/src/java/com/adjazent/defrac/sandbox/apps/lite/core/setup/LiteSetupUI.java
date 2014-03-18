package com.adjazent.defrac.sandbox.apps.lite.core.setup;

import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.dnd.LiteDnDGhost;
import com.adjazent.defrac.sandbox.apps.lite.core.dnd.LiteDnDManager;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.resource.UIResourceLoaderTexturePacker;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.texture.UITextureManager;
import defrac.display.Stage;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSetupUI extends Job implements IUIResourceLoaderQueueObserver
{
	private final Stage stage;

	public LiteSetupUI( Stage stage )
	{
		this.stage = stage;
	}

	@Override
	public void onStart()
	{
		UIFontManager.initialize();
		UITextureManager.initialize();
		LiteDnDManager.initialize( stage, new LiteDnDGhost() );

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "lite/helvetica11.png", "lite/helvetica11.fnt", "helvetica11" ) );
		queue.add( new UIResourceLoaderSparrowFont( "lite/helvetica14.png", "lite/helvetica14.fnt", "helvetica14" ) );
		queue.add( new UIResourceLoaderSparrowFont( "lite/helvetica24.png", "lite/helvetica24.fnt", "helvetica24" ) );
		queue.add( new UIResourceLoaderTexturePacker( "lite/skins.png", "lite/skins.xml", "skins" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		LiteCore.ui.setup( UITextureManager.get().getAtlas( "skins" ) );

		complete();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		fail( error );
	}

	@Override
	public String toString()
	{
		return "[LiteSetupUI]";
	}
}