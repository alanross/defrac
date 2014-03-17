package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.data.ILiteSceneObserver;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteInputSource;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneItem;
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
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneEditor extends UISurface implements ILiteDropTarget, ILiteSceneObserver
{
	private Layer _elementLayer = new Layer();
	private Layer _resizerLayer = new Layer();

	private UISurface _outline;

	private LiteSceneResizeHandles _resizer;

	private Rectangle _itemOriginalDim = new Rectangle();
	private Rectangle _itemRestrictDim = new Rectangle();
	private Point _dragStart = new Point();
	private boolean _dragEnabled;
	private boolean _dragActive;

	private LiteSceneItem _activeItem;
	private UISurface _activeHandle;

	private boolean _enabled = true;

	private Hashtable<LiteSceneItem, LiteSceneItemView> _items;

	private LiteScene _model;

	public LiteSceneEditor( IUISkin skin )
	{
		super( skin );

		_items = new Hashtable<LiteSceneItem, LiteSceneItemView>();

		_resizer = new LiteSceneResizeHandles( _resizerLayer );

		_resizerLayer.visible( false );

		_outline = LiteCore.ui.createSurface( "ActiveOutline", 0, 0, ( int ) width(), ( int ) height() );
		_outline.visible( false );

		addChild( _elementLayer );
		addChild( _resizerLayer );
		addChild( _outline );
	}

	private Rectangle createItemDimensions( Point global )
	{
		final float dx = ( global.x - _dragStart.x );
		final float dy = ( global.y - _dragStart.y );

		Rectangle d;

		if( _activeHandle != null )
		{
			d = _resizer.calculateDimensions( _activeHandle, _itemOriginalDim, dx, dy );

			if( d.x > _itemRestrictDim.x )
			{
				d.x = _itemRestrictDim.x;
			}
			if( d.y > _itemRestrictDim.y )
			{
				d.y = _itemRestrictDim.y;
			}
			if( d.width < _itemRestrictDim.width )
			{
				d.width = _itemRestrictDim.width;
			}
			if( d.height < _itemRestrictDim.height )
			{
				d.height = _itemRestrictDim.height;
			}
		}
		else
		{
			d = new Rectangle( _itemOriginalDim.x + dx, _itemOriginalDim.y + dy, _itemOriginalDim.width, _itemOriginalDim.height );
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
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
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
		if( ( uiEvent.type & UIEventType.ACTION ) == 0 )
		{
			return;
		}

		if( _activeItem == null )
		{
			return;
		}

		UIActionEvent event = ( UIActionEvent ) uiEvent;

		Point global = new Point( event.pos.x, event.pos.y );

		if( event.type == UIEventType.ACTION_BEGIN )
		{
			_dragStart.set( global.x, global.y );
			_dragEnabled = true;
			_dragActive = false;

			_activeHandle = _resizer.getHandle( event.target );

			_itemOriginalDim.set( _activeItem.x(), _activeItem.y(), _activeItem.width(), _activeItem.height() );

			_itemRestrictDim.x = _activeItem.x() + _activeItem.width() - _activeItem.minWidth;
			_itemRestrictDim.y = _activeItem.y() + _activeItem.height() - _activeItem.minHeight;
			_itemRestrictDim.width = _activeItem.minWidth;
			_itemRestrictDim.height = _activeItem.minHeight;
		}
		if( event.type == UIEventType.ACTION_MOVE )
		{
			_dragActive = _dragEnabled;

			if( _dragActive )
			{
				_activeItem.setDimensions( createItemDimensions( global ) );
			}
		}
		if( event.type == UIEventType.ACTION_END )
		{
			if( _dragActive )
			{
				_activeItem.setDimensions( createItemDimensions( global ) );
			}

			_dragEnabled = false;
			_dragActive = false;
		}
		if( event.type == UIEventType.ACTION_DOUBLE )
		{
			_activeItem.setDimensions( new Rectangle( 0, 0, _elementLayer.width(), _elementLayer.height() ) );
		}
	}

	@Override
	public void onLiteSceneModified( LiteSceneItem item, int type )
	{
		if( type == LiteScene.ITEM_ATTACHED )
		{
			addItem( item );
		}
		if( type == LiteScene.ITEM_DETACHED )
		{
			removeItem( item );
		}
		if( type == LiteScene.ITEM_SELECTED )
		{
			selectItem( item );
		}
		if( type == LiteScene.ITEM_MODIFIED )
		{
			modifyItem( item );
		}
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

		if( _model == null )
		{
			return;
		}

		String itemID = "Item " + _model.numItems();

		IUISkin s = ( ( LiteInputSource ) dragItem.getDndData() ).full.clone();

		Point local = _elementLayer.globalToLocal( new Point( pos.x, pos.y ) );

		_model.add( new LiteSceneItem( itemID, s, local.x, local.y, s.getDefaultWidth(), s.getDefaultHeight() ) );
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
		_resizerLayer.visible( false );
		_activeItem = null;

		Enumeration<LiteSceneItem> items = _items.keys();

		while( items.hasMoreElements() )
		{
			removeItem( items.nextElement() );
		}

		if( _model != null )
		{
			_model.removeObserver( this );
		}

		_model = scene;
		_model.addObserver( this );

		for( int i = 0; i < _model.numItems(); i++ )
		{
			addItem( _model.get( i ) );
		}
	}

	private void addItem( LiteSceneItem item )
	{
		LiteSceneItemView view = new LiteSceneItemView( item );

		_items.put( item, view );

		_elementLayer.addChild( view );
	}

	private void removeItem( LiteSceneItem item )
	{
		LiteSceneItemView view = _items.remove( item );

		_activeItem = null;
		_resizerLayer.visible( false );

		_elementLayer.removeChild( view );
	}

	private void selectItem( LiteSceneItem item )
	{
		LiteSceneItemView view = _items.get( item );

		_activeItem = item;

		_resizerLayer.visible( true );
		_resizer.setPosition( view );
	}

	private void modifyItem( LiteSceneItem item )
	{
		LiteSceneItemView view = _items.get( item );

		_activeItem = item;

		view.update( item.dimensions() );

		_resizerLayer.visible( true );
		_resizer.setPosition( view );
	}

	public void enabled( boolean value )
	{
		if( _enabled != value )
		{
			_enabled = value;

			_activeItem = null;
			_resizerLayer.visible( false );
		}
	}

	public boolean enabled()
	{
		return _enabled;
	}

	@Override
	public String toString()
	{
		return "[LiteSceneEditor]";
	}
}