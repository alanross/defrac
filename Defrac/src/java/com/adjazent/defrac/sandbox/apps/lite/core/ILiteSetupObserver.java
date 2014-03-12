package com.adjazent.defrac.sandbox.apps.lite.core;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface ILiteSetupObserver
{
	void onLiteSetupSuccess();

	void onLiteSetupMessage( String message );

	void onLiteSetupFailure( Error error );
}