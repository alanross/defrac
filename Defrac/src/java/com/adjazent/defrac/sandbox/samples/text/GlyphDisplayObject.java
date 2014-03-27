package com.adjazent.defrac.sandbox.samples.text;

import defrac.display.BlendMode;
import defrac.display.DisplayObject;
import defrac.display.Texture;
import defrac.display.render.RenderContent;
import defrac.display.render.Renderer;
import defrac.gl.GLMatrix;
import defrac.lang.Procedure;
import defrac.text.BitmapFont;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class GlyphDisplayObject extends DisplayObject
{
	BitmapFont _bitmapFont = null;

	public GlyphDisplayObject()
	{
		BitmapFont.fromFnt( "fonts/font.fnt", "fonts/font.png" ).onSuccess( new Procedure<BitmapFont>()
		{
			@Override
			public void apply( BitmapFont bitmapFont )
			{

				_bitmapFont = bitmapFont;

				invalidate();
			}
		} );
	}

	@Override
	public RenderContent render( @Nonnull GLMatrix glMatrix, @Nonnull GLMatrix glMatrix1, @Nonnull Renderer renderer, @Nonnull BlendMode blendMode, float v )
	{
		if( null == _bitmapFont )
		{
			return null;
		}

		BitmapFont.CharacterData a = _bitmapFont.get( 'a' );
		Texture t = a.texture;

		// kerning between a and b
		a.kerning( 'b' );

		float[] aDim = new float[]{ 0, t.trimmedWidth, t.trimmedHeight, 0 };
		float[] aUvs = new float[]{ t.uv00x, t.uv00y, t.uv01x, t.uv01y, t.uv10x, t.uv10y, t.uv11x, t.uv11y };

		// actually only do this when we change text
		initAABB( 0, 0, t.width, t.height );

		v *= alpha();

		return renderer.drawBitmapFont( glMatrix, glMatrix1, v, this.blendMode.inherit( blendMode ), t.textureData, aDim, aUvs, 1, 1.0f, 1.0f, 1.0f, 1.0f, _bitmapFont, 1.0f );
	}

	@Override
	public String toString()
	{
		return "[GlyphDisplayObject]";
	}
}