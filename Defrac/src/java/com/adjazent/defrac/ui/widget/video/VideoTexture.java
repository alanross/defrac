package com.adjazent.defrac.ui.widget.video;

import com.adjazent.defrac.core.stage.StageProvider;
import defrac.annotation.MacroWeb;
import defrac.display.*;
import defrac.event.EnterFrameEvent;
import defrac.event.Events;
import defrac.gl.GL;
import defrac.gl.GLTexture;
import defrac.lang.Procedure;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class VideoTexture
{
	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.canPlayVideo" )
	private static native boolean canPlayVideo();

	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.canPlayVideo" )
	private static native boolean canPlayMP4();

	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.canPlayVideo" )
	private static native boolean canPlayOGV();

	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.canPlayVideo" )
	private static native boolean canPlayWEBM();

	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.attachVideoElement" )
	private static native boolean attachVideoElement( String fileName );

	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.detachVideoElement" )
	private static native boolean detachVideoElement();

	@MacroWeb( "com.adjazent.defrac.ui.video.NativeVideo.uploadVideoTexture" )
	private static native boolean uploadVideoTexture( GL gl );

	private final TextureData _textureData;
	private final Stage stage;
	private final int textureWidth;
	private final int textureHeight;

	public VideoTexture( int videoWidth, int videoHeight, String fileName )
	{
		this.textureWidth = videoWidth;
		this.textureHeight = videoHeight;

		this.stage = StageProvider.stage;

		byte[] pixels = new byte[ videoWidth * videoHeight * 4 ];

		for( int i = 0; i < pixels.length; i += 4 )
		{
			pixels[ i ] = 0;
			pixels[ i + 1 ] = 0;
			pixels[ i + 2 ] = 0;
			pixels[ i + 3 ] = 127;
		}

		_textureData = TextureData.Persistent.fromData(
				pixels,
				videoWidth,
				videoHeight,
				TextureDataFormat.RGBA,
				TextureDataRepeat.REPEAT,
				TextureDataSmoothing.NO_SMOOTHING
		);

		Events.onEnterFrame.attach( new Procedure<EnterFrameEvent>()
		{
			@Override
			public void apply( EnterFrameEvent event )
			{
				onEnterFrame();
			}
		} );

		attachVideoElement( fileName );

	}

	private void onEnterFrame()
	{
		GL gl = stage.gl();

		GLTexture texture = stage.getOrCreateTexture( _textureData );

		gl.bindTexture( gl.TEXTURE_2D, texture );

		uploadVideoTexture( gl );

		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );
	}

	public Texture createTexture()
	{
		return new Texture( _textureData, 0, 0, textureWidth, textureHeight );
	}

	public Texture createTexture( float offsetX, float offsetY, float trimmedWidth, float trimmedHeight )
	{
		return new Texture( _textureData, 0, 0, textureWidth, textureHeight, 0, offsetX, offsetY, trimmedWidth, trimmedHeight );
	}

	@Override
	public String toString()
	{
		return "[VideoTexture]";
	}
}