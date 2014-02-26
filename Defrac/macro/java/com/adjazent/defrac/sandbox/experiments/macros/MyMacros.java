package com.adjazent.defrac.sandbox.experiments.macros;

import defrac.compiler.Context;
import defrac.compiler.macro.Macro;
import defrac.compiler.macro.MethodBody;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MyMacros extends Macro
{
	private final static String CLAZZ = "com.adjazent.defrac.sandbox.experiments.macros.MyMacros";

	public MyMacros( @Nonnull Context context )
	{
		super( context );
	}

	@Nonnull
	public MethodBody callMyFunction()
	{
		return MethodBody( Return( MethodCall( MemberAccess( ClassTypeReference( CLAZZ ), "myFunction" ) ) ) );
	}

	@Nonnull
	public MethodBody callMyFunctionWithParam()
	{
		// how does one pass params?
		return MethodBody( Return( MethodCall( MemberAccess( ClassTypeReference( CLAZZ ), "myFunctionWithParam" ) ) ) );
	}

	@Nonnull
	public MethodBody callMyFunctionWithParams()
	{
		return MethodBody( Return( MethodCall( MemberAccess( ClassTypeReference( CLAZZ ), "myFunctionWithParams" ) ) ) );
	}

	@Nonnull
	public MethodBody callMyFunctionWithReturnInt()
	{
		return MethodBody( Return( MethodCall( MemberAccess( ClassTypeReference( CLAZZ ), "myFunctionWithReturnInt" ) ) ) );
	}

	@Nonnull
	public MethodBody callMyFunctionWithReturnString()
	{
		return MethodBody( Return( MethodCall( MemberAccess( ClassTypeReference( CLAZZ ), "myFunctionWithReturnString" ) ) ) );
	}

	@Nonnull
	public MethodBody callMyFunctionWithReturnByteArray()
	{
		return MethodBody( Return( MethodCall( MemberAccess( ClassTypeReference( CLAZZ ), "myFunctionWithReturnByteArray" ) ) ) );
	}
}
