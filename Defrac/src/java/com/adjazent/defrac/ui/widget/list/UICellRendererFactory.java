package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.math.MMath;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UICellRendererFactory implements IUICellRendererFactory
{
	private LinkedList<UICellRenderer> _rendererPool = new LinkedList<UICellRenderer>();
	private int _rendererPoolGrowthRate = 8;

	public UICellRendererFactory()
	{
	}

	public UICellRenderer create( int index )
	{
		if( 0 >= _rendererPool.size() )
		{
			int n = _rendererPoolGrowthRate;

			while( --n > -1 )
			{
				_rendererPool.addLast( new UICellRenderer() );
			}
		}

		UICellRenderer renderer = _rendererPool.pollLast();

		renderer.setColor( ( index % 2 == 0 ) ? ( ( int ) MMath.rand( 0xFF000000, 0xFFFFFFFF ) ) : 0xFF696969 );

		return renderer;
	}

	public void release( UICellRenderer renderer )
	{
		_rendererPool.addLast( renderer );
	}

	@Override
	public String toString()
	{
		return "[UICellRendererFactory]";
	}
}

