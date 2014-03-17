package com.adjazent.defrac.sandbox.apps.lite.core.data;

import com.adjazent.defrac.ui.surface.IUISkin;
import defrac.geom.Rectangle;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneItem
{
	public final float minWidth = 40;
	public final float minHeight = 40;

	public final IUISkin skin;
	public final String id;

	private final Rectangle _dimensions;
	private boolean _selected = false;
	private float _alpha = 1.0f;
	private LiteScene _scene;

	public LiteSceneItem( String id, IUISkin skin, float x, float y, float width, float height )
	{
		this.id = id;
		this.skin = skin;

		_dimensions = new Rectangle( x, y, width, height );
	}

	void attach( LiteScene scene )
	{
		_scene = scene;

		_scene.onItemAttached( this );
	}

	void detach( LiteScene scene )
	{
		_scene.onItemDetached( this );

		_scene = null;
	}

	public void selected( boolean value )
	{
		if( _selected != value )
		{
			_selected = value;

			_scene.onItemSelected( this );
		}
	}

	public boolean selected()
	{
		return _selected;
	}

	public void alpha( float value )
	{
		if( _alpha != value )
		{
			_alpha = value;

			_scene.onItemModified( this );
		}
	}

	public float alpha()
	{
		return _alpha;
	}

	public void setDimensions( Rectangle d )
	{
		setDimensions( d.x, d.y, d.width, d.height );
	}

	public void setDimensions( float x, float y, float width, float height )
	{
		if( x != _dimensions.x || y != _dimensions.y || width != _dimensions.width || height != _dimensions.height )
		{
			_dimensions.set( x, y, width, height );

			_scene.onItemModified( this );
		}
	}

	public Rectangle dimensions()
	{
		return _dimensions;
	}

	public float x()
	{
		return _dimensions.x;
	}

	public float y()
	{
		return _dimensions.y;
	}

	public float width()
	{
		return _dimensions.width;
	}

	public float height()
	{
		return _dimensions.height;
	}

	@Override
	public String toString()
	{
		return "[LiteSceneItem id:" + id + "]";
	}
}