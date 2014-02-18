package com.adjazent.defrac.sandbox.experiments.core.job;

import com.adjazent.defrac.core.job.IJob;
import com.adjazent.defrac.core.job.IJobObserver;
import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.job.JobQueue;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.experiments.Experiment;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EJob extends Experiment implements IJobObserver
{
	public EJob()
	{
	}

	protected void onInit()
	{
		JobQueue chain = new JobQueue();

		Job t1 = new MyJob( "1" );
		Job t2 = new MyJob( "2" );
		Job t3 = new MyJob( "3" );
		Job t4 = new MyJob( "4" );

		chain.addJob( t1 ).addJob( t2 ).addJob( t3 ).addJob( t4 );
		chain.addObserver( this );
		chain.start();
	}

	public void onJobProgress( IJob job, float progress )
	{
		Log.info( Context.DEFAULT, "onJobProgress", job, progress );
	}

	public void onJobCompleted( IJob job )
	{
		Log.info( Context.DEFAULT, "onJobCompleted", job );
	}

	public void onJobCancelled( IJob job )
	{
		Log.info( Context.DEFAULT, "onJobCancelled", job );
	}

	@Override
	public void onJobFailed( IJob job, Error error )
	{
		//To change body of implemented methods use File | Settings | File Templates.
		Log.info( Context.DEFAULT, "onJobFailed", job, error );
	}

	@Override
	public String toString()
	{
		return "[EJob]";
	}
}