package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.LiteInputSource;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneElement;
import com.adjazent.defrac.sandbox.apps.lite.core.dnd.ILiteDragItem;
import com.adjazent.defrac.sandbox.apps.lite.core.dnd.ILiteDropTarget;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.DisplayObject;
import defrac.display.Layer;
import defrac.display.event.UIActionEvent;
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
public final class LiteSceneEditor extends UISurface implements ILiteDropTarget
{
	private LiteSceneEditorResizer _resizer;

	private Layer _elementLayer = new Layer();
	private Layer _resizerLayer = new Layer();

	private UISurface _outline;

	private Rectangle _origDim = new Rectangle();
	private Rectangle _minSize = new Rectangle();
	private Point _dragStart = new Point();
	private boolean _dragEnabled;
	private boolean _dragActive;

	private LiteSceneEditorElement _activeElement;
	private UISurface _activeHandle;

	private boolean _enabled = true;

	private LiteScene _model;

	public LiteSceneEditor( IUISkin skin )
	{
		super( skin );

		_resizer = new LiteSceneEditorResizer( _resizerLayer );

		_resizerLayer.visible( false );

		_outline = LiteCore.ui.createSurface( "ActiveOutline", 0, 0, ( int ) width(), ( int ) height() );
		_outline.visible( false );

		addChild( _elementLayer );
		addChild( _resizerLayer );
		addChild( _outline );
	}

	private Rectangle createElementDimensions( Point global )
	{
		final float dx = ( global.x - _dragStart.x );
		final float dy = ( global.y - _dragStart.y );

		Rectangle d;

		if( _activeHandle != null )
		{
			d = _resizer.calculateDimensions( _activeHandle, _origDim, dx, dy );

			if( d.x > _minSize.x )
			{
				d.x = _minSize.x;
			}
			if( d.y > _minSize.y )
			{
				d.y = _minSize.y;
			}
			if( d.width < _minSize.width )
			{
				d.width = _minSize.width;
			}
			if( d.height < _minSize.height )
			{
				d.height = _minSize.height;
			}

		}
		else
		{
			d = new Rectangle( _origDim.x + dx, _origDim.y + dy, _origDim.width, _origDim.height );
		}

		int margin = 5;

		if( d.x < -( d.width - margin ) )
		{
			d.x = -( d.width - margin );
		}

		if( d.y < -( d.height - margin ) )
		{
			d.y = -( d.height - margin );
		}

		if( d.x > width() - margin )
		{
			d.x = width() - margin;
		}

		if( d.y > height() - margin )
		{
			d.y = height() - margin;
		}

		return d;
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		if( containsPoint( local.x, local.y ) )
		{
			if( _enabled )
			{
				int n = _resizerLayer.size();

				while( --n > -1 )
				{
					DisplayObject handle = _resizerLayer.getChildAt( n );

					if( handle.captureEventTarget( point ) != null )
					{
						return handle;
					}
				}

				n = _elementLayer.size();

				while( --n > -1 )
				{
					DisplayObject item = _elementLayer.getChildAt( n );

					if( item.captureEventTarget( point ) != null )
					{
						return item;
					}
				}
			}

			return this;
		}

		return null;
	}

	@Override
	public void processEvent( @Nonnull final UIEvent uiEvent )
	{
		if( ( uiEvent.type & UIEventType.ACTION ) != 0 )
		{
			if( _activeElement != null )
			{
				onActionEvent( ( UIActionEvent ) uiEvent );
			}
		}
	}

	private void onActionEvent( UIActionEvent event )
	{
		Point global = new Point( event.pos.x, event.pos.y );

		if( event.type == UIEventType.ACTION_BEGIN )
		{
			_dragStart.set( global.x, global.y );
			_dragEnabled = true;
			_dragActive = false;

			_activeHandle = _resizer.getHandle( event.target );

			_origDim.set( _activeElement.x(), _activeElement.y(), _activeElement.width(), _activeElement.height() );

			_minSize.x = _activeElement.x() + _activeElement.width() - _activeElement.minWidth;
			_minSize.y = _activeElement.y() + _activeElement.height() - _activeElement.minHeight;
			_minSize.width = _activeElement.minWidth;
			_minSize.height = _activeElement.minHeight;

		}
		if( event.type == UIEventType.ACTION_MOVE )
		{
			_dragActive = _dragEnabled;

			if( _dragActive )
			{
				_activeElement.setDimensions( createElementDimensions( global ) );
			}
		}
		if( event.type == UIEventType.ACTION_END )
		{
			if( _dragActive )
			{
				_activeElement.setDimensions( createElementDimensions( global ) );
			}

			_dragEnabled = false;
			_dragActive = false;
		}
		if( event.type == UIEventType.ACTION_DOUBLE )
		{
			_activeElement.setDimensions( new Rectangle( 0, 0, _elementLayer.width(), _elementLayer.height() ) );
		}
	}

	public DisplayObject resizeTo( float width, float height )
	{
		super.resizeTo( width, height );

		_elementLayer.scrollRect( new Rectangle( 0, 0, width - 2, height - 2 ) );
		_elementLayer.moveTo( 1, 1 );

		_resizerLayer.scrollRect( new Rectangle( 0, 0, width - 2, height - 2 ) );
		_resizerLayer.moveTo( 1, 1 );

		return this;
	}

	public void populate( LiteScene scene )
	{
		_model = scene;

		if( _activeElement != null )
		{
			deactivate( _activeElement );

			_activeElement = null;
		}

		_elementLayer.removeAllChildren();

		for( int i = 0; i < _model.numElements(); i++ )
		{
			add( new LiteSceneEditorElement( _model.get( i ) ) );
		}
	}

	public void add( LiteSceneEditorElement element )
	{
		element.attach( this );

		_elementLayer.addChild( element );
	}

	public void activate( LiteSceneEditorElement element )
	{
		_activeElement = element;
		_resizerLayer.visible( true );
		_resizer.setPosition( element );
	}

	public void deactivate( LiteSceneEditorElement element )
	{
		_activeElement = null;
		_resizerLayer.visible( false );
	}

	public void onUpdated( LiteSceneEditorElement element )
	{
		_resizer.setPosition( element );
	}

	public void setEnabled( boolean value )
	{
		if( _enabled != value )
		{
			_enabled = value;

			_resizerLayer.visible( _enabled );
		}
	}

	public boolean getEnabled()
	{
		return _enabled;
	}

	@Override
	public boolean onDragIn( ILiteDragItem dragItem, Point pos )
	{
		boolean accepted = ( dragItem.getDndData() instanceof LiteInputSource );

		_outline.visible( accepted );

		return accepted;
	}

	@Override
	public void onDragOut( ILiteDragItem dragItem, Point pos )
	{
		_outline.visible( false );
	}

	@Override
	public void onDragDone( ILiteDragItem dragItem, Point pos )
	{
		_outline.visible( false );

		if( dragItem.getDndData() instanceof LiteInputSource )
		{
			LiteInputSource data = ( LiteInputSource ) dragItem.getDndData();

			Point local = _elementLayer.globalToLocal( new Point( pos.x, pos.y ) );

			if( _model != null )
			{
				Rectangle dim = new Rectangle( local.x, local.y, data.full.getDefaultWidth(), data.full.getDefaultHeight() );

				LiteSceneElement element = new LiteSceneElement( dim, data.full.clone() );

				_model.add( element );

				add( new LiteSceneEditorElement( element ) );
			}
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneEditor]";
	}
}