package com.adjazent.defrac.ui.widget.text;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.IUITextRenderer;
import com.adjazent.defrac.ui.text.processing.UITextLayout;
import com.adjazent.defrac.ui.text.processing.UITextLine;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UILabel extends UISurface implements IUITextRenderer
{
	private final LinkedList<Image> _images = new LinkedList<Image>();

	private UITextProcessor _processor;
	private MRectangle _bounds;
	private Layer _container;

	private boolean _autoSize = true;

	public UILabel( UITextFormat textFormat )
	{
		super();

		init( textFormat, false );
	}

	public UILabel( UITextFormat textFormat, IUISkin skin )
	{
		super( skin );

		init( textFormat, false );
	}

	public UILabel( UITextFormat textFormat, boolean multiLine )
	{
		init( textFormat, multiLine );
	}

	public UILabel( UITextFormat textFormat, boolean multiLine, IUISkin skin )
	{
		super( skin );

		init( textFormat, multiLine );
	}

	private void init( UITextFormat textFormat, boolean multiLine )
	{
		_bounds = new MRectangle();

		if( multiLine )
		{
			_processor = UITextProcessor.createMultiLine( textFormat, this );
		}
		else
		{
			_processor = UITextProcessor.createSingleLine( textFormat, this );
		}

		_container = new Layer();

		addChild( _container );
	}

	@Override
	public UIEventTarget captureEventTarget( Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( _bounds.contains( local.x, local.y ) ) ? this : null;
	}

	@Override
	public void renderText( UITextLayout block, UITextFormat format )
	{
		while( !_images.isEmpty() )
		{
			removeChild( _images.removeLast() );
		}

		if( _autoSize )
		{
			resizeTo( ( float ) block.bounds.width, ( float ) block.bounds.height );
		}
		else
		{
			resizeTo( ( float ) _bounds.width, ( float ) _bounds.height );
		}

		if( block.bounds.width <= 0 || block.bounds.height <= 0 )
		{
			return;
		}

		LinkedList<UITextLine> lines = block.lines;

		for( UITextLine line : lines )
		{
			LinkedList<UIGlyph> glyphs = line.glyphs;

			for( UIGlyph g : glyphs )
			{
				Image image = new Image( g.getTexture() );

				image.moveTo( g.getX(), g.getY() );

				_images.addLast( image );

				addChild( image );
			}
		}
	}

	@Override
	public void renderTextDependantAction()
	{
	}

	public void setFormat( UITextFormat value )
	{
		_processor.setFormat( value );
	}

	public void setText( String value )
	{
		_processor.setText( value );
	}

	public void setSize( float width, float height )
	{
		if( _autoSize )
		{
			throw new GenericError( this + " Can not set size while autosize is active." );
		}
		else
		{
			_bounds.resizeTo( width, height );
			_processor.setSize( width, height );
		}
	}

	public void setAutoSize( boolean value )
	{
		_autoSize = value;

		if( _autoSize )
		{
			_processor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE );
		}
		else
		{
			_processor.setSize( ( int ) _bounds.width, ( int ) _bounds.height );
		}
	}

	public boolean getAutoSize()
	{
		return _autoSize;
	}

	public String getText()
	{
		return _processor.getText();
	}

	public int getTextWidth()
	{
		return _processor.getTextWidth();
	}

	public int getTextHeight()
	{
		return _processor.getTextHeight();
	}

	@Override
	public float width()
	{
		return ( _autoSize ) ? _processor.getTextWidth() : ( int ) _bounds.width;
	}

	@Override
	public float height()
	{
		return ( _autoSize ) ? _processor.getTextHeight() : ( int ) _bounds.height;
	}

	@Override
	public String toString()
	{
		return "[UILabel id:" + id + "]";
	}
}