package com.adjazent.defrac.ui.component.list;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.processing.UITextRenderer;
import defrac.display.DisplayObject;
import defrac.display.Layer;
import defrac.display.Quad;

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

	private Quad _background;
	private UITextProcessor _textProcessor;
	private UITextRenderer _textRenderer;


	public UICellRenderer()
	{
		_container = new Layer();

		_bounds = new MRectangle();

		_background = new Quad( 100, 100, 0xFFFF0000 );

		_textRenderer = new UITextRenderer();
		_textProcessor = UITextProcessor.create( new UITextFormat( "Helvetica" ), _textRenderer, true );
		_textProcessor.setText( "UNDEFINED" );

		_container.addChild( _background );
		_container.addChild( _textRenderer );
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

		_textProcessor.setText( _data.getText() + " - " + StringUtils.randomSequence( 5 ) );
		_textProcessor.render();
		_textRenderer.moveTo( 5, 5 );

		_background.width( cellWidth );
		_background.height( cellHeight );
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
		_background.color( _color );
	}

	@Override
	public String toString()
	{
		return "[UICellRenderer text:" + _textProcessor.getText() + "]";
	}
}

