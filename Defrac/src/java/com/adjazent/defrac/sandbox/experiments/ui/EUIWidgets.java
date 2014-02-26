package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.math.MMath;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.sandbox.events.IKeyboard;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.resource.UIResourceLoaderTexturePacker;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.texture.UITextureManager;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;
import com.adjazent.defrac.ui.widget.list.UICellData;
import com.adjazent.defrac.ui.widget.list.UICellRendererFactory;
import com.adjazent.defrac.ui.widget.list.UIList;
import com.adjazent.defrac.ui.widget.range.UISlider;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.event.KeyboardEvent;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIWidgets extends Experiment implements IUIResourceLoaderQueueObserver, IActionObserver, IEnterFrame, IKeyboard
{
	private UILabel _labelSL1;
	private UILabel _labelSL2;
	private UILabel _labelML1;
	private UILabel _labelML2;
	private UIButton _button;
	private UIToggleButton _toggleButton1;
	private UIToggleButton _toggleButton2;
	private UIToggleGroup _toggleGroup;
	private UISlider _slider;
	private UISurface _surface;
	private UIList _list;

	private boolean _ready = false;

	private final int SPEED_SLOW = 3;
	private final int SPEED_FAST = 8;

	private int _scrollSpeed = SPEED_SLOW;
	private int _keyCode;

	public EUIWidgets()
	{
	}

	@Override
	protected void onInit()
	{
		UIFontManager.initialize();
		UITextureManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica11.png", "fonts/helvetica11.fnt", "helvetica" ) );
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica24.png", "fonts/helvetica24.fnt", "helvetica24" ) );
		queue.add( new UIResourceLoaderTexturePacker( "skins/pro7.png", "skins/pro7.xml", "skins" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		UITextureAtlas skins = UITextureManager.get().getAtlas( "skins" );

		// --------------------- LABEL
		_labelSL1 = new UILabel( new UITextFormat( "helvetica24" ) );
		_labelSL1.setBackground( 0xFF888888 );
		_labelSL1.setText( "Hello World How Are You? Very GoodThankYou" );
		_labelSL1.moveTo( 50, 50 );
		_labelSL1.id = "sl1";

		_labelML1 = new UILabel( new UITextFormat( "helvetica24" ), true );
		_labelML1.setBackground( 0xFF888888 );
		_labelML1.setText( "Hello World How Are You? Very GoodThankYou" );
		_labelML1.moveTo( 50, 100 );
		_labelML1.id = "ml1";

		_labelSL2 = new UILabel( new UITextFormat( "helvetica24" ) );
		_labelSL2.setBackground( 0xFFFF0000 );
		_labelSL2.setText( "Hello World How Are You? Very GoodThankYou" );
		_labelSL2.setAutoSize( false );
		_labelSL2.moveTo( 600, 50 );
		_labelSL2.id = "sl2";

		_labelML2 = new UILabel( new UITextFormat( "helvetica24" ), true );
		_labelML2.setBackground( 0xFFFF0000 );
		_labelML2.setText( "Hello World How Are You? Very GoodThankYou" );
		_labelML2.setAutoSize( false );
		_labelML2.moveTo( 600, 100 );
		_labelML2.id = "ml2";

		// --------------------- TOGGLE BUTTON
		_toggleButton1 = new UIToggleButton( skins.getTexture( "ButtonSceneSettingsDeselected" ), skins.getTexture( "ButtonSceneSettingsSelected" ) );
		_toggleButton1.moveTo( 50, 200 );
		_toggleButton1.id = "tb1";

		_toggleButton2 = new UIToggleButton( skins.getTexture( "ButtonSceneSettingsDeselected" ), skins.getTexture( "ButtonSceneSettingsSelected" ) );
		_toggleButton2.moveTo( 100, 200 );
		_toggleButton2.id = "tb2";

		_toggleGroup = new UIToggleGroup( _toggleButton1, _toggleButton2 );
		_toggleGroup.onSelect.add( this );

		// --------------------- BUTTON
		_button = new UIButton( skins.getTexture( "ButtonChallenge" ) );
		_button.moveTo( 150, 200 );
		_button.onClick.add( this );
		_button.id = "b1";

		// --------------------- SLIDER
		_slider = new UISlider( skins.getTexture( "PlayOutSliderTrack" ), skins.getTexture( "PlayOutSliderThumb" ), skins.getTexture( "PlayOutSliderValue" ) );
		_slider.resizeTo( 100, _slider.height() );
		_slider.moveTo( 50, 250 );
		_slider.onValueChange.add( this );
		_slider.onValueComplete.add( this );
		_slider.id = "s1";

		// --------------------- LIST
		_list = new UIList( new UICellRendererFactory() );
		_list.moveTo( 50, 300 );
		_list.resizeTo( 200, 350 );
		_list.setBackground( 0xFFA2A2A2 );

		for( int i = 0; i < 30; ++i )
		{
			int h = 20;//20 + ( int ) ( Math.random() * 40 );
			_list.addItem( new UICellData( "" + i, h ) );
		}

		// --------------------- SURFACE
		_surface = new UISurface( skins.getTexture( "AreaEmpty" ) );
		_surface.moveTo( 300, 300 );
		_surface.resizeTo( 300, 100 );
		_surface.id = "s1";


		addChild( _labelSL1 );
		addChild( _labelSL2 );
		addChild( _labelML1 );
		addChild( _labelML2 );
		addChild( _toggleButton1 );
		addChild( _toggleButton2 );
		addChild( _button );
		addChild( _slider );
		addChild( _surface );
		addChild( _list );

		_ready = true;
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public void onEnterFrame()
	{
		if( _ready )
		{
			float h = ( float ) MMath.clamp( mousePos.y - _labelML2.y(), 0, 200 );
			_labelSL2.setSize( ( float ) mousePos.x - _labelSL2.x(), 40 );
			_labelML2.setSize( ( float ) mousePos.x - _labelML2.x(), h );

			if( _keyCode == 38 )
			{
				_list.setOffset( _list.getOffset() - _scrollSpeed );
			}
			if( _keyCode == 40 )
			{
				_list.setOffset( _list.getOffset() + _scrollSpeed );
			}
		}
	}

	@Override
	public void onKeyDown( KeyboardEvent event )
	{
		_keyCode = event.keyCode;

		if( event.keyCode == 16 )
		{
			_scrollSpeed = SPEED_FAST;
		}
	}

	@Override
	public void onKeyUp( KeyboardEvent event )
	{
		_keyCode = 0;

		if( event.keyCode == 16 )
		{
			_scrollSpeed = SPEED_SLOW;
		}
	}

	@Override
	public void onActionEvent( Action action )
	{
		_labelSL1.setText( "Action received " + action.getOrigin() );
	}


	@Override
	public String toString()
	{
		return "[EUIWidgets]";
	}
}