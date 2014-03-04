package com.adjazent.defrac.ui.widget.video;

import defrac.annotation.MacroWeb;
import defrac.gl.GL;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UIVideoUtil
{
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

	public UIVideoUtil()
	{

	}

	@Override
	public String toString()
	{
		return "[UIVideoUtil]";
	}
}