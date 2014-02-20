package com.adjazent.defrac.ui.component.list;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.processing.UITextRenderer;
import com.adjazent.defrac.ui.surface.UICanvasPainter;
import defrac.display.Canvas;
import defrac.display.DisplayObject;
import defrac.display.Layer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public class UICellRenderer
{
	private MRectangle _bounds;
	private UICellItem _data;

	private int _color = 0;

	private Layer _container;

	private UICanvasPainter _painter;
	private Canvas _background;

	private UITextProcessor _textProcessor;
	private UITextRenderer _textRenderer;


	public UICellRenderer()
	{
		_container = new Layer();

		_bounds = new MRectangle();

		_painter = new UICanvasPainter();
		_background = new Canvas( 100, 100, _painter );

		UITextFormat textFormat = new UITextFormat( "Helvetica" );

		_textRenderer = new UITextRenderer();
		_textProcessor = UITextProcessor.create( textFormat, _textRenderer, true );
		_textProcessor.setText( "UNDEFINED" );
		_textProcessor.render();

		_container.addChild( _background );
		_container.addChild( _textRenderer );
	}

	private void repaint()
	{
		_painter.fillRect( 0, 0, 100, 100, _color );
	}

	public void onAttach( UICellItem data, int cellWidth, int cellHeight )
	{
		if( _data != null )
		{
			throw new GenericError( this + " Attach – Data is not null!" );
		}

		_data = data;
		_bounds.width = cellWidth;
		_bounds.height = cellHeight;

		_background.scaleToSize( cellWidth, cellHeight );

		_textProcessor.setText( _data.getText() + " - " + StringUtils.randomSequence( 5 ) );
		_textProcessor.render();

		_textRenderer.moveTo( 5, 5 );

		repaint();
	}

	public void onDetach()
	{
		if( _data == null )
		{
			throw new GenericError( this + " Detach – Data is null!" );
		}

		_data = null;

		_bounds.width = 0;
		_bounds.height = 0;
		_textProcessor.setText( "UNDEFINED" );
	}

	public UICellItem getData()
	{
		return _data;
	}

	public DisplayObject getDisplayObject()
	{
		return _container;
	}

	public void setX( int value )
	{
		_container.x( value );
	}

	public int getX()
	{
		return ( int ) _container.x();
	}

	public void setY( int value )
	{
		_container.y( value );
	}

	public int getY()
	{
		return ( int ) _container.y();
	}

	public int getWidth()
	{
		return ( int ) _bounds.width;
	}

	public int getHeight()
	{
		return ( int ) _bounds.height;
	}

	public int getColor()
	{
		return _color;
	}

	public void setColor( int value )
	{
		_color = value;
	}

	@Override
	public String toString()
	{
		return "[UICellRenderer text:" + _textProcessor.getText() + "]";
	}
}

