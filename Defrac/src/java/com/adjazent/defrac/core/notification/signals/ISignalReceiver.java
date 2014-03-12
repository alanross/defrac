package com.adjazent.defrac.core.notification.signals;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface ISignalReceiver
{
	void onSignal( ISignalSource signalSource, int signalType );
}

