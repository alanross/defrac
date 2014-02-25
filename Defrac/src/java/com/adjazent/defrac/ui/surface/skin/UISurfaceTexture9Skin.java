package com.adjazent.defrac.ui.surface.skin;

import com.adjazent.defrac.core.error.NullError;
import com.adjazent.defrac.core.error.ValueError;
import com.adjazent.defrac.math.geom.MRectangle;
import com.adjazent.defrac.ui.surface.IUISkin;
import com.adjazent.defrac.ui.surface.UISurface;
import com.adjazent.defrac.ui.texture.UITexture;
import com.adjazent.defrac.ui.utils.bitmap.UISlice9Grid;
import defrac.display.Image;
import defrac.display.Texture;
import defrac.display.TextureData;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class UISurfaceTexture9Skin implements IUISkin
{
	private Image _tl = new Image();    //top left
	private Image _tc = new Image();    //top center
	private Image _tr = new Image();    //top right

	private Image _ml = new Image();    // middle left
	private Image _mc = new Image();    // middle center
	private Image _mr = new Image();    // middle right

	private Image _bl = new Image();    // bottom left
	private Image _bc = new Image();    // bottom center
	private Image _br = new Image();    // bottom right

	private int _sliceLeft = 0;
	private int _sliceRight = 0;
	private int _sliceTop = 0;
	private int _sliceBottom = 0;

	private int _minWidth = 0;
	private int _minHeight = 0;

	public UISurfaceTexture9Skin()
	{
	}

	private void prepare( TextureData source, MRectangle rect, UISlice9Grid sliceGrid )
	{
		if( null == source )
		{
			throw new NullError( this + " Source can not be null" );
		}

		_sliceLeft = sliceGrid.left;
		_sliceRight = sliceGrid.right;
		_sliceTop = sliceGrid.top;
		_sliceBottom = sliceGrid.bottom;

		_minWidth = _sliceLeft + _sliceRight;
		_minHeight = _sliceTop + _sliceBottom;

		MRectangle[] slices = UISlice9Grid.createSlices( rect, sliceGrid );

		_tl.texture( slice( source, slices[ 0 ] ) );
		_tc.texture( slice( source, slices[ 1 ] ) );
		_tr.texture( slice( source, slices[ 2 ] ) );

		_ml.texture( slice( source, slices[ 3 ] ) );
		_mc.texture( slice( source, slices[ 4 ] ) );
		_mr.texture( slice( source, slices[ 5 ] ) );

		_bl.texture( slice( source, slices[ 6 ] ) );
		_bc.texture( slice( source, slices[ 7 ] ) );
		_br.texture( slice( source, slices[ 8 ] ) );
	}

	private Texture slice( TextureData textureData, MRectangle rect )
	{
		if( rect.width <= 0 || rect.height <= 0 )
		{
			return null;
		}

		return new Texture( textureData, ( float ) rect.x, ( float ) rect.y, ( float ) rect.width, ( float ) rect.height );
	}

	public void init( UITexture texture )
	{
		prepare( texture.getTextureData(), texture.getSkinRect(), texture.getSliceGrid() );
	}

	@Override
	public void attach( UISurface surface )
	{
		surface.addChild( _tl );
		surface.addChild( _tc );
		surface.addChild( _tr );

		surface.addChild( _ml );
		surface.addChild( _mc );
		surface.addChild( _mr );

		surface.addChild( _bl );
		surface.addChild( _bc );
		surface.addChild( _br );
	}

	@Override
	public void detach( UISurface surface )
	{
		surface.removeChild( _tl );
		surface.removeChild( _tc );
		surface.removeChild( _tr );

		surface.removeChild( _ml );
		surface.removeChild( _mc );
		surface.removeChild( _mr );

		surface.removeChild( _bl );
		surface.removeChild( _bc );
		surface.removeChild( _br );
	}

	@Override
	public void resizeTo( float width, float height )
	{
		if( width <= 0 || height <= 0 )
		{
			return;
		}

		if( width < _minWidth )
		{
			throw new ValueError( this, "Width is smaller than combined left+right slices width. " +
					"Width:" + width + ", Left:" + _sliceLeft + ", Right:" + _sliceRight );
		}

		if( height < _minHeight )
		{
			throw new ValueError( this, "Height is smaller than combined top+bottom slices height. " +
					"Height:" + height + ", Top:" + _sliceTop + ", Bottom:" + _sliceBottom );
		}

		final int offsetRight = ( int ) (width - _sliceRight);
		final int offsetBottom = ( int ) (height - _sliceBottom);
		final int centerWidth = ( int ) (width - ( _sliceLeft + _sliceRight ));
		final int centerHeight = ( int ) (height - ( _sliceTop + _sliceBottom ));

		if( _tl != null )
		{
			_tl.moveTo( 0, 0 );
		}

		if( _bl != null )
		{
			_bl.moveTo( 0, offsetBottom );
		}

		if( _tr != null )
		{
			_tr.moveTo( offsetRight, 0 );
		}

		if( _br != null )
		{
			_br.moveTo( offsetRight, offsetBottom );
		}

		if( _mc != null )
		{
			_mc.moveTo( _sliceLeft, _sliceTop );
			_mc.scaleToSize( centerWidth, centerHeight );
		}

		if( _ml != null )
		{
			_ml.moveTo( 0, _sliceTop );
			_ml.scaleToSize( _sliceLeft, centerHeight );
		}

		if( _mr != null )
		{
			_mr.moveTo( offsetRight, _sliceTop );
			_mr.scaleToSize( _sliceRight, centerHeight );
		}

		if( _tc != null )
		{
			_tc.moveTo( _sliceLeft, 0 );
			_tc.scaleToSize( centerWidth, _sliceTop );
		}

		if( _bc != null )
		{
			_bc.moveTo( _sliceLeft, offsetBottom );
			_bc.scaleToSize( centerWidth, _sliceBottom );
		}
	}

	public int getMinWidth()
	{
		return _minWidth;
	}

	public int getMinHeight()
	{
		return _minHeight;
	}

	@Override
	public String toString()
	{
		return "[UISurfaceTexture9Skin]";
	}
}