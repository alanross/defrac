package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.widget.video.Video;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EGLSLDynamicTexture extends Experiment
{
	private Video _video;

	public EGLSLDynamicTexture()
	{
	}

	@Override
	protected void onInit()
	{
		_video = new Video( 640, 360 );
		_video.load( "video_640x360" );
		_video.moveTo( 100, 100 );

		addChild( _video );
	}


	@Override
	public String toString()
	{
		return "[EGLSLDynamicTexture]";
	}
}