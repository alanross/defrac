package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.math.MColor;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.surface.UICanvasPainter;
import defrac.display.Canvas;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUICanvasPainter extends Experiment
{
	UICanvasPainter painter;
	Canvas canvas;

	public EUICanvasPainter()
	{
	}

	@Override
	protected void onInit()
	{
		painter = new UICanvasPainter();
		canvas = new Canvas( app.width(), app.height(), painter );

		addChild( canvas );

		int color = MColor.str2hex( "FF0000FF" );

		painter.fillRect( 50, 50, 300, 30, color );
	}

	@Override
	public String toString()
	{
		return "[EUICanvasPainter]";
	}
}