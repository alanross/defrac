package com.adjazent.defrac.sandbox.samples.flutterman.macro;

import defrac.compiler.Context;
import defrac.compiler.macro.Macro;
import defrac.compiler.macro.MethodBody;

import javax.annotation.Nonnull;

/**
 * @author Joa Ebert
 * @version 0.1
 */
public final class Browser extends Macro
{
	public Browser( @Nonnull Context context )
	{
		super( context );
	}

	@Nonnull
	public MethodBody openWebsite()
	{
		// This macro is equivalent to writing:
		//
		//   return com.adjazent.defrac.sandbox.samples.flutterman.macro.Browser.openWebsite()
		//
		// Although we could do some more stuff like generate different
		// code for different platforms.
		//
		// The actual implementation can be found in the Java code for
		// the individual platforms.
		return MethodBody(
				Return(
						StaticCall( "com.adjazent.defrac.sandbox.samples.flutterman.macro.Browser", "openWebsite" )
				)
		);
	}
}
