package com.adjazent.defrac.sandbox.experiments.apps;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IPro7CoreUIObserver
{
	void onPro7CoreUISetupSuccess();

	void onPro7CoreUISetupFailure( Error error );
}