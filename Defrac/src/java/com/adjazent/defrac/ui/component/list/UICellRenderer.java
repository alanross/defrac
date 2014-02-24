package com.adjazent.defrac.ui.component.list;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.processing.UITextComposerSingleLine;
import com.adjazent.defrac.ui.text.processing.UITextInteractor;
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
	private static final String UNDEFINED = "UNDEFINED";

	private MRectangle _bounds;
	private UICellData _data;

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
		_textRenderer.moveTo( 5, 5 );

		_textProcessor = new UITextProcessor( new UITextComposerSingleLine(), _textRenderer, new UITextInteractor(), new UITextFormat( "Helvetica" ) );
		_textProcessor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE );
		_textProcessor.setText( UNDEFINED );

		_container.addChild( _background );
		_container.addChild( _textRenderer );
	}

	public void onAttach( UICellData data, int cellWidth, int cellHeight )
	{
		if( _data != null )
		{
			throw new GenericError( this + " Attach – Data is not null!" );
		}

		_data = data;

		_bounds.resizeTo( cellWidth, cellHeight );

		_textProcessor.setText( _data.getText() + " - " + StringUtils.randomSequence( 5 ) );
		_textProcessor.render();

		_background.scaleToSize( cellWidth, cellHeight );
	}

	public void onDetach()
	{
		if( _data == null )
		{
			throw new GenericError( this + " Detach – Data is null!" );
		}

		_data = null;

		_bounds.resizeTo( 0, 0 );
		_textProcessor.setText( UNDEFINED );
	}

	public UICellData getData()
	{
		return _data;
	}

	public DisplayObject getContainer()
	{
		return _container;
	}

	public void setColor( int value )
	{
		_background.color( value );
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


	@Override
	public String toString()
	{
		return "[UICellRenderer text:" + _textProcessor.getText() + "]";
	}
}

