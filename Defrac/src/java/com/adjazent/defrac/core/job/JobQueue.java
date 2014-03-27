package com.adjazent.defrac.core.job;

import com.adjazent.defrac.core.error.*;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class JobQueue extends Job implements IJobObserver
{
	private LinkedList<IJob> _jobs;

	private IJob _active;

	private int _index;

	/**
	 * Creates a new instance of JobQueue.
	 */
	public JobQueue()
	{
		_jobs = new LinkedList<IJob>();
		_index = -1;
		_active = null;
	}

	/**
	 *
	 */
	private void process()
	{
		if( ++_index < _jobs.size() )
		{
			_active = _jobs.get( _index );
			_active.addObserver( this );
			_active.start();
		}
		else
		{
			complete();
		}
	}

	@Override
	protected void onStart()
	{
		if( isRunning() )
		{
			throw new GenericError( this, "Can't start JobQueue, it is already running." );
		}

		_index = -1;

		process();
	}

	@Override
	protected void onCancel()
	{
		if( !isRunning() )
		{
			throw new GenericError( this, "Can't cancel JobQueue, it is not running." );
		}

		// One can either cancel the JobQueue oder cancel a Job directly.
		// If a job was cancelled active will be null;
		if( _active != null )
		{
			_active.removeObserver( this );
			_active.cancel();
		}
	}

	/**
	 * Frees all references of the object.
	 */
	@Override
	public void dispose()
	{
		super.dispose();

		while( !_jobs.isEmpty() )
		{
			_jobs.removeLast();
		}

		_jobs = null;
	}

	/**
	 *
	 */
	public JobQueue addJob( IJob job )
	{
		if( isRunning() )
		{
			throw new GenericError( this, "Can not addJob Job while JobQueue is running." );
		}

		if( hasJob( job ) )
		{
			throw new ElementAlreadyExistsError( this );
		}

		_jobs.addLast( job );

		return this;
	}

	/**
	 *
	 */
	public JobQueue removeJob( IJob job )
	{
		if( isRunning() )
		{
			throw new GenericError( this, "Can not removeTextureSkin Job while JobQueue is running." );
		}

		if( !hasJob( job ) )
		{
			throw new ElementDoesNotExistError( this );
		}

		_jobs.remove( job );

		return this;
	}

	/**
	 *
	 */
	public void removeJobs()
	{
		int n = _jobs.size();

		while( --n > -1 )
		{
			removeJob( _jobs.get( n ) );
		}
	}

	/**
	 *
	 */
	public IJob getJobAt( int index )
	{
		if( index < 0 || index >= _jobs.size() || _jobs.size() == 0 )
		{
			throw new OutOfBoundsError( index, 0, _jobs.size() );
		}

		return _jobs.get( index );
	}

	/**
	 *
	 */
	public boolean hasJob( IJob job )
	{
		final int index = _jobs.indexOf( job );

		return ( -1 != index );
	}

	/**
	 *
	 */
	public int numJobs()
	{
		return _jobs.size();
	}

	/**
	 *
	 */
	public void onJobProgress( IJob job, float progress )
	{
		dispatchJobProgress( this, ( _index + progress ) / _jobs.size() );
	}

	/**
	 *
	 */
	public void onJobCompleted( IJob job )
	{
		_active.removeObserver( this );
		_active = null;

		dispatchJobProgress( this, ( float ) ( ( _index + 1.0 ) / _jobs.size() ) );

		process();
	}

	/**
	 *
	 */
	public void onJobCancelled( IJob job )
	{
		_active.removeObserver( this );
		_active = null;

		cancel();
	}

	/**
	 *
	 */
	public void onJobFailed( IJob job, Error error )
	{
		_active.removeObserver( this );
		_active = null;

		fail( error );
	}

	/**
	 * Creates and returns a string representation of the JobQueue object.
	 */
	@Override
	public String toString()
	{
		return "[JobQueue]";
	}
}

