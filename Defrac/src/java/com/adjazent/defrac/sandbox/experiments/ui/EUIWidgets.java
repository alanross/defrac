package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.sandbox.events.IKeyboard;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.resource.UIResourceLoaderTexturePacker;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.texture.UITextureManager;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;
import com.adjazent.defrac.ui.widget.list.UICellData;
import com.adjazent.defrac.ui.widget.list.UIList;
import com.adjazent.defrac.ui.widget.list.UIListInteractions;
import com.adjazent.defrac.ui.widget.range.UISlider;
import com.adjazent.defrac.ui.widget.text.UILabel;
import com.adjazent.defrac.ui.widget.text.UITextField;
import defrac.display.Layer;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIWidgets extends Experiment implements IUIResourceLoaderQueueObserver, IActionObserver, IEnterFrame, IKeyboard
{
	private UILabel _label;
	private UIButton _button;
	private UIToggleButton _toggleButton1;
	private UIToggleButton _toggleButton2;
	private UIToggleGroup _toggleGroup;
	private UISlider _slider;
	private UISurface _surface;
	private UIList _list;
	private UITextField _textField;

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
		_label = new UILabel( new UITextFormat( "Helvetica24" ) );
		_label.setText( "Hello UILabel!" );
		_label.moveTo( 50.0f, 50.0f );

		// --------------------- TOGGLE BUTTON
		_toggleButton1 = new UIToggleButton(
				UISkinFactory.create( skins.getTexture( "ButtonSceneSettingsDeselected" ) ),
				UISkinFactory.create( skins.getTexture( "ButtonSceneSettingsSelected" ) )
		);
		_toggleButton1.moveTo( 50, 100 );
		_toggleButton1.id = "tb1";

		_toggleButton2 = new UIToggleButton(
				UISkinFactory.create( skins.getTexture( "ButtonSceneSettingsDeselected" ) ),
				UISkinFactory.create( skins.getTexture( "ButtonSceneSettingsSelected" ) )
		);
		_toggleButton2.moveTo( 100, 100 );
		_toggleButton2.id = "tb2";

		_toggleGroup = new UIToggleGroup( _toggleButton1, _toggleButton2 );
		_toggleGroup.onSelect.add( this );

		// --------------------- BUTTON
		_button = new UIButton(
				UISkinFactory.create( skins.getTexture( "ButtonChallenge" ) ) );
		_button.moveTo( 150, 100 );
		_button.onClick.add( this );
		_button.id = "b1";

		// --------------------- SLIDER
		_slider = new UISlider(
				UISkinFactory.create( skins.getTexture( "PlayOutSliderTrack" ) ),
				UISkinFactory.create( skins.getTexture( "PlayOutSliderThumb" ) ),
				UISkinFactory.create( skins.getTexture( "PlayOutSliderValue" ) )
		);
		_slider.resizeTo( 100, _slider.height() );
		_slider.moveTo( 600, 100 );
		_slider.onValueChange.add( this );
		_slider.onValueComplete.add( this );
		_slider.id = "s1";

		// --------------------- LIST
		_list = new UIList( new MyCellRendererFactory( new UITextFormat( "Helvetica" ) ), new UIListInteractions() );
		_list.moveTo( 50, 150 );
		_list.resizeTo( 200, 350 );
		_list.setBackground( 0xFFA2A2A2 );

		for( int i = 0; i < 30; ++i )
		{
			int h = 20;//20 + ( int ) ( Math.random() * 40 );
			_list.addItem( new UICellData( "Cell Data: " + i, h ) );
		}

		// --------------------- SURFACE
		_surface = new UISurface( UISkinFactory.create( skins.getTexture( "AreaEmpty" ) ) );
		_surface.moveTo( 350, 150 );
		_surface.resizeTo( 300, 100 );
		_surface.id = "s1";

		_textField = new UITextField( UISkinFactory.create( skins.getTexture( "TextInputBackground" ) ), new UITextFormat( "Helvetica24" ) );
		_textField.moveTo( 350, 270 );
		_textField.resizeTo( 300, 100 );
		_textField.setText( "WHEWHEHello World" );

		Layer container = new Layer();

		container.moveTo( 50, 50 );
		container.addChild( _label );
		container.addChild( _toggleButton1 );
		container.addChild( _toggleButton2 );
		container.addChild( _button );
		container.addChild( _slider );
		container.addChild( _surface );
		container.addChild( _list );
		container.addChild( _textField );

		addChild( container );

		activateEvents();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public void onActionEvent( Action action )
	{
		_label.setText( "Action received " + action.origin );
	}

	@Override
	public String toString()
	{
		return "[EUIWidgets]";
	}
}