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
//		runVideoCanvas();
	}

	private void runVideoCanvas()
	{
		VideoCanvas videoCanvas = new VideoCanvas( 640, 360, "video_640x360" );
		videoCanvas.moveTo( 100, 100 );
		addChild( videoCanvas );
	}

	private void runVideoTexture()
	{
		VideoTexture videoTexture = new VideoTexture( 640, 360, "video_640x360" );

		Image img1 = new Image( videoTexture.createTexture( 0, 100, 210, 300 ) );
		Image img2 = new Image( videoTexture.createTexture( 210, 100, 210, 300 ) );
		Image img3 = new Image( videoTexture.createTexture( 420, 100, 210, 300 ) );
		Image img4 = new Image( videoTexture.createTexture() );

		img1.moveTo( 100, 50 );
		img2.moveTo( 311, 50 );
		img3.moveTo( 522, 50 );
		img4.moveTo( 100, 370 );

		addChild( img1 );
		addChild( img2 );
		addChild( img3 );
		addChild( img4 );
	}

	@Override
	public String toString()
	{
		return "[EVideo]";
	}
}