package com.adjazent.defrac.sandbox.experiments.macros;

import com.adjazent.defrac.sandbox.Experiment;
import defrac.annotation.MacroWeb;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EMacros extends Experiment
{
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunction" )
	private static native boolean callMyFunction();

	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionFillByteArray" )
	private static native void callMyFunctionFillByteArray( byte[] param );

	public EMacros()
	{
	}

	@Override
	protected void onInit()
	{
		callMyFunction();

		byte[] param = new byte[ 4 ];
		param[ 0 ] = 1;
		param[ 1 ] = 2;
		param[ 2 ] = 3;
		param[ 3 ] = 4;

		callMyFunctionFillByteArray( param );
	}

	@Override
	public String toString()
	{
		return "[EMacros]";
	}
}