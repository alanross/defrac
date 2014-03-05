package com.adjazent.defrac.sandbox.experiments.apps.theme;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IPro7ThemeObserver
{
	void onPro7CoreUISetupSuccess();

	void onPro7CoreUISetupFailure( Error error );
}