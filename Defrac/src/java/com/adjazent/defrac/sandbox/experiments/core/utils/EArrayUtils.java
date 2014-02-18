package com.adjazent.defrac.sandbox.experiments.core.utils;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.ArrayUtils;
import com.adjazent.defrac.sandbox.experiments.Experiment;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EArrayUtils extends Experiment
{
	public EArrayUtils()
	{
	}

	@Override
	protected void onInit()
	{
		Log.info( Context.DEFAULT, this, "permute", ArrayUtils.permute( new Integer[]{ 1, 2, 3 } ) );
		Log.info( Context.DEFAULT, this, "shuffle", ArrayUtils.shuffle( new Integer[]{ 1, 2, 3, 4, 5 } ) );
		Log.info( Context.DEFAULT, this, "shuffleFisherYates", ArrayUtils.shuffleFisherYates( new Integer[]{ 1, 2, 3, 4, 5 } ) );

		Log.info( Context.DEFAULT, this, "ARRAY INT1 " + ArrayUtils.join( new Integer[]{ 1, 2, 3 }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY INT2 " + ArrayUtils.join( new Integer[]{ 1 }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY INT3 " + ArrayUtils.join( new Integer[]{ }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY STRING1 " + ArrayUtils.join( new String[]{ "X", "Y", "Z", null }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY STRING2 " + ArrayUtils.join( new String[]{ "X", null }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY STRING3 " + ArrayUtils.join( new String[]{ "X" }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY STRING4 " + ArrayUtils.join( new String[]{ }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY OBJECT1 " + ArrayUtils.join( new Test[]{ new Test( "A" ), new Test( "B" ), new Test( "C" ) }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY OBJECT2 " + ArrayUtils.join( new Test[]{ new Test( "A" ) }, ", " ) );
		Log.info( Context.DEFAULT, this, "ARRAY OBJECT3 " + ArrayUtils.join( new Test[]{ }, ", " ) );
	}

	private class Test
	{
		private String _str;

		public Test( String str )
		{
			_str = str;
		}

		@Override
		public String toString()
		{
			return "[Test " + _str + "]";
		}
	}

	@Override
	public String toString()
	{
		return "[EArrayUtils]";
	}
}