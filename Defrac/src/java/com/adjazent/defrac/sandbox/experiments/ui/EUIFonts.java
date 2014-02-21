package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.processing.UITextRenderer;
import defrac.display.Image;
import defrac.display.Texture;
import defrac.display.event.UIActionEvent;
import defrac.display.event.UIEventType;
import defrac.event.Event;
import defrac.event.Events;
import defrac.lang.Procedure;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIFonts extends Experiment implements IUIResourceLoaderQueueObserver, Procedure<Event>
{
	UITextProcessor _textProcessor1;
	UITextProcessor _textProcessor2;
	UITextProcessor _textProcessor3;
	UITextRenderer _renderer1;
	UITextRenderer _renderer2;
	UITextRenderer _renderer3;

	public EUIFonts()
	{
	}

	protected void onInit()
	{
		UIFontManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica64.png", "fonts/helvetica64.fnt", "helvetica" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingSuccess" );

		addChild( _renderer1 = new UITextRenderer() ).moveTo( 50.0f, 50.0f );
		addChild( _renderer2 = new UITextRenderer() ).moveTo( 50.0f, 150.0f );
		addChild( _renderer3 = new UITextRenderer() ).moveTo( 50.0f, 250.0f );

		_textProcessor1 = createTextProcessor( _renderer1, "Hello Moko!" );
		_textProcessor2 = createTextProcessor( _renderer2, "Output" );
		_textProcessor3 = createTextProcessor( _renderer3, "Output" );

		addGlyph( 'M', 50.0f, 350.0f );

		Events.onEnterFrame.attach( this );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	private void addGlyph( char character, float posX, float posY )
	{
		UIFont font = UIFontManager.get().getFont( "helvetica" );

		UIGlyph g = font.getGlyphWithChar( character );

		MRectangle r = g.getSourceRect();
		Image image = new Image( new Texture( font.getTextureData(), ( float ) r.x, ( float ) r.y, ( float ) r.width, ( float ) r.height ) );

		addChild( image ).moveTo( posX, posY );
	}

	private UITextProcessor createTextProcessor( UITextRenderer renderer, String text )
	{
		UITextFormat textFormat = new UITextFormat( "Helvetica" );

		UITextProcessor textProcessor = UITextProcessor.create( textFormat, renderer, true );

		textProcessor.setText( text );
		textProcessor.render(); // no size restrictions

		return textProcessor;
	}

	private int step = 0;
	private defrac.geom.Point mousePos = new defrac.geom.Point();

	@Override
	public void apply( @Nonnull final Event event )
	{
		stage.eventManager().pointerPos( mousePos, /*index=*/0 );

		// mousePos for get glyph

		if( step++ == 40 )
		{
			_textProcessor1.setText( "Hello Moko! " + StringUtils.randomSequence( 15 ) );
			_textProcessor1.render();
			step = 0;
		}
	}

	@Override
	protected void processEvent( @javax.annotation.Nonnull defrac.display.event.UIEvent uiEvent )
	{
		if( _textProcessor1 == null )
		{
			return;
		}

		if( uiEvent.type == UIEventType.ACTION_MOVE )
		{
			if( uiEvent instanceof UIActionEvent )
			{
				UIActionEvent actionEvent = ( UIActionEvent ) uiEvent;

				defrac.geom.Point p = actionEvent.pos;

				_renderer1.globalToLocal( p );

				MPoint p2 = new MPoint( actionEvent.pos.x, actionEvent.pos.y );

				String t1 = handleGlyphUnderPoint( p2 );
				String t2 = handleWordUnderPoint( p2 );

				_textProcessor2.setText( t1 );
				_textProcessor2.render();

				_textProcessor3.setText( t2 );
				_textProcessor3.render();
			}
		}
	}

	private String handleGlyphUnderPoint( MPoint p )
	{
		UIGlyph g = _textProcessor1.getGlyphUnderPoint( p );

		return ( g != null ) ? Character.toString( g.getCharacter() ) : "";
	}

	private String handleWordUnderPoint( MPoint p )
	{
		UITextSelection selection = new UITextSelection();

		_textProcessor1.getWordUnderPoint( p, selection );

		int i0 = selection.firstIndex;
		int i1 = selection.lastIndex;

		if( i0 != i1 )
		{
			return _textProcessor1.getText().substring( i0, i1 + 1 );
		}

		return "";
	}

	@Override
	public String toString()
	{
		return "[EUIFonts]";
	}
}