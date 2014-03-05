package com.adjazent.defrac.sandbox.experiments.apps;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.button.UIToggleGroup;
import com.adjazent.defrac.ui.widget.list.UICellData;
import com.adjazent.defrac.ui.widget.list.UIList;
import com.adjazent.defrac.ui.widget.range.UISlider;
import com.adjazent.defrac.ui.widget.text.UILabel;
import defrac.display.Layer;

import static com.adjazent.defrac.core.log.Log.info;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7Studio extends Experiment implements IPro7CoreUIObserver, IActionObserver
{
	private UILabel _label;
	private UIButton _button;
	private UIToggleButton _toggleButton1;
	private UIToggleButton _toggleButton2;
	private UIToggleGroup _toggleGroup;
	private UISlider _slider;
	private UISurface _surface;
	private UIList _list;

	public Pro7Studio()
	{
	}

	@Override
	protected void onInit()
	{
		Pro7UI.initialize( this );
	}

	@Override
	public void onPro7CoreUISetupSuccess()
	{
		_label = Pro7UI.get().createLabelBig();
		_label.setText( "Hello UILabel!" );
		_label.moveTo( 50, 50 );

		_toggleButton1 = Pro7UI.get().createToggleButton( "ButtonSceneSettingsDeselected", "ButtonSceneSettingsSelected" );
		_toggleButton1.moveTo( 50, 100 );
		_toggleButton1.id = "tb1";

		_toggleButton2 = Pro7UI.get().createToggleButton( "ButtonSceneSettingsDeselected", "ButtonSceneSettingsSelected" );
		_toggleButton2.moveTo( 100, 100 );
		_toggleButton2.id = "tb2";

		_toggleGroup = new UIToggleGroup( _toggleButton1, _toggleButton2 );
		_toggleGroup.onSelect.add( this );

		_button = Pro7UI.get().createButton( "ButtonChallenge" );
		_button.moveTo( 150, 100 );
		_button.onClick.add( this );
		_button.id = "b1";

		_slider = Pro7UI.get().createSlider( "PlayOutSliderTrack", "PlayOutSliderThumb", "PlayOutSliderValue" );
		_slider.resizeTo( 100, _slider.height() );
		_slider.moveTo( 600, 100 );
		_slider.onValueChange.add( this );
		_slider.onValueComplete.add( this );
		_slider.id = "s1";

		_list = Pro7UI.get().createList();
		_list.moveTo( 50, 150 );
		_list.resizeTo( 200, 350 );

		for( int i = 0; i < 80; ++i )
		{
			_list.addItem( new UICellData( "Cell Data: " + i, 20 ) );
		}

		_surface = Pro7UI.get().createSurface( "AreaEmpty" );
		_surface.moveTo( 350, 150 );
		_surface.resizeTo( 300, 100 );
		_surface.id = "s1";

		Layer container = new Layer();
		container.moveTo( 50, 50 );

		container.addChild( _label );
		container.addChild( _toggleButton1 );
		container.addChild( _toggleButton2 );
		container.addChild( _button );
		container.addChild( _slider );
		container.addChild( _surface );
		container.addChild( _list );

		addChild( container );

		activateEvents();
	}

	@Override
	public void onPro7CoreUISetupFailure( Error error )
	{
		info( Context.DEFAULT, this, "Failed to load fonts" );
	}

	@Override
	public void onActionEvent( Action action )
	{
		_label.setText( "Action received " + action.getOrigin() );
	}


	@Override
	public String toString()
	{
		return "[Pro7Studio]";
	}
}