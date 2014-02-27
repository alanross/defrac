package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.UITextRenderer;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.display.Quad;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIText extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame
{
	private UITextProcessor _processor;
	private UITextRenderer _renderer;

	private UILabel _labelSL1;
	private UILabel _labelSL2;
	private UILabel _labelML1;
	private UILabel _labelML2;

	private Quad _word = new Quad( 1, 1, 0xFF886644 );
	private Quad _char = new Quad( 1, 1, 0x88FF0000 );
	private Quad _caret = new Quad( 1, 1, 0xFFFFFFFF );

	public EUIText()
	{
	}

	protected void onInit()
	{
		UIFontManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica24.png", "fonts/helvetica24.fnt", "Helvetica" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		UITextFormat format = new UITextFormat( "Helvetica" );

		_renderer = new UITextRenderer();
		_processor = UITextProcessor.createSingleLine( format, _renderer );

		_processor.setText( "Hello World, How Are You?" );
		_processor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE ); // similar to autosize

		_labelSL1 = new UILabel( format );
		_labelSL1.setBackground( 0xFF888888 );
		_labelSL1.setText( "Single, AutoSize, How Are You? Very GoodThankYou" );
		_labelSL1.id = "sl1";

		_labelML1 = new UILabel( format, true );
		_labelML1.setBackground( 0xFF888888 );
		_labelML1.setText( "Multi, AutoSize, How Are You? Very GoodThankYou" );
		_labelML1.id = "ml1";

		_labelSL2 = new UILabel( format );
		_labelSL2.setBackground( 0xFFFF0000 );
		_labelSL2.setText( "Single, SetSize, How Are You? Very GoodThankYou" );
		_labelSL2.setAutoSize( false );
		_labelSL2.id = "sl2";

		_labelML2 = new UILabel( format, true );
		_labelML2.setBackground( 0xFFFF0000 );
		_labelML2.setText( "Multi, SetSize, How Are You? Very GoodThankYou" );
		_labelML2.setAutoSize( false );
		_labelML2.id = "ml2";

		addChild( _word );
		addChild( _char );
		addChild( _renderer ).moveTo( 50.0f, 50.0f );
		addChild( _caret );

		addChild( _labelSL1 ).moveTo( 50, 100 );
		addChild( _labelML1 ).moveTo( 50, 150 );
		addChild( _labelSL2 ).moveTo( 700, 100 );
		addChild( _labelML2 ).moveTo( 700, 150 );

		activateEvents();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	@Override
	public void onEnterFrame()
	{
		float h = ( float ) MMath.clamp( mousePos.y - _labelML2.y(), 0, 200 );
		_labelSL2.setSize( mousePos.x - _labelSL2.x(), 40 );
		_labelML2.setSize( mousePos.x - _labelML2.x(), h );

		_renderer.globalToLocal( mousePos );

		MPoint p = new MPoint( mousePos.x, mousePos.y );

		handleWordSelection( p );

		handleCharSelection( p );

		//canvas.copyPixels( _renderer.bitmapData, _renderer.bitmapData.rect, _origin );

		handleCaret( p );
	}

	private void handleWordSelection( MPoint p )
	{
		UITextSelection selection = new UITextSelection();

		_processor.getWordUnderPoint( p, selection );

		int i0 = selection.firstIndex;
		int i1 = selection.lastIndex;

		if( i0 != i1 )
		{
			UIGlyph g0 = _processor.getGlyphAt( i0 );
			UIGlyph g1 = _processor.getGlyphAt( i1 );

			MRectangle r = new MRectangle();
			r.x = _renderer.x() + g0.getSelectionRect().x;
			r.y = _renderer.y();
			r.width = g1.getSelectionRect().x + g1.getSelectionRect().width - g0.getSelectionRect().x;
			r.height = 24;

			_word.moveTo( ( float ) r.x, ( float ) r.y );
			_word.scaleToSize( ( float ) r.width, ( float ) r.height );
		}
		else
		{
			_word.moveTo( 0, 0 );
			_word.scaleToSize( 0, 0 );
		}

	}

	private void handleCharSelection( MPoint p )
	{
		// don"t do this before create was called, as create is also responsible for layouting the glyphs
		UIGlyph glyph = _processor.getGlyphUnderPoint( p );

		if( glyph != null )
		{
			MRectangle r = glyph.getSelectionRect().clone();

			r.x += _renderer.x();
			r.y += _renderer.y();

			_char.moveTo( ( float ) r.x, ( float ) r.y );
			_char.scaleToSize( ( float ) r.width, ( float ) r.height );
		}
		else
		{
			_char.moveTo( 0, 0 );
			_char.scaleToSize( 0, 0 );
		}
	}

	private void handleCaret( MPoint p )
	{
		int index = _processor.getCursorIndex( p );

		if( index > -1 )
		{
			UIGlyph glyph = _processor.getGlyphAt( index );
			MRectangle r = glyph.getSelectionRect().clone();

			r.x = glyph.getSelectionRect().x + _renderer.x();
			r.y = glyph.getSelectionRect().y + _renderer.y();

			_caret.scaleToSize( 1, 24 );
			_caret.moveTo( ( float ) ( r.x + r.width ), ( float ) r.y );
		}
		else
		{
			_caret.scaleToSize( 1, 24 );
			_caret.moveTo( _renderer.x(), _renderer.y() );
		}
	}

	@Override
	public String toString()
	{
		return "[EUIText]";
	}
}