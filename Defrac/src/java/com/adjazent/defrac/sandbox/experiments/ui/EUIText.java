package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MPoint;
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

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIText extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame
{
	private UITextProcessor _processor;
	private UITextRenderer _renderer;

	private UITextProcessor _processorWord;
	private UITextRenderer _rendererWord;

	private UITextProcessor _processorChar;
	private UITextRenderer _rendererChar;

	private UILabel _labelSL1;
	private UILabel _labelSL2;
	private UILabel _labelML1;
	private UILabel _labelML2;

	private int _step = 0;

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
		_processor = createTextProcessor( format, _renderer, "Hello Moko!" );

		_rendererWord = new UITextRenderer();
		_processorWord = createTextProcessor( format, _rendererWord, "Word:" );

		_rendererChar = new UITextRenderer();
		_processorChar = createTextProcessor( format, _rendererChar, "Char:" );

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

		addChild( _renderer ).moveTo( 50.0f, 50.0f );
		addChild( _rendererWord ).moveTo( 50.0f, 100.0f );
		addChild( _rendererChar ).moveTo( 50.0f, 150.0f );

		addChild( _labelSL1 ).moveTo( 50, 200 );
		addChild( _labelML1 ).moveTo( 50, 250 );
		addChild( _labelSL2 ).moveTo( 700, 200 );
		addChild( _labelML2 ).moveTo( 700, 250 );

		activateEvents();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	private UITextProcessor createTextProcessor( UITextFormat format, UITextRenderer renderer, String text )
	{
		UITextProcessor textProcessor = UITextProcessor.createSingleLine( format, renderer );

		textProcessor.setText( text );
		textProcessor.setSize( Integer.MAX_VALUE, Integer.MAX_VALUE ); // similar to autosize

		return textProcessor;
	}

	@Override
	public void onEnterFrame()
	{
		if( _step++ == 40 )
		{
			_processor.setText( "Hello Moko! " + StringUtils.randomSequence( 15 ) );
			_step = 0;
		}

		float h = ( float ) MMath.clamp( mousePos.y - _labelML2.y(), 0, 200 );
		_labelSL2.setSize( mousePos.x - _labelSL2.x(), 40 );
		_labelML2.setSize( mousePos.x - _labelML2.x(), h );

		_renderer.globalToLocal( mousePos );

		MPoint p = new MPoint( mousePos.x, mousePos.y );

		_processorWord.setText( "Word: " + getWordUnderPoint( p ) );
		_processorChar.setText( "Char: " + getCharUnderPoint( p ) );
	}

	private String getCharUnderPoint( MPoint p )
	{
		UIGlyph g = _processor.getGlyphUnderPoint( p );

		return ( g != null ) ? Character.toString( g.getCharacter() ) : "";
	}

	private String getWordUnderPoint( MPoint p )
	{
		UITextSelection selection = new UITextSelection();

		_processor.getWordUnderPoint( p, selection );

		int i0 = selection.firstIndex;
		int i1 = selection.lastIndex;

		if( i0 != i1 )
		{
			return _processor.getText().substring( i0, i1 + 1 );
		}

		return "";
	}

	@Override
	public String toString()
	{
		return "[EUIText]";
	}
}