package com.adjazent.defrac.sandbox.apps.lite.core;

import com.adjazent.defrac.sandbox.apps.lite.scene.settings.LiteSceneItemCellRenderer;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.texture.UITextureAtlas;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.button.UIToggleButton;
import com.adjazent.defrac.ui.widget.list.IUICellRenderer;
import com.adjazent.defrac.ui.widget.list.IUICellRendererFactory;
import com.adjazent.defrac.ui.widget.list.UIList;
import com.adjazent.defrac.ui.widget.list.UIListInteractions;
import com.adjazent.defrac.ui.widget.range.UISlider;
import com.adjazent.defrac.ui.widget.text.UILabel;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteUI implements IUICellRendererFactory
{
	private UITextureAtlas _atlas;

	private LinkedList<IUICellRenderer> _rendererPool = new LinkedList<IUICellRenderer>();

	public LiteUI()
	{
	}

	public void setup( UITextureAtlas atlas )
	{
		_atlas = atlas;
	}

	public IUICellRenderer create( int rowIndex )
	{
		if( 0 >= _rendererPool.size() )
		{
			int n = 8;

			while( --n > -1 )
			{
				_rendererPool.addLast( createCellRenderer() );
			}
		}

		return _rendererPool.pollLast();
	}

	public void release( IUICellRenderer renderer )
	{
		_rendererPool.addLast( renderer );
	}

	public IUISkin createSkin( String skinId )
	{
		return UISkinFactory.create( _atlas.getTexture( skinId ) );
	}

	public UISurface createSurface( String skinId )
	{
		return new UISurface( createSkin( skinId ) );
	}

	public UISurface createSurface( String skinId, int width, int height )
	{
		UISurface surface = new UISurface( createSkin( skinId ) );

		surface.resizeTo( width, height );

		return surface;
	}

	public UISurface createSurface( String skinId, int x, int y, int width, int height )
	{
		UISurface surface = new UISurface( createSkin( skinId ) );

		surface.moveTo( x, y );

		surface.resizeTo( width, height );

		return surface;
	}

	public UILabel createLabelBig()
	{
		return new UILabel( new UITextFormat( "Helvetica24" ) );
	}

	public UILabel createLabelSmall()
	{
		return new UILabel( new UITextFormat( "Helvetica11" ) );
	}

	public UIButton createButton( String skinIdNormal, String skinIdActive )
	{
		return new UIButton( UISkinFactory.create( _atlas.getTexture( skinIdNormal ) ) );
	}

	public UIToggleButton createToggleButton( String skinIdDeselected, String skinIdSelected )
	{
		return new UIToggleButton(
				UISkinFactory.create( _atlas.getTexture( skinIdDeselected ) ),
				UISkinFactory.create( _atlas.getTexture( skinIdSelected ) )
		);
	}

	public UISlider createSlider( String skinIdTrack, String skinIdThumb, String skinIdValue )
	{
		return new UISlider(
				UISkinFactory.create( _atlas.getTexture( skinIdTrack ) ),
				UISkinFactory.create( _atlas.getTexture( skinIdThumb ) ),
				UISkinFactory.create( _atlas.getTexture( skinIdValue ) )
		);
	}

	public UIList createList()
	{
		return new UIList( this, new UIListInteractions() );
	}

	public LiteSceneItemCellRenderer createCellRenderer()
	{
		return new LiteSceneItemCellRenderer(
				UISkinFactory.create( _atlas.getTexture( "ListCellDeselected" ) ),
				UISkinFactory.create( _atlas.getTexture( "ListCellSelected" ) ),
				new UITextFormat( "Helvetica11" ),
				_atlas.getTexture( "ListIconImageDeselected" ).getTexture()
		);
	}

	@Override
	public String toString()
	{
		return "[LiteUI]";
	}
}