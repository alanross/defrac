package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.utils.render.IUIRenderListener;
import com.adjazent.defrac.ui.utils.render.UIRenderRequest;
import com.adjazent.defrac.ui.widget.UIActionType;
import defrac.display.DisplayObjectContainer;
import defrac.display.Layer;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIList extends UISurface implements IUIRenderListener, IActionObserver
{
	public final Action onSelect = new Action( UIActionType.CELL_SELECT );

	private final LinkedList<UICellData> _items = new LinkedList<UICellData>();
	private final LinkedList<IUICellRenderer> _renderer = new LinkedList<IUICellRenderer>();
	private final IUICellRendererFactory _factory;

	private final Layer _container = new Layer();

	private final UIRenderRequest _renderRequest;

	private UIListInteractions _interactions;

	private int _itemsHeight = 0;
	private int _offset = 0;
	private int _cellSpacing = 1;
	private UICellData _selectedItem;
	private boolean _multiSelection = false;

	public UIList( IUICellRendererFactory rendererFactory )
	{
		super();

		_factory = rendererFactory;

		_renderRequest = new UIRenderRequest( this );

		addChild( _container );
	}

	public UIList( IUISkin skin, IUICellRendererFactory rendererFactory )
	{
		super( skin );

		_factory = rendererFactory;

		_renderRequest = new UIRenderRequest( this );

		addChild( _container );
	}

	public UIList( IUICellRendererFactory rendererFactory, UIListInteractions interactions )
	{
		_factory = rendererFactory;

		_renderRequest = new UIRenderRequest( this );

		addChild( _container );

		_interactions = interactions;
		_interactions.attach( this );
	}

	public UIList( IUISkin skin, IUICellRendererFactory rendererFactory, UIListInteractions interactions )
	{
		super( skin );

		_factory = rendererFactory;

		_renderRequest = new UIRenderRequest( this );

		addChild( _container );

		_interactions = interactions;
		_interactions.attach( this );
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.type == UIActionType.CELL_SELECT )
		{
			if( action.origin != _selectedItem )
			{
				if( _selectedItem != null && !_multiSelection )
				{
					_selectedItem.selected( false );
				}

				_selectedItem = ( UICellData ) action.origin;

				onSelect.send( this );
			}
		}
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		if( bounds.contains( local.x, local.y ) )
		{
			int n = _renderer.size();

			while( --n > -1 )
			{
				DisplayObjectContainer c = _renderer.get( n ).getContainer();

				if( c.captureEventTarget( point ) != null )
				{
					return c;
				}
			}

			return this;
		}

		return null;
	}

	private void addRenderer( float viewWidth, boolean top, IUICellRenderer renderer, UICellData item )
	{
		if( top )
		{
			_renderer.addFirst( renderer );
		}
		else
		{
			_renderer.addLast( renderer );
		}

		_container.addChild( renderer.getContainer() );

		renderer.onAttach( item, viewWidth, ( float ) item.getHeight() );

		item.inViewRange = true;
	}

	private void removeRenderer( IUICellRenderer renderer, UICellData item )
	{
		renderer.onDetach();

		item.inViewRange = false;

		_renderer.remove( renderer );

		_container.removeChild( renderer.getContainer() );

		_factory.release( renderer );
	}

	private IUICellRenderer getAssociatedRenderer( UICellData data )
	{
		int n = _renderer.size();

		while( --n > -1 )
		{
			if( _renderer.get( n ).getData() == data )
			{
				return _renderer.get( n );
			}
		}

		return null;
	}

	private void measure()
	{
		int result = 0;

		for( UICellData _item : _items )
		{
			result += _item.getHeight() + _cellSpacing;
		}

		_itemsHeight = result;
	}

	public void render()
	{
		if( bounds.width < 1 || bounds.height < 1 )
		{
			throw new GenericError( this + " Invalid size" );
		}

		final int viewWidth = ( int ) bounds.width;
		final int viewHeight = ( int ) bounds.height;
		final int minView = _offset;
		final int maxView = _offset + viewHeight;
		final int numItems = _items.size();
		int itemOffset = 0;
		IUICellRenderer renderer;

		Stack<IUICellRenderer> processed = new Stack<IUICellRenderer>();

		for( int i = 0; i < numItems; ++i )
		{
			UICellData item = _items.get( i );

			if( itemOffset + item.getHeight() < minView || itemOffset > maxView )
			{
				if( item.inViewRange )
				{
					renderer = getAssociatedRenderer( item );

					removeRenderer( renderer, item );
				}
			}
			else
			{
				if( !item.inViewRange )
				{
					renderer = _factory.create( i );

					addRenderer( viewWidth, ( itemOffset < _offset ), renderer, item );
				}
				else
				{
					renderer = getAssociatedRenderer( item );
				}

				renderer.setY( itemOffset - _offset );

				processed.push( renderer );
			}

			itemOffset += item.getHeight() + _cellSpacing;
		}

		int n = _renderer.size();

		while( --n > -1 )
		{
			renderer = _renderer.get( n );

			if( !processed.contains( renderer ) )
			{
				removeRenderer( renderer, renderer.getData() );
			}
			else
			{
				processed.remove( renderer );
			}
		}
	}

	public void addItem( UICellData item )
	{
		if( hasItem( item ) )
		{
			throw new ElementAlreadyExistsError();
		}

		_items.addLast( item );

		item.onSelect.add( this );

		measure();

		_renderRequest.invalidate();
	}

	public void removeItem( UICellData item )
	{
		if( !hasItem( item ) )
		{
			throw new ElementDoesNotExistError();
		}

		if( _selectedItem == item )
		{
			_selectedItem = null;
		}

		item.onSelect.remove( this );

		_items.remove( item );

		measure();

		_renderRequest.invalidate();
	}

	public void removeItemAt( int index )
	{
		if( index < 0 || index >= _items.size() )
		{
			throw new ElementDoesNotExistError();
		}

		removeItem( _items.get( index ) );
	}

	public void removeAllItems()
	{
		int n = _items.size();

		while( --n > -1 )
		{
			UICellData item = _items.get( n );

			item.onSelect.remove( this );

			_items.remove( item );
		}

		_selectedItem = null;

		measure();

		_renderRequest.invalidate();
	}

	public boolean hasItem( UICellData item )
	{
		return ( -1 != _items.indexOf( item ) );
	}

	public UICellData getItem( int index )
	{
		return _items.get( index );
	}

	public int numItems()
	{
		return _items.size();
	}

	public void resizeTo( int width, int height )
	{
		if( width < 1 || height < 1 )
		{
			throw new GenericError( this + " Invalid size" );
		}

		if( bounds.width != width || bounds.height != height )
		{
			super.resizeTo( width, height );

			scrollRect( new Rectangle( 0, 0, width, height ) );

			_renderRequest.invalidate();
		}
	}

	public int getOffset()
	{
		return _offset;
	}

	public void setOffset( int value )
	{
		value = ( _itemsHeight < bounds.height ) ? 0 : MMath.clampInt( value, 0, ( int ) ( _itemsHeight - bounds.height ) );

		if( _offset != value )
		{
			_offset = value;

			_renderRequest.invalidate();
		}
	}

	public void multiSelection( boolean value )
	{
		_multiSelection = value;
	}

	public int getItemsHeight()
	{
		return _itemsHeight;
	}

	public int getCellSpacing()
	{
		return _cellSpacing;
	}

	public UICellData getSelectedItem()
	{
		return _selectedItem;
	}

	public int getSelectedIndex()
	{
		return ( _selectedItem != null ) ? _items.indexOf( _selectedItem ) : -1;
	}

	@Override
	public String toString()
	{
		return "[UIList id:" + id + "]";
	}
}