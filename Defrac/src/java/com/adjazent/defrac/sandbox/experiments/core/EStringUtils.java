package com.adjazent.defrac.sandbox.experiments.core;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.sandbox.Experiment;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EStringUtils extends Experiment
{
	public EStringUtils()
	{
	}

	protected void onInit()
	{
		Log.info( Context.DEFAULT, this, "trim", StringUtils.trim( "Hello World" ) );
		Log.info( Context.DEFAULT, this, "trim", StringUtils.trim( " Hello World" ) );
		Log.info( Context.DEFAULT, this, "trim", StringUtils.trim( "Hello World " ) );
		Log.info( Context.DEFAULT, this, "trim", StringUtils.trim( " Hello World " ) );
		Log.info( Context.DEFAULT, this, "trim", StringUtils.trim( "    Hello World " ) );
		Log.info( Context.DEFAULT, this, "trim", StringUtils.trim( "    Hello World     " ) );

		Log.info( Context.DEFAULT, this, "reverse", StringUtils.reverse( "Hello World" ) );

		Log.info( Context.DEFAULT, this, "cut", StringUtils.cut( "Hello <CUT THIS OUT> World", "<", ">" ) );

		Log.info( Context.DEFAULT, this, "getLevenshteinDistance", StringUtils.getLevenshteinDistance( "Hello", "Hello" ) );
		Log.info( Context.DEFAULT, this, "getLevenshteinDistance", StringUtils.getLevenshteinDistance( "Hello", "Hello2" ) );
		Log.info( Context.DEFAULT, this, "getLevenshteinDistance", StringUtils.getLevenshteinDistance( "Hello", "hello" ) );

		Log.info( Context.DEFAULT, this, "randomSequence", StringUtils.randomSequence( 5 ) );
		Log.info( Context.DEFAULT, this, "randomSequence", StringUtils.randomSequence( 5 ) );
		Log.info( Context.DEFAULT, this, "randomSequence", StringUtils.randomSequence( 5 ) );

		Log.info( Context.DEFAULT, this, "split", StringUtils.split( "Hello world, how are you?", ' ' ) );
	}

	@Override
	public String toString()
	{
		return "[EStringUtils]";
	}
}