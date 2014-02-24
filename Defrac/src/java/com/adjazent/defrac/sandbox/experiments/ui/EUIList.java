package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.sandbox.events.IKeyboard;
import com.adjazent.defrac.ui.widget.list.UICellData;
import com.adjazent.defrac.ui.widget.list.UICellRendererFactory;
import com.adjazent.defrac.ui.widget.list.UIList;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import defrac.event.KeyboardEvent;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIList extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame, IKeyboard
{
	private final int SPEED_SLOW = 3;
	private final int SPEED_FAST = 8;
	private UIList _list;

	private int _scrollSpeed = SPEED_SLOW;
	private int _keyCode;

	public EUIList()
	{
	}

	@Override
	protected void onInit()
	{
		UIFontManager.initialize();

		UIResourceLoaderQueue queue = new UIResourceLoaderQueue();
		queue.add( new UIResourceLoaderSparrowFont( "fonts/helvetica11.png", "fonts/helvetica11.fnt", "helvetica" ) );
		queue.addObserver( this );
		queue.load();
	}

	@Override
	public void onResourceLoadingSuccess()
	{
		_list = new UIList( new UICellRendererFactory() );
		_list.moveTo( 100.0f, 150.0f );
		_list.resizeTo( 200, 350 );
		_list.setBackground( 0xFFA2A2A2 );

		addChild( _list );

		for( int i = 0; i < 30; ++i )
		{
			int h = 20;//20 + ( int ) ( Math.random() * 40 );
			_list.addItem( new UICellData( "" + i, h ) );
		}

		_list.setOffset( 0 );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
	}

	@Override
	public void onEnterFrame()
	{
		if( _keyCode == 38 )
		{
			_list.setOffset( _list.getOffset() - _scrollSpeed );
		}
		if( _keyCode == 40 )
		{
			_list.setOffset( _list.getOffset() + _scrollSpeed );
		}
	}

	@Override
	public void onKeyDown( KeyboardEvent event )
	{
		_keyCode = event.keyCode;

		if( event.keyCode == 16 )
		{
			_scrollSpeed = SPEED_FAST;
		}
	}

	@Override
	public void onKeyUp( KeyboardEvent event )
	{
		_keyCode = 0;

		if( event.keyCode == 16 )
		{
			_scrollSpeed = SPEED_SLOW;
		}
	}

	@Override
	public String toString()
	{
		return "[EUIList]";
	}
}