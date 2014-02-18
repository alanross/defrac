package com.adjazent.defrac.ui.resource;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.job.Job;
import defrac.display.TextureData;
import defrac.event.ResourceEvent;
import defrac.lang.Procedure;
import defrac.resource.TextureDataResource;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UILoadTexJob extends Job
{
	private TextureDataResource _loader;
	private String _url;
	private TextureData _textureData;

	public UILoadTexJob( String url )
	{
		_url = url;
	}

	@Override
	protected void onStart()
	{
		if( _loader != null )
		{
			throw new GenericError( this, "Already loading" );
		}

		_loader = TextureDataResource.from( _url );

		_loader.onStart.attach( new Procedure<ResourceEvent.Start>()
		{
			@Override
			public void apply( ResourceEvent.Start event )
			{
			}
		} );
		_loader.onProgress.attach( new Procedure<ResourceEvent.Progress>()
		{
			@Override
			public void apply( ResourceEvent.Progress event )
			{
			}
		} );
		_loader.onError.attach( new Procedure<ResourceEvent.Error>()
		{
			@Override
			public void apply( ResourceEvent.Error event )
			{
				fail( new Error( "LoadError" + event.toString() ) );
			}
		} );
		_loader.onComplete.attach( new Procedure<ResourceEvent.Complete<TextureData>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<TextureData> event )
			{
				_textureData = ( TextureData ) event.content;

				complete();
			}
		} );

		_loader.load();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		_loader = null;
		_textureData = null;
	}

	public TextureData getTextureData()
	{
		return _textureData;
	}

	public String getUrl()
	{
		return _url;
	}

	@Override
	public String toString()
	{
		return "[UILoadTexJob]";
	}
}

