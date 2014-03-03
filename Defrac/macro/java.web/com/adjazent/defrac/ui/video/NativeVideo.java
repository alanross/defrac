package com.adjazent.defrac.ui.video;

import defrac.compiler.Context;
import defrac.compiler.macro.Macro;
import defrac.compiler.macro.MethodBody;
import defrac.compiler.macro.Parameter;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class NativeVideo extends Macro
{
	public NativeVideo( @Nonnull Context context )
	{
		super( context );
	}

	@Nonnull
	public MethodBody canPlayVideo()
	{
		// double ! turns return value into boolean
		return MethodBody( Return( Untyped( "!!document.createElement('video').canPlayType" ) ) );
	}

	@Nonnull
	public MethodBody canPlayMP4()
	{
		// return value, per crappy definition of js:
		// "probably" if the browser is fairly confident it can play this format
		// "maybe" if the browser thinks it might be able to play this format
		// "" (an empty string) if the browser is certain it can’t play this format
		return MethodBody( Return( Untyped( "!!document.createElement('video').canPlayType('video/mp4;')" ) ) );
	}

	@Nonnull
	public MethodBody canPlayOGV()
	{
		return MethodBody( Return( Untyped( "!!document.createElement('video').canPlayType('video/ogg;')" ) ) );
	}

	@Nonnull
	public MethodBody canPlayWEBM()
	{
		return MethodBody( Return( Untyped( "!!document.createElement('video').canPlayType('video/webm;')" ) ) );
	}

	@Nonnull
	public MethodBody attachVideoElement( Parameter fileName )
	{
		return MethodBody(
				Untyped( "var v = document.createElement('video');                 \n" +
						"                                                          \n" +
						"v.setAttribute( 'muted', true );                          \n" +
						"v.setAttribute( 'preload', true );                        \n" +
						"v.setAttribute( 'autoplay', true );                       \n" +
						"v.setAttribute( 'loop', true );                           \n" +
						"v.src = 'video_640x360.mp4';                              \n" + // to be replaced with filename
						"v.id = 'the_video';                                       \n" +
						"v.play();                                                 \n" +
						"                                                          \n" +
						"var c = document.createElement('div');                    \n" +
						"c.id = 'the_video_container';                             \n" +
						"c.style.visibility = 'hidden';                            \n" +
						"c.appendChild(v);                                         \n" +
						"document.body.insertBefore( c, document.body.firstChild );\n",
						fileName
				)
		);
	}

	@Nonnull
	public MethodBody detachVideoElement()
	{
		return MethodBody(
				Untyped( "var v = document.getElementById('the_video');            \n" +
						"if( v ){ v.stop();}                                       \n" +
						"                                                          \n" +
						"var c = document.getElementById('the_video_container');   \n" +
						"if( c ){ c.parentNode.removeChild( c ); }                 \n"
				)
		);
	}

	@Nonnull
	public MethodBody uploadVideoTexture( @Nonnull final Parameter gl )
	{
		return MethodBody(
				Variable(
						"videoSource",
						ClassTypeReference( "intrinsic.HTMLVideoElement" ),
						Untyped( "document.getElementById( 'the_video' )" ) ),
				Variable(
						"webGlSubstrate",
						ClassTypeReference( "defrac.gl.WebGLSubstrate" ),
						Cast(
								ClassTypeReference( "defrac.gl.WebGLSubstrate" ),
								MemberAccess( ParameterAccess( gl ), "gl" )
						)
				),
				Variable(
						"webGlContext",
						ClassTypeReference( "intrinsic.WebGLRenderingContext" ), MemberAccess( VariableAccess( "webGlSubstrate" ), "context" )
				),
				If( NE( VariableAccess( "videoSource" ), Null() ),
						Block(
								MethodCall(
										MemberAccess( VariableAccess( "webGlContext" ), "texImage2D" ),
										List(
												StaticAccess( "defrac.gl.GLSubstrate", "TEXTURE_2D" ),
												Int( 0 ),
												StaticAccess( "defrac.gl.GLSubstrate", "RGBA" ),
												StaticAccess( "defrac.gl.GLSubstrate", "RGBA" ),
												StaticAccess( "defrac.gl.GLSubstrate", "UNSIGNED_BYTE" ),
												VariableAccess( "videoSource" )
										)
								)
						),
						Empty()
				)
		);
	}
}