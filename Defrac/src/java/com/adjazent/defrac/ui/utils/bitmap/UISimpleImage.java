package com.adjazent.defrac.ui.utils.bitmap;

import defrac.display.Image;
import defrac.display.Texture;
import defrac.display.TextureData;
import defrac.event.ResourceEvent;
import defrac.lang.Procedure;
import defrac.resource.TextureDataResource;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISimpleImage extends Image
{
	public UISimpleImage( String resourceFile )
	{
		load( resourceFile );
	}

	public void load( String resourceFile )
	{
		final TextureDataResource resource = TextureDataResource.from( resourceFile );

		resource.onStart.attach( new Procedure<ResourceEvent.Start>()
		{
			@Override
			public void apply( ResourceEvent.Start event )
			{
			}
		} );
		resource.onProgress.attach( new Procedure<ResourceEvent.Progress>()
		{
			@Override
			public void apply( ResourceEvent.Progress event )
			{
			}
		} );
		resource.onError.attach( new Procedure<ResourceEvent.Error>()
		{
			@Override
			public void apply( ResourceEvent.Error event )
			{
			}
		} );
		resource.onComplete.attach( new Procedure<ResourceEvent.Complete<TextureData>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<TextureData> event )
			{
				System.out.println( "Complete " + event.toString() );

				texture( new Texture( event.content ), true );
			}
		} );

		resource.load();
	}

	@Override
	public String toString()
	{
		return "[UISimpleImage]";
	}
}