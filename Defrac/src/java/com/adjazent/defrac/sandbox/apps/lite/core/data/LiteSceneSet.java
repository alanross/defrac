package com.adjazent.defrac.sandbox.apps.lite.core.data;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneSet
{
	public final int id;
	public final LiteScene a;
	public final LiteScene b;

	public LiteSceneSet( int id, LiteScene a, LiteScene b )
	{
		this.id = id;
		this.a = a;
		this.b = b;
	}

	public void reset()
	{
		a.reset();
		b.reset();
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSet id:" + id + "]";
	}
}