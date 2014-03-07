package com.adjazent.defrac.sandbox.apps.theme;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.list.IUICellRenderer;
import com.adjazent.defrac.ui.widget.list.UICellData;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.display.DisplayObjectContainer;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7CellRenderer extends UISurface implements IUICellRenderer, IActionObserver
{
	public final Action onClick = new Action( UIActionType.CELL_CLICK );

	private IUISkin _skinDeselected;
	private IUISkin _skinSelected;
	private UILabel _label;

	private UICellData _data;

	public Pro7CellRenderer( IUISkin skinDeselected, IUISkin skinSelected, UITextFormat textFormat )
	{
		super( skinDeselected );

		_skinDeselected = skinDeselected;
		_skinSelected = skinSelected;

		_label = new UILabel( textFormat );
		_label.setAutoSize( false );

		addChild( _label );
	}

	private void repaint()
	{
		setSkin( ( _data.getSelected() ? _skinSelected : _skinDeselected ) );
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	protected void processEvent( @Nonnull final UIEvent event )
	{
		super.processEvent( event );

		if( event.type == UIEventType.ACTION_BEGIN && event.target == this )
		{
			_data.setSelected( !_data.getSelected() );
		}
		if( event.type == UIEventType.ACTION_SINGLE && event.target == this )
		{
			onClick.send( this );
		}
	}

	@Override
	public void onActionEvent( Action action )
	{
		repaint();
	}

	public void onAttach( UICellData data, float cellWidth, float cellHeight )
	{
		if( _data != null )
		{
			throw new GenericError( this + " Attach – Data is not null!" );
		}

		_data = data;
		_data.onSelect.add( this );

		resizeTo( cellWidth, cellHeight );

		_label.setText( _data.getText() );
		_label.moveTo( 5, 5 );
		_label.setSize( cellWidth - 10, cellHeight - 5 );

		repaint();
	}

	public void onDetach()
	{
		if( _data == null )
		{
			throw new GenericError( this + " Detach – Data is null!" );
		}

		_data.onSelect.remove( this );
		_data = null;

		_label.setText( "UNDEFINED" );
	}

	@Override
	public UICellData getData()
	{
		return _data;
	}

	@Override
	public DisplayObjectContainer getContainer()
	{
		return this;
	}

	@Override
	public void setX( int value )
	{
		this.x( value );
	}

	@Override
	public void setY( int value )
	{
		this.y( value );
	}

	@Override
	public String toString()
	{
		return "[Pro7CellRenderer text:" + _label.getText() + "]";
	}
}