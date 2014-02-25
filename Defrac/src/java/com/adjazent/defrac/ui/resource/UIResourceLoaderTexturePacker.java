package com.adjazent.defrac.ui.resource;

import com.adjazent.defrac.core.error.SyncError;
import com.adjazent.defrac.core.job.IJob;
import com.adjazent.defrac.core.job.IJobObserver;
import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.job.JobQueue;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.xml.XML;
import com.adjazent.defrac.core.xml.XMLNode;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.texture.UITextureManager;
import com.adjazent.defrac.ui.utils.bitmap.UISlice9Grid;
import defrac.display.TextureData;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIResourceLoaderTexturePacker extends Job implements IUIResourceLoader, IJobObserver
{
	private UILoadTexJob _texLoader;
	private UILoadXmlJob _xmlLoader;
	private JobQueue _loadQueue;
	private String _id;

	public UIResourceLoaderTexturePacker( String skinTextureUrl, String skinXmlUrl, String id )
	{
		_id = id;
		_texLoader = new UILoadTexJob( skinTextureUrl );
		_xmlLoader = new UILoadXmlJob( skinXmlUrl );

		_loadQueue = new JobQueue();
		_loadQueue.addJob( _texLoader );
		_loadQueue.addJob( _xmlLoader );
		_loadQueue.addObserver( this );
	}

	private void clear()
	{
		_loadQueue.removeJob( _texLoader );
		_loadQueue.removeJob( _xmlLoader );

		_texLoader.dispose();
		_texLoader = null;

		_xmlLoader.dispose();
		_xmlLoader = null;
	}

	private UITextureAtlas createTextureAtlas( TextureData textureData, XML xml )
	{
		LinkedList<XMLNode> sprites = xml.getAllByName( "sprite" );

		UITextureAtlas atlas = new UITextureAtlas( textureData, _id );

		int n = sprites.size();

		while( --n > -1 )
		{
			createTexture( atlas, sprites.get( n ) );
		}

		return atlas;
	}

	private void createTexture( UITextureAtlas atlas, XMLNode sprite )
	{
		String id = sprite.getAttribute( "n" );

		int left = Integer.parseInt( sprite.getAttribute( "left" ) );
		int right = Integer.parseInt( sprite.getAttribute( "right" ) );
		int top = Integer.parseInt( sprite.getAttribute( "top" ) );
		int bottom = Integer.parseInt( sprite.getAttribute( "bottom" ) );

		MRectangle rect = new MRectangle(
				Integer.parseInt( sprite.getAttribute( "x" ) ),
				Integer.parseInt( sprite.getAttribute( "y" ) ),
				Integer.parseInt( sprite.getAttribute( "w" ) ),
				Integer.parseInt( sprite.getAttribute( "h" ) )
		);

		if( left > 0 || right > 0 || top > 0 || bottom > 0 )
		{
			atlas.addTexture( id, rect, new UISlice9Grid( left, right, top, bottom ) );
		}
		else
		{
			atlas.addTexture( id, rect );
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		clear();

		_loadQueue.dispose();
		_loadQueue = null;
	}

	@Override
	protected void onStart()
	{
		if( _loadQueue.isRunning() )
		{
			throw new SyncError( this, "is already running" );
		}

		_loadQueue.start();
	}

	@Override
	protected void onCancel()
	{
		if( _loadQueue.isRunning() )
		{
			_loadQueue.cancel();
		}

		clear();
	}

	@Override
	protected void onComplete()
	{
		clear();
	}

	@Override
	protected void onFail()
	{
		clear();
	}

	@Override
	public void onJobProgress( IJob job, float progress )
	{
	}

	@Override
	public void onJobCompleted( IJob job )
	{
		UITextureManager.get().addAtlas( createTextureAtlas( _texLoader.getTextureData(), _xmlLoader.getXml() ) );

		Log.trace( Context.UI, "Completed loading TextureAtlas: " + _texLoader.getUrl() + " | " + _xmlLoader.getUrl() );

		complete();
	}

	@Override
	public void onJobCancelled( IJob job )
	{

	}

	@Override
	public void onJobFailed( IJob job, Error error )
	{
		fail( error );
	}

	@Override
	public String toString()
	{
		return "[UIResourceLoaderTexturePacker]";
	}
}

