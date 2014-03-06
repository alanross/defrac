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
	private Quad _selection;
	private Quad _background;
	private Layer _container;

	private boolean _autoSize = true;

	public UILabel( UITextFormat textFormat )
	{
		init( textFormat, false );
	}

	public UILabel( UITextFormat textFormat, boolean multiLine )
	{
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

		_background = new Quad( 1, 1, 0x0 );
		_selection = new Quad( 1, 1, 0xFF000000 );
		_selection.visible( false );
		_container = new Layer();

		addChild( _background );
		addChild( _selection );
		addChild( _container );
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

	public void setSelection( UITextSelection selection )
	{
		// Note: We will have several rects for multiline, though not implemented yet
		LinkedList<MRectangle> rectangles = _processor.getSelectionRect( selection );

		if( rectangles.size() > 0 )
		{
			MRectangle r = rectangles.get( 0 );

			_selection.visible( true );
			_selection.moveTo( ( float ) r.x, ( float ) r.y );
			_selection.scaleToSize( ( float ) r.width, ( float ) r.height );
		}
		else
		{
			_selection.visible( false );
			_selection.moveTo( 0, 0 );
			_selection.scaleToSize( 0, 0 );
		}
	}

	public void getCaretRect( MPoint p, MRectangle r )
	{
		_processor.getCaretRectAtPoint( p, r );
	}

	public void selectWordAtPoint( MPoint point, UITextSelection selection )
	{
		_processor.selectWordAtPoint( point, selection );
	}

	public void selectCharAtPoint( MPoint point, UITextSelection selection )
	{
		_processor.selectCharAtPoint( point, selection );
	}

	public UIGlyph getGlyphAtPoint( MPoint point )
	{
		return _processor.getGlyphAtPoint( point );
	}

	public int getCaretIndexForPoint( MPoint point )
	{
		return _processor.getCaretIndexAtPoint( point );
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
		return ( _autoSize ) ? _processor.getTextWidth() : ( int ) _bounds.width;
	}

	public int getHeight()
	{
		return ( _autoSize ) ? _processor.getTextHeight() : ( int ) _bounds.height;
	}

	public boolean getAutoSize()
	{
		return _autoSize;
	}

	public void setBackgroundColor( int color )
	{
		_background.color( color );
	}

	public void setSelectionColor( int color )
	{
		_selection.color( color );
	}

	@Override
	public String toString()
	{
		return "[UILabel id:" + id + "]";
	}
}