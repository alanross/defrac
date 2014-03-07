package com.adjazent.defrac.sandbox.apps.model.input;

/**
 * @author Alan Ross
 * @version 0.1
 */
public abstract class Pro7Input
{
	public final String id;

	public Pro7Input( String id )
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "[Pro7Input]";
	}
}