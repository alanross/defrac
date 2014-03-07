package com.adjazent.defrac.sandbox.apps.model;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.ui.widget.video.UIVideoTexture;
import com.adjazent.defrac.ui.widget.video.UIVideoUtils;
import defrac.display.Texture;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7VideoProvider
{
	final int videoWidth = 640;
	final int videoHeight = 360;

	UIVideoTexture videoTexture;

	public Pro7VideoProvider()
	{
		String videoPath = "video_640x360";

		int format = UIVideoUtils.getProbableVideoFormat();

		if( format == -1 )
		{
			info( Context.DEFAULT, this, "No supported format found. Aborting" );
			return;
		}
		if( format == UIVideoUtils.WEBM )
		{
			info( Context.DEFAULT, this, "Found WebM support, but have no video. Aborting." );
			return;
		}
		if( format == UIVideoUtils.MP4 )
		{
			info( Context.DEFAULT, this, "Selected mp4 video format." );
			videoPath += ".mp4";
		}
		if( format == UIVideoUtils.OGV )
		{
			info( Context.DEFAULT, this, "Selected ogv video format." );
			videoPath += ".ogv";
		}

		videoTexture = new UIVideoTexture( videoWidth, videoHeight, videoPath );
	}

	public Texture createTexture()
	{
		return videoTexture.createTexture();
	}

	public Texture createTextureTile( float offsetX, float offsetY, float width, float height )
	{
		return videoTexture.createTextureTile( offsetX, offsetY, width, height );
	}

	@Override
	public String toString()
	{
		return "[Pro7VideoProvider]";
	}
}