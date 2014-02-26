package com.adjazent.defrac.sandbox.experiments.macros;

import intrinsic.Toplevel;

import static defrac.lang.Bridge.toJSString;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MyMacros
{
	public static boolean myFunction()
	{
		//myJSFunction();

		Toplevel.window().open( toJSString( "http://aross.io/" ), toJSString( "_blank" ) );

		return true;
	}

	public static boolean myFunctionWithParam()
	{
		//myJSFunctionWithParam( param );

		return true;
	}

	public static boolean myFunctionWithParams()
	{
		//myJSFunctionWithParams( param1, param2 );

		return true;
	}

	public static boolean /*int*/ myFunctionWithReturnInt()
	{
		//myJSFunctionWithReturnInt(); // returns int

		int returnValue = 127;

		//return returnValue;
		return true;
	}

	public static boolean /*String*/ myFunctionWithReturnString()
	{
		//myJSFunctionWithReturnString(); // returns string

		String returnValue = "Hello World";

		//return returnValue;
		return true;
	}

	public static boolean /*byte[]*/ myFunctionWithReturnByteArray()
	{
		//myJSFunctionWithReturnByteArray(); // returns bytearray

		byte[] returnValue = new byte[4];

		returnValue[0] = 1;
		returnValue[1] = 2;
		returnValue[2] = 3;
		returnValue[3] = 4;

		//return returnValue;
		return true;
	}
}