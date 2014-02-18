package com.adjazent.defrac.core.job;

/**
 * @author Alan Ross
 * @version 0.1
 */
public interface IJob
{
	/**
	 * Start the job.
	 */
	void start();

	/**
	 * Cancel the job.
	 */
	void cancel();

	/**
	 * Returns true if the job is currently running, false otherwise.
	 */
	boolean isRunning();

	/**
	 * Add an observer to be notified of changes to the job status.
	 */
	void addObserver( IJobObserver observer );

	/**
	 * Remove an given observer from being notified of changes to the job status.
	 */
	void removeObserver( IJobObserver observer );

	/**
	 * Returns true if given observer is held by the job, false otherwise.
	 */
	boolean hasObserver( IJobObserver observer );
}

