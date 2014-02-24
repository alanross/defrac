package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.core.notification.signals.ISignalReceiver;
import com.adjazent.defrac.core.notification.signals.ISignalSource;
import com.adjazent.defrac.core.notification.signals.Signals;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.resource.UIResourceLoaderTexturePacker;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.texture.UITextureManager;
import com.adjazent.defrac.ui.widget.pro7.*;
import com.adjazent.defrac.ui.widget.text.UILabel;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIWidgets extends Experiment implements IUIResourceLoaderQueueObserver, ISignalReceiver, IActionObserver
{
	UILabel label;
	UIButton button;
	UIToggleButton toggleButton1;
	UIToggleButton toggleButton2;
	UIToggleGroup toggleGroup;
	UISlider slider;

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

		Signals.initialize();
		Signals.addReceiver( this );

		// --------------------- LABEL
		label = new UILabel( new UITextFormat( "helvetica" ) );
		label.setText( "Hello Widgets" );
		label.moveTo( 100, 100 );

		// --------------------- TOGGLE BUTTON
		toggleButton1 = new UIToggleButton(
				skins.getTexture( "ButtonSceneSettingsDeselected" ),
				skins.getTexture( "ButtonSceneSettingsSelected" )
		);
		toggleButton1.moveTo( 100, 150 );

		toggleButton2 = new UIToggleButton(
				skins.getTexture( "ButtonSceneSettingsDeselected" ),
				skins.getTexture( "ButtonSceneSettingsSelected" )
		);
		toggleButton2.moveTo( 150, 150 );

		toggleGroup = new UIToggleGroup( toggleButton1, toggleButton2 );

		// --------------------- BUTTON
		button = new UIButton( skins.getTexture( "ButtonChallenge" ) );
		button.moveTo( 100, 200 );
		button.clickAction.add( this );

		// --------------------- SLIDER
		slider = new UISlider(
				skins.getTexture( "PlayOutSliderTrack" ),
				skins.getTexture( "PlayOutSliderThumb" ),
				skins.getTexture( "PlayOutSliderValue" )
		);
		slider.scaleToSize( 100, 17 );
		slider.moveTo( 100, 250 );

		addChild( label );
		addChild( toggleButton1 );
		addChild( toggleButton2 );
		addChild( button );
		addChild( slider );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public void onSignal( int signalType, ISignalSource signalSource )
	{
		label.setText( "Signal received " + signalSource );

		if( signalSource == toggleButton1 )
		{
			if( signalType == UISignalTypes.BUTTON_SELECT )
			{
				info( Context.DEFAULT, this, "TOGGLE BUTTON SELECTED" );
			}
			else if( signalType == UISignalTypes.BUTTON_DESELECT )
			{
				info( Context.DEFAULT, this, "TOGGLE BUTTON DESELCTED" );
			}
		}
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