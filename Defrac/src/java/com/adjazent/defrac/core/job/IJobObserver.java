package com.adjazent.defrac.core.job;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IJobObserver
{
	/**
	 * Called upon update in the progress of the job.
	 */
	void onJobProgress( IJob job, float progress );

	/**
	 * Called when the job is completed successfully.
	 */
	void onJobCompleted( IJob job );

	/**
	 * Called when the job was cancelled.
	 */
	void onJobCancelled( IJob job );

	/**
	 * Called when the job encountered an error.
	 */
	void onJobFailed( IJob job, Error error );
}

