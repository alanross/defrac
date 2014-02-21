package com.adjazent.defrac.ui.component.list;

import com.adjazent.defrac.core.error.ElementAlreadyExistsError;
import com.adjazent.defrac.core.error.ElementDoesNotExistError;
import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.stage.StageProvider;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MRectangle;
import defrac.display.DisplayObject;
import defrac.display.Layer;
import defrac.display.Quad;
import defrac.event.StageEvent;
import defrac.lang.Procedure;

import java.util.LinkedList;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIList extends Layer
{
	private Procedure<StageEvent.Render> renderProcedure = new Procedure<StageEvent.Render>()
	{
		@Override
		public void apply( StageEvent.Render event )
		{
			StageProvider.stage.onRender.detach( renderProcedure );

			render();

			_dirty = false;
		}
	};

	private LinkedList<UICellItem> _items = new LinkedList<UICellItem>();

	private LinkedList<UICellRenderer> _renderer = new LinkedList<UICellRenderer>();
	private IUICellRendererFactory _factory;

	private Layer _container = new Layer();
	private Quad _background = new Quad( 1, 1, 0 );

	private MRectangle _bounds = new MRectangle();
	private int _itemsHeight = 0;
	private int _offset = 0;
	private int _cellSpacing = 1;
	private boolean _dirty = false;

	public UIList( IUICellRendererFactory rendererFactory )
	{
		_factory = rendererFactory;

		addChild( _background );
		addChild( _container );
	}

	private UICellRenderer getAssociatedRenderer( UICellItem item )
	{
		int n = _renderer.size();

		while( --n > -1 )
		{
			if( _renderer.get( n ).getData() == item )
			{
				return _renderer.get( n );
			}
		}

		return null;
	}

	private void measure()
	{
		int result = 0;

		for( UICellItem _item : _items )
		{
			result += _item.getHeight() + _cellSpacing;
		}

		_itemsHeight = result;
	}

	private void requestRender()
	{
		if( !_dirty )
		{
			_dirty = true;

			StageProvider.stage.onRender.attach( renderProcedure );
		}
	}

	private void render()
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
		UICellRenderer renderer;

		for( int i = 0; i < numItems; ++i )
		{
			UICellItem item = _items.get( i );

			info( Context.DEFAULT, this, i, item );

			if( itemOffset + item.getHeight() < minView || itemOffset > maxView )
			{
				if( item.inViewRange )
				{
					renderer = getAssociatedRenderer( item );

					info( Context.DEFAULT, this, item, renderer );

					renderer.onDetach();

					item.inViewRange = false;

					_renderer.remove( renderer );

					_container.removeChild( renderer.getDisplayObject() );

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

					_container.addChild( renderer.getDisplayObject() );

					item.inViewRange = true;

					renderer.onAttach( item, viewWidth, item.getHeight() );
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

	public void addItem( UICellItem item )
	{
		if( hasItem( item ) )
		{
			throw new ElementAlreadyExistsError();
		}

		_items.addLast( item );

		measure();

		requestRender();
	}

	public void removeItem( UICellItem item )
	{
		if( !hasItem( item ) )
		{
			throw new ElementDoesNotExistError();
		}

		_items.remove( item );

		measure();

		requestRender();
	}

	public boolean hasItem( UICellItem item )
	{
		return ( -1 != _items.indexOf( item ) );
	}

	public UICellItem getItem( int index )
	{
		return _items.get( index );
	}

	public void resizeTo( int width, int height )
	{
		if( width < 1 || height < 1 )
		{
			throw new GenericError( this + " Invalid size" );
		}

		if( _bounds.width != width || _bounds.height != height )
		{
			_bounds.width = width;
			_bounds.height = height;

			_background.width( width );
			_background.height( height );
			_background.color( 0xFF00FF00 );

//			scrollRect( new defrac.geom.Rectangle( ( float ) 0, ( float ) 0, ( float ) width, ( float ) height ) );

			requestRender();
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

			requestRender();
		}
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

	public DisplayObject getDisplayObject()
	{
		return _container;
	}

	@Override
	public String toString()
	{
		return "[UIList]";
	}
}