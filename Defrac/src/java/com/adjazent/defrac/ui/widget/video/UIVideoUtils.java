package com.adjazent.defrac.ui.widget.video;

import com.adjazent.defrac.math.MMath;
import defrac.annotation.MacroWeb;
import defrac.gl.GL;

import java.util.Arrays;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIVideoUtils
{
	public static final int MP4 = 1;
	public static final int OGV = 2;
	public static final int WEBM = 3;

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.supportsVideo" )
	public static native boolean supportsVideo();

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.supportsMP4" )
	public static native String supportsMP4();

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.supportsOGV" )
	public static native String supportsOGV();

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.supportsWEBM" )
	public static native String supportsWEBM();

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.isReady" )
	public static native boolean isReady();

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.attachVideoElement" )
	public static native boolean attachVideoElement( String videoUri );

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.detachVideoElement" )
	public static native boolean detachVideoElement();

	@MacroWeb( "com.adjazent.defrac.ui.video.UIVideoMacros.uploadVideoTexture" )
	public static native boolean uploadVideoTexture( GL gl );

	public static int getProbableVideoFormat()
	{
		VideoFormat ogv = new VideoFormat( OGV, convertSupportResult( supportsOGV() ) );
		VideoFormat mp4 = new VideoFormat( MP4, convertSupportResult( supportsMP4() ) );
		VideoFormat webm = new VideoFormat( WEBM, convertSupportResult( supportsWEBM() ) );

		VideoFormat[] formats = new VideoFormat[]{ ogv, mp4, webm };

		Arrays.sort( formats );

		// none are supported
		if( formats[ 0 ].support == 0 )
		{
			return -1;
		}

		// prefer ogv
		if( ogv.support == formats[ 0 ].support )
		{
			return ogv.id;
		}

		// return best fit
		return formats[ 0 ].id;
	}

	private static int convertSupportResult( String type )
	{
		// return value for video support, per crappy definition of js:
		// "probably" if the browser is fairly confident it can play this format
		// "maybe" if the browser thinks it might be able to play this format
		// "" (an empty string) if the browser is certain it can’t play this format

		if( type != null && !type.isEmpty() )
		{
			if( type.compareToIgnoreCase( "maybe" ) == 0 )
			{
				return 1;
			}
			if( type.compareToIgnoreCase( "probably" ) == 0 )
			{
				return 2;
			}
		}

		return 0;
	}

	public UIVideoUtils()
	{
	}

	@Override
	public String toString()
	{
		return "[UIVideoUtils]";
	}

	private static final class VideoFormat implements Comparable<VideoFormat>
	{
		int id;
		int support;

		private VideoFormat( int id, int support )
		{
			this.id = id;
			this.support = support;
		}

		public int compareTo( VideoFormat other )
		{
			return MMath.clampInt( other.support - this.support, -1, 1 );
		}
	}
}