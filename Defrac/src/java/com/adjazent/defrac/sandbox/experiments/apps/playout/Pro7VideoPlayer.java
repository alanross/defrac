package com.adjazent.defrac.sandbox.experiments.apps.playout;

import com.adjazent.defrac.sandbox.experiments.apps.theme.Pro7Theme;
import com.adjazent.defrac.sandbox.experiments.apps.video.Pro7VideoSourceManager;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.range.UISlider;
import defrac.display.Image;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7VideoPlayer extends UISurface
{
	private UISlider _slider;
	private Image _video;

	public Pro7VideoPlayer()
	{
		super( Pro7Theme.get().createSkin( "AreaEmpty" ) );

		resizeTo( 423, 240 );

		_video = new Image( Pro7VideoSourceManager.get().createTextureTile( 0,0, 423, 240 ) );
		addChild( _video );

		_slider = Pro7Theme.get().createSlider( "PlayOutSliderTrack", "PlayOutSliderThumb", "PlayOutSliderValue" );
		_slider.resizeTo( 403, 17 );
		_slider.moveTo( 10, 200 );
		addChild( _slider );
	}

	@Override
	public String toString()
	{
		return "[Pro7VideoPlayer]";
	}
}

