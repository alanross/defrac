package com.adjazent.defrac.ui.widget.video;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.stage.StageProvider;
import defrac.annotation.MacroWeb;
import defrac.display.Canvas;
import defrac.gl.*;
import defrac.lang.Procedure;

import static com.adjazent.defrac.core.log.Log.info;
import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.createShader;
import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.linkProgram;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class Video extends Canvas implements Procedure<Canvas.Arguments>
{
	private static final String FRAGMENT_SHADER = "" +
			"precision mediump float;                              \n" +
			"uniform sampler2D u_image;                            \n" +
			"varying vec2 v_texCoord;                              \n" +
			"                                                      \n" +
			"void main() {                                         \n" +
			"    gl_FragColor = texture2D(u_image, v_texCoord);    \n" +
			"}";

	private static final String VERTEX_SHADER = "" +
			"attribute vec2 a_position;                            \n" +
			"attribute vec2 a_texCoord;                            \n" +
			"uniform vec2 u_resolution;                            \n" +
			"varying vec2 v_texCoord;                              \n" +
			"                                                      \n" +
			"void main() {                                         \n" +
			"vec2 zeroToOne = a_position / u_resolution;           \n" +
			"vec2 zeroToTwo = zeroToOne * 2.0;                     \n" +
			"vec2 clipSpace = zeroToTwo - 1.0;                     \n" +
			"gl_Position = vec4(clipSpace * vec2(1, -1), 0, 1);    \n" +
			"v_texCoord = a_texCoord;                              \n" +
			"}";

	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.ui.GLVideo.attachVideoElement" )
	private static native boolean attachVideoElement( String fileName );

	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.ui.GLVideo.detachVideoElement" )
	private static native boolean detachVideoElement( String fileName );

	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.ui.GLVideo.uploadVideoTexture" )
	private static native boolean uploadVideoTexture( GL gl );

	public Video( float width, float height )
	{
		super( width, height );

		procedure( this );
	}

	public void load( String fileName )
	{
		attachVideoElement( fileName );
	}

	@Override
	public void apply( Canvas.Arguments arguments )
	{
		GL gl = arguments.gl;

		GLShader vertexShader = createShader( gl, VERTEX_SHADER, GL.VERTEX_SHADER );
		if( null == vertexShader )
		{
			info( Context.UI, this, "VertexShaded error" );
			return;
		}

		GLShader fragmentShader = createShader( gl, FRAGMENT_SHADER, GL.FRAGMENT_SHADER );
		if( null == fragmentShader )
		{
			info( Context.UI, this, "FragmentShaded error" );
			return;
		}

		GLProgram program = gl.createProgram();

		if( !linkProgram( gl, program, vertexShader, fragmentShader, new String[]{ "a_position" } ) )
		{
			gl.deleteProgram( program );
			return;
		}

		if( null == program )
		{
			info( Context.UI, this, "We have some code, but no glProgram which means there was a syntax error" );
			return;
		}

		gl.clear( GL.COLOR_BUFFER_BIT );
		gl.useProgram( program );

		int positionLocation = gl.getAttribLocation( program, "a_position" );
		int texCoordLocation = gl.getAttribLocation( program, "a_texCoord" );

		float[] uvs = new float[]{ 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };

		GLBuffer texCoordBuffer = gl.createBuffer();
		gl.bindBuffer( GL.ARRAY_BUFFER, texCoordBuffer );
		gl.bufferData( GL.ARRAY_BUFFER, uvs, 0, uvs.length, gl.STATIC_DRAW );
		gl.enableVertexAttribArray( texCoordLocation );
		gl.vertexAttribPointer( texCoordLocation, 2, gl.FLOAT, false, 0, 0 );

		//------------ video texture
		GLTexture texture = gl.createTexture();
		gl.bindTexture( gl.TEXTURE_2D, texture );
		//byte[] rgba = new byte[]{ ( byte ) 127, ( byte ) 127, ( byte ) 127, ( byte ) 127 };
		//gl.uploadVideoTexture( GL.TEXTURE_2D, 0, GL.RGBA, 1, 1, 0, GL.RGBA, GL.UNSIGNED_BYTE, rgba );
		uploadVideoTexture( gl );

		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );
		//------------

		GLUniformLocation resolutionLocation = gl.getUniformLocation( program, "u_resolution" );
		gl.uniform2f( resolutionLocation, StageProvider.stage.width(), StageProvider.stage.height() );
		GLBuffer buffer = gl.createBuffer();
		gl.bindBuffer( gl.ARRAY_BUFFER, buffer );
		gl.enableVertexAttribArray( positionLocation );
		gl.vertexAttribPointer( positionLocation, 2, gl.FLOAT, false, 0, 0 );
		setRectangle( gl, 100, 100, 200, 300 );
		gl.drawArrays( gl.TRIANGLES, 0, 6 );
	}

	private void setRectangle( GL gl, int x, int y, int width, int height )
	{
		float x1 = x;
		float x2 = x + width;
		float y1 = y;
		float y2 = y + height;

		float[] vertices = new float[]{ x1, y1, x2, y1, x1, y2, x1, y2, x2, y1, x2, y2 };

		gl.bufferData( GL.ARRAY_BUFFER, vertices, 0, vertices.length, GL.STATIC_DRAW );
	}

	@Override
	public String toString()
	{
		return "[Video]";
	}
}