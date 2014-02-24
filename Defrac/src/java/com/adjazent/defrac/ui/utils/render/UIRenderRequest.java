package com.adjazent.defrac.ui.utils.render;

import com.adjazent.defrac.core.stage.StageProvider;
import defrac.event.StageEvent;
import defrac.lang.Procedure;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIRenderRequest
{
	private final Procedure<StageEvent.Render> procedure = new Procedure<StageEvent.Render>()
	{
		@Override
		public void apply( StageEvent.Render event )
		{
			repaint();
		}
	};

	private boolean _invalidated = false;
	private IUIRenderListener _listener;

	public UIRenderRequest( IUIRenderListener listener )
	{
		_listener = listener;
	}

	public void invalidate()
	{
		if( !_invalidated )
		{
			_invalidated = true;

			StageProvider.stage.onRender.attach( procedure );
		}
	}

	private void repaint()
	{
		StageProvider.stage.onRender.detach( procedure );

		_listener.render();

		_invalidated = false;
	}

	@Override
	public String toString()
	{
		return "[UIRenderRequest]";
	}
}