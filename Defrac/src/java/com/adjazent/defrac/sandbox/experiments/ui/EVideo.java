package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.widget.video.UIVideoCanvas;
import com.adjazent.defrac.ui.widget.video.UIVideoTexture;
import com.adjazent.defrac.ui.widget.video.UIVideoUtils;
import defrac.display.Image;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EVideo extends Experiment
{
	final int videoWidth = 640;
	final int videoHeight = 360;

	public EVideo()
	{
	}

	@Override
	protected void onInit()
	{
		if( !UIVideoUtils.supportsVideo() )
		{
			info( Context.DEFAULT, this, "Html5 video element not supported. Aborting." );
			return;
		}

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

		runVideoTexture( videoPath );
//		runVideoCanvas(videoPath);
	}

	private void runVideoCanvas( String videoPath )
	{
		UIVideoCanvas videoCanvas = new UIVideoCanvas( videoWidth, videoHeight, videoPath );
		videoCanvas.moveTo( 100, 100 );
		addChild( videoCanvas );
	}

	private void runVideoTexture( String videoPath )
	{
		UIVideoTexture videoTexture = new UIVideoTexture( videoWidth, videoHeight, videoPath );

		int offsetX = 50;
		int offsetY = 50;

		int tileW = 60;
		int tileH = 20;

		int spacingX = 1;
		int spacingY = 1;

		int rows = ( int ) Math.floor( videoHeight / tileH );
		int cols = ( int ) Math.floor( videoWidth / tileW );

		for( int r = 0; r < rows; ++r )
		{
			for( int c = 0; c < cols; ++c )
			{
				int px = ( c * tileW );
				int py = ( r * tileH );

				Image img = new Image( videoTexture.createTextureTile( px, py, tileW, tileH ) );
				addChild( img ).moveTo( px + ( c * spacingX ) + offsetX, py + ( r * spacingY ) + offsetY );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[EVideo]";
	}
}