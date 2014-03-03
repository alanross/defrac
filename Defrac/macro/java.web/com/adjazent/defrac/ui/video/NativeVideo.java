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
	public MethodBody attachVideoElement( Parameter fileName )
	{
		return MethodBody( Untyped( "console.log( \"attachVideoElement\",${0} )", fileName ) );
	}

	@Nonnull
	public MethodBody detachVideoElement( Parameter fileName )
	{
		return MethodBody( Untyped( "console.log( \'detachVideoElement\',${0} )", fileName ) );
	}

	@Nonnull
	public MethodBody uploadVideoTexture( @Nonnull final Parameter gl )
	{
		return MethodBody(
				Variable(
						"src",
						ClassTypeReference( "intrinsic.HTMLVideoElement" ),
						Untyped( "document.getElementById( \"video\" )" ) ),
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
				If( NE( VariableAccess( "src" ), Null() ),
						Block(
								MethodCall(
										MemberAccess( VariableAccess( "webGlContext" ), "texImage2D" ),
										List(
												StaticAccess( "defrac.gl.GLSubstrate", "TEXTURE_2D" ),
												Int( 0 ),
												StaticAccess( "defrac.gl.GLSubstrate", "RGBA" ),
												StaticAccess( "defrac.gl.GLSubstrate", "RGBA" ),
												StaticAccess( "defrac.gl.GLSubstrate", "UNSIGNED_BYTE" ),
												VariableAccess( "src" )
										)
								)
						),
						Empty()
				)
		);
	}
}