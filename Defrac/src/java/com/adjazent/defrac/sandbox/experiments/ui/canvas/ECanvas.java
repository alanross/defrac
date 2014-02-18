package com.adjazent.defrac.sandbox.experiments.ui.canvas;

import com.adjazent.defrac.sandbox.experiments.Experiment;
import defrac.display.Canvas;

/**
 *
 */
public class ECanvas extends Experiment
{
	private Canvas _canvas;

	public ECanvas()
	{
	}

	@Override
	protected void onInit()
	{
		// Create a Canvas element with its own renderer implementation
		// We pass the UIEventManager instance to the CanvasRenderer so
		// we can easily access global pointer coordinates without having
		// to listen for events
		_canvas = new Canvas( app.width(), app.height(), new CanvasRenderer( stage.eventManager() ) );

		addChild( _canvas );
	}

	@Override
	protected void onResize( float width, float height )
	{
		System.out.println( "onResize" + width + " " + height );

		// Adjusts the quality of the shader:
		//
		//   1 = No downsampling
		//   4 = OK
		//   8 = Starts to look really ugly
		int ratio = 4;

		// We do not want to fry the graphics card so we simply
		// draw a smaller _canvas and scale the result
		_canvas.size( width / ratio, height / ratio );
		_canvas.scaleTo( ratio );
	}
}
