package com.adjazent.defrac.sandbox.samples.flutterman.macro;

import intrinsic.Toplevel;

import static defrac.lang.Bridge.toJSString;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class Browser
{
	public static boolean openWebsite()
	{
		Toplevel.window().open(
				toJSString( "http://www.defrac.com/" ),
				toJSString( "_blank" ) );
		return true;
	}
}
