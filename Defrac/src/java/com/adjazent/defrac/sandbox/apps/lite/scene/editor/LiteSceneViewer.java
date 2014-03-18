package com.adjazent.defrac.sandbox.apps.lite.scene.editor;

import com.adjazent.defrac.sandbox.apps.lite.core.data.ILiteSceneObserver;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteScene;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteSceneItem;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import defrac.display.DisplayObject;
import defrac.display.Layer;
import defrac.display.event.UIEventTarget;
import defrac.geom.Point;
import defrac.geom.Rectangle;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteSceneViewer extends UISurface implements ILiteSceneObserver
{
	protected Layer itemLayer = new Layer();

	protected Hashtable<LiteSceneItem, LiteSceneItemView> items;

	protected LiteScene model;

	public LiteSceneViewer( IUISkin skin )
	{
		super( skin );

		items = new Hashtable<LiteSceneItem, LiteSceneItemView>();

		addChild( itemLayer );
	}

	@Override
	public UIEventTarget captureEventTarget( @javax.annotation.Nonnull Point point )
	{
		Point local = this.globalToLocal( new Point( point.x, point.y ) );

		return ( containsPoint( local.x, local.y ) ) ? this : null;
	}

	@Override
	public void onLiteSceneModified( LiteSceneItem item, int type )
	{
		if( type == LiteScene.ITEM_ATTACHED )
		{
			addItem( item );
		}
		if( type == LiteScene.ITEM_DETACHED )
		{
			removeItem( item );
		}
		if( type == LiteScene.ITEM_SELECTED )
		{
			selectItem( item );
		}
		if( type == LiteScene.ITEM_MODIFIED )
		{
			modifyItem( item );
		}
	}

	public DisplayObject resizeTo( float width, float height )
	{
		super.resizeTo( width, height );

		itemLayer.scrollRect( new Rectangle( 0, 0, width - 2, height - 2 ) );
		itemLayer.moveTo( 1, 1 );

		return this;
	}

	public void populate( LiteScene scene )
	{
		Enumeration<LiteSceneItem> items = this.items.keys();

		while( items.hasMoreElements() )
		{
			removeItem( items.nextElement() );
		}

		if( model != null )
		{
			model.removeObserver( this );
		}

		model = scene;
		model.addObserver( this );

		for( int i = 0; i < model.numItems(); i++ )
		{
			addItem( model.get( i ) );
		}
	}

	protected void addItem( LiteSceneItem item )
	{
		LiteSceneItemView view = new LiteSceneItemView( item );

		items.put( item, view );

		itemLayer.addChild( view );
	}

	protected void removeItem( LiteSceneItem item )
	{
		LiteSceneItemView view = items.remove( item );

		itemLayer.removeChild( view );
	}

	protected void selectItem( LiteSceneItem item )
	{
	}

	protected void modifyItem( LiteSceneItem item )
	{
		LiteSceneItemView view = items.get( item );

		view.update( item.dimensions(), item.alpha() );
	}

	@Override
	public String toString()
	{
		return "[LiteSceneEditor]";
	}
}