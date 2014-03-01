package com.adjazent.defrac.sandbox.experiments.ui;

import defrac.display.TextureData;
import defrac.display.TextureDataFormat;
import defrac.display.TextureDataRepeat;
import defrac.display.TextureDataSmoothing;
import defrac.gl.GL;
import defrac.gl.GLTexture;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class GLBitmapData
{
	public final TextureData textureData;
	public final byte[] pixels;
	public final int width;
	public final int height;

	public static GLTexture createAndSetGLTexture( GL gl, TextureData textureData )
	{
		GLTexture texture = gl.createTexture();
		gl.bindTexture( gl.TEXTURE_2D, texture );

		textureData.texImage2D( gl, gl.TEXTURE_2D, 0, gl.RGB );

		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );

		gl.bindTexture( gl.TEXTURE_2D, null );

		return texture;
	}

	public static GLTexture createAndSetGLTexture( GL gl, byte[] rgbaByteArray, int width, int height )
	{
		GLTexture texture = gl.createTexture();
		gl.bindTexture( gl.TEXTURE_2D, texture );

		gl.texImage2D( gl.TEXTURE_2D, 0, gl.RGBA, width, height, 0, gl.RGBA, gl.UNSIGNED_BYTE, rgbaByteArray );

		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );

		gl.bindTexture( gl.TEXTURE_2D, null );

		return texture;
	}

	public boolean isPowerOf2( int value )
	{
		return ( ( value & ( value - 1 ) ) == 0 );
	}

	public void setTextureFilteringAndMips( GL gl, int width, int height )
	{
		if( isPowerOf2( width ) && isPowerOf2( height ) )
		{
			// the dimensions are power of 2 so generate mips and turn on tri-linear filtering.
			gl.generateMipmap( gl.TEXTURE_2D );
			gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_LINEAR );
		}
		else
		{
			// at least one of the dimensions is not a power of 2 so set the filtering so WebGL will render it.
			gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
			gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );
			gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR );
		}
	}

	public static byte[] rgbaCreate( int width, int height, byte r, byte g, byte b, byte a )
	{
		byte[] pixels = new byte[ width * height * 4 ];

		for( int i = 0; i < pixels.length; i += 4 )
		{
			pixels[ i ] = r;
			pixels[ i + 1 ] = g;
			pixels[ i + 2 ] = b;
			pixels[ i + 3 ] = a;
		}

		return pixels;
	}

	public GLBitmapData( int width, int height, byte r, byte g, byte b, byte a )
	{
		TextureDataFormat format = TextureDataFormat.RGBA;
		TextureDataRepeat repeat = TextureDataRepeat.REPEAT;
		TextureDataSmoothing smooth = TextureDataSmoothing.NO_SMOOTHING;

		this.width = width;
		this.height = height;
		this.pixels = rgbaCreate( width, height, r, g, b, a );

		this.textureData = TextureData.Persistent.fromData( pixels, width, height, format, repeat, smooth );
	}

	@Override
	public String toString()
	{
		return "[GLBitmapData]";
	}
}