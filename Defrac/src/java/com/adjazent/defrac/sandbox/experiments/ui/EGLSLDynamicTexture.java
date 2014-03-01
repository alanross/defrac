package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.sandbox.Experiment;
import defrac.annotation.MacroWeb;
import defrac.display.*;
import defrac.event.ResourceEvent;
import defrac.gl.*;
import defrac.lang.Procedure;
import defrac.resource.TextureDataResource;

import static com.adjazent.defrac.core.log.Log.info;
import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.createShader;
import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.linkProgram;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EGLSLDynamicTexture extends Experiment implements Procedure<Canvas.Arguments>
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

	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.ui.GLVideoMacro.getVideoPixelsFromJS" )
	private static native boolean getVideoPixelsFromJS( byte[] byteArray );

	@MacroWeb( "com.adjazent.defrac.sandbox.experiments.ui.GLVideoMacro.getVideoTextureFromJS" )
	private static native boolean getVideoTextureFromJS( GL gl, int glTarget, int glFormat, int glType, GLTexture glTexture, TextureData textureData );


	private boolean _reload;
	private Canvas _canvas;
	private Texture _texture;
	private GLBitmapData _bmd;

	private final int _vidWidth = 640;
	private final int _vidHeight = 380;
	private byte[] _vidPixels = new byte[ _vidWidth * _vidHeight * 4 ];

	public EGLSLDynamicTexture()
	{
	}

	@Override
	protected void onInit()
	{
		addChild( _canvas = new Canvas( app.width(), app.height(), this ) );

		_bmd = new GLBitmapData( 8, 8, ( byte ) 127, ( byte ) 127, ( byte ) 0, ( byte ) 0 );

		TextureDataResource loader = TextureDataResource.from( "images/scalegridtest1.png" );
		loader.onComplete.attach( new Procedure<ResourceEvent.Complete<TextureData>>()
		{
			@Override
			public void apply( ResourceEvent.Complete<TextureData> event )
			{
				_texture = new Texture( event.content );
				_reload = true;
			}
		} );
		loader.load();
	}

	@Override
	public void onResize( float width, float height )
	{
		int ratio = 4;

		_canvas.size( width / ratio, height / ratio );
		_canvas.scaleTo( ratio );
	}

	@Override
	public void apply( Canvas.Arguments arguments )
	{
		if( null == VERTEX_SHADER || null == FRAGMENT_SHADER || null == _texture )
		{
			return;
		}

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


		// ------ getVideoPixelsFromJS start
		getVideoPixelsFromJS( _vidPixels );
		TextureData videoTextureData = TextureData.Persistent.fromData(
				_vidPixels,
				_vidWidth,
				_vidHeight,
				TextureDataFormat.RGBA,
				TextureDataRepeat.REPEAT,
				TextureDataSmoothing.NO_SMOOTHING
		);
		GLTexture texture = stage.getOrCreateTexture( videoTextureData );
		gl.bindTexture( gl.TEXTURE_2D, texture );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );
		// ------ getVideoPixelsFromJS end


		//byte[] pixels = GLBitmapData.rgbaCreate( 10, 10, ( byte ) 127, ( byte ) 0, ( byte ) 0, ( byte ) 127 );
		//GLTexture texture = GLBitmapData.createAndSetGLTexture( gl, pixels, 10, 10 );
		//gl.bindTexture( gl.TEXTURE_2D, texture );


		GLUniformLocation resolutionLocation = gl.getUniformLocation( program, "u_resolution" );
		gl.uniform2f( resolutionLocation, app.width(), app.height() );
		GLBuffer buffer = gl.createBuffer();
		gl.bindBuffer( gl.ARRAY_BUFFER, buffer );
		gl.enableVertexAttribArray( positionLocation );
		gl.vertexAttribPointer( positionLocation, 2, gl.FLOAT, false, 0, 0 );
		setRectangle( gl, 100, 100, 400, 300 );
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
		return "[EGLSLDynamicTexture]";
	}
}