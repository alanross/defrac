package com.adjazent.defrac.sandbox.apps.lite.core.setup;

import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.ui.widget.video.UIVideoUtils;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSetupData extends Job
{
	public LiteSetupData()
	{
	}

	@Override
	protected void onStart()
	{
		String videoPath = "video_640x360";
		int videoWidth = 640;
		int videoHeight = 360;

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

		LiteCore.data.setup( videoPath, videoWidth, videoHeight );

		complete();
	}

	@Override
	public String toString()
	{
		return "[LiteSetupData]";
	}
}