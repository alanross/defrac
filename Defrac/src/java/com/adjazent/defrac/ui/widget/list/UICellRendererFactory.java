package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.math.MMath;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UICellRendererFactory implements IUICellRendererFactory
{
	private LinkedList<IUICellRenderer> _rendererPool = new LinkedList<IUICellRenderer>();
	private int _rendererPoolGrowthRate = 8;

	public UICellRendererFactory()
	{
	}

	public IUICellRenderer create( int rowIndex )
	{
		if( 0 >= _rendererPool.size() )
		{
			int n = _rendererPoolGrowthRate;

			while( --n > -1 )
			{
				_rendererPool.addLast( new UICellRenderer() );
			}
		}

		IUICellRenderer renderer = _rendererPool.pollLast();

		renderer.setBackgroundColor( ( rowIndex % 2 == 0 ) ? ( ( int ) MMath.rand( 0xFF000000, 0xFFFFFFFF ) ) : 0xFF696969 );

		return renderer;
	}

	public void release( IUICellRenderer renderer )
	{
		_rendererPool.addLast( renderer );
	}

	@Override
	public String toString()
	{
		return "[UICellRendererFactory]";
	}
}

