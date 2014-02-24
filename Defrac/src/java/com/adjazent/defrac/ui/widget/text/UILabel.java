package com.adjazent.defrac.ui.widget.text;

import com.adjazent.defrac.core.error.GenericError;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.UITextRenderer;
import defrac.display.Layer;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UILabel extends Layer
{
	UITextProcessor _processor;
	UITextRenderer _renderer;
	MRectangle _bounds = new MRectangle();

	private boolean _autosize = false;

	public UILabel( UITextFormat textFormat )
	{
		_renderer = new UITextRenderer();
		_processor = UITextProcessor.createSingleLine( textFormat, _renderer );

		addChild( _renderer );
	}

	public void setFormat( UITextFormat value )
	{
		_processor.setFormat( value );
	}

	public void setText( String value )
	{
		_processor.setText( value );
	}

	public void setSize( int width, int height )
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

	@Override
	public String toString()
	{
		return "[UILabel]";
	}
}