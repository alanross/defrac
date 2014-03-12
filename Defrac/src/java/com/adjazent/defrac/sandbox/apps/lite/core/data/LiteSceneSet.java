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

	private boolean _selected = false;

	public LiteSceneSet( int id, LiteScene a, LiteScene b )
	{
		this.id = id;

		this.a = a;
		this.b = b;
	}

	public boolean selected()
	{
		return _selected;
	}

	public void selected( boolean value )
	{
		if( _selected != value )
		{
			_selected = value;

			if( _selected )
			{
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneSet]";
	}
}