package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import com.adjazent.defrac.ui.text.UITextFormat;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UICellRendererFactory implements IUICellRendererFactory
{
	private LinkedList<IUICellRenderer> _rendererPool = new LinkedList<IUICellRenderer>();
	private int _rendererPoolGrowthRate = 8;
	private UITextFormat _textFormat;

	public UICellRendererFactory( UITextFormat textFormat )
	{
		_textFormat = textFormat;
	}

	public IUICellRenderer create( int rowIndex )
	{
		if( 0 >= _rendererPool.size() )
		{
			int n = _rendererPoolGrowthRate;

			while( --n > -1 )
			{
				_rendererPool.addLast( new UICellRenderer(
						UISkinFactory.create( 0xFF494949 ),
						UISkinFactory.create( 0xFF898989 ),
						_textFormat
				) );
			}
		}

		return _rendererPool.pollLast();
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

