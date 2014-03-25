package com.adjazent.defrac.sandbox.samples.flutterman.macro;

import intrinsic.Toplevel;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class Browser
{
	public static boolean openWebsite()
	{
		Toplevel.window().open( "http://www.defrac.com/", "_blank" );
		return true;
	}
}
