package com.adjazent.defrac.sandbox.apps.model.input;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7InputListSet
{
	public static final Pro7InputList live = new Pro7InputList();
	public static final Pro7InputList vod = new Pro7InputList();
	public static final Pro7InputList img = new Pro7InputList();

	public Pro7InputListSet()
	{
	}

	@Override
	public String toString()
	{
		return "[Pro7InputListSet]";
	}
}