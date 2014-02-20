package com.adjazent.defrac.ui.surface;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.math.MColor;
import defrac.display.Canvas;
import defrac.gl.*;
import defrac.lang.Procedure;

import static com.adjazent.defrac.core.log.Log.info;
import static com.adjazent.defrac.sandbox.experiments.ui.canvas.GLUtil.createShader;
import static com.adjazent.defrac.sandbox.experiments.ui.canvas.GLUtil.linkProgram;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UICanvasPainter implements Procedure<Canvas.Arguments>
{
	private static final String FRAGMENT_SHADER = "" +
			"varying vec4 vColor;                                   \n" +
			"                                                       \n" +
			"void main()                                            \n" +
			"{                                                      \n" +
			"    gl_FragColor = vColor;                             \n" +
			"}";

	private static final String VERTEX_SHADER = "" +
			"attribute vec2 aPosition;                              \n" +
			"attribute vec4 aVertexColor;                           \n" +
			"uniform vec2 uResolution;                              \n" +
			"varying vec4 vColor;                                   \n" +
			"                                                       \n" +
			"void main()                                            \n" +
			"{                                                      \n" +
			"    // convert the rectangle from pixels to 0.0 to 1.0 \n" +
			"    vec2 zeroToOne = aPosition / uResolution;          \n" +
			"    // convert from 0->1 to 0->2                       \n" +
			"    vec2 zeroToTwo = zeroToOne * 2.0;                  \n" +
			"    // convert from 0->2 to -1->+1 (clipspace)         \n" +
			"    vec2 clipSpace = zeroToTwo - 1.0;                  \n" +
			"    gl_Position = vec4(clipSpace * vec2(1, -1), 0, 1); \n" +
			"    vColor = aVertexColor; // vec4(1,1,0,1);  // rgba  \n" +
			"}";

	private boolean _reload;
	private String _vertexShaderResource;
	private String _fragmentShaderResource;

	private GLProgram _glProgram;
	private GLBuffer _glRectBuffer;
	private GLBuffer _glColorBuffer;
	private GLUniformLocation _glUResolution;
	private int _glAColor;

	private float[] _bounds = new float[ 12 ];
	private float[] _colors = new float[ 6 ];

	public UICanvasPainter()
	{
		_fragmentShaderResource = FRAGMENT_SHADER;
		_vertexShaderResource = VERTEX_SHADER;

		_reload = true;
	}

	public void fillRect( int x, int y, int width, int height, int color )
	{
		int[] c255 = MColor.hex2argb( color );
		float a = ( float ) c255[ 0 ] / 255.0f;
		float r = ( float ) c255[ 1 ] / 255.0f;
		float g = ( float ) c255[ 2 ] / 255.0f;
		float b = ( float ) c255[ 3 ] / 255.0f;

		_colors = new float[]{
				r, g, b, 1.0f,
				r, g, b, 1.0f,
				r, g, b, 1.0f,
				r, g, b, 1.0f,
				r, g, b, 1.0f,
				r, g, b, 1.0f
		};

		_bounds[ 0 ] = x;
		_bounds[ 1 ] = y;

		_bounds[ 2 ] = x + width;
		_bounds[ 3 ] = y;

		_bounds[ 4 ] = x;
		_bounds[ 5 ] = y + height;

		_bounds[ 6 ] = x;
		_bounds[ 7 ] = y + height;

		_bounds[ 8 ] = x + width;
		_bounds[ 9 ] = y;

		_bounds[ 10 ] = x + width;
		_bounds[ 11 ] = y + height;

		_reload = true;
	}

	@Override
	public void apply( Canvas.Arguments arguments )
	{
		if( null == _fragmentShaderResource || null == _vertexShaderResource )
		{
			return;
		}

		GL gl = arguments.gl;

		if( _reload )
		{
			_reload = false;

			if( null != _glProgram )
			{
				gl.deleteProgram( _glProgram );
				_glProgram = null;
				_glUResolution = null;
			}

			GLShader vertexShader = createShader( gl, _vertexShaderResource, GL.VERTEX_SHADER );
			if( null == vertexShader )
			{
				info( Context.UI, this, "VertexShaded error" );
				return;
			}

			GLShader fragmentShader = createShader( gl, _fragmentShaderResource, GL.FRAGMENT_SHADER );
			if( null == fragmentShader )
			{
				info( Context.UI, this, "FragmentShaded error" );
				return;
			}

			_glProgram = gl.createProgram();

			if( !linkProgram( gl, _glProgram, vertexShader, fragmentShader, new String[]{ "aPosition", "aVertexColor" } ) )
			{
				gl.deleteProgram( _glProgram );
				_glProgram = null;
				return;
			}

			_glUResolution = gl.getUniformLocation( _glProgram, "uResolution" );
			_glAColor = gl.getAttribLocation( _glProgram, "aVertexColor" );
		}

		if( null == _glProgram )
		{
			info( Context.UI, this, "Syntax Error in the GLProgram." );
			return;
		}

		gl.clear( GL.COLOR_BUFFER_BIT );
		gl.useProgram( _glProgram );
		gl.uniform2f( _glUResolution, arguments.width, arguments.height );

		if( null == _glRectBuffer )
		{
			_glRectBuffer = gl.createBuffer();
			gl.bindBuffer( GL.ARRAY_BUFFER, _glRectBuffer );
			gl.bufferData( GL.ARRAY_BUFFER, _bounds, GL.STATIC_DRAW );
		}
		else
		{
			gl.bindBuffer( GL.ARRAY_BUFFER, _glRectBuffer );
		}

		gl.enableVertexAttribArray( 0 );
		gl.vertexAttribPointer( 0, 2, GL.FLOAT, false, 0, 0 );

		if( null == _glColorBuffer )
		{
			_glColorBuffer = gl.createBuffer();
			gl.bindBuffer( GL.ARRAY_BUFFER, _glColorBuffer );
			gl.bufferData( GL.ARRAY_BUFFER, _colors, GL.STATIC_DRAW );
		}
		else
		{
			gl.bindBuffer( GL.ARRAY_BUFFER, _glColorBuffer );
		}

		gl.enableVertexAttribArray( _glAColor );
		gl.vertexAttribPointer( _glAColor, 4, GL.FLOAT, false, 0, 0 );

		gl.drawArrays( GL.TRIANGLES, 0, 6 );
	}

	@Override
	public String toString()
	{
		return "[UICanvasPainter]";
	}
}