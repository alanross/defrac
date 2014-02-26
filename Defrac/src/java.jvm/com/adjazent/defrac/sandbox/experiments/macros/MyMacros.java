package com.adjazent.defrac.sandbox.experiments.macros;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MyMacros
{
	public static boolean myFunction()
	{
		return true;
	}

	public static boolean myFunctionWithParam()
	{
		return true;
	}

	public static boolean myFunctionWithParams()
	{
		return true;
	}

	public static boolean /*int*/ myFunctionWithReturnInt()
	{
		int returnValue = 127;

		//return returnValue;
		return true;
	}

	public static boolean /*String*/ myFunctionWithReturnString()
	{
		String returnValue = "Hello World";

		//return returnValue;
		return true;
	}

	public static boolean /*byte[]*/ myFunctionWithReturnByteArray()
	{
		byte[] returnValue = new byte[4];

		returnValue[0] = 1;
		returnValue[1] = 2;
		returnValue[2] = 3;
		returnValue[3] = 4;

		//return returnValue;
		return true;
	}
}