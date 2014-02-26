package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.log.Context;
import com.adjazent.defrac.core.log.Log;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IAppResize;
import defrac.display.Canvas;
import defrac.event.ResourceEvent;
import defrac.gl.*;
import defrac.lang.Procedure;
import defrac.resource.StringResource;

import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.createShader;
import static com.adjazent.defrac.sandbox.samples.canvas.GLUtil.linkProgram;

/**
 * http://greggman.github.io/webgl-fundamentals/webgl/lessons/webgl-fundamentals.html
 *
 * @author Alan Ross
 * @version 0.1
 */
public final class EGSLS01 extends Experiment implements Procedure<Canvas.Arguments>, IAppResize
{
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
	private String _vertexShaderResource;
	private String _fragmentShaderResource;

	private GLProgram _glProgram;
	private GLBuffer _glBuffer;
	private GLUniformLocation _glUniformResolution;

	public EGSLS01()
	{
	}

	@Override
	protected void onInit()
	{
		_canvas = new Canvas( app.width(), app.height(), this );

		addChild( _canvas );

		StringResource fragmentResourceLoader = StringResource.from( "shader/glsl01/FragmentShader.glsl" );
		fragmentResourceLoader.onComplete.attach( onFragmentShaderLoadHandler );
		fragmentResourceLoader.load();

		StringResource vertexResourceLoader = StringResource.from( "shader/glsl01/VertexShader.glsl" );
		vertexResourceLoader.onComplete.attach( onVertexShaderLoadHandler );
		vertexResourceLoader.load();
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
				_glUniformResolution = null;
			}

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

			_glProgram = gl.createProgram();

			if( !linkProgram( gl, _glProgram, vertexShader, fragmentShader, new String[]{ "position" } ) )
			{
				gl.deleteProgram( _glProgram );
				_glProgram = null;
				return;
			}

			_glUniformResolution = gl.getUniformLocation( _glProgram, "resolution" );
		}

		if( null == _glProgram )
		{
			Log.info( Context.UI, this, "We have some code, but no _glProgram which means there was a syntax error" );
			return;
		}

		if( null == _glBuffer )
		{
			_glBuffer = gl.createBuffer();

			gl.bindBuffer( GL.ARRAY_BUFFER, _glBuffer );

			gl.bufferData(
					GL.ARRAY_BUFFER,
					new float[]{
							10.0f, 20.0f,
							80.0f, 20.0f,
							10.0f, 30.0f,
							10.0f, 30.0f,
							80.0f, 20.0f,
							80.0f, 30.0f
					},
					0, 12,
					GL.STATIC_DRAW
			);
		}
		else
		{
			// We already have a _glBuffer and need to bind it!
			gl.bindBuffer( GL.ARRAY_BUFFER, _glBuffer );
		}

		gl.clear( GL.COLOR_BUFFER_BIT );
		gl.useProgram( _glProgram );
		gl.uniform2f( _glUniformResolution, arguments.width, arguments.height );

		gl.enableVertexAttribArray( 0 );
		gl.vertexAttribPointer( 0, 2, GL.FLOAT, false, 0, 0 );
		gl.drawArrays( GL.TRIANGLES, 0, 6 );
	}

	@Override
	public String toString()
	{
		return "[EGSLS01]";
	}
}