package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.widget.text.UILabel;
import com.adjazent.defrac.ui.widget.text.UITextField;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIText extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame, IActionObserver
{
	private UILabel _info;

	private UILabel _labelSL1;
	private UILabel _labelSL2;
	private UILabel _labelML1;
	private UILabel _labelML2;

	private UITextField _textField;

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

		_info = new UILabel( format );
		_info.setText( "Info" );
		_info.id = "info";

		_labelSL1 = new UILabel( format, UISkinFactory.create( 0x33888888 ) );
		_labelSL1.setText( "Single, AutoSize, How Are You? Very GoodThankYou" );
		_labelSL1.id = "sl1";

		_labelSL2 = new UILabel( format, UISkinFactory.create( 0xFFFF0000 ) );
		_labelSL2.setText( "Single, SetSize, How Are You? Very GoodThankYou" );
		_labelSL2.setAutoSize( false );
		_labelSL2.setSize( 400, 30 );
		_labelSL2.id = "sl2";

		_labelML1 = new UILabel( format, true, UISkinFactory.create( 0x33888888 ) );
		_labelML1.setText( "Multi, AutoSize, How Are You?\nVery GoodThankYou" );
		_labelML1.id = "ml1";

		_labelML2 = new UILabel( format, true, UISkinFactory.create( 0xFFFF0000 ) );
		_labelML2.setText( "Multi, SetSize, How Are You?\nVery GoodThankYou" );
		_labelML2.setAutoSize( false );
		_labelML2.id = "ml2";

		_textField = new UITextField( UISkinFactory.create( 0x33E0E0E0 ), format );
		_textField.resizeTo( 400, 50 );
		_textField.setText( "Hello World How are you?" );
		_textField.onSelection.add( this );
		_textField.onText.add( this );

		addChild( _info ).moveTo( 50, 50 );
		addChild( _labelSL1 ).moveTo( 50, 200 );
		addChild( _labelML1 ).moveTo( 50, 250 );
		addChild( _labelSL2 ).moveTo( 700, 200 );
		addChild( _labelML2 ).moveTo( 700, 250 );
		addChild( _textField ).moveTo( 50, 400 );

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
		_labelSL2.setSize( mousePos.x - _labelSL2.x(), _labelSL2.height() );
		_labelML2.setSize( mousePos.x - _labelML2.x(), h );
	}

	@Override
	public void onActionEvent( Action action )
	{
		int i0 = _textField.getSelectionFirst();
		int i1 = _textField.getSelectionLast();

		String text = _textField.getText();

		_info.setText( text + " :: " + i0 + " - " + i1 + " | " + ( ( i0 > -1 && i1 > -1 ) ? text.substring( i0, i1 ) : "-1/-1" ) );
	}

	@Override
	public String toString()
	{
		return "[EUIText]";
	}
}