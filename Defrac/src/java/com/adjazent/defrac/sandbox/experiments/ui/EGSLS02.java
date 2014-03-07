package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import defrac.display.Canvas;
import defrac.display.Texture;
import defrac.display.TextureData;
import defrac.event.ResourceEvent;
import defrac.gl.*;
import defrac.lang.Procedure;
import defrac.resource.StringResource;
import defrac.resource.TextureDataResource;

import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.createShader;
import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.linkProgram;

/**
 * http://greggman.github.io/webgl-fundamentals/webgl/webgl-2d-image.html
 * http://greggman.github.io/webgl-fundamentals/webgl/lessons/webgl-image-processing.html
 * http://learningwebgl.com/blog/?p=1786
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class EGSLS02 extends Experiment implements Procedure<Canvas.Arguments>
{
	private Procedure<ResourceEvent.Complete<TextureData>> onTextureLoadHandler = new Procedure<ResourceEvent.Complete<TextureData>>()
	{
		@Override
		public void apply( ResourceEvent.Complete<TextureData> event )
		{
			onTextureLoadSuccess( event.content );
		}
	};

	private Procedure<ResourceEvent.Complete<String>> onFragmentShaderLoadHandler = new Procedure<ResourceEvent.Complete<String>>()
	{
		@Override
		public void apply( ResourceEvent.Complete<String> event )
		{
			onFragmentShaderLoadSuccess( event.content );
		}
	};

	private Procedure<ResourceEvent.Complete<String>> onVertexShaderLoadHandler = new Procedure<ResourceEvent.Complete<String>>()
	{
		@Override
		public void apply( ResourceEvent.Complete<String> event )
		{
			onVertexShaderLoadSuccess( event.content );
		}
	};

	private boolean _reload;
	private Canvas _canvas;
	private Texture _textureResource;
	private String _vertexShaderResource;
	private String _fragmentShaderResource;

	public EGSLS02()
	{
	}

	@Override
	protected void onInit()
	{
		_canvas = new Canvas( app.width(), app.height(), this );

		addChild( _canvas );

		TextureDataResource textureResourceLoader = TextureDataResource.from( "img/scalegridtest1.png" );
		textureResourceLoader.onComplete.attach( onTextureLoadHandler );
		textureResourceLoader.load();

		StringResource fragmentResourceLoader = StringResource.from( "shader/glsl02/FragmentShader.glsl" );
		fragmentResourceLoader.onComplete.attach( onFragmentShaderLoadHandler );
		fragmentResourceLoader.load();

		StringResource vertexResourceLoader = StringResource.from( "shader/glsl02/VertexShader.glsl" );
		vertexResourceLoader.onComplete.attach( onVertexShaderLoadHandler );
		vertexResourceLoader.load();
	}

	private void onTextureLoadSuccess( TextureData data )
	{
		Log.info( Context.UI, this, "onTextureLoadSuccess" );

		_textureResource = new Texture( data );

		_reload = true;
	}

	private void onFragmentShaderLoadSuccess( String code )
	{
		Log.info( Context.UI, this, "onFragmentShaderLoadSuccess" );

		_fragmentShaderResource = code;

		_reload = true;
	}

	private void onVertexShaderLoadSuccess( String code )
	{
		Log.info( Context.UI, this, "onVertexShaderLoadSuccess" );

		_vertexShaderResource = code;

		_reload = true;
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
		if( null == _fragmentShaderResource || null == _vertexShaderResource || null == _textureResource )
		{
			return;
		}

		GL gl = arguments.gl;

		GLShader vertexShader = createShader( gl, _vertexShaderResource, GL.VERTEX_SHADER );
		if( null == vertexShader )
		{
			Log.info( Context.UI, this, "VertexShaded error" );
			return;
		}

		GLShader fragmentShader = createShader( gl, _fragmentShaderResource, GL.FRAGMENT_SHADER );
		if( null == fragmentShader )
		{
			Log.info( Context.UI, this, "FragmentShaded error" );
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
			Log.info( Context.UI, this, "We have some code, but no _glProgram which means there was a syntax error" );
			return;
		}

		gl.clear( GL.COLOR_BUFFER_BIT );
		gl.useProgram( program );


		int positionLocation = gl.getAttribLocation( program, "a_position" );
		int texCoordLocation = gl.getAttribLocation( program, "a_texCoord" );

		GLBuffer texCoordBuffer = gl.createBuffer();
		gl.bindBuffer( GL.ARRAY_BUFFER, texCoordBuffer );
		gl.bufferData(
				GL.ARRAY_BUFFER,
				new float[]{
						0.0f, 0.0f,
						1.0f, 0.0f,
						0.0f, 1.0f,
						0.0f, 1.0f,
						1.0f, 0.0f,
						1.0f, 1.0f
				},
				0,12,
				gl.STATIC_DRAW );

		gl.enableVertexAttribArray( texCoordLocation );
		gl.vertexAttribPointer( texCoordLocation, 2, gl.FLOAT, false, 0, 0 );

		// Create a texture.
		GLTexture texture = stage.getOrCreateTexture( _textureResource );
		gl.bindTexture( gl.TEXTURE_2D, texture );

		// Set the parameters so we can render any size image.
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST );
		gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST );

		// Upload the image into the texture.
		//gl.texImage2D( gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, image );

		// lookup uniforms
		GLUniformLocation resolutionLocation = gl.getUniformLocation( program, "u_resolution" );

		// set the resolution
		gl.uniform2f( resolutionLocation, app.width(), app.height() );

		// Create a buffer for the position of the rectangle corners.
		GLBuffer buffer = gl.createBuffer();
		gl.bindBuffer( gl.ARRAY_BUFFER, buffer );
		gl.enableVertexAttribArray( positionLocation );
		gl.vertexAttribPointer( positionLocation, 2, gl.FLOAT, false, 0, 0 );

		// Set a rectangle the same size as the image.
		setRectangle( gl, 0, 0, 400, 300 );

		// Draw the rectangle.
		gl.drawArrays( gl.TRIANGLES, 0, 6 );
	}

	private void setRectangle( GL gl, int x, int y, int width, int height )
	{
		float x1 = x;
		float x2 = x + width;
		float y1 = y;
		float y2 = y + height;
		gl.bufferData(
				GL.ARRAY_BUFFER,
				new float[]{
						x1, y1,
						x2, y1,
						x1, y2,
						x1, y2,
						x2, y1,
						x2, y2
				},
				0, 12, GL.STATIC_DRAW );
	}

	@Override
	public String toString()
	{
		return "[EGSLS02]";
	}
}