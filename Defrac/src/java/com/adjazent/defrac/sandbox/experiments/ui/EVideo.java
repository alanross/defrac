package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.widget.video.VideoCanvas;
import com.adjazent.defrac.ui.widget.video.VideoTexture;
import defrac.display.Image;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EVideo extends Experiment
{
	public EVideo()
	{
	}

	@Override
	protected void onInit()
	{
		runVideoTexture();
	}

	private void runVideoCanvas()
	{
		VideoCanvas videoCanvas = new VideoCanvas( 640, 360 );
		videoCanvas.load( "video_640x360" );
		videoCanvas.moveTo( 100, 100 );
		addChild( videoCanvas );
	}

	private void runVideoTexture()
	{
		VideoTexture videoTexture = new VideoTexture( 640, 360 );

		Image img1 = new Image( videoTexture.createTexture() );
		Image img2 = new Image( videoTexture.createTexture( 100, 100, 540, 260 ) );

		img1.moveTo( 100, 100 );
		img2.moveTo( 100, 101 );

		addChild( img1 );
		addChild( img2 );
	}

	@Override
	public String toString()
	{
		return "[EVideo]";
	}
}