package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.widget.video.UIVideoCanvas;
import com.adjazent.defrac.ui.widget.video.UIVideoTexture;
import defrac.display.Image;

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
		runVideoTexture();
//		runVideoCanvas();
	}

	private void runVideoCanvas()
	{
		UIVideoCanvas videoCanvas = new UIVideoCanvas( videoWidth, videoHeight, "video_640x360.mp4" );
		videoCanvas.moveTo( 100, 100 );
		addChild( videoCanvas );
	}

	private void runVideoTexture()
	{
		UIVideoTexture videoTexture = new UIVideoTexture( videoWidth, videoHeight, "video_640x360.mp4" );

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