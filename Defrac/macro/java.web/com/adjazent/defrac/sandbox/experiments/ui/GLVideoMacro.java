package com.adjazent.defrac.sandbox.experiments.ui;

import defrac.compiler.Context;
import defrac.compiler.macro.Macro;
import defrac.compiler.macro.MethodBody;
import defrac.compiler.macro.Parameter;

import javax.annotation.Nonnull;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class GLVideoMacro extends Macro
{
	public GLVideoMacro( @Nonnull Context context )
	{
		super( context );
	}

	@Nonnull
	public MethodBody getVideoPixelsFromJS( Parameter byteArray )
	{
		return MethodBody( Untyped( "getVideoPixels(${0} )", byteArray ) );
	}

	@Nonnull
	public MethodBody getVideoTextureFromJS( Parameter gl, Parameter glTarget, Parameter glFormat, Parameter glType, Parameter glTexture, Parameter textureData )
	{
		return MethodBody( Untyped( "getVideoTexture(${0}, ${1}, ${2}, ${3}, ${4}, ${5})", gl, glTarget, glFormat, glType, glTexture, textureData ) );
	}

//	@Nonnull
//	public MethodBody getVideoTexture( Parameter gl, Parameter glTarget, Parameter glFormat, Parameter glType, Parameter glTexture, Parameter textureData )
//	{
//		return MethodBody(
//				Variable( "src", ClassTypeReference( "java.lang.Object" ), Untyped( "document.getElementById( \"video\" )" ) ),
//				// HACK
//				Variable(
//						"webGlSubstrate",
//						ClassTypeReference( "defrac.gl.WebGLSubstrate" ),
//						Cast( ClassTypeReference( "defrac.gl.WebGLSubstrate" ), MemberAccess( ParameterAccess( gl ), "gl" ) )
//				),
//				Variable(
//						"webGlContext",
//						ClassTypeReference( "intrinsic.WebGLRenderingContext" ),
//						MemberAccess( VariableAccess( "webGlSubstrate" ), "context" )
//				),
//				If( NE( VariableAccess( "src" ), Null() ),
//						Block(
//								Untyped( "${0}.texImage2D(${1}, 0, ${2}, ${3}, ${4}, ${5})",
//										VariableAccess( "webGlContext" ),
//										StaticAccess( "defrac.gl.GL", "TEXTURE_2D" ),
//										StaticAccess( "defrac.gl.GL", "RGBA" ),
//										StaticAccess( "defrac.gl.GL", "RGB" ),
//										StaticAccess( "defrac.gl.GL", "UNSIGNED_BYTE" ),
//										VariableAccess( "src" )
//								)
//						),
//						Empty()
//				)
//		);
//	}


//	@Nonnull
//	public MethodBody getVideoTexture( Parameter gl, Parameter target, Parameter format, Parameter type, Parameter texture )
//	{
//		return MethodBody(
//				Variable( "src", ClassTypeReference( "java.lang.Object" ), Untyped( "document.getElementById( \"video\" )" ) ),
//				// HACK
//				Variable(
//						"webGlSubstrate",
//						ClassTypeReference( "defrac.gl.WebGLSubstrate" ),
//						Cast(
//								ClassTypeReference( "defrac.gl.WebGLSubstrate" ),
//								MemberAccess( ParameterAccess( gl ), "gl" )
//						)
//				),
//				Variable(
//						"webGlContext",
//						ClassTypeReference( "intrinsic.WebGLRenderingContext" ),
//						MemberAccess( VariableAccess( "webGlSubstrate" ), "context" )
//				),
//				If( NE( VariableAccess( "src" ), Null() ),
//						Block(
//								Untyped( "${0}.texImage2D(${1}, 0, ${2}, ${3}, ${4}, ${5})",
//										VariableAccess( "webGlContext" ),
//										StaticAccess( "defrac.gl.GL", "TEXTURE_2D" ),
//										StaticAccess( "defrac.gl.GL", "RGBA" ),
//										StaticAccess( "defrac.gl.GL", "RGB" ),
//										StaticAccess( "defrac.gl.GL", "UNSIGNED_BYTE" ),
//										VariableAccess( "src" )
//								)
//						),
//						Empty()
//				)
//		);
//	}
}