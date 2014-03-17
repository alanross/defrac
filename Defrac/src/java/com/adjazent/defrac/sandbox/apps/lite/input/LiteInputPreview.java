package com.adjazent.defrac.sandbox.apps.lite.input;

import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteInputSource;
import com.adjazent.defrac.sandbox.apps.lite.core.dnd.ILiteDragItem;
import com.adjazent.defrac.ui.surface.UISurface;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteInputPreview extends UISurface implements ILiteDragItem
{
	private LiteInputSource _data;

	public LiteInputPreview()
	{
		super();

		resizeTo( 180, 100 );
	}

	public void setup( LiteInputSource data )
	{
		setSkin( data.preview );

		_data = data;
	}

	@Override
	public Object getDndData()
	{
		return _data;
	}

	@Override
	public String toString()
	{
		return "[LiteInputPreview]";
	}
}

