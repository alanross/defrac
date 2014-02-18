package com.adjazent.defrac.ui.resource;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IUIResourceLoaderQueueObserver
{
	/**
	 * Called of the resource loading process ended successfully.
	 */
	void onResourceLoadingSuccess();

	/**
	 * Called of the resource loading process failed.
	 */
	void onResourceLoadingFailure();
}

