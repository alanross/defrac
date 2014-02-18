package com.adjazent.defrac.ui.text;

import com.adjazent.defrac.core.error.OutOfBoundsError;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.glyph.*;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UITextProcessor //implements IDisposable
{
	private LinkedList<UIGlyph> _glyphsOriginal = new LinkedList<UIGlyph>();
	private LinkedList<UIGlyph> _glyphsLayouted = new LinkedList<UIGlyph>();

	private Rectangle _boundsOriginal = new Rectangle();
	private Rectangle _boundsLayouted = new Rectangle();

	private UITextFormat _format;
	private UIFont _atlas;

	private IUIGlyphLayouter _layouter;
	private IUIGlyphRenderer _renderer;
	private UIGlyphInteraction _interaction;

	private String _text;

	/**
	 * Creates and returns a new instance of UITextProcessor.
	 */
	public static UITextProcessor createSingleLine( IUIGlyphRenderer renderer )
	{
		return new UITextProcessor( new UIGlyphLayouterSingleLine(), new UIGlyphInteraction(), renderer );
	}

	/**
	 * Creates and returns a new instance of UITextProcessor.
	 */
	public static UITextProcessor createSingleLine( IUIGlyphRenderer renderer, UITextFormat format )
	{
		UITextProcessor processor = new UITextProcessor( new UIGlyphLayouterSingleLine(), new UIGlyphInteraction(), renderer );

		if( format != null )
		{
			processor.setFormat( format );
		}

		return processor;
	}

	/**
	 * Creates a new instance of UITextProcessor.
	 */
	public UITextProcessor( IUIGlyphLayouter layouter, UIGlyphInteraction interaction, IUIGlyphRenderer renderer )
	{
		_layouter = layouter;
		_interaction = interaction;
		_renderer = renderer;

		_text = new String();
	}

	/**
	 * Sets the TextFormat. This function must be called before calling any
	 * other such (setText) or layout.
	 */
	public void setFormat( UITextFormat textFormat )
	{
		_format = textFormat.clone();

		_atlas = UIFontManager.get().getFont( _format.fontId );

		UIGlyph dot = _atlas.getGlyph( '.' );

		LinkedList<UIGlyph> dots = new LinkedList<UIGlyph>();
		dots.add( dot );
		dots.add( dot.clone() );
		dots.add( dot.clone() );

		_layouter.setEllipsis( dots, _format );

		setText( _text );
	}

	/**
	 * Set the text to be processed.
	 */
	public void setText( String str )
	{
		_text = str;

		// clean the string before using it
		//str = str.replace( RegExp( /[\f\r\v]/gm),"\n");
		//str = str.replace( RegExp( /[\a\e\t]/gm)," ");

		_glyphsOriginal.clear();

		final int n = str.length();

		for( int i = 0; i < n; ++i )
		{
			_glyphsOriginal.addLast( _atlas.getGlyph( str.charAt( i ) ) );
		}

		_layouter.layoutGlyphs( _glyphsOriginal, _boundsOriginal, _format, Integer.MAX_VALUE, Integer.MAX_VALUE );
	}

	/**
	 * Layouts the glyphs. The visual representation is defined by the text format.
	 * If maxWidth is setAnd the text requires more space than defined by maxWidth,
	 * the text is cropped using ellipsis to fit in to maxWidth.
	 */
	public void layout()
	{
		layout( Integer.MAX_VALUE, Integer.MAX_VALUE );
	}

	/**
	 * Layouts the glyphs. The visual representation is defined by the text format.
	 * If maxWidth is setAnd the text requires more space than defined by maxWidth,
	 * the text is cropped using ellipsis to fit in to maxWidth.
	 */
	public void layout( int maxWidth, int maxHeight )
	{
		_glyphsLayouted.clear();

		// make a copy, the glyphs will have the position from layout result done when text was set.
		_glyphsLayouted = new LinkedList<UIGlyph>( _glyphsOriginal );

		_layouter.layoutGlyphs( _glyphsLayouted, _boundsLayouted, _format, maxWidth, maxHeight );

		_renderer.renderGlyphs( _glyphsLayouted, _format, _boundsLayouted );
	}

	public void dispose()
	{
		_layouter = null;
		_renderer = null;
		_interaction = null;

		_text = null;

		_glyphsOriginal = null;

		_boundsOriginal = null;
		_boundsLayouted = null;
	}

	public UIGlyph getGlyphUnderPoint( Point point )
	{
		return _interaction.getGlyphUnderPoint( _glyphsOriginal, point );
	}

	public void getWordUnderPoint( Point point, UITextSelection selection )
	{
		_interaction.getWordUnderPoint( _glyphsOriginal, point, selection );
	}

	public int getCursorIndex( Point point )
	{
		return _interaction.getCursorIndexForPoint( _glyphsOriginal, point );
	}

	public UIGlyph getGlyphAt( int index )
	{
		if( index < 0 || index >= _glyphsOriginal.size() )
		{
			throw new OutOfBoundsError( index, 0, _glyphsOriginal.size() );
		}

		return _glyphsOriginal.get( index );
	}

	public Rectangle getBoundsOriginal()
	{
		return _boundsOriginal;
	}

	public Rectangle getBoundsLayouted()
	{
		return _boundsLayouted;
	}

	public String getText()
	{
		return _text;
	}

	@Override
	public String toString()
	{
		return "[UITextProcessor]";
	}
}

