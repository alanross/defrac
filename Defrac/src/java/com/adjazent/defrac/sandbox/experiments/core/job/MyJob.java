package com.adjazent.defrac.sandbox.experiments.core.job;

import com.adjazent.defrac.core.job.Job;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import defrac.event.TimerEvent;
import defrac.lang.Procedure;
import defrac.util.Timer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class MyJob extends Job
{
	private final Procedure<TimerEvent.Tick> timerTickProcedure = new Procedure<TimerEvent.Tick>()
	{
		@Override
		public void apply( TimerEvent.Tick event )
		{
			delayedProgress();
		}
	};

	private String _name;
	private float _progress;
	private Timer _timer;

	public MyJob( String name )
	{
		_name = name;
		_timer = new Timer( 50 );
		_timer.onTick.attach( timerTickProcedure );
	}

	@Override
	protected void onStart()
	{
		Log.info( Context.DEFAULT, "started Job: " + _name );

		_progress = 0.0f;

		_timer.start();
	}

	private void delayedProgress()
	{
		_progress += .1f;

		if( _progress >= 1.0f )
		{
			_timer.stop();
			complete();
		}
		else
		{
			dispatchJobProgress( this, _progress );
		}
	}

	@Override
	protected void onCancel()
	{
		Log.info( Context.DEFAULT, "cancel Job: " + _name );
	}

	@Override
	public void dispose()
	{
		super.dispose();

		_name = null;
	}

	@Override
	public String toString()
	{
		return "[MyJob name " + _name + "]";
	}
}