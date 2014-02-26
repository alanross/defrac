package com.adjazent.defrac.sandbox.experiments.macros;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import defrac.annotation.MacroJVM;
import defrac.annotation.MacroWeb;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EMacros extends Experiment
{
	@MacroJVM( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunction" )
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunction" )
	private static native boolean callMyFunction();

	@MacroJVM( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithParam" )
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithParam" )
	private static native boolean callMyFunctionWithParam();

	@MacroJVM( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithParams" )
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithParams" )
	private static native boolean callMyFunctionWithParams();

	@MacroJVM( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithReturnInt" )
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithReturnInt" )
	private static native boolean callMyFunctionWithReturnInt();

	@MacroJVM( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithReturnString" )
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithReturnString" )
	private static native boolean callMyFunctionWithReturnString();

	@MacroJVM( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithReturnByteArray" )
	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.macros.MyMacros.callMyFunctionWithReturnByteArray" )
	private static native boolean callMyFunctionWithReturnByteArray();


	public EMacros()
	{
	}

	@Override
	protected void onInit()
	{
		Log.info( Context.DEFAULT, this, "Call Macro callMyFunction start" );

		callMyFunction();

		Log.info( Context.DEFAULT, this, "Call Macro callMyFunction completed" );
	}

	@Override
	public String toString()
	{
		return "[EMacros]";
	}
}