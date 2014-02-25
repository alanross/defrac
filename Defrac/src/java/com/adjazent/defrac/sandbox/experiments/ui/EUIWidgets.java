package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.sandbox.Experiment;
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
import com.adjazent.defrac.ui.widget.range.UISlider;
import com.adjazent.defrac.ui.widget.text.UILabel;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIWidgets extends Experiment implements IUIResourceLoaderQueueObserver, IActionObserver
{
	UILabel label;
	UIButton button;
	UIToggleButton toggleButton1;
	UIToggleButton toggleButton2;
	UIToggleGroup toggleGroup;
	UISlider slider;
	UISurface surface;

	public EUIWidgets()
	{
	}

	@Override
	protected void onInit()
	{
		UIFontManager.initialize();
		UITextureManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica24.png", "fonts/helvetica24.fnt", "helvetica" ) );
		queue.add( new UIResourceLoaderTexturePacker( "skins/pro7.png", "skins/pro7.xml", "skins" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		UITextureAtlas skins = UITextureManager.get().getAtlas( "skins" );

		// --------------------- LABEL
		label = new UILabel( new UITextFormat( "helvetica" ) );
		label.setText( "Hello Widgets" );
		label.moveTo( 100, 100 );
		label.id = "l1";

		// --------------------- TOGGLE BUTTON
		toggleButton1 = new UIToggleButton( skins.getTexture( "ButtonSceneSettingsDeselected" ), skins.getTexture( "ButtonSceneSettingsSelected" ) );
		toggleButton1.moveTo( 100, 150 );
		toggleButton1.id = "tb1";

		toggleButton2 = new UIToggleButton( skins.getTexture( "ButtonSceneSettingsDeselected" ), skins.getTexture( "ButtonSceneSettingsSelected" ) );
		toggleButton2.moveTo( 150, 150 );
		toggleButton2.id = "tb2";

		toggleGroup = new UIToggleGroup( toggleButton1, toggleButton2 );
		toggleGroup.onSelect.add( this );

		// --------------------- BUTTON
		button = new UIButton( skins.getTexture( "ButtonChallenge" ) );
		button.moveTo( 100, 200 );
		button.onClick.add( this );
		button.id = "b1";

		// --------------------- SLIDER
		slider = new UISlider( skins.getTexture( "PlayOutSliderTrack" ), skins.getTexture( "PlayOutSliderThumb" ), skins.getTexture( "PlayOutSliderValue" ) );
		slider.resizeTo( 100, slider.height() );
		slider.moveTo( 100, 250 );
		slider.id = "s1";

		// --------------------- SURFACE
		surface = new UISurface( skins.getTexture( "AreaEmpty" ) );
		surface.moveTo( 100, 300 );
		surface.resizeTo( 100, 300 );
		surface.id = "s1";

		addChild( label );
		addChild( toggleButton1 );
		addChild( toggleButton2 );
		addChild( button );
		addChild( slider );
		addChild( surface );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public void onActionEvent( Action action )
	{
		label.setText( "Action received " + action );
	}

	@Override
	public String toString()
	{
		return "[EUIWidgets]";
	}
}