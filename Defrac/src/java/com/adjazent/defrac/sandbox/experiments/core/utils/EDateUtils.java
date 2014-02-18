package com.adjazent.defrac.sandbox.experiments.core.utils;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.DateUtils;
import com.adjazent.defrac.sandbox.experiments.Experiment;

import java.util.Date;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EDateUtils extends Experiment
{
	public EDateUtils()
	{
	}

	protected void onInit()
	{
		Date d = new Date();

		Log.info( Context.DEFAULT, this, d.getTime() );
		Log.info( Context.DEFAULT, this, "getDDMonthYYYYHHMMSS: ", DateUtils.getDDMonthYYYYHHMMSS( d ) );
		Log.info( Context.DEFAULT, this, "getTimeStamp: ", DateUtils.getTimeStamp() );
		Log.info( Context.DEFAULT, this, "milliToMinSec: ", DateUtils.milliToMinSec(60000) ); //1 min
		Log.info( Context.DEFAULT, this, "getApproximation: ", DateUtils.getApproximation(600000) ); //1 hour
	}

	@Override
	public String toString()
	{
		return "[EDateUtils]";
	}
}