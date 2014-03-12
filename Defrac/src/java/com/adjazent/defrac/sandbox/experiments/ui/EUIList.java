package com.adjazent.defrac.sandbox.experiments.ui;

import com.adjazent.defrac.core.notification.action.Action;
import com.adjazent.defrac.core.notification.action.IActionObserver;
import com.adjazent.defrac.sandbox.Experiment;
import com.adjazent.defrac.sandbox.events.IEnterFrame;
import com.adjazent.defrac.sandbox.events.IKeyboard;
import com.adjazent.defrac.ui.surface.skin.UISkinFactory;
import com.adjazent.defrac.ui.text.UITextFormat;
import com.adjazent.defrac.ui.widget.button.UIButton;
import com.adjazent.defrac.ui.widget.list.UICellData;
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
public final class EUIList extends Experiment implements IUIResourceLoaderQueueObserver, IEnterFrame, IKeyboard, IActionObserver
{
	private final int SPEED_SLOW = 3;
	private final int SPEED_FAST = 8;
	private UIList _list;

	private int _scrollSpeed = SPEED_SLOW;
	private int _keyCode;

	private UIButton _buttonAdd;
	private UIButton _buttonRemove;

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
		_list = new UIList( new MyCellRendererFactory( new UITextFormat( "Helvetica" ) ) );
		_list.moveTo( 100, 150 );
		_list.resizeTo( 200, 350 );
		_list.setBackground( 0xFFA2A2A2 );


		for( int i = 0; i < 3; ++i )
		{
			int h = 20;//20 + ( int ) ( Math.random() * 40 );
			_list.addItem( new UICellData( "Item " + _list.numItems(), h ) );
		}

		_list.setOffset( 0 );

		_buttonAdd = new UIButton( UISkinFactory.create( 0xFF00FF00 ) );
		_buttonAdd.resizeTo( 90, 20 );
		_buttonAdd.moveTo( 100, 520 );
		_buttonAdd.onClick.add( this );

		_buttonRemove = new UIButton( UISkinFactory.create( 0xFFFF0000 ) );
		_buttonRemove.resizeTo( 90, 20 );
		_buttonRemove.moveTo( 210, 520 );
		_buttonRemove.onClick.add( this );

		addChild( _buttonAdd );
		addChild( _buttonRemove );
		addChild( _list );

		activateEvents();
	}

	@Override
	public void onResourceLoadingFailure( Error error )
	{
	}

	@Override
	public void onActionEvent( Action action )
	{
		if( action.origin == _buttonAdd )
		{
			_list.addItem( new UICellData( "Item " + _list.numItems(), 20 ) );
		}
		if( action.origin == _buttonRemove )
		{
			if( _list.numItems() > 0 )
			{
				_list.removeItemAt( _list.numItems() - 1 );
			}
		}
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