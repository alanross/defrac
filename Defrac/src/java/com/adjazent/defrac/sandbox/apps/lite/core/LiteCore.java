package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.core.error.SingletonError;
import com.adjazent.defrac.core.job.IJob;
import com.adjazent.defrac.core.job.IJobObserver;
import com.adjazent.defrac.core.job.JobQueue;
import com.adjazent.defrac.sandbox.apps.lite.core.setup.LiteSetupData;
import com.adjazent.defrac.sandbox.apps.lite.core.setup.LiteSetupUI;
import defrac.display.Stage;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteCore implements IJobObserver
{
	public static final LiteData data = new LiteData();
	public static final LiteUI ui = new LiteUI();

	private static LiteCore _instance;

	private final ILiteSetupObserver _observer;

	public static void initialize( Stage stage, ILiteSetupObserver observer )
	{
		if( _instance != null )
		{
			throw new SingletonError( "LiteCore" );
		}

		_instance = new LiteCore( observer );
		_instance.setup( stage );
	}

	private LiteCore( ILiteSetupObserver observer )
	{
		_observer = observer;
	}

	private void setup( Stage stage )
	{
		_observer.onLiteSetupMessage( "Setup started." );

		JobQueue setupQueue;
		setupQueue = new JobQueue();
		setupQueue.addJob( new LiteSetupUI( stage ) );
		setupQueue.addJob( new LiteSetupData() );
		setupQueue.addObserver( this );
		setupQueue.start();
	}

	@Override
	public void onJobCompleted( IJob job )
	{
		_observer.onLiteSetupSuccess();
	}

	@Override
	public void onJobCancelled( IJob job )
	{
	}

	@Override
	public void onJobFailed( IJob job, Error error )
	{
		_observer.onLiteSetupFailure( error );
	}

	@Override
	public void onJobProgress( IJob job, float progress )
	{
		_observer.onLiteSetupMessage( "Setup progress: " + progress );
	}

	@Override
	public String toString()
	{
		return "[LiteCore]";
	}
}