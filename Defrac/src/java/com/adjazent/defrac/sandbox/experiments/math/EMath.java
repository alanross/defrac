package com.adjazent.defrac.sandbox.experiments.math;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.math.MColor;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.sandbox.Experiment;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EMath extends Experiment
{
	public EMath()
	{

	}

	@Override
	protected void onInit()
	{
		Log.info( Context.DEFAULT, this, MColor.hex2argb( MColor.str2hex( "#FFFF0000" ) ) );

		Log.info( Context.DEFAULT, this, "abs", MMath.abs( -1.01 ) );
		Log.info( Context.DEFAULT, this, "average", MMath.average( new double[]{ 1, 2, 3 } ) );
		Log.info( Context.DEFAULT, this, "circleArea", MMath.circleArea( 2 ) );
		Log.info( Context.DEFAULT, this, "circleCircumference", MMath.circleCircumference( 2 ) );
		Log.info( Context.DEFAULT, this, "clamp", MMath.clamp( -1.01, 0.0, 1.0 ) );
		Log.info( Context.DEFAULT, this, "clampInt", MMath.clampInt( 100, 0, 10 ) );
		Log.info( Context.DEFAULT, this, "cumulatedPercent", MMath.cumulatedPercent( 100, 1, 1 ) );
		Log.info( Context.DEFAULT, this, "deg2rad", MMath.deg2rad( 180 ) );
		Log.info( Context.DEFAULT, this, "dist", MMath.dist( 0, 0, 2, 2 ) );
		Log.info( Context.DEFAULT, this, "distToCircleCenter", MMath.distToCircleCenter( 5, 5, 6 ) );
		Log.info( Context.DEFAULT, this, "equals", MMath.equals( 0, 0.2, 0.5 ) );
		Log.info( Context.DEFAULT, this, "equals2D", MMath.equals2D( new MPoint( 1.0, 1.0 ), new MPoint( 1.1, 1.1 ), 0.2 ) );
		Log.info( Context.DEFAULT, this, "getDirection2D", MMath.getDirection2D( new MPoint( 0, 0 ), new MPoint( 1, 1 ) ) );
		Log.info( Context.DEFAULT, this, "grid", MMath.grid( 2, 2, 1, 1, 0, 0 ) );
		Log.info( Context.DEFAULT, this, "grid2", MMath.grid2( 20, 20, 10, 10, 0, 0 ) );
		Log.info( Context.DEFAULT, this, "int2Binary", MMath.int2Binary( 8 ) );
		Log.info( Context.DEFAULT, this, "isEven", MMath.isEven( 2 ) );
		Log.info( Context.DEFAULT, this, "isInBounds", MMath.isInBounds( 2, 1, 3 ) );
		Log.info( Context.DEFAULT, this, "max", MMath.max( 0, 1 ) );
		Log.info( Context.DEFAULT, this, "min", MMath.min( 0, 1 ) );
		Log.info( Context.DEFAULT, this, "nextPowerOfTwo", MMath.nextPowerOfTwo( 7 ) );
		Log.info( Context.DEFAULT, this, "normalize", MMath.normalize( 75, 50, 100 ) );
		Log.info( Context.DEFAULT, this, "rad2deg", MMath.rad2deg( 3.14 ) );
		Log.info( Context.DEFAULT, this, "rand", MMath.rand( 3, 5 ) );
		Log.info( Context.DEFAULT, this, "round", MMath.round( 1.4995 ) );
		Log.info( Context.DEFAULT, this, "roundToPrecision", MMath.roundToPrecision( 1.2345, 2 ) );
		Log.info( Context.DEFAULT, this, "scale", MMath.scale( 0.5, 0, 1, 0, 100 ) );
		Log.info( Context.DEFAULT, this, "sum", MMath.sum( new double[]{ 1, 2, 3 } ) );

		MPoint p = new MPoint( 3.2, 1.4 );
		MMath.quantize2D( p, 2, 2 );
		Log.info( Context.DEFAULT, this, "quantize2D", p );
	}

	@Override
	public String toString()
	{
		return "[EMath]";
	}
}