package com.adjazent.defrac.sandbox.samples.flutterman.macro;

import java.net.URI;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class Browser
{
	public static boolean openWebsite()
	{
		try
		{
			java.awt.Desktop.getDesktop().browse( new URI( "http://www.defrac.com/" ) );
			return true;
		}
		catch( final Throwable t )
		{
			return false;
		}
	}
}
