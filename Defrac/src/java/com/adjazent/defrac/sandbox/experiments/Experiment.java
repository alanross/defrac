package com.adjazent.defrac.sandbox.experiments;

import defrac.app.GenericApp;
import defrac.display.Layer;
import defrac.display.Stage;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class Experiment extends Layer
{
	protected Stage stage;
	protected GenericApp app;

	public Experiment()
	{
	}

	public final void init( Stage theStage, GenericApp theApp )
	{
		this.stage = theStage;
		this.app = theApp;

		onInit();
	}

	public final void resizeTo( float width, float height )
	{
		System.out.print( width + " x " + height );

		onResize( width, height );
	}

	protected void onInit()
	{
	}

	protected void onResize( float width, float height )
	{
	}

	@Override
	public String toString()
	{
		return "[Experiment]";
	}
}