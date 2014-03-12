package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneElement;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneEditorElement extends UISurface
{
	public final float minWidth = 40;
	public final float minHeight = 40;

	private LiteSceneEditor _editor;

	private LiteSceneElement _model;

	public LiteSceneEditorElement( LiteSceneElement model )
	{
		super( model.skin );

		_model = model;

		super.moveTo( _model.dim.x, _model.dim.y );
		super.resizeTo( _model.dim.width, _model.dim.height );
	}

	public void attach( LiteSceneEditor editor )
	{
		_editor = editor;
	}

	public void detach( LiteSceneEditor editor )
	{
		_editor = null;
	}

	public void setDimensions( Rectangle d )
	{
		_model.dim.set( d.x, d.y, d.width, d.height );

		super.moveTo( d.x, d.y );
		super.resizeTo( d.width, d.height );

		if( _editor != null )
		{
			_editor.onUpdated( this );
		}
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent uiEvent )
	{
		if( uiEvent.target == this )
		{
			if( uiEvent.type == UIEventType.FOCUS_IN )
			{
				_editor.activate( this );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneEditorElement]";
	}
}