package com.adjazent.defrac.sandbox.experiments.ui.font;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.core.utils.StringUtils;
import com.adjazent.defrac.sandbox.experiments.Experiment;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextProcessor;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.UIFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyph;
import com.adjazent.defrac.ui.text.font.glyph.UIGlyphImageRenderer;
import defrac.display.Image;
import defrac.display.Layer;
import defrac.display.Texture;
import defrac.display.event.UIActionEvent;
import defrac.display.event.UIEventType;
import defrac.event.Event;
import defrac.event.Events;
import defrac.geom.Point;
import defrac.geom.Rectangle;
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
	Layer _textLayer1;
	Layer _textLayer2;
	Layer _textLayer3;

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

		addChild( _textLayer1 = new Layer() ).moveTo( 50.0f, 50.0f );
		addChild( _textLayer2 = new Layer() ).moveTo( 50.0f, 150.0f );
		addChild( _textLayer3 = new Layer() ).moveTo( 50.0f, 250.0f );

		_textProcessor1 = createTextProcessor( _textLayer1, "Hello Moko!" );
		_textProcessor2 = createTextProcessor( _textLayer2, "Output" );
		_textProcessor3 = createTextProcessor( _textLayer3, "Output" );

		addGlyph( 'M', 50.0f, 350.0f );

		Events.onEnterFrame.attach( this );
	}

	@Override
	public void onResourceLoadingFailure()
	{
		Log.info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	private void addGlyph( char character, float posX, float posY )
	{
		UIFont font = UIFontManager.get().getFont( "helvetica" );

		UIGlyph g = font.getGlyph( character );

		Rectangle r = g.getSourceRect();
		Image image = new Image( new Texture( font.getTextureData(), r.x, r.y, r.width, r.height ) );

		addChild( image ).moveTo( posX, posY );
	}

	private UITextProcessor createTextProcessor( Layer container, String text )
	{
		UITextFormat textFormat = new UITextFormat( "Helvetica", 0, 0, 0, 0 );
		UIGlyphImageRenderer renderer = new UIGlyphImageRenderer( container );
		UITextProcessor textProcessor = UITextProcessor.createSingleLine( renderer, textFormat );

		textProcessor.setText( text );
		textProcessor.layout(); // no size restrictions

		return textProcessor;
	}

	private int step = 0;
	private Point mousePos = new Point();

	@Override
	public void apply( @Nonnull final Event event )
	{
		stage.eventManager().pointerPos( mousePos, /*index=*/0 );

		// mousePos for get glyph

		if( step++ == 40 )
		{
			_textProcessor1.setText( "Hello Moko! " + StringUtils.randomSequence( 15 ) );
			_textProcessor1.layout();
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

				Point p = actionEvent.pos;

				_textLayer1.globalToLocal( p );

				_textProcessor2.setText( handleGlyphUnderPoint( p ) );
				_textProcessor2.layout();

				_textProcessor3.setText( handleWordUnderPoint( p ) );
				_textProcessor3.layout();
			}
		}
	}

	private String handleGlyphUnderPoint( Point p )
	{
		UIGlyph g = _textProcessor1.getGlyphUnderPoint( p );

		return ( g != null ) ? g.getLetter() : "";
	}

	private String handleWordUnderPoint( Point p )
	{
		UITextSelection selection = new UITextSelection();

		_textProcessor1.getWordUnderPoint( p, selection );

		int i0 = selection.firstIndex;
		int i1 = selection.lastIndex;

		if( i0 != i1 )
		{
			return _textProcessor1.getText().substring( i0, i1 +1 );
		}

		return "";
	}

	@Override
	public String toString()
	{
		return "[EUIFonts]";
	}
}