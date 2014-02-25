package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.utils.bitmap.UISimpleImage;
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
public final class EUIImages extends Experiment
{
	private Texture _texture;

	public EUIImages()
	{
	}

	@Override
	protected void onInit()
	{
		final TextureDataResource textureDataResource;
		textureDataResource = TextureDataResource.from( "skins/skins2.png" );
		textureDataResource.onStart.attach( new Procedure<ResourceEvent.Start>()
		{
			@Override
			public void apply( ResourceEvent.Start event )
			{
				System.out.println( "Start " + event.toString() );
			}
		} );
		textureDataResource.onProgress.attach( new Procedure<ResourceEvent.Progress>()
		{
			@Override
			public void apply( ResourceEvent.Progress event )
			{
				System.out.println( "Progress " + event.toString() );
			}
		} );
		textureDataResource.onComplete.attach( new Procedure<ResourceEvent.Complete<TextureData>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<TextureData> event )
			{
				System.out.println( "Complete " + event.toString() );

				_texture = new Texture( event.content );

				Image image = new Image( _texture );

				addChild( image );

				removeChild( image );
			}
		} );
		textureDataResource.onError.attach( new Procedure<ResourceEvent.Error>()
		{
			@Override
			public void apply( ResourceEvent.Error event )
			{
				System.out.println( "Error " + event.toString() );
			}
		} );

		textureDataResource.load();

		UISimpleImage simpleImage;
		simpleImage = new UISimpleImage( "skins/skins3.png" );
		simpleImage.moveTo( 200, 200 );
		addChild( simpleImage );
	}

	@Override
	public String toString()
	{
		return "[EUIImages]";
	}
}