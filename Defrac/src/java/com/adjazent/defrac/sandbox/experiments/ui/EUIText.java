package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.math.geom.MPoint;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.UITextSelection;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.display.Quad;
import defrac.geom.Point;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIText extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame
{
	private UILabel _labelSL1;
	private UILabel _labelSL2;
	private UILabel _labelML1;
	private UILabel _labelML2;

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

		_labelSL1 = new UILabel( format );
		_labelSL1.setBackgroundColor( 0x33888888 );
		_labelSL1.setSelectionColor( 0xFFAA0000 );
		_labelSL1.setText( "Single, AutoSize, How Are You? Very GoodThankYou" );
		_labelSL1.id = "sl1";

		_labelSL2 = new UILabel( format );
		_labelSL2.setBackgroundColor( 0xFFFF0000 );
		_labelSL2.setText( "Single, SetSize, How Are You? Very GoodThankYou" );
		_labelSL2.setAutoSize( false );
		_labelSL2.setSize( 400, 30 );
		_labelSL2.id = "sl2";

		_labelML1 = new UILabel( format, true );
		_labelML1.setBackgroundColor( 0x33888888 );
		_labelML1.setSelectionColor( 0xFFAA0000 );
		_labelML1.setText( "Multi, AutoSize, How Are You? Very GoodThankYou" );
		_labelML1.id = "ml1";

		_labelML2 = new UILabel( format, true );
		_labelML2.setBackgroundColor( 0xFFFF0000 );
		_labelML2.setText( "Multi, SetSize, How Are You?\nVery GoodThankYou" );
		_labelML2.setAutoSize( false );
		_labelML2.id = "ml2";

		addChild( _labelSL1 ).moveTo( 50, 200 );
		addChild( _labelML1 ).moveTo( 50, 250 );
		addChild( _labelSL2 ).moveTo( 700, 200 );
		addChild( _labelML2 ).moveTo( 700, 250 );
		addChild( _caret );

		activateEvents();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "onResourceLoadingFailure" );
	}

	@Override
	public void onEnterFrame()
	{
		float h = ( float ) MMath.clamp( mousePos.y - _labelML2.y(), 0, 200 );
		_labelSL2.setSize( mousePos.x - _labelSL2.x(), _labelSL2.getHeight() );
		_labelML2.setSize( mousePos.x - _labelML2.x(), h );

		UITextSelection selection = new UITextSelection();

		Point p1 = _labelSL1.globalToLocal( new Point( mousePos.x, mousePos.y ) );
		_labelSL1.selectWordAtPoint( new MPoint( p1.x, p1.y ), selection );
		_labelSL1.setSelection( selection );

		Point p2 = _labelML1.globalToLocal( new Point( mousePos.x, mousePos.y ) );
		_labelML1.selectCharAtPoint( new MPoint( p2.x, p2.y ), selection );
		_labelML1.setSelection( selection );

		MRectangle caretRect = new MRectangle();
		_labelSL1.getCaretRect( new MPoint( p1.x, p1.y ), caretRect );
		_caret.moveTo( ( float ) caretRect.x + _labelSL1.x(), ( float ) caretRect.y + _labelSL1.y() );
		_caret.scaleToSize( ( float ) caretRect.width, ( float ) caretRect.height );
	}

	@Override
	public String toString()
	{
		return "[EUIText]";
	}
}