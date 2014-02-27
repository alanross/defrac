package com.adjazent.defrac.ui.widget.list;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.widget.UIActionType;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.display.DisplayObjectContainer;
import defrac.display.Quad;
import defrac.display.event.UIEvent;
import defrac.display.event.UIEventTarget;
import defrac.display.event.UIEventType;
import defrac.geom.Point;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UICellRenderer extends DisplayObjectContainer implements IUICellRenderer, IActionObserver
{
	private static final String UNDEFINED = "UNDEFINED";

	public final Action onClick = new Action( UIActionType.CELL_CLICK );

	private MRectangle _bounds;
	private UICellData _data;

	private Quad _background;
	private UILabel _label;

	public UICellRenderer()
	{
		_bounds = new MRectangle();

		_background = new Quad( 1, 1, 0x0 );

		_label = new UILabel( new UITextFormat( "Helvetica" ) );
		_label.setAutoSize( false );

		addChild( _background );
		addChild( _label );
	}

	private void repaint()
	{
		_background.alpha( _data.getSelected() ? 1.0f : 0.5f );
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( _bounds.contains( local.x, local.y ) ) ? this : null;
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
		if( action.getOrigin() == _data )
		{
			repaint();
		}
	}

	public void onAttach( UICellData data, float cellWidth, float cellHeight )
	{
		if( _data != null )
		{
			throw new GenericError( this + " Attach – Data is not null!" );
		}

		_data = data;
		_data.onSelect.add( this );

		_bounds.resizeTo( cellWidth, cellHeight );
		_background.scaleToSize( cellWidth, cellHeight );

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

		_bounds.resizeTo( 0, 0 );
		_background.scaleToSize( 0, 0 );
		_label.setText( UNDEFINED );
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
	public void setBackgroundColor( int value )
	{
		_background.color( value );
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
		return "[UICellRenderer text:" + _label.getText() + "]";
	}
}