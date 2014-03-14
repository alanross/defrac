package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.ui.widget.video.UIVideoTexture;
import com.adjazent.defrac.ui.widget.video.UIVideoUtils;
import defrac.display.Texture;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * If Firefox failes to load the video resource
 * ("Content-Type" of "text/plain" is not supported. Load ...),
 * add the following lines to htaccess:
 *
 * AddType video/webm .webm
 * AddType video/ogg .ogv
 * AddType video/mp4 .mp4
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteVideoProvider extends Job
{
	final int videoWidth = 640;
	final int videoHeight = 360;

	UIVideoTexture videoTexture;

	public LiteVideoProvider()
	{
	}

	@Override
	protected void onStart()
	{
		String videoPath = "video_640x360";

		int format = UIVideoUtils.getProbableVideoFormat();

		if( format == -1 )
		{
			fail( new Error( "No supported video format available. Aborting" ) );
			return;
		}
		if( format == UIVideoUtils.WEBM )
		{
			info( Context.DEFAULT, this, "Selected webm video format." );
			videoPath += ".webm";
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

		complete();
	}

	public Texture createTexture()
	{
		return videoTexture.createTexture();
	}

	public Texture createTextureTile( float offsetX, float offsetY, float width, float height )
	{
		return videoTexture.createTextureTile( offsetX, offsetY, width, height );
	}
}