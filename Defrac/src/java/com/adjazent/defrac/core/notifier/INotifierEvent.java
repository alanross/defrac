package com.adjazent.defrac.core.notifier;

import com.adjazent.defrac.core.utils.IDisposable;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface INotifierEvent extends IDisposable
{
	void reset();

	String toString();
}

