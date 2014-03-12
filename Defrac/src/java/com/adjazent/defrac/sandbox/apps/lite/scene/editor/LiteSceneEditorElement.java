package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.event.*;
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

	public LiteSceneEditorElement( IUISkin skin )
	{
		super( skin );
	}

	public void attach( LiteSceneEditor editor )
	{
		_editor = editor;
	}

	public void detach( LiteSceneEditor editor )
	{
		_editor = null;
	}

	public void setDimensions( Rectangle dimensions )
	{
		setDimensions( dimensions.x, dimensions.y, dimensions.width, dimensions.height );
	}

	public void setDimensions( float x, float y, float width, float height )
	{
		super.moveTo( x, y );
		super.resizeTo( width, height );

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
			if( ( uiEvent.type & UIEventType.FOCUS ) != 0 )
			{
				if( uiEvent.type == UIEventType.FOCUS_IN )
				{
					_editor.activate( this );
				}
				else if( uiEvent.type == UIEventType.FOCUS_OUT )
				{
					//_editor.deactivate( this );
				}
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneEditorElement]";
	}
}