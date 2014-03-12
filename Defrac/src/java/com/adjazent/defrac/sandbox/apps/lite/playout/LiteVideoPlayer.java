package com.adjazent.defrac.sandbox.apps.lite.playout;

import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.widget.range.UISlider;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteVideoPlayer extends UISurface
{
	private LiteVideoDisplay _video;
	private UISlider _slider;

	public LiteVideoPlayer()
	{
		super( LiteCore.ui.createSkin( "AreaEmpty" ) );

		resizeTo( 423, 240 );

		_video = new LiteVideoDisplay( LiteCore.video.createTextureTile( 0, 0, 423, 240 ) );

		_slider = LiteCore.ui.createSlider( "PlayOutSliderTrack", "PlayOutSliderThumb", "PlayOutSliderValue" );
		_slider.resizeTo( 403, 17 );
		_slider.moveTo( 10, 200 );

		addChild( _video );
		addChild( _slider );
	}

	@Override
	public String toString()
	{
		return "[LiteVideoPlayer]";
	}
}

