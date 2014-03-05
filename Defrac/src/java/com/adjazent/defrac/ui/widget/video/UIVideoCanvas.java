package com.adjazent.defrac.ui.widget.video;

import com.adjazent.defrac.core.log.Context;
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
public final class UIVideoCanvas extends Canvas implements Procedure<Canvas.Arguments>
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

	private static final byte[] BLANK_PIXELS = new byte[]{ 0, 0, 0, (byte)0xff };

	private GLProgram program;
	private boolean reload;

	private GLBuffer texCoordBuffer;
	private GLBuffer vertexBuffer;

	private GLUniformLocation resolutionLocation;
	private int positionLocation;
	private int texCoordLocation;

	public UIVideoCanvas( float width, float height, String fileName )
	{
		super( width, height );

		procedure( this );

		reload = true;

		UIVideoUtils.attachVideoElement( fileName );
	}

	@Override
	public void apply( Canvas.Arguments arguments )
	{
		GL gl = arguments.gl;

		if( reload )
		{
			reload = false;

			if( null != program )
			{
				// We already have a working program, get rid of it
				gl.deleteProgram( program );
				program = null;
			}

			// Create a new vertex and fragment shader
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

			// Create a new program
			program = gl.createProgram();

			// Link the program, this will do some cleanup of the shaders for us
			if( !linkProgram( gl, program, vertexShader, fragmentShader, new String[]{ "position" } ) )
			{
				gl.deleteProgram( program );
				program = null;
				return;
			}

			resolutionLocation = gl.getUniformLocation( program, "u_resolution" );
			positionLocation = gl.getAttribLocation( program, "a_position" );
			texCoordLocation = gl.getAttribLocation( program, "a_texCoord" );
		}

		if( null == program )
		{
			info( Context.UI, this, "We have some code, but no glProgram which means there was a syntax error" );
			return;
		}

		gl.clear( GL.COLOR_BUFFER_BIT );
		gl.useProgram( program );

		if( null == texCoordBuffer )
		{
			float[] uvs = createUvs();
			texCoordBuffer = gl.createBuffer();
			gl.bindBuffer( GL.ARRAY_BUFFER, texCoordBuffer );
			gl.bufferData( GL.ARRAY_BUFFER, uvs, 0, uvs.length, gl.STATIC_DRAW );
		}
		else
		{
			gl.bindBuffer( GL.ARRAY_BUFFER, texCoordBuffer );
		}

		gl.enableVertexAttribArray( texCoordLocation );
		gl.vertexAttribPointer( texCoordLocation, 2, gl.FLOAT, false, 0, 0 );

		if( null == vertexBuffer )
		{
			float[] vertices = createVertices( 0, 0, width(), height() );
			vertexBuffer = gl.createBuffer();
			gl.bindBuffer( gl.ARRAY_BUFFER, vertexBuffer );
			gl.bufferData( GL.ARRAY_BUFFER, vertices, 0, vertices.length, GL.STATIC_DRAW );
		}
		else
		{
			gl.bindBuffer( gl.ARRAY_BUFFER, vertexBuffer );
		}

		gl.enableVertexAttribArray( positionLocation );
		gl.vertexAttribPointer( positionLocation, 2, gl.FLOAT, false, 0, 0 );

		GLTexture texture = gl.createTexture();
		gl.bindTexture( gl.TEXTURE_2D, texture );

		// still need function to check if js video is ready and playing
		boolean videoAvailable = true;

		if( videoAvailable )
		{
			UIVideoUtils.uploadVideoTexture( gl );
		}
		else
		{
			gl.texImage2D( GL.TEXTURE_2D, 0, GL.RGBA, 1, 1, 0, GL.RGBA, GL.UNSIGNED_BYTE, BLANK_PIXELS );
		}

		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );
		//------------

		gl.uniform2f( resolutionLocation, width(), height() );
		gl.drawArrays( gl.TRIANGLES, 0, 6 );
	}

	private float[] createVertices( float x, float y, float width, float height )
	{
		float xw = x + width;
		float yh = y + height;

		return new float[]{ x, y, xw, y, x, yh, x, yh, xw, y, xw, yh };
	}

	private float[] createUvs()
	{
		return new float[]{ 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
	}

	@Override
	public String toString()
	{
		return "[UIVideoCanvas]";
	}
}