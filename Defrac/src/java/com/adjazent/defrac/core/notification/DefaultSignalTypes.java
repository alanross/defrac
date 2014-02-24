package com.adjazent.defrac.core.notification;


import com.adjazent.defrac.core.notification.signals.Signals;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class DefaultSignalTypes
{
	public static final int DEFAULT_TYPE = Signals.createTypeID();

	private DefaultSignalTypes()
	{
	}

	@Override
	public String toString()
	{
		return "[DefaultSignalTypes]";
	}
}

