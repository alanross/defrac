package com.adjazent.defrac.sandbox.apps.playout;

import com.adjazent.defrac.sandbox.apps.model.Pro7Model;
import com.adjazent.defrac.sandbox.apps.theme.Pro7Theme;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.range.UISlider;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Pro7VideoPlayer extends UISurface
{
	private Pro7VideoArea _video;
	private UISlider _slider;

	public Pro7VideoPlayer()
	{
		super( Pro7Theme.get().createSkin( "AreaEmpty" ) );

		resizeTo( 423, 240 );

		_video = new Pro7VideoArea( Pro7Model.videoProvider.createTextureTile( 0, 0, 423, 240 ) );
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

