package com.adjazent.defrac.sandbox.experiments.ui.components;

import com.adjazent.defrac.sandbox.experiments.Experiment;
import com.adjazent.defrac.ui.component.list.UICellItem;
import com.adjazent.defrac.ui.component.list.UICellRendererFactory;
import com.adjazent.defrac.ui.component.list.UIList;
import com.adjazent.defrac.ui.resource.IUIResourceLoaderQueueObserver;
import com.adjazent.defrac.ui.resource.UIResourceLoaderQueue;
import com.adjazent.defrac.ui.resource.UIResourceLoaderSparrowFont;
import com.adjazent.defrac.ui.text.font.UIFontManager;
import defrac.event.EnterFrameEvent;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.lang.Procedure;

/**
 * @author Alan Ross
 * @version 0.1
 */
public final class EUIList extends Experiment implements IUIResourceLoaderQueueObserver
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
		_list.moveTo( 100, 150 );
		_list.resizeTo( 200, 350 );

		addChild( _list.getDisplayObject() );

		final int n = 250;

		for( int i = 0; i < n; ++i )
		{
			int h = 20;//20 + ( int ) ( Math.random() * 40 );
			_list.addItem( new UICellItem( "" + i, h ) );
		}

		_list.setOffset( 0 );

		Events.onKeyDown.attach( new Procedure<KeyboardEvent>()
		{
			@Override
			public void apply( KeyboardEvent event )
			{
				_keyCode = event.keyCode;

				if( event.keyCode == 16 )
				{
					_scrollSpeed = SPEED_FAST;
				}
			}
		} );

		Events.onKeyUp.attach( new Procedure<KeyboardEvent>()
		{
			@Override
			public void apply( KeyboardEvent event )
			{
				_keyCode = 0;

				if( event.keyCode == 16 )
				{
					_scrollSpeed = SPEED_SLOW;
				}
			}
		} );

		Events.onEnterFrame.attach( new Procedure<EnterFrameEvent>()
		{
			@Override
			public void apply( EnterFrameEvent event )
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
		} );
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String toString()
	{
		return "[EUIList]";
	}
}