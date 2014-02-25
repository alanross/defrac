package com.adjazent.defrac.ui.resource;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.job.IJob;
import com.adjazent.defrac.core.job.IJobObserver;
import com.adjazent.defrac.core.job.JobQueue;
import com.adjazent.defrac.core.utils.IDisposable;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIResourceLoaderQueue implements IJobObserver, IDisposable
{
	private LinkedList<IUIResourceLoaderQueueObserver> _observer;

	private JobQueue _queue;

	/**
	 * Creates a new instance of UIResourceLoaderQueue.
	 */
	public UIResourceLoaderQueue()
	{
		_observer = new LinkedList<IUIResourceLoaderQueueObserver>();

		_queue = new JobQueue();
		_queue.addObserver( this );
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
		while( !_observer.isEmpty() )
		{
			_observer.removeLast();
		}

		_observer = null;

		_queue.dispose();
		_queue = null;
	}

	/**
	 *
	 */
	public void addObserver( IUIResourceLoaderQueueObserver observer )
	{
		if( hasObserver( observer ) )
		{
			throw new ElementAlreadyExistsError( this );
		}

		_observer.addFirst( observer );
	}

	/**
	 *
	 */
	public void removeObserver( IUIResourceLoaderQueueObserver observer )
	{
		if( !hasObserver( observer ) )
		{
			throw new ElementDoesNotExistError( this );
		}

		_observer.remove( observer );
	}

	/**
	 *
	 */
	public boolean hasObserver( IUIResourceLoaderQueueObserver observer )
	{
		return ( -1 != _observer.indexOf( observer ) );
	}

	/**
	 *
	 */
	public void add( IUIResourceLoader loader )
	{
		_queue.addJob( loader );
	}

	/**
	 *
	 */
	public void remove( IUIResourceLoader loader )
	{
		_queue.removeJob( loader );
	}

	/**
	 *
	 */
	public void clear()
	{
		_queue.removeJobs();
	}

	/**
	 *
	 */
	public void load()
	{
		_queue.start();
	}

	/**
	 *
	 */
	public void onJobProgress( IJob job, float progress )
	{
	}

	/**
	 *
	 */
	public void onJobCompleted( IJob job )
	{
		_queue.removeJobs();

		int n = _observer.size();

		while( --n > -1 )
		{
			_observer.get( n ).onResourceLoadingSuccess();
		}
	}

	/**
	 *
	 */
	public void onJobCancelled( IJob job )
	{
		throw new GenericError( this, "Font UIResourceLoaderQueue loader in loading queue was cancelled." );
	}

	/**
	 *
	 */
	public void onJobFailed( IJob job, Error error )
	{
		int n = _observer.size();

		while( --n > -1 )
		{
			_observer.get( n ).onResourceLoadingFailure( error );
		}
	}

	/**
	 * Creates and returns a string representation of the UIResourceLoaderQueue object.
	 */
	@Override
	public String toString()
	{
		return "[UIResourceLoaderQueue]";
	}
}

