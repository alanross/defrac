package com.adjazent.defrac.sandbox.experiments.macros;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.annotation.MacroWeb;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EMacros extends Experiment
{
	public EMacros()
	{
	}

	@Override
	protected void onInit()
	{

	}

	// Macro to open http://www.defrac.com/ in a browser window
	@MacroWeb( "com.adjazent.defrac.sandbox.samples.flutterman.macro.Browser.openWebsite" )
	private static native boolean myMacro();

	@Override
	public String toString()
	{
		return "[EMacros]";
	}
}