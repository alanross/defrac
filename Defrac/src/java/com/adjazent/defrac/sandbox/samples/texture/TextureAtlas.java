package com.adjazent.defrac.sandbox.samples.texture;

import defrac.display.Texture;
import defrac.display.TextureData;
import defrac.lang.Function;

import javax.annotation.Nonnull;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class TextureAtlas
{
	// Function which returns a TextureAtlas for a given TextureData
	public static final Function<TextureData, TextureAtlas> DATA_TO_ATLAS = new Function<TextureData, TextureAtlas>()
	{
		@Override
		@Nonnull
		public TextureAtlas apply( @Nonnull final TextureData textureData )
		{
			return new TextureAtlas( textureData );
		}
	};

	@Nonnull
	public final Texture[] snowflakes;

	@Nonnull
	public final Texture window;

	@Nonnull
	public final Texture background;

	private TextureAtlas( @Nonnull final TextureData textureData )
	{
		// Create Texture instances from a given TextureData
		window = new Texture( textureData, 0.0f, 128.0f, 1024.0f, 590.0f );
		background = new Texture( textureData, 0.0f, 704.0f, 1024.0f, 586.0f );
		snowflakes = new Texture[ 8 ];

		// We have 8 snowflakes in the first row of the texture, each is a 128x128 sprite
		for( int i = 0; i < 8; ++i )
		{
			snowflakes[ i ] = new Texture( textureData, i * 128.0f, 0.0f, 128.0f, 128.0f );
		}
	}

	@Override
	public String toString()
	{
		return "[TextureAtlas]";
	}
}
