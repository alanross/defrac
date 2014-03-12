package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.utils.render.IUIRenderListener;
import com.adjazent.defrac.ui.utils.render.UIRenderRequest;
import defrac.display.DisplayObjectContainer;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIList extends Layer implements IUIRenderListener
{
	public String id = "";

	private LinkedList<UICellData> _items = new LinkedList<UICellData>();
	private LinkedList<IUICellRenderer> _renderer = new LinkedList<IUICellRenderer>();
	private IUICellRendererFactory _factory;

	private Layer _container = new Layer();
	private Quad _background = new Quad( 1, 1, 0x0 );

	private UIRenderRequest _renderRequest;

	private MRectangle _bounds = new MRectangle();
	private int _itemsHeight = 0;
	private int _offset = 0;
	private int _cellSpacing = 1;

	private UIListInteractions _interactions;

	public UIList( IUICellRendererFactory rendererFactory )
	{
		_factory = rendererFactory;

		_renderRequest = new UIRenderRequest( this );

		addChild( _background );
		addChild( _container );
	}

	public UIList( IUICellRendererFactory rendererFactory, UIListInteractions interactions )
	{
		_factory = rendererFactory;

		_renderRequest = new UIRenderRequest( this );

		addChild( _background );
		addChild( _container );

		_interactions = interactions;
		_interactions.attach( this );
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		if( _bounds.contains( local.x, local.y ) )
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
		if( _bounds.width < 1 || _bounds.height < 1 )
		{
			throw new GenericError( this + " Invalid size" );
		}

		int viewWidth = ( int ) _bounds.width;
		int viewHeight = ( int ) _bounds.height;
		int numItems = _items.size();
		int itemOffset = 0;
		int minView = _offset;
		int maxView = _offset + viewHeight;
		IUICellRenderer renderer;

		for( int i = 0; i < numItems; ++i )
		{
			UICellData item = _items.get( i );

			if( itemOffset + item.getHeight() < minView || itemOffset > maxView )
			{
				if( item.inViewRange )
				{
					renderer = getAssociatedRenderer( item );

					renderer.onDetach();

					item.inViewRange = false;

					_renderer.remove( renderer );

					_container.removeChild( renderer.getContainer() );

					_factory.release( renderer );
				}
			}
			else
			{
				if( !item.inViewRange )
				{
					renderer = _factory.create( i );

					if( itemOffset < _offset )
					{
						_renderer.addFirst( renderer );
					}
					else
					{
						_renderer.addLast( renderer );
					}

					_container.addChild( renderer.getContainer() );

					item.inViewRange = true;

					renderer.onAttach( item, ( float ) viewWidth, ( float ) item.getHeight() );
				}
				else
				{
					renderer = getAssociatedRenderer( item );
				}

				renderer.setY( itemOffset - _offset );
			}

			itemOffset += item.getHeight() + _cellSpacing;
		}
	}

	public void addItem( UICellData item )
	{
		if( hasItem( item ) )
		{
			throw new ElementAlreadyExistsError();
		}

		_items.addLast( item );

		measure();

		_renderRequest.invalidate();
	}

	public void removeItem( UICellData item )
	{
		if( !hasItem( item ) )
		{
			throw new ElementDoesNotExistError();
		}

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

		_items.remove( _items.get( index ) );

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

		if( _bounds.width != width || _bounds.height != height )
		{
			_bounds.resizeTo( width, height );
			_background.scaleToSize( width, height );

			scrollRect( new defrac.geom.Rectangle( 0, 0, width, height ) );

			_renderRequest.invalidate();
		}
	}

	public int getOffset()
	{
		return _offset;
	}

	public void setOffset( int value )
	{
		value = ( _itemsHeight < _bounds.height ) ? 0 : MMath.clampInt( value, 0, ( int ) ( _itemsHeight - _bounds.height ) );

		if( _offset != value )
		{
			_offset = value;

			_renderRequest.invalidate();
		}
	}

	public void setBackground( int color )
	{
		_background.color( color );
	}

	public int getItemsHeight()
	{
		return _itemsHeight;
	}

	public int getCellSpacing()
	{
		return _cellSpacing;
	}

	public int getWidth()
	{
		return ( int ) _bounds.width;
	}

	public int getHeight()
	{
		return ( int ) _bounds.height;
	}

	@Override
	public String toString()
	{
		return "[UIList id:" + id + "]";
	}
}