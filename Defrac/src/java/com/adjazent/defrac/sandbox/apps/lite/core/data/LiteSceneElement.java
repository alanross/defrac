package com.adjazent.defrac.sandbox.apps.lite.core.data;

import com.adjazent.defrac.ui.surface.IUISkin;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneElement
{
	private final Rectangle _dimensions;
	public final IUISkin skin;
	public final String id;

	private boolean _selected;
	private LiteScene _scene;

	public LiteSceneElement( String id, Rectangle dim, IUISkin skin )
	{
		this.id = id;
		this.skin = skin;

		_dimensions = dim;
		_selected = false;
	}

	void attach( LiteScene scene )
	{
		_scene = scene;
	}

	void detach( LiteScene scene )
	{
		_scene = null;
	}

	public void setDimensions( float x, float y, float width, float height )
	{
		_dimensions.set( x, y, width, height );
		_scene.onUpdated( this );
	}

	public Rectangle getDimensions()
	{
		return _dimensions;
	}

	public boolean selected()
	{
		return _selected;
	}

	public void selected( boolean value )
	{
		if( _selected != value )
		{
			_selected = value;
			_scene.onSelected( this );
		}
	}

	@Override
	public String toString()
	{
		return "[LiteSceneElement id:" + id + "]";
	}
}