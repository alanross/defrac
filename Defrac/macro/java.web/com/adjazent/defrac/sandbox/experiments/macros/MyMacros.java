package com.adjazent.defrac.sandbox.experiments.macros;

import defrac.compiler.Context;
import defrac.compiler.macro.Macro;
import defrac.compiler.macro.MethodBody;
import defrac.compiler.macro.Parameter;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MyMacros extends Macro
{
	public MyMacros( @Nonnull Context context )
	{
		super( context );
	}

	@Nonnull
	public MethodBody callMyFunction()
	{
		return MethodBody( Untyped( "myJSFunction()" ) );
	}

	@Nonnull
	public MethodBody callMyFunctionFillByteArray( Parameter byteArray )
	{
		return MethodBody( Untyped( "myJSFunctionFillByteArray(${0})", byteArray ) );
	}
}