package com.adjazent.defrac.sandbox.experiments.lang;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.experiments.Experiment;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EDataTypes extends Experiment
{
	public EDataTypes()
	{
	}

	@Override
	protected void onInit()
	{
		Log.info( Context.DEFAULT, "Min byte value = " + Byte.MIN_VALUE );
		Log.info( Context.DEFAULT, "Max byte value = " + Byte.MAX_VALUE );
		Log.info( Context.DEFAULT, "Min short value = " + Short.MIN_VALUE );
		Log.info( Context.DEFAULT, "Max short value = " + Short.MAX_VALUE );
		Log.info( Context.DEFAULT, "Min int value = " + Integer.MIN_VALUE );
		Log.info( Context.DEFAULT, "Max int value = " + Integer.MAX_VALUE );
		Log.info( Context.DEFAULT, "Min long value = " + Long.MIN_VALUE );
		Log.info( Context.DEFAULT, "Max long value = " + Long.MAX_VALUE );
		Log.info( Context.DEFAULT, "Min float value = " + Float.MIN_VALUE );
		Log.info( Context.DEFAULT, "Max float value = " + Float.MAX_VALUE );
		Log.info( Context.DEFAULT, "Min double value = " + Double.MIN_VALUE );
		Log.info( Context.DEFAULT, "Max double value = " + Double.MAX_VALUE );

		Log.info( Context.DEFAULT, "-------------------------" );

		Log.info( Context.DEFAULT, "PRIMITIVE BYTE", new byte[]{ 2, 3, 4 } );
		Log.info( Context.DEFAULT, "PRIMITIVE SHORT", new short[]{ 5, 6, 7 } );
		Log.info( Context.DEFAULT, "PRIMITIVE INT", new int[]{ 8, 9, 10 } );
		Log.info( Context.DEFAULT, "PRIMITIVE LONG", new long[]{ 11L, 12L, 13L } );
		Log.info( Context.DEFAULT, "PRIMITIVE FLOAT", new float[]{ 1.1f, 2.2f, 3.3f } );
		Log.info( Context.DEFAULT, "PRIMITIVE DOUBLE", new double[]{ 1.1, 2.2, 3.3 } );
		Log.info( Context.DEFAULT, "PRIMITIVE BOOLEAN", new boolean[]{ true, false, true } );
		Log.info( Context.DEFAULT, "PRIMITIVE CHAR", new char[]{ 'a', 'b', 'c' } );
		Log.info( Context.DEFAULT, "OBJECT STRING", new String[]{ "hello", "world" } );
		Log.info( Context.DEFAULT, "OBJECT INTEGER", new Integer[]{ 6, 7, 8 } );
	}

	@Override
	public String toString()
	{
		return "[EDataTypes]";
	}
}