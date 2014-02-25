package com.adjazent.defrac.ui.widget.text;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.IUITextRenderer;
import com.adjazent.defrac.ui.text.processing.UITextLayout;
import com.adjazent.defrac.ui.text.processing.UITextLine;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.display.Quad;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UILabel extends Layer implements IUITextRenderer
{
	private final LinkedList<Image> _images = new LinkedList<Image>();

	public String id = "";

	private UITextProcessor _processor;
	private MRectangle _bounds;
	private Quad _background;
	private Layer _container;

	private boolean _autosize = true;

	public UILabel( UITextFormat textFormat )
	{
		_bounds = new MRectangle();
		_processor = UITextProcessor.createSingleLine( textFormat, this );

		_background = new Quad( 1, 1, 0x0 );
		_container = new Layer();

		addChild( _background );
		addChild( _container );
	}

	@Override
	public void process( UITextLayout block, UITextFormat format )
	{
		while( !_images.isEmpty() )
		{
			removeChild( _images.removeLast() );
		}

		if( _autosize )
		{
			_background.scaleToSize( ( float ) block.bounds.width, ( float ) block.bounds.height );
		}
		else
		{
			_background.scaleToSize( ( float ) _bounds.width, ( float ) _bounds.height );
		}

		if( block.bounds.width <= 0 || block.bounds.height <= 0 )
		{
			return;
		}

		int n = block.lines.size();

		for( int i = 0; i < n; ++i )
		{
			UITextLine line = block.lines.get( i );

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
		if( _autosize )
		{
			throw new GenericError( this + " Can not set size while autosize is active." );
		}
		else
		{
			_bounds.resizeTo( width, height );
			_processor.setSize( width, height );
		}
	}

	public UIGlyph getGlyphUnderPoint( MPoint point )
	{
		return _processor.getGlyphUnderPoint( point );
	}

	public void getWordUnderPoint( MPoint point, UITextSelection selection )
	{
		_processor.getWordUnderPoint( point, selection );
	}

	public int getCursorIndex( MPoint point )
	{
		return _processor.getCursorIndex( point );
	}

	public UIGlyph getGlyphAt( int index )
	{
		return _processor.getGlyphAt( index );
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

	public int getWidth()
	{
		return _processor.getWidth();
	}

	public int getHeight()
	{
		return _processor.getHeight();
	}

	public boolean getAutoSize()
	{
		return _autosize;
	}

	public void setAutosize( boolean value )
	{
		_autosize = value;

		if( _autosize )
		{
			_processor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE );
		}
		else
		{
			_processor.setSize( ( int ) _bounds.width, ( int ) _bounds.height );
		}
	}


	public void setBackground( int color )
	{
		_background.color( color );
	}

	@Override
	public String toString()
	{
		return "[UILabel id:" + id + "]";
	}
}