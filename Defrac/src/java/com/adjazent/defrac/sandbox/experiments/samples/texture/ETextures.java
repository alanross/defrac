package com.adjazent.defrac.sandbox.experiments.samples.texture;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.concurrent.Future;
import defrac.display.*;
import defrac.event.EnterFrameEvent;
import defrac.event.Events;
import defrac.event.StageEvent;
import defrac.lang.Procedure;
import defrac.resource.TextureDataResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Joa Ebert
 * @author Alan Ross
 * @version 0.1
 */
public final class ETextures extends Experiment
{
	// Number of snowflakes on screen
	private static final int NUM_SNOWFLAKES = 1250;

	@Nullable
	private DisplayObjectContainer container;

	@Override
	protected void onInit()
	{
		// Change the background color to white
		stage.backgroundColor( 0xffffffff );

		// Access a resource from the "resources" directory.
		// We want to load it as a TextureData, so we choose the TextureDataResource.
		// Other resource types can be loaded with InputStreamResource or BinaryResource.
		// Note: If we enable WebP conversion (on by default) a runtime choice will be made to load the WebP version of this image
		final TextureDataResource textureDataResource = TextureDataResource.from( "images/textures.png" );

		// Start loading the resource, this will prepare a Future of TextureData
		// We could also attach listeners to textureDataResource (like textureDataResource.onComplete)
		// but for the sake of this example, we take a shortcut and act on the Future
		final Future<TextureData> dataFuture = textureDataResource.load();

		// Map the Future of TextureData to a Future of TextureAtlas
		// This computation will take place only if the computation succeeds
		final Future<TextureAtlas> textureAtlasFuture = dataFuture.map( TextureAtlas.DATA_TO_ATLAS );

		// The TextureAtlas has been loaded, continue with it.
		//
		// Note: Futures are computations that are either complete, or incomplete If a computation already completed, the listener will be called immediately.
		textureAtlasFuture.onSuccess( new Procedure<TextureAtlas>()
		{
			@Override
			public void apply( final TextureAtlas textureAtlas )
			{
				continueWithAtlas( textureAtlas );
			}
		} );
	}

	private void continueWithAtlas( @Nonnull final TextureAtlas textureAtlas )
	{
		// Create a layer that will hold all the snowflakes.
		// We can give a hint about how many display objects
		// will be contained in this layer to save some memory
		final Layer snowflakeLayer = new Layer( NUM_SNOWFLAKES );

		// The setup for the stage is quite simple,
		// prepare a container (so we can easily reposition everything)
		// put the "background" in the background, then the snowflakes in the
		// middle and the window layer on top
		container = new Layer( 3 );
		container.addChild( new Image( textureAtlas.background ).moveBy( 0, -3 ) );
		container.addChild( snowflakeLayer );
		container.addChild( new Image( textureAtlas.window ) );

		// Center the registration point of the container
		container.centerRegistrationPoint();

		// Add the container to the stage
		addChild( container );

		// Now prepare the actual snowflakes...
		final Snowflake[] snowflakes = new Snowflake[ NUM_SNOWFLAKES ];

		for( int i = 0; i < NUM_SNOWFLAKES; ++i )
		{
			final Snowflake snowflake = new Snowflake( textureAtlas );

			snowflake.reset();
			snowflakeLayer.addChild( snowflake.moveToRandomPosition().blendMode( BlendMode.SCREEN ) );
			snowflakes[ i ] = snowflake;
		}

		centerEverything();

		// Update all the snowflakes on every frame
		Events.onEnterFrame.attach( new Procedure<EnterFrameEvent>()
		{
			@Override
			public void apply( final EnterFrameEvent enterFrameEvent )
			{
				for( final Snowflake snowflake : snowflakes )
				{
					snowflake.update();
				}
			}
		} );

		// On resize, center everything
		stage.onResize.attach( new Procedure<StageEvent.Resize>()
		{
			@Override
			public void apply( StageEvent.Resize event )
			{
				centerEverything();
			}
		} );
	}

	private void centerEverything()
	{
		if( null != container )
		{
			// The registration point of the container is already at its center
			// so we can safely move it to the middle of the stage
			container.moveTo( stage.width() * 0.5f, stage.height() * 0.5f );
		}
	}

	@Override
	public String toString()
	{
		return "[ETextures]";
	}
}
