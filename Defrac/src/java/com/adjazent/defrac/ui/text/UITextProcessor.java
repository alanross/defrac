package com.adjazent.defrac.ui.text;


import com.adjazent.defrac.core.error.OutOfBoundsError;
import com.adjazent.defrac.core.utils.IDisposable;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.*;
import com.adjazent.defrac.ui.utils.render.IUIRenderListener;
import com.adjazent.defrac.ui.utils.render.UIRenderRequest;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextProcessor implements IDisposable, IUIRenderListener
{
	private LinkedList<UIGlyph> _glyphs = new LinkedList<UIGlyph>();
	private LinkedList<UIGlyph> _ellipsis = new LinkedList<UIGlyph>();

	private String _text = "";

	private MRectangle _bounds = new MRectangle( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );

	private UIRenderRequest _renderRequest;

	private IUITextComposer _composer;
	private IUITextRenderer _renderer;
	private UITextInteractor _interact;
	private UITextFormat _format;
	private UIFont _font;
	private UITextLayout _block;

	public static UITextProcessor createSingleLine( UITextFormat format, IUITextRenderer renderer )
	{
		IUITextComposer composer = new UITextComposerSingleLine();

		UITextInteractor interactor = new UITextInteractor();

		return new UITextProcessor( composer, renderer, interactor, format );
	}

	public static UITextProcessor createMultiLine( UITextFormat format, IUITextRenderer renderer )
	{
		IUITextComposer composer = new UITextComposerMultiLine();

		UITextInteractor interactor = new UITextInteractor();

		return new UITextProcessor( composer, renderer, interactor, format );
	}

	public UITextProcessor( IUITextComposer composer, IUITextRenderer renderer, UITextInteractor interact, UITextFormat format )
	{
		_composer = composer;
		_renderer = renderer;
		_interact = interact;

		_renderRequest = new UIRenderRequest( this );

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

		_renderRequest.invalidate();
	}

	public void setText( String value )
	{
		//value = value.replace( RegExp( /[\a\e\t]/gm),' ');

		if( _composer instanceof UITextComposerSingleLine )
		{
			//NOTE: No RegEx support yet.

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

		_renderRequest.invalidate();
	}

	public void setSize( float width, float height )
	{
		_bounds.resizeTo( width, height );

		_renderRequest.invalidate();
	}

	public void render()
	{
		LinkedList<UIGlyph> copy = ( LinkedList<UIGlyph> ) _glyphs.clone();

		_block = _composer.process( copy, _ellipsis, _font, _format, _bounds );

		_renderer.process( _block, _format );
	}

	public void dispose()
	{
		_glyphs = null;
		_ellipsis = null;

		_text = null;

		_bounds = null;

		_renderRequest = null;
		_composer = null;
		_renderer = null;
		_interact = null;
		_format = null;
		_font = null;
		_block = null;
	}

	public LinkedList<MRectangle> getSelectionRect( UITextSelection selection )
	{
		LinkedList<MRectangle> rectangles = new LinkedList<MRectangle>();

		int i0 = selection.firstIndex;
		int i1 = selection.lastIndex;

		if( i0 != i1 )
		{
			MRectangle b0 = getGlyphAt( i0 ).getSelectionRect();
			MRectangle b1 = getGlyphAt( i1 ).getSelectionRect();

			rectangles.addLast( new MRectangle( b0.x, 0, b1.x + b1.width - b0.x, _font.getLineHeight() ) );
		}
		else if( i0 > -1 )
		{
			MRectangle b = getGlyphAt( i0 ).getSelectionRect();

			rectangles.addLast( new MRectangle( b.x, 0, b.width, _font.getLineHeight() ) );
		}

		return rectangles;
	}

	public void getCursorRect( MPoint p, MRectangle r )
	{
		int index = getCursorIndex( p );

		r.setTo( 0, 0, 1, _font.getLineHeight() );

		if( index > -1 )
		{
			MRectangle b = getGlyphAt( index ).getSelectionRect();

			r.moveTo( b.x, b.y );
		}
	}

	public UIGlyph getGlyphUnderPoint( MPoint point )
	{
		return _interact.getGlyphUnderPoint( _glyphs, point );
	}

	public void getCharUnderPoint( MPoint point, UITextSelection selection )
	{
		_interact.getCharUnderPoint( _glyphs, point, selection );
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

	@Override
	public String toString()
	{
		return "[UITextProcessor]";
	}
}

