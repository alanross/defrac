package com.adjazent.defrac.sandbox.apps.lite.input;

import com.adjazent.defrac.sandbox.apps.lite.core.LiteCore;
import com.adjazent.defrac.sandbox.apps.lite.core.data.LiteInputSource;
import com.adjazent.defrac.ui.surface.UISurface;

import java.util.LinkedList;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class LiteInputList extends UISurface
{
	private final UISurface[] _slots = new UISurface[ 4 ];
	private final LiteInputPreview[] _previews = new LiteInputPreview[ 4 ];

	private boolean _active = false;

	public LiteInputList()
	{
		super();

		resizeTo( 788, 128 );

		int step = 194;
		int offset = 12;
		int n = _slots.length;

		for( int i = 0; i < n; i++ )
		{
			UISurface slot = LiteCore.ui.createSurface( "AreaInput", offset, 12, 184, 103 );

			addChild( slot );

			_slots[ i ] = slot;

			offset += step;
		}

		visible( false );
	}

	public void populate( LinkedList<LiteInputSource> data )
	{
		int n = ( data.size() < _slots.length ) ? data.size() : _slots.length;

		for( int i = 0; i < n; i++ )
		{
			LiteInputPreview p = new LiteInputPreview();
			p.setup( data.get( i ) );
			p.moveTo( 2, 2 );

			_slots[ i ].addChild( p );
			_previews[ i ] = p;
		}
	}

	public void activate( boolean value )
	{
		if( _active != value )
		{
			_active = value;

			visible( value );
		}
	}

	@Override
	public String toString()
	{
		return "[LiteInputList]";
	}
}