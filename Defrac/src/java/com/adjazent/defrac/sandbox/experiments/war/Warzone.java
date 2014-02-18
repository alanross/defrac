package com.adjazent.defrac.sandbox.experiments.war;

import com.adjazent.defrac.core.error.AbstractFunctionError;
import com.adjazent.defrac.sandbox.experiments.Experiment;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Warzone extends Experiment
{
	public Warzone()
	{
//		Vector			LinkedList
// 		push()    -> 	addLast()
//		pop()     -> 	pollLast()
//		shift()   -> 	pollFirst()
//		unshift() -> 	addFirst()
	}

	@Override
	protected void onInit()
	{
		System.out.println( "HELLO" );

		throw new AbstractFunctionError(this);

		// background color
		// quad vs layer
		// layer.resize()?
		// quad alpha ?

	}

	@Override
	public String toString()
	{
		return "[Warzone]";
	}
}