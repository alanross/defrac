package com.adjazent.defrac.core.job;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class Job implements IJob
{
	private LinkedList<IJobObserver> _observers;

	private boolean _running;

	/**
	 * Creates a new instance of Job.
	 */
	public Job()
	{
		_observers = new LinkedList<IJobObserver>();
	}

	/**
	 *
	 */
	protected void onStart()
	{
		//to handle starting of job in subclass
	}

	/**
	 *
	 */
	protected void onCancel()
	{
		//to handle cancellation of job in subclass
	}

	/**
	 *
	 */
	protected void onComplete()
	{
		//to handle job completion in subclass
	}

	/**
	 *
	 */
	protected void onFail()
	{
		//to handle job failure in subclass
	}

	/**
	 *
	 */
	public final void start()
	{
		onStart();

		_running = true;
	}

	/**
	 *
	 */
	public final void cancel()
	{
		onCancel();

		_running = false;

		dispatchJobCancelled( this );
	}

	/**
	 *
	 */
	public final void complete()
	{
		onComplete();

		_running = false;

		dispatchJobCompleted( this );
	}

	/**
	 *
	 */
	public final void fail( Error error )
	{
		onFail();

		_running = false;

		dispatchJobFailed( this, error );
	}

	/**
	 *
	 */
	public final void addObserver( IJobObserver observer )
	{
		if( hasObserver( observer ) )
		{
			throw new ElementAlreadyExistsError( this );
		}

		_observers.addFirst( observer );
	}

	/**
	 *
	 */
	public final void removeObserver( IJobObserver observer )
	{
		if( !hasObserver( observer ) )
		{
			throw new ElementDoesNotExistError( this );
		}

		_observers.remove( observer );
	}

	/**
	 *
	 */
	public final boolean hasObserver( IJobObserver observer )
	{
		final int index = _observers.indexOf( observer );

		return ( -1 != index );
	}

	/**
	 *
	 */
	protected final void dispatchJobCompleted( IJob job )
	{
		int n = _observers.size();

		while( --n > -1 )
		{
			_observers.get( n ).onJobCompleted( job );
		}
	}

	/**
	 *
	 */
	protected final void dispatchJobCancelled( IJob job )
	{
		int n = _observers.size();

		while( --n > -1 )
		{
			_observers.get( n ).onJobCancelled( job );
		}
	}

	/**
	 *
	 */
	protected final void dispatchJobFailed( IJob job, Error error )
	{
		int n = _observers.size();

		while( --n > -1 )
		{
			_observers.get( n ).onJobFailed( job, error );
		}
	}

	/**
	 *
	 */
	protected final void dispatchJobProgress( IJob job, float progress )
	{
		int n = _observers.size();

		while( --n > -1 )
		{
			_observers.get( n ).onJobProgress( job, progress );
		}
	}

	/**
	 *
	 */
	public final boolean isRunning()
	{
		return _running;
	}

	/**
	 * Frees all references of the object.
	 */
	public void dispose()
	{
		while( !_observers.isEmpty() )
		{
			_observers.removeLast();
		}

		_observers = null;
	}

	/**
	 * Creates and returns a string representation of the Job object.
	 */
	@Override
	public String toString()
	{
		return "[Job]";
	}
}

