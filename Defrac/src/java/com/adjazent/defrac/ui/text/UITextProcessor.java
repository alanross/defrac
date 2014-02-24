package com.adjazent.defrac.ui.text;


import com.adjazent.defrac.core.error.OutOfBoundsError;
import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.*;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextProcessor implements IDisposable
{
	private LinkedList<UIGlyph> _glyphs = new LinkedList<UIGlyph>();
	private LinkedList<UIGlyph> _ellipsis = new LinkedList<UIGlyph>();

	private String _text = "";

	private MRectangle _bounds = new MRectangle();

	private IUITextComposer _composer;
	private UITextRenderer _renderer;
	private UITextInteractor _interact;
	private UITextFormat _format;
	private UIFont _font;
	private UITextLayout _block;

	public UITextProcessor( IUITextComposer composer, UITextRenderer renderer, UITextInteractor interact, UITextFormat format )
	{
		_composer = composer;
		_renderer = renderer;
		_interact = interact;

		setFormat( format );
	}

	public void setFormat( UITextFormat value )
	{
		_format = value.clone();

		_font = UIFontManager.get().getFont( _format.fontId );

		UIGlyph glyph = _font.getGlyphWithChar( '.' );

		_ellipsis = new LinkedList<UIGlyph>();
		_ellipsis.add( glyph.clone() );
		_ellipsis.add( glyph.clone() );
		_ellipsis.add( glyph.clone() );
	}

	public void setText( String value )
	{
		//value = value.replace( RegExp( /[\a\e\t]/gm),' ');

		if( _composer instanceof UITextComposerSingleLine )
		{
			//value = value.replace( RegExp( /[\f\r\v]/gm ), ' ' );
			value = value.replace( '\f', ' ' );
			value = value.replace( '\r', ' ' );
			value = value.replace( '\n', ' ' );
		}
		else
		{
			value = value.replace( '\f', '\n' );
			value = value.replace( '\r', '\n' );

			//value = value.replace( RegExp( /[\f\r\v]/gm),'\n');
		}

		_text = value;

		_glyphs.clear();

		final int n = value.length();

		for( int i = 0; i < n; ++i )
		{
			_glyphs.addLast( _font.getGlyphWithChar( value.charAt( i ) ).clone() );
		}
	}

	public void setSize( int width, int height )
	{
		_bounds.resizeTo( width, height );
	}

	public void render()
	{
		_block = _composer.process( ( LinkedList<UIGlyph> ) _glyphs.clone(), _ellipsis, _font, _format, _bounds );

		_renderer.process( _block, _format );
	}

	public void dispose()
	{
		_glyphs = null;
		_text = null;
		_block = null;

		_renderer = null;
		_composer = null;
		_interact = null;
	}

	public UIGlyph getGlyphUnderPoint( MPoint point )
	{
		return _interact.getGlyphUnderPoint( _glyphs, point );
	}

	public void getWordUnderPoint( MPoint point, UITextSelection selection )
	{
		_interact.getWordUnderPoint( _glyphs, point, selection );
	}

	public int getCursorIndex( MPoint point )
	{
		return _interact.getCursorIndexForPoint( _glyphs, point );
	}

	public UIGlyph getGlyphAt( int index )
	{
		if( index < 0 || index >= _glyphs.size() )
		{
			throw new OutOfBoundsError( index, 0, _glyphs.size() );
		}

		return _glyphs.get( index );
	}

	public String getText()
	{
		return _text;
	}

	public int getTextWidth()
	{
		return ( _block != null ) ? ( int ) _block.bounds.width : 0;
	}

	public int getTextHeight()
	{
		return ( _block != null ) ? ( int ) _block.bounds.height : 0;
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
		return "[UITextProcessor]";
	}
}

